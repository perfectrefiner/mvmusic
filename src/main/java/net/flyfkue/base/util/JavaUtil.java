package net.flyfkue.base.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

import net.flyfkue.base.log.Log;

public class JavaUtil {
	public static String DEFAULT_CHARSET = "UTF-8";

	public static void main(String[] args) {
		// String from =
		// "E:/workspaces/ssq/bin/net/mbs/app/jquery-1.8.3.min.js";
		// String to = "D:/ssq/jquery-1.8.3.min.js";
		// copyFile(from, to);
		// Log.add(Log.info);
		// Log.i(TAG, JavaUtil.lowerFirstLetter("Tabcd"));
		// Log.i(TAG, JavaUtil.lowerFirstLetter("tabcd"));
		// Log.i(TAG, JavaUtil.lowerFirstLetter("t"));
		// Log.i(TAG, JavaUtil.lowerFirstLetter("_tabcd"));
		// for (int i = 0; i < 100; i++) {
		// List<Integer> d = JavaUtil.genRandomNumberSerailsList(21, 1, 8);
		// Log.i(TAG, d.toString());
		// }

		// String ss = "abc.txt";
		// System.out.println(getFilePrefix(ss, '.'));
		// System.out.println(getFileSuffix(ss, '.'));
		// ss = "abc";
		// System.out.println(getFilePrefix(ss, '.'));
		// System.out.println(getFileSuffix(ss, '.'));

		// String s = "anim%2d.png";
		// System.out.println(s);
		// System.out.println(s.replace("%\\d+d", ""));
		// System.out.println(s.replaceAll("%\\d+d", ""));

		List<String> lst = readFile2List("/test/strings.txt");
		long beg = System.currentTimeMillis();
		int sum = 0;
		for (int i = 0; i < 1000; i++) {
			for (String str : lst) {
				// if (isNum1(str)) sum++; // 76 105 111
				// if (isNum2(str)) sum++; // 76 156 86
				// if (isNum3(str)) sum++; // 47 101 64
				// if (isNum4(str)) sum++; // 776 920 1050
				// if (isNum5(str)) sum++; // 701 1070 800
				// if (isNum6(str)) sum++; // 514 747 493
				// if (isNum7(str)) sum++; // 29 76 28
				// if (isNum8(str)) sum++; // 151 102 127
			}
		}
		long end = System.currentTimeMillis();
		System.out.println(sum);
		System.out.println(end - beg);
	}

	/**
	 * 公因子消除
	 * 
	 * @return
	 */
	private static void cnm_2(int[] nr, ListIterator<Integer> it) {
		while (it.hasPrevious()) {
			it.previous();
		}
		while (it.hasNext()) {
			Integer m = (Integer) it.next();
			label_for: for (int i = 0; i < nr.length; i++) {
				if (nr[i] < 2) {
					continue;
				}
				int n = nr[i];
				if (m > 1 && (m % n == 0)) {
					m = m / n;
					nr[i] = 1;
					if (m > 1) {
						it.set(m);
						// m = run9_12(nr, it);
					} else {
						it.remove();
						break label_for;
					}
				}
			}
		}
	}

	/**
	 * 计算cnm
	 * 
	 * @param n
	 * @param m
	 * @return
	 */
	public static long cnm(int n, int m) {
		int[] nr = new int[m];
		m--;
		ArrayList<Integer> mr = new ArrayList<Integer>(m);
		for (int i = m - 1, idx = 0; i >= 0; i--, idx++) {
			mr.add(i + 2);
		}
		for (int i = 0, j = n; i < nr.length; i++, j--) {
			nr[i] = j;
		}
		// for (int i = 0, idx = m - 1; i < m; i++, idx--) {
		// mr.set(idx, i + 2); //不可用,sizecheck抛出异常
		// }
		ListIterator<Integer> it = mr.listIterator();// 建议ListIterator提供moveToLast和moveToFirst方法
		while (it.hasNext() || it.hasPrevious()) {
			for (int i = 0; i < nr.length; i++) {
				nr[i] = cnm_1(nr[i], it);
			}
			cnm_2(nr, it);
		}

		// JavaUtil.printArray(nr, ",");
		// Log.i(TAG, );
		while (it.hasPrevious()) {
			Log.i(TAG, "----" + it.previous());
		}
		while (it.hasNext()) {
			Log.i(TAG, "----" + it.next());
		}

		// JavaUtil.printArray(nr, ",");
		// Log.i(TAG, );
		// while (it.hasPrevious()) {
		// Log.i(TAG, it.previous());
		// }
		// while (it.hasNext()) {
		// Log.i(TAG, it.next());
		// }

		// Log.i(TAG, "----");
		long ret = 1;
		for (int i = 0; i < nr.length; i++) {
			ret *= nr[i];
		}
		// Log.i(TAG, "====");
		return ret;
	}

	/**
	 * 大数据消除
	 * 
	 * @param n
	 * @param it
	 * @return
	 */
	private static int cnm_1(int n, ListIterator<Integer> it) {
		// System.out.print("run9_11:" + n + "\t");
		// JavaUtil.printArray(it);
		if (n < 2) {
			return n;
		}
		// if (!it.hasNext()) {
		// 如果已经是最后一个元素,建议ListIterator最好加一个moveToFirst,moveToLast方法
		while (it.hasPrevious()) {
			it.previous();
		}
		// }
		while (it.hasNext()) {
			int m = it.next();
			if (m > 1 && (n % m == 0)) {
				n = n / m;
				// System.out.print("before remove:" + n + "\t");
				// JavaUtil.printArray(it);
				it.remove();
				// System.out.print("after remove:" + n + "\t");
				// JavaUtil.printArray(it);
				if (n > 1) {
					n = cnm_1(n, it);
				}
				return n;
			}
		}
		return n;
	}

	private static String TAG = JavaUtil.class.getSimpleName().toString();
	private static Random ran = new Random();

