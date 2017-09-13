package part5.produceCustom;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<PCData> queue=new ArrayBlockingQueue<PCData>(20);

        Thread p=new Thread(new Producer(queue));
        Thread c=new Thread(new Consumer(queue));
        p.start();c.start();
        p.join();
        c.join();

    }
}
