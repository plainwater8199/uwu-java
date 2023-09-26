
```text
1、给你两个按 非递减顺序 排列的整数数组 nums1 和 nums2，另有两个整数 m 和 n ，分别表示 nums1 和 nums2 中的元素数目。
请你 合并 nums2 到 nums1 中，使合并后的数组同样按 非递减顺序 排列。
注意：最终，合并后数组不应由函数返回，而是存储在数组 nums1 中。为了应对这种情况，nums1 的初始长度为 m + n，其中前 m 个元素表示应合并的元素，后 n 个元素为 0 ，应忽略。nums2 的长度为 n 。
```

```java
class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int p1 = 0;
        int p2= 0;
        int[] sorted = new int[m+n];
        int cur;
        while(p1<m || p2 < n){
            if(p1 == m){
                cur = nums2[p2++];
            }else if(p2==n){
                cur = nums1[p1++];
            }else if(nums1[p1] < nums2[p2]){
                cur = nums1[p1++];
            }else{
                cur = nums2[p2++];
            }
            sorted[p1+p2-1] = cur;
        }
        for(int i =0;i != m+n;++i){
            nums1[i] = sorted[i];
        }
    }
}
```

```text
2、给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素，并返回移除后数组的新长度。

不要使用额外的数组空间，你必须仅使用 O(1) 额外空间并 原地 修改输入数组。

元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。
```

```java
class Solution {
    public int removeElement(int[] nums, int val) {
        int n = nums.length;
        int left = 0;
        for(int right = 0;right <n;right++){
            if(nums[right] != val){
                nums[left] = nums[right];
                left++;
            }
        }
        return left;
    
    }
}
```


```text
3、给你一个 升序排列 的数组 nums ，请你 原地 删除重复出现的元素，使每个元素 只出现一次 ，返回删除后数组的新长度。元素的 相对顺序 应该保持 一致 。然后返回 nums 中唯一元素的个数。

考虑 nums 的唯一元素的数量为 k ，你需要做以下事情确保你的题解可以被通过：

更改数组 nums ，使 nums 的前 k 个元素包含唯一元素，并按照它们最初在 nums 中出现的顺序排列。nums 的其余元素与 nums 的大小不重要。
返回 k 。
```

```java
class Solution {
    public int removeDuplicates(int[] nums) {
        int n = nums.length;
        if(n==0){
            return 0;
        }
        int fast = 1;
        int slow = 1;
        while(fast < n){
            if(nums[fast] != nums[fast-1]){
                nums[slow] = nums[fast];
                slow++;//先调整位置再赋值
            }
            fast++;
        }
        return slow;
    }
}
```

```text
3、给你一个 升序排列 的数组 nums ，请你 原地 删除重复出现的元素，使每个元素 只出现一次 ，返回删除后数组的新长度。元素的 相对顺序 应该保持 一致 。然后返回 nums 中唯一元素的个数。

考虑 nums 的唯一元素的数量为 k ，你需要做以下事情确保你的题解可以被通过：

更改数组 nums ，使 nums 的前 k 个元素包含唯一元素，并按照它们最初在 nums 中出现的顺序排列。nums 的其余元素与 nums 的大小不重要。
返回 k 。
```

```java
class Solution {
    public int removeDuplicates(int[] nums) {
        int n = nums.length;
        if(n==0){
            return 0;
        }
        int fast = 1;
        int slow = 1;
        while(fast < n){
            if(nums[fast] != nums[fast-1]){
                nums[slow] = nums[fast];
                slow++;//先调整位置再赋值
            }
            fast++;
        }
        return slow;
    }
}
```

```text
4、给你一个有序数组 nums ，请你 原地 删除重复出现的元素，使得出现次数超过两次的元素只出现两次 ，返回删除后数组的新长度。

不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
```

```java
class Solution {
    public int removeDuplicates(int[] nums) {
        int n = nums.length;
        if(n <= 2){
            return n;
        }
        int fast = 2;
        int slow = 2;
        while(fast <n){
            if(nums[slow-2] != nums[fast] ){
                nums[slow] = nums[fast];
                slow++;
            }
            fast++;
        }
        return slow;
    }
}
```


```text
5、给定一个大小为 n 的数组 nums ，返回其中的多数元素。多数元素是指在数组中出现次数 大于 ⌊ n/2 ⌋ 的元素。

你可以假设数组是非空的，并且给定的数组总是存在多数元素。
```

