
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
如果在将所有大写字符转换为小写字符、并移除所有非字母数字字符之后，短语正着读和反着读都一样。则可以认为该短语是一个 回文串 。

字母和数字都属于字母数字字符。

给你一个字符串 s，如果它是 回文串 ，返回 true ；否则，返回 false 。




```

```java
class Solution {
    public boolean isPalindrome(String s) {
        int n = s.length();
        int left = 0, right= n-1;
        while(left<right){
            while(left<right && !Character.isLetterOrDigit(s.charAt(left))){
                ++left;
            }
            while(left<right && !Character.isLetterOrDigit(s.charAt(right))){
                --right;
            }

            if(left<right){
                if(Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))){
                    return false;
                }
                ++left;
                --right;
            }
        }
        return true;
    }
}

```

```text
26、给定字符串 s 和 t ，判断 s 是否为 t 的子序列。

字符串的一个子序列是原始字符串删除一些（也可以不删除）字符而不改变剩余字符相对位置形成的新字符串。（例如，"ace"是"abcde"的一个子序列，而"aec"不是）。



```

```java
class Solution {
    public boolean isSubsequence(String s, String t) {
        int n = s.length();
        int m = t.length();
        int i = 0;
        int j = 0;

        while(i < n && j <m){
            if(s.charAt(i) == t.charAt(j)){
                i++;
            }
            j++;
        }
        return i == n;
    }
}

```


```text
27、给你一个下标从 1 开始的整数数组 numbers ，该数组已按 非递减顺序排列  ，请你从数组中找出满足相加之和等于目标数 target 的两个数。如果设这两个数分别是 numbers[index1] 和 numbers[index2] ，则 1 <= index1 < index2 <= numbers.length 。

以长度为 2 的整数数组 [index1, index2] 的形式返回这两个整数的下标 index1 和 index2。

你可以假设每个输入 只对应唯一的答案 ，而且你 不可以 重复使用相同的元素。

你所设计的解决方案必须只使用常量级的额外空间。



```

```java
class Solution {//双指针
    public int[] twoSum(int[] numbers, int target) {
        int low = 0;
        int high = numbers.length -1;
        while(low < high){
            int sum = numbers[low] + numbers[high];
            if(sum == target){
                return new int []{low+1,high+1};
            } else if(sum < target){
                low ++;
            }else{
                --high;
            }
        }
        return new int[]{-1,-1};
    }
}

class Solution {//二分法
    public int[] twoSum(int[] numbers, int target) {
        for(int i = 0;i<numbers.length;++i){
            int low = i+1;
            int high = numbers.length-1;
            while(low <= high){
                int mid = (high - low)/2+low;
                if(numbers[mid] == target-numbers[i]){
                    return new int[]{i+1,mid+1};
                }else if(numbers[mid] > target-numbers[i]){
                    high = mid -1;
                }else{
                    low = mid +1;
                }
            }
        }
        return new int[]{-1,-1};
    }
}

```


```text
28、给定一个长度为 n 的整数数组 height 。有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i]) 。

找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。

返回容器可以储存的最大水量。

说明：你不能倾斜容器。



```

```java
class Solution {
    public int maxArea(int[] height) {
        int l = 0;
        int r = height.length -1;
        int ans = 0;

        while(l<r){
            int area = Math.min(height[l],height[r])*(r-l);//两边最短的为宽
            ans = Math.max(ans,area);
            if(height[l] <=height[r]){
                l++;
            }else{
                r--;
            }
        }
        return ans;
    }
}

```



```text
29、给你一个整数数组 nums ，判断是否存在三元组 [nums[i], nums[j], nums[k]] 满足 i != j、i != k 且 j != k ，同时还满足 nums[i] + nums[j] + nums[k] == 0 。请

你返回所有和为 0 且不重复的三元组。

注意：答案中不可以包含重复的三元组。



```

```java
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        int n = nums.length;
        Arrays.sort(nums);
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        //枚举a
        for(int first = 0; first < n; ++ first){
            //需要和上次的数不同
            if(first > 0 && nums[first] == nums[first -1]){
                continue;
            }

            //c对应的指针初始指向数组的最右端
            int third = n-1;
            int target = - nums[first];

            //枚举b
            for(int second = first+1;second <n;++second){
                if(second > first+1 && nums[second] == nums[second-1]){//需要和上次的数不同
                    continue;
                }
                //需要保证b的指针在c的指针的左侧
                while(second <third && nums[second] + nums[third] > target){
                    -- third;
                }
                if(second == third){
                    break;
                }
                if(nums[second] + nums[third] == target){
                    List<Integer> list = new ArrayList<>();
                    list.add(nums[first]);
                    list.add(nums[second]);
                    list.add(nums[third]);
                    ans.add(list);
                }

            }
        }
        return ans;
    }
}

```


```text
30、给定一个含有 n 个正整数的数组和一个正整数 target 。

找出该数组中满足其总和大于等于 target 的长度最小的 连续子数组 [numsl, numsl+1, ..., numsr-1, numsr] ，并返回其长度。如果不存在符合条件的子数组，返回 0 。

 

示例 1：

输入：target = 7, nums = [2,3,1,2,4,3]
输出：2
解释：子数组 [4,3] 是该条件下的长度最小的子数组。



```

```java
class Solution {
    public int minSubArrayLen(int target, int[] nums) {
        int n = nums.length;
        if(n == 0){
            return 0;
        }

        int ans = Integer.MAX_VALUE;
        int start = 0;
        int end = 0;
        int sum = 0;

        while(end < n){//循环终止条件
            sum += nums[end];
            while(sum >= target){//如果数列和>target,则移动开始值
                ans = Math.min(ans,end-start+1);
                sum -=nums[start];
                start++;
            }
            end++;
        }
        return ans == Integer.MAX_VALUE ? 0 : ans;

    }
}

```

```text
31、给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。

 

示例 1:

输入: s = "abcabcbb"
输出: 3 
解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。



```

```java

class Solution {
    public int lengthOfLongestSubstring(String s) {
        //哈希集合，记录每个字符是否出现过
        Set<Character> occ = new HashSet<>();
        int n = s.length();
        //右指针，初始值为-1，相当于我们在字符串的左边界的左侧，还没有开始移动
        int rk = -1;
        int ans = 0;
        for(int i = 0;i<n;++i){
            if(i!=0){
                occ.remove(s.charAt(i-1));//左指针向右移动一格，移除一个字符
            }
            while(rk + 1 <n && !occ.contains(s.charAt(rk+1))){
                occ.add(s.charAt(rk+1));
                ++rk;
            }
            ans = Math.max(ans,rk-i+1);
        }
        return ans;
    }
}
```

```text
32、给定一个字符串 s 和一个字符串数组 words。 words 中所有字符串 长度相同。

 s 中的 串联子串 是指一个包含  words 中所有字符串以任意顺序排列连接起来的子串。

