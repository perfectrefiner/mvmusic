package net.flyfkue.base.util;

import java.util.List;

public class BinaryTree {
	private static SortingUtil su;
	private int idx; // 根节点数据
	private BinaryTree left; // 左子树
	private BinaryTree right; // 右子树

	public BinaryTree(int[] sortcolumns, int idx) {
		this(idx);
		su = new SortingUtil(sortcolumns);
	}

	private BinaryTree(int idx) {
		this.idx = idx;
		left = null;
		right = null;
	}

	public void preOrder(BinaryTree root, List<Integer> lst) { // 先根遍历
		if (root == null) { return; }
		lst.add(root.idx);
		preOrder(root.left, lst);
		preOrder(root.right, lst);
	}

	public void insert(BinaryTree root, int idx, List<String[]> lst) {
		// 向二叉树中插入子节点
		if (su.compare(lst.get(idx), lst.get(root.idx)) > 0) {
			// 二叉树的左节点都比根节点小su.compare(new, current) > 0
			if (root.right == null) {
				root.right = new BinaryTree(idx);
			} else {
				this.insert(root.right, idx, lst);
			}
		} else { // 二叉树的右节点都比根节点大
			if (root.left == null) {
				root.left = new BinaryTree(idx);
			} else {
				this.insert(root.left, idx, lst);
			}
		}
	}
}
