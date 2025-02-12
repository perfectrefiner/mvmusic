package net.flyfkue.base.log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import net.flyfkue.base.util.JavaUtil;

public class Log {
	public static final int VERBOSE = 1;
	public static final int DEBUG = 2;
	public static final int INFO = 4;
	public static final int WARN = 8;
	public static final int ERROR = 16;
	public static final int DEBUGERROR = 512;
	public static final int FATAL = 32;
	public static final int OPER = 256;
	public static final int USER = 64;
	public static final int SYSTEM = 128;
	private static Set<String> sets = new HashSet<String>();
	public static String sdfStr = "yyyyMMddHHmmss.sss";
	public static SimpleDateFormat sdf = new SimpleDateFormat(sdfStr);
	public static String userLogfile = null;
	public static StringBuffer sbUserLogfile = new StringBuffer("");
	public static String separatorCharacter = "; ";
	public static boolean printTag = true;
	public static boolean print2Console = true;
	public static int writeLogTimeInterval = 1000; // ms
	private static long nextWriteLogFileTime = 0L;
	private static StringBuilder nextWriteLogStr = null;
	private static Lock pushLogLock = new ReentrantLock();
	private static Lock writeLogLock = new ReentrantLock();
	private static Map<String, StringBuilder> log2filePoper = new HashMap<>();
	private static Map<String, StringBuilder> log2filePusher = new HashMap<>();
	private static boolean isWritingThreadRunning = false;

	private static int currentlevel = 1023;
	// private static int currentlevel = 1023 ^ DEBUG;
	// private static int currentlevel = 0xffffffff;
	// private static int currentlevel = 1023 ^ VERBOSE ^ DEBUG;
	private static long lastModified = 0;

	public static void setLogFile() {
		setLogFile("/log" + sdf.format(new Date()) + ".log");
	}

	public static void setLogFile(String logFile) {
		userLogfile = logFile.replace("\\", "/");
	}

	public static void reset() {
		File f = new File("/logs/conf/log.conf");
		if (!f.exists()) { return; }
		if (f.lastModified() > lastModified) {
			lastModified = f.lastModified();
			String vals = JavaUtil.readFile("/logs/conf/log.conf");
			String[] sa = vals.trim().split("\r\n", 2);
			if (sa.length < 1) {
				// 使用默认
				return;
			}
			int level = Integer.valueOf(sa[0]);
			if (level < 0) { return; }
			String allowPrintClass;
			if (sa.length < 2) {
				allowPrintClass = "";
			} else {
				allowPrintClass = sa[1].replaceAll("\\s+", ";");
			}
			reset(level, allowPrintClass);
		}
	}

	private static void reset(int level, String allowPrintClass) {
		System.out.println("reset:" + allowPrintClass);
		currentlevel = level;
		sets = new HashSet<String>();
		String[] sa = allowPrintClass.split(";");
		for (String str : sa) {
			str = str.trim();
			if (str.length() > 0) {
				sets.add(str);
			}
			// try {
			// Class<?> clzz = Class.forName(str);
			// System.out.println("clzz.getName():" + clzz.getName());
			// } catch (ClassNotFoundException e) {
			// e.printStackTrace();
			// }
		}
		System.out.println("currentlevel:" + currentlevel + ",set:" + sets);
	}

	public static void add(int currentlevel) {
		Log.currentlevel = 0xff & currentlevel;
	}

	public static String getLogStr(Object... oa) {
		StringBuffer sb = new StringBuffer();
		for (Object o : oa) {
			if (o == null) {
				sb.append("null" + separatorCharacter);
			} else {
				if (o instanceof String[]) {
					sb.append(Arrays.toString((String[]) o));
				} else if (o instanceof Object[]) {
					sb.append(Arrays.toString((Object[]) o));
				} else {
					sb.append(o + separatorCharacter);
				}
			}
		}
		return sb.toString().substring(0, sb.length() - 1);
	}

