package com.uwu.study.algorithm;

import java.util.Deque;
import java.util.LinkedList;

public class AlgorithmTest {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

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

    public static void main(String[] args) {
        int[] post = {9,15,7,20,3};
        int[] in = {9,3,15,20,7};
        AlgorithmTest test = new AlgorithmTest();
        System.out.println(test.buildTree(in,post));
    }



}
