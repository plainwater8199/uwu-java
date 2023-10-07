```text
1、给定一个二叉树 root ，返回其最大深度。

二叉树的 最大深度 是指从根节点到最远叶子节点的最长路径上的节点数。



```

```java
class Solution {//深度优先遍历
    public int maxDepth(TreeNode root) {
        if(root == null){
            return 0;
        }else{
            int leftHeight = maxDepth(root.left);
            int rightHeight = maxDepth(root.right);
            return Math.max(leftHeight,rightHeight)+1;
        }

    }
}

class Solution {//广度优先遍历
    public int maxDepth(TreeNode root) {
        if(root == null){
            return 0;
        }

        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);

        int ans = 0;
        while(!queue.isEmpty()){
            int size = queue.size();
            while(size > 0){
                TreeNode node = queue.poll();
                if(node.left != null){
                    queue.offer(node.left);
                }
                if(node.right != null){
                    queue.offer(node.right);
                }
                size--;
            }
            ans++;
        }
        return ans;

    }
}

```

```text
2、给你两棵二叉树的根节点 p 和 q ，编写一个函数来检验这两棵树是否相同。

如果两个树在结构上相同，并且节点具有相同的值，则认为它们是相同的。



```

```java
class Solution { //深度优先遍历
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if(p == null && q == null ){
            return true;
        }else if( p == null || q == null){
            return false;
        }else if(p.val != q.val){
            return false;
        }else{
            return isSameTree(p.left,q.left) && isSameTree(p.right,q.right);
        }
    }
}

class Solution { //广度优先遍历
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if(p == null && q == null ){
            return true;
        }else if( p == null || q == null){
            return false;
        }

        Queue<TreeNode> queue1 = new LinkedList<>();
        Queue<TreeNode> queue2 = new LinkedList<>();
        queue1.offer(p);
        queue2.offer(q);

        while(!queue1.isEmpty() && !queue2.isEmpty()){
            TreeNode node1 = queue1.poll();
            TreeNode node2 = queue2.poll();
            if(node1.val != node2.val){
                return false;
            }

            TreeNode left1 = node1.left;
            TreeNode right1 = node1.right;
            TreeNode left2 = node2.left;
            TreeNode right2 = node2.right;

            if(left1 == null ^ left2 == null){
                return false;
            }
            if(right1 == null ^ right2 == null){
                return false;
            }

            if(left1 != null){
                queue1.offer(left1);
            }
            if(right1 != null){
                queue1.offer(right1);
            }
            if(left2 != null){
                queue2.offer(left2);
            }
            if(right2 != null){
                queue2.offer(right2);
            }
        }
        return queue1.isEmpty() && queue2.isEmpty();
    }
}

```

```text
3、给你一棵二叉树的根节点 root ，翻转这棵二叉树，并返回其根节点。



```

```java
class Solution {
    public TreeNode invertTree(TreeNode root) {
        if(root == null){
            return null;
        }
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);
        root.left = right;
        root.right = left;
        return root;

    }
}

```

```text
4、给你一个二叉树的根节点 root ， 检查它是否轴对称。



```

```java
class Solution {
    public boolean isSymmetric(TreeNode root) {
        return check(root,root);
    }

    public boolean check(TreeNode p,TreeNode q){
        if(p == null && q == null){
            return true;
        }
        if(p== null || q == null){
            return false;
        }

        return p.val == q.val && check(p.right,q.left) && check(p.left,q.right);
    }
}

class Solution {
    public boolean isSymmetric(TreeNode root) {
        return check(root,root);
    }

    public boolean check(TreeNode p,TreeNode q){
        Queue<TreeNode>  queue  = new LinkedList<>();
        queue.offer(p);
        queue.offer(q);
        while(!queue.isEmpty()){
            p = queue.poll();
            q = queue.poll();
            if(p == null && q == null){
                continue;
            }
            if((p == null || p == null) || (p.val != q.val)){
                return false;
            }

            queue.offer(p.left);
            queue.offer(q.right);

            queue.offer(p.right);
            queue.offer(q.left);
        }
        return true;
    }
}

```

```text
5、给定两个整数数组 preorder 和 inorder ，其中 preorder 是二叉树的先序遍历， inorder 是同一棵树的中序遍历，请构造二叉树并返回其根节点。
示例 1:


输入: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
输出: [3,9,20,null,null,15,7]




```