例如，如果 words = ["ab","cd","ef"]， 那么 "abcdef"， "abefcd"，"cdabef"， "cdefab"，"efabcd"， 和 "efcdab" 都是串联子串。 "acdbef" 不是串联子串，因为他不是任何 words 排列的连接。
返回所有串联子串在 s 中的开始索引。你可以以 任意顺序 返回答案。



```

```java
class Solution {//????
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> res = new ArrayList<Integer>();
        int m = words.length;
        int n = words[0].length();
        int ls = s.length();

        for(int i = 0; i<n ;i++){
            if(i+m*n >ls){//边界情况
                break;
            }

            Map<String,Integer> differ = new HashMap<String,Integer>();//
            for(int j = 0; j<m;j++){
                String word = s.substring(i+j*n,i+(j+1)*n);
                differ.put(word,differ.getOrDefault(word,0)+1);
            }

            for(String word: words){
                differ.put(word,differ.getOrDefault(word,0)-1);
                if(differ.get(word) == 0){
                    differ.remove(word);
                }
            }

            for(int start = i;start <ls-m*n+1;start+=n){//滑动窗口
                if(start != i){
                    String word = s.substring(start+(m-1)*n,start + m*n);
                    differ.put(word,differ.getOrDefault(word,0)+1);
                    if(differ.get(word) == 0){
                        differ.remove(word);
                    }
                    word = s.substring(start-n,start);
                    differ.put(word,differ.getOrDefault(word,0)-1);
                    if(differ.get(word) == 0){
                        differ.remove(word);
                    }
                }
                if(differ.isEmpty()){
                    res.add(start);
                }
            }
        }
        return res;
    }
}

```

```text
33、给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 "" 。

 

注意：

对于 t 中重复字符，我们寻找的子字符串中该字符数量必须不少于 t 中该字符数量。
如果 s 中存在这样的子串，我们保证它是唯一的答案。
 

示例 1：

输入：s = "ADOBECODEBANC", t = "ABC"
输出："BANC"
解释：最小覆盖子串 "BANC" 包含来自字符串 t 的 'A'、'B' 和 'C'。
示例 2：

输入：s = "a", t = "a"
输出："a"
解释：整个字符串 s 是最小覆盖子串。




```

```java
class Solution {
    Map<Character,Integer> ori = new HashMap<Character,Integer>();//原始t中的元素和个数
    Map<Character,Integer> cnt = new HashMap<Character,Integer>();//滑动窗口中的元素和个数
    public String minWindow(String s, String t) {//s="ADOBECODEBANC"，t="ABC"
        int tLen = t.length();
        for(int i = 0; i<tLen;i++){//统计t中的所有元素  A,1 B,1 C,1
            char c = t.charAt(i);
            ori.put(c,ori.getOrDefault(c,0)+1);
        }

        int l = 0; 
        int r= -1;
        int len = Integer.MAX_VALUE;
        int ansL = -1;//窗口的左边界
        int ansR = -1;//窗口的右边界

        int sLen = s.length();
        while(r<sLen){//右边界先滑动，滑动到找到满足包含所有的t元素
            ++r;
            if(r <sLen && ori.containsKey(s.charAt(r))){
                cnt.put(s.charAt(r),cnt.getOrDefault(s.charAt(r),0)+1);
            }

            while(check() && l<= r){//满足后，开始滑动左边界
                if(r-l+1 <len){
                    len = r-l+1;
                    ansL = l;
                    ansR = l+len;
                }
                if(ori.containsKey(s.charAt(l))){
                    cnt.put(s.charAt(l),cnt.getOrDefault(s.charAt(l),0)-1);
                }
                ++l;
            }
        }
        return ansL == -1 ? "" : s.substring(ansL,ansR);

    }

    public boolean check(){
        Iterator iterator = ori.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry entry = (Map.Entry)iterator.next();
            Character key = (Character)entry.getKey();
            Integer val = (Integer)entry.getValue();
            if(cnt.getOrDefault(key,0) < val){
                return false;
            }
        }
        return true;
    }
}

```

```text
34、请你判断一个 9 x 9 的数独是否有效。只需要 根据以下规则 ，验证已经填入的数字是否有效即可。

数字 1-9 在每一行只能出现一次。
数字 1-9 在每一列只能出现一次。
数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。（请参考示例图）
 

注意：

一个有效的数独（部分已被填充）不一定是可解的。
只需要根据以上规则，验证已经填入的数字是否有效即可。
空白格用 '.' 表示。




```

```java
class Solution {//?????
    public boolean isValidSudoku(char[][] board) {
        int[][] rows = new int[9][9];
        int[][]columns = new int[9][9];
        int[][][] subboxes = new int[3][3][9];
        for(int i = 0;i<9;i++){
            for(int j = 0;j<9;j++){
                char c = board[i][j];
                if(c != '.'){
                    int index = c-'0'-1;
                    rows[i][index]++;
                    columns[j][index]++;
                    subboxes[i/3][j/3][index]++;
                    if(rows[i][index]>1 || columns[j][index]>1 || subboxes[i/3][j/3][index]>1){
                        return false;
                    }
                }
            }
        }
        return true;
    }
}

```

```text
35、给你一个 m 行 n 列的矩阵 matrix ，请按照 顺时针螺旋顺序 ，返回矩阵中的所有元素。



```

```java
class Solution {
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> order = new ArrayList<>();
        if(matrix == null ||  matrix.length == 0 || matrix[0].length == 0){
            return order;
        }

        int rows = matrix.length;
        int columns = matrix[0].length;
        int left = 0;
        int right = columns -1;
        int top = 0;
        int bottom = rows-1;

        while(left<= right && top<= bottom){
            for(int column = left;column <= right;column++){//上横
                order.add(matrix[top][column]);
            }
            for(int row = top+1;row<=bottom;row++){//右竖
                order.add(matrix[row][right]);
            }
            if(left<right && top<bottom){//最里面只用统计一行或者一竖
                for(int column = right-1; column >left; column--){//下横
                    order.add(matrix[bottom][column]);
                }
                for(int row = bottom;row >top;row--){
                    order.add(matrix[row][left]);
                }
            }
            left++;
            right--;
            top++;
            bottom--;
        }
        return order;
    }
}

```

```text
36、给定一个 n × n 的二维矩阵 matrix 表示一个图像。请你将图像顺时针旋转 90 度。

