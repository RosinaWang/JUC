package part5.produceCustom;

/**
 * 这个是生产者生产的产品，供消费者消费
 * Created by Dell on 2017-08-02.
 */
public class PCData {
    private int intDate;

    public void setDate(int date) {
        this.intDate = date;
    }
    public PCData() {
    }

    public PCData(int Date) {
        this.intDate = Date;
    }
    public int getDate() {
        return intDate;
    }
}
