package thread;

public class StopThread implements Runnable {
    private boolean flag = true;

    @Override
    public void run() {
        int i = 0;
        while (flag) {
            System.out.println(Thread.currentThread().getName() + "执行第" + i++ + "次");
        }
    }

    public void stop(){
        this.flag = false;
    }

    public static void main(String[] args) {
        StopThread stopThread = new StopThread();
        new Thread(stopThread,"线程A").start();

        for (int i = 0; i < 1000; i++) {
            if (i==900){
                stopThread.stop();
                System.out.println("该线程停止");
            }
            System.out.println(Thread.currentThread().getName() + "执行第" + i + "次");

        }
    }

}