你必须在 原地 旋转图像，这意味着你需要直接修改输入的二维矩阵。请不要 使用另一个矩阵来旋转图像。





```

```java
class Solution {
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        int[][] matrix_new = new int[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                matrix_new[j][n - i - 1] = matrix[i][j];
            }
        }
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                matrix[i][j] = matrix_new[i][j];
            }
        }
    }
}



class Solution {
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        for(int i = 0; i<n/2;++i){
            for(int j = 0; j<(n+1)/2;++j){
                int temp = matrix[i][j];
                matrix[i][j] = matrix[n-j-1][i];
                matrix[n-j-1][i] = matrix[n-i-1][n-j-1];
                matrix[n-i-1][n-j-1] = matrix[j][n-i-1];
                matrix[j][n-i-1]= temp;
            }
        }
    }
}

```

```text
37、给定一个 m x n 的矩阵，如果一个元素为 0 ，则将其所在行和列的所有元素都设为 0 。请使用 原地 算法。





```

```java
class Solution {
    public void setZeroes(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        boolean[] row = new boolean[m];
        boolean[] col = new boolean[n];
        for(int i =0;i<m;i++){
            for(int j = 0;j<n;j++){
                if(matrix[i][j] == 0){
                    row[i]= col[j] = true;
                }
            }
        }
        for(int i = 0; i<m;i++){
            for(int j = 0;j<n;j++){
                if(row[i] || col[j]){
                    matrix[i][j] = 0;
                }
            }
        }
    }
}

```

```text
38、根据 百度百科 ， 生命游戏 ，简称为 生命 ，是英国数学家约翰·何顿·康威在 1970 年发明的细胞自动机。

给定一个包含 m × n 个格子的面板，每一个格子都可以看成是一个细胞。每个细胞都具有一个初始状态： 1 即为 活细胞 （live），或 0 即为 死细胞 （dead）。每个细胞与其八个相邻位置（水平，垂直，对角线）的细胞都遵循以下四条生存定律：

如果活细胞周围八个位置的活细胞数少于两个，则该位置活细胞死亡；
如果活细胞周围八个位置有两个或三个活细胞，则该位置活细胞仍然存活；
如果活细胞周围八个位置有超过三个活细胞，则该位置活细胞死亡；
如果死细胞周围正好有三个活细胞，则该位置死细胞复活；
下一个状态是通过将上述规则同时应用于当前状态下的每个细胞所形成的，其中细胞的出生和死亡是同时发生的。给你 m x n 网格面板 board 的当前状态，返回下一个状态。



```

```java
class Solution {
    public void gameOfLife(int[][] board) {
        int[] neighbors = {0, 1, -1};

        int rows = board.length;
        int cols = board[0].length;

        // 创建复制数组 copyBoard
        int[][] copyBoard = new int[rows][cols];

        // 从原数组复制一份到 copyBoard 中
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                copyBoard[row][col] = board[row][col];
            }
        }

        // 遍历面板每一个格子里的细胞
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {

                // 对于每一个细胞统计其八个相邻位置里的活细胞数量
                int liveNeighbors = 0;

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {

                        if (!(neighbors[i] == 0 && neighbors[j] == 0)) {
                            int r = (row + neighbors[i]);
                            int c = (col + neighbors[j]);

                            // 查看相邻的细胞是否是活细胞
                            if ((r < rows && r >= 0) && (c < cols && c >= 0) && (copyBoard[r][c] == 1)) {
                                liveNeighbors += 1;
                            }
                        }
                    }
                }

                // 规则 1 或规则 3      
                if ((copyBoard[row][col] == 1) && (liveNeighbors < 2 || liveNeighbors > 3)) {
                    board[row][col] = 0;
                }
                // 规则 4
                if (copyBoard[row][col] == 0 && liveNeighbors == 3) {
                    board[row][col] = 1;
                }
            }
        }
    }
}


```

```text
39、给你两个字符串：ransomNote 和 magazine ，判断 ransomNote 能不能由 magazine 里面的字符构成。

如果可以，返回 true ；否则返回 false 。

magazine 中的每个字符只能在 ransomNote 中使用一次。



```

```java
class Solution {
    public boolean canConstruct(String ransomNote, String magazine) {
        if(ransomNote.length() > magazine.length()){
            return false;
        }
        int[] cnt = new int[26];
        for(char c : magazine.toCharArray()){
            cnt[c-'a']++;
        }
        for(char c : ransomNote.toCharArray()){
            cnt[c-'a']--;
            if(cnt[c-'a']<0){
                return false;
            }
        }
        return true;
    }
}

```

```text
40、给定两个字符串 s 和 t ，判断它们是否是同构的。

如果 s 中的字符可以按某种映射关系替换得到 t ，那么这两个字符串是同构的。

每个出现的字符都应当映射到另一个字符，同时不改变字符的顺序。不同字符不能映射到同一个字符上，相同字符只能映射到同一个字符上，字符可以映射到自己本身。



```

```java
class Solution {
    public boolean isIsomorphic(String s, String t) {
        Map<Character,Character> s2t = new HashMap<>();
        Map<Character,Character> t2s = new HashMap<>();

        int len = s.length();
        for(int i = 0;i<len;++i){
            char x = s.charAt(i);
            char y = t.charAt(i);

            if((s2t.containsKey(x) && s2t.get(x) != y) || (t2s.containsKey(y) && t2s.get(y) != x)){
                return false;
            }

            s2t.put(x,y);
            t2s.put(y,x);
        }
        return true;
    }
}

```

```text
41、给定一种规律 pattern 和一个字符串 s ，判断 s 是否遵循相同的规律。

这里的 遵循 指完全匹配，例如， pattern 里的每个字母和字符串 s 中的每个非空单词之间存在着双向连接的对应规律。

 

示例1:

输入: pattern = "abba", s = "dog cat cat dog"
输出: true



```

```java
class Solution {
    public boolean wordPattern(String pattern, String s) {
        Map<String,Character> str2ch = new HashMap<>();
        Map<Character,String> ch2str = new HashMap<>();
        int m = s.length();
        int i = 0;

        for(int p = 0;p<pattern.length();++p){
            char ch = pattern.charAt(p);
            if(i>=m){
                return false;
            }
            int j = i;
            while(j<m && s.charAt(j) != ' '){
                j++;
            }

            String temp = s.substring(i,j);
            if(str2ch.containsKey(temp) && str2ch.get(temp) != ch){
                return false;
            }

            if(ch2str.containsKey(ch) && !temp.equals(ch2str.get(ch))){
                return false;
            }
            str2ch.put(temp,ch);
            ch2str.put(ch,temp);
            i= j+ 1;
        }
        return i >= m;
    }
}

