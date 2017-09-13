package part5.future;

/**
 * Created by Dell on 2017-08-03.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Client client=new Client();
        Data data=client.request("abbb");
        Thread.sleep(2000);
        String a=data.getResult();
        System.out.println(a);
    }
}
