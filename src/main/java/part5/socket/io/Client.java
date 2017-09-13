package part5.socket.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by Dell on 2017-08-04.
 */
public class Client {
    public static void main(String[] args) throws IOException {
        Socket client=new Socket();
        client.connect(new InetSocketAddress("localhost",9999));
        PrintWriter writer=new PrintWriter(client.getOutputStream(),true);
        writer.println("hello ,I am client");
        writer.flush();
        BufferedReader reader=new BufferedReader(new InputStreamReader(client.getInputStream()));
        System.out.println("msg from server :"+reader.readLine());

        if(writer!=null)writer.close();
        if(reader!=null)reader.close();
        if(client!=null)client.close();
    }
}
