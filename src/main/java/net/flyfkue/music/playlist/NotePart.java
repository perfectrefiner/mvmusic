package net.flyfkue.music.playlist;

import java.util.ArrayList;
import java.util.List;

import net.flyfkue.base.log.Log;
import net.flyfkue.base.util.JavaUtil;

public class NotePart {
	private String str;
	private String pat;
	private int idx = 0;
	private int idxBrackets = 0;
	private int raise;
	private List<Character> rhythm = new ArrayList<Character>();
	private List<String> tone = new ArrayList<String>();
	private List<int[]> bracketPair = new ArrayList<>();
	public static final char[] rhythms = new char[] { 'w', 'h', 'q', 'i', 's', 't', 'x', 'n' };
	public static final char[] dotspecial = new char[rhythms.length];
	public static final String[] dotback = new String[rhythms.length];
	static {
		for (int i = 0; i < dotspecial.length; i++) {
			dotspecial[i] = (char) (rhythms[i] + '.');
			dotback[i] = rhythms[i] + ".";
		}
	}

	public static int getRhythmType(char c) {
		for (int i = 0; i < rhythms.length; i++) {
			if (rhythms[i] == c) return 1;
		}
		for (int i = 0; i < dotspecial.length; i++) {
			if (dotspecial[i] == c) return 2;
		}
		return -1;
	}

	public NotePart(String str, int raiseTone) {
		raise = raiseTone;
		pat = str;
	}

	public void setBracketRight(int idxBrackets, int idx) {
		bracketPair.get(idxBrackets)[1] = idx;
	}

	public void setBracketLeft(int idxBrackets, int idx) {
		if (idxBrackets < bracketPair.size()) {
			bracketPair.set(idxBrackets, new int[] { idx, -1 });
		} else {
			bracketPair.add(new int[] { idx, -1 });
		}
	}

	public boolean hasBracket(int len) {
		if (str.charAt(0) == '[') {
			setBracketLeft(idxBrackets, idx);
			str = str.substring(1);
			return true;
		} else if (str.charAt(len - 1) == ']') {
			setBracketRight(idxBrackets, idx);
			idxBrackets++;
			str = str.substring(0, len - 1);
			// 继续执行s.length() == 2
			return true;
		}
		return false;
	}

	public static String replaceDotBack(String str) {
		for (int i = 0; i < dotspecial.length; i++) {
			str = str.replace(dotspecial[i] + "", dotback[i]);
//			Log.d(dotspecial[i] + "", (int)(dotspecial[i]), dotback[i]);
		}
		return str;
	}

	public static char addDot(char c) {
		// Log.d(c, c + '.');
		return (char) (c + '.');
	}

	private String raise(String str, int raiseTone) {
		if (str == null || str.length() == 0) return "";
		if (str.charAt(0) == '0') return str;
		int len = str.length();
		if (len == 1) throw new RuntimeException("only accept C5, C5w, C#5w, C5w., C#5w. style: " + str);
		if (len == 2) {
			if (isUpdown(str.charAt(1))) { // A#6
				Log.d(str);
				return new NoteInfo().raiseTone(str, raiseTone);
			} else { // C5
				return new NoteInfo().raiseTone(str, raiseTone);
			}
		}
		if (len == 3) {
			if (isUpdown(str.charAt(1))) { // A#6
				return new NoteInfo().raiseTone(str.substring(0, 3), raiseTone) + str.substring(3);
			} else { // C5w
				return new NoteInfo().raiseTone(str.substring(0, 2), raiseTone) + str.substring(2);
			}

		}
		if (len == 5) return new NoteInfo().raiseTone(str.substring(0, 3), raiseTone) + str.substring(4); // C#5w.
		if (len == 4) {
			if (str.charAt(1) == '#' || str.charAt(1) == 'b') { // C#5w
				return new NoteInfo().raiseTone(str.substring(0, 3), raiseTone) + str.substring(4);
			} else { // C5w. 36h.
				// Log.d(str);
				return new NoteInfo().raiseTone(str.substring(0, 2), raiseTone) + addDot(str.charAt(2));
			}
		}
		return str;
	}

	private String setTone(int idx, String s, char c) {
		if (idx < tone.size()) {
			tone.set(idx, s);
		} else {
			tone.add(s);
		}
		if (idx < rhythm.size()) {
			rhythm.set(idx, c);
		} else {
			rhythm.add(c);
		}
		return tone.get(idx);
	}

