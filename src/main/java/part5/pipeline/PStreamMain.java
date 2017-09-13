package part5.pipeline;

/**
 * Created by Dell on 2017-08-03.
 */
public class PStreamMain {
    //将操作分布在不同的线程中，线程间的信息交换,利用多核优势
    public static void main(String[] args){
        new Thread(new Plus()).start();
        new Thread(new Multiply()).start();
        new Thread(new Div()).start();

        for(int i=1;i<=1000;i++){
            for(int j=1;j<=1000;j++){
                Msg msg=new Msg();
                msg.i=i;
                msg.j=j;
                msg.orgStr="(("+i+"+"+j+")*"+i+")/2";
                Plus.bq.add(msg);
            }
        }

    }
}
