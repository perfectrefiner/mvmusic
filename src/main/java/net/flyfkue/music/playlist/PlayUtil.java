package net.flyfkue.music.playlist;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
		Log.add(Log.DEBUG | Log.VERBOSE);
		Log.setLogFile();
		Log.reset();
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
		playJfugueFile("music_jcldxt.txt", 0);
//		 playJfugueFile("music_zals.txt", 0);
//		 playJfugueFile("music_zals2.txt", 0);
//		 playJfugueFile("music_zals3.txt", 0);
		// playJfugueFile("music_pi500.txt", 0);
		// playJfugueFile("music_e1000.txt", 0);
	}

	public void playJfugueFile(String file, int raiseTone) {
		// Log.d(new File(file).getAbsolutePath());
		String str = parse(file, raiseTone);
		String revstr = reverse(str);
		play(str, "piano");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		play(revstr, "piano");
	}

	private String reverse(String str) {
		str = str.toLowerCase(Locale.US);
		String[] sa = str.split("\\s+");
		StringBuilder sbi = new StringBuilder();
		int beg = 0;
		for (; beg < sa.length; beg++) {
			if (sa[beg].startsWith("v")) {
				if (!sa[beg + 1].startsWith("i")) {
					break;
				}
			}
			sbi.append(sa[beg] + " ");
		}
		StringBuilder sbi2 = new StringBuilder();
		for (int i = sa.length - 1; i >= beg; i--) {
			if (sa[i].startsWith("v")) {
				sbi.append(sa[i] + " " + sbi2.toString() + " ");
				sbi2.delete(0, sbi2.length());
			} else {
				sbi2.append(sa[i] + " ");
			}
		}
		return sbi.toString();
	}

	private String parse(String file, int raiseTone) {
		// List<String> pat = JavaUtil.readFile2List(file);
		String tmp = JavaUtil.readFile(file);
		tmp = tmp.toLowerCase(Locale.US);
		tmp = RhythmInfo.replaceEmpty(tmp);
		tmp = RhythmInfo.replaceAndTrim(tmp);
		tmp = RhythmInfo.replaceDecToInc(tmp);
		String[] sa = tmp.split("\r\n");
		List<String> pat = new ArrayList<>();
		for (String s : sa) {
			pat.add(s);
		}
		return parse(pat, raiseTone);
	}

	private String parse(List<String> pat, int raiseTone) {
		List<String> sbilst = new ArrayList<String>();
		String prior = null;
		RhythmInfo ri = null;
		int repeat = 0, repeatbeg = -1, repeatend = -1;
		for (int i = 0; i < pat.size(); i++) {
			String str = pat.get(i);
			if (str.startsWith("#")) {
			} else if (str.trim().length() == 0) {
			} else if (str.startsWith(":")) {
				ri = new RhythmInfo(str, raiseTone).parseColonLine();
				sbilst.add(ri.getSbistr());
				prior = ri.getVoicestr();
				if (repeat > 0) {
					if (repeatbeg == -1) repeatbeg = sbilst.size() - 1;
				}
				// Log.d(prior);
			} else if (str.startsWith(">")) {
				sbilst.add(str.substring(1) + " ");
				// Log.d(sbilst.get(sbilst.size() - 1));
			} else if (str.startsWith("{")) {
				repeat = Integer.valueOf(str.substring(1).trim()) - 1;
			} else if (str.startsWith("}")) {
				repeatend = sbilst.size();
				while (repeat > 0) {
					for (int j = repeatbeg; j < repeatend; j++) {
						sbilst.add(sbilst.get(j));
					}
					repeat--;
				}
				repeatbeg = -1;
				repeatend = -1;
			} else if (str.startsWith("v")) { // semicolon
				sbilst.add("v" + str.substring(1).trim() + " ");
				// Log.d(sbilst.get(sbilst.size() - 1));
			} else if (str.startsWith("=")) {
				 Log.d(str);
				ri = new RhythmInfo(str.substring(1).trim(), raiseTone).parseEqualLine(ri);
				sbilst.set(sbilst.size() - 1, "v" + prior.substring(1, prior.length() - 1) + " " //
						+ ri.getSbistr().replace("\\s+", " "));
				// Log.d(sbilst.get(sbilst.size() - 1));
			} else {
				ri = new RhythmInfo(prior + str, raiseTone).parseColonLine();
				sbilst.add(ri.getSbistr());
				// Log.d(sbilst.get(sbilst.size() - 1));
				// if(sbilst.get(sbilst.size() - 1).trim().startsWith("V0 d6")) {
				// System.out.println(123);
				// }
			}
		}
		StringBuilder sbi = new StringBuilder();
		for (String str : sbilst) {
			sbi.append(str);
		}
		// Log.d(sbi.toString());
		return NotePart.replaceDotBack(sbi.toString());
	}

	// private char[] genRhythm(String string) {
	// return null;
	// }

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
		Log.d(complexPattern);
		player.play(complexPattern);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
