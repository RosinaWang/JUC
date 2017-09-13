package part5;

/**
 * Created by Dell on 2017-08-02.
 */
public class StaticSingleton {
    private StaticSingleton(){
        System.out.println("        create");
    }
    private static class SingletonHolder{
        private static StaticSingleton instance=new StaticSingleton();
    }
    public static StaticSingleton getStaticSingleton(){
        return SingletonHolder.instance;
    }
}
