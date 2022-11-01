package Data;

public class Extensions {
    public static boolean compareCharArrays(char[] q1, char[] q2){
        int len = Math.min(q1.length, q2.length);
        for (int i = 0; i < len; i++){
            if (q1[i] == '0' || q2[i] == '0') continue;
            if (q1[i] != q2[i]) return false;
        }
        return true;
    }
}