```

```text
42、给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。

注意：若 s 和 t 中每个字符出现的次数都相同，则称 s 和 t 互为字母异位词。

 

示例 1:

输入: s = "anagram", t = "nagaram"
输出: true



```

```java
class Solution {
    public boolean isAnagram(String s, String t) {
        if(s.length() != t.length()){
            return false;
        }
        int[] table = new int[26];
        for(int i = 0;i<s.length();i++){
            table[s.charAt(i)-'a']++;
        }
        for(int i = 0;i<t.length();i++){
            table[t.charAt(i)-'a']--;
            if(table[t.charAt(i)-'a']<0){
                return false;
            }
        }
        return true;
    }
}

```

```text
43、给你一个字符串数组，请你将 字母异位词 组合在一起。可以按任意顺序返回结果列表。

字母异位词 是由重新排列源单词的所有字母得到的一个新单词。

 

示例 1:

输入: strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
输出: [["bat"],["nat","tan"],["ate","eat","tea"]]




```

```java
class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String,List<String>> map = new HashMap<String,List<String>>();
        for(String str: strs){
            char[] array = str.toCharArray();
            Arrays.sort(array);
            String key = new String(array);
            List<String> list = map.getOrDefault(key,new ArrayList<String>());
            list.add(str);
            map.put(key,list);
        }
        return new ArrayList<List<String>>(map.values());
    }
}

```

```text
44、给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。

你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。

你可以按任意顺序返回答案。

 

示例 1：

输入：nums = [2,7,11,15], target = 9
输出：[0,1]
解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
示例 2：

输入：nums = [3,2,4], target = 6
输出：[1,2]
示例 3：

输入：nums = [3,3], target = 6
输出：[0,1]



```

```java
class Solution {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer,Integer> hashtable = new HashMap<>();
        for(int i = 0;i<nums.length;++i){
            if(hashtable.containsKey(target-nums[i])){
                return new int[]{hashtable.get(target-nums[i]),i};
            }
            hashtable.put(nums[i],i);
        }
        return new int[0];
    }
}

```

```text
45、编写一个算法来判断一个数 n 是不是快乐数。

「快乐数」 定义为：

对于一个正整数，每一次将该数替换为它每个位置上的数字的平方和。
然后重复这个过程直到这个数变为 1，也可能是 无限循环 但始终变不到 1。
如果这个过程 结果为 1，那么这个数就是快乐数。
如果 n 是 快乐数 就返回 true ；不是，则返回 false 。

 

示例 1：

输入：n = 19
输出：true
解释：
12 + 92 = 82
82 + 22 = 68
62 + 82 = 100
12 + 02 + 02 = 1
示例 2：

输入：n = 2
输出：false



```

```java
class Solution {
    public boolean isHappy(int n) {
        Set<Integer> seen = new HashSet<>();
        while(n != 1 && !seen.contains(n)){
            seen.add(n);
            n = getNext(n);
        }
        return n == 1;
    }

    private int getNext(int n){
        int totalSum = 0;
        while(n>0){
            int d = n%10;
            n = n/10;
            totalSum += d*d;
        }
        return totalSum;
    }
}

```

```text
46、给你一个整数数组 nums 和一个整数 k ，判断数组中是否存在两个 不同的索引 i 和 j ，满足 nums[i] == nums[j] 且 abs(i - j) <= k 。如果存在，返回 true ；否则，返回 false 。

 

示例 1：

输入：nums = [1,2,3,1], k = 3
输出：true
示例 2：

输入：nums = [1,0,1,1], k = 1
输出：true
示例 3：

输入：nums = [1,2,3,1,2,3], k = 2
输出：false
 



```

```java
class Solution {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Map<Integer,Integer> map = new HashMap<>();
        int length = nums.length;
        for(int i = 0;i<length;i++){
            int num = nums[i];
            if(map.containsKey(num) && i-map.get(num)<=k){
                return true;
            }
            map.put(num,i);
        }
        return false;
    }
}

```

```text
47、给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。

请你设计并实现时间复杂度为 O(n) 的算法解决此问题。

 

示例 1：

输入：nums = [100,4,200,1,3,2]
输出：4
解释：最长数字连续序列是 [1, 2, 3, 4]。它的长度为 4。
示例 2：

输入：nums = [0,3,7,2,5,8,4,6,0,1]
输出：9
 



```

```java
class Solution {
    public int longestConsecutive(int[] nums) {
        Set<Integer> numSet = new HashSet<Integer>();
        for(int num:nums){
            numSet.add(num);
        }

        int longestStreak = 0;
        for(int num : numSet){
            if(!numSet.contains(num-1)){
                int currentNum = num;
                int currentStreak = 1;

                while(numSet.contains(currentNum+1)){
                    currentNum += 1;
                    currentStreak += 1;
                }

                longestStreak = Math.max(longestStreak,currentStreak);
            }
        }
        return longestStreak;
    }
}

```

```text
48、给定一个  无重复元素 的 有序 整数数组 nums 。

返回 恰好覆盖数组中所有数字 的 最小有序 区间范围列表 。也就是说，nums 的每个元素都恰好被某个区间范围所覆盖，并且不存在属于某个范围但不属于 nums 的数字 x 。

列表中的每个区间范围 [a,b] 应该按如下格式输出：

"a->b" ，如果 a != b
"a" ，如果 a == b
 

示例 1：

输入：nums = [0,1,2,4,5,7]
输出：["0->2","4->5","7"]
解释：区间范围是：
[0,2] --> "0->2"
[4,5] --> "4->5"
[7,7] --> "7"
示例 2：

输入：nums = [0,2,3,4,6,8,9]
输出：["0","2->4","6","8->9"]
解释：区间范围是：
[0,0] --> "0"
[2,4] --> "2->4"
[6,6] --> "6"
[8,9] --> "8->9"




```

```java
class Solution {
    public List<String> summaryRanges(int[] nums) {
        List<String> ret = new ArrayList<>();
        int i =0;
        int n = nums.length;
        while(i<n){
            int low = i;
            i++;
            while(i<n && nums[i]== nums[i-1]+1){
                i++;
            }
            int high = i-1;

            StringBuffer temp = new StringBuffer(Integer.toString(nums[low]));
            if(low<high){
                temp.append("->");
                temp.append(Integer.toString(nums[high]));
            }
            ret.add(temp.toString());
        }
        return ret;
    }
}

```

```text
49、以数组 intervals 表示若干个区间的集合，其中单个区间为 intervals[i] = [starti, endi] 。请你合并所有重叠的区间，并返回 一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间 。

 

