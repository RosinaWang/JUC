package part5.socket.nio;


import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Dell on 2017-08-04.
 */
public class NIOEchoService {
    private Selector selector;//一个selector会处理多个客户端的事件
    private ExecutorService tp= Executors.newCachedThreadPool();
    public static Map<Socket,Long> time_stat=new HashMap<Socket, Long>(10240);

    private void startServer() throws IOException {
        selector= SelectorProvider.provider().openSelector();//打开一个selector
        ServerSocketChannel ssc=ServerSocketChannel.open();//打开一个服务端的socket通道
        ssc.configureBlocking(false);//非阻塞模式

        InetSocketAddress isa=new InetSocketAddress(InetAddress.getLocalHost(),6666);
        ssc.socket().bind(isa);//给该通道绑定一个地址
        //SelectionKey表示一对Selector和channel的关系
        SelectionKey acceptKey=ssc.register(selector,SelectionKey.OP_ACCEPT);//注册一个对连接感兴趣的selector

        for(;;){//等待-分发网络消息
            selector.select();//阻塞方法，没有数据准备好，会等待，有数据时，会返回
            Set readyKeys=selector.selectedKeys();//selector同时为多个Channel服务
            Iterator i=readyKeys.iterator();
            long e=0;
            while (i.hasNext()){//依次处理所有的channel数据
                SelectionKey sk=(SelectionKey) i.next();
                i.remove();//防止处理多个相同的SelectionKey
                if(sk.isAcceptable()){
                    doAccept(sk);
                }else if(sk.isValid()&&sk.isReadable()){
                    if(!time_stat.containsKey(((SocketChannel)sk.channel()).socket())){
                        time_stat.put(((SocketChannel)sk.channel()).socket(),System.currentTimeMillis());
                    }
                    doRead(sk);
                }else if(sk.isValid()&&sk.isWritable()){
                    doWrite(sk);
                    e=System.currentTimeMillis();
                    long b=time_stat.remove(((SocketChannel)sk.channel()).socket());
                    System.out.println("spend: "+(e-b)+" ms");
                }
            }
        }
    }

    private void doAccept(SelectionKey sk) throws IOException {//客户端的接收
        ServerSocketChannel server=(ServerSocketChannel) sk.channel();
        SocketChannel clientChannel=server.accept();
        clientChannel.configureBlocking(false);

        //注册read到该通道，为啥客户端注册
        SelectionKey clientKey=clientChannel.register(selector,SelectionKey.OP_READ);
        EchoClient echoClient=new EchoClient();
        clientKey.attach(echoClient);

        InetAddress clientAddress=clientChannel.socket().getInetAddress();
        System.out.println("Accepted connection from"+clientAddress.getHostAddress()+" .");



    }
    private void doRead(SelectionKey sk) throws IOException {//读取
        SocketChannel channel=(SocketChannel) sk.channel();
        ByteBuffer bb=ByteBuffer.allocate(8192);
        int len=channel.read(bb);
        if(len<0){
            disconnect(sk);
            return;
        }
        bb.flip();
        tp.execute(new HandleMsg(sk,bb));
    }



    private void doWrite(SelectionKey sk) throws IOException {
        SocketChannel channel=(SocketChannel)sk.channel();
        EchoClient echoClient=(EchoClient)sk.attachment();
        LinkedList<ByteBuffer> outq=echoClient.getOutputQueue();
        ByteBuffer bb=outq.getLast();
        int len=channel.write(bb);
        if(len==-1){
            disconnect(sk);
            return;
        }

        if(bb.remaining()==0){
            outq.removeLast();
        }
        if(outq.size()==0){
            sk.interestOps(SelectionKey.OP_READ);
        }
    }

    private void disconnect(SelectionKey sk) {
    }

}
