package part3;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by Dell on 2017-07-31.
 */
public class CyclicBarrierDemo {
    public static class Soldier implements Runnable{
        private String soldier;
        private final CyclicBarrier cyclic;

        public Soldier(CyclicBarrier cyclic,String soldier) {
            this.soldier = soldier;
            this.cyclic = cyclic;
        }
        @Override
        public void run() {
            try {
                cyclic.await();//会进行下一次计数
                doWork();
                cyclic.await();//下一次
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
        void doWork() throws InterruptedException {
            Thread.sleep(Math.abs(new Random().nextInt())%20000);
            System.out.println(soldier+":任务完成");
        }
    }

    public static class BarrierRun implements Runnable{
        boolean flag;
        int N;

        public BarrierRun(boolean flag, int n) {
            this.flag = flag;
            N = n;
        }

        @Override
        public void run() {
            if(flag){
                System.out.println("司令：【士兵"+N+"个，任务完成！】");
            }else{
                System.out.println("司令：【士兵"+N+"个，集合完毕！】");
                flag=true;
            }
        }
    }
    public static void main(String[] args){
        final int N=10;
        Thread[] allSoldier=new Thread[N];
        boolean flag=false;
        //这个循环栅栏会跑两次，一次是所有线程都到的时候，领一次是所有线程完成工作时
        CyclicBarrier cyclic=new CyclicBarrier(N,new BarrierRun(flag,N));//这是一个循环栅栏，当有N个线程时，就会执行第二个参数里的任务
        System.out.println("集合队伍！！！！！");
        for(int i=0;i<N;++i){
            System.out.println("士兵"+i+"报道！");
            allSoldier[i]=new Thread(new Soldier(cyclic,"士兵"+i));
            allSoldier[i].start();
            if(i==5){
                allSoldier[0].interrupt();
            }
        }


    }
}
