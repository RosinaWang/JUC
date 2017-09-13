package part6;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created by Dell on 2017-08-09.
 */
public class TestCompletableFuture {
    public static class AskThread implements Runnable{
        CompletableFuture<Integer> re=null;
        public AskThread(CompletableFuture<Integer> re) {
            this.re = re;
        }
        public void run() {
            int myRe=0;
            try {
                myRe=re.get()*re.get();
            } catch (Exception e) {e.printStackTrace();}
            System.out.println(myRe);
        }
    }
    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        final CompletableFuture<Integer> future=new CompletableFuture<>();
//        new Thread(new AskThread(future)).start();
//        Thread.sleep(200);
//        future.complete(60);

    //CompletableFuture有公共线程池ForkJoinPool，其中都是守护线程，如果主线程退出，线程池也将结束
//        final CompletableFuture<Void> future=CompletableFuture.supplyAsync(()->calc(50))
//                .exceptionally(ex->{System.out.println(ex.toString());return 0;})
//                .thenApply((i)->Integer.toString(i))
//                .thenApply((str)->"\""+str+"\"")
//                .thenAccept(System.out::println);
//        System.out.println(future.get());

        //组合多个CompletableFuture
//        CompletableFuture<Void> fu=
//                CompletableFuture.supplyAsync(()->calc(50))
//                .thenCompose((i)->CompletableFuture.supplyAsync(()->calc(i)))
//                .thenApply((str)->"\""+str+"\"")
//                .thenAccept(System.out::println);
//        System.out.println("fu      get  "+fu.get());

        //组合
        CompletableFuture<Integer> intFu1=CompletableFuture.supplyAsync(()->calc(50));
        CompletableFuture<Integer> intFu2=CompletableFuture.supplyAsync(()->calc(10));

        CompletableFuture<Void> fu=intFu1.thenCombine(intFu2,(i,j)->(i+j)).thenAccept(System.out::println);
        System.out.println("fu  get    "+fu.get());


    }

    private static Integer calc(Integer i)  {
        return i/2;
    }
}
