package Counter_ex6;

public class CounterMain {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter(0);
        String [] methods = new String[] {"asynchronous", "synchronized block", "synchronized method", "locker"};

        for (String method : methods) {
            System.out.println(method + ":");
            Thread increment = new Thread(new IncrementThread(counter, method));
            Thread decrement = new Thread(new DecrementThread(counter, method));
            increment.start();
            decrement.start();
            increment.join();
            decrement.join();
            System.out.println(counter.getCounter());
            counter.setCounter(0);
        }

        System.out.println("Декілька потоків:");
        for (String method : methods) {
            System.out.println(method + ":");
            Thread increment1 = new Thread(new IncrementThread(counter, method));
            Thread decrement1 = new Thread(new DecrementThread(counter, method));
            Thread increment2 = new Thread(new IncrementThread(counter, method));
            Thread decrement2 = new Thread(new DecrementThread(counter, method));
            increment1.start();
            decrement1.start();
            increment2.start();
            decrement2.start();
            increment1.join();
            decrement1.join();
            increment2.join();
            decrement2.join();

            System.out.println(counter.getCounter());
            counter.setCounter(0);
        }
    }
}
