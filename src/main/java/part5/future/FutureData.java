package part5.future;

/**是Future的关键，实际上是RealData的代理，封装了获取RealData等待过程
 * Created by Dell on 2017-08-03.
 */
public class FutureData implements Data {
    protected RealData realData=null;//FutureData是RealData的包装
    protected boolean isReady=false;
    public synchronized void setRealData(RealData realData){
        if(isReady)
            return;
        this.realData=realData;//注入RealData
        isReady=true;
        notifyAll();//通知数据可以使用
    }

    /**
     * 获取真实数据，如果没完成，会一直等待，直到数据完成
     * @return
     */
    public synchronized String getResult() {
        while (!isReady){
            try {
                System.out.println("获取RealData要等待一下");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return realData.result;
    }
}
