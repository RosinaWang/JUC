package part5.socket.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by Dell on 2017-08-04.
 */
public class HeavySocketClient {
    private static ExecutorService tp= Executors.newCachedThreadPool();
    private static final int sleep_time=1000*1000*1000;

    public static void main(String[] args) throws IOException {
        EchoClient ec=new EchoClient();
        for(int i=0;i<10;i++){
            tp.execute(ec);
        }
    }
    //在这个案例中，每个服务端线程都要消耗6秒去等待客户端输入完成，浪费资源
    public static class EchoClient implements Runnable{
        public void run() {
            try {
                Socket client=new Socket();
                client.connect(new InetSocketAddress("localhost",9999));
                PrintWriter writer=new PrintWriter(client.getOutputStream(),true);
                writer.print("H");
                LockSupport.parkNanos(sleep_time);
                writer.print("e");
                LockSupport.parkNanos(sleep_time);
                writer.print("l");
                LockSupport.parkNanos(sleep_time);
                writer.print("l");
                LockSupport.parkNanos(sleep_time);
                writer.print("o");
                LockSupport.parkNanos(sleep_time);
                writer.print("!");
                LockSupport.parkNanos(sleep_time);
                writer.println();
                writer.flush();

                BufferedReader reader=new BufferedReader(new InputStreamReader(client.getInputStream()));
                System.out.println("from server:  "+reader.readLine());

                if(writer!=null)writer.close();
                if(reader!=null)reader.close();
                if(client!=null)client.close();
            } catch (IOException e) {e.printStackTrace();}
        }

        public void enqueue(ByteBuffer bb) {
        }
    }
}
