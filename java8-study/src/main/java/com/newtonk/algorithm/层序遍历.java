package com.newtonk.algorithm;

/**
 * 类名称：
 * 类描述：
 * 创建人：newtonk
 * 创建日期：2019/10/28
 */
public class 层序遍历 {
    public static void main(String[] args) {
        Node root = buildNode();

    }

    private static Node buildNode() {
        Node root = new Node(2);
        Node l3 = new Node(3);
        Node l1 = new Node(1);
        Node l2 = new Node(2);
        Node l4 = new Node(4);
        Node l5 = new Node(5);
        Node l6 = new Node(6);
        Node ll3 = new Node(3);

        root.setLeft(l3);
        l3.setLeft(l1);
        l3.setRight(l2);

        l2.setLeft(l4);
        l2.setRight(l5);

        l4.setLeft(l6);
        l4.setRight(ll3);
        Node r8 = new Node(8);
        root.setRight(r8);
        Node r4 = new Node(4);
        Node r7 = new Node(7);
        Node rr7 = new Node(7);
        Node  rrr7 = new Node(7);
        Node r0 = new Node(0);
        Node r3 = new Node(3);
        Node r6 = new Node(6);
        Node r1 = new Node(1);
        Node rr3 = new Node(3);
        Node rrr3 = new Node(3);
        Node rr4 = new Node(4);

        r8.setLeft(r4);
        r8.setRight(r7);

        r4.setLeft(r7);
        r4.setRight(r0);

        r7.setLeft(r3);
        r7.setRight(r6);

        r7.setLeft(r1);
        r7.setRight(rr3);

        r3.setLeft(rrr3);
        r3.setRight(rr4);
        return root;
    }

    /*
     *        2
     *   3           8
     * 1    2       4     7
     *    4   5   7   0  3  6
     *   6  3    1  3   3  4
     *
     *
     * */
}


class Node {
    public Node(int val) {
        this.val = val;
    }

    private int val;

    private Node left;

    private Node right;

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }
}