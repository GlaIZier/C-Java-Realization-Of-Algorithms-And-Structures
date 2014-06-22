import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by mkhokhlushin on 03.04.2014.
 */
public class ConcurrentBST<K extends Comparable<K>, V> implements IMap<K, V> {

   // use lock to avoid concurrency of threads
   private static final Lock LOCK = new ReentrantLock();

   // no need to declare volatile because of locks usage. It synchronizes cached variables
   private volatile Node root;

   private volatile int size;

   private class Node implements Comparable<Node> {
      // atomic to read from increment it concurrently
      private AtomicInteger editsNumber = new AtomicInteger(0);
      private final K key;
      private AtomicReference<V> value;
      private volatile Node left;
      private volatile Node right;

      private Node(K key, V value) {
         this.key = key;
         this.value = new AtomicReference<V>(value);
         this.editsNumber = new AtomicInteger(0);
      }
      @Override
      public int compareTo(Node that) {
         if (that == null) throw new IllegalArgumentException();
         if (this.key.compareTo(that.key) < 0) return -1;
         else if (this.key.compareTo(that.key) > 0) return 1;
         else return 0;
      }
      @Override
      public String toString() {
         return "Key: " + key + "; Value: " + value;
      }
   }

   // forEach method class
   public static class PrintAll<K extends Comparable<K>, V>  implements IProcess<K, V> {
      private List<Thread> processes = new ArrayList<Thread>();
      @Override
      public void add(Thread t) {
         if (t != null) processes.add(t);
      }
      @Override
      public void process(K key, V value, Thread invoked) {
         add(invoked);
         process(key, value);
      }
      @Override
      public void process(K key, V value) {
         System.out.println(key + ": " + value);
      }
      @Override
      public void startAndWait() {
         try {
            // JDK8
            processes.stream().forEach( (t) -> t.start() );
            // And legacy
            for (Thread t : processes) {
               try {
                  t.join();
               } catch (InterruptedException e) {
                  e.printStackTrace();
               }
            }
         }
         // if fail try again
         catch (ConcurrentModificationException e) {
            startAndWait();
         }
         processes.clear();
      }
   }

   public class PrintAllTask extends RecursiveAction {
      private final Node current;
      private final IProcess process;
      public PrintAllTask(Node current, IProcess process) {
         this.current = current;
         this.process = process;
      }
      @Override
      protected void compute() {
//         System.out.println(current);
         process.process(current.key, current.value);
         if (current.left != null) {
            PrintAllTask left = new PrintAllTask(current.left, process);
            left.fork();
            left.join();
         }
         if (current.right != null) {
            PrintAllTask right = new PrintAllTask(current.right, process);
            right.fork();
            right.join();
         }
      }
   }

   public class TreeSumTask extends RecursiveTask<Integer> {
      private final Node current;
      private Integer currentSum;

      public TreeSumTask(Node current) {
         this(current, 0);
      }
      public TreeSumTask(Node current, Integer currentSum) {
         this.current = current;
         this.currentSum = currentSum;
      }
      @Override
      protected Integer compute() {
         if (!(current.value.get() instanceof Integer) )
            throw new IllegalArgumentException("Can't calculate sum of not Integers!");
         currentSum += (Integer) current.value.get();
         if (current.left != null) {
            TreeSumTask left = new TreeSumTask(current.left);
            left.fork();
            currentSum += left.join();
         }
         if (current.right != null) {
            TreeSumTask right = new TreeSumTask(current.right);
            right.fork();
            currentSum += right.join();
         }
         return currentSum;
      }
   }

   @Override
   public boolean isEmpty() {
      return size == 0;
   }

   @Override
   public int size() {
      return size;
   }

