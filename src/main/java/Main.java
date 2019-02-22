import Server.ReflectionServer;

public class Main {
    public static void main(String[] args) {
        try {
            new ReflectionServer().startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
