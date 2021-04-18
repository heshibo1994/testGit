package thread;
// 线程不安全，会有复负数
public class UnsafeBuyTicket {
    public static void main(String[] args) {
        BuyTicket station = new BuyTicket();
        new Thread(station,"A").start();
        new Thread(station,"B").start();
        new Thread(station,"C").start();
    }
}



class BuyTicket implements Runnable{
    private int ticketName = 10;
    boolean flag = true;


    @Override
    public void run() {
        while (true){
            try {
                buy();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public synchronized void buy() throws InterruptedException {
        if (ticketName<=0){
            flag = false;
            return;
        }
        Thread.sleep(1000);
        System.out.println(Thread.currentThread().getName()+"买了第"+ticketName--);
    }
}