	/**
	 * 小基单栈
	 * 
	 * @param arr
	 *            待排序数组
	 * @param sortAsc
	 *            顺序逆序
	 * @return 修改的arr的引用,可以不用return的
	 */
	public static int[] stackSortLowSingle(int[] arr, boolean sortAsc) {
		if (arr == null || arr.length == 0) {
			return null;
		}
		Stack<Integer> low = new Stack<Integer>(); // low
		low.push(arr[0]);
		int highIdx = 0;
		for (int i = 1; i < arr.length; i++) {
			while (highIdx > 0) {
				if (arr[highIdx - 1] < arr[i]) {
					low.push(arr[--highIdx]);
					continue;
				}
				break;
			}
			while (!low.isEmpty()) {
				if (low.peek() > arr[i]) {
					arr[highIdx++] = low.pop();
					continue;
				}
				break;
			}
			low.push(arr[i]);
		}
		while (!low.isEmpty()) {
			arr[highIdx++] = low.pop();
		}
		if (sortAsc) {
			int len = arr.length;
			for (int i = 0; i < len / 2; i++) {
				int b = arr[i];
				arr[i] = arr[len - i - 1];
				arr[len - i - 1] = b;
			}
		}
		return arr;
	}

	/**
	 * 小基双栈变种
	 * 
	 * @param arr
	 *            待排序数组
	 * @param sortAsc
	 *            顺序逆序
	 * @return 修改的arr的引用,可以不用return的
	 */
	public static int[] stackSortLowDualArr(int[] arr, boolean sortAsc) {
		if (arr == null || arr.length == 0) {
			return null;
		}
		int[] low = new int[arr.length];
		int[] high = new int[arr.length];
		int lowIdx = 1, highIdx = 0;
		low[0] = arr[0];
		for (int i = 1; i < arr.length; i++) {
			while (highIdx > 0) {
				if (high[highIdx - 1] < arr[i]) {
					low[lowIdx++] = high[--highIdx];
					continue;
				}
				break;
			}
			while (lowIdx > 0) {
				if (low[lowIdx - 1] > arr[i]) {
					high[highIdx++] = low[--lowIdx];
					continue;
				}
				break;
			}
			low[lowIdx++] = arr[i];
		}

		if (sortAsc) {
			for (int i = 0; i < lowIdx; i++) {
				arr[i] = low[i];
			}
			for (int i = 0; i < highIdx; i++) {
				arr[arr.length - 1 - i] = high[i];
			}
		} else {
			for (int i = 0; i < lowIdx; i++) {
				arr[i] = high[i];
			}
			for (int i = 0; i < lowIdx; i++) {
				arr[arr.length - 1 - i] = low[i];
			}
		}
		return arr;
	}

	/**
	 * 小基双栈
	 * 
	 * @param arr
	 *            待排序数组
	 * @param sortAsc
	 *            顺序逆序
	 * @return 修改的arr的引用,可以不用return的
	 */
	public static int[] stackSortLowDual(int[] arr, boolean sortAsc) {
		if (arr == null || arr.length == 0) {
			return null;
		}
		Stack<Integer> low = new Stack<Integer>(); // low
		Stack<Integer> high = new Stack<Integer>(); // high
		low.push(arr[0]);
		for (int i = 1; i < arr.length; i++) {
			while (!high.isEmpty()) {
				if (high.peek() < arr[i]) {
					low.push(high.pop());
					continue;
				}
				break;
			}
			while (!low.isEmpty()) {
				if (low.peek() > arr[i]) {
					high.push(low.pop());
					continue;
				}
				break;
			}
			low.push(arr[i]);
		}

		int numL = low.size();
		int numR = high.size();
		if (sortAsc) {
			for (int i = 0; i < numL; i++) {
				arr[numL - 1 - i] = low.pop();
			}
			for (int i = 0; i < numR; i++) {
				arr[numL + i] = high.pop();
			}
		} else {
			for (int i = 0; i < numR; i++) {
				arr[numR - 1 - i] = high.pop();
			}
			for (int i = 0; i < numL; i++) {
				arr[numR + i] = low.pop();
			}
		}
		return arr;
	}

	/**
	 * 双色球用,重力排序
	 * 
	 * @param arr
	 * @param sortAsc
	 * @return
	 */
	public static int[] shuangseqiuSort(int[] arr) {
		if (arr == null || arr.length != 6) {
			return null;
		}
		int min = 1, max = 33;
		for (int i : arr) { // range check
			if (i < min || i > max) {
				return null;
			}
		}
		int[] tmp = new int[33];
		for (int i = 0; i < arr.length; i++) {
			tmp[arr[i] - 1] = arr[i];
		}
		int cnt = 0;
		for (int i : tmp) {
			if (i > 0) {
				arr[cnt++] = i;
			}
		}
		return arr;
	}

	/**
	 * 得到某列
	 * 
	 * @param array
	 * @param rowNum
	 * @return
	 */
	public static String[] getRow(String[][] array, int rowNum) {
		String[] res = new String[array.length];
		for (int i = 0; i < res.length; i++) {
			res[i] = array[i][rowNum];
		}
		return res;
	}

	public static String getFilePrefix(String str, char splitchar) {
		if (str == null) {
			return null;
		}
		int idx = str.lastIndexOf(splitchar);
		if (idx == -1) {
			return str;
		}
		return str.substring(0, idx);
	}

	public static String getFileSuffix(String str, char splitchar) {
		if (str == null) {
			return null;
		}
		int idx = str.lastIndexOf(splitchar);
		if (idx == -1) {
			return str;
		}
		return str.substring(idx);
	}

	public static int getSubStrBetweenMark(String str, String markbeg, String markend) {
		int beg = str.indexOf(markbeg);
		if (beg == -1) { // 文件名没有.res
			// Log.d("filename has no mark!", str, markbeg, beg);
			return -1;
		}
		int end = str.indexOf(".", beg + markbeg.length());
		try {
			return Integer.parseInt(str.substring(beg + markbeg.length(), end));
		} catch (Exception e2) {
			// Log.d("mark has no end!", str, markend, beg, end);
			return -1;
		}
	}

	public static boolean isEmpty(String str, boolean trimstr) {
		if (str == null)
			return true;
		if (trimstr) {
			str = str.trim();
		}
		if (str.length() == 0)
			return true;
		return false;
	}

	public static boolean isNum(String str) {
		int len = str.length();
		for (int i = 0; i < len; i++) {
			if (str.charAt(i) < '0' || str.charAt(i) > '9')
				return false;
		}
		return true;
	}

