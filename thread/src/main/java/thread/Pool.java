package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Pool {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(10);
        service.execute(new MyThreadPool());
        service.execute(new MyThreadPool());
        service.execute(new MyThreadPool());
        service.execute(new MyThreadPool());
        service.shutdown();
    }
}

class MyThreadPool implements Runnable{
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }
}
