package part5.pipeline;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Dell on 2017-08-03.
 */
public class Div implements Runnable {
    public static BlockingQueue<Msg> bq=new LinkedBlockingQueue<Msg>();
    public void run() {
        while(true){
            try {
                Msg msg=bq.take();
                msg.i=msg.i/2;
                System.out.println(msg.orgStr+"="+msg.i);
            } catch (InterruptedException e) {e.printStackTrace();}
        }
    }
}
