package path_SumII_113;

import java.util.*;

public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }


    class Solution {
        /**
         * [1,2] 1过不了，实际输出[[1]] 预期输出[]
         */
        List<List<Integer>> res = new ArrayList<>();

        public List<List<Integer>> pathSum(TreeNode root, int targetSum) {

            List<Integer> list = new ArrayList<>();
            dfs(root, targetSum, list);
            if(res.size() == 0 || targetSum == 0) return new ArrayList<>();
            return res;
        }


        public void dfs(TreeNode root, int targetSum, List<Integer> list) {
            if (root == null) {
                if (targetSum == 0 && !res.contains(list)) {
                    res.add(list);
                }
                return;
            }
            list.add(root.val);
            List<Integer> copyListL = new ArrayList<>(list);
            List<Integer> copyListR = new ArrayList<>(list);
            dfs(root.left, targetSum - root.val, copyListL);
            dfs(root.right, targetSum - root.val, copyListR);
        }


        /**
         * dfs
         * 枚举每一条从根节点到叶子节点的路径。当我们遍历到叶子节点，且此时路径和恰为目标和时，我们就找到了一条满足条件的路径。
         * 时间复杂度：O(N^2)，其中 N 是树的节点数。在最坏情况下，树的上半部分为链状，下半部分为完全二叉树，
         * 并且从根节点到每一个叶子节点的路径都符合题目要求。此时，路径的数目为 O(N)，并且每一条路径的节点个数也为 O(N)，
         * 因此要将这些路径全部添加进答案中，时间复杂度为 O(N^2)
         * 空间复杂度：O(N)，其中 N 是树的节点数。空间复杂度主要取决于栈空间的开销，栈中的元素个数不会超过树的节点数。
         */
        List<List<Integer>> ret = new LinkedList<List<Integer>>();
        Deque<Integer> path = new LinkedList<Integer>();

        public List<List<Integer>> pathSum1(TreeNode root, int targetSum) {
            dfs1(root, targetSum);
            return ret;
        }

        public void dfs1(TreeNode root, int targetSum) {
            if(root == null) return;
            path.offerLast(root.val);
            targetSum -= root.val;
            if(root.left == null && root.right == null && targetSum == 0) ret.add(new LinkedList<>(path));
            dfs1(root.left, targetSum);
            dfs1(root.right, targetSum);
            path.pollLast();//精髓
        }


        /**
         * bfs
         * 遍历这棵树。当我们遍历到叶子节点，且此时路径和恰为目标和时，我们就找到了一条满足条件的路径。
         * 为了节省空间，我们使用哈希表记录树中的每一个节点的父节点。每次找到一个满足条件的节点，
         * 我们就从该节点出发不断向父节点迭代，即可还原出从根节点到当前节点的路径。
         *
         * 时间复杂度：O(N^2)，其中 N 是树的节点数。
         * 空间复杂度：O(N)，其中 N 是树的节点数。空间复杂度主要取决于哈希表和队列空间的开销，
         * 哈希表需要存储除根节点外的每个节点的父节点，队列中的元素个数不会超过树的节点数。
         */
        List<List<Integer>> ret2 = new LinkedList<List<Integer>>();
        Map<TreeNode, TreeNode> map = new HashMap<TreeNode, TreeNode>();
        public List<List<Integer>> pathSum2(TreeNode root, int targetSum) {
            if(root == null) return ret2;
            Queue<TreeNode> queueNode = new LinkedList<TreeNode>();
            Queue<Integer> queueSum = new LinkedList<Integer>();
            queueNode.offer(root);
            queueSum.offer(0);
            while(!queueNode.isEmpty()) {
                TreeNode node = queueNode.poll();
                int rec = queueSum.poll() + node.val;

                if(node.left == null && node.right == null) {
                    if(rec == targetSum) getPath(node);
                } else {
                    if(node.left != null) {
                        map.put(node.left, node);
                        queueNode.offer(node.left);
                        queueSum.offer(rec);
                    }
                    if(node.right != null) {
                        map.put(node.right, node);
                        queueNode.offer(node.right);
                        queueSum.offer(rec);
                    }
                }
            }
            return ret2;
        }


        public void getPath(TreeNode node) {
            List<Integer> tmp = new LinkedList<Integer>();
            while(node != null) {
                tmp.add(node.val);
                node = map.get(node);
            }
            Collections.reverse(tmp);
            ret.add(new LinkedList<Integer>(tmp));
        }


        /**
         * 传统递归
         * 这种不停的创建list，效率很差
         * @param root
         * @param sum
         * @return
         */
        public List<List<Integer>> pathSum3(TreeNode root, int sum) {
                List<List<Integer>> result = new ArrayList<>();
                dfs3(root, sum, new ArrayList<>(), result);
                return result;
        }

        public void dfs3(TreeNode root, int sum, List<Integer> list, List<List<Integer>> result) {
            //节点为空直接返回
            if(root == null) return;
//因为list是引用传递，为了防止递归的时候分支污染，要在每个路径中都要新建一个subList
            List<Integer> subList = new ArrayList<>(list);
            //当前值加入到subList中
            subList.add(root.val);
            //到达叶子节点必须return了
            if(root.left == null && root.right == null) {
                //如果找到了一组，
                if(sum == root.val) result.add(subList);
                return;
            }

            //没到达叶子节点，继续从他的左右两个子节点往下找
            dfs3(root.left, sum - root.val, subList, res);
            dfs3(root.right, sum - root.val, subList, res);


        }


        /**
         * 回溯，往下减
         * 递归的时候提到过为了防止分支污染我们还可以把使用过的值在返回的时候把它给remove掉，这就是大家常提的回溯算法
         * @param root
         * @param sum
         * @return
         */
  public List<List<Integer>> pathSum4(TreeNode root, int sum) {
                List<List<Integer>> result = new ArrayList<>();
                dfs3(root, sum, new ArrayList<>(), result);
                return result;
        }

        public void dfs4(TreeNode root, int sum, List<Integer> list, List<List<Integer>> result) {
            //节点为空直接返回
            if(root == null) return;
            //当前值加入到List中
            list.add(root.val);
            //到达叶子节点必须return了
            if(root.left == null && root.right == null) {
                //如果找到了一组，
                if(sum == root.val) result.add(list);
                //别忘了把最后加入的节点值给移除掉，因为下一步直接return了，不会再走最后一行的remove了
                //所以这里在return之前提前把最后一个节点的值remove
                list.remove(list.size() - 1);
                return;
            }

            //没到达叶子节点，继续从他的左右两个子节点往下找
            dfs3(root.left, sum - root.val, list, res);
            dfs3(root.right, sum - root.val, list, res);


            //我们要理解递归的本质，当递归往下传递的时候他最后还是会往回走，
            //我们把这个值使用完之后还要把它给移除，这就是回溯
            list.remove(list.size() - 1);//精髓

        }


        /**
         * 回溯，往下累加
         * @param root
         * @param sum
         * @return
         */
        public List<List<Integer>> pathSum5(TreeNode root, int sum) {
            List<List<Integer>> result = new ArrayList<>();
            dfs5(root, sum, 0, new ArrayList<>(), result);
            return result;
        }

        public void dfs5(TreeNode root, int sum, int toal, List<Integer> list,
                        List<List<Integer>> result) {
            //如果节点为空直接返回
            if (root == null)
                return;
            //把当前节点值加入到list中
            list.add(root.val);
            //没往下走一步就要计算走过的路径和
            toal += root.val;
            //如果到达叶子节点，就不能往下走了，直接return
            if (root.left == null && root.right == null) {
                //如果到达叶子节点，并且sum等于toal，说明我们找到了一组，
                //要把它放到result中
                if (sum == toal)
                    result.add(new ArrayList(list));
                //注意别忘了把最后加入的结点值给移除掉，因为下一步直接return了，
                //不会再走最后一行的remove了，所以这里在rerurn之前提前把最后
                //一个结点的值给remove掉。
                list.remove(list.size() - 1);
                //到叶子节点之后直接返回，因为在往下就走不动了
                return;
            }
            //如果没到达叶子节点，就继续从他的左右两个子节点往下找
            dfs5(root.left, sum, toal, list, result);
            dfs5(root.right, sum, toal, list, result);
            //我们要理解递归的本质，当递归往下传递的时候他最后还是会往回走，
            //我们把这个值使用完之后还要把它给移除，这就是回溯
            list.remove(list.size() - 1);
        }


        /**
         *bfs
         * @param root
         * @param sum
         * @return
         */
        public List<List<Integer>> pathSum6(TreeNode root, int sum) {
            List<List<Integer>> res = new ArrayList<>();
            if(root == null) return res;
            Queue<TreeNode> queueNode = new LinkedList<>();
            Queue<List<Integer>> queueList = new LinkedList<>();
            queueNode.add(root);
            List<Integer> list = new ArrayList<>();
            list.add(root.val);
            queueList.add(list);

            while(!queueNode.isEmpty()) {
                TreeNode node = queueNode.poll();
                List<Integer> tempList = queueList.poll();
                if(node.left == null && node.right == null && node.val == sum)
                    res.add(tempList);
                //左子节点不为空，左子节点和路径入队
                if(node.left != null) {
                    tempList.add(node.left.val);
                    queueList.add(new ArrayList<>(tempList));
                    node.left.val += node.val;
                    queueNode.add(node.left);
                    tempList.remove(tempList.size() - 1);
                }
                //右子节点不为空，右子节点和路径入队
                if(node.right != null) {
                    tempList.add(node.right.val);
                    queueList.add(new ArrayList<>(tempList));
                    node.right.val += node.val;
                    queueNode.add(node.right);
                }
            }
            return res;
        }


        /**
         *dfs非递归
         * @param root
         * @param sum
         * @return
         */
        public List<List<Integer>> pathSum7(TreeNode root, int sum) {
            List<List<Integer>> res = new ArrayList<>();
            if(root == null) return res;
            Stack<TreeNode> stackNode = new Stack<>();
            Stack<List<Integer>> stackList = new Stack<>();
            stackNode.add(root);

            List<Integer> list = new ArrayList<>();
            list.add(root.val);
            stackList.add(list);
            while(!stackNode.isEmpty()) {
                TreeNode node = stackNode.pop();
                List<Integer> tempList = stackList.pop();
                if(node.left == null && node.right == null && node.val == sum) res.add(tempList);
                if(node.right != null) {
                    tempList.add(node.right.val);
                    stackList.add(new ArrayList<>(tempList));
                    node.right.val += node.val;
                    stackNode.push(node.right);
                    tempList.remove(tempList.size() - 1);
                }

                if(node.left != null) {
                    tempList.add(node.left.val);
                    stackList.add(new ArrayList<>(tempList));
                    node.left.val += node.val;
                    stackNode.push(node.left);
                    tempList.remove(tempList.size() - 1);
                }

            }
            return res;
        }


        /**
         * 回溯模板
         * 在对左右结点递归调用之前，先判断结点是否为空，左右结点非空才继续调用；
         * 对比参考代码 1 和参考代码 2：由于有可能一个结点只有左结点或者只有右结点，因此递归调用完成以后，一定要 path.removeLast();。
         *
         * 回溯算法全程只使用一个变量去搜索所有可能的情况，在符合条件的时候才做复制和保存。如果 path 变量每次都复制，就不需要重置。
         * @param root
         * @param sum
         * @return
         */
        public List<List<Integer>> pathSum8(TreeNode root, int sum) {
            List<List<Integer>> res = new ArrayList<>();
            if(root == null) return res;
            Deque<Integer> path = new ArrayDeque<>();
            dfs8(root, sum, path, res);
            return res;
        }
        public void dfs8(TreeNode node, int sum, Deque<Integer> path, List<List<Integer>> res) {
            if(node == null) return;
            sum -= node.val;
            path.addLast(node.val);

            if(node.left == null && node.right == null && sum == 0) {
                res.add(new ArrayList<>(path));
                return;
            }

            //在递归调用之前如果先判断了非空，在递归完成之后，需要重置path
            if(node.left != null) {
                dfs8(node.left, sum, path, res);
                path.removeLast();
            }
            if(node.right != null) {
                dfs8(node.right, sum, path, res);
                path.removeLast();
            }







        }
    }
}