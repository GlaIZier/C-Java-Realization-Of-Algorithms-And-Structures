import java.util.List;

/**
 * Created by mkhokhlushin on 03.04.2014.
 */
public interface IMap<K extends Comparable<K>, V> {

   interface IProcess<K extends Comparable<K>, V> {
      /**
       *  Add to list of Threads
       */
      public void add(Thread t);
      public void process(K key, V value, Thread invoked);
      /**
       * Process the IMap. Usually is used in a new Thread
       * @param key
       * @param value
       */
      public void process(K key, V value);
      /**
       *  Starts and waits all added threads
       */
      public void startAndWait();
   }

   // default methods by java 8
   default public boolean isEmpty() {
      return true;
   };

   default public int size() {
      return 0;
   };

   public void add(K key, V value);

   public boolean isContained(K key);

   public V get(K key);

   public int getEditsCount(K key);

   public void forEach(IProcess process);

   public void forkJoinForEach(IProcess process);

   public void printAll();

   public void printAllWithExec();

}
