package part5.disruptor;

import com.lmax.disruptor.WorkHandler;

/**
 * Created by Dell on 2017-08-03.
 */
public class Consumer implements WorkHandler<PCData> {
    public void onEvent(PCData event) throws Exception {
        System.out.println(Thread.currentThread().getId()+":Event:----"+event.getDate()*event.getDate());
    }
}
