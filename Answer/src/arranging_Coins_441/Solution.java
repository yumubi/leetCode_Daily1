package arranging_Coins_441;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    /**
     * 内存超出
     */
    static List<Integer> table = new ArrayList<>();
    static  {
        int i = 0;
        int reslut = 0;
        while(reslut < Integer.MAX_VALUE) {
            reslut = i * ( i + 1) / 2;
            table.add(reslut);
        }
    }

    public int arrangingCoins(int n) {
            for(int i = 0; i < table.size(); i++) {
                if(n >= table.get(i)) return i + 1;
            }
            return 0;
    }
}
