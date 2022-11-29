package assign_Cookies_455;

import java.util.Arrays;

public class Solution {
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int cnt = 0;
        int idxG = 0, idxS = 0;
        while(idxG < g.length && idxS < s.length) {
            if(s[idxS] >= g[idxG]) {
                cnt++;
                idxG++;
            }
            idxS++;
        }
        return cnt;
    }


    //贪心算法（greedy algorithm）就是这样的算法，它在每一步都做出当时看起来最佳的选择。
    //也就是说，它总是做出局部最优的选择，寄希望这样的选择能导致全局最优解。

    //所以这道题我们可以让胃口大的吃大块，胃口小的吃小块。
        public int findContentChildren1(int[] g, int[] s) {
            //排序数组，使用java内置库，每一个的时间复杂度都是O(nlongn)
            Arrays.sort(g);
            Arrays.sort(s);
            //对每一个孩子进行分析，记录总的满足孩子的数量
            int ans=0;//记录满足孩子的总数
            for(int i=0,j=0; i<g.length&&j<s.length ;++i,++j){
                //g[i]是当前需要满足的孩子的胃口
                //s[j]是当前饼干的大小
                while(j<s.length&&s[j]<g[i]){
                    //如果当前饼干的大小小于孩子的胃口，那么就换一块更大的饼干，如此一直更新饼干的大小，但是需要注意的是饼干不能越界
                    ++j;
                }
                //出循环意味着满足了当前这个孩子，但是还有一种可能是所有饼干都不能满足当前这个孩子的胃口
                if(j<s.length){
                    ans++;
                }
                //如果饼干真的越界，那么for语句会自动break
            }
            return ans;
        }






}
