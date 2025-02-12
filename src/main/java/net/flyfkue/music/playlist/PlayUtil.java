package net.flyfkue.music.playlist;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

import net.flyfkue.base.log.Log;
import net.flyfkue.base.util.JavaUtil;

public class PlayUtil {

	public static void main(String[] args) {
		PlayUtil p = new PlayUtil();
		// p.test();
		p.test1();
		// p.test2();
		// p.playJfugueFile("/workseclipse/jee/myjfugue/music_jcldxt.txt");
		// p.playMidi("/workseclipse/jee/myjfugue/15.mid");
	}

	private void test() {
		Player player = new Player();
		Pattern complexPattern = new Pattern("T120 V0 I[Piano] C4 E4 G4 C5 W[2] R W[2] E4 G4 B4 E5 W[2] R W[2]" //
				+ " G4 B4 D5 G5 W[2] R W[2] C5 E5 G5 C6 W[2] R W[2]").setInstrument("Piano");
		player.play(complexPattern);
	}

	private void test2() {
		String str1 = "/workseclipse/jee/myjfugue/music_jcldxt.txt";
		String str2 = str1;
		StringBuilder sbi = new StringBuilder(str1);
		sbi.setCharAt(4, '!');
		str1.replace('/', '|');
		str2 = str2.replace('/', '|');
		System.out.println(str1);
		System.out.println(str2);
		System.out.println(sbi);
	}

	private void test1() {
		Log.d(new File("music_jcldxt.txt").getAbsolutePath());
		playJfugueFile("music_jcldxt.txt", 0);
	}

	public void playJfugueFile(String file, int raiseTone) {
		play(parse(file, raiseTone), "piano");
	}

	private String parse(String file, int raiseTone) {
		List<String> pat = JavaUtil.readFile2List(file);
		return parse(pat, raiseTone);
	}

	private String parse(List<String> pat, int raiseTone) {
		StringBuilder sbi = new StringBuilder();
		String prior = null;
		int vcnt = 0; // no. of voice
		int vcntInc = 0; // no. of voice
		RhythmInfo ri = null;
		for (int i = 0; i < pat.size(); i++) {
			String str = pat.get(i);
			if (str.startsWith("#")) {
			} else if (str.trim().length() == 0) {
				vcntInc=0;
			} else if (str.startsWith(":")) {
				ri = parseColonLine(str + " ", raiseTone);
				sbi.append(ri.getSbistr());
				prior = ri.getVstr();
				vcntInc=0;
			} else if (str.startsWith(">")) {
				sbi.append(str.substring(1) + " ");
				vcntInc=0;
			} else if (str.startsWith(";")) {
				vcnt++;
				sbi.append("V" + str.substring(1).trim() + " ");
				vcntInc=0;
			} else if (str.startsWith("=")) {
				// if (ri == null) ri = new RhythmInfo(pat.get(i - 1));
				ri = ri.parseEqualLine(str.substring(1).trim(), raiseTone, vcnt + vcntInc);
				sbi.append(ri.getSbistr());
				vcntInc++;
			} else {
				ri = parseColonLine(prior + str + " ", raiseTone);
				sbi.append(ri.getSbistr());
				vcntInc=0;
				// sbi.append(str.trim() + " ");
			}
		}
		Log.d(sbi.toString());
		return sbi.toString();
	}

	private char[] genRhythm(String string) {
		return null;
	}

	private RhythmInfo parseColonLine(String pat, int raiseTone) {
		pat = pat.replace('|', ' ');
		pat = pat.trim();
		RhythmInfo ri = new RhythmInfo(pat);
		char[] ca = pat.toCharArray();
		if (ca[0] != ':') throw new RuntimeException("not colon");
		int idx = 1;
		ri.append("V");
		while (true) {
			ri.append(ca[idx]);
			idx++;
			if (ca[idx] == ':') break;
		}
		idx++;
		ri.setVstr(pat.substring(0, idx));
		ri.append(" ");
		NotePart np = ri.conv(pat.substring(idx), raiseTone);
		ri.append(np.str);
		ri.setNp(np);
		return ri;
	}

