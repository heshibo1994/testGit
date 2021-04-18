package thread;

public class MyRunnable implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            System.out.println("MyThread。。。。。" + i);
        }
    }

    public static void main(String[] args) {
        MyRunnable myRunnable = new MyRunnable();
        new Thread(myRunnable).start();

        for (int i = 0; i < 2000; i++) {
            System.out.println("main。。。。。" + i);
        }
    }
}
