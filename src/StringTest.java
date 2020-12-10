public class StringTest {
    //String str = new String("hello");
    String str = "hello";
    public void change(String str){
        str = "world";
    }

    public static void main(String[] args) {
        StringTest ex = new StringTest();
        ex.change(ex.str);
        System.out.println(ex.str);
    }
}
