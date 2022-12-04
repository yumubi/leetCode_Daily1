package base7_504;

public class Solution {
    public String convertToBase7(int num) {
        if(num == 0) return "0";
        StringBuilder sb = new StringBuilder();
        boolean isNegative = num > 0 ? false : true;
        int val = Math.abs(num);
        while(val > 0) {
            sb.append(val % 7);
            val /= 7;
        }
        if(isNegative) sb.append("-");
        return sb.reverse().toString();
        //return Integer.toString(num,7);
    }

    /**
     * 时间复杂度：O(log∣num∣)，
     * 空间复杂度：O(log∣num∣)。部分语言可以直接修改字符串，空间复杂度为 O(1)。
     * @param num
     * @return
     */
    public String convertToBase7_1(int num) {
        if(num == 0) return "0";
        boolean negative = num < 0;
        num = Math.abs(num);
        StringBuffer digits = new StringBuffer();
        while(num > 0) {
            digits.append(num % 7);
            num /= 7;
        }
        if(negative) digits.append('-');
        return digits.reverse().toString();
    }


    public String convertToBase7_2(int n) {
        boolean flag = n < 0;
        if(flag) n = -n;
        StringBuilder sb = new StringBuilder();
        do {
            sb.append(n % 7);
            n /= 7;
        } while (n != 0);
        sb.reverse();
        return flag ? "-" + sb.toString() : sb.toString();
    }


//    public static String toString(int i, int radix) {
//        // 如果需要转化的进制 < 2 || 需要转化为进制 > 36 则默认转换为十进制数
//        if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX)
//            radix = 10;
//
//        // 十进制就直接进行字符输出
//        /* Use the faster version */
//        if (radix == 10) {
//            return toString(i);
//        }
//
//        char buf[] = new char[33];
//        boolean negative = (i < 0);
//        int charPos = 32;
//
//        // 这里是先将正数转化为负数 在进行进制转换的
//        if (!negative) {
//            i = -i;
//        }
//        // 这里相当于是短除法 数学知识学过的哦
//        while (i <= -radix) {
//            buf[charPos--] = digits[-(i % radix)];
//            i = i / radix;
//        }
//        buf[charPos] = digits[-i];
//        // 如果是负数需要添上 符号
//        if (negative) {
//            buf[--charPos] = '-';
//        }
//
//        return new String(buf, charPos, (33 - charPos));
//    }
//











}
