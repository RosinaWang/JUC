package part5.socket.aio;

/**
 * Created by Dell on 2017-08-16.
 */
public class TimeServer {
    public static void main(String[] args){
        int port=8080;
        AsyncTimeServerHandler timeServer=new AsyncTimeServerHandler(port);
        new Thread(timeServer,"AIO-server").start();
    }
}
