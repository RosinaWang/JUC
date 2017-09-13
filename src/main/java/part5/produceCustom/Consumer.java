package part5.produceCustom;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Dell on 2017-08-02.
 */
public class Consumer implements Runnable {
    private BlockingQueue<PCData> queue;

    public Consumer(BlockingQueue<PCData> queue) {
        this.queue = queue;
    }


    @Override
    public void run() {
        while (true){
            PCData data= null;
            try {
                data = queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Consumer consume "+data.getDate()+"   res=");
        }
    }
}
