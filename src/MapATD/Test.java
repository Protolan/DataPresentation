package MapATD;

import MapATD.ListATD.Map;

public class Test {
    public static void main(String[] args) {
        Map map = new Map();
        map.assign("Mark".toCharArray(), "Darm".toCharArray());
        map.assign("Park".toCharArray(), "Warm".toCharArray());
        map.assign("Del".toCharArray(), "Phol".toCharArray());
        map.print();
    }
}