```java
class Solution {
    public int majorityElement(int[] nums) {
        int count = 0;
        Integer candidate = null;

        for(int num : nums){
            if(count == 0){
                candidate = num;
            }

            count += (num == candidate) ? 1 : -1;
        }
        return candidate;
    }
}
```


```text
6、给定一个整数数组 nums，将数组中的元素向右轮转 k 个位置，其中 k 是非负数。
```

```java
class Solution {
    public void rotate(int[] nums, int k) {
        int n = nums.length;
        int[] newArr = new int[n];
        for(int i = 0;i<n;++i){
            newArr[(i+k)%n] = nums[i];
        }
        System.arraycopy(newArr,0,nums,0,n);
    }
}

class Solution {
    public void rotate(int[] nums, int k) {
        int n = nums.length;
        k = k%n;
        int count = gcd(k,n);
        for(int start = 0;start < count ;++start){
            int current =start;
            int prev = nums[start];
            do{
                int next = (current + k) % n;
                int temp = nums[next];
                nums[next] = prev;
                prev = temp;
                current = next;
            }while(start != current);
        }
    }
    public int gcd(int x,int y){
        return y>0 ? gcd( y, x%y ) : x;
    }
}


class Solution {
    public void rotate(int[] nums, int k) {
        k %= nums.length;
        reverse(nums,0,nums.length-1);
        reverse(nums,0,k-1);
        reverse(nums,k,nums.length -1);
    }
    public void reverse(int[] nums,int start,int end){
        while(start <end){
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start += 1;
            end -= 1;
        }
    }
}

```


```text
7、给定一个数组 prices ，它的第 i 个元素 prices[i] 表示一支给定股票第 i 天的价格。

你只能选择 某一天 买入这只股票，并选择在 未来的某一个不同的日子 卖出该股票。设计一个算法来计算你所能获取的最大利润。

返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0 。
```

```java
class Solution {
    public int maxProfit(int[] prices) {
        int minpreice = Integer.MAX_VALUE;
        int maxProfit = 0;

        for(int i = 0; i<prices.length;i++){
            if(prices[i] <minpreice){//找出最小值
                minpreice = prices[i];
            }else if(prices[i] -minpreice > maxProfit){//找出最大利润
                maxProfit = prices[i] -minpreice;
            }
        }
        return maxProfit;
    }
}
```


```text
8、给你一个整数数组 prices ，其中 prices[i] 表示某支股票第 i 天的价格。

在每一天，你可以决定是否购买和/或出售股票。你在任何时候 最多 只能持有 一股 股票。你也可以先购买，然后在 同一天 出售。

返回 你能获得的 最大 利润 。
```

```java
class Solution {//动态规划
    public int maxProfit(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n][2];
        dp[0][0] = 0;
        dp[0][1] = -prices[0];

        for(int i = 1; i<n;++i){
            //dp[i][0]:表示第i天交易完后手里没有股票的最大利润。
            dp[i][0] = Math.max(dp[i-1][0],dp[i-1][1]+prices[i]);
            //dp[i][1]:表示第i天交易完后手里持有股票的最大利润。
            dp[i][1] = Math.max(dp[i-1][1],dp[i-1][0]-prices[i]);
        }
        return dp[n-1][0];
    }
}

class Solution {//动态规划
    public int maxProfit(int[] prices) {
        int n = prices.length;
        int dp0 = 0;
        int dp1 = -prices[0];
        for(int i = 1;i <n;++i){
            //只需记录前一天的状态，与其它状态无关
            int newDp0 = Math.max(dp0,dp1+prices[i]);
            int newDp1 = Math.max(dp1,dp0-prices[i]);
            dp0 = newDp0;
            dp1 = newDp1;
        }
        return dp0;
    }
}

class Solution {//贪心
    public int maxProfit(int[] prices) {
        int ans = 0;
        int n = prices.length;
        for(int i = 1;i<n;++i){
            ans += Math.max(0,prices[i]-prices[i-1]);
        }
        return ans;
    }
}
```


```text
9、给你一个非负整数数组 nums ，你最初位于数组的 第一个下标 。数组中的每个元素代表你在该位置可以跳跃的最大长度。

判断你是否能够到达最后一个下标，如果可以，返回 true ；否则，返回 false 。
```

```java
class Solution {//贪心
    public boolean canJump(int[] nums) {
        int n = nums.length;
        int rightmost = 0;
        for(int i = 0; i<n; ++i){
            if(i<= rightmost){//如果跳跃的最大位置都小于数组的当前位置，则不会到达。
                rightmost = Math.max(rightmost,i+nums[i]);
                if(rightmost >= n-1){
                    return true;
                }
            }
        }
        return false;
    }
}
```


