package part5.useJdkFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by Dell on 2017-08-03.
 */
public class FutureMain {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        //构造FutureTask
        FutureTask<String> future=new FutureTask<String>(new RealData("ac"));
        ExecutorService executor= Executors.newFixedThreadPool(1);
        //执行Futuretask,相当于上例中client发送请求。
        //开启线程进行RealData的Call（）
        executor.submit(future);
        System.out.println("请求完毕");
        Thread.sleep(2000);
        System.out.println("数据   ="+future.get());
    }
}
