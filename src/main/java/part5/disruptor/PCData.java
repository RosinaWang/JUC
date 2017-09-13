package part5.disruptor;

/**
 * 这个是生产者生产的产品，供消费者消费
 * Created by Dell on 2017-08-02.
 */
public class PCData {
    private long date;

    public void setDate(long date) {
        this.date = date;
    }
    public long getDate() {
        return date;
    }
}
