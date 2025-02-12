package net.flyfkue.base.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Stack;

import net.flyfkue.base.log.Log;

public class JavaUtil {

	public static int TYPE_JAVA_STRING = 1;
	public static int TYPE_JAVA_SHORT = 2;
	public static int TYPE_JAVA_INTEGER = 3;
	public static int TYPE_JAVA_LONG = 4;
	public static int TYPE_JAVA_DOUBLE = 5;
	public static int TYPE_JAVA_DATE = 6;
	public static int TYPE_JAVA_TIMESTAMP = 7;
	public static int TYPE_JAVA_BOOLEAN = 8;
	/** 验证码类型为仅字母，即大写、小写字母混合 */
	public static final int TYPE_LETTER_ONLY = 6;
	/** 验证码类型为数字、大写字母、小写字母混合 */
	public static final int TYPE_ALL_MIXED = 7;
	/** 验证码类型为数字、大写字母混合 */
	public static final int TYPE_NUM_UPPER = 3;
	/** 验证码类型为数字、小写字母混合 */
	public static final int TYPE_NUM_LOWER = 5;
	/** 验证码类型为仅数字 0~9 */
	public static final int TYPE_NUM_ONLY = 1;
	/** 验证码类型为仅大写字母 */
	public static final int TYPE_UPPER_ONLY = 2;
	/** 验证码类型为仅小写字母 */
	public static final int TYPE_LOWER_ONLY = 4;

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
		long beg = System.currentTimeMillis();
		long sum = 0;
		long cnt = 1000000;
		JavaUtil ju = new JavaUtil();
		for (int i = 0; i < cnt; i++) {
			// // sum += new JavaUtil().test1();
			// // ju.test3(); // 1729
			// ju.test4(); // 17309
			ju.test5();
		}
		long end = System.currentTimeMillis();
		// System.out.println(sum * 1.0 / cnt);
		System.out.println(end - beg);
	}

	public boolean test5() {
		class Test5Pojo {
			private Long rid;
			private String userName;
			private Date crtime = new Date();

			public Long getRid() {
				return rid;
			}

			public void setRid(Long rid) {
				this.rid = rid;
			}

			public String getUserName() {
				return userName;
			}

			public void setUserName(String userName) {
				this.userName = userName;
			}

			public Date getCrtime() {
				return crtime;
			}

			public void setCrtime(Date crtime) {
				this.crtime = crtime;
			}

			public Test5Pojo(Long rid, String userName) {
				super();
				this.rid = rid;
				this.userName = userName;
			}

			@Override
			public String toString() {
				return "Test5Pojo [rid=" + rid + ", userName=" + userName + ", crtime=" + crtime + "]";
			}
		}
		Test5Pojo tp2 = new Test5Pojo(2L, "haha");
		object2Map(tp2); // 4555
		// Test5Pojo tp4 = new Test5Pojo(4L, "aabb");
		// object2Map(tp2);
		// System.out.println(tp2);
		// System.out.println(object2Map(tp2));
		// System.out.println("+++");
		// List<Test5Pojo> lst = new ArrayList<Test5Pojo>();
		// lst.add(tp2);
		// lst.add(tp4);
		// System.out.println(lst);
		// System.out.println(object2Map(lst));
		return true;
	}

	public boolean test4() {
		String s = "getXsdfadfa";
		return s.substring(0, 3).equals("get");
	}

	public boolean test3() {
		String s = "getXsdfadfa";
		// System.out.println(s.charAt(3));
		// System.out.println(s.substring(3));
		return s.startsWith("get");
	}

	public long test2() {
		long beg = System.currentTimeMillis();
		List<Map<String, Object>> lst = new ArrayList<Map<String, Object>>();
		for (int i = 1; i < 10; i++) {
			Map<String, Object> m = new HashMap<String, Object>();
			lst.add(m);
			m.put("rid", i);
			m.put("name", "name" + i);
			if (i == 7) {
				m.put("pid", 3);
				continue;
			}
			if (i == 3) {
				m.put("pid", 0);
				continue;
			}
			m.put("pid", 7);
			// m.put("pid", (i == 7) ? 3 : 7);
		}
		System.out.println(lst);
		System.out.println(list2Tree(lst, "rid", "pid", "childrens"));
		long end = System.currentTimeMillis();
		System.out.println(beg);
		System.out.println(end);
		return end - beg;
	}

	public long test1() {
		long beg = System.currentTimeMillis();
		// System.out.println(81*81); // 6561
		int c = 0;
		for (long i = 0; i < 9999999999L; i++) {
			c = 999999999 % 6561; // 914.2
			// c = 6561%3; // 923.3
		}
		long end = System.currentTimeMillis();
		System.out.println(beg);
		System.out.println(end);
		return end - beg;
	}

	public long test() {
		long beg = System.currentTimeMillis();
		int maxNum = 100001;
		// List<Integer> lst = getPrimeNumber2(maxNum); // 100001 1229 13.7
		// 485.2
		// List<Integer> lst = getPrimeNumber1(maxNum); // 100001 1229 33.5
		// 1941.2
		boolean lst = isPrime1(1234587877); // 0.4
		// boolean lst = isPrime2(1234587877); // 13.9
		// List<Integer> lst = getPrimeNumber2(1234567897,1234587897);
		long end = System.currentTimeMillis();
		System.out.println(beg);
		System.out.println(end);
		System.out.println(end - beg);
		System.out.println(lst);
		// System.out.println(lst.size());
		return end - beg;
	}

	private static String TAG = JavaUtil.class.getSimpleName().toString();
	private static Random ran = new Random();

	/**
	 * 获取类中所有public getXxx方法
	 * 
	 * @param clzz
	 * @return
	 */
	public static List<Method> getPublicGetXxx(Class<? extends Object> clzz) {
		// 经测试,true,false的方式比反射的方式快三倍以上,因此使用上面true,false的方式
		// 6021,8302
		return getMethodsOfGetOrSet(clzz, Modifier.PUBLIC, true);
		// 25047,16153
		// return getMethodsOfGetOrSet(clzz, Modifier.PUBLIC,
		// Fcaller.me(JavaUtil.class, "isGetXxx"));
	}

	/**
	 * 获取类中所有public setXxx方法
	 * 
	 * @param clzz
	 * @return
	 */
	public static List<Method> getPublicSetXxx(Class<? extends Object> clzz) {
		return getMethodsOfGetOrSet(clzz, Modifier.PUBLIC, false);
		// return getMethodsOfGetOrSet(clzz, Modifier.PUBLIC,
		// Fcaller.me(JavaUtil.class, "isSetXxx"));
	}

	/**
	 * 待测试,同getMethodsOfGetOrSet,只是是方法传递
	 * 
	 * @param clzz
	 * @param modifier
	 * @param fc
	 * @return
	 */
	private static List<Method> getMethodsOfGetOrSet(Class<? extends Object> clzz, int modifier, Fcaller fc) {
		List<Method> ret = new ArrayList<Method>();
		Method[] mtds = clzz.getDeclaredMethods();
		for (Method m : mtds) {
			if (matchModifier(m, modifier) && (boolean) fc.call(m)) {
				ret.add(m);
			}
		}
		return ret;
	}

	/**
	 * @param clzz
	 * @param modifier
	 * @param isGetOrSetMethod
	 *            true getXxx, false setXxx
	 * @return
	 */
	private static List<Method> getMethodsOfGetOrSet(Class<? extends Object> clzz, int modifier,
			boolean isGetOrSetMethod) {
		List<Method> ret = new ArrayList<Method>();
		Method[] mtds = clzz.getDeclaredMethods();
		for (Method m : mtds) {
			if (matchModifier(m, modifier) && (isGetOrSetMethod ? isGetXxx(m) : isSetXxx(m))) {
				ret.add(m);
			}
		}
		return ret;
	}

	/**
	 * List<对象>转List<map>
	 * 
	 * @param lst
	 * @return
	 */
	public static List<Map<String, Object>> object2Map(List<?> lst) {
		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
		if (lst == null || lst.isEmpty()) return ret;
		List<Method> gets = getPublicGetXxx(lst.get(0).getClass());
		try {
			for (Object o : lst) {
				ret.add(object2Map(o, gets));
			}
			return ret;
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return ret;
		}
	}

	/**
	 * 对象转map
	 * 
	 * @param o
	 * @return
	 */
	public static Map<String, Object> object2Map(Object o) {
		List<Method> gets = getPublicGetXxx(o.getClass());
		try {
			return object2Map(o, gets);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return new HashMap<String, Object>();
		}
	}

	private static Map<String, Object> object2Map(Object o, List<Method> gets)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Map<String, Object> ret = new HashMap<String, Object>();
		for (Method m : gets) {
			String fieldname = lowerFirstLetter(m.getName().substring(3));
			ret.put(fieldname, m.invoke(o));
		}
		return ret;
	}

	/**
	 * 方法满足setXxx
	 * 
	 * @param mtdname
	 * @return
	 */
	public static boolean isSetXxx(String mtdname) {
		return mtdname.length() > 3 && mtdname.startsWith("set") && isUpper(mtdname.charAt(3));
	}

	/**
	 * 方法满足setXxx
	 * 
	 * @param mtdname
	 * @return
	 */
	public static boolean isSetXxx(Method m) {
		return isSetXxx(m.getName());
	}

	/**
	 * 方法满足getXxx
	 * 
	 * @param mtdname
	 * @return
	 */
	public static boolean isGetXxx(String mtdname) {
		return mtdname.length() > 3 && mtdname.startsWith("get") && isUpper(mtdname.charAt(3));
	}

	/**
	 * 方法满足getXxx
	 * 
	 * @param mtdname
	 * @return
	 */
	public static boolean isGetXxx(Method m) {
		return isGetXxx(m.getName());
	}

	/**
	 * 方法修饰符是否是modifier
	 * 
	 * @param m
	 * @param modifier
	 * @return
	 */
	public static boolean matchModifier(Method m, int modifier) {
		return (m.getModifiers() & modifier) == modifier;
	}

	/**
	 * 是否数字
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isDigit(char c) {
		return c >= '0' && c <= '9';
	}

	/**
	 * 是否小写
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isLower(char c) {
		return c >= 'a' && c <= 'z';
	}

	/**
	 * 是否大写
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isUpper(char c) {
		return c >= 'A' && c <= 'Z';
	}

	/**
	 * 数据库查询出来有层级关系(如树形菜单)的列表数据转树状列表数据
	 * 
	 * @param lst
	 *            数据库查询出来的列表数据
	 * @param kridname
	 *            主键id
	 * @param kpidname
	 *            父节点主键id
	 * @param kchildrenname
	 *            子节点名称
	 * @return ex.<br/>
	 *         <i> [{rid=1, name=name1, pid=7}, {rid=2, name=name2, pid=7}, {rid=3,
	 *         name=name3, pid=0}, {rid=4, name=name4, pid=7}, {rid=5, name=name5,
	 *         pid=7}, {rid=6, name=name6, pid=7}, {rid=7, name=name7, pid=3},
	 *         {rid=8, name=name8, pid=7}, {rid=9, name=name9, pid=7}] </i><br/>
	 *         转化为<br/>
	 *         <i> [{rid=3, name=name3, pid=0, childrens=[{rid=7, name=name7, pid=3,
	 *         childrens=[{rid=1, name=name1, pid=7}, {rid=2, name=name2, pid=7},
	 *         {rid=4, name=name4, pid=7}, {rid=5, name=name5, pid=7}, {rid=6,
	 *         name=name6, pid=7}, {rid=8, name=name8, pid=7}, {rid=9, name=name9,
	 *         pid=7}]}]}] </i>
	 */
	public static List<Map<String, Object>> list2Tree(List<Map<String, Object>> lst, String kridname, //
			String kpidname, String kchildrenname) {
		Map<Object, Map<String, Object>> m = new HashMap<Object, Map<String, Object>>();
		for (Map<String, Object> o : lst) {
			m.put(o.get(kridname), o);
		}
		List<Map<String, Object>> topgfl = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> o : lst) {
			if (m.get(o.get(kpidname)) == null) {
				topgfl.add(o);
			} else {
				Map<String, Object> tm = m.get(o.get(kpidname));
				Object tmpo = tm.get(kchildrenname);
				List<Map<String, Object>> tlst = (tmpo == null) ? new ArrayList<Map<String, Object>>() : //
						(List<Map<String, Object>>) tmpo;
				tlst.add(o);
				tm.put(kchildrenname, tlst);
			}
		}
		return topgfl;
	}

	/**
	 * list2TreeObj功能同list2Tree,区别是list2Tree是List&lt;map>作为参数,list2TreeObj是List&lt;Object>对象作为参数<br/>
	 * list2TreeObj方法是参考list2Tree方法写的,待测试
	 * 
	 * @param lst
	 * @param kridname
	 *            主键id
	 * @param kpidname
	 *            父节点主键id
	 * @param kchildrenname
	 *            pojo中子节点名称,优先查找addKchildrenname(o)方法,如果没有,则使用getKchildrenname()和
	 *            setKchildrenname(List&lt;o>)进行取值和赋值
	 * @return 参考list2Tree,只是list2Tree是map形式,list2TreeObj是对象形式
	 */
	public static List<?> list2TreeObj(List<?> lst, String kridname, String kpidname, String kchildrenname) {
		String getRid = "get" + upperFirstLetter(kridname);
		String getPid = "get" + upperFirstLetter(kpidname);
		String getChildren = "get" + upperFirstLetter(kchildrenname);
		String setChildren = "set" + upperFirstLetter(kchildrenname);
		String addChildren = "add" + upperFirstLetter(kchildrenname);
		Map<Object, Object> m = new HashMap<Object, Object>();
		for (Object o : lst) {
			m.put(invoke(o, getRid), o);
		}
		List<Object> topgfl = new ArrayList<Object>();
		for (Object o : lst) {
			Object tm = m.get(invoke(o, getPid));
			if (tm == null) {
				topgfl.add(o);
			} else {
				if (hasMtd(tm, addChildren, o)) {
					invoke(tm, addChildren, o);
				} else {
					Object tmpo = invoke(tm, getChildren);
					List<Object> tlst = (tmpo == null) ? new ArrayList<Object>() : (List<Object>) tmpo;
					tlst.add(o);
					invoke(tm, setChildren, tlst);
				}
			}
		}
		return topgfl;
	}

	public static boolean hasMtd(Object o, String methodName, Object... args) {
		Class<? extends Object> clzz = o.getClass();
		try {
			Method mtd;
			if (args == null || args.length == 0) {
				mtd = clzz.getDeclaredMethod(methodName);
			} else {
				Class[] parameterTypes = new Class[args.length];
				for (int i = 0; i < args.length; i++) {
					parameterTypes[i] = args[i].getClass();
				}
				mtd = clzz.getDeclaredMethod(methodName, parameterTypes);
			}
			return mtd != null;
		} catch (IllegalArgumentException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static Object invoke(Object o, String methodName, Object... args) {
		Class<? extends Object> clzz = o.getClass();
		try {
			Method mtd;
			if (args == null || args.length == 0) {
				mtd = clzz.getDeclaredMethod(methodName);
			} else {
				Class[] parameterTypes = new Class[args.length];
				for (int i = 0; i < args.length; i++) {
					parameterTypes[i] = args[i].getClass();
				}
				mtd = clzz.getDeclaredMethod(methodName, parameterTypes);
			}
			return mtd.invoke(o, args);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean isPrime1(long no) {
		// List<Integer> ret = new ArrayList<Integer>();
		int square = (int) Math.sqrt(no);
		for (int i = 2; i < square; i++) {
			if (no % i == 0) { return false; }
		}
		return true;
	}

	public static boolean isPrime2(long no) {
		int square = (int) Math.sqrt(no);
		List<Integer> lst = getPrimeNumber2(square);
		for (int i = 0; i < lst.size(); i++) {
			if (no % lst.get(i) == 0) { return false; }
		}
		return true;
	}

	public static List<Integer> getPrimeNumber2(int minNum, int maxNum) {
		List<Integer> ret = new ArrayList<Integer>();
		for (int no = minNum; no < maxNum; no++) {
			if (isPrime1(no)) {
				ret.add(no);
			}
		}
		return ret;
	}

	// 100001 1229 13.7 485.2
	public static List<Integer> getPrimeNumber2(int maxNum) {
		// int sqrtno = (int) Math.sqrt(maxNum) + 1;
		int leastPrime = 2;
		List<Integer> ret = new ArrayList<Integer>();
		ret.add(leastPrime);
		boolean isPrime = true;
		for (int ii = leastPrime + 1; ii < maxNum; ii++) {
			isPrime = true;
			for (int j = 0; j < ret.size(); j++) {
				Integer prime = ret.get(j);
				if (ii % prime == 0) {
					isPrime = false;
					break;
				}
			}
			if (isPrime) {
				ret.add(ii);
			}
		}
		return ret;
	}

	// 100001 1229 33.5 1941.2
	public static List<Integer> getPrimeNumber1(int maxNum) {
		// int sqrtno = (int) Math.sqrt(maxNum) + 1;
		int leastPrime = 2;
		List<Integer> ret = new ArrayList<Integer>();
		ret.add(leastPrime);

		List<Integer> mid = new ArrayList<Integer>();
		for (int i = 2; i < maxNum; i++) {
			mid.add(i);
		}
		// boolean ctn = true;
		while (true) {
			Integer prime = ret.get(ret.size() - 1);
			Iterator<Integer> it = mid.iterator();
			while (it.hasNext()) {
				Integer ii = it.next();
				if (ii % prime == 0) {
					it.remove();
				}
			}
			if (mid.size() < 1) {
				break;
			}
			ret.add(mid.get(0));
			mid.remove(0);
		}
		return ret;
	}

	/**
	 * str.substring(0, str.lastIndexOf(splitchar))
	 * 
	 * @param str
	 * @param splitchar
	 * @return
	 */
	public static String getFilePrefix(String str, char splitchar) {
		if (str == null) { return null; }
		int idx = str.lastIndexOf(splitchar);
		if (idx == -1) { return str; }
		return str.substring(0, idx);
	}

	public static int char2int(String colHeader) {
		// String a2z = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		if (colHeader == null) throw new RuntimeException("colHeader不合法");
		colHeader = colHeader.toUpperCase(Locale.US);
		char[] cs = colHeader.toCharArray();
		int ret = 0;
		for (int i = 0; i < cs.length; i++) {
			ret = ret * 26 + (cs[i] - 'A' + 1);
		}
		return ret;
	}

	/**
	 * str.substring(str.lastIndexOf(splitchar))
	 * 
	 * @param str
	 * @param splitchar
	 * @return
	 */
	public static String getFileSuffix(String str, char splitchar) {
		if (str == null) { return null; }
		int idx = str.lastIndexOf(splitchar);
		if (idx == -1) { return str; }
		return str.substring(idx);
	}

	public static List<String> minus(List<String> lst1, List<String> lst2) {
		List<String> ret = clone(lst1);
		ret.removeAll(lst2);
		return ret;
	}

	public static List<String> cross(List<String> lst1, List<String> lst2) {
		List<String> ret = clone(lst1);
		ret.retainAll(lst2);
		return ret;
	}

	public static List<String> clone(List<String> lst) {
		List<String> ret = new ArrayList<String>();
		for (String str : lst) {
			ret.add(str);
		}
		return ret;
	}

	public static String format(SimpleDateFormat sdf, Date ordertime) {
		if (ordertime == null) return null;
		return sdf.format(ordertime);
	}

	public static String format(Double dd, DecimalFormat df) {
		if (dd == null) return null;
		return df.format(dd.doubleValue());
	}

	public static List<Integer> split2intList(String str, String splitReg) {
		String[] sa = str.split(splitReg);
		List<Integer> ret = new ArrayList<Integer>(sa.length);
		for (int i = 0; i < sa.length; i++) {
			ret.add(Integer.valueOf(sa[i].trim()));
		}
		return ret;
	}

	public static int[] split2intarr(String str, String splitReg) {
		String[] sa = str.split(splitReg);
		int[] ret = new int[sa.length];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = Integer.valueOf(sa[i].trim());
		}
		return ret;
	}

	public static List<String> splitAndSort(String str, String splitReg) {
		String[] sa = str.split(splitReg);
		Arrays.sort(sa);
		List<String> ret = new ArrayList<String>(sa.length);
		for (int i = 0; i < sa.length; i++) {
			ret.add(sa[i]);
		}
		return ret;
	}

	public static boolean isEmpty(String str, boolean trimstr) {
		if (str == null) return true;
		if (trimstr) {
			str = str.trim();
		}
		if (str.length() == 0) return true;
		return false;
	}

	public static String getString(Object o) {
		if (o == null) return "";
		if (o instanceof String) return (String) o;
		return o.toString();
	}

	public static Long getLong(Object o) {
		if (o instanceof BigInteger) return ((BigInteger) o).longValue();
		if (o instanceof Long) return (Long) o;
		if (o instanceof Integer) return ((Integer) o).longValue();
		if (o instanceof BigDecimal) return ((BigDecimal) o).longValue();
		if (o instanceof String) return Long.valueOf((String) o);
		throw new RuntimeException("hjj:Converting o to BigInteger error!" + o.getClass());
	}

	public static BigInteger getBigInteger(Object o) {
		if (o instanceof BigInteger) return (BigInteger) o;
		if (o instanceof Long) return BigInteger.valueOf((Long) o);
		if (o instanceof Integer) return BigInteger.valueOf((Integer) o);
		if (o instanceof BigDecimal) return ((BigDecimal) o).toBigInteger();
		if (o instanceof String) return new BigInteger((String) o);
		throw new RuntimeException("hjj:Converting o to BigInteger error!" + o.getClass());
	}

	public static int setDefaultValInt(Map map, String key, Integer defaultVal, int min, int max) {
		setDefaultValInt(map, key, defaultVal);
		int ii = (int) map.get(key);
		if (ii > min && ii < max) return defaultVal;
		map.put(key, defaultVal);
		return (int) map.get(key);
	}

	public static String setDefaultValStr(Map map, String key, String defaultVal) {
		if (map.get(key) == null) {
			map.put(key, defaultVal);
			return defaultVal;
		}
		return String.valueOf(map.get(key));
	}

	public static int setDefaultValInt(Map map, String key, Integer defaultVal) {
		if (map.get(key) == null) {
			map.put(key, defaultVal);
			return defaultVal;
		}
		String v = String.valueOf(map.get(key)).trim();
		if (v.matches("\\d+")) {
			map.put(key, Integer.valueOf(v));
		} else {
			map.put(key, defaultVal);
		}
		return (int) map.get(key);
	}

	public static long tickDefaultValLong(Map map, String key, Long defaultVal) {
		if (map.get(key) == null) { return defaultVal; }
		String v = String.valueOf(map.get(key)).trim();
		if (v.matches("\\d+")) {
			long lng = Long.valueOf(v);
			if (lng == defaultVal.longValue()) {
				map.remove(key);
			} else {
				map.put(key, lng);
				return lng;
			}
		} else {
			map.remove(key);
		}
		return defaultVal;
	}

	public static long tickDefaultValInt(Map map, String key, Integer defaultVal) {
		if (map.get(key) == null) { return defaultVal; }
		String v = String.valueOf(map.get(key)).trim();
		if (v.matches("\\d+")) {
			int ii = Integer.valueOf(v);
			if (ii == defaultVal.intValue()) {
				map.remove(key);
			} else {
				map.put(key, ii);
				return ii;
			}
		} else {
			map.remove(key);
		}
		return defaultVal;
	}

	public static long setDefaultValLong(Map map, String key, Long defaultVal, long min, long max) {
		setDefaultValLong(map, key, defaultVal);
		long ii = (long) map.get(key);
		if (ii > min && ii < max) return ii;
		map.put(key, defaultVal);
		return (long) map.get(key);
	}

	public static long setDefaultValLong(Map map, String key, Long defaultVal) {
		if (map.get(key) == null) {
			map.put(key, defaultVal);
			return defaultVal;
		}
		String v = String.valueOf(map.get(key)).trim();
		if (v.matches("\\d+")) {
			map.put(key, Long.valueOf(v));
		} else {
			map.put(key, defaultVal);
		}
		return (long) map.get(key);
	}

	public static void convertRgbToGray(int[] pixels) {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = convertRgbToGray(pixels[i]);
		}
	}

	public static int[] stringArr2intArr(String[] sa) {
		if (sa == null) { return null; }
		if (sa.length == 0) { return new int[0]; }
		int[] ia = new int[sa.length];
		for (int i = 0; i < ia.length; i++) {
			ia[i] = Integer.valueOf(sa[i].trim());
		}
		return ia;
	}

	public static List<Integer> stringArr2intList(String[] sa) {
		if (sa == null) { return null; }
		List<Integer> ret = new ArrayList<Integer>();
		if (sa.length == 0) { return ret; }
		for (int i = 0; i < sa.length; i++) {
			ret.add(Integer.valueOf(sa[i].trim()));
		}
		return ret;
	}

	/**
	 * 删除文件和子文件夹
	 * 
	 * @param file
	 * @return
	 */
	public static boolean deleteDir(File file) {
		if (!file.exists()) { return false; }
		if (file.isDirectory()) {
			File[] fl = file.listFiles();
			for (int i = 0; i < fl.length; i++) {
				if (!deleteDir(fl[i])) { return false; }
			}
		}
		return file.delete();
	}

	public static void convertRgbToGray(int[][] arrays) {
		for (int i = 0; i < arrays.length; i++) {
			convertRgbToGray(arrays[i]);
		}
	}

	/**
	 * 灰度值计算
	 * 
	 * @param pixels
	 *            像素
	 * @return int 灰度值
	 */
	public static int convertRgbToGray(int pixels) {
		int red = (pixels >> 16) & 0xFF;
		int green = (pixels >> 8) & 0xFF;
		int blue = (pixels) & 0xFF;
		// rgb转灰度公式
		// http://blog.csdn.net/cool1949/article/details/6649429
		// if (adobe) {
		// return (red ^ 2.2 * 0.2973 + green ^ 2.2 * 0.6274 + blue ^ 2.2 *
		// 0.0753)
		// ^ (1 / 2.2);
		// }
		// return (int) (0.3 * _red + 0.59 * _green + 0.11 * _blue);
		return (red + green << 1 + blue) >> 2;
	}

	/**
	 * 大小不一致直接返回0 <br/>
	 * 计算"汉明距离"（Hamming distance）。<br/>
	 * 如果不相同的数据位不超过5，就说明两张图片很相似；如果大于10，就说明这是两张不同的图片。
	 * 
	 * @param sourceHashCode
	 *            源hashCode
	 * @param hashCode
	 *            与之比较的hashCode
	 */
	public static double hammingCompare(String sourceHashCode, String hashCode) {
		double difference = 0;
		if (sourceHashCode.length() == hashCode.length() && sourceHashCode.length() != 0) {
			int len = 0;
			for (int i = 0; i < sourceHashCode.length(); i++) {
				if (sourceHashCode.charAt(i) == hashCode.charAt(i)) {
					len++;
				}
			}
			difference = (double) len / sourceHashCode.length();
		}
		return difference;
	}

	/**
	 * 大小不一致直接返回0 <br/>
	 * 按bit计算"汉明距离"（Hamming distance）的相似度
	 */
	public static double hammingCompareBit(String sourceHashCode, String hashCode, int radix) {
		double difference = 0;
		int bitnumber = -1;
		if (radix == 16) { // 16进制四位
			bitnumber = 4;
		} else if (radix == 64) { // 64进制四位
			bitnumber = 6;
		}
		if (bitnumber == -1) { throw new RuntimeException("不支持的进制数"); }
		if (sourceHashCode.length() == hashCode.length() && sourceHashCode.length() != 0) {
			int len = 0;
			for (int i = 0; i < sourceHashCode.length(); i++) {
				char cs = sourceHashCode.charAt(i);
				char ch = hashCode.charAt(i);
				len += JavaUtil.getSameBitCount(cs, ch, radix, bitnumber);
			}
			difference = (double) len / sourceHashCode.length() / bitnumber;
		}
		return difference;
	}

	public static int getSameBitCount(char cs, char ch, int radix, int bitnumber) {
		byte is = (byte) char2LiteralInt(cs, radix);
		byte ih = (byte) char2LiteralInt(ch, radix);
		// int len = -1;
		// if (radix == 16) { // 16进制四位
		// len = 4;
		// } else if (radix == 64) { // 64进制四位
		// len = 6;
		// }
		// if (len == -1) {
		// throw new RuntimeException("不支持的进制数");
		// }
		// 抛出-1异常
		int ret = 0;
		int r = is ^ ih;
		for (int i = 0; i < bitnumber; i++) {
			if (((r >> i) & 1) == 0) {
				ret++;
			}
		}
		return ret;
	}

	/**
	 * 默认添加到最后
	 * 
	 * @param num
	 *            被追加的数字
	 * @param appendchar
	 *            追加的字符
	 * @param maxLen
	 *            追加到的长度
	 * @param appendBefore
	 *            true添加到最前, false默认添加到最后
	 * @return
	 */
	public static String bringUp(int num, char appendchar, int maxLen, boolean appendBefore) {
		StringBuffer sb = new StringBuffer();
		sb.append(num);
		while (sb.length() < maxLen) {
			if (appendBefore) {
				sb.insert(0, appendchar);
			} else {
				sb.append(appendchar);
			}
		}
		return sb.toString();
	}

	/**
	 * 矩形区域是否包含点
	 * 
	 * @param rect
	 * @param px
	 * @param py
	 * @return
	 */
	public static boolean isRectContainsPoint(Rect rect, int px, int py) {
		int x1 = rect.left;
		int x2 = rect.right;
		int y1 = rect.top;
		int y2 = rect.bottom;
		if (px > x1 && px < x2 && py > y1 && py < y2) { return true; }
		return false;
	}

	public static void appendSbInt(StringBuffer sb, Integer[] str, String divChar) {
		for (int i = 0; i < str.length - 1; i++) {
			sb.append(str[i]);
			sb.append(divChar);
		}
		sb.append(str[str.length - 1]);
	}

	public static void appendSbInt(StringBuffer sb, List<Integer[]> lst, String columnDiv, String rowDiv) {
		for (int i = 0; i < lst.size(); i++) {
			appendSbInt(sb, lst.get(i), columnDiv);
			sb.append(rowDiv);
		}
	}

	public static void appendSb(StringBuffer sb, String[] str, String divChar) {
		for (int i = 0; i < str.length - 1; i++) {
			sb.append(str[i]);
			sb.append(divChar);
		}
		sb.append(str[str.length - 1]);
	}

	public static void appendSb(StringBuffer sb, List<String> str, String divChar) {
		for (int i = 0; i < str.size() - 1; i++) {
			sb.append(str.get(i));
			sb.append(divChar);
		}
		sb.append(str.get(str.size() - 1));
	}

	/**
	 * 格式化打印List<String[]>形式的结果集
	 * 
	 * @param sb
	 * @param lst
	 * @param columnDiv
	 * @param rowDiv
	 */
	public static void appendSb(StringBuffer sb, List<String[]> lst, String columnDiv, String rowDiv) {
		for (int i = 0; i < lst.size(); i++) {
			appendSb(sb, lst.get(i), columnDiv);
			sb.append(rowDiv);
		}
	}

	public static boolean isRectContainsPoint(RectF rect, int px, int py) {
		float x1 = rect.left;
		float x2 = rect.right;
		float y1 = rect.top;
		float y2 = rect.bottom;
		if (px > x1 && px < x2 && py > y1 && py < y2) { return true; }
		return false;
	}

	/**
	 * 根据percentNumerator在percentDenominator中占有率返回true和false的概率
	 * 
	 * @param percentNumerator
	 * @param percentDenominator
	 * @return
	 */
	public static boolean isEligibility(int percentNumerator, int percentDenominator) {
		if (percentDenominator > percentNumerator) {
			if (percentNumerator < ran.nextInt(percentDenominator)) { return false; }
			return true;
		}
		return true;
	}

	public static int expand(int[] a, int lvall) {
		int[] idx = new int[lvall];
		idx[0] = -1;
		return expand(a, idx, 1, lvall);
	}

	/**
	 * 从字符串str中找到第一个最内层的配对的括号
	 * 
	 * @param str
	 *            原始字符串
	 * @param idx
	 *            int[]{0, 0}存放最外层的配对的括号的两个左右下标
	 * @return null,括号不匹配或str本身为null "",没有找到,此时下标为int[]{0,0}<br/>
	 *         ex:xx)返回"" xx返回"" ""返回"" null返回null (xx)返回xx (xx)y)返回xx (z(xx)y返回xx
	 */
	public static String getInnerMatchedBrackets(String str, int[] idx) {
		if (str == null) return null;
		idx[1] = str.indexOf(')');
		if (idx[1] == -1) { return ""; }
		idx[0] = str.lastIndexOf("(", idx[1]);
		if (idx[0] == -1) { return ""; }
		return str.substring(idx[0] + 1, idx[1]);
	}

	/**
	 * 从字符串str中找到第一个最外层的配对的括号
	 * 
	 * @param str
	 *            原始字符串
	 * @param idx
	 *            int[]{0, 0}存放最外层的配对的括号的两个左右下标
	 * @return null,括号不匹配或str本身为null "",没有找到,此时下标为int[]{0,0}<br/>
	 *         ex:xx)返回"" xx返回"" ""返回"" (xx)返回xx (xx)y)返回xx (z(xx)y返回null null返回null
	 */
	public static String getMatchedBrackets(String str, int[] idx) {
		if (str == null) return null;
		// int[] idx = new int[] { -1, -1 }; // beg, end
		// idx通过参数传进来,方便后边取截取位置
		idx[0] = str.indexOf('(');
		if (idx[0] == -1) { return ""; }
		idx[1] = getRightBracket(str, idx[0] + 1) + 1;
		if (idx[1] < 1 || idx[1] < idx[0]) {
			// 括号不匹配
			return null;
		}
		return str.substring(idx[0] + 1, idx[1] - 1);
		// idx[0] = -1;
		// idx[1] = -1;
		// for (int i = 0; i < str.length(); i++) {
		// if (str.charAt(i) == '(') {
		// idx[0] = i;
		// idx[1] = getRightBracket(str, idx[0] + 1) + 1;
		// if (idx[1] < 1 || idx[1] < idx[0]) {
		// // 括号不匹配
		// return null;
		// }
		// return str.substring(idx[0] + 1, idx[1] - 1);
		// }
		// }
		// return "";
	}

	private static int getRightBracket(String str, int beg) {
		int ly = 1;
		for (int i = beg; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (ch == '(') {
				ly++;
			} else if (ch == ')') {
				ly--;
				if (ly == 0) { return i; }
			}
		}
		return -1;
	}

	/**
	 * @param a
	 *            数组
	 * @param idx
	 *            j=i+1
	 * @param lvcurrent
	 *            当前层数
	 * @param lvall
	 *            for层数
	 * @return
	 */
	private static int expand(int[] a, int[] idx, int lvcurrent, int lvall) {
		int s = 0;
		if (lvcurrent == lvall - 1) {
			// Log.i(TAG, "a:"+idx[lvcurrent]);
			for (int i = idx[lvcurrent - 1] + 1; i < a.length; i++) {
				s += a[i];
				// s+=a[idx[lvcurrent-1]]*a[idx[i]];
			}
			return s;
		} else {
			// Log.i(TAG, "b:"+idx[lvcurrent]);
			for (int i = idx[lvcurrent - 1] + 1; i < a.length; i++) {
				idx[lvcurrent] = i;
				s += a[i] * expand(a, idx, lvcurrent + 1, lvall);
			}
			return s;
		}
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
	 * 大数据消除
	 * 
	 * @param n
	 * @param it
	 * @return
	 */
	private static int cnm_1(int n, ListIterator<Integer> it) {
		// System.out.print("run9_11:" + n + "\t");
		// JavaUtil.printArray(it);
		if (n < 2) { return n; }
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

	private static int[] cnminit(int interact) {
		int[] r = new int[interact];
		for (int i = 0; i < r.length; i++) {
			r[i] = i;
		}
		return r;
	}

	private static boolean hasGtMax(int[] ii, int max) {
		for (int i = 0; i < ii.length; i++) {
			if (ii[i] > max) { return true; }
		}
		return false;
	}

	private static int cnmcalc(int[] ii, int idx, int max) {
		ii[idx]++;
		if (ii[idx] > max) {
			if (idx == 0) {
				return -1;
			} else {
				int tmpidx = idx;
				while (true) {
					if (hasGtMax(ii, max)) {
						tmpidx--;
						cnmcalc(ii, tmpidx, max);
						for (int i = tmpidx; i < ii.length - 1; i++) {
							ii[i + 1] = ii[i] + 1;
						}
					} else {
						break;
					}
				}
			}
		}
		if (ii[idx] > max) { return -1; }
		return ii[idx];
	}

	public static List<int[]> cnmlst(int allcnt, int interactcnt) {
		int loopcnt = (int) JavaUtil.cnm(allcnt, interactcnt);
		List<int[]> ret = new ArrayList<int[]>();
		int[] r = cnminit(interactcnt);
		ret.add(r);
		int[] t = copyArray(r);
		for (int i = 0; i < loopcnt - 1; i++) {
			cnmcalc(t, interactcnt - 1, allcnt - 1);
			ret.add(t);
			r = t;
			t = copyArray(r);
			// System.out.println("-->");
			// print(r);
		}
		// for (int i = 0; i < ret.size(); i++) {
		// printArray(ret.get(i));
		// }
		// System.out.println("ret.size():" + ret.size());
		return ret;
	}

	/**
	 * 小基单栈变种
	 * 
	 * @param arr
	 *            待排序数组
	 * @param sortAsc
	 *            顺序逆序
	 * @return 修改的arr的引用,可以不用return的
	 */
	public static int[] stackSortLowSingleArr(int[] arr, boolean sortAsc) {
		if (arr == null || arr.length == 0) { return null; }
		int[] low = new int[arr.length];
		int lowIdx = 1, highIdx = 0;
		low[0] = arr[0];
		for (int i = 1; i < arr.length; i++) {
			while (highIdx > 0) {
				if (arr[highIdx - 1] < arr[i]) {
					low[lowIdx++] = arr[--highIdx];
					continue;
				}
				break;
			}
			while (lowIdx > 0) {
				if (low[lowIdx - 1] > arr[i]) {
					arr[highIdx++] = low[--lowIdx];
					continue;
				}
				break;
			}
			low[lowIdx++] = arr[i];
		}
		while (lowIdx > 0) {
			arr[highIdx++] = low[--lowIdx];
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
	 * 小基单栈
	 * 
	 * @param arr
	 *            待排序数组
	 * @param sortAsc
	 *            顺序逆序
	 * @return 修改的arr的引用,可以不用return的
	 */
	public static int[] stackSortLowSingle(int[] arr, boolean sortAsc) {
		if (arr == null || arr.length == 0) { return null; }
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
		if (arr == null || arr.length == 0) { return null; }
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
		if (arr == null || arr.length == 0) { return null; }
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
		if (arr == null || arr.length != 6) { return null; }
		int min = 1, max = 33;
		for (int i : arr) { // range check
			if (i < min || i > max) { return null; }
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
		if (map.isEmpty()) { return "?"; }
		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			String k = it.next();
			List<String> lst = map.get(k);
			if (lst.isEmpty()) { throw new RuntimeException("lst为空是个什么情况??"); }
			if (idx >= lst.size()) {
				idx -= lst.size();
				continue;
			}
			for (String string : lst) {
				if (idx == 0) { return string; }
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
		System.out.println();
	}

	public static void printArrayHex(byte[] arr) {
		int cnt = 0;
		for (byte b : arr) {
			cnt++;
			if (cnt % 16 == 1) {
				System.out.println();
				System.out.print(fixLiteral(Integer.toHexString(cnt), 8) + ' ');
			}
			String s = Integer.toHexString(b);
			s = fixLiteral(s, 2);
			s = s.toUpperCase(Locale.US);
			System.out.print(s + " ");
		}
		System.out.println();
	}

	private static String fixLiteral(String s, int fixNo) {
		return fixLiteral(s, fixNo, '0');
	}

	public static String fixLiteral(String str, int fixNo, char appendChar) {
		if (str.length() < fixNo) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < fixNo - str.length(); i++) {
				sb.append(appendChar);
			}
			return sb.toString() + str;
		}
		if (str.length() == fixNo) { return str; }
		return str.substring(str.length() - fixNo);
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

	private static int[] copyArray(int[] s) {
		int[] r = new int[s.length];
		for (int i = 0; i < s.length; i++) {
			r[i] = s[i];
		}
		return r;
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
		if (block != randomNumberSerails.length) { return; }
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
	 * 生成验证码字符串
	 * 
	 * @param type
	 *            验证码类型，参见本类的静态属性
	 * @param length
	 *            验证码长度，大于0的整数
	 * @param exChars
	 *            需排除的特殊字符（仅对数字、字母混合型验证码有效，无需排除则为null）
	 * @return 验证码字符串
	 */
	public static String generateTextCode(int type, int length, String exChars) {
		if (length <= 0) return "";
		StringBuffer code = new StringBuffer();
		int i = 0;
		Random r = new Random();
		switch (type) {
		// 仅数字
		case TYPE_NUM_ONLY:
			while (i < length) {
				int t = r.nextInt(10);
				if (exChars == null || exChars.indexOf(t + "") < 0) {// 排除特殊字符
					code.append(t);
					i++;
				}
			}
			break;
		// 仅字母（即大写字母、小写字母混合）
		case TYPE_LETTER_ONLY:
			while (i < length) {
				int t = r.nextInt(123);
				if ((t >= 97 || (t >= 65 && t <= 90)) && (exChars == null || exChars.indexOf((char) t) < 0)) {
					code.append((char) t);
					i++;
				}
			}
			break;
		// 数字、大写字母、小写字母混合
		case TYPE_ALL_MIXED:
			while (i < length) {
				int t = r.nextInt(123);
				if ((t >= 97 || (t >= 65 && t <= 90) || (t >= 48 && t <= 57))
						&& (exChars == null || exChars.indexOf((char) t) < 0)) {
					code.append((char) t);
					i++;
				}
			}
			break;
		// 数字、大写字母混合
		case TYPE_NUM_UPPER:
			while (i < length) {
				int t = r.nextInt(91);
				if ((t >= 65 || (t >= 48 && t <= 57)) && (exChars == null || exChars.indexOf((char) t) < 0)) {
					code.append((char) t);
					i++;
				}
			}
			break;
		// 数字、小写字母混合
		case TYPE_NUM_LOWER:
			while (i < length) {
				int t = r.nextInt(123);
				if ((t >= 97 || (t >= 48 && t <= 57)) && (exChars == null || exChars.indexOf((char) t) < 0)) {
					code.append((char) t);
					i++;
				}
			}
			break;
		// 仅大写字母
		case TYPE_UPPER_ONLY:
			while (i < length) {
				int t = r.nextInt(91);
				if ((t >= 65) && (exChars == null || exChars.indexOf((char) t) < 0)) {
					code.append((char) t);
					i++;
				}
			}
			break;
		// 仅小写字母
		case TYPE_LOWER_ONLY:
			while (i < length) {
				int t = r.nextInt(123);
				if ((t >= 97) && (exChars == null || exChars.indexOf((char) t) < 0)) {
					code.append((char) t);
					i++;
				}
			}
			break;
		}
		return code.toString();
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
		if (len <= 0) { return lst; }
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
		if (len <= 0) { return lst; }
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
		if (genNum > maxNum) { throw new RuntimeException("不合法:genNum>maxNum"); }
		if (genNum < 0 || maxNum < 0) { throw new RuntimeException("不合法:genNum <0 ||  maxNum<0"); }
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

	public static void printArray(Map<String, String[]> array) {
		Iterator<Entry<String, String[]>> it = array.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String[]> en = it.next();
			System.out.println("key:" + en.getKey());
			JavaUtil.printArray(en.getValue());
		}
		System.out.println();
	}

	public static void printArray(Object[] array) {
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
		try {
			File f = new File(file);
			if (!f.exists()) {
				mkParent(f);
			}
			BufferedWriter bw = new BufferedWriter(new FileWriter(file, append));
			bw.write(content);
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeFile(String file, List<String> content, String charset, boolean append) {
		StringBuffer sb = new StringBuffer();
		for (String str : content) {
			sb.append(str + "\r\n");
		}
		sb.delete(sb.length() - 2, sb.length());
		writeFile(file, sb.toString(), charset, append);
	}

	public static boolean mkDirs(String file) {
		File f = new File(file);
		if (!f.exists()) return f.mkdirs();
		return true;
	}

	public static boolean mkParent(String file) {
		return mkParent(new File(file));
	}

	public static boolean mkParent(File file) {
		File fp = file.getParentFile();
		if (fp == null) return false;
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

	public static void writeFile(File file, String content, String charset) {
		writeFile(file, content, charset, false);
	}

	/**
	 * 写文件,BufferedWriter方式
	 * 
	 * @param file
	 * @param content
	 */
	public static void writeFile(File file, String content) {
		try {
			mkParent(file);
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write(content);
			bw.flush();
			bw.close();
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
	public static void writeFile(String file, String content) {
		writeFile(new File(file), content);
	}

	public static void writeFile(String file, String content, String charset, boolean append) {
		writeFile(new File(file), content, charset, append);
	}

	public static void writeFile(String file, String content, String charset) {
		writeFile(new File(file), content, charset, false);
	}

	public static boolean copyDir(String from, String to, boolean deleteDestfileIfExist) {
		File f = new File(from);
		File t = new File(to);
		if (!f.exists()) {
			System.err.println("源文件夹不存在");
			return false;
		}
		if (t.exists()) {
			if (deleteDestfileIfExist) {
				deleteDir(t);
			} else {
				System.err.println("目标文件夹已存在");
				return false;
			}
		}
		if (f.isDirectory()) {
			t.mkdirs();
			File[] fl = f.listFiles();
			for (File fs : fl) {
				if (!copyDir(f.getAbsolutePath() + "/" + fs.getName(), t.getAbsolutePath() + "/" + fs.getName(),
						deleteDestfileIfExist)) { return false; }
			}
		} else {
			if (!copyFile(f.getAbsolutePath(), t.getAbsolutePath(), deleteDestfileIfExist)) {
				System.err.println(
						MessageFormat.format("复制文件失败:from:{0},to:{1}", f.getAbsolutePath(), t.getAbsolutePath()));
				return false;
			}
		}
		return true;
	}

	public static boolean copyDir(String from, String to) {
		return copyDir(from, to, false);
	}

	public static boolean renameFile(String from, String to, boolean deleteDestfileIfExist) {
		File ffrom = new File(from);
		File fto = new File(to);
		if (!mkParent(fto)) { return false; }
		if (deleteDestfileIfExist) {
			fto.delete();
		}
		return ffrom.renameTo(fto);
	}

	public static boolean renameFile(String from, String to) {
		return renameFile(from, to, false);
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
				if (deleteDestfileIfExist) {
					t.delete();
				} else {
					System.err.println("目标文件已存在");
					return false;
				}
			}
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(t));
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
		try {
			byte[] b = new byte[(int) file.length()];
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			bis.read(b);
			bis.close();
			return b;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static String readFile(File file, String charset) {
		try {
			if (!file.canRead()) {
				System.err.println("cannotRead:" + file.getAbsolutePath());
				return "cannotRead";
			}
			StringBuffer sb = new StringBuffer();
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis, charset);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line + "\r\n");
			}
			isr.close();
			fis.close();
			return sb.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 读文件,BufferedReader方式
	 * 
	 * @param file
	 * @return
	 */
	public static String readFile(File file) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String tmp;
			StringBuffer sb = new StringBuffer();
			while ((tmp = br.readLine()) != null) {
				sb.append(tmp + "\r\n");
			}
			br.close();
			return sb.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static List<String> readFile2List(File file) {
		return readFile2List(file, "UTF-8");
	}

	public static List<String> readFile2List(File file, String charset) {
		try {
			List<String> lst = new ArrayList<String>();
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis, charset);
			BufferedReader br = new BufferedReader(isr);
			String tmp;
			while ((tmp = br.readLine()) != null) {
				lst.add(tmp);
			}
			isr.close();
			fis.close();
			return lst;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static List<String> readFile2List(String file, String charset) {
		return readFile2List(new File(file), charset);
	}

	public static List<String> readFile2List(String file) {
		return readFile2List(new File(file));
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
		if (arr == null || arr.length == 0) { return null; }

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
		// str = str.replaceAll("\\\\", "/");
		str = str.replace("\\", "/");
		str = str.replaceAll("/+", "/");
		return str;
	}

	/**
	 * ex.
	 * System.out.println(parent("/test", '/'));
	 * System.out.println(parent("/test/sss", '/'));
	 * System.out.println(parent("test/sss", '/')); System.out.println(parent("sss",
	 * '/')); 依次返回 空字符串,/test,test,null
	 * 
	 * @param str
	 * @param splitchar
	 * @return
	 */
	public static String parent(String str, char splitchar) {
		int ii = str.lastIndexOf(splitchar);
		if (ii == -1) { return null; }
		return str.substring(0, ii);
	}

	public static String replace(String str, char oldChar, char newChar) {
		str = str.replace(oldChar, newChar);
		return str;
	}

	public static String replace(String str, String oldChar, String newChar) {
		str = str.replace(oldChar, newChar);
		return str;
	}

	public static String tickSpecial(String str) {
		if (str == null) return null;
		char[] sa = str.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < sa.length; i++) {
			if (checkRange(sa[i], 'a', 'z') || checkRange(sa[i], 'A', 'Z') || checkRange(sa[i], '0', '9')) {
				sb.append(sa[i]);
			}
		}
		return sb.toString();
	}

	public static boolean checkRange(char val, char min, char max) {
		if (val >= min && val <= max) { return true; }
		return false;
	}

	/**
	 * 1.通配符的写法参考msdos *0-n个任意字符 ?1个任意字符<br/>
	 * 2.为了方便修改一个限制 a*e匹配:a:/b/c/d/e ae <br/>
	 * 3.为了统一修改一个限制 a?e匹配a/e
	 * 
	 * @deprecated 本函数有错,\\?\\*和\\*\\?应该是匹配>=1而不是>=0
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

	/**
	 * 查询出来结果集了,有时候需要临时调整一下排列顺序,再查询一次数据库实际上有点多余, linq感觉挺麻烦的,自己写个吧,暂时只对String[]进行排序
	 * 
	 * @param lst
	 * @param sortIdx
	 * @return
	 */
	public static List<Integer> sort(List<String[]> lst, int... sortIdx) {
		BinaryTree bt = new BinaryTree(sortIdx, 0); // 二叉树内放下标
		for (int i = 1; i < lst.size(); i++) {
			String[] arr = lst.get(i);
			if (arr == null) { throw new RuntimeException("记录为空??,你是怎么查出来的??"); }
			bt.insert(bt, i, lst);
		}
		List<Integer> orders = new ArrayList<Integer>();
		bt.preOrder(bt, orders);
		return orders;
	}

	/**
	 * 来自https://blog.csdn.net/qq_35713827/article/details/109203561
	 * Byte字节转Hex
	 * @param b 字节
	 * @return Hex
	 */
	public static String byteToHex(byte b) {
		String hexString = Integer.toHexString(b & 0xFF);
		// 由于十六进制是由0~9、A~F来表示1~16，所以如果Byte转换成Hex后如果是<16,就会是一个字符（比如A=10），通常是使用两个字符来表示16进制位的,
		// 假如一个字符的话，遇到字符串11，这到底是1个字节，还是1和1两个字节，容易混淆，如果是补0，那么1和1补充后就是0101，11就表示纯粹的11
		if (hexString.length() < 2) {
			hexString = new StringBuilder(String.valueOf(0)).append(hexString).toString();
		}
		return hexString.toUpperCase();
	}

	/**
	 * 来自https://blog.csdn.net/qq_35713827/article/details/109203561
	 * 字节数组转Hex
	 * @param bytes 字节数组
	 * @return Hex
	 */
	public static String byteToHex(String splitter, byte... bytes) {
		return byteToHex(splitter, bytes, 0, bytes.length);
		// StringBuilder sbi = new StringBuilder();
		// if (splitter == null) splitter = "";
		// if (bytes != null && bytes.length > 0) {
		// sbi.append(byteToHex(bytes[0]));
		// for (int i = 1; i < bytes.length; i++) {
		// sbi.append(splitter + byteToHex(bytes[i]));
		// }
		// }
		// return sbi.toString();
	}

	public static String byteToHex(String splitter, int numPerLine, byte[] bytes) {
		StringBuilder sbi = new StringBuilder();
		int beg = 0, end = bytes.length;
		while (beg < end) {
			sbi.append(JavaUtil.byteToHex(" ", bytes, beg, beg + numPerLine));
			beg += numPerLine;
			sbi.append("\r\n");
		}
		return sbi.toString();
	}

	public static String byteToHex(String splitter, byte[] bytes, int beg, int end) {
		StringBuilder sbi = new StringBuilder();
		if (splitter == null) splitter = "";
		if (bytes == null || bytes.length == 0) { return sbi.toString(); }
		if (beg >= bytes.length) { return sbi.toString(); }
		if (end >= bytes.length) {
			end = bytes.length;
		}
		sbi.append(byteToHex(bytes[beg]));
		beg++;
		while (beg < end) {
			sbi.append(splitter + byteToHex(bytes[beg]));
			beg++;
		}
		return sbi.toString();
	}

	/**
	 * 来自https://blog.csdn.net/qq_35713827/article/details/109203561
	 * Hex转Byte字节
	 * @param hex 十六进制字符串
	 * @return 字节
	 */
	public static byte hexToByte(String hex) {
		return (byte) Integer.parseInt(hex, 16);
	}

	/**
	 * 来自https://blog.csdn.net/qq_35713827/article/details/109203561
	 * Hex转Byte字节数组
	 * @param hex 十六进制字符串
	 * @return 字节数组
	 */
	public static byte[] hexToBytes(String splitter, String hex) {
		if (splitter != null && splitter.length() > 0) {
			hex = hex.replace(splitter, "");
		}
		int hexLength = hex.length();
		byte[] result;
		// 判断Hex字符串长度，如果为奇数个需要在前边补0以保证长度为偶数
		// 因为Hex字符串一般为两个字符，所以我们在截取时也是截取两个为一组来转换为Byte。
		if (hexLength % 2 == 1) {
			// 奇数
			hexLength++;
			hex = "0" + hex;
		}
		result = new byte[(hexLength / 2)];
		for (int i = 0, j = 0; i < hexLength; i += 2, j++) {
			result[j] = hexToByte(hex.substring(i, i + 2));
		}
		return result;
	}

	public static byte[] hexToBytes(String hex) {
		return hexToBytes(null, hex);
	}

	/**
	 * 26+26+10=62
	 * 
	 * @param binary
	 * @return
	 */
	public static char binaryTo64Hex(int binary) {
		if (binary < 0 || binary > 63) {
			Log.i(TAG, binary);
			return ' ';
		}
		if (binary < 10) { return (char) (binary + '0'); }
		if (binary < 36) { return (char) (binary - 10 + 'a'); }
		if (binary < 62) { return (char) (binary - 36 + 'A'); }
		if (binary == 62) { return '#'; }
		if (binary == 63) { return '$'; }
		return ' ';
	}

	public static int char2LiteralInt(char c, int radix) {
		if (radix == 16) { // 16进制
			return char2LiteralInt16radix(c);
		}
		if (radix == 64) { // 64进制
			return char2LiteralInt64radix(c);
		}
		return -1;
	}

	/**
	 * 0-f的字符转换为真正的字面义0-63
	 * 
	 * @param c
	 * @return
	 */
	public static int char2LiteralInt64radix(char c) {
		if (c >= '0' && c <= '9') { return c - '0'; }
		if (c >= 'a' && c <= 'z') { return c - 'a' + 10; }
		if (c >= 'A' && c <= 'Z') { return c - 'A' + 36; }
		if (c == '#') { return 62; }
		if (c == '$') { return 63; }
		return -1;
	}

	/**
	 * 0-f的字符转换为真正的字面义0-15
	 * 
	 * @param c
	 * @return
	 */
	public static int char2LiteralInt16radix(char c) {
		if (c >= '0' && c <= '9') { return c - '0'; }
		if (c >= 'A' && c <= 'F') { return c - 'A' + 10; }
		if (c >= 'a' && c <= 'f') { return c - 'a' + 10; }
		return -1;
	}

	/**
	 * 二进制转为十六进制
	 * 
	 * @param int
	 *            binary
	 * @return char hex
	 */
	public static char binaryTo16Hex(int binary) {
		if (binary < 0 || binary > 15) { return ' '; }
		if (binary < 10) { return (char) (binary + '0'); }
		return (char) (binary - 10 + 'a');
	}

	/**
	 * 根据dots复制[beg,end)行
	 * 
	 * @param dots
	 * @param beg
	 * @param end
	 * @return
	 */
	public static int[][] extractRow(int[][] dots, int beg, int end) {
		int[][] ret = new int[end - beg][];
		for (int i = beg; i < end; i++) {
			ret[i - beg] = dots[i];
		}
		return ret;
	}

	/**
	 * 根据dots复制[beg,end)列
	 * 
	 * @see extractRow
	 * @param dots
	 * @param beg
	 * @param end
	 * @return
	 */
	public static int[][] extractCol(int[][] dots, int beg, int end) {
		int[][] ret = new int[dots.length][end - beg];
		for (int i = 0; i < ret.length; i++) {
			for (int j = 0; j < ret[i].length; j++) {
				ret[i][j] = dots[i][j + beg];
			}
		}
		return ret;
	}

	/**
	 * 获取clzz的绝对路径
	 * 
	 * @param clzz
	 * @return
	 */
	public static String getAbsolutePathOfClass(Class<?> clzz, boolean getParent) {
		String classNamePath = clzz.getName().replace('.', '/') + ".class";
		URL url = clzz.getClassLoader().getResource(classNamePath);
		// String path = StringUtils.replace(url.getPath(), "%20", " ");
		String path = url.getPath().replace("%20", " ");
		if (getParent) { return JavaUtil.replaceSlash(new File(path).getParent() + '/'); }
		return JavaUtil.replaceSlash(new File(path).getPath());
	}

	/**
	 * 首字母小s写 ex. Log.i(TAG, JavaUtil.lowerFirstLetter("Tabcd"));<br/>
	 * Log.i(TAG, JavaUtil.lowerFirstLetter("tabcd"));<br/>
	 * Log.i(TAG, JavaUtil.lowerFirstLetter("t"));<br/>
	 * Log.i(TAG, JavaUtil.lowerFirstLetter("_tabcd"));<br/>
	 * 结果为: <br/>
	 * tabcd<br/>
	 * tabcd<br/>
	 * t<br/>
	 * _tabcd<br/>
	 * 
	 * @param str
	 * @return
	 */
	public static String lowerFirstLetter(String str) {
		if (str == null) { return str; }
		str = str.trim();
		if (str.length() == 0) { return str; }
		StringBuffer ret = new StringBuffer();
		char[] chs = str.toCharArray();
		int minus = 'a' - 'A';
		if (chs[0] >= 'A' && chs[0] <= 'Z') {
			ret.append((char) (chs[0] + minus));
		} else {
			ret.append(chs[0]);
		}
		for (int i = 1; i < chs.length; i++) {
			ret.append(chs[i]);
		}
		return ret.toString();
	}

	/**
	 * 首字母大写 ex. Log.i(TAG, JavaUtil.upperFirstLetter("Tabcd"));<br/>
	 * Log.i(TAG, JavaUtil.upperFirstLetter("tabcd"));<br/>
	 * Log.i(TAG, JavaUtil.upperFirstLetter("t"));<br/>
	 * Log.i(TAG, JavaUtil.upperFirstLetter("_tabcd"));<br/>
	 * 结果为: <br/>
	 * Tabcd<br/>
	 * Tabcd<br/>
	 * T<br/>
	 * _tabcd<br/>
	 * 
	 * @param str
	 * @return
	 */
	public static String upperFirstLetter(String str) {
		if (str == null) { return str; }
		str = str.trim();
		if (str.length() == 0) { return str; }
		StringBuffer ret = new StringBuffer();
		char[] chs = str.toCharArray();
		int minus = 'a' - 'A';
		if (chs[0] >= 'a' && chs[0] <= 'z') {
			ret.append((char) (chs[0] - minus));
		} else {
			ret.append(chs[0]);
		}
		for (int i = 1; i < chs.length; i++) {
			ret.append(chs[i]);
		}
		return ret.toString();
	}

	/**
	 * 下划线转驼峰 ex. Log.i(TAG, JavaUtil.underline2Camel("Ta1bcd"));<br/>
	 * Log.i(TAG, JavaUtil.underline2Camel("T_ab2cd"));<br/>
	 * Log.i(TAG, JavaUtil.underline2Camel("_Tab3cd"));<br/>
	 * Log.i(TAG, JavaUtil.underline2Camel("_tabc4d"));<br/>
	 * Log.i(TAG, JavaUtil.underline2Camel("__t5abcd"));<br/>
	 * Log.i(TAG, JavaUtil.underline2Camel("Tabcd_6"));<br/>
	 * Log.i(TAG, JavaUtil.underline2Camel("Tab_c7d_"));<br/>
	 * 结果为:<br/>
	 * Ta1bcd<br/>
	 * TAb2cd<br/>
	 * Tab3cd<br/>
	 * Tabc4d<br/>
	 * T5abcd<br/>
	 * Tabcd6<br/>
	 * TabC7d
	 * 
	 * 
	 * @param str
	 * @return
	 */
	public static String underline2Camel(String str) {
		String[] sa = str.split("_");
		String res = sa[0];
		for (int i = 1; i < sa.length; i++) {
			res += upperFirstLetter(sa[i]);
		}
		return res;
	}

	/**
	 * AfterAll_All_all_的形式转化为After_all_all_all_的形式<br/>
	 * Log.i(TAG, JavaUtil
	 * .camel2Underline("TAnnouncementManagementMapper.xml",true));<br/>
	 * Log.i(TAG, JavaUtil
	 * .camel2Underline("TAnnouncement_ManagementMapper.xml",true));<br/>
	 * Log.i(TAG, JavaUtil
	 * .camel2Underline("TAnnouncemen_tManagementMapper.xml",true));<br/>
	 * Log.i(TAG, JavaUtil
	 * .camel2Underline("tAnnouncementManagementMapper_.xml",true));<br/>
	 * Log.i(TAG, JavaUtil
	 * .camel2Underline("TAnnouncementManagementMapper.xml",false));<br/>
	 * Log.i(TAG, JavaUtil
	 * .camel2Underline("TAnnouncement_ManagementMapper.xml",false));<br/>
	 * Log.i(TAG, JavaUtil
	 * .camel2Underline("TAnnouncemen_tManagementMapper.xml",false));<br/>
	 * Log.i(TAG, JavaUtil
	 * .camel2Underline("tAnnouncementManagementMapper_.xml",false));<br/>
	 * 结果为: t_announcement_management_mapper.xml<br/>
	 * t_announcement__management_mapper.xml<br/>
	 * t_announcemen_t_management_mapper.xml<br/>
	 * t_announcement_management_mapper_.xml<br/>
	 * t_announcement_management_mapper.xml<br/>
	 * t_announcement_management_mapper.xml<br/>
	 * t_announcemen_t_management_mapper.xml<br/>
	 * t_announcement_management_mapper_.xml<br/>
	 * 
	 * 
	 * @param str
	 * @param underlineUppercaseAfterUnderLine
	 * @return
	 */
	public static String camel2Underline(String str, boolean underlineUppercaseAfterUnderLine) {
		if (str == null) { return str; }
		str = str.trim();
		if (str.length() == 0) { return str; }
		StringBuffer ret = new StringBuffer();
		char[] chs = str.toCharArray();
		int minus = 'A' - 'a';
		if (chs[0] >= 'A' && chs[0] <= 'Z') { // 如果首字母大写,直接转成小写
			ret.append((char) (chs[0] - minus));
		} else {
			ret.append(chs[0]);
		}
		for (int i = 1; i < chs.length; i++) {
			if (chs[i] >= 'A' && chs[i] <= 'Z') {
				chs[i] -= minus; // 现将其转为小写
				char c = ret.charAt(ret.length() - 1);
				// Log.i(TAG, c);
				if (c >= 'a' && c <= 'z') { // 如果前面是字母
					ret.append('_');
				} else {
					if (underlineUppercaseAfterUnderLine) {
						ret.append('_');
					}
				}
			}
			ret.append(chs[i]);
		}
		return ret.toString();
	}

	/**
	 * 数组的值都加一个incValue
	 * 
	 * @param a
	 *            数组
	 * @param incValue
	 *            增加一个值
	 * @param consta
	 *            true不能破坏a false直接改变a
	 * @return
	 */
	public static int[] arrayAdd(int[] a, int incValue, boolean consta) {
		if (consta) { // 不能破坏a
			int[] r = new int[a.length];
			for (int j = 0; j < r.length; j++) {
				r[j] = a[j] + incValue;
			}
			return r;
		}
		for (int j = 0; j < a.length; j++) {
			a[j] += incValue;
		}
		return a;
	}

	public static void arrayAdd(int[] a, int i) {
		arrayAdd(a, i, false);
	}

	public static class CountArrayOperator {
		public final static int eq = 1;
		public final static int neq = 2;
		public final static int gt = 4;
		public final static int lt = 8;
		public final static int ge = 16;
		public final static int le = 32;

		private static <T extends Comparable<T>> Map<Integer, Point> countArrayNeq(T[][] arr, T eqVal) {
			int sum = 0;
			Map<Integer, Point> map = new LinkedHashMap<Integer, Point>();
			for (int i = 0; i < arr.length; i++) {
				for (int j = 0; j < arr[i].length; j++) {
					if (eqVal == null) {
						if (arr[i][j] != null) {
							map.put(sum++, new Point(i, j));
						}
					} else {
						if (eqVal.compareTo(arr[i][j]) != 0) {
							map.put(sum++, new Point(i, j));
						}
					}
				}
			}
			return map;
		}

		private static <T extends Comparable<T>> Map<Integer, Point> countArrayEq(T[][] arr, T eqVal) {
			int sum = 0;
			Map<Integer, Point> map = new LinkedHashMap<Integer, Point>();
			for (int i = 0; i < arr.length; i++) {
				for (int j = 0; j < arr[i].length; j++) {
					if (eqVal == null) {
						if (arr[i][j] == null) {
							map.put(sum++, new Point(i, j));
						}
					} else {
						if (eqVal.compareTo(arr[i][j]) == 0) {
							map.put(sum++, new Point(i, j));
						}
					}
				}
			}
			return map;
		}

		private static <T extends Comparable<T>> Map<Integer, Point> countArrayGt(T[][] arr, T eqVal) {
			int sum = 0;
			Map<Integer, Point> map = new LinkedHashMap<Integer, Point>();
			for (int i = 0; i < arr.length; i++) {
				for (int j = 0; j < arr[i].length; j++) {
					if (arr[i][j] == null) {
						if (eqVal != null) {
							if (eqVal.compareTo(arr[i][j]) <= 0) {
								map.put(sum++, new Point(i, j));
							}
						}
					} else {
						if (arr[i][j].compareTo(eqVal) > 0) {
							map.put(sum++, new Point(i, j));
						}
					}
				}
			}
			return map;
		}

		private static <T extends Comparable<T>> Map<Integer, Point> countArrayLt(T[][] arr, T eqVal) {
			int sum = 0;
			Map<Integer, Point> map = new LinkedHashMap<Integer, Point>();
			for (int i = 0; i < arr.length; i++) {
				for (int j = 0; j < arr[i].length; j++) {
					if (arr[i][j] == null) {
						if (eqVal != null) {
							if (eqVal.compareTo(arr[i][j]) >= 0) {
								map.put(sum++, new Point(i, j));
							}
						}
					} else {
						if (arr[i][j].compareTo(eqVal) < 0) {
							map.put(sum++, new Point(i, j));
						}
					}
				}
			}
			return map;
		}

		private static <T extends Comparable<T>> Map<Integer, Point> countArrayLe(T[][] arr, T eqVal) {
			int sum = 0;
			Map<Integer, Point> map = new LinkedHashMap<Integer, Point>();
			for (int i = 0; i < arr.length; i++) {
				for (int j = 0; j < arr[i].length; j++) {
					if (arr[i][j] == null) {
						if (eqVal != null) {
							if (eqVal.compareTo(arr[i][j]) > 0) {
								map.put(sum++, new Point(i, j));
							}
						}
					} else {
						if (arr[i][j].compareTo(eqVal) <= 0) {
							map.put(sum++, new Point(i, j));
						}
					}
				}
			}
			return map;
		}

		private static <T extends Comparable<T>> Map<Integer, Point> countArrayGe(T[][] arr, T eqVal) {
			int sum = 0;
			Map<Integer, Point> map = new LinkedHashMap<Integer, Point>();
			for (int i = 0; i < arr.length; i++) {
				for (int j = 0; j < arr[i].length; j++) {
					if (arr[i][j] == null) {
						if (eqVal != null) {
							if (eqVal.compareTo(arr[i][j]) < 0) {
								map.put(sum++, new Point(i, j));
							}
						}
					} else {
						if (arr[i][j].compareTo(eqVal) >= 0) {
							map.put(sum++, new Point(i, j));
						}
					}
				}
			}
			return map;
		}
	}

	public static Map<Integer, Point> countArray(Integer[][] arr, Integer eqVal, int operator) {
		if (operator == CountArrayOperator.eq) { return CountArrayOperator.countArrayEq(arr, eqVal); }
		if (operator == CountArrayOperator.neq) { return CountArrayOperator.countArrayNeq(arr, eqVal); }
		if (operator == CountArrayOperator.gt) { return CountArrayOperator.countArrayGt(arr, eqVal); }
		if (operator == CountArrayOperator.lt) { return CountArrayOperator.countArrayLt(arr, eqVal); }
		if (operator == CountArrayOperator.le) { return CountArrayOperator.countArrayLe(arr, eqVal); }
		if (operator == CountArrayOperator.ge) { return CountArrayOperator.countArrayGe(arr, eqVal); }
		return new HashMap<Integer, Point>();
	}

	public static <T extends Comparable<T>> Map<Integer, Point> countArray(T[][] arr, T eqVal, int operator) {
		if (operator == CountArrayOperator.eq) { return CountArrayOperator.countArrayEq(arr, eqVal); }
		if (operator == CountArrayOperator.neq) { return CountArrayOperator.countArrayNeq(arr, eqVal); }
		if (operator == CountArrayOperator.gt) { return CountArrayOperator.countArrayGt(arr, eqVal); }
		if (operator == CountArrayOperator.lt) { return CountArrayOperator.countArrayLt(arr, eqVal); }
		if (operator == CountArrayOperator.le) { return CountArrayOperator.countArrayLe(arr, eqVal); }
		if (operator == CountArrayOperator.ge) { return CountArrayOperator.countArrayGe(arr, eqVal); }
		return new HashMap<Integer, Point>();
	}

	/**
	 * 判断ca的星期是否在day指定的星期中,<br/>
	 * 由于星期1,2,3,4,5,6,7对应1,2,3,4,5,6,0,所以传入的day中7转换成0,<br/>
	 * 推荐范围为0-7(其他范围对其取余),是因为中国的生活习惯从星期一到星期天,外国的生活习惯从星期天到星期六
	 * 
	 * @param ca
	 * @return
	 */
	public static boolean isWeekIncluded(Calendar ca, int... day) {
		int date = ca.get(Calendar.DAY_OF_WEEK) - 1; // 0 Sunday
		if (day == null) { return false; }
		for (int d : day) {
			if (d % 7 == date) { return true; }
		}
		return false;
	}

	/**
	 * 角度转弧度
	 * 
	 * @param d
	 *            角度
	 * @return
	 */
	public static double degree2rad(double degree) {
		return degree * Math.PI / 180.0;
	}

	/**
	 * 地球半径，单位为公里；
	 */
	public static final double EARTH_RADIUS = 6378.137; // km

	/**
	 * 通过经纬度计算距离
	 * 
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return
	 */
	public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
		double radLat1 = degree2rad(lat1);
		double radLat2 = degree2rad(lat2);
		double a = radLat1 - radLat2;
		double b = degree2rad(lng1) - degree2rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(
				Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}

	/**
	 * 计算文件的md5值,注意返回的是一串字符串,包含abcd,所以不能用于计算serialVersionUID
	 * 
	 * @param file
	 * @return 文件的md5值
	 */
	public static String getMd5OfFile(File file) {
		String value = null;
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(byteBuffer);
			BigInteger bi = new BigInteger(1, md5.digest());
			value = bi.toString(16);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}

	public static Object[] readObj(String filepath, Class... classes) {
		try {
			if (classes.length == 0) { throw new RuntimeException("Classes can not be empty!"); }
			Object[] oa = new Object[classes.length];
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filepath));
			int idx = 0;
			for (Class clzz : classes) {
				System.out.println(clzz);
				if (clzz == String.class) {
					oa[idx++] = ois.readUTF();
				} else if (clzz == Integer.class) {
					oa[idx++] = ois.readInt();
				} else {
					oa[idx++] = ois.readObject();
				}
			}
			ois.close();
			return oa;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void writeObj(String filepath, Object... objs) {
		try {
			File file = new File(filepath);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
			for (Object o : objs) {
				if (o instanceof String) {
					oos.writeUTF((String) o);
				} else if (o instanceof Integer) {
					oos.writeInt((Integer) o);
				} else {
					oos.writeObject(o);
				}
			}
			oos.flush();
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 遍历dir目录,具有共同的suffix的文件,文件名去掉suffix的部分,分组到链表
	 * @param dir
	 * @param suffix
	 * @return
	 */
	public static List<LinkedList<String>> getIncFileGrpedLinkedLists(String dir, String suffix) {
		List<LinkedList<String>> ret = new ArrayList<LinkedList<String>>();
		File f = new File(dir);
		File[] fl = f.listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				if (file.isFile() && file.getName().endsWith(suffix)) return true;
				return false;
			}
		});
		for (File file : fl) {
			String fname = file.getName();
			String fileNameNoSuffix = fname.substring(0, fname.length() - suffix.length());
			int idx = containsInLinkedListsInFirstNode(ret, fileNameNoSuffix, false);
			if (idx > -1) {
				ret.get(idx).add(fileNameNoSuffix);
				continue;
			}
			idx = containsInLinkedListsInFirstNode(ret, fileNameNoSuffix, true);
			if (idx > -1) {
				ret.get(idx).addFirst(fileNameNoSuffix);
			} else {
				LinkedList<String> ll = new LinkedList<String>();
				ll.add(fileNameNoSuffix);
				ret.add(ll);
			}
		}
		for (LinkedList<String> ll : ret) {
			Collections.sort(ll);
		}
		return ret;
	}

	/**
	 * 遍历ll列表中的每一个链表,看str是否startsWith链表头元素
	 * @param ll
	 * @param str
	 * @param inverse true表示链表头.startsWith(str),false表示str.startsWith(链表头)
	 * @return >=0表示是, <0表示否,理论上会!=0,因为a.jpg和a.png不能同时满足一个suffix
	 */
	private static int containsInLinkedListsInFirstNode( //
			List<LinkedList<String>> ll, String str, boolean inverse) {
		for (int i = 0; i < ll.size(); i++) {
			LinkedList<String> l = ll.get(i);
			if (l.isEmpty()) continue;
			if (inverse) {
				if (l.get(0).startsWith(str)) return i;
			} else {
				if (str.startsWith(l.get(0))) return i;
			}
		}
		return -1;
	}

}
