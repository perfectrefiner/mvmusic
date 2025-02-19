package net.flyfkue.music.playlist;

import java.util.HashMap;
import java.util.Map;

import net.flyfkue.base.log.Log;

public class NoteInfo {
	private static final Map<String, Integer> NOTE2INT = new HashMap<>();
//	private static final Map<String, Integer> NOTE2INT2 = new HashMap<>();
	private static final Map<Integer, String> INT2NOTE = new HashMap<>();
	private static String[] pref = new String[] { "c", "c#", "d", "d#", "e", "f", "f#", "g", "g#", "a", "a#", "b" };
//	private static String[] pref2 = new String[] { "C", "DB", "D", "EB", "E", "F", "GB", "G", "AB", "A", "BB", "B" };
	static {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 12; j++) {
				INT2NOTE.put(i * 12 + j, pref[j] + i);
				NOTE2INT.put(pref[j] + i, i * 12 + j);
//				NOTE2INT2.put(pref2[j] + i, i * 12 + j);
			}
		}
	}

	public String raiseTone(String note, int raiseTone) {
		return INT2NOTE.get(NOTE2INT.get(note) + raiseTone);
//		Integer s = NOTE2INT.get(note);
//		Log.d(note, s);
////		if (s == null) return INT2NOTE.get(NOTE2INT2.get(note) + raiseTone);
//		return INT2NOTE.get(s + raiseTone);
	}

}
