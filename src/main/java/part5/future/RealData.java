package part5.future;

/**
 * Created by Dell on 2017-08-03.
 */
public class RealData {
    protected final String result;

    public String getResult() {
        return result;
    }

    public RealData(String result) throws InterruptedException {
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<10;i++){
            sb.append(result);
            Thread.sleep(100);
        }
        this.result = sb.toString();
    }
}
