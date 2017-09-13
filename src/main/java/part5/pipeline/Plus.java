package part5.pipeline;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Dell on 2017-08-03.
 */
public class Plus implements Runnable {
    public static BlockingQueue<Msg> bq=new LinkedBlockingQueue<Msg>();
    public void run() {
        while(true){
            try {
                Msg msg=bq.take();
                msg.j=msg.i+msg.j;
                Multiply.bq.add(msg);
            } catch (InterruptedException e) {e.printStackTrace();}

        }
    }
}
