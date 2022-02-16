package Counter_ex6;

public class IncrementThread implements Runnable{
    private final Counter counter;
    private final String method;

    public IncrementThread(Counter counter, String method){
        this.counter = counter;
        this.method = method;
    }

    @Override
    public void run() {
        switch (method){
            case ("asynchronous"):
                for (int i=0; i<100000; i++){
                    counter.asyncIncrement();
                }
                break;
            case ("synchronized block"):
                for (int i=0; i<100000; i++){
                    counter.syncIncrementBlock();
                }
                break;
            case ("synchronized method"):
                for (int i=0; i<100000; i++){
                    counter.syncIncrementMethod();
                }
                break;
            case ("locker"):
                for (int i=0; i<100000; i++){
                    counter.lockIncrement();
                }
                break;
        }
    }
}
