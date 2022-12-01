package island_Perimeter_463;

import java.util.ArrayDeque;
import java.util.Queue;

public class Solution {
    public int islandPerimeter(int[][] grid) {
        int perimeter = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    perimeter += 4;
                    if (j - 1 >= 0 && grid[i][j - 1] == 1) perimeter -= 2;
                    if (i - 1 >= 0 && grid[i - 1][j] == 1) perimeter -= 2;
                }
            }
        }
        return perimeter;
    }

    /**
     * 迭代
     * 对于一个陆地格子的每条边，它被算作岛屿的周长当且仅当这条边为网格的边界或者相邻的另一个格子为水域。
     * 因此，我们可以遍历每个陆地格子，看其四个方向是否为边界或者水域，如果是，将这条边的贡献（即 1）加入答案ans 中即可。
     */
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};

    public int islandPerimeter1(int[][] grid) {
        int n = grid.length, m = grid[0].length;
        int ans = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                if (grid[i][j] == 1) {
                    int cnt = 0;
                    for (int k = 0; k < 4; ++k) {
                        int tx = i + dx[k];
                        int ty = j + dy[k];
                        if (tx < 0 || tx >= n || ty < 0 || ty >= m
                                || grid[tx][ty] == 0) {
                            cnt += 1;
                        }
                    }
                }
            }
        }
        return ans;
    }


    public int islandPerimeter2(int[][] grid) {
        int n = grid.length, m = grid[0].length;
        int ans = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                if (grid[i][j] == 1) {
                    ans += dfs(i, j, grid, n, m);
                }
            }
        }
        return ans;
    }


    /**
     * dfs
     * 深度优先搜索遍历的方式，此时遍历的方式可扩展至统计多个岛屿各自的周长。
     * 需要注意的是为了防止陆地格子在深度优先搜索中被重复遍历导致死循环，我们需要将遍历过的陆地格子标记为已经遍历过，
     * 下面的代码中我们设定值为 2 的格子为已经遍历过的陆地格子。
     *
     * @param x
     * @param y
     * @param grid
     * @param n
     * @param m
     * @return
     */
    public int dfs(int x, int y, int[][] grid, int n, int m) {
        if (x < 0 || x >= n || y < 0 || y >= m || grid[x][y] == 0) return 1;
        if (grid[x][y] == 2) return 0;
        grid[x][y] = 2;
        int res = 0;
        for (int i = 0; i < 4; ++i) {
            int tx = x + dx[i];
            int ty = y + dy[i];
            res += dfs(tx, ty, grid, n, m);
        }
        return res;
    }


    public int islandPerimeter3(int[][] grid) {
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] == 1)
                    // 题目限制只有一个岛屿，计算一个即可
                    return dfs3(grid, r, c);
            }
        }
        return 0;
    }

    int dfs3(int[][] grid, int r, int c) {
        // 从一个岛屿方格走向网格边界，周长加 1
        if (!(0 <= r && r < grid.length && 0 <= c && c < grid[0].length)) return 1;
        // 从一个岛屿方格走向水域方格，周长加 1
        if (grid[r][c] == 0) return 1;
        if (grid[r][c] != 1) return 0;
        grid[r][c] = 2;//将方格标记为"已遍历"
        // 继续往四个方向“扩散”，目标是遇到边界和海水，答案随着递归出栈向上返回，得出大的答案
        return dfs3(grid, r - 1, c)
                + dfs3(grid, r + 1, c)
                + dfs3(grid, r, c - 1)
                + dfs3(grid, r, c + 1);
    }



    /**
     * dfs
     * 深度优先搜索需要使用与网格相同大小的二维数组记录每个方格是否被访问过，初始时所有方格的状态都是未访问。依次遍历网格中的每个方格，
     * 如果遇到一个方格是陆地且状态是未访问，则当前方格是岛屿，访问与当前陆地连接的所有陆地，即访问岛屿的所有方格，得到岛屿的周长。
     * 对于岛屿中的每个方格，考虑与当前方格在四个方向上相邻的方格，可能有两种情况。
     *      如果相邻的方格是未访问的陆地，则需要继续访问该相邻的方格。
     *      如果相邻的方格是水域或相邻的方格不存在，则当前方向的边是岛屿的边缘，将岛屿的周长加 1。
     * 遍历结束之后，即可得到岛屿的周长。
     *
     * 实现方面有以下两点说明。
     *      对于每个陆地需要向四个方向遍历，可以创建方向数组实现四个方向的遍历。
     *      此处的解法为新建与网格相同大小的二维数组记录每个方格是否被访问过，也可以不新建二维数组，而是在网格上原地修改访问过的方格
     * 虽然原地修改可以省略新建二维数组的空间，但是不能省略队列空间，因此空间复杂度相同。
     */
    static int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    int row, col;
    int[][] grid;
    boolean[][] visited;

    public int islandPerimeter4(int[][] grid) {
        int perimeter = 0;
        this.row = grid.length;
        this.col = grid[0].length;
        this.grid = grid;
        this.visited = new boolean[row][col];
        for(int i = 0; i < row; i++) {
           for(int j = 0; j < col; j++) {
               if(grid[i][j] == 0 || visited[i][j]) continue;
               perimeter += dfs4(i, j);
           }
        }
        return perimeter;
    }


    public int dfs4(int currRow, int currCol) {
        int perimeter = 0;
        visited[currRow][currCol] = true;
        for(int[] dir : dirs) {
            int nextRow = currRow + dir[0], nextCol = currCol + dir[1];
            if(nextRow >= 0 && nextRow < row && nextCol >= 0 && nextCol < col && grid[nextRow][nextCol] == 1) {
                if(!visited[nextRow][nextCol]) {
                    perimeter += dfs4(nextRow, nextCol);
                }
            }
            else {
                perimeter++;
            }
        }
        return perimeter;
    }


    /**
     * bfs
     * 为了计算岛屿的周长，需要遍历岛屿中的每个方格，寻找岛屿的边缘。
     * 岛屿中的每个方格有四条边，对于每条边，如果这条边为网格的边界或者关于这条边的相邻方格是水域，则这条边是岛屿的边缘。岛屿的周长为岛屿的全部边缘长度之和，
     * 由于每个格子的边长都是 1，因此岛屿的周长为岛屿的边缘数量。
     * 根据上述分析，可以使用广度优先搜索计算岛屿的周长。
     * 广度优先搜索需要使用与网格相同大小的二维数组记录每个方格是否被访问过，初始时所有方格的状态都是未访问。依次遍历网格中的每个方格，
     * 如果遇到一个方格是陆地且状态是未访问，则当前方格是岛屿，访问与当前陆地连接的所有陆地，即访问岛屿的所有方格，得到岛屿的周长。
     * 对于岛屿中的每个方格，考虑与当前方格在四个方向上相邻的方格，可能有两种情况。
     *      如果相邻的方格是未访问的陆地，则需要继续访问该相邻的方格。
     *      如果相邻的方格是水域或相邻的方格不存在，则当前方向的边是岛屿的边缘，将岛屿的周长加 11。
     * 遍历结束之后，即可得到岛屿的周长。
     * 实现方面有以下两点说明。
     *      对于每个陆地需要向四个方向遍历，可以创建方向数组实现四个方向的遍历。
     *      此处的解法为新建与网格相同大小的二维数组记录每个方格是否被访问过，也可以不新建二维数组，而是在网格上原地修改访问过的方格。虽然原地修改可以省略新建二维数组的空间，
     *但是不能省略队列空间，因此空间复杂度相同。
     * @param grid
     * @return
     */
    public int islandPerimeter5(int[][] grid) {
        int perimeter = 0;
        int row = grid.length, col = grid[0].length;
        boolean[][] visited = new boolean[row][col];
        for(int i = 0; i < row; i++) {
            for(int j = 0; j < col; j++) {
                if(grid[i][j] == 0 || visited[i][j]) {
                    continue;
                }
            visited[i][j] = true;
                Queue<int[]> queue = new ArrayDeque<int[]>();
                queue.offer(new int[]{i, j});
                while(!queue.isEmpty()) {
                    int[] cell = queue.poll();
                    int currRow = cell[0], currCol = cell[1];
                    for(int[] dir : dirs) {
                        int nextRow = currRow + dir[0], nextCol = currCol + dir[1];
                        if(nextRow >= 0 && nextRow < row && nextCol >= 0 && nextCol < col && grid[nextRow][nextCol] == 1) {
                            if(!visited[nextRow][nextCol]) {
                                visited[nextRow][nextCol] = true;
                                queue.offer(new int[]{nextRow, nextCol});
                            }
                        } else {
                            perimeter++;
                        }
                    }
                }
            }
        }
        return perimeter;
    }


    /**
     * 计算岛屿的周长时，只需要考虑岛屿的边缘。岛屿中的每个方格有四条边，对于每条边，如果这条边为网格的边界或者关于这条边的相邻方格是水域，则这条边是岛屿的边缘。
     * 因此，只要遍历岛屿中的每个方格，判断方格的四条边是否为岛屿的边缘即可。
     * 对于方格 grid[i][j]，当 grid[i][j]=1 时，分别考虑其上、下、左、右的四条边，判断每条边是否为岛屿的边缘的做法如下。
     * 如果 i=0 或 grid[i−1][j]=0，则 grid[i][j] 的上侧的边是岛屿的边缘。
     * 如果 i=row−1 或 grid[i+1][j]=0，则 grid[i][j] 的下侧的边是岛屿的边缘。
     * 如果 j=0 或 grid[i][j−1]=0，则 grid[i][j] 的左侧的边是岛屿的边缘。
     * 如果 j=col−1 或 grid[i][j+1]=0，则 grid[i][j] 的右侧的边是岛屿的边缘。
     * 对于每个岛屿的边缘，将周长加 1
     * 由于上述做法只访问岛屿中的每个方格，且每个方格只访问一次，因此岛屿的每个边缘只会计算一次。
     * @param grid
     * @return
     */
    public int islandPerimeter6(int[][] grid) {
        int perimeter = 0;
        int row = grid.length, col = grid[0].length;
        for(int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j] == 0) continue;
                if (i == 0 || grid[i - 1][j] == 0) perimeter++;
                if (i == row - 1 || grid[i + 1][j] == 0) perimeter++;
                if (j == 0 || grid[i][j - 1] == 0) perimeter++;
                if (j == col - 1 || grid[i][j + 1] == 0) perimeter++;
            }
        }
        return perimeter;
    }
}
