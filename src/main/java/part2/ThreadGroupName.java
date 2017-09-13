package part2;

/**
 * Created by Dell on 2017-07-31.
 */
public class ThreadGroupName implements Runnable{
    public static void main(String[] args) throws InterruptedException {
       ThreadGroup tg=new ThreadGroup("PrintGroup");
       Thread t1=new Thread(tg,new ThreadGroupName(),"T1");
        Thread t2=new Thread(tg,new ThreadGroupName(),"T2");
        t1.start();
        t2.start();
        System.out.println(tg.activeCount());
        tg.list();//打印出线程组的所有线程信息
    }
    @Override
    public void run() {
        String groupAndName=Thread.currentThread().getThreadGroup().getName()+"-"+Thread.currentThread().getName();
        while (true){
            System.out.println("I am "+groupAndName);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