```java
class Solution {//递归
    private Map<Integer,Integer> indexMap;
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        int n = preorder.length;
        //构造哈希映射，帮助我们快速定位根节点
        indexMap = new HashMap<>();
        for(int i = 0; i <n;i++){
            indexMap.put(inorder[i],i);
        }
        return myBuildTree(preorder,inorder,0,n-1,0,n-1);
    }

    public TreeNode myBuildTree(int[] preorder, int[] inorder,int preL,int preR,int inL,int inR){
        if(preL> preR){
            return null;
        }
        //前序遍历的第一个节点就是根节点
        int preRoot = preL;
        //在中序遍历中定位到根节点
        int inRoot = indexMap.get(preorder[preL]);
        //建立根节点
        TreeNode root = new TreeNode(preorder[preRoot]);
        //得到左子树中的节点数目
        int sizeLefeSubtree = inRoot-inL;
        //递归的构造左子树，并连接到根结点
        //先序遍历中从左边界+1开始的sizeLeftSubtree个元素对应了中序遍历中从左边界开始到根节点定位-1的元素
        root.left = myBuildTree(preorder,inorder,preL+1,preL+sizeLefeSubtree,inL,inRoot-1);
        //递归地构造右子树，并连接到根节点
        //先序遍历中从左边界+1+左子树节点数量开始到右边界的元素对应了中序遍历中从根节点+1到右边界的元素
        root.right = myBuildTree(preorder,inorder,preL+sizeLefeSubtree+1,preR,inRoot+1,inR);
        return root;
    }
}

```

```text
6、给定两个整数数组 inorder 和 postorder ，其中 inorder 是二叉树的中序遍历， postorder 是同一棵树的后序遍历，请你构造并返回这颗 二叉树 。



```

```java
class Solution {
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        if(postorder == null || postorder.length == 0){
            return null;
        }
        TreeNode root = new TreeNode(postorder[postorder.length-1]);
        Deque<TreeNode> deque= new LinkedList<>();//定义一个栈
        deque.push(root);//添加根元素
        int inorderIndex = inorder.length-1;
        for(int i = postorder.length-2;i>=0;i--){
            int postorderVal = postorder[i];
            TreeNode node = deque.peek();//peek()函数返回栈顶的元素，但不弹出该栈顶元素。
            if(node.val != inorder[inorderIndex]){
                node.right = new TreeNode(postorderVal);
                deque.push(node.right);
            }else{
                while(!deque.isEmpty() && deque.peek().val == inorder[inorderIndex]){
                    node = deque.pop();//pop()函数返回栈顶的元素，并且将该栈顶元素出栈。
                    inorderIndex--;
                }
                node.left = new TreeNode(postorderVal);
                deque.push(node.left);
            }
        }
        return root;
    }
}

```

```text
7、给定一个二叉树：

struct Node {
  int val;
  Node *left;
  Node *right;
  Node *next;
}
填充它的每个 next 指针，让这个指针指向其下一个右侧节点。如果找不到下一个右侧节点，则将 next 指针设置为 NULL 。

初始状态下，所有 next 指针都被设置为 NULL 。



```

```java
class Solution {
    public Node connect(Node root) {
        if(root == null){
            return root;
        }
        Queue<Node> queue = new LinkedList<>();//定义一个队列
        queue.add(root);
        while(!queue.isEmpty()){
            int levelCount  = queue.size();//每一层的数量
            Node pre = null;//前一个节点
            for(int i = 0;i<levelCount;i++){
                Node node = queue.poll();//出队
                if(pre != null){//如果pre为null表示该节点为这一层的第一个节点，如果不为null，则让前一个节点指向它
                    pre.next = node;
                }
                pre = node;
                if(node.left != null){
                    queue.add(node.left);
                }
                if(node.right != null){
                    queue.add(node.right);
                }
            }
        }
        return root;
    }
}

```

```text
8、给你二叉树的根结点 root ，请你将它展开为一个单链表：

展开后的单链表应该同样使用 TreeNode ，其中 right 子指针指向链表中下一个结点，而左子指针始终为 null 。
展开后的单链表应该与二叉树 先序遍历 顺序相同。



```

