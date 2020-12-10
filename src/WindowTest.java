import javafx.beans.binding.ObjectExpression;

class Window implements Runnable{
    private int ticket = 100;

    public void run(){

        while(true){
            synchronized (this){
                if (ticket > 0){
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    System.out.println(Thread.currentThread().getName() + ": Sold Ticket, remaining: " + ticket);
                    ticket--;
                }
                else {
                    break;
                }
            }
        }
    }
}


public class WindowTest {
    public static void main(String[] args) {
        Window w = new Window();

        Thread window1 = new Thread(w);
        Thread window2 = new Thread(w);
        Thread window3 = new Thread(w);

        window1.setName("w1");
        window2.setName("w2");
        window3.setName("w3");

            window2.start();
            window1.start();
            window3.start();
    }
}