	private static String getLogStr(String type, Object o) {
		if (o == null) {
			// return (printTag ? (sdf.format(new Date()) + ":" + type + ":") : "") +
			// "null";
			if (printTag) return sdf.format(new Date()) + ":" + type + ":null";
			else return "null";
		} else {
			if (o instanceof Object[]) {
				if (printTag) return sdf.format(new Date()) + ":" + type + ":" + Arrays.toString((Object[]) o);
				else return Arrays.toString((Object[]) o);
			}
			if (o instanceof String[]) {
				if (printTag) return sdf.format(new Date()) + ":" + type + ":" + Arrays.toString((String[]) o);
				else return Arrays.toString((String[]) o);
			}
			// if (o.getClass().isArray()){
			// Object[] oa=(Object[]) o;
			// return sdf.format(new Date()) + ":" + type + ":" +
			// Arrays.toString(oa);
			// }
			// System.out.println("o.getClass():"+o.getClass());
			if (printTag) return sdf.format(new Date()) + ":" + type + ":" + o.toString();
			else return o.toString();
		}
	}

	private static void logout(String type, Object o) {
		String logstr = getLogStr(type, o);
		if (print2Console) System.out.println(logstr);
		writeLog2File(userLogfile, logstr);
		// LOG.info(getLogStr(type, o));
	}

	// private static void testpusherpoper(String mark) {
	// System.out.println(mark + ": pusher:" + log2filePusher.size() + ", poper:" + log2filePoper.size());
	// }

	private static void pushLogStr(String userLogfile, String logstr) {
		// try {
		// pushLogLock.lock();
		// testpusherpoper("before");
		StringBuilder sbi = log2filePusher.get(userLogfile);
		if (sbi == null) {
			sbi = new StringBuilder();
			log2filePusher.put(userLogfile, sbi);
		}
		sbi.append(logstr);
		// testpusherpoper("after");

		// } catch (Exception e) {
		// e.printStackTrace();
		// } finally {
		// pushLogLock.unlock();
		// }
	}

	/**
	 * 
	 */
	private static void fireWritingThreadDeadLoop() {
		if (isWritingThreadRunning) { return; }
		// try {
		// if (!writeLogLock.tryLock()) { return; } // 136 204639
		// writeLogLock.lock(); // 132 204169 135389
		// if (isWritingThreadRunning) { return; }
		isWritingThreadRunning = true;
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				while (this.run0()) {
					;
				}
			}

