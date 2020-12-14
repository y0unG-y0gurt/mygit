import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.Callable;

public class TcpTask6s implements Callable {
    private String mac;
    private int intervaltime;
    private int totaltime;

    public TcpTask6s(String mac, int intervaltime, int totaltime){
        this.mac = mac;
        this.intervaltime = intervaltime;
        this.totaltime = totaltime;
    }


    @Override
    public Object call() throws IOException {
        //1.建立TCP连接
//        final String ip="192.168.229.1";   //本机服务器端ip地址
//        final int port = 6000;//端口号
        final String ip="47.99.70.92";   //福祉服务器端ip地址
        final int port = 8822;//端口号

        Socket sck = new Socket(ip, port);
        Random random = new Random();
        //2.传输内容
        //生命雷达两种频率的数据格式：
        //2s间隔："realtimeData2/0.00/480.00/64.56/230.35/0.85/-0.13/-1.53/-0.26/1.58/-2.06/-2.22/-2.78/2.33/-1.85/1.27/0.14/-5.70/2.06/1.57/2.58/2.01/2.87/3.12/2.91/-1.10";
        //6s间隔："realtimeData1/0.00/28.82/88.26/0.00/61.32/173.32/0.00/65.30/190.74";
        String head = this.mac + "/0/radar_" + this.mac + "/bioradar/1/realtimeData1/";
        String[] radar6s = new String[3];
        OutputStream os = sck.getOutputStream();

        for(int i = 0; i < totaltime/intervaltime; i++){
            for (int j = 0; j < radar6s.length; j++){
                radar6s[j] = random.nextInt(2) + ".00" + "/" + String.format("%.2f", Math.random()*30) + "/" + String.format("%.2f", Math.random()*200) + "/";
            }
            String content = head + radar6s[0] + radar6s[1] + radar6s[2];

            content = content.substring(0,content.length()-1);  //删去最后一个斜杠"/"

            System.out.println(content);

            send(content, os);

            for(int j = 0; j < intervaltime; j++){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        //3.关闭连接
        sck.close();
        return null;
    }

    public static void intToByteArray(int n , byte[] b) {
        // 由高位到低位
        b[0] = (byte) (n & 0xff);
        b[1] = (byte) (n >> 8 & 0xff);
        b[2] = (byte) (n >> 16 & 0xff);
        b[3] = (byte) (n >> 24 & 0xff);
    }

    //发送函数，注意需要加上前四位的包头
    public static void send(String content, OutputStream os) throws IOException {
        byte[] bytes = content.getBytes("GBK");
        byte[] arr = new byte[256];

        Arrays.fill(arr, (byte)0x00);
        intToByteArray(bytes.length , arr);

        for(int j = 0 ; j < bytes.length ; j++){
            arr[j+4] = bytes[j];
        }
        os.write(arr, 0, bytes.length+4);
    }
}