```text
10、给定一个长度为 n 的 0 索引整数数组 nums。初始位置为 nums[0]。

每个元素 nums[i] 表示从索引 i 向前跳转的最大长度。换句话说，如果你在 nums[i] 处，你可以跳转到任意 nums[i + j] 处:

0 <= j <= nums[i] 
i + j < n
返回到达 nums[n - 1] 的最小跳跃次数。生成的测试用例可以到达 nums[n - 1]。
```

```java
class Solution {
    public int jump(int[] nums) {
        int length = nums.length;
        int end = 0;
        int maxPosition= 0;
        int steps = 0;
        for(int i = 0;i<length-1;i++){
            maxPosition = Math.max(maxPosition,i+nums[i]);//该位子能达到的最远位子
            if(i == end){//当结点跳到达这一步的最远位子时才进行下一步，
                end = maxPosition;
                steps++;
            }
        }
        return steps;
    }
}
```


```text
11、给你一个整数数组 citations ，其中 citations[i] 表示研究者的第 i 篇论文被引用的次数。计算并返回该研究者的 h 指数。

根据维基百科上 h 指数的定义：h 代表“高引用次数” ，一名科研人员的 h 指数 是指他（她）至少发表了 h 篇论文，并且每篇论文 至少 被引用 h 次。如果 h 有多种可能的值，h 指数 是其中最大的那个。
示例 1：

输入：citations = [3,0,6,1,5]
输出：3 
解释：给定数组表示研究者总共有 5 篇论文，每篇论文相应的被引用了 3, 0, 6, 1, 5 次。
     由于研究者有 3 篇论文每篇 至少 被引用了 3 次，其余两篇论文每篇被引用 不多于 3 次，所以她的 h 指数是 3。
     
```

```java
class Solution {
    public int hIndex(int[] citations) {
        Arrays.sort(citations);
        int h = 0;
        int i = citations.length-1;
        while(i>=0 && citations[i] >h){
            h++;
            i--;
        }
        return h;
    }
}

class Solution {//二分搜索
    public int hIndex(int[] citations) {
        int left = 0;
        int right = citations.length;
        int mid = 0;
        int cnt = 0;
        while(left<right){
            mid = (left+right+1)>>1;//+1防止死循环
            cnt = 0;
            for(int i=0;i<citations.length;i++){
                if(citations[i] >= mid){
                    cnt++;
                }
            }
            if(cnt>=mid){//要找的答案再[miud,right]区间中
                left=mid;
            }else{//要找的答案再[0,mid]区间中
                right = mid-1;
            }
        }
        return left;
    }
}


```

```text
12、实现RandomizedSet 类：

RandomizedSet() 初始化 RandomizedSet 对象
bool insert(int val) 当元素 val 不存在时，向集合中插入该项，并返回 true ；否则，返回 false 。
bool remove(int val) 当元素 val 存在时，从集合中移除该项，并返回 true ；否则，返回 false 。
int getRandom() 随机返回现有集合中的一项（测试用例保证调用此方法时集合中至少存在一个元素）。每个元素应该有 相同的概率 被返回。
你必须实现类的所有函数，并满足每个函数的 平均 时间复杂度为 O(1) 。

 
```

```java
class RandomizedSet {

    List<Integer> nums;
    Map<Integer,Integer> indices;
    Random random;

    public RandomizedSet() {
        nums = new ArrayList<>();
        indices = new HashMap<>();
        random = new Random();

    }

    public boolean insert(int val) {
        if(indices.containsKey(val)){
            return false;
        }
        int index = nums.size();
        nums.add(val);
        indices.put(val,index);
        return true;
    }

    public boolean remove(int val) {
        if(!indices.containsKey(val)){
            return false;
        }
        int index = indices.get(val);
        int last = nums.get(nums.size()-1);
        nums.set(index,last);//将数组最后一个值放在删除值的位置
        indices.put(last,index);//更新hash表中的位置
        nums.remove(nums.size()-1);
        indices.remove(val);
        return true;
    }

    public int getRandom() {
        int randomIndex = random.nextInt(nums.size());
        return nums.get(randomIndex);

    }
}



```

```text
13、给你一个整数数组 nums，返回 数组 answer ，其中 answer[i] 等于 nums 中除 nums[i] 之外其余各元素的乘积 。

题目数据 保证 数组 nums之中任意元素的全部前缀元素和后缀的乘积都在  32 位 整数范围内。

请不要使用除法，且在 O(n) 时间复杂度内完成此题。

 

 
```