   @Override
   public void add(K key, V value) {
      Node newNode = new Node(key, value);
//      System.out.println("Node created: " + newNode );
      // Node where we want to add. It refers to previous to current Node.
      Node targetNode = null;
      Node current = root;
      // Search place to add
      while (current != null) {
         targetNode = current;
         if (newNode.compareTo(current) > 0)
            current = current.right;
         else if (newNode.compareTo(current) < 0)
            current = current.left;
         else
            break;
      }
      LOCK.lock();
      try {
        // System.out.println("Locked: " + targetNode + "; " + newNode);
         // if target = null it means that bst is empty
         if (targetNode == null)
            root = addToLockedBranch(root, newNode);
         else if (newNode.compareTo(targetNode) > 0)
            targetNode.right = addToLockedBranch(targetNode.right, newNode);
         else if (newNode.compareTo(targetNode) < 0)
            targetNode.left = addToLockedBranch(targetNode.left, newNode);
         else {
            System.out.println("Node was edited: " + newNode + ". Target:" + targetNode);
            targetNode.value.set(newNode.value.get());
            targetNode.editsNumber.incrementAndGet();
         }
      }
      finally {
         LOCK.unlock();
      }
   }

   private Node addToLockedBranch(Node current, Node newNode) {
      if (current == null) {
         size++;
         System.out.println("Node was added: " + newNode + ". Root: " + root);
         return newNode;
      }
      if (newNode.compareTo(current) < 0) current.left = addToLockedBranch(current.left, newNode);
      else if (newNode.compareTo(current) > 0) current.right = addToLockedBranch(current.right, newNode);
      else {
         System.out.println("Node was edited: " + newNode + ". Current:" + current + ".");
         current.value.set(newNode.value.get());
         current.editsNumber.incrementAndGet();
      }
      return current;
   }

   @Override
   public V get(K key) {
      Node node = get(root, key);
      if (node == null) {
         System.out.println("Missed to get " + key.toString());
         return null;
      }
      else {
         System.out.println("Get " +  key.toString() + " by thread " + Thread.currentThread());
         return  node.value.get();
      }
   }

   private Node get(Node current, K key) {
      if (current == null) return null;
      if (key.compareTo(current.key) < 0) return get(current.left, key);
      else if (key.compareTo(current.key) > 0) return get(current.right, key);
      else return current;
   }

   @Override
   public boolean isContained(K key) {
      return get(key) != null;
   }

   // returns -1 if no such key
   public int getEditsCount(K key) {
      Node node = get(root, key);
      if (node == null)
         return -1;
      else
         return node.editsNumber.get();
   }

   @Override
   public void forEach(IProcess process) {
      System.out.println("\nForEach(): ");
      forEach(root, process);
      process.startAndWait();
   }

   private void forEach (final Node current, final IProcess process) {
      if (current == null)
         return;
      if (current.left != null)
         forEach(current.left, process);
      // process with java 8. Add Runnable to new Thread
      process.add( new Thread ( () -> process.process(current.key, current.value) ) );
      if (current.right != null)
         forEach(current.right, process);
   }

   @Override
   public void forkJoinForEach(IProcess process) {
      System.out.println("\nforkJoinForEach()");
      ForkJoinPool fjPool = new ForkJoinPool();
      PrintAllTask rootTask = new PrintAllTask(root, process);
      fjPool.submit(rootTask);
      rootTask.join();
   }

   @Override
   public void printAll() {
      System.out.println("\nAll tree by printAll()");
      printAll(root);
   }

   private void printAll(Node current) {
      if (current == null)
         return;
      if (current.left != null)
         printAll(current.left);
      System.out.println(current);
      if (current.right != null)
         printAll(current.right);
   }

   @Override
   public void printAllWithExec() {
      ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
      System.out.println("\nAll tree by printAllWithExec()");
      printAll(root, executorService);
      executorService.shutdown();
      try {
         executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
   }

   private void printAll(final Node current, ExecutorService executorService) {
      if (current == null)
         return;
      if (current.left != null)
         printAll(current.left, executorService);
      executorService.submit(new Runnable() {
         @Override
         public void run() {
            System.out.println(current);
         }
      } );
      if (current.right != null)
         printAll(current.right, executorService);
   }

   // Just to test RecursveTask
   public void printTreeSum() {
      System.out.println("\n Tree sum by printTreeSum()");
      ForkJoinPool fjPool = new ForkJoinPool();
      TreeSumTask rootTask = new TreeSumTask(root);
      fjPool.submit(rootTask);
      System.out.println(rootTask.join());
   }
}