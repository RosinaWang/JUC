package part5.disruptor;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * Created by Dell on 2017-08-03.
 */
public class Producer {
    private final RingBuffer<PCData> ringBuffer;
    public Producer(RingBuffer<PCData> ringBuffer) {
        this.ringBuffer=ringBuffer;
    }

    public void pushData(ByteBuffer bb) {
        long sequence=ringBuffer.next();
        try {
            PCData event = ringBuffer.get(sequence);
            event.setDate(bb.getLong(0));
        }finally {
            ringBuffer.publish(sequence);
        }
    }
}
