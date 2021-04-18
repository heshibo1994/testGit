package thread;

// 死锁：多个线程获取了对方的资源
public class DeadLock {
    public static void main(String[] args) {
        MyDeadLockThread myDeadLockThreadA = new MyDeadLockThread(true,"aaa");
        MyDeadLockThread myDeadLockThreadB = new MyDeadLockThread(false,"bbb");
        myDeadLockThreadA.start();
        myDeadLockThreadB.start();
    }
}


class A {
}

class B {
}

class MyDeadLockThread extends Thread {
    boolean choice ;
    String name;

    public MyDeadLockThread(boolean choice,String name){
        this.choice = choice;
        this.name=name;
    }


    static A a = new A();
    static B b = new B();
    @Override
    public void run() {
        try {
            fun();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void fun() throws InterruptedException {
        if (choice){
            synchronized (a){
                System.out.println(this.name+"获取到a的锁");
                Thread.sleep(1000);
            }
            synchronized (b){
                System.out.println(this.name+"获取到b的锁");
            }
        }else {
            synchronized (b){
                System.out.println(this.name+"获取到b的锁");
                Thread.sleep(1000);

            }
            synchronized (a){
                System.out.println(this.name+"获取到a的锁");
            }
        }
    }
}