	// public static boolean isNum1(String str) {
	// char[] ca = str.toCharArray();
	// for (char c : ca) {
	// if (c < '0' || c > '9') return false;
	// }
	// return true;
	// }
	//
	// public static boolean isNum7(String str) {
	// int len = str.length();
	// for (int i = 0; i < len; i++) {
	// if (str.charAt(i) < '0' || str.charAt(i) > '9') return false;
	// }
	// return true;
	// }
	//
	// public static boolean isNum2(String str) {
	// char[] ca = str.toCharArray();
	// for (char c : ca) {
	// if (!Character.isDigit(c)) return false;
	// }
	// return true;
	// }
	//
	// public static boolean isNum3(String str) {
	// int len = str.length();
	// for (int i = 0; i < len; i++) {
	// if (!Character.isDigit(str.charAt(i))) return false;
	// }
	// return true;
	// }
	//
	// public static boolean isNum4(String str) {
	// return str.matches("[0-9]+");
	// }
	//
	// public static boolean isNum5(String str) {
	// return str.matches("^[0-9]+$");
	// }
	//
	// public static boolean isNum6(String str) {
	// return str.matches("\\d+");
	// }
	// public static boolean isNum8(String str) {
	// try {
	// Integer.parseInt(str);
	// return false;
	// } catch (Exception e2) {
	// return true;
	// }
	// }

	/**
	 * 得到某几列
	 * 
	 * @param array
	 * @param rowNum
	 * @return
	 */
	public static String[][] getRow(String[][] array, int... rowNum) {
		String[][] res = new String[array.length][rowNum.length];
		for (int i = 0; i < res.length; i++) {
			for (int j = 0; j < rowNum.length; j++) {
				res[i][j] = array[i][rowNum[j]];
			}
		}
		return res;
	}