			private boolean run0() {
				if (log2filePoper.isEmpty()) {
					Map<String, StringBuilder> tmp = log2filePoper;
					log2filePoper = log2filePusher;
					log2filePusher = tmp;
					// testpusherpoper("change");
					if (log2filePoper.isEmpty()) {
						isWritingThreadRunning = false;
						return false;
					}
					return true;
				}
				// try {
				// pushLogLock.lock();
				// if (log2filePoper.isEmpty()) {
				// isWritingThreadRunning = false;
				// return false;
				// }
				Iterator<Entry<String, StringBuilder>> it = log2filePoper.entrySet().iterator();
				while (it.hasNext()) {
					Entry<String, StringBuilder> en = it.next();
					String loggingFile = en.getKey();
					String LoggingContent = en.getValue().toString();
					JavaUtil.writeFile(loggingFile, LoggingContent, true);
				}
				log2filePoper.clear();
				// isWritingThreadRunning = false;
				// } catch (Exception e) {
				// e.printStackTrace();
				// } finally {
				// pushLogLock.unlock();
				// }
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return true;
			}
		});
		// t.setDaemon(true);
		t.start();
		// } catch (Exception e) {
		// e.printStackTrace();
		// } finally {
		// writeLogLock.unlock();
		// }
	}

	// /**
	// * @see fireWritingThreadDeadLoop
	// * @deprecated
	// * 改为使用fireWritingThreadDeadLoop方法<br/>
	// * 经测试,这个方法写midlog只写了前几个文件,后面文件都没有写入,<br/>
	// * 经分析原因如下:本方法在t.start();后,此时的isWritingThreadRunning为true,<br/>
	// * 而pushLogLock.unlock();后,Thread.sleep(100);期间,pushLogLock为unlock状态,<br/>
	// * 程序在此期间会迅速多次执行if (isWritingThreadRunning) { return; },导致log2file积累,<br/>
	// * 如果这个时候程序由于运行太快而导致已经结束的话,此时log2file中的积累就不会写文件,所以会缺文件,<br/>
	// * 但是将isWritingThreadRunning = false;放到Thread.sleep(100);之前是无法解决该问题的,<br/>
	// * 因为在执行isWritingThreadRunning = false;的时间段,主线程很可能已经向log2file积累了数据,<br/>
	// * 然而将isWritingThreadRunning = false;放到pushLogLock.unlock();之前也不合适,<br/>
	// * 因为会导致程序看似另起了线程在写文件,实际由于主线程陷入锁等待,运行效果跟单线程没区别
	// */
	// private static void fireWritingThread() {
	// // System.out.println("fireWritingThread"+log2file.size());
	// if (isWritingThreadRunning) { return; }
	// try {
	// writeLogLock.lock();
	// if (isWritingThreadRunning) { return; }
	// isWritingThreadRunning = true;
	// Thread t = new Thread(new Runnable() {
	// @Override
	// public void run() {
	// if (log2file == null) { return; }
	// try {
	// pushLogLock.lock();
	// if (log2file == null) { return; }
	// Iterator<Entry<String, StringBuilder>> it = log2file.entrySet().iterator();
	// while (it.hasNext()) {
	// Entry<String, StringBuilder> en = it.next();
	// String loggingFile = en.getKey();
	// String LoggingContent = en.getValue().toString();
	// JavaUtil.writeFile(loggingFile, LoggingContent, true);
	// }
	// log2file.clear();
	// } catch (Exception e) {
	// e.printStackTrace();
	// } finally {
	// pushLogLock.unlock();
	// }
	// try {
	// Thread.sleep(100);
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// isWritingThreadRunning = false;
	// }
	// });
	// // t.setDaemon(true);
	// t.start();
	// } catch (Exception e) {
	// e.printStackTrace();
	// } finally {
	// writeLogLock.unlock();
	// }
	// }

	private static void writeLog2File(String userLogfile, String logstr) {
		if (userLogfile == null) { return; }
		pushLogStr(userLogfile, logstr + "\r\n");
		fireWritingThreadDeadLoop();
		// fireWritingThread();

		// JavaUtil.writeFile(userLogfile, logstr + "\r\n", true);
		// if (writeLogTimeInterval < 1) {
		// JavaUtil.writeFile(userLogfile, logstr + "\r\n", true);
		// return;
		// }
		// writeLogLock.lock();
		// if (nextWriteLogStr == null) {
		// nextWriteLogStr = new StringBuilder();
		// }
		// nextWriteLogStr.append(logstr + "\r\n");
		// writeLogLock.unlock();
		// if (System.currentTimeMillis() > nextWriteLogFileTime) {
		// nextWriteLogFileTime = System.currentTimeMillis() + writeLogTimeInterval;
		// writeLogLock.lock();
		// if (nextWriteLogStr != null) {
		// JavaUtil.writeFile(userLogfile, nextWriteLogStr.toString(), true);
		// nextWriteLogStr = new StringBuilder();
		// }
		// writeLogLock.unlock();
		// }
	}
	// private static FileOutputStream fos = null;
	// private static OutputStreamWriter osw = null;
	// public static void close() {
	// try {
	// if (osw != null) {
	// osw.flush();
	// osw.close();
	// }
	// } catch (IOException e) {
	// e.printStackTrace();
	// } finally {
	// osw = null;
	// }
	// try {
	// if (fos != null) {
	// fos.close();
	// }
	// } catch (IOException e) {
	// e.printStackTrace();
	// }finally {
	// fos = null;
	// }
	// }
	// public static void writeFile(File file, String content, String charset,
	// boolean append, boolean doClose) {
	// try {
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
	// }

	private static void logerr(String type, Object o) {
		String logstr = getLogStr(type, o);
		if (print2Console) System.err.println(logstr);
		writeLog2File(userLogfile, logstr);
		// LOG.error(getLogStr(type, o));
	}

	private static void v(Object o) {
		if ((currentlevel & VERBOSE) != VERBOSE) { return; }
		logout("v", o);
	}

	private static void d(Object o) {
		if ((currentlevel & DEBUG) != DEBUG) { return; }
		logout("d", o);
	}

	private static void i(Object o) {
		if ((currentlevel & INFO) != INFO) { return; }
		logout("i", o);
	}

	private static void w(Object o) {
		if ((currentlevel & WARN) != WARN) { return; }
		logerr("w", o);
	}

	private static void e(Object o) {
		if ((currentlevel & ERROR) != ERROR) { return; }
		logerr("e", o);
	}

	private static void de(Object o) {
		if ((currentlevel & DEBUGERROR) != DEBUGERROR) { return; }
		logerr("de", o);
	}

	private static void f(Object o) {
		if ((currentlevel & FATAL) != FATAL) { return; }
		logout("f", o);
	}

	private static void p(Object o) {
		if ((currentlevel & OPER) != OPER) { return; }
		logout("p", o);
	}

	private static void u(Object o) {
		if ((currentlevel & USER) != USER) { return; }
		logout("u", o);
	}

	private static void s(Object o) {
		if ((currentlevel & SYSTEM) != SYSTEM) { return; }
		logout("s", o);
	}

	private static String match(int type) {
		if ((currentlevel & type) != type) { return null; }
		try {
			throw new RuntimeException("hahaha");
		} catch (Exception e) {
			StackTraceElement[] sta = e.getStackTrace();
			if (sta.length < 3) { return null; }
			for (int i = 2; i < 5; i++) {
				// 0 match 1 p/d/v/i... 2开始才是调用者
				if (!("net.flyfkue.base.log.Log".equals(sta[i].getClassName()))) {
					if (sets.isEmpty() || sets.contains(sta[i].getClassName())) {
						if (printTag) return sta[i].getFileName() //
								+ ":" + sta[i].getLineNumber() //
								+ ":" + sta[i].getMethodName() + ":" //
						;
						else return "";
					}
					return null;
				}
			}
		}
		return null;
	}

	public static void v(Object... o) {
		String tag = match(VERBOSE);
		if (tag != null) {
			v(tag + getLogStr(o));
		}
		// if ((currentlevel & VERBOSE) != VERBOSE) {
		// return;
		// }
	}

	public static void d(Object... o) {
		String tag = match(DEBUG);
		if (tag != null) {
			d(tag + getLogStr(o));
		}
	}

	public static void i(Object... o) {
		String tag = match(INFO);
		if (tag != null) {
			i(tag + getLogStr(o));
		}
	}

	public static void w(Object... o) {
		String tag = match(WARN);
		if (tag != null) {
			w(tag + getLogStr(o));
		}
	}

	public static void e(Object... o) {
		String tag = match(ERROR);
		if (tag != null) {
			e(tag + getLogStr(o));
		}
	}

	public static void de(Object... o) {
		String tag = match(DEBUGERROR);
		if (tag != null) {
			de(tag + getLogStr(o));
		}
	}

	public static void f(Object... o) {
		String tag = match(FATAL);
		if (tag != null) {
			f(tag + getLogStr(o));
		}
	}

	public static void p(Object... o) {
		String tag = match(OPER);
		if (tag != null) {
			p(tag + getLogStr(o));
		}
	}

	public static void u(Object... o) {
		String tag = match(USER);
		if (tag != null) {
			u(tag + getLogStr(o));
		}
	}

	public static void s(Object... o) {
		String tag = match(SYSTEM);
		if (tag != null) {
			s(tag + getLogStr(o));
		}
	}

	public static String getSeparatorCharacter() {
		return separatorCharacter;
	}

	public static void setSeparatorCharacter(String separatorCharacter) {
		Log.separatorCharacter = separatorCharacter;
	}

	public static SimpleDateFormat getSdf() {
		return sdf;
	}

	public static void setSdf(String format) {
		Log.sdfStr = format;
		Log.sdf = new SimpleDateFormat(format);
	}

	public static boolean isPrintTag() {
		return printTag;
	}

	public static void setPrintTag(boolean printTag) {
		Log.printTag = printTag;
	}

	public static boolean isPrint2Console() {
		return print2Console;
	}

	public static void setPrint2Console(boolean print2Console) {
		Log.print2Console = print2Console;
	}

	public static void main(String[] args) {
		// Date date = new Date();
		// // 返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。
		// long time = date.getTime();
		// System.out.println(time); // 1558322532170
		// // select unix_timestamp(now()); -- 1558322534
		// SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmss");
		// String s = sdf.format(new Date());
		// System.out.println(Long.valueOf(s));
		System.out.println(1023 ^ VERBOSE ^ DEBUG);
		System.out.println(1023 ^ DEBUG);
	}

}
