package com.newtonk.algorithm;

/**
 * 类名称：
 * 类描述：
 * @author：qiang.tang
 * 创建日期：2019/10/23
 */
public class reverseLinkedList {


	public static void main(String[] args) {
		LinkedNode head = new LinkedNode(0);
		LinkedNode current = head;
		for (int i = 1; i <=10 ; i++) {
			current.next = new LinkedNode(i);
			current = current.next;
		}

		printNode(head);
		head = reverse(head);
		System.out.println("-------");
		printNode(head);
	}


	private static LinkedNode reverse(LinkedNode head) {
		if(head == null|| head.next ==null){
			return head;
		}
		LinkedNode pre = null;
		LinkedNode current = head;
		LinkedNode tmp = null;
		do{
			tmp = current.next;

			current.next = pre;

			pre = current;
			current = tmp;

		}while(current!=null);

		return pre;
	}



	public static void printNode(LinkedNode linkedNode){
		if(linkedNode == null){
			return ;
		}
		LinkedNode current = linkedNode;
		do{
			System.out.println(current.val);
			current = current.next;
		}while (current!=null);
	}

}

class LinkedNode {
	public int val;
	public LinkedNode(int val){
		this.val = val;
	}
	public LinkedNode next;
}
