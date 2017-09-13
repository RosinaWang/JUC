package part5.pipeline;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Dell on 2017-08-03.
 */
public class Multiply implements Runnable {
    public static BlockingQueue<Msg> bq=new LinkedBlockingQueue<Msg>();
    public void run() {
        while(true){
            try {
                Msg msg=bq.take();
                msg.i=msg.i*msg.j;
                Div.bq.add(msg);
            } catch (InterruptedException e) {e.printStackTrace();}

        }
    }
}
