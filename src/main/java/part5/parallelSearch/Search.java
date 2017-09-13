package part5.parallelSearch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Dell on 2017-08-03.
 */
public class Search {
    static int[] arr;
    static ExecutorService pool= Executors.newCachedThreadPool();
    static final int Thread_Num=2;
    static AtomicInteger result=new AtomicInteger(-1);
    public static int search(int searchValue,int beginPos,int endPos){
        int i=0;//i指向的是下标
        for(i=beginPos;i<endPos;i++){
            if(result.get()>=0){
                return result.get();
            }
            if(arr[i]==searchValue){
                if(!result.compareAndSet(-1,i)){
                    return result.get();
                }
                return i;//设置成功的情况下
            }
        }
        return -1;
    }
    public static class SearchTask implements Callable<Integer>{
        int begin,end,searchValue;

        public SearchTask(int begin, int end, int searchValue) {
            this.begin = begin;
            this.end = end;
            this.searchValue = searchValue;
        }

        public Integer call() throws Exception {
            int re=search(searchValue,begin,end);
            return re;
        }
    }
    public static int pSearch(int searchValue) throws ExecutionException, InterruptedException {
        int subArrSize=arr.length/Thread_Num+1;
        List<Future<Integer>> re=new ArrayList<Future<Integer>>();
        for(int i=0;i<arr.length;i+=subArrSize){
            int end=i+subArrSize;
            if(end>=arr.length)
                end=arr.length-1;
            re.add(pool.submit(new SearchTask(searchValue,i,end)));
        }
        for(Future<Integer> fu:re){
            if(fu.get()>=0)
                return fu.get();
        }
        return -1;
    }

}
