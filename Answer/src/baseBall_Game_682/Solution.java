package baseBall_Game_682;

import org.junit.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Solution {
    public int calPoints(String[] operations) {
        Deque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < operations.length; i++) {
            if (operations[i].equals("C")) deque.pollLast();
            else if (operations[i].equals("D")) deque.offerLast(deque.peekLast() * 2);
            else if(operations[i].equals("+")){
                int last = deque.pollLast();
                int lastSec = deque.pollLast();
                int e = last + lastSec;
                deque.offerLast(lastSec);
                deque.offerLast(last);
                deque.offerLast(e);
            }
            else deque.offerLast(Integer.parseInt(operations[i]));
        }
        int sum = 0;
        while (!deque.isEmpty()) {

            sum += deque.pollLast();
        }
        return sum;
    }

    public int calPoints01(String[] ops) {
        int ret = 0;
        List<Integer> points = new ArrayList<Integer>();
        for(String op : ops) {
            int n = points.size();
            switch (op.charAt(0)) {
                case '+' :

            }
        }

    }




    @Test
    public void test() {
        String[] ops = {"5","2","C","D","+"};
        System.out.println(calPoints(ops));
    }
}