```java
class Solution {
    public int[] productExceptSelf(int[] nums) {
        int length = nums.length;
        int[] answer = new int[length];

        answer[0] = 1;//表示i左侧所有元素的乘积
        for(int i = 1;i<length;i++){
            answer[i] = nums[i-1]*answer[i-1];
        }

        int R = 1;//刚开始右边没有元素，所以乘1
        for(int i = length-1;i>=0;i--){
            answer[i] = answer[i] * R;
            R *=nums[i];//i-1位置右边元素的乘积
        }
        return answer;
    }
}


```


```text
14、在一条环路上有 n 个加油站，其中第 i 个加油站有汽油 gas[i] 升。

你有一辆油箱容量无限的的汽车，从第 i 个加油站开往第 i+1 个加油站需要消耗汽油 cost[i] 升。你从其中的一个加油站出发，开始时油箱为空。

给定两个整数数组 gas 和 cost ，如果你可以按顺序绕环路行驶一周，则返回出发时加油站的编号，否则返回 -1 。如果存在解，则 保证 它是 唯一 的。

 

 

 
```

```java
class Solution {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int n = gas.length;
        int i= 0;
        while(i<n){//从每个加油站开始便利需要多少油
            int sumOfGas = 0;
            int sumOfCost = 0;
            int cnt = 0;
            while(cnt <n){
                int j = (i+cnt) % n;//循环
                sumOfGas += gas[j];
                sumOfCost += cost[j];
                if(sumOfCost > sumOfGas){
                    break;
                }
                cnt++;
            }
            if(cnt == n){//？？？？？？？？？？？？？？
                return i;
            }else{//
                i = i +cnt +1;
            }
        }
        return -1;
    }
}

```


```text
15、n 个孩子站成一排。给你一个整数数组 ratings 表示每个孩子的评分。

你需要按照以下要求，给这些孩子分发糖果：

每个孩子至少分配到 1 个糖果。
相邻两个孩子评分更高的孩子会获得更多的糖果。
请你给每个孩子分发糖果，计算并返回需要准备的 最少糖果数目 。

 

 
```

```java
class Solution {
    public int candy(int[] ratings) {
        int n =ratings.length;
        int ret = 1;//一共需要多少糖果
        int inc = 1;//最近的递增序列长度
        int dec = 0;//当前递减序列长度
        int pre =1;//前一个同学分的糖果数量
        for(int i =1;i<n;i++){
            if(ratings[i] >= ratings[i-1]){//是递增的
                dec = 0;
                pre = ratings[i] == ratings[i-1] ? 1 : pre +1;//如果等于就赋值为1，如果大于就加1
                ret += pre;//统计
                inc = pre;//最近递增序列长度就是pre
            }else{//递减
                dec++;
                if(dec==inc){//当递减长度等于最近的递增长度时，最后位子加1
                    dec++;
                }
                ret+=dec;
                pre = 1;//递减的最后一个为1
            }
        }
        return ret;
    }
}
```


```text
16、给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
 
```

```java
class Solution {
    public int trap(int[] height) {
        int ans = 0;//接水量
        int left = 0;//左指针
        int right = height.length-1;//右指针
        int leftMax = 0;
        int rightMax = 0;

        while(left <right){
            leftMax = Math.max(leftMax,height[left]);
            rightMax = Math.max(rightMax,height[right]);
            if(height[left] < height[right]){//当左边小于右边是，左指针移动
                ans += leftMax - height[left];
                ++left;
            }else{
                ans += rightMax - height[right];
                --right;
            }
        }
        return ans;
    }
}
```


```text
17、罗马数字包含以下七种字符: I， V， X， L，C，D 和 M。

字符          数值
I             1
V             5
X             10
L             50
C             100
D             500
M             1000
例如， 罗马数字 2 写做 II ，即为两个并列的 1 。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。

通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：

I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。 
C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
给定一个罗马数字，将其转换成整数。

 
 
```

```java
class Solution {

    Map<Character,Integer> symbolValues = new HashMap<Character,Integer>(){{
        put('I',1);
        put('V',5);
        put('X',10);
        put('L',50);
        put('C',100);
        put('D',500);
        put('M',1000);

    }} ;
    public int romanToInt(String s) {
        int ans = 0;
        int n = s.length();
        for(int i = 0;i<n;++i){
            int value = symbolValues.get(s.charAt(i));
            //int[] values = {1000,900,500,400,100,90,50,40,10,9,5,4,1};
            //String[] symbols = {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};
            if(i< n-1 && value < symbolValues.get(s.charAt(i+1))){//？？？？？
                ans -= value;
            }else{
                ans += value;
            }
        }
        return ans;
    }
}
```

