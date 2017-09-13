package part2;

/**
 * Created by Dell on 2017-07-31.
 */
public class NoVisibility {
    private static boolean ready;
    private static int number;

    private static class ReaderThread extends Thread{
        public void run(){
            while (!ready);
            System.out.println(number);
        }
    }
    public static void main(String[] args) throws InterruptedException {
        new ReaderThread().start();
        Thread.sleep(1000);
        number=42;
        ready=true;
        Thread.sleep(10000);
    }

}
