package part5.useJdkFuture;

import java.util.concurrent.Callable;

/**
 * Created by Dell on 2017-08-03.
 */
public class RealData implements Callable<String> {
    private String para;

    public RealData(String para) {
        this.para = para;
    }
    //构造真实数据并返回
    public String call() throws Exception {
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<10;i++){
            sb.append(para);
            Thread.sleep(100);
        }
        return sb.toString();
    }
}