示例 1：

输入：intervals = [[1,3],[2,6],[8,10],[15,18]]
输出：[[1,6],[8,10],[15,18]]
解释：区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].
示例 2：

输入：intervals = [[1,4],[4,5]]
输出：[[1,5]]
解释：区间 [1,4] 和 [4,5] 可被视为重叠区间。



```

```java
class Solution {
    public int[][] merge(int[][] intervals) {
        if(intervals.length == 0){
            return new int[0][2];
        }

        Arrays.sort(intervals,new Comparator<int[]>(){
            public int compare(int [] interval1,int[] interval2){
                return interval1[0] - interval2[0];
            }
        });

        List<int[]> merged = new ArrayList<int[]>();
        for(int i = 0;i<intervals.length;++i ){
            int L= intervals[i][0];
            int R = intervals[i][1];
            if(merged.size() == 0 || merged.get(merged.size()-1)[1]<L){
                merged.add(new int[]{L,R});
            }else{
                merged.get(merged.size()-1)[1] = Math.max(merged.get(merged.size()-1)[1],R);
            }
        }
        return merged.toArray(new int[merged.size()][]);
    }
}

```

```text
50、给你一个 无重叠的 ，按照区间起始端点排序的区间列表。

在列表中插入一个新的区间，你需要确保列表中的区间仍然有序且不重叠（如果有必要的话，可以合并区间）。

 

示例 1：

输入：intervals = [[1,3],[6,9]], newInterval = [2,5]
输出：[[1,5],[6,9]]
示例 2：

输入：intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
输出：[[1,2],[3,10],[12,16]]
解释：这是因为新的区间 [4,8] 与 [3,5],[6,7],[8,10] 重叠。
示例 3：

输入：intervals = [], newInterval = [5,7]
输出：[[5,7]]



```

```java
class Solution {
    public int[][] insert(int[][] intervals, int[] newInterval) {
        int left = newInterval[0];
        int right = newInterval[1];
        boolean placed = false;
        List<int[]> ansList = new ArrayList<int[]>();
        for (int[] interval : intervals) {
            if (interval[0] > right) {
                // 在插入区间的右侧且无交集
                if (!placed) {
                    ansList.add(new int[]{left, right});
                    placed = true;                    
                }
                ansList.add(interval);
            }else if (interval[1] < left) {
                // 在插入区间的左侧且无交集
                ansList.add(interval);
            }else {
                // 与插入区间有交集，计算它们的并集
                left = Math.min(left, interval[0]);
                right = Math.max(right, interval[1]);
            }
        }
        if (!placed) {
            ansList.add(new int[]{left, right});
        }
        int[][] ans = new int[ansList.size()][2];
        for (int i = 0; i < ansList.size(); ++i) {
            ans[i] = ansList.get(i);
        }
        return ans;
    }
}

```

```text
51、有一些球形气球贴在一堵用 XY 平面表示的墙面上。墙面上的气球记录在整数数组 points ，其中points[i] = [xstart, xend] 表示水平直径在 xstart 和 xend之间的气球。你不知道气球的确切 y 坐标。

一支弓箭可以沿着 x 轴从不同点 完全垂直 地射出。在坐标 x 处射出一支箭，若有一个气球的直径的开始和结束坐标为 xstart，xend， 且满足  xstart ≤ x ≤ xend，则该气球会被 引爆 。可以射出的弓箭的数量 没有限制 。 弓箭一旦被射出之后，可以无限地前进。

给你一个数组 points ，返回引爆所有气球所必须射出的 最小 弓箭数 。

 
示例 1：

输入：points = [[10,16],[2,8],[1,6],[7,12]]
输出：2
解释：气球可以用2支箭来爆破:
-在x = 6处射出箭，击破气球[2,8]和[1,6]。
-在x = 11处发射箭，击破气球[10,16]和[7,12]。
示例 2：

输入：points = [[1,2],[3,4],[5,6],[7,8]]
输出：4
解释：每个气球需要射出一支箭，总共需要4支箭。
示例 3：

输入：points = [[1,2],[2,3],[3,4],[4,5]]
输出：2
解释：气球可以用2支箭来爆破:
- 在x = 2处发射箭，击破气球[1,2]和[2,3]。
- 在x = 4处射出箭，击破气球[3,4]和[4,5]。




