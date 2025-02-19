package net.flyfkue.music.playlist;

public class RhythmInfo {

	private StringBuilder sbi = new StringBuilder();
	private String pat;
	private String vstr; // voice v0,v1...
	private NotePart np;
	private int raise;

	public RhythmInfo(String str, int raiseTone) {
		pat = str;
		raise = raiseTone;
	}

	public static String replaceDecToInc(String str) {
		String[] t1 = new String[] { "1", "1#", "2", "2#", "3", "4", "4#", "5", "5#", "6", "6#", "7" };
		String[] t2 = new String[] { "1", "2b", "2", "3b", "3", "4", "5b", "5", "6b", "6", "7b", "7" };
		for (int i = 0; i < t2.length; i++) {
			str = str.replace(t2[i], t1[i]);
		}
		return str;
	}

	public static String replaceEmpty(String str) {
		str = str.replaceAll("\t", " ");
		str = str.replaceAll(" +", " ");
		str = str.replace(" .", ".");
		str = str.replace(" +", "+");
		str = str.replace("+ ", "+");
		str = str.replace("[ ", " [");
		str = str.replace(" ]", "] ");
		str = str.replaceAll(" +", " ");
		return str;
	}

	public static String replaceAndTrim(String str) {
		str = str.replace('|', ' ');
		str = str.replace("''''", "9");
		str = str.replace("'''", "8");
		str = str.replace("''", "7");
		str = str.replace("'", "6");
		str = str.replace("\"\"", "9");
		str = str.replace("\"", "7");
		str = str.replace(",,,,", "1");
		str = str.replace(",,,", "2");
		str = str.replace(",,", "3");
		str = str.replace(",", "4");
		str = str.replace(";;", "1");
		str = str.replace(";", "3");
		// 因为.用于延音符,所以不能用.:表示降两度,换用,;
		str = str.trim();
		return str;
	}

	public void append(char c) {
		sbi.append(c);
	}

	public void append(String str) {
		sbi.append(str);
	}

	public String getVoicestr() {
		return vstr;
	}

	public void setVoicestr(String voicestr) {
		vstr = voicestr;
	}

	public RhythmInfo parseColonLine() {
		pat = replaceAndTrim(pat);
		char[] ca = pat.toCharArray();
		if (ca[0] != ':') throw new RuntimeException("not colon");
		int idx = 1;
		append("V");
		while (true) {
			append(ca[idx]);
			idx++;
			if (ca[idx] == ':') break;
		}
		append(" ");
		idx++;
		setVoicestr(pat.substring(0, idx));
		np = new NotePart(pat.substring(idx), raise).conv();
		append(np.getStr());
		return this;
	}

	public String getSbistr() {
		return sbi.toString();
	}

	public RhythmInfo parseEqualLine(RhythmInfo prior) {
		pat = replaceAndTrim(pat);
		np = new NotePart(pat, raise).mergeTo(prior.np);
		append(np.getStr());
		return this;
	}

}