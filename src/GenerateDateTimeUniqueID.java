import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 功能： 以秒为单位生成唯一的序列号 <br/>
 * 生成格式：YYMMddHHmmssXXXXXXX <br/>
 * XXXXXXX：代表序列号，从1开始
 * 例子: 1808312321280000001 或者 1808312321280001234 <br/>
 * <p>局限性： 每秒生成最大范围 (1000万-1) 个数</p>
 * Created by Administrator on 2018/8/31.
 */
public final class GenerateDateTimeUniqueID {

    //    private static final DateFormat DF = new SimpleDateFormat("yyMMddHHmmss");
    private static final ThreadLocal<DateFormat> DATE_FORMAT_THREAD_LOCAL = new ThreadLocal<DateFormat>(){
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyMMddHHmmss");
        }
    };
    private static volatile long LAST_TIME = -1;
    private static final AtomicInteger COUNT = new AtomicInteger();

    //测试是否有生成重复的ID
    public static final ConcurrentMap<String, Boolean> MAP = new ConcurrentHashMap<String, Boolean>();

    /*******
     *
     * 测试机器系统参数： Win7 64位 i5-4210M 4core 2.6GHz 内存8GB
     *
     * ********/

    /**
     * 测试10个线程并发产生，每秒可以产生500万左右个序列号
     * *
     */
    public static long generateDateTimeUniqueId() {
        Date date = new Date();
        String dateStr = DATE_FORMAT_THREAD_LOCAL.get().format(date);
//        String dateStr = DF.format(date);
        long curTime = Long.parseLong(dateStr);
        int curCount = 0;

        synchronized (GenerateDateTimeUniqueID.class) {
            if (curTime < LAST_TIME) {
                curTime = LAST_TIME;
            } else if (curTime > LAST_TIME) {
                LAST_TIME = curTime;
                System.out.println(Thread.currentThread().getName() + "-" + COUNT.get());
                COUNT.set(0);
            }
            curCount = COUNT.incrementAndGet();
        }

        return curTime * 10000000 + curCount;
    }

    private GenerateDateTimeUniqueID() {
    }

    public static void main(String[] args) {

        //测试
        int num = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(num);
        for (int i = 0; i < num; i++) {
            executorService.submit(new TestThread());
        }
    }

    static class TestThread implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    //generateDateTimeUniqueId();
                    System.out.println(generateDateTimeUniqueId());
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(0);
                }

            }
        }
    }
}