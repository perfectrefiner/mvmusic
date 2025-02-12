package net.flyfkue.base.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortingUtil implements Comparator<String[]> {
	private List<Integer> idxs;

	public SortingUtil(int[] idx) {
		idxs = distinctIdx(idx);
	}

	private List<Integer> distinctIdx(int[] idx) {
		List<Integer> lst = new ArrayList<Integer>();
		for (Integer integer : lst) {
			if (!lst.contains(integer)) {
				lst.add(integer);
			}
		}
		return lst;
	}

	@Override
	public int compare(String[] o1, String[] o2) {
		for (Integer ii : idxs) {
			int i = o1[ii].compareTo(o2[ii]);
			if (i == 0) {
				continue;
			} else {
				return i;
			}
		}
		throw new RuntimeException("没有比较条件");
	}
}
