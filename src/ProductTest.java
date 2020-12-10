class Clerk{
    private int productCount = 0;
    public void produceProduct() {
        synchronized (this) {
            if (productCount < 20) {
                System.out.println("Produce Product No." + ++productCount);
                notify();
            } else {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void consumeProduct() {
        synchronized (this) {
            if (productCount > 0) {
                System.out.println("Consume Product No." + productCount--);
                notify();
            } else {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

class Producer extends Thread{
        private Clerk clerk;

        public Producer(Clerk clerk){
            this.clerk = clerk;
        }

    @Override
    public void run() {
            while(true){
                try {
                    sleep(15);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                clerk.produceProduct();
            }

    }
}

class Consumer extends Thread{
    private Clerk clerk;

    public Consumer(Clerk clerk){
        this.clerk = clerk;
    }

    @Override
    public void run() {
        while(true){
            try {
                sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            clerk.consumeProduct();
        }
    }
}

public class ProductTest {
    public static void main(String[] args) {
        Clerk clerk = new Clerk();

        Producer producer = new Producer(clerk);
        Consumer consumer = new Consumer(clerk);
        producer.start();
        consumer.start();
    }
}
