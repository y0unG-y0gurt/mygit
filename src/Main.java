import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] p = {1, 2, 3, 4, 5};
        swap(p,1,3);
        System.out.println(Arrays.stream(p).max());
    }

    private static void swap(int[] p, int i, int i1) {
//        int tmp = p[i];
//        p[i] = p[i1];
//        p[i1] = tmp;
        p[i] = p[i] ^ p[i1];
        p[i1] = p[i] ^ p[i1];
        p[i] = p[i] ^ p[i1];
    }
}