	private char conv(char c) {
		switch (c) {
		case '1':
			return 'c';
		case '2':
			return 'd';
		case '3':
			return 'e';
		case '4':
			return 'f';
		case '5':
			return 'g';
		case '6':
			return 'a';
		case '7':
			return 'b';
		default:
			return c;
		}
	}

	public NotePart mergeTo(NotePart priorNp) {
		List<Character> priorRhythm = priorNp.rhythm;
		List<String> priorTone = priorNp.tone;
		List<int[]> priorBracketPair = priorNp.bracketPair;
		bracketPair = priorBracketPair;
		List<String> currbrackets = grpBrackets(pat.toCharArray());
		int j = 0;
		for (int i = 0; i < priorBracketPair.size(); i++) {
			int[] aa = priorBracketPair.get(i);
			for (; j < aa[0]; j++) {
				setTone(j, priorTone.get(j), priorRhythm.get(j));
			}
			String currstr = currbrackets.get(i);
			if (currstr == null || currstr.trim().length() == 0) continue;
			NotePart np2 = new NotePart(currstr, raise).conv(false, priorRhythm, j);
			List<String> currTone = np2.tone;
			for (int k = 0; j <= aa[1]; j++, k++) {
				setTone(j, priorTone.get(j).trim() + "+" + currTone.get(k), priorRhythm.get(j));
			}
		}
		for (; j < priorTone.size(); j++) {
			setTone(j, priorTone.get(j), priorRhythm.get(j));
		}
		StringBuilder sbi = new StringBuilder();
		for (String s : tone) {
			sbi.append(s + " ");
		}
		str = sbi.toString();
		return this;
	}

	private boolean isUpdown(char c) {
		return c == '#' || c == 'b';
	}

	private boolean isAlpha(char c) {
		return JavaUtil.isLower(c) || JavaUtil.isUpper(c);
	}