```text
18、罗马数字包含以下七种字符: I， V， X， L，C，D 和 M。

字符          数值
I             1
V             5
X             10
L             50
C             100
D             500
M             1000
例如， 罗马数字 2 写做 II ，即为两个并列的 1 。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。

通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：

I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。 
C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
给你一个整数，将其转为罗马数字。

 
 
```

```java
class Solution {
    int[] values = {1000,900,500,400,100,90,50,40,10,9,5,4,1};
    String[] symbols = {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};
    public String intToRoman(int num) {
        StringBuffer roman = new StringBuffer();
        for(int i =0; i< values.length;++i){//循环数字的数组
            int value = values[i];
            String symbol = symbols[i];
            while(num >= value){//如果数组比它大
                num -=value;
                roman.append(symbol);
            }
            if(num == 0){//num为0退出
                break;
            }
        }
        return roman.toString();
    }
}
```


```text
19、给你一个字符串 s，由若干单词组成，单词前后用一些空格字符隔开。返回字符串中 最后一个 单词的长度。

单词 是指仅由字母组成、不包含任何空格字符的最大子字符串。
 
```

```java
class Solution {
    public int lengthOfLastWord(String s) {
        int index = s.length() -1;
        while(s.charAt(index) == ' '){//去除最后的空格
            index--;
        }
        int wordLength = 0;
        while(index >= 0 && s.charAt(index) != ' '){//读取字符串长度
            wordLength++;
            index--;
        }
        return wordLength;
    }
}
```

```text
20、编写一个函数来查找字符串数组中的最长公共前缀。

如果不存在公共前缀，返回空字符串 ""。
 
```

```java
class Solution {
    public String longestCommonPrefix(String[] strs) {
        if(strs == null || strs.length == 0){
            return "";
        }
        int length = strs[0].length();
        int count = strs.length;
        for(int i = 0; i< length;i++){
            char c = strs[0].charAt(i);
            for(int j = 1; j< count;j++){
                if(i == strs[j].length() || strs[j].charAt(i) != c){
                    return strs[0].substring(0,i);
                }
            }
        }
        return strs[0];
    }
}
```

```text
21、给你一个字符串 s ，请你反转字符串中 单词 的顺序。

单词 是由非空格字符组成的字符串。s 中使用至少一个空格将字符串中的 单词 分隔开。

返回 单词 顺序颠倒且 单词 之间用单个空格连接的结果字符串。

注意：输入字符串 s中可能会存在前导空格、尾随空格或者单词间的多个空格。返回的结果字符串中，单词间应当仅用单个空格分隔，且不包含任何额外的空格。

 

示例 1：

输入：s = "the sky is blue"
输出："blue is sky the"
```

```java
class Solution {
    public String reverseWords(String s) {
        s = s.trim();
        // 正则匹配连续的空白字符作为分隔符分割
        List<String> wordList = Arrays.asList(s.split("\\s+"));
        Collections.reverse(wordList);
        return String.join(" ",wordList);
    }
}
```


```text
22、将一个给定字符串 s 根据给定的行数 numRows ，以从上往下、从左到右进行 Z 字形排列。

比如输入字符串为 "PAYPALISHIRING" 行数为 3 时，排列如下：

P   A   H   N
A P L S I I G
Y   I   R
之后，你的输出需要从左往右逐行读取，产生出一个新的字符串，比如："PAHNAPLSIIGYIR"。

请你实现这个将字符串进行指定行数变换的函数：

string convert(string s, int numRows);
```

```java
class Solution {
    public String convert(String s, int numRows) {
        int n =s.length();
        int r=numRows;
        if(r==1||r>=n){
            return s;
        }
        int t = r*2-2;//r+r+2;t为多少个字符组成一个Z
        //每个周期会占用矩阵上的1+r-2 = r-1列
        int c = (n+t-1)/t *(r-1);//需要多少行,
        char[][] mat = new char[r][c];
        for(int i = 0,x= 0,y= 0;i<n;++i){
            mat[x][y] = s.charAt(i);
            if(i%t<r-1){
                ++x;//向下移动
            }else{
                --x;//向上移动
                ++y;//向右移动
            }
        }

        //输出
        StringBuffer ans = new StringBuffer();
        for(char[] row : mat){
            for(char ch : row){
                if(ch != 0){
                    ans.append(ch);
                }
            }
        }
        return ans.toString();
    }
}
```