```java
class Solution {//前序遍历
    public void flatten(TreeNode root) {
        List<TreeNode> list = new ArrayList<TreeNode>();
        preOrderTraversal(root,list);
        int size = list.size();
        for(int i = 1;i<size;i++){
            TreeNode prev = list.get(i-1);
            TreeNode curr = list.get(i);
            prev.left = null;
            prev.right = curr;
        }
    }

    public void preOrderTraversal(TreeNode root,List<TreeNode> list){
        if(root != null){
            list.add(root);
            preOrderTraversal(root.left,list);
            preOrderTraversal(root.right,list);
        }
    }
}

class Solution {//寻找前驱节点
    public void flatten(TreeNode root) {
        TreeNode curr = root;
        while(curr != null){
            if(curr.left != null){
                TreeNode next = curr.left;
                TreeNode predecessor = next;
                while(predecessor.right != null){
                    predecessor = predecessor.right;
                }
                predecessor.right = curr.right;
                curr.left = null;
                curr.right = next;
            }
            curr = curr.right;
        }
    }
}

```

```text
9、给你二叉树的根节点 root 和一个表示目标和的整数 targetSum 。判断该树中是否存在 根节点到叶子节点 的路径，这条路径上所有节点值相加等于目标和 targetSum 。如果存在，返回 true ；否则，返回 false 。

叶子节点 是指没有子节点的节点。



```

```java
class Solution {//深度优先遍历
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if(root == null){
            return false;
        }
        if(root.left == null && root.right == null){
            return targetSum == root.val;
        }
        return hasPathSum(root.left,targetSum-root.val) || hasPathSum(root.right,targetSum-root.val);
    }
}

```

```text
10、给你一个二叉树的根节点 root ，树中每个节点都存放有一个 0 到 9 之间的数字。
每条从根节点到叶节点的路径都代表一个数字：

例如，从根节点到叶节点的路径 1 -> 2 -> 3 表示数字 123 。
计算从根节点到叶节点生成的 所有数字之和 。

叶节点 是指没有子节点的节点。

 

示例 1：


输入：root = [1,2,3]
输出：25
解释：
从根到叶子节点路径 1->2 代表数字 12
从根到叶子节点路径 1->3 代表数字 13
因此，数字总和 = 12 + 13 = 25



```

```java
class Solution {//深度优先搜索
    public int sumNumbers(TreeNode root) {
        return dfs(root,0);
    }

    public int dfs(TreeNode root,int prevSum){
        if(root == null){
            return 0;
        }
        int sum = prevSum*10+root.val;
        if(root.left==null && root.right == null){
            return sum;
        }else{
            return dfs(root.left,sum)+dfs(root.right,sum);
        }
    }
}

```

```text
11、二叉树中的 路径 被定义为一条节点序列，序列中每对相邻节点之间都存在一条边。同一个节点在一条路径序列中 至多出现一次 。该路径 至少包含一个 节点，且不一定经过根节点。

路径和 是路径中各节点值的总和。

给你一个二叉树的根节点 root ，返回其 最大路径和 。

 

示例 1：


输入：root = [1,2,3]
输出：6
解释：最优路径是 2 -> 1 -> 3 ，路径和为 2 + 1 + 3 = 6



```

```java
class Solution {//???????
    int maxSum = Integer.MIN_VALUE;
    public int maxPathSum(TreeNode root) {
        maxGain(root);
        return maxSum;
    }
    public int maxGain(TreeNode node){
        if(node == null){
            return 0;
        }

        //递归计算左右子节点的最大贡献值
        //只有子啊最大贡献值大于0时，才会选取对应子节点
        int leftGain = Math.max(maxGain(node.left),0);
        int rightGain = Math.max(maxGain(node.right),0);
        //节点的最大路径和取决于该节点的值与该节点的左右子节点的最大贡献值
        int priceNewPath = node.val + leftGain + rightGain;
        //更新答案
        maxSum = Math.max(maxSum,priceNewPath);

        return node.val + Math.max(leftGain,rightGain);
    }
}

```

```text
12、实现一个二叉搜索树迭代器类BSTIterator ，表示一个按中序遍历二叉搜索树（BST）的迭代器：
BSTIterator(TreeNode root) 初始化 BSTIterator 类的一个对象。BST 的根节点 root 会作为构造函数的一部分给出。指针应初始化为一个不存在于 BST 中的数字，且该数字小于 BST 中的任何元素。
boolean hasNext() 如果向指针右侧遍历存在数字，则返回 true ；否则返回 false 。
int next()将指针向右移动，然后返回指针处的数字。
注意，指针初始化为一个不存在于 BST 中的数字，所以对 next() 的首次调用将返回 BST 中的最小元素。

你可以假设 next() 调用总是有效的，也就是说，当调用 next() 时，BST 的中序遍历中至少存在一个下一个数字



```

