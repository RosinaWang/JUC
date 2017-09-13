package part5.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Dell on 2017-08-04.
 */
public class EchoClient {
    private Selector selector;
    private LinkedList<ByteBuffer> outputQueue;

    public void init(String ip,int port) throws IOException {
        SocketChannel channel=SocketChannel.open();
        channel.configureBlocking(false);
        this.selector= SelectorProvider.provider().openSelector();
        channel.connect(new InetSocketAddress(ip,port));
        channel.register(selector, SelectionKey.OP_CONNECT);
    }
    public void working() throws IOException {
        while (true){
            if(!selector.isOpen())
                break;
            selector.select();
            Iterator<SelectionKey> its=this.selector.selectedKeys().iterator();
            while (its.hasNext()){
                SelectionKey key=its.next();
                its.remove();
                //连接事件发生
                if(key.isConnectable()){
                    connect(key);
                }else if(key.isReadable()){
                    read(key);
                }
            }

        }
    }

    private void connect(SelectionKey key) throws IOException {
        SocketChannel channel=(SocketChannel) key.channel();
        if(channel.isConnectionPending()){
            channel.finishConnect();
        }
        channel.configureBlocking(false);
        channel.write(ByteBuffer.wrap(new String("hello server!\r\n").getBytes()));
        channel.register(this.selector,SelectionKey.OP_READ);
    }
    private void read(SelectionKey key) throws IOException {
        SocketChannel channel=(SocketChannel) key.channel();
        ByteBuffer buffer=ByteBuffer.allocate(100);
        channel.read(buffer);
        byte[] data=buffer.array();
        String msg=new String(data).trim();
        System.out.println("客户端收到消息： "+msg);
        channel.close();
        key.selector().close();
    }

    public LinkedList<ByteBuffer> getOutputQueue() {
        return outputQueue;
    }
}
