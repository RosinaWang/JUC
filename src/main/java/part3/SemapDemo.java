package part3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by Dell on 2017-07-31.
 */
public class SemapDemo implements Runnable {
    static final Semaphore semp=new Semaphore(5);

    @Override
    public void run() {
        try {
            semp.acquire();
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getId()+":done!");
            semp.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        ExecutorService exec= Executors.newFixedThreadPool(20);
        SemapDemo d1=new SemapDemo();
        SemapDemo d2=new SemapDemo();
        for(int i=0;i<20;i++){
            if(i>=10){
                exec.submit(d1);
            }else{
                exec.submit(d2);
            }
        }
    }












}
