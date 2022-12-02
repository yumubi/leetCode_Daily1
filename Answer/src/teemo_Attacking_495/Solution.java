package teemo_Attacking_495;

import org.junit.Test;

public class Solution {
    public int findPoisonedDuration(int[] timeSeries, int duration) {
        int poisonedTime = 0;
        for(int i = 1; i < timeSeries.length; i++) {
            int end = timeSeries[i - 1] + duration;
            if(end > timeSeries[i]) poisonedTime += (timeSeries[i] - timeSeries[i - 1]);
            else poisonedTime += duration;
        }
        return poisonedTime + duration;
    }

    /**
     * 单次扫描
     * 我们只需要对数组进行一次扫描就可以计算出总的中毒持续时间。我们记录艾希恢复为未中毒的起始时间 expired，设艾希遭遇第 i 次的攻击的时间为 timeSeries[i]。
     * 当艾希遭遇第 i攻击时：
     * 如果当前他正处于未中毒状态，则此时他的中毒持续时间应增加 duration同时更新本次中毒结束时间 expired等于 timeSeries[i]+duration；
     * 如果当前他正处于中毒状态，由于中毒状态不可叠加，我们知道上次中毒后结束时间为 expired，本次中毒后结束时间为 timeSeries[i]+duration
     * 因此本次中毒增加的持续中毒时间为 timeSeries[i]+duration−expired
     * 我们将每次中毒后增加的持续中毒时间相加即为总的持续中毒时间。
     * @param timeSeries
     * @param duration
     * @return
     */
    public int findPoisonedDuration1(int[] timeSeries, int duration) {
        int ans = 0;
        int expired = 0;
        for(int i = 0; i < timeSeries.length; ++i) {
            if(timeSeries[i] >= expired) ans += duration;
            else ans += timeSeries[i] + duration - expired;
            expired = timeSeries[i] + duration;
        }
        return ans;
    }


    /**
     * 题目已确保 timeSeries 为非递减排序，按照顺序进行遍历处理即可。
     * 我们使用 ansans 统计答案，使用 last 记录上一次攻击的结束点，对于任意的 timeSeries[i] 而言，
     * 假设其发起点为 s = timeSeries[i]，结束点为 e = s + duration - 1，针对 last和 s 进行分情况讨论即可：
     * last<s ：两次攻击不重合，则有 ans += duration; last = e
     * last>=s ：两次攻击重合，则有 ans += e - last; last = e
     * 注意：lastlast 作为上次的结束点，在处理timeSeries[i] 时，last 是一个「已被统计」的存在，
     * 因此我们需要将其初始化为 -1（使用一个比 0小的数值作为哨兵），以确保当 timeSeries[0] = 0时，第 0 秒能够被计数。

     * @param timeSeries
     * @param duration
     * @return
     */
    public int findPoisonedDuration2(int[] timeSeries, int duration) {
        int ans = 0, last = -1;
        for(int s : timeSeries) {
            int e = s + duration - 1;
            ans += last < s ? duration : e - last;
            last = e;
        }
        return ans;
    }



    @Test
    public void test() {
        int[] timeSeries = {1, 2};
        int duration = 2;
        System.out.println(findPoisonedDuration(timeSeries, duration));
    }
}
