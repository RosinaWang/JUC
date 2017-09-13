package part5.socket.io;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Dell on 2017-08-04.
 */
public class MultiThreadEchoServer
{
    private static ExecutorService tp= Executors.newCachedThreadPool();

    /**多线程服务器，倾向于让CPU IO等待
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Socket clientSocket=null;
        ServerSocket echoServer=null;
        echoServer=new ServerSocket(9999);
        while (true){
            clientSocket=echoServer.accept();
            System.out.println(clientSocket.getRemoteSocketAddress()+" connect!");
            if(HandleMsg.count==0){
                HandleMsg.begin=System.currentTimeMillis();
            }
            HandleMsg.count++;
            tp.execute(new HandleMsg(clientSocket));
        }
    }
    //处理客户端的线程
    static class HandleMsg implements Runnable{
        Socket clientsocket;
        static long begin;
        static long end;
        static int count=0;
        public HandleMsg(Socket clientsocket) {
            this.clientsocket = clientsocket;
        }
        public void run() {
            //处理客户端的消息
            BufferedReader is=null;
            PrintWriter os=null;
            try {
                is=new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));
                os=new PrintWriter(clientsocket.getOutputStream(),true);
                //从Is中读取数据
                String inputLine=null;
                long b=System.currentTimeMillis();
                while((inputLine=is.readLine())!=null){
                    os.println("server receive client msg"+inputLine);
                }
                long e=System.currentTimeMillis();
                System.out.println(" spend   : "+(e-b)+" ms");
                end=e;
                System.out.println("这是第几个count  "+count);
                if(count==10){
                    System.out.println("处理10个客户端进程耗费  "+(end-begin)+" ms");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    if(is!=null)is.close();
                    if(os!=null)os.close();
                    clientsocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
