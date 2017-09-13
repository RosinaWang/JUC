package part5.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * Created by Dell on 2017-08-03.
 */
public class PCDataFactory implements EventFactory<PCData>{
    public PCData newInstance() {
        return new PCData();
    }
}
