import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TcpClients {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(4);
//        service.submit(new TcpTask6s("c44f3355cfe1",6,6000));
//        service.submit(new TcpTask6s("c44f33551b1d",6,6000));
//        service.submit(new TcpTask6s("4c11ae741508",6,6000));
//        service.submit(new TcpTask6s("4c11ae744db0",6,6000));
        service.submit(new TcpTask2s("c44f3355cfe1",2,6000));
        service.shutdown();
    }
}