```

```java
class Solution {
    public int findMinArrowShots(int[][] points) {
        if (points.length == 0) {
            return 0;
        }
        Arrays.sort(points, new Comparator<int[]>() {
            public int compare(int[] point1, int[] point2) {
                if (point1[1] > point2[1]) {
                    return 1;
                } else if (point1[1] < point2[1]) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        int pos = points[0][1];
        int ans = 1;
        for (int[] balloon: points) {
            if (balloon[0] > pos) {
                pos = balloon[1];
                ++ans;
            }
        }
        return ans;
    }
}

```

```text
52、给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。

有效字符串需满足：

左括号必须用相同类型的右括号闭合。
左括号必须以正确的顺序闭合。
每个右括号都有一个对应的相同类型的左括号。
 

示例 1：

输入：s = "()"
输出：true
示例 2：

输入：s = "()[]{}"
输出：true
示例 3：

输入：s = "(]"
输出：false
 



```

```java
class Solution {
    public boolean isValid(String s) {
        int n = s.length();
        if(n%2 == 1){
            return false;
        }
        Map<Character,Character> pairs = new HashMap<Character,Character>(){{
            put(')','(');
            put(']','[');
            put('}','{');
        }};

        Deque<Character> stack = new LinkedList<Character>();
        for(int i = 0;i<n;i++){
            char ch = s.charAt(i);
            if(pairs.containsKey(ch)){
                if(stack.isEmpty()||stack.peek()!= pairs.get(ch)){
                    return false;
                }
                stack.pop();
            }else{
                stack.push(ch);
            }
        }
        return stack.isEmpty();
    }
}

```

```text
53、给你一个字符串 path ，表示指向某一文件或目录的 Unix 风格 绝对路径 （以 '/' 开头），请你将其转化为更加简洁的规范路径。

在 Unix 风格的文件系统中，一个点（.）表示当前目录本身；此外，两个点 （..） 表示将目录切换到上一级（指向父目录）；两者都可以是复杂相对路径的组成部分。任意多个连续的斜杠（即，'//'）都被视为单个斜杠 '/' 。 对于此问题，任何其他格式的点（例如，'...'）均被视为文件/目录名称。

请注意，返回的 规范路径 必须遵循下述格式：

始终以斜杠 '/' 开头。
两个目录名之间必须只有一个斜杠 '/' 。
最后一个目录名（如果存在）不能 以 '/' 结尾。
此外，路径仅包含从根目录到目标文件或目录的路径上的目录（即，不含 '.' 或 '..'）。
返回简化后得到的 规范路径 。

 

示例 1：

输入：path = "/home/"
输出："/home"
解释：注意，最后一个目录名后面没有斜杠。 
示例 2：

输入：path = "/../"
输出："/"
解释：从根目录向上一级是不可行的，因为根目录是你可以到达的最高级。
示例 3：

输入：path = "/home//foo/"
输出："/home/foo"
解释：在规范路径中，多个连续斜杠需要用一个斜杠替换。
示例 4：

输入：path = "/a/./b/../../c/"
输出："/c"



```

```java
class Solution {
    public String simplifyPath(String path) {
        String[] names = path.split("/");
        Deque<String> stack = new ArrayDeque<String>();
        for (String name : names) {
            if ("..".equals(name)) {
                if (!stack.isEmpty()) {
                    stack.pollLast();
                }
            } else if (name.length() > 0 && !".".equals(name)) {
                stack.offerLast(name);
            }
        }
        StringBuffer ans = new StringBuffer();
        if (stack.isEmpty()) {
            ans.append('/');
        } else {
            while (!stack.isEmpty()) {
                ans.append('/');
                ans.append(stack.pollFirst());
            }
        }
        return ans.toString();
    }
}

```

```text
54、设计一个支持 push ，pop ，top 操作，并能在常数时间内检索到最小元素的栈。

实现 MinStack 类:

MinStack() 初始化堆栈对象。
void push(int val) 将元素val推入堆栈。
void pop() 删除堆栈顶部的元素。
int top() 获取堆栈顶部的元素。
int getMin() 获取堆栈中的最小元素。



```

```java
class MinStack {
    Deque<Integer> xStack;
    Deque<Integer> minStack;

    public MinStack() {
        xStack = new LinkedList<Integer>();
        minStack = new LinkedList<Integer>();
        minStack.push(Integer.MAX_VALUE);
    }
    
    public void push(int val) {
        xStack.push(val);
        minStack.push(Math.min(minStack.peek(), val));
    }
    
    public void pop() {
        xStack.pop();
        minStack.pop();
    }
    
    public int top() {
        return xStack.peek();
    }
    
    public int getMin() {
        return minStack.peek();
    }
}


```

```text
55、给你一个字符串数组 tokens ，表示一个根据 逆波兰表示法 表示的算术表达式。

请你计算该表达式。返回一个表示表达式值的整数。

注意：

有效的算符为 '+'、'-'、'*' 和 '/' 。
每个操作数（运算对象）都可以是一个整数或者另一个表达式。
两个整数之间的除法总是 向零截断 。
表达式中不含除零运算。
输入是一个根据逆波兰表示法表示的算术表达式。
答案及所有中间计算结果可以用 32 位 整数表示。
 

示例 1：

输入：tokens = ["2","1","+","3","*"]
输出：9
解释：该算式转化为常见的中缀算术表达式为：((2 + 1) * 3) = 9
示例 2：

输入：tokens = ["4","13","5","/","+"]
输出：6
解释：该算式转化为常见的中缀算术表达式为：(4 + (13 / 5)) = 6



```

```java
class Solution {
    public int evalRPN(String[] tokens) {
        Deque<Integer> stack = new LinkedList<Integer>();
        int n = tokens.length;
        for (int i = 0; i < n; i++) {
            String token = tokens[i];
            if (isNumber(token)) {
                stack.push(Integer.parseInt(token));
            } else {
                int num2 = stack.pop();
                int num1 = stack.pop();
                switch (token) {
                    case "+":
                        stack.push(num1 + num2);
                        break;
                    case "-":
                        stack.push(num1 - num2);
                        break;
                    case "*":
                        stack.push(num1 * num2);
                        break;
                    case "/":
                        stack.push(num1 / num2);
                        break;
                    default:
                }
            }
        }
        return stack.pop();
    }

    public boolean isNumber(String token) {
        return !("+".equals(token) || "-".equals(token) || "*".equals(token) || "/".equals(token));
    }
}

```

```text
56、给你一个字符串表达式 s ，请你实现一个基本计算器来计算并返回它的值。

注意:不允许使用任何将字符串作为数学表达式计算的内置函数，比如 eval() 。

示例 1：

输入：s = "1 + 1"
输出：2
示例 2：

输入：s = " 2-1 + 2 "
输出：3
示例 3：

输入：s = "(1+(4+5+2)-3)+(6+8)"
输出：23

```

```java
class Solution {
    public int calculate(String s) {
        Deque<Integer> ops = new LinkedList<Integer>();
        ops.push(1);
        int sign = 1;

        int ret = 0;
        int n = s.length();
        int i = 0;
        while (i < n) {
            if (s.charAt(i) == ' ') {
                i++;
            } else if (s.charAt(i) == '+') {
                sign = ops.peek();
                i++;
            } else if (s.charAt(i) == '-') {
                sign = -ops.peek();
                i++;
            } else if (s.charAt(i) == '(') {
                ops.push(sign);
                i++;
            } else if (s.charAt(i) == ')') {
                ops.pop();
                i++;
            } else {
                long num = 0;
                while (i < n && Character.isDigit(s.charAt(i))) {
                    num = num * 10 + s.charAt(i) - '0';
                    i++;
                }
                ret += sign * num;
            }
        }
        return ret;
    }
}

```

```text
57、给你一个链表的头节点 head ，判断链表中是否有环。

如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则链表中存在环。 为了表示给定链表中的环，评测系统内部使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。注意：pos 不作为参数进行传递 。仅仅是为了标识链表的实际情况。

如果链表中存在环 ，则返回 true 。 否则，返回 false 。

 

示例 1：
输入：head = [3,2,0,-4], pos = 1
输出：true
解释：链表中有一个环，其尾部连接到第二个节点。



```

```java
public class Solution {
    public boolean hasCycle(ListNode head) {
        Set<ListNode> seen = new HashSet<ListNode>();
        while (head != null) {
            if (!seen.add(head)) {
                return true;
            }
            head = head.next;
        }
        return false;
    }
}

```

```text
58、给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。

请你将两个数相加，并以相同形式返回一个表示和的链表。

你可以假设除了数字 0 之外，这两个数都不会以 0 开头。

 

示例 1：


输入：l1 = [2,4,3], l2 = [5,6,4]
输出：[7,0,8]
解释：342 + 465 = 807.
示例 2：

输入：l1 = [0], l2 = [0]
输出：[0]



```

```java
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = null, tail = null;
        int carry = 0;
        while (l1 != null || l2 != null) {
            int n1 = l1 != null ? l1.val : 0;
            int n2 = l2 != null ? l2.val : 0;
            int sum = n1 + n2 + carry;
            if (head == null) {
                head = tail = new ListNode(sum % 10);
            } else {
                tail.next = new ListNode(sum % 10);
                tail = tail.next;
            }
            carry = sum / 10;
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
        }
        if (carry > 0) {
            tail.next = new ListNode(carry);
        }
        return head;
    }
}

```

```text
59、将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。 


示例 1：


输入：l1 = [1,2,4], l2 = [1,3,4]
输出：[1,1,2,3,4,4]
示例 2：

输入：l1 = [], l2 = []
输出：[]




```

```java
class Solution {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return list2;
        } else if (list2 == null) {
            return list1;
        } else if (list1.val < list2.val) {
            list1.next = mergeTwoLists(list1.next, list2);
            return list1;
        } else {
            list2.next = mergeTwoLists(list1, list2.next);
            return list2;
        }
    }
}