	public void playMidi(String midiPath) {
		try {
			// 创建一个Sequencer对象
			Sequencer sequencer = MidiSystem.getSequencer();
			// 打开Sequencer
			sequencer.open();
			// 创建一个Sequence对象，用于存储MIDI文件
			// midiPath="D:/workseclipse/jee/myjfugue/音节.midi";
			Sequence sequence = MidiSystem.getSequence(new File(midiPath));
			// 将Sequence对象设置到Sequencer中
			sequencer.setSequence(sequence);
			// 启动播放
			sequencer.start();
			// 等待播放完成
			while (sequencer.isRunning()) {
				Thread.sleep(1000);
			}
			// 停止播放
			sequencer.stop();
			// 关闭Sequencer
			sequencer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void play(String pattern, String instrument) {
		if (pattern == null || instrument == null) return;
		Player player = new Player();
		Pattern complexPattern = new Pattern(pattern).setInstrument(instrument);
		player.play(complexPattern);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	class RhythmInfo {

		private StringBuilder sbi = new StringBuilder();
		private String pat;
		private String vstr; // voice v0,v1...
		private List<Character> rhythm = new ArrayList<Character>();
		private List<String> tone = new ArrayList<String>();
		private List<int[]> bracketPair = new ArrayList<>();
		private NotePart np;

		public RhythmInfo(String str) {
			pat = str;
		}

		public void setNp(NotePart notePart) {
			np = notePart;
		}

		public String getVstr() {
			return vstr;
		}

		public Object getSbistr() {
			return sbi.toString();
		}

		public void setVstr(String vs) {
			vstr = vs;
		}

		private String raise(String str, int raiseTone) {
			if (str == null || str.length() == 0) return "";
			if (str.charAt(0) == '0') return str;
			int len = str.length();
			if (len == 1 || len == 2) throw new RuntimeException("only accept C5w, C#5w, C5w., C#5w. style: " + str);
			if (len == 3) return new NoteInfo().raiseTone(str.substring(0, 2), raiseTone) + str.substring(2);
			if (len == 5) return new NoteInfo().raiseTone(str.substring(0, 3), raiseTone) + str.substring(4);
			if (len == 4) {
				if (str.charAt(1) == '#' || str.charAt(1) == 'B') return new NoteInfo().raiseTone(str.substring(0, 3), raiseTone) + str.substring(4);
				else return new NoteInfo().raiseTone(str.substring(0, 2), raiseTone) + str.substring(3);
			}
			return str;
		}

		private char conv(char c) {
			switch (c) {
			case '1':
				return 'C';
			case '2':
				return 'D';
			case '3':
				return 'E';
			case '4':
				return 'F';
			case '5':
				return 'G';
			case '6':
				return 'A';
			case '7':
				return 'B';
			default:
				return c;
			}
		}

		private RhythmInfo parseEqualLine(String pat, int raiseTone, int vidx) {
			pat = pat.replace('|', ' ');
			pat = pat.trim();
			char[] ca = pat.toCharArray();
			this.clearSbi();
			this.append("V" + vidx + " ");
			List<String> brs = grpBrackets(ca);
			List<int[]> priorLine = this.getBracket();
			int len = brs.size();
			int j = 0;
			for (int i = 0; i < len; i++) {
				int[] ia = priorLine.get(i);
				for (; j < ia[0]; j++) {
					char c = rhythm.get(j);
					this.append("0" + c + " ");
				}
				String br = brs.get(i);
				NotePart np1 = conv(br, raiseTone);
				String[] sa = np1.str.split("\\s+");
				for (j = ia[0]; j < ia[1]; j++) {
					String a = sa[j - ia[0]];
					this.append(a.substring(0, a.length()-1) + rhythm.get(j) + " ");
				}
			}
			return this;
		}

		public List<String> grpBrackets(char[] ca) {
			List<String> ret = new ArrayList<>();
			StringBuilder sbi = new StringBuilder();
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

		public NotePart conv(String music, int raiseTone) {
			StringBuilder sbi = new StringBuilder();
			String[] sa = music.split("\\s+");
			NotePart np = new NotePart(this);
			for (int i = 0; i < sa.length; i++) {
				String s = sa[i];
				if (s == null || s.length() == 0) continue;
				np.str = s;
				if (s.length() == 4) {
					if (np.hasBracket(4)) {
						s = np.str;
					} else {
						sbi.append(setTone(np.idx, raise(conv(s.charAt(0)) + s.substring(1), raiseTone) + " ", //
								s.charAt(2)));
						np.idx++;
						continue; // 进入下一个循环
					}
				}
				if (s.length() == 3) {
					if (np.hasBracket(3)) {
						s = np.str;
					} else {
						sbi.append(setTone(np.idx, raise(conv(s.charAt(0)) + s.substring(1), raiseTone) + " ", s.charAt(2)));
						np.idx++;
						continue; // 进入下一个循环
					}
				}
				if (s.length() == 2) {
					if (np.hasBracket(2)) {
						s = np.str;
					} else {
						if (s.charAt(0) == '0') {
							sbi.append(setTone(np.idx, s + " ", s.charAt(1)));
						} else {
							char c = s.charAt(1);
							if ((c >= 0 && c <= 9) || c == '#' || c == 'B') { // 15->15w
								sbi.append(setTone(np.idx, raise(conv(s.charAt(0)) + s.substring(1), raiseTone) + "w ", 'w'));
							} else { // 1q->15q
								sbi.append(setTone(np.idx, raise(conv(s.charAt(0)) + "5" + s.substring(1), raiseTone) + " ", s.charAt(1)));
							}
						}
						np.idx++;
						continue; // 进入下一个循环
					}
				}
				if (s.length() == 1) {
					if (raiseTone == 0) {
						sbi.append(setTone(np.idx, s + "w ", 'w'));
					} else {
						sbi.append(setTone(np.idx, raise(conv(s.charAt(0)) + "", raiseTone) + "w ", 'w'));
					}
					np.idx++;
					// 进入下一个循环
				}
			}
			np.str = sbi.substring(0, sbi.length());
			return np;
		}

		private List<int[]> getBracket() {
			return bracketPair;
		}

		private void setBracketRight(int idxBrackets, int idx) {
			bracketPair.get(idxBrackets)[1] = idx;
		}

		private void setBracketLeft(int idxBrackets, int idx) {
			if (idxBrackets < bracketPair.size()) {
				bracketPair.set(idxBrackets, new int[] { idx, -1 });
			} else {
				bracketPair.add(new int[] { idx, -1 });
			}
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

		public void append(char c) {
			sbi.append(c);
		}

		public void clearSbi() {
			sbi.delete(0, sbi.length());
		}

		public void append(String str) {
			sbi.append(str);
		}

	}

	class NotePart {
		int idx = 0;
		int idxBrackets = 0;
		String str;
		RhythmInfo ri;

		public NotePart(RhythmInfo rhythmInfo) {
			ri = rhythmInfo;
		}

		public boolean hasBracket(int len) {
			if (str.charAt(0) == '[') {
				ri.setBracketLeft(idxBrackets, idx);
				str = str.substring(1);
				return true;
			} else if (str.charAt(len - 1) == ']') {
				ri.setBracketRight(idxBrackets, idx);
				idxBrackets++;
				str = str.substring(0, len - 1);
				// 继续执行s.length() == 2
				return true;
			}
			return false;
		}
	}

	static class NoteInfo {
		private static final Map<String, Integer> NOTE2INT = new HashMap<>();
		private static final Map<Integer, String> INT2NOTE = new HashMap<>();
		private static String[] pref = new String[] { "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B" };
		static {
			for (int i = 1; i < 9; i++) {
				for (int j = 0; j < 12; j++) {
					INT2NOTE.put(i * 12 + j, pref[j] + i);
					NOTE2INT.put(pref[j] + i, i * 12 + j);
				}
			}
		}

		private String raiseTone(String note, int raiseTone) {
			return INT2NOTE.get(NOTE2INT.get(note) + raiseTone);
		}
	}
}
