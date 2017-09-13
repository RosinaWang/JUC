package part5.produceCustom;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * 生产者生产PCData，放入BlockingQueue
 * Created by Dell on 2017-08-02.
 */
public class Producer implements Runnable{
    private BlockingQueue<PCData> queue;

    public Producer(BlockingQueue<PCData> queue) {
        this.queue = queue;
    }


    @Override
    public void run() {
        while (true){
            PCData data=new PCData(new Random().nextInt(1000));
            try {
                queue.put(data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Producer produce "+data.getDate());
        }
    }
}
