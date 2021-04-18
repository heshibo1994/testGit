package thread;

// 模拟买票
public class MyRunnableBuyTicket implements Runnable {
    private static int ticketNum = 10;

    @Override
    public void run() {
        while (true) {
            if (ticketNum <= 0) {
                break;
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "买了第" + ticketNum-- + "张票");
        }
    }


    public static void main(String[] args) {
        MyRunnableBuyTicket ticket = new MyRunnableBuyTicket();
        new Thread(ticket, "A").start();
        new Thread(ticket, "B").start();
        new Thread(ticket, "C").start();
    }
}
