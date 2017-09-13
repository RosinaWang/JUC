package part5.socket.nio;

import part5.socket.io.HeavySocketClient;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;

/**
 * Created by Dell on 2017-08-04.
 */
public class HandleMsg implements Runnable{
    SelectionKey sk;
    ByteBuffer bb;

    public HandleMsg(SelectionKey sk, ByteBuffer bb) {
        this.sk = sk;
        this.bb = bb;
    }

    public void run() {
        HeavySocketClient.EchoClient echoClient=(HeavySocketClient.EchoClient) sk.attachment();
        echoClient.enqueue(bb);
        sk.interestOps(SelectionKey.OP_READ|SelectionKey.OP_WRITE);
        //强迫selector立即返回
//        selector.wakeup();
    }
}