```

```text
60、给你一个长度为 n 的链表，每个节点包含一个额外增加的随机指针 random ，该指针可以指向链表中的任何节点或空节点。

构造这个链表的 深拷贝。 深拷贝应该正好由 n 个 全新 节点组成，其中每个新节点的值都设为其对应的原节点的值。新节点的 next 指针和 random 指针也都应指向复制链表中的新节点，并使原链表和复制链表中的这些指针能够表示相同的链表状态。复制链表中的指针都不应指向原链表中的节点 。

例如，如果原链表中有 X 和 Y 两个节点，其中 X.random --> Y 。那么在复制链表中对应的两个节点 x 和 y ，同样有 x.random --> y 。

返回复制链表的头节点。

用一个由 n 个节点组成的链表来表示输入/输出中的链表。每个节点用一个 [val, random_index] 表示：

val：一个表示 Node.val 的整数。
random_index：随机指针指向的节点索引（范围从 0 到 n-1）；如果不指向任何节点，则为  null 。
你的代码 只 接受原链表的头节点 head 作为传入参数。

 

示例 1：



输入：head = [[7,null],[13,0],[11,4],[10,2],[1,0]]
输出：[[7,null],[13,0],[11,4],[10,2],[1,0]]



```

```java
class Solution {
    Map<Node, Node> cachedNode = new HashMap<Node, Node>();
    public Node copyRandomList(Node head) {
        if (head == null) {
            return null;
        }
        if (!cachedNode.containsKey(head)) {
            Node headNew = new Node(head.val);
            cachedNode.put(head, headNew);
            headNew.next = copyRandomList(head.next);
            headNew.random = copyRandomList(head.random);
        }
        return cachedNode.get(head);
    }

```

```text
61、给你单链表的头指针 head 和两个整数 left 和 right ，其中 left <= right 。请你反转从位置 left 到位置 right 的链表节点，返回 反转后的链表 。
 

示例 1：


输入：head = [1,2,3,4,5], left = 2, right = 4
输出：[1,4,3,2,5]
示例 2：

输入：head = [5], left = 1, right = 1
输出：[5]




```

```java
class Solution {
    public ListNode reverseBetween(ListNode head, int left, int right) {
        // 设置 dummyNode 是这一类问题的一般做法
        ListNode dummyNode = new ListNode(-1);
        dummyNode.next = head;
        ListNode pre = dummyNode;
        for (int i = 0; i < left - 1; i++) {
            pre = pre.next;
        }
        ListNode cur = pre.next;
        ListNode next;
        for (int i = 0; i < right - left; i++) {
            next = cur.next;
            cur.next = next.next;
            next.next = pre.next;
            pre.next = next;
        }
        return dummyNode.next;
    }
}

```

```text
62、给你链表的头节点 head ，每 k 个节点一组进行翻转，请你返回修改后的链表。

k 是一个正整数，它的值小于或等于链表的长度。如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。

你不能只是单纯的改变节点内部的值，而是需要实际进行节点交换。


```

```java
class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode hair = new ListNode(0);
        hair.next = head;
        ListNode pre = hair;

        while (head != null) {
            ListNode tail = pre;
            // 查看剩余部分长度是否大于等于 k
            for (int i = 0; i < k; ++i) {
                tail = tail.next;
                if (tail == null) {
                    return hair.next;
                }
            }
            ListNode nex = tail.next;
            ListNode[] reverse = myReverse(head, tail);
            head = reverse[0];
            tail = reverse[1];
            // 把子链表重新接回原链表
            pre.next = head;
            tail.next = nex;
            pre = tail;
            head = tail.next;
        }

        return hair.next;
    }

    public ListNode[] myReverse(ListNode head, ListNode tail) {
        ListNode prev = tail.next;
        ListNode p = head;
        while (prev != tail) {
            ListNode nex = p.next;
            p.next = prev;
            prev = p;
            p = nex;
        }
        return new ListNode[]{tail, head};
    }
}

```

```text
63、给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。

示例 1：


输入：head = [1,2,3,4,5], n = 2
输出：[1,2,3,5]
示例 2：

输入：head = [1], n = 1
输出：[]


```

```java
class Solution {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0, head);
        int length = getLength(head);
        ListNode cur = dummy;
        for (int i = 1; i < length - n + 1; ++i) {
            cur = cur.next;
        }
        cur.next = cur.next.next;
        ListNode ans = dummy.next;
        return ans;
    }

    public int getLength(ListNode head) {
        int length = 0;
        while (head != null) {
            ++length;
            head = head.next;
        }
        return length;
    }
}

```

```text
64、给定一个已排序的链表的头 head ， 删除原始链表中所有重复数字的节点，只留下不同的数字 。返回 已排序的链表 。

 

示例 1：


输入：head = [1,2,3,3,4,4,5]
输出：[1,2,5]



```

```java
class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode dummy = new ListNode(0, head);
        ListNode cur = dummy;
        while (cur.next != null && cur.next.next != null) {
            if (cur.next.val == cur.next.next.val) {
                int x = cur.next.val;
                while (cur.next != null && cur.next.val == x) {
                    cur.next = cur.next.next;
                }
            } else {
                cur = cur.next;
            }
        }

        return dummy.next;

    }
}

```

```text
65、给你一个链表的头节点 head ，旋转链表，将链表每个节点向右移动 k 个位置。



