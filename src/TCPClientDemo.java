import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClientDemo {

    public static void main(String[] args) throws UnknownHostException, IOException {
        // TODO Auto-generated method stub
        //1.建立TCP连接
        String ip="192.168.229.1";   //服务器端ip地址
        int port = 6000;//端口号
        Socket[] sck = new Socket[3];
        for(int i = 0;i < sck.length; i++){
            sck[i] = new Socket(ip, port);
        }
        //2.传输内容
        String content="这是一个java模拟客户端\n";
        byte[] bstream=content.getBytes("GBK");  //转化为字节流
        OutputStream[] os = new OutputStream[3];
        for(int i = 0;i < sck.length; i++){
            os[i]=sck[i].getOutputStream();   //输出流
            os[i].write(bstream);
        }
        for(int i = 0; i < 10; i++){
            os[0].write(bstream);
            os[1].write(bstream);
            os[2].write(bstream);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //3.关闭连接
        sck[0].close();
        sck[1].close();
        sck[2].close();
    }

}