	/**
	 * 根据下标在Map中查找指定元素,注意Map无序和有序的问题<br/>
	 * 推荐LinkedHashMap,TreeMap和NavigableMap<br/>
	 * ex. TreeMap<String, List<String>> map = new TreeMap<String,
	 * List<String>>();<br/>
	 * List<String> lst1 = new ArrayList<String>();<br/>
	 * lst1.add("a1");<br/>
	 * lst1.add("b1");<br/>
	 * lst1.add("c1");<br/>
	 * map.put("m1", lst1);<br/>
	 * List<String> lst2 = new ArrayList<String>();<br/>
	 * lst2.add("a2");<br/>
	 * lst2.add("b2"); <br/>
	 * lst2.add("c2"); <br/>
	 * map.put("m2", lst2);<br/>
	 * List<String> lst3 = new ArrayList<String>(); <br/>
	 * lst3.add("a3"); <br/>
	 * lst3.add("b3"); <br/>
	 * lst3.add("c3");<br/>
	 * map.put("m3", lst3);<br/>
	 * Log.i(TAG, JavaUtil.getValue(map, 0)); //a1<br/>
	 * Log.i(TAG, JavaUtil.getValue(map, 2)); //c1<br/>
	 * Log.i(TAG, JavaUtil.getValue(map, 5)); //c2<br/>
	 * Log.i(TAG, JavaUtil.getValue(map, 8)); //c3<br/>
	 * Log.i(TAG, JavaUtil.getValue(map, 9)); //异常<br/>
	 * 
	 * @param map
	 * @param idx
	 * @return
	 */
	public static String getValue(Map<String, List<String>> map, int idx) {
		if (map.isEmpty()) {
			return "?";
		}
		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			String k = it.next();
			List<String> lst = map.get(k);
			if (lst.isEmpty()) {
				throw new RuntimeException("lst为空是个什么情况??");
			}
			if (idx >= lst.size()) {
				idx -= lst.size();
				continue;
			}
			for (String string : lst) {
				if (idx == 0) {
					return string;
				}
				idx--;
			}
		}
		// throw new ArrayIndexOutOfBoundsException("貌似:idx越界,确认下呗!");
		return "?";
	}

	/**
	 * 打印数组,行分割
	 * 
	 * @param lst
	 */
	public static void printArray(List<Integer[]> lst) {
		for (Integer[] integers : lst) {
			for (Integer integer : integers) {
				System.out.print(integer + "\t");
			}
			Log.i(TAG, "\n");
		}
	}

	// public static <E> void printArray(E arr) {
	// for (byte b : arr) {
	// System.out.print(Integer.toHexString(b) + " ");
	// }
	// Log.i(TAG, );
	// }
	// /**
	// * 打印数组,行分割
	// *
	// * @param lst
	// */
	// public static <E> void printArray(List<E> lst) {
	// for (E iis : lst) {
	// // printArray(iis);
	// Log.i(TAG, iis);
	// }
	// }
	/**
	 * 打印数组,空格分割
	 * 
	 * @param arr
	 */
	public static void printArray(int[] arr) {
		for (int b : arr) {
			System.out.print(Integer.toHexString(b) + " ");
		}
		Log.i(TAG, "\n");
	}

	public static void printArray(int[] arr, boolean toHex) {
		if (toHex) {
			printArray(arr);
			return;
		}
		for (int b : arr) {
			System.out.print(b + " ");
		}
		Log.i(TAG, "\n");
	}

	/**
	 * 打印数组,空格分割
	 * 
	 * @param arr
	 */
	public static void printArray(byte[] arr) {
		for (byte b : arr) {
			System.out.print(Integer.toHexString(b) + " ");
		}
		Log.i(TAG, "\n");
	}

	/**
	 * 打印ListIterator,行分割,恢复指针位置
	 * 
	 * @param it
	 */
	public static void printArray(ListIterator<Integer> it) {
		int cnt = 0;
		while (it.hasPrevious()) {
			it.previous();
			cnt++;
		}
		while (it.hasNext()) {
			Integer integer = (Integer) it.next();
			System.out.print(integer + "\t");
		}
		Log.i(TAG, "\n");
		while (it.hasPrevious()) {
			it.previous();
		}
		while (cnt > 0) {
			cnt--;
			it.next();
		}
	}

	/**
	 * List<Object[]>转字符串,\t分割,行分割
	 * 
	 * @param lst
	 * @return
	 */
	public static String getFormatString(List lst) {
		StringBuffer sb = new StringBuffer();
		for (Object o : lst) {
			if (o instanceof Object[]) {
				Object[] integers = (Object[]) o;
				for (int i = 0; i < integers.length - 1; i++) {
					sb.append(integers[i] + "\t");
				}
				sb.append(integers[integers.length - 1] + "\r\n");
			}
		}
		return sb.toString();
	}

	/**
	 * 打印数组,\t分割,行分割
	 * 
	 * @param array
	 */
	public static void printArray(int[][] array) {
		int rowLength = array.length;
		for (int i = 0; i < rowLength; i++) {
			for (int j = 0; j < array[i].length - 1; j++) {
				System.out.print(Integer.toHexString(array[i][j]) + " ");
				// System.out.print(array[i][j] + "\t\t");
			}
			System.out.print(array[i][array[i].length - 1]);
			Log.i(TAG, "\n");
		}
	}

	/**
	 * 打印数组,\t分割,行分割
	 * 
	 * @param array
	 */
	public static void printArray(Integer[][] array) {
		int rowLength = array.length;
		for (int i = 0; i < rowLength; i++) {
			for (int j = 0; j < array[i].length - 1; j++) {
				if (array[i][j] == null) {
					System.out.print(-1 + " ");
				} else {
					System.out.print(Integer.toHexString(array[i][j]) + " ");
				}
				// System.out.print(array[i][j] + "\t\t");
			}
			System.out.print(array[i][array[i].length - 1]);
			Log.i(TAG, "\n");
		}
	}

	/**
	 * 打印数组,\t分割,行分割
	 * 
	 * @param array
	 */
	public static void printArray(String[][] array) {
		int rowLength = array.length;
		for (int i = 0; i < rowLength; i++) {
			for (int j = 0; j < array[i].length - 1; j++) {
				System.out.print(array[i][j] + "\t\t");
			}
			System.out.print(array[i][array[i].length - 1]);
			Log.i(TAG, "\n");
		}
	}

	/**
	 * trim(每行)
	 */
	public static List<String> trim(List<String> lst) {
		List<String> ret = new ArrayList<String>();
		for (String ss : lst) {
			// if(ss.startsWith("0123567-0123568-0123478")){
			// System.out.println("a: "+ss);
			// System.out.println("b: "+ss.trim());
			// }
			ret.add(ss.trim());
		}
		return ret;
	}

	/**
	 * 移除空行
	 */
	public static List<String> removeBlankLine(List<String> lst) {
		List<String> ret = new ArrayList<String>();
		for (String ss : lst) {
			if (ss.trim().length() > 0) {
				ret.add(ss);
			}
		}
		return ret;
	}

	public static String list2Str(List<String> sour, String splitter) {
		StringBuilder sbi = new StringBuilder();
		for (String str : sour) {
			sbi.append(str + splitter);
		}
		return sbi.toString();
	}

	public static int[] str2Int(String sour, String splitter) {
		String[] sa = sour.split(splitter);
		int[] ret = new int[sa.length];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = Integer.valueOf(sa[i]);
		}
		return ret;
	}

	public static List<String> str2List(String sour, String splitter) {
		String[] sa = sour.split(splitter);
		List<String> ret = new ArrayList<String>();
		for (String str : sa) {
			ret.add(str);
		}
		return ret;
	}

	public static List<String> removeComment(List<String> lst) {
		StringBuilder sbi = new StringBuilder();
		for (String str : lst) {
			sbi.append(str + "\r\n");
		}
		String sour = removeComment(sbi.toString());
		return str2List(sour, "\r\n");
	}

	// https://blog.csdn.net/bison2008/article/details/6891074
	/**
	 * 删除注释 /* * /
	 */
	public static String removeComment(String src) {
		StringBuffer sb = new StringBuffer();
		int currentState = -1; // 0=",1=//,2=/*
		for (int i = 0; i < src.length(); i++) {
			char c = src.charAt(i);
			if (c == '\"') {
				if (currentState == -1) {
					currentState = 0;
					sb.append(c);
				} else if (currentState == 0) {
					currentState = -1;
					sb.append(c);
				}
			} else if (c == '/') {
				i++;
				if (i == src.length()) {
					if (currentState != 1 && currentState != 2) {
						sb.append("/");
					}
					break;
				}
				char next = src.charAt(i);

				if (next == '/') {
					if (currentState == -1) {
						currentState = 1;
					} else if (currentState == 0) {
						sb.append("//");
					}
				} else if (next == '*') {
					if (currentState == -1) {
						currentState = 2;
					} else if (currentState == 0) {
						sb.append("/*");
					}
				} else if (next == '\\') {
					if (currentState != 1 && currentState != 2) {
						sb.append("/");
					}
					i--;
				} else if (next == '\\') {
					if (currentState != 1 && currentState != 2) {
						sb.append("/");
					}
					i--;
				} else {
					if (currentState != 1 && currentState != 2) {
						sb.append("/").append(next);
					}
				}
			} else if (c == '*') {
				i++;
				if (i == src.length()) {
					if (currentState != 1 && currentState != 2) {
						sb.append("*");
					}
					break;
				}
				char next = src.charAt(i);

				if (next == '/') {
					if (currentState == 2) {
						currentState = -1;
					} else if (currentState != 1) {
						sb.append("*/");
					}
				} else if (next == '\\') {
					if (currentState != 1 && currentState != 2) {
						sb.append("*");
					}
					i--;
				} else {
					if (currentState != 1 && currentState != 2) {
						sb.append("*").append(next);
					}
				}
			} else if (c == '\n') {
				if (currentState == 1) {
					currentState = -1;
				}
				if (currentState != 2) {
					sb.append("\r\n");
				}
			} else if (c == '\\') {
				i++;
				if (i == src.length()) {
					if (currentState != 1 && currentState != 2) {
						sb.append("\\");
					}
				}
				char next = src.charAt(i);
				if (currentState != 1 && currentState != 2) {
					sb.append("\\").append(next);
				} else {
					i--;
				}
			} else {
				if (currentState != 1 && currentState != 2) {
					sb.append(c);
				}
			}
		}
		return sb.toString();
	}

	/**
	 * trim数组
	 * 
	 * @param array
	 * @return
	 */
	public static String[] trimArray(String[] array) {
		int len = array.length;
		for (int i = 0; i < len; i++) {
			array[i] = array[i].trim();
		}
		return array;
	}

	/**
	 * 转置,外部需要确保数组为方阵,引用方式,无返回值
	 * 
	 * @param mat
	 */
	public static void matrixTransposition(int[][] square) {
		int block = square.length;
		for (int i = 0; i < block; i++) {
			for (int j = i + 1; j < block; j++) {
				square[i][j] = square[i][j] ^ square[j][i];
				square[j][i] = square[i][j] ^ square[j][i];
				square[i][j] = square[i][j] ^ square[j][i];
			}
		}
	}

	/**
	 * 转置,方阵,引用方式,无返回值<br/>
	 * 00-xx
	 * 
	 * @param mat
	 */
	public static void matrixTransposition(Object[][] square) {
		int block = square.length;
		Object tmp;
		for (int i = 0; i < block; i++) {
			for (int j = i + 1; j < block; j++) {
				tmp = square[i][j];
				square[i][j] = square[j][i];
				square[j][i] = tmp;
			}
		}
	}

	/**
	 * 浅copy
	 * 
	 * @param arr
	 * @return
	 */
	// @SuppressWarnings("unchecked")
	public static <T> T[][] copyArray(T[][] arr) {
		final T[][] ret = (T[][]) Array.newInstance(arr[0].getClass(), arr.length);
		// Log.i(TAG, ret+","+ret.length);
		for (int i = 0; i < arr.length; i++) {
			// Log.i(TAG, (ret[i] == null)+":"+(arr[i][0]==null));
			final T[] t = (T[]) Array.newInstance(arr[i][0].getClass(), arr[i].length);
			// Log.i(TAG, t+","+t.length);
			ret[i] = t;
			for (int j = 0; j < arr[i].length; j++) {
				ret[i][j] = arr[i][j];
			}
		}
		return ret;
	}

	/**
	 * 浅copy,方阵
	 * 
	 * @param arr
	 * @return
	 */
	public static Object[][] copySquare(Object[][] square) {
		int block = square.length;
		Object[][] ret = new Object[block][block];
		for (int i = 0; i < block; i++) {
			for (int j = 0; j < block; j++) {
				ret[i][j] = square[i][j];
			}
		}
		return ret;
	}

	/**
	 * 上下镜像
	 * 
	 * @param square
	 */
	public static void matrixMirrorUpdown(Object[][] square) {
		Object tmp;
		int block = square.length;
		int mirrorMiddle = (int) (block / 2);
		int blockSize = block - 1;
		for (int x = 0; x < mirrorMiddle; x++) {
			for (int y = 0; y < block; y++) {
				tmp = square[blockSize - x][y];
				square[blockSize - x][y] = square[x][y];
				square[x][y] = tmp;
			}
		}
	}

	/**
	 * 左右镜像
	 * 
	 * @param square
	 */
	public static void matrixMirrorLeftright(Object[][] square) {
		Object tmp;
		int block = square.length;
		int mirrorMiddle = (int) (block / 2);
		int blockSize = block - 1;
		for (int x = 0; x < block; x++) {
			for (int y = 0; y < mirrorMiddle; y++) {
				tmp = square[x][blockSize - y];
				square[x][blockSize - y] = square[x][y];
				square[x][y] = tmp;
			}
		}
	}

	/**
	 * 逆时针旋转times次,外部需要确保数组为方阵,引用方式,无返回值<br/>
	 * 一次旋转:转置+镜像,或者使用公式
	 * 
	 * @param mat
	 * @param times
	 */
	public static void matrixRotateClockAntiorder(Object[][] square, int times) {
		while (times > 0) {
			matrixTransposition(square); // 转置
			matrixMirrorLeftright(square);// 镜像
			times--;
		}
	}

	/**
	 * 顺时针旋转times次,外部需要确保数组为方阵,引用方式,无返回值<br/>
	 * 一次旋转:转置+镜像,或者使用公式 c=m-ro r=co
	 * 
	 * @param mat
	 * @param times
	 */
	public static void matrixRotateClockorder(Object[][] square, int times) {
		while (times > 0) {
			matrixTransposition(square); // 转置
			matrixMirrorUpdown(square);// 镜像
			times--;
		}
	}

	/**
	 * 数组arr根据randomNumberSerails的指针调换位置,引用方式,无返回值
	 * 
	 * @param arr
	 * @param randomNumberSerails
	 */
	public static void changeArr(int[] arr, int[] randomNumberSerails) {
		int block = arr.length;
		if (block != randomNumberSerails.length) {
			return;
		}
		int[] mark = new int[block]; // row.length
		for (int j = 0; j < block; j++) { // mark.length
			if (mark[j] == 1) { // 已经替换,下一个
				continue;
			}
			int currentIdx = randomNumberSerails[j]; // 当前指针
			if (currentIdx == j) {
				continue;
			}
			while (true) {
				arr[currentIdx] = arr[currentIdx] ^ arr[j];
				arr[j] = arr[currentIdx] ^ arr[j];
				arr[currentIdx] = arr[currentIdx] ^ arr[j];
				mark[currentIdx] = 1;
				currentIdx = randomNumberSerails[currentIdx];
				if (currentIdx == j) { // 如果是自己
					break;
				}
				// arr[i][row[idx]];
			}
		}
	}

	/**
	 * 改变mapkey的值<br/>
	 * 注意:返回的新map和原始的map的顺序一致,但是key变了
	 * 
	 * @param mapoldOrdered
	 * @param newKeys
	 * @return
	 */
	public static <T> Map<Integer, T> changeMapKey(Map<Integer, T> mapoldOrdered, int[] newKeys) {
		Map<Integer, T> mapsnewOrdered = new LinkedHashMap<Integer, T>();
		int idx = 0;
		for (Iterator iterator = mapoldOrdered.values().iterator(); iterator.hasNext();) {
			mapsnewOrdered.put(newKeys[idx++], (T) iterator.next());
		}
		return mapsnewOrdered;
	}

	/**
	 * 改变map顺序<br/>
	 * 注意:1.返回的新map和原始的map的顺序不一致<br/>
	 * 2.pos数组必须是0,1,2...N<br/>
	 * 3.由于第二点,本方法有使用限制,更推荐使用changeMapKey方法<br/>
	 * 
	 * @deprecated
	 * @see changeMapKey
	 * @param mapoldOrdered
	 * @param pos
	 * @return
	 */
	public static <T> Map<Integer, T> changeMapOrder(Map<Integer, T> mapoldOrdered, int[] pos) {
		Map<Integer, T> mapsnewOrdered = new LinkedHashMap<Integer, T>();
		// int newpos = 0;
		for (int i = 0; i < pos.length; i++) {
			mapsnewOrdered.put(i, mapoldOrdered.get(pos[i]));
			// mapsnewOrdered.put(newpos++, mapoldOrdered.get(pos[i]));
		}
		return mapsnewOrdered;
	}

	/**
	 * 生成maxNum个不重复的随机数字,从0开始包括0,maxNum结束不包括maxNum
	 * 
	 * @param maxNum
	 * @return
	 */
	public static int[] genRandomNumberSerails(int maxNum) {
		int[] arr = new int[maxNum];
		List<Integer> lst = new LinkedList<Integer>();
		for (int i = 0; i < maxNum; i++) {
			lst.add(i);
		}
		Random r = new Random();
		for (int i = 0; i < maxNum; i++) {
			int idx = r.nextInt(maxNum - i);
			arr[i] = lst.get(idx);
			lst.remove(idx);
		}
		return arr;
	}

	/**
	 * 生成随机color
	 * 
	 * @param maxNum
	 *            0xffffff或者0xffffffff
	 * @return ex.#f0830e
	 */
	public static String genRandomColor(int seed) {
		Random rand = new Random();
		return "#" + Integer.toHexString(rand.nextInt());
	}

	/**
	 * 生成maxNum个不重复的随机数字,从0开始包括0,maxNum结束不包括maxNum,返回List
	 * 
	 * @param maxNum
	 * @return
	 */
	public static List<Integer> genRandomNumberSerailsList(int maxNum) {
		List<Integer> arr = new ArrayList<Integer>(maxNum);
		List<Integer> lst = new LinkedList<Integer>();
		for (int i = 0; i < maxNum; i++) {
			lst.add(i);
		}
		Random r = new Random();
		for (int i = 0; i < maxNum; i++) {
			int idx = r.nextInt(maxNum - i);
			arr.add(lst.get(idx));
			lst.remove(idx);
		}
		return arr;
	}

	/**
	 * 生成min(maxNum-start,len)个不重复的随机数字,从start开始包括start,maxNum结束不包括maxNum,返回List
	 * 
	 * @param maxNum
	 * @param start
	 * @param len
	 * @return
	 */
	public static List<Integer> genRandomNumberSerailsList(int maxNum, int start, int number) {
		int len, len1 = maxNum - start;
		List<Integer> lst = new LinkedList<Integer>();
		if (len1 > number) {
			len = number;
		} else {
			len = len1;
		}
		if (len <= 0) {
			return lst;
		}
		int[] ret = new int[len1];
		for (int i = 0; i < len1; i++) {
			ret[i] = i;
		}
		Random r = new Random();
		for (int i = 0; i < len; i++) {
			// int idx = r.nextInt(len - i);
			int idx = r.nextInt(len1);
			int tmp = ret[i];
			ret[i] = ret[idx];
			ret[idx] = tmp;
			// 不能用这个,因为下标可能是同一个元素,而a^a=0
			// ret[i] = ret[i] ^ ret[idx];
			// ret[idx] = ret[i] ^ ret[idx];
			// ret[i] = ret[i] ^ ret[idx];
		}
		List<Integer> arr = new ArrayList<Integer>(len);
		for (int i = 0; i < len; i++) {
			arr.add(ret[i] + start);
		}
		return arr;
	}

	/**
	 * 生成maxNum个不重复的随机数字,从start开始包括start,maxNum结束不包括maxNum,返回List
	 * 
	 * @param maxNum
	 * @return
	 */
	public static List<Integer> genRandomNumberSerailsList(int maxNum, int start) {
		int len = maxNum - start;
		List<Integer> lst = new LinkedList<Integer>();
		if (len <= 0) {
			return lst;
		}
		List<Integer> arr = new ArrayList<Integer>(len);
		for (int i = 0; i < len; i++) {
			lst.add(i);
		}
		Random r = new Random();
		for (int i = 0; i < len; i++) {
			int idx = r.nextInt(len - i);
			arr.add(lst.get(idx) + start);
			lst.remove(idx);
		}
		return arr;
	}

	/**
	 * 生成genMaxNum个不重复的随机数字,从0开始包括0,maxNum结束不包括maxNum
	 * 
	 * @param maxNum
	 *            最大值
	 * @param genNum
	 *            最多生成个数
	 * @return
	 */
	public static int[] genRandomNumberSerails(int maxNum, int genNum) {
		if (genNum > maxNum) {
			throw new RuntimeException("不合法:genNum>maxNum");
		}
		if (genNum < 0 || maxNum < 0) {
			throw new RuntimeException("不合法:genNum <0 ||  maxNum<0");
		}
		int[] arr = new int[genNum];
		List<Integer> lst = new LinkedList<Integer>();
		for (int i = 0; i < maxNum; i++) {
			lst.add(i);
		}
		Random r = new Random();
		for (int i = 0; i < genNum; i++) {
			int idx = r.nextInt(maxNum - i);
			arr[i] = lst.get(idx);
			lst.remove(idx);
		}
		return arr;
	}

	/**
	 * 打印数组
	 * 
	 * @param array
	 * @param seperator
	 */
	public static void printArray(String[] array, String seperator) {
		int len = array.length;
		for (int i = 0; i < len - 1; i++) {
			System.out.print(array[i] + "" + seperator);
		}
		System.out.print(array[len - 1]);
	}

	/**
	 * 打印数组
	 * 
	 * @param array
	 * @param seperator
	 */
	public static void printArray(int[] array, String seperator) {
		int len = array.length;
		for (int i = 0; i < len - 1; i++) {
			System.out.print(array[i] + "" + seperator);
		}
		System.out.print(array[len - 1]);
	}

	/**
	 * 打印数组,行分割
	 * 
	 * @param array
	 */
	public static void printArray(String[] array) {
		int len = array.length;
		for (int i = 0; i < len; i++) {
			Log.i(TAG, array[i]);
		}
	}

	/**
	 * 写文件,BufferedWriter方式
	 * 
	 * @param file
	 * @param content
	 * @param append
	 */
	public static void writeFile(String file, String content, boolean append) {
		writeFile(new File(file), content, append);
	}

	public static void writeFile(File f, String content, boolean append) {
		writeFile(f, content, DEFAULT_CHARSET, append);
		// try {
		// if (!f.exists()) {
		// mkParent(f);
		// }
		// BufferedWriter bw = new BufferedWriter(new FileWriter(f, append));
		// bw.write(content);
		// bw.flush();
		// bw.close();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
	}

	public static boolean mkParent(File file) {
		File fp = file.getParentFile();
		if (fp == null)
			return false;
		if (!fp.exists()) {
			fp.mkdirs();
		}
		return true;
	}

	/**
	 * 写流文件
	 * 
	 * @param file
	 * @param ca
	 */
	public static void writeStream(File file, byte[] ca) {
		try {
			mkParent(file);
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			bos.write(ca);
			bos.flush();
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeFile(File file, String content, String charset) {
		writeFile(file, content, charset, false);
	}

	public static void writeFile(String filestr, String content, String charset, boolean append) {
		writeFile(new File(filestr), content, charset, false);
		// try {
		// File file = new File(filestr);
		// mkParent(file);
		// FileOutputStream fos = new FileOutputStream(file, append);
		// OutputStreamWriter osw = new OutputStreamWriter(fos, charset);
		// osw.write(content);
		// osw.flush();
		// osw.close();
		// fos.close();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
	}

	public static void writeFile(File file, String content, String charset, boolean append) {
		try {
			mkParent(file);
			FileOutputStream fos = new FileOutputStream(file, append);
			OutputStreamWriter osw = new OutputStreamWriter(fos, charset);
			osw.write(content);
			osw.flush();
			osw.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 写文件,BufferedWriter方式
	 * 
	 * @param file
	 * @param content
	 */
	public static void writeFile(File file, String content) {
		writeFile(file, content, DEFAULT_CHARSET, false);
		// try {
		// mkParent(file);
		// BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		// bw.write(content);
		// bw.flush();
		// bw.close();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
	}

	/**
	 * 写文件,BufferedWriter方式
	 * 
	 * @param file
	 * @param content
	 */
	public static void writeFile(String file, String content) {
		writeFile(new File(file), content);
	}

	public static void writeFile(String file, String content, String charset) {
		writeFile(new File(file), content, charset);
	}

	public static boolean copyDir(File from, File to, boolean deleteDestfileIfExist) {
		try {
			if (!from.exists()) {
				System.err.println("源文件夹不存在");
				return false;
			}
			if (to.exists()) {
				// t.delete();
				if (deleteDestfileIfExist) {
					delDirWithSub(to);
				}
			}
			if (!to.exists()) {
				to.mkdirs();
			}
			File[] fl = from.listFiles();
			for (File f : fl) {
				if (f.isDirectory()) {
					copyDir(f, new File(to.getAbsolutePath() + "/" + f.getName()), false);
				} else {
					copyFile(f.getAbsolutePath(), to.getAbsolutePath() + "/" + f.getName(), false);
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean copyFile(String from, String to, boolean deleteDestfileIfExist) {
		try {
			File f = new File(from);
			File t = new File(to);
			if (!f.exists()) {
				System.err.println("源文件不存在");
				return false;
			}
			if (t.exists()) {
				// t.delete();
				if (deleteDestfileIfExist) {
					t.delete();
				} else {
					System.err.println("目标文件已存在");
					return false;
				}
			}
			File tp = t.getParentFile();
			if (!tp.exists()) {
				tp.mkdirs();
			}
			FileInputStream fis = new FileInputStream(f);
			BufferedInputStream bis = new BufferedInputStream(fis);
			FileOutputStream fos = new FileOutputStream(t);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			byte[] ba = new byte[5000];
			int len = 1;
			while (len > 0) {
				len = bis.read(ba);
				// Log.i(TAG, len);
				if (len > 0) {
					bos.write(ba, 0, len);
				}
			}
			bos.flush();
			bos.close();
			bis.close();
			fos.close();
			fis.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean copyFile(String from, String to) {
		return copyFile(from, to, false);
	}

	/**
	 * 读流文件
	 * 
	 * @param file
	 * @return
	 */
	public static byte[] readStream(File file) {
		BufferedInputStream bis = null;
		try {
			byte[] b = new byte[(int) file.length()];
			bis = new BufferedInputStream(new FileInputStream(file));
			bis.read(b);
			return b;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				bis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static String readFile(File file, String charset) {
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			StringBuffer sb = new StringBuffer();
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis, charset);
			br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line + "\r\n");
			}
			return sb.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				isr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 读文件,BufferedReader方式
	 * 
	 * @param file
	 * @return
	 */
	public static String readFile(File file) {
		return readFile(file, DEFAULT_CHARSET);
	}

	public static List<String> readFile2List(File file) {
		return readFile2List(file, DEFAULT_CHARSET);
	}

	public static List<String> readFile2List(File file, String charset) {
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			List<String> lst = new ArrayList<String>();
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis, charset);
			br = new BufferedReader(isr);
			String tmp;
			while ((tmp = br.readLine()) != null) {
				lst.add(tmp);
			}
			return lst;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				isr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static List<String> readFile2List(String file, String charset) {
		return readFile2List(new File(file), charset);
	}

	public static List<String> readFile2List(String file) {
		return readFile2List(new File(file));
	}

	/**
	 * 寻找dir目录下的所有文件,只找一层
	 * 
	 * @param dir
	 *            遍历dir下的文件和文件夹,只找一层,不再找文件夹下的文件和文件夹
	 * @param suffix
	 *            后缀,如果为null或者""则不限制
	 * @param matchFile
	 *            找文件
	 * @param matchDir
	 *            找目录
	 * @return
	 */
	public static List<String> listDir(String dir, String suffix, boolean matchFile, boolean matchDir) {
		List<String> ret = new ArrayList<String>();
		List<File> lst = listDir(new File(dir), suffix, matchFile, matchDir);
		if (lst == null)
			return ret;
		for (File file : lst) {
			ret.add(file.getAbsolutePath());
		}
		return ret;
	}

	/**
	 * @see listDir(String dir, String suffix, boolean matchFile, boolean matchDir)
	 */
	public static List<File> listDir(File dir, String suffix, boolean matchFile, boolean matchDir) {
		List<File> ret = new ArrayList<File>();
		if (dir == null || !dir.exists()) {
			return null;
		}
		File[] fl = dir.listFiles();
		for (File file : fl) {
			String fname = file.getName();
			if ((matchFile && file.isFile()) || (matchDir && file.isDirectory())) {
				if (suffix == null || fname.endsWith(suffix)) {
					ret.add(file);
				}
			}
		}
		return ret;
	}

	/**
	 * 删除目录及其子目录,子文件,这个操作不可逆
	 * 
	 * @deprecated 这个操作不可逆,建议使用rename
	 * @return
	 */
	public static boolean delDirWithSub(File dir) {
		File[] lst = dir.listFiles();
		for (File f : lst) {
			if (f.isFile()) {
				f.delete();
			} else {
				delDirWithSub(f);
			}
		}
		dir.delete();
		return true;
	}

	public static boolean rename(String from, String to) {
		return rename(new File(from), new File(to));
	}

	public static boolean rename(File from, File to) {
		if (to.exists()) {
			return false;
		}
		return from.renameTo(to);
	}

	/**
	 * 读文件,BufferedReader方式
	 * 
	 * @param file
	 * @return
	 */
	public static String readFile(String file) {
		return readFile(new File(file));
	}

	public static String readFile(String file, String charset) {
		return readFile(new File(file), charset);
	}

	/**
	 * 排序返回下标
	 * 
	 * @param arr
	 * @return
	 */
	public static int[] sortArrayRetIndexOrder(int[] arr) {
		if (arr == null || arr.length == 0) {
			return null;
		}

		return null;
	}

	/**
	 * 功能描述: checkeStyle
	 * 
	 * @Title: replaceSlash
	 * @Description: 将/ab\\/c\\d/替换成/ab/c/d
	 * @creator pengqianyu
	 * @param str
	 *            待替换字符串
	 * @return String 替换后字符串
	 */
	public static String replaceSlash(String str) {
		str = replaceSlashRight(str);
		str = str.replace("\\\\", "/");
		return str;
	}

	/**
	 * 将/ab\\/c\\d/替换成\ab\c\d
	 */
	public static String replaceSlashRight(String str) {
		str = str.replaceAll("\\\\", "/");
		str = str.replaceAll("/+", "\\\\");
		return str;
	}

	/**
	 * 1.通配符的写法参考msdos *0-n个任意字符 ?1个任意字符<br/>
	 * 2.为了方便修改一个限制 a*e匹配:a:/b/c/d/e ae <br/>
	 * 3.为了统一修改一个限制 a?e匹配a/e
	 * 
	 * @param path
	 *            路径
	 * @param reg
	 *            通配符
	 * @return 0 完全匹配 1路径匹配完毕,通配符未匹配完毕 2路径未匹配完毕,通配符匹配完毕 3路径和通配符中途就不匹配
	 */
	public static int tpfmatch(String path, String tpf) {
		tpf = tpf.replaceAll("\\*\\*", "\\*");
		tpf = tpf.replaceAll("\\?\\*", "\\*");
		tpf = tpf.replaceAll("\\*\\?", "\\*");
		// ??????是合法通配符
		path = path.toLowerCase(Locale.US);
		tpf = tpf.toLowerCase(Locale.US);
		char[] pa = path.toCharArray(), ra = tpf.toCharArray();
		int pi = 0, ri = 0;
		while (pi < pa.length && ri < ra.length) {
			if (ra[ri] == '?' || pa[pi] == ra[ri]) {
				pi++;
				ri++;
				continue;
			}
			if (ra[ri] == '*') {
				if (ri == ra.length - 1) { // 最后一个是通配符
					ri++;
					pi = pa.length;
					break;
				} else { // 通配符在中间,需要向后匹配
					ri++;
					do {
						if (pa[pi] == ra[ri]) {
							pi++;
							ri++;
							break;
						}
						pi++;
						continue;
					} while (pi < pa.length && ri < ra.length);
				}
			}
			return 3; // 11
		}
		if (pi == pa.length) { // 0x
			if (ri == ra.length) { // 00
				return 0;
			}
			return 1; // 01
		} else { // 1x
			if (ri == ra.length) { // 10
				return 2;
			}
			throw new RuntimeException("你是怎么跳出循环的,为什么前面没有跳??"); // 11
		}
	}

	public static String union(String division, List<String> strs) {
		StringBuffer sb = new StringBuffer();
		for (String string : strs) {
			sb.append(string);
		}
		return sb.toString();
	}

	/**
	 * 日期格式化
	 * 
	 * @param ca
	 * @return
	 */
	public static String formatCalendar(Calendar ca) {
		return formatCalendar(ca, "");
	}

	public static String formatDate(Date d) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return df.format(d);
	}

	public static String formatDate(Date d, String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(d);
	}

	/**
	 * 日期格式化
	 * 
	 * @param ca
	 * @param splitLine
	 * @return
	 */
	public static String formatCalendar(Calendar ca, String splitLine) {
		int y = ca.get(Calendar.YEAR);
		int m = ca.get(Calendar.MONTH) + 1;
		int d = ca.get(Calendar.DAY_OF_MONTH);
		StringBuffer sb = new StringBuffer();
		sb.append(y);
		sb.append(splitLine);
		if (m < 10) {
			sb.append(0);
		}
		sb.append(m);
		sb.append(splitLine);
		if (d < 10) {
			sb.append(0);
		}
		sb.append(d);
		return sb.toString();
	}

	public static List<String> signalling(String sa, char splitter) {
		List<String> ret = new ArrayList<String>();
		char[] ca = sa.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ca.length - 1; i++) { // 最后一个不管
			if (ca[i] == splitter) {
				if (ca[i + 1] == splitter) { // 默认前置
					sb.append(splitter);
					i++;
				} else {
					ret.add(sb.toString());
					sb = new StringBuffer();
				}
			} else {
				sb.append(ca[i]);
			}
		}
		sb.append(ca[ca.length - 1]);
		ret.add(sb.toString());
		return ret;
	}

	public static void writeoo(File file, Object... params) throws FileNotFoundException, IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
		for (int i = 0; i < params.length; i++) {
			System.out.println(params[i]);
			oos.writeObject(params[i]);
		}
		oos.flush();
		oos.close();
	}

	public static Object[] readoo(File file, int number) throws Exception {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(file));
			Object[] oa = new Object[number];
			for (int i = 0; i < oa.length; i++) {
				oa[i] = ois.readObject();
			}
			return oa;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public static boolean isUpper(char c) {
		if(c>='A' && c<='Z') return true;
		return false;
	}

	public static boolean isLower(char c) {
		if(c>='a' && c<='z') return true;
		return false;
	}

	public static boolean isDigit(char c) {
		if(c>='0' && c<='9') return true;
		return false;
	}

}
