package can_Place_Flowers_605;

public class Solution {
    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        if(n == 0) return true;
        int nextIdx = 0;
        int held = n;
        if(flowerbed.length == 1) {
            if(n <= 1 && flowerbed[0] == 0) return true;
            else return false;
        }
        while (nextIdx < flowerbed.length - 1){
            if(flowerbed[nextIdx] == 0  && (flowerbed[nextIdx + 1] != 1)) {
                held--;
            }
            else if(flowerbed[nextIdx + 1] == 1) nextIdx++;
            nextIdx += 2;
        }
        if(nextIdx == flowerbed.length - 1 && flowerbed[nextIdx] == 0 && flowerbed[nextIdx - 1] == 0) held--;
        return held <= 0;
    }

    /**
     * 贪心算法
     * 判断能否在不打破种植规则的情况下在花坛内种入 n 朵花，从贪心的角度考虑，应该在不打破种植规则的情况下种入尽可能多的花，然后判断可以种入的花的最多数量是否大于或等于 n。
     *
     * 假设花坛的下标 i 和下标 j 处都种植了花，其中 j−i≥2，且在下标 [i+1,j−1] 范围内没有种植花，则只有当j−i≥4 时才可以在下标 i 和下标 j 之间种植更多的花，
     * 且可以种植花的下标范围是 [i+2,j−2]。可以种植花的位置数是 p=j−i−3，当 p 是奇数时最多可以在该范围内种植 (p+1)/2 朵花，
     * 当 p 是偶数时最多可以在该范围内种植 p/2 朵花。由于当 p 是偶数时，在整数除法的规则下 p/2 和 (p+1)/2 相等，因此无论 p 是奇数还是偶数，
     * 都是最多可以在该范围内种植 (p+1)/2 朵花，即最多可以在该范围内种植 (j−i−2)/2 朵花。
     * 上述情况是在已有的两朵花之间种植花的情况（已有的两朵花之间没有别的花）。假设花坛的下标 l 处是最左边的已经种植的花，
     * 下标 r 处是最右边的已经种植的花（即对于任意 k<l 或 k>r 都有 flowerbed[k]=0），如何计算在下标 l 左边最多可以种植多少朵花以及在下标 r 右边最多可以种植多少朵花？
     * 下标 l 左边有 l 个位置，当l<2 时无法在下标 l 左边种植花，当 l≥2 时可以在下标范围 [0,l−2] 范围内种植花，可以种植花的位置数是 l−1，最多可以种植 l/2 朵花。
     * 令 m 为数组 flowerbed 的长度，下标 r 右边有m−r−1 个位置，可以种植花的位置数是 m−r−2，最多可以种植 (m−r−1)/2 朵花。
     * 如果花坛上没有任何花朵，则有 m 个位置可以种植花，最多可以种植 (m+1)/2 朵花。
     * 根据上述计算方法，计算花坛中可以种入的花的最多数量，判断是否大于或等于 n 即可。具体做法如下。
     * 维护prev 表示上一朵已经种植的花的下标位置，初始时 prev=−1，表示尚未遇到任何已经种植的花。
     * 从左往右遍历数组 \flowerbed，当遇到 flowerbed[i]=1 时根据 prev 和 i 的值计算上一个区间内可以种植花的最多数量，然后令 prev=i，继续遍历数组 flowerbed 剩下的元素。
     * 遍历数组 flowerbed 结束后，根据数组 prev 和长度 m 的值计算最后一个区间内可以种植花的最多数量。
     * 判断整个花坛内可以种入的花的最多数量是否大于或等于 n。

     * @param n
     * @return
     */
    public boolean canPlaceFlowers01(int[] flowerbed, int n) {
        int count = 0;
        int m = flowerbed.length;
        int prev = -1;
        for(int i = 0; i < m; i++) {
            if(flowerbed[i] == 1) {
                if(prev < 0) count += i / 2;
                else count += (i - prev - 2) / 2;
                prev = i;
            }
        }
        if(prev < 0) count += (m + 1) / 2;
        else count += (m - prev - 1) / 2;
        return count >= n;
    }

    //由于只需要判断能否在不打破种植规则的情况下在花坛内种入 n 朵花，不需要具体知道最多可以在花坛内种入多少朵花，
    // 因此可以在循环内进行优化，当可以种入的花的数量已经达到 n，则可以直接返回 true，不需要继续计算数组剩下的部分。

    //时间复杂度：O(m)，其中 m 是数组 flowerbed 的长度。需要遍历数组一次。
    //空间复杂度：O(1)。额外使用的空间为常数。
    public boolean canPlaceFlowers02(int[] flowerbed, int n) {
        int count = 0;
        int m = flowerbed.length;
        int prev = -1;
        for(int i = 0; i < m; i++) {
            if(flowerbed[i] == 1) {
                if(prev < 0) count += i / 2;
                else count += (i - prev - 2) / 2;
                if(count >= n) return true;
                prev = i;
            }
        }
        if(prev < 0) count += (m + 1) / 2;
        else count += (m - prev - 1) / 2;
        return count >= n;
    }


    /**
     * 跳格子解法
     * 采用“跳格子”的解法只需遍历不到一遍数组，处理以下两种不同的情况即可：
     * 【1】当遍历到index遇到1时，说明这个位置有花，那必然从index+2的位置才有可能种花，因此当碰到1时直接跳过下一格。
     * 【2】当遍历到index遇到0时，由于每次碰到1都是跳两格，因此前一格必定是0，此时只需要判断下一格是不是1即可得出index这一格能不能种花，
     * 如果能种则令n减一，然后这个位置就按照遇到1时处理，即跳两格；如果index的后一格是1，说明这个位置不能种花且之后两格也不可能种花（参照【1】），直接跳过3格。
     * 当n减为0时，说明可以种入n朵花，则可以直接退出遍历返回true；如果遍历结束n没有减到0，说明最多种入的花的数量小于n，则返回false。
     * @param flowered
     * @param n
     * @return
     */
    public boolean canPlaceFlowers03(int[] flowered, int n) {
        for(int i = 0, len = flowered.length; i < len && n > 0; ) {
            if(flowered[i] == 1) {
                i += 2;
            } else if(i == flowered.length - 1 || flowered[i + 1] == 0) {
                n--;
            } else {
                i += 3;
            }
        }
        return n <= 0;
    }


    public boolean canPlaceFlowers04(int[] flowered, int n) {
        int num = 0, count = 1;//假设再数组左边添加0，以解决边界问题，令count初始为1
        for(int i = 0; i < flowered.length; i++) {
            if(flowered[i] == 0) {
                count++;
            } else {
                count = 0;
            }
            if(count == 3) {//每连续3个0种一次花
                num++;
                count = 1;
            }
        }
        if(count == 2) {//如果最后count为2而不是1，表示最后一个位置可以种花
            num++;
        }
        return n <= num;
    }


}