```

```java
class Solution {
    public ListNode rotateRight(ListNode head, int k) {
         if (k == 0 || head == null || head.next == null) {
            return head;
        }
        int n = 1;
        ListNode iter = head;
        while (iter.next != null) {
            iter = iter.next;
            n++;
        }
        int add = n - k % n;
        if (add == n) {
            return head;
        }
        iter.next = head;
        while (add-- > 0) {
            iter = iter.next;
        }
        ListNode ret = iter.next;
        iter.next = null;
        return ret;
    }
}

```

```text
66、给你一个链表的头节点 head 和一个特定值 x ，请你对链表进行分隔，使得所有 小于 x 的节点都出现在 大于或等于 x 的节点之前。

你应当 保留 两个分区中每个节点的初始相对位置。

 

示例 1：


输入：head = [1,4,3,2,5,2], x = 3
输出：[1,2,2,4,3,5]
示例 2：

输入：head = [2,1], x = 2
输出：[1,2]



```

```java
class Solution {
    public ListNode partition(ListNode head, int x) {
        ListNode small = new ListNode(0);
        ListNode smallHead = small;
        ListNode large = new ListNode(0);
        ListNode largeHead = large;
        while (head != null) {
            if (head.val < x) {
                small.next = head;
                small = small.next;
            } else {
                large.next = head;
                large = large.next;
            }
            head = head.next;
        }
        large.next = null;
        small.next = largeHead.next;
        return smallHead.next;
    }
}

```

```text
67、请你设计并实现一个满足  LRU (最近最少使用) 缓存 约束的数据结构。
实现 LRUCache 类：
LRUCache(int capacity) 以 正整数 作为容量 capacity 初始化 LRU 缓存
int get(int key) 如果关键字 key 存在于缓存中，则返回关键字的值，否则返回 -1 。
void put(int key, int value) 如果关键字 key 已经存在，则变更其数据值 value ；如果不存在，则向缓存中插入该组 key-value 。如果插入操作导致关键字数量超过 capacity ，则应该 逐出 最久未使用的关键字。
函数 get 和 put 必须以 O(1) 的平均时间复杂度运行。

 

示例：

输入
["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
[[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
输出
[null, null, null, 1, null, -1, null, -1, 3, 4]




```

```java
class LRUCache {
    class DLinkedNode {
        int key;
        int value;
        DLinkedNode prev;
        DLinkedNode next;
        public DLinkedNode() {}
        public DLinkedNode(int _key, int _value) {key = _key; value = _value;}
    }

    private Map<Integer, DLinkedNode> cache = new HashMap<Integer, DLinkedNode>();
    private int size;
    private int capacity;
    private DLinkedNode head, tail;

    public LRUCache(int capacity) {
        this.size = 0;
        this.capacity = capacity;
        // 使用伪头部和伪尾部节点
        head = new DLinkedNode();
        tail = new DLinkedNode();
        head.next = tail;
        tail.prev = head;
    }
    
    public int get(int key) {
        DLinkedNode node = cache.get(key);
        if (node == null) {
            return -1;
        }
        // 如果 key 存在，先通过哈希表定位，再移到头部
        moveToHead(node);
        return node.value;
    }
    
    public void put(int key, int value) {
        DLinkedNode node = cache.get(key);
        if (node == null) {
            // 如果 key 不存在，创建一个新的节点
            DLinkedNode newNode = new DLinkedNode(key, value);
            // 添加进哈希表
            cache.put(key, newNode);
            // 添加至双向链表的头部
            addToHead(newNode);
            ++size;
            if (size > capacity) {
                // 如果超出容量，删除双向链表的尾部节点
                DLinkedNode tail = removeTail();
                // 删除哈希表中对应的项
                cache.remove(tail.key);
                --size;
            }
        }
        else {
            // 如果 key 存在，先通过哈希表定位，再修改 value，并移到头部
            node.value = value;
            moveToHead(node);
        }

    }
    private void addToHead(DLinkedNode node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(DLinkedNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void moveToHead(DLinkedNode node) {
        removeNode(node);
        addToHead(node);
    }

    private DLinkedNode removeTail() {
        DLinkedNode res = tail.prev;
        removeNode(res);
        return res;
    }
}

```

```text
68、给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。

子数组 是数组中的一个连续部分。

 

示例 1：

输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
输出：6
解释：连续子数组 [4,-1,2,1] 的和最大，为 6 。
示例 2：

输入：nums = [1]
输出：1
示例 3：

输入：nums = [5,4,-1,7,8]
输出：23



```

```java
class Solution {//动态规划
    public int maxSubArray(int[] nums) {
        int pre = 0;
        int maxAns = nums[0];
        for(int x : nums){
            pre = Math.max(pre+x,x);
            maxAns = Math.max(maxAns,pre);
        }
        return maxAns;
    }
}

```


```text
65、给定一个长度为 n 的环形整数数组 nums ，返回 nums 的非空 子数组 的最大可能和 。

环形数组 意味着数组的末端将会与开头相连呈环状。形式上， nums[i] 的下一个元素是 nums[(i + 1) % n] ， nums[i] 的前一个元素是 nums[(i - 1 + n) % n] 。

子数组 最多只能包含固定缓冲区 nums 中的每个元素一次。形式上，对于子数组 nums[i], nums[i + 1], ..., nums[j] ，不存在 i <= k1, k2 <= j 其中 k1 % n == k2 % n 。

 

示例 1：

输入：nums = [1,-2,3,-2]
输出：3
解释：从子数组 [3] 得到最大和 3
示例 2：

输入：nums = [5,-3,5]
输出：10
解释：从子数组 [5,5] 得到最大和 5 + 5 = 10
示例 3：

输入：nums = [3,-2,2,-3]
输出：3
解释：从子数组 [3] 和 [3,-2,2] 都可以得到最大和 3




```

```java
class Solution {
    public int maxSubarraySumCircular(int[] nums) {
        int n = nums.length;
        int[] leftMax = new int[n];

        //对云坐标0处的元素单独处理，冰面考虑子树组为空的情况
        leftMax[0] = nums[0];
        int leftSum = nums[0];
        int pre = nums[0];
        int res = nums[0];
        for(int i = 1;i<n;i++){
            pre = Math.max(pre+nums[i],nums[i]);
            res = Math.max(res,pre);
            leftSum +=nums[i];
            leftMax[i] = Math.max(leftMax[i-1],leftSum);
        }

        //从右到左枚举后缀，固定后缀，选择最大前缀
        int rightSum = 0;
        for(int i = n-1;i>0;i-- ){
            rightSum += nums[i];
            res = Math.max(res,rightSum+leftMax[i-1]);
        }
        return res;

    }
}

```


```text
69、



```

```java


```


```text
70、



```

```java


```

