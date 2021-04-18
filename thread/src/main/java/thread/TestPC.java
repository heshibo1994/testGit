package thread;

public class TestPC {
    public static void main(String[] args) {
        SynContainer container = new SynContainer();
        new Producer(container).start();
        new Consumer(container).start();
    }

}

class Producer extends Thread{
    SynContainer synContainer;
    public Producer(SynContainer synContainer){
        this.synContainer = synContainer;
    }

    @Override
    public void run(){
        for (int i = 0; i < 100; i++) {
            synContainer.push(new Product(i));
            System.out.println("生产了"+i+"个产品");

        }
    }
}
class Consumer extends Thread{
    SynContainer synContainer;
    public Consumer(SynContainer synContainer){
        this.synContainer = synContainer;
    }

    @Override
    public void run(){
        for (int i = 0; i < 100; i++) {
            synContainer.pop();
            System.out.println("消费了"+synContainer.pop().id+"个产品");

        }
    }
}

class Product {
    int id;
    Product (int id){
        this.id = id;
    }

}
class SynContainer{
    // 定义容器大小
    Product [] products = new Product[10];

    int count = 0;
    // 生产者放入产品
    public synchronized void push(Product product){
        // 如果容器满了，就等待消费者消费
        if (count == products.length){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 否则，生产产品放入
        products[count] = product;
        count++;
        this.notifyAll();
    }

    public synchronized Product pop(){
        // 如果容器为空，则无法消费
        if (count==0){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 可以消费
        count--;
        Product product = products[count];
        this.notifyAll();
        return product;

    }
}