```java
class BSTIterator {
    private int idx;
    private List<Integer> arr;
    public BSTIterator(TreeNode root) {
        idx = 0;
        arr = new ArrayList<Integer>();
        inOrderTraversal(root,arr);
    }

    private void inOrderTraversal(TreeNode root,List<Integer> arr){
        if(root == null){
            return;
        }
        inOrderTraversal(root.left,arr);
        arr.add(root.val);
        inOrderTraversal(root.right,arr);
    }
    
    public int next() {
        return arr.get(idx++);
    }
    
    public boolean hasNext() { 
        return idx <arr.size();

    }
}


```

```text
13、给你一棵 完全二叉树 的根节点 root ，求出该树的节点个数。

完全二叉树 的定义如下：在完全二叉树中，除了最底层节点可能没填满外，其余每层节点数都达到最大值，并且最下面一层的节点都集中在该层最左边的若干位置。若最底层为第 h 层，则该层包含 1~ 2h 个节点。



```

```java
class Solution {
    public int countNodes(TreeNode root) {
        if(root == null){
            return 0;
        }
        int level = 0;
        TreeNode node = root;
        while(node.left != null){
            level++;
            node = node.left;
        }

        int low = 1<<level, high = (1<<(level+1))-1;//1<<level,2的level次方
        while(low<high){
            int mid = (high-low+1)/2+low;
            if(exists(root,level,mid)){
                low = mid;
            }else{
                high = mid-1;
            }
        }
        return low;
    }

    public boolean exists(TreeNode root,int level, int k){
        int bits = 1<<(level-1);
        TreeNode node = root;
        while(node != null && bits >0){
            if((bits & k) == 0){
                node = node.left;
            }else{
                node = node.right;
            }
            bits >>= 1;
        }
        return node != null;
    }
}

```

```text
14、
给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。

百度百科中最近公共祖先的定义为：“对于有根树 T 的两个节点 p、q，最近公共祖先表示为一个节点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。”

 

示例 1：


输入：root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
输出：3
解释：节点 5 和节点 1 的最近公共祖先是节点 3 。


```

```java
class Solution {
    private TreeNode ans;
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        dfs(root,p,q);
        return this.ans;
    }

    private boolean dfs(TreeNode root,TreeNode p, TreeNode q){
        if(root == null){
            return false;
        }
        boolean lson  = dfs(root.left,p,q);
        boolean rson = dfs(root.right,p ,q);
        if((lson && rson) || ((root.val == p.val || root.val == q.val) && (lson || rson))){
            ans = root;
        }
        return lson || rson || (root.val == p.val || root.val == q.val);

    }
}

```

```text
15、给定一个二叉树的 根节点 root，想象自己站在它的右侧，按照从顶部到底部的顺序，返回从右侧所能看到的节点值。

 



```

```java
class Solution {
    public List<Integer> rightSideView(TreeNode root) {
        Map<Integer,Integer> rightMostValueAtDepth = new HashMap<Integer,Integer>();
        int maxDepth = -1;

        Queue<TreeNode> nodeQueue = new LinkedList<TreeNode>();
        Queue<Integer> depthQueue = new LinkedList<Integer>();
        nodeQueue.add(root);
        depthQueue.add(0);

        while(!nodeQueue.isEmpty()){
            TreeNode node = nodeQueue.remove();
            int depth = depthQueue.remove();
            if(node != null){
                maxDepth = Math.max(maxDepth,depth);//维护二叉树的最大深度

                rightMostValueAtDepth.put(depth,node.val);//由于每一层最后一个访问的节点才是我们要的答案，因此不断更新对应深度的信息即可

                nodeQueue.add(node.left);
                nodeQueue.add(node.right);
                depthQueue.add(depth+1);
                depthQueue.add(depth+1);
            }
        }

        List<Integer> rightView = new ArrayList<Integer>();
        for(int depth = 0;depth <= maxDepth;depth ++){
            rightView.add(rightMostValueAtDepth.get(depth));
        }
        return rightView;
    }
}

```

```text
16、给定一个非空二叉树的根节点 root , 以数组的形式返回每一层节点的平均值。与实际答案相差 10-5 以内的答案可以被接受。

 



```

```java
class Solution {
    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> averages = new ArrayList<Double>();
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        while(!queue.isEmpty()){
            double sum = 0;
            int size = queue.size();
            for(int i = 0; i<size;i++){
                TreeNode node = queue.poll();
                sum+= node.val;
                TreeNode left = node.left,right = node.right;
                if(left != null){
                    queue.offer(left);
                }
                if(right != null){
                    queue.offer(right);
                }
            }
            averages.add(sum/size);
        }
        return averages;
    }
}

```

