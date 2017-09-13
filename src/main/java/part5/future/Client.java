package part5.future;

/**
 * Created by Dell on 2017-08-03.
 */
public class Client {
    public Data request(final String queryStr){
        final FutureData future=new FutureData();
        new Thread(){
            @Override
            public void run(){
                RealData realData= null;
                try {
                    realData = new RealData(queryStr);
                } catch (InterruptedException e) {e.printStackTrace();}
                future.setRealData(realData);
            }
        }.start();
        return future;
    }
}
