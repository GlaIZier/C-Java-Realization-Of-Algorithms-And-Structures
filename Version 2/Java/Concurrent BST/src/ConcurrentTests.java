import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Mike on 06.04.2014.
 */
public class ConcurrentTests {

   private static final int THREADS_NUMBER = 100;

   public static void main(String[] args) throws InterruptedException {
      final ConcurrentBST<Integer, Integer> bst = new ConcurrentBST<Integer, Integer>();

      List<Thread> threads = new ArrayList<Thread>();
      for (int i = 0; i < THREADS_NUMBER; i++) {
         final int ii = i;
         threads.add(new Thread() {
            @Override
            // use legacy code
            public void run() {
               Random r = new Random();
               int n = r.nextInt(100) + 1;
               if (n <= 25) {
                  n = r.nextInt(100) + 1;
                  bst.add(n, n);
               }
               else {
                  n = r.nextInt(100) + 1;
                  bst.get(n);
               }
            }
         });
      }
      // start all with Java8
      threads.stream().forEach( (t) -> t.start() );

      // wait for complete all threads. Legacy code
      for (Thread t : threads) {
         t.join();
      }

      System.out.println(bst.size());
      System.out.println(bst.getEditsCount(100));

      // print all
      IMap.IProcess<Integer, Integer> printAllProcess = new ConcurrentBST.PrintAll<Integer, Integer>();
      bst.forEach(printAllProcess);
      bst.forkJoinForEach(printAllProcess);
      bst.printAll();
      bst.printAllWithExec();
      bst.printTreeSum();
   }
}