```text
17、给你二叉树的根节点 root ，返回其节点值的 层序遍历 。 （即逐层地，从左到右访问所有节点）。





```

```java
class Solution {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> ret = new ArrayList<List<Integer>>();
        if(root == null){
            return ret;
        }

        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        while(!queue.isEmpty()){
            List<Integer> level = new ArrayList<Integer>();
            int currentLevelSize = queue.size();
            for(int i = 1;i<=currentLevelSize;++i){
                TreeNode node = queue.poll();
                level.add(node.val);
                if(node.left != null){
                    queue.offer(node.left);
                }
                if(node.right != null){
                    queue.offer(node.right);
                }
            }
            ret.add(level);
        }
        return ret;
    }
}

```

```text
18、给你二叉树的根节点 root ，返回其节点值的 锯齿形层序遍历 。（即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行）。





```

```java
class Solution {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> ans = new LinkedList<List<Integer>>();
        if(root == null){
            return ans;
        }

        Queue<TreeNode> nodeQueue = new ArrayDeque<TreeNode>();
        nodeQueue.offer(root);
        boolean isOrderLeft = true;
        while(!nodeQueue.isEmpty()){
            Deque<Integer> levelList = new LinkedList<Integer>();
            int size = nodeQueue.size();
            for(int i = 0;i<size;i++){
                TreeNode curNode = nodeQueue.poll();
                if(isOrderLeft){
                    levelList.offerLast(curNode.val);
                }else{
                    levelList.offerFirst(curNode.val);
                }
                if(curNode.left != null){
                    nodeQueue.offer(curNode.left);
                }
                if(curNode.right != null){
                    nodeQueue.offer(curNode.right);
                }
            }
            ans.add(new LinkedList<Integer>(levelList));
            isOrderLeft = !isOrderLeft;
        }
        return ans;
    }
}

```

```text
19、给你一个二叉搜索树的根节点 root ，返回 树中任意两不同节点值之间的最小差值 。

差值是一个正数，其数值等于两值之差的绝对值。



```

```java
class Solution {
    int pre;
    int ans;
    public int getMinimumDifference(TreeNode root) {
        ans = Integer.MAX_VALUE;
        pre = -1;
        dfs(root);
        return ans;
    }

    public void dfs(TreeNode root){
        if(root == null){
            return;
        }
        dfs(root.left);
        if(pre == -1){
            pre = root.val;
        }else{
            ans = Math.min(ans,root.val-pre);
            pre = root.val;
        }
        dfs(root.right);
    }
}

```

```text
20、给定一个二叉搜索树的根节点 root ，和一个整数 k ，请你设计一个算法查找其中第 k 个最小元素（从 1 开始计数）。



```

```java
class Solution {
    public int kthSmallest(TreeNode root, int k) {
        Deque<TreeNode> stack = new ArrayDeque<TreeNode>();
        while(root != null || !stack.isEmpty()){
            while(root != null){
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            --k;
            if(k ==0){
                break;
            }
            root = root.right;
        }
        return root.val;
    }
}

```

```text
21、给你一个二叉树的根节点 root ，判断其是否是一个有效的二叉搜索树。

有效 二叉搜索树定义如下：

节点的左子树只包含 小于 当前节点的数。
节点的右子树只包含 大于 当前节点的数。
所有左子树和右子树自身必须也是二叉搜索树。




```

```java
class Solution {
    public boolean isValidBST(TreeNode root) {
        Deque<TreeNode> stack = new LinkedList<TreeNode>();
        double inOrder = - Double.MAX_VALUE;

        while(!stack.isEmpty() || root != null){
            while(root != null){
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            if(root.val <= inOrder){
                return false;
            }
            inOrder = root.val;
            root = root.right;
        }
        return true;
    }
}

```

```text
22、


```

```java


```

```text
23、



```

```java


```

```text
24、



```

```java


```

```text
25、



```

```java


```

```text
26、



```

```java


```

```text
27、



```

```java


```

```text
28、



```

```java


```

```text
29、



```

```java


```

```text
30、



```

```java


```

```text
31、



```

```java


```

```text
32、



```

```java


```

```text
33、



```

```java


```

```text
34、



```

```java


```

```text
35、



```

```java


```

```text
36、



```

```java


```