	/**
	 * @param appendRhythm true,自动生成w, false,取priorRhythm[j+idx]
	 * @return true外面执行idx++, false外面不执行idx++
	 */
	private boolean parse(StringBuilder sbi, boolean appendRhythm, List<Character> priorRhythm, int j) {
		if (str == null || str.length() == 0) return false;
		if (str.length() == 6) { // [64h.]
			if (!hasBracket(6)) { // 1#4w
				throw new RuntimeException("what2? " + str);
			}
		}
		if (str.length() == 5) { // [4h.]
			if (!hasBracket(5)) { // 1#4w
				if (isUpdown(str.charAt(1))) { // 6#6H.
					Log.d(str, conv(str.charAt(0)) + str.substring(1, 3));
					sbi.append(setTone(idx, raise(conv(str.charAt(0)) + str.substring(1, 3), raise) + " ", addDot(str.charAt(3))));
				} else {
					throw new RuntimeException("what1? " + str);
				}
				return true;
			}
		}
		// if (str.equals("36h.")) {
		// System.out.println(123);
		// }
		if (str.length() == 4) {
			if (!hasBracket(4)) {
				if (isUpdown(str.charAt(1))) { // 1#4w
					sbi.append(setTone(idx, raise(conv(str.charAt(0)) + str.substring(1), raise) + " ", str.charAt(3)));
				} else { // 36h.
					sbi.append(setTone(idx, raise(conv(str.charAt(0)) + str.substring(1), raise) + " ", addDot(str.charAt(2))));
				}
				return true;
			}
		}
		if (str.length() == 3) {
			if (!hasBracket(3)) {
				char c = str.charAt(1);
				if (JavaUtil.isDigit(c)) { // 15q
					sbi.append(setTone(idx, raise(conv(str.charAt(0)) + str.substring(1), raise) + " ", str.charAt(2)));
					return true;
				} else if (isUpdown(c)) {
					c = str.charAt(2);
					if (JavaUtil.isDigit(c)) {
						if (appendRhythm) { // 1#4->1#4w
							sbi.append(setTone(idx, raise(conv(str.charAt(0)) + str.substring(1), raise) + "w ", 'w'));
						} else {
							sbi.append(setTone(idx, raise(conv(str.charAt(0)) + str.substring(1), raise) + //
									priorRhythm.get(j + idx) + " ", priorRhythm.get(j + idx)));
						}
					} else { // 1#q->1#5q
						sbi.append(setTone(idx, raise(conv(str.charAt(0)) + str.substring(1, 1) + "5", raise) + " ", //
								str.charAt(2)));
					}
					return true;
				} else if (isAlpha(c)) { // 4h.
					sbi.append(setTone(idx, raise(conv(str.charAt(0)) + "5" + str.substring(1), raise) + " ", addDot(str.charAt(1))));
					return true;
				} else {
					throw new RuntimeException("unexpected format: " + str);
				}
			}
		}
		if (str.length() == 2) {
			if (!hasBracket(2)) {
				if (str.charAt(0) == '0') { // 0q
					sbi.append(setTone(idx, str + " ", str.charAt(1)));
					return true;
				} else {
					char c = str.charAt(1);
					if (JavaUtil.isDigit(c)) { // 15->15w
						if (appendRhythm) {
							sbi.append(setTone(idx, raise(conv(str.charAt(0)) + str.substring(1), raise) + "w ", 'w'));
						} else {
							sbi.append(setTone(idx, raise(conv(str.charAt(0)) + str.substring(1), raise) + //
									priorRhythm.get(j + idx) + " ", priorRhythm.get(j + idx)));
						}
					} else if (isUpdown(c)) { // 1B->1B5w
						if (isUpdown(c)) { // 6#
							if (appendRhythm) {
								Log.d(str, conv(str.charAt(0)) + str.substring(1), raise);
								sbi.append(setTone(idx, raise(conv(str.charAt(0)) + str.substring(1) + "5", raise) + "w ", 'w'));
							} else {
								sbi.append(setTone(idx, raise(conv(str.charAt(0)) + str.substring(1) + "5", raise) + //
										priorRhythm.get(j + idx) + " ", priorRhythm.get(j + idx)));
							}
						} else { // 4h
							if (appendRhythm) {
								Log.d(str, conv(str.charAt(0)) + str.substring(1), raise);
								sbi.append(setTone(idx, raise(conv(str.charAt(0)) + "5", raise) + str.charAt(1), str.charAt(1)));
							} else {
								sbi.append(setTone(idx, raise(conv(str.charAt(0)) + "5", raise) + //
										priorRhythm.get(j + idx) + " ", priorRhythm.get(j + idx)));
							}
						}
					} else { // 1q->15q
						sbi.append(setTone(idx, raise(conv(str.charAt(0)) + "5" + str.substring(1), raise) + " ", //
								str.charAt(1)));
					}
					return true;
				}
			}
		}
		if (str.length() == 1) {
			if (str.charAt(0) == '0') { // 0->0w
				if (appendRhythm) {
					sbi.append(setTone(idx, str + "w ", 'w'));
				} else {
					sbi.append(setTone(idx, str + priorRhythm.get(j + idx) + " ", priorRhythm.get(j + idx)));
				}
				return true;
			} else { // 1->15w
				if (appendRhythm) {
					// Log.d(str, conv(str.charAt(0)) + "5");
					sbi.append(setTone(idx, raise(conv(str.charAt(0)) + "5", raise) + "w ", 'w'));
				} else {
					sbi.append(setTone(idx, raise(conv(str.charAt(0)) + "5", raise) + //
							priorRhythm.get(j + idx) + " ", priorRhythm.get(j + idx)));
				}
				return true;
			}
		}
		throw new RuntimeException("why here? " + str);
	}

	public NotePart conv() {
		return conv(true, null, 0);
	}

	public NotePart conv(boolean appendRhythm, List<Character> priorRhythm, int j) {
		StringBuilder sbi = new StringBuilder();
		String[] sa = pat.split("\\s+");
		for (int i = 0; i < sa.length; i++) {
			// sa[i]
			str = sa[i];
			if (parse(sbi, appendRhythm, priorRhythm, j)) idx++;
		}
		str = sbi.substring(0, sbi.length());
		return this;
	}

	public String getStr() {
		return str;
	}

	public List<String> grpBrackets(char[] ca) {
		StringBuilder sbi = new StringBuilder();
		List<String> ret = new ArrayList<>();
		boolean inBracket = false;
		for (char c : ca) {
			if (c == '[') {
				if (inBracket) {
					// 嵌套了
					throw new RuntimeException("can not support [[]]");
				} else {
					inBracket = true;
				}
			} else if (c == ']') {
				if (inBracket) {
					inBracket = false;
					ret.add(sbi.toString());
					sbi.delete(0, sbi.length());
				}
			} else if (inBracket) {
				sbi.append(c);
			}
		}
		return ret;
	}

}