```text
23、给你两个字符串 haystack 和 needle ，请你在 haystack 字符串中找出 needle 字符串的第一个匹配项的下标（下标从 0 开始）。
如果 needle 不是 haystack 的一部分，则返回  -1 。

 

```

```java
class Solution {//KMP ??????
    public int strStr(String haystack, String needle) {
        int n = haystack.length();
        int m = needle.length();
        if(m == 0){
            return 0;
        }
        int[] pi = new int[m];
        for(int i = 1,j= 0;i<m;i++){
            while(j >0 && needle.charAt(i) != needle.charAt(j)){
                j = pi[j-1];
            }
            if(needle.charAt(i) == needle.charAt(j)){
                j++;
            }
            pi[i] = j;
        }

        for(int i = 0, j = 0;i<n;i++){
            while(j>0 && haystack.charAt(i) != needle.charAt(j)){
                j = pi[j-1];
            }
            if(haystack.charAt(i) == needle.charAt(j)){
                j++;
            }
            if(j == m){
                return i-m +1;
            }
        }
        return -1;
    }
}

```

```text
24、给定一个单词数组 words 和一个长度 maxWidth ，重新排版单词，使其成为每行恰好有 maxWidth 个字符，且左右两端对齐的文本。

你应该使用 “贪心算法” 来放置给定的单词；也就是说，尽可能多地往每行中放置单词。必要时可用空格 ' ' 填充，使得每行恰好有 maxWidth 个字符。

要求尽可能均匀分配单词间的空格数量。如果某一行单词间的空格不能均匀分配，则左侧放置的空格数要多于右侧的空格数。

文本的最后一行应为左对齐，且单词之间不插入额外的空格。

注意:

单词是指由非空格字符组成的字符序列。
每个单词的长度大于 0，小于等于 maxWidth。
输入单词数组 words 至少包含一个单词。

示例 1:

输入: words = ["This", "is", "an", "example", "of", "text", "justification."], maxWidth = 16
输出:
[
   "This    is    an",
   "example  of text",
   "justification.  "
]

```

```java
class Solution {
    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> ans = new ArrayList<String>();
        int right = 0, n = words.length;
        while (true) {
            int left = right; // 当前行的第一个单词在 words 的位置
            int sumLen = 0; // 统计这一行单词长度之和
            // 循环确定当前行可以放多少单词，注意单词之间应至少有一个空格
            while (right < n && sumLen + words[right].length() + right - left <= maxWidth) {
                sumLen += words[right++].length();
            }

            // 当前行是最后一行：单词左对齐，且单词之间应只有一个空格，在行末填充剩余空格
            if (right == n) {
                StringBuffer sb = join(words, left, n, " ");
                sb.append(blank(maxWidth - sb.length()));
                ans.add(sb.toString());
                return ans;
            }

            int numWords = right - left;
            int numSpaces = maxWidth - sumLen;

            // 当前行只有一个单词：该单词左对齐，在行末填充剩余空格
            if (numWords == 1) {
                StringBuffer sb = new StringBuffer(words[left]);
                sb.append(blank(numSpaces));
                ans.add(sb.toString());
                continue;
            }

            // 当前行不只一个单词
            int avgSpaces = numSpaces / (numWords - 1);
            int extraSpaces = numSpaces % (numWords - 1);
            StringBuffer sb = new StringBuffer();
            sb.append(join(words, left, left + extraSpaces + 1, blank(avgSpaces + 1))); // 拼接额外加一个空格的单词
            sb.append(blank(avgSpaces));
            sb.append(join(words, left + extraSpaces + 1, right, blank(avgSpaces))); // 拼接其余单词
            ans.add(sb.toString());
        }
    }

    // blank 返回长度为 n 的由空格组成的字符串
    public String blank(int n) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < n; ++i) {
            sb.append(' ');
        }
        return sb.toString();
    }

    // join 返回用 sep 拼接 [left, right) 范围内的 words 组成的字符串
    public StringBuffer join(String[] words, int left, int right, String sep) {
        StringBuffer sb = new StringBuffer(words[left]);
        for (int i = left + 1; i < right; ++i) {
            sb.append(sep);
            sb.append(words[i]);
        }
        return sb;
    
    }
}

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
23、



```

```java


```


```text
23、



```

```java


```