package net.flyfkue.base.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * StrUtil中FuncCaller的实现
 * 
 * @author Administrator
 * 
 */
public class Fcaller {
	private String methodName;
	private Class<?> clzz;
	private Object obj;

	private Fcaller() {

	}

	@SuppressWarnings("rawtypes")
	public Object call(Object... args) {
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
			return mtd.invoke(obj, args);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Fcaller me(Object o, String methodName) {
		try {
			Fcaller fc = new Fcaller();
			fc.methodName = methodName;
			if (o instanceof Class<?>) {
				fc.clzz = (Class<?>) o; // 静态方法
				fc.obj = null;
			} else {
				fc.clzz = o.getClass(); // 对象方法
				fc.obj = o;
			}
			return fc;
		} catch (SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}

}
