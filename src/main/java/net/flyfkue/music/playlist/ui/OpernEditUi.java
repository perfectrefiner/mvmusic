package net.flyfkue.music.playlist.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Locale;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.flyfkue.base.log.Log;
import net.flyfkue.base.util.JavaUtil;
import net.flyfkue.base.util.SwingUtil;
import net.flyfkue.music.playlist.NotePart;

public class OpernEditUi {
	private int framewidth = 830;
	private int frameheight = 500;
	private static OpernEditUi self;
	private JFrame frame;
	private SwingUtil su = new SwingUtil();
	private JTextField textField;
	private JTextArea jtaRes;
	private UndoLst ul = new UndoLst();
	private JRadioButton[] jrb;
	private static final int SUM = 0, MUL = 1;

	private void ui() {
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		final JPanel panel = new JPanel(new BorderLayout());
		frame = su.newjf("opern工具", true, panel, 0, 0, framewidth, frameheight);

		JPanel[] pa = su.addLayout(panel, "BnBnB", "BorderLayout");
		JPanel[] pan = su.addLayout(pa[0], "FnnnF", "BorderLayout");
		JPanel[] pac = su.addLayout(pa[4], "CnnnF", "BorderLayout");
		JPanel[] pacc = su.addLayout(pac[4], "BnBnB", "BorderLayout");
		// JPanel[] pn2 = su.addLayout(pn[2], "CnFnF", "BorderLayout");
		GridBagConstraints gbc = new GridBagConstraints();

		JButton jb1 = su.newjb(pan[0], "del|", "删除|,alt+m");
		// jb1.setMnemonic('d');
		jrb = su.newjrb(pan[0], 0, "3'", "4'");
		JButton jb2 = su.newjb(pan[0], "add|", "加|");
		JButton jb5 = su.newjb(pan[0], "uncmnt", "有问题");
		JButton jb3 = su.newjb(pan[0], "undo", "撤销,ctrl+z");
		JButton jb4 = su.newjb(pan[0], "redo", "重做,ctrl+y");

		jtaRes = su.newjta("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n", new TitledBorder("opern"), new Font("Serif", 0, 16), true, //
				true, true, 0, 0, 0, 0, 69, 15);
		jtaRes.setLineWrap(true);
		su.newjsp(pacc[4], jtaRes, 5, 0);

		action(jb1, jb2, jb3, jb4, jb5);
	}

	private void bindShortcuts() {
		jtaRes.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				ul.addUndo(jtaRes.getText());
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				ul.addUndo(jtaRes.getText());
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				ul.addUndo(jtaRes.getText());
			}
		});
		jtaRes.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char ch = e.getKeyChar();
				if (NotePart.getRhythmType(ch) == 1 || ch == ' ' || ch == '-' || ch == '=') {
					int beg = jtaRes.getSelectionStart();
					int end = jtaRes.getSelectionEnd();
					if (beg != end) {
						String str;
						if (ch == '-' || ch == '=') { // 降八度 升八度
							str = add8height(jtaRes.getSelectedText(), ch);
						} else {
							str = addChar(jtaRes.getSelectedText(), ch);
						}
						str = str.replaceAll(" +", " ");
						String proir = jtaRes.getText().substring(0, beg);
						String suff = jtaRes.getText().substring(end);
						jtaRes.setText(proir + str + suff);
						jtaRes.setSelectionStart(beg);
						jtaRes.setSelectionEnd(beg + str.length());
						jtaRes.requestFocusInWindow();
						ul.addUndo(jtaRes.getText());
						e.consume();
					} else { // do nothing
					}
				} else {

				}
			}
		});

		// 获取InputMap和ActionMap
		InputMap inputMap = jtaRes.getInputMap(JComponent.WHEN_FOCUSED);
		ActionMap actionMap = jtaRes.getActionMap();

		// 绑定Ctrl+Z快捷键
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK), "undo");
		actionMap.put("undo", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ul.undo(jtaRes);
			}
		});

		// 绑定Ctrl+Y快捷键
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK), "redo");
		actionMap.put("redo", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ul.redo(jtaRes);
			}
		});

		// 绑定Ctrl+/快捷键
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SLASH, InputEvent.CTRL_DOWN_MASK), "redo");
		actionMap.put("redo", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				comment(e);
			}
		});

	}

	private String addBlank(String str) {
		StringBuilder ret = new StringBuilder();
		char[] ca = str.toCharArray();
		for (int i = 0; i < ca.length; i++) {
			if (JavaUtil.isDigit(ca[i])) {
				ret.append(ca[i] + " ");
			} else {
				ret.append(ca[i]);
			}
		}
		return ret.toString();
	}

	private void action(JButton jb1, JButton jb2, JButton jb3, JButton jb4, JButton jb5) {
		ul.addUndo(jtaRes.getText());
		bindShortcuts();
		jb1.addActionListener(this::removeSplit);
		jb2.addActionListener(this::addSplit);
		// jb5.addActionListener(this::unComment);
		jb3.addActionListener(e -> {
			ul.undo(jtaRes);
		});
		jb4.addActionListener(e -> {
			ul.redo(jtaRes);
		});
	}

	private void removeSplit(ActionEvent e) {
		String str = jtaRes.getText();
		str = str.replace('|', ' ');
		str = str.replaceAll(" +", " ");
		jtaRes.setText(str);
	}

	private int indexof(char[] ris, char ch) {
		for (int i = 0; i < ris.length; i++) {
			if (ris[i] == ch) return i;
		}
		return -1;
	}

	private void addSplit(ActionEvent e) {
		removeSplit(e);
		int sep = jrb[0].isSelected() ? 3 : 4;
		int ri = 512;
		char[] ris = NotePart.rhythms;
		int max = sep * (ri >> 1); // 3'是三个h不是三个w
		int[] res = new int[] { 0, 0 };
		String str = jtaRes.getText();
		str = str.toLowerCase(Locale.US);
		StringBuilder sbi = new StringBuilder();
		String[] sa = str.split("\\s+");
		for (int i = 0; i < sa.length; i++) {
			if (sa[i].trim().length() == 0) continue;
			res[MUL] = 0;
			int idx = sa[i].length() - 1;
			if (sa[i].charAt(idx) == '.') {
				res[MUL] += (ri >> 1);
				idx--;
			}
			if (sa[i].charAt(idx) == '#' || sa[i].charAt(idx) == 'b' || JavaUtil.isDigit(sa[i].charAt(idx))) {
				addSplit_1(ri, max, res, sbi, sa, i, 0);
				continue;
			}
			int ii = indexof(ris, sa[i].charAt(idx));
			if (ii > -1) {
				addSplit_1(ri, max, res, sbi, sa, i, ii);
				continue;
			}
			throw new RuntimeException("what: " + sa[i]);
		}
		jtaRes.setText(sbi.toString());
	}

	private void addSplit_1(int ri, int max, int[] res, StringBuilder sbi, String[] sa, int i, int ii) {
		res[MUL] = (res[MUL] >> ii) + (ri >> ii);
		res[SUM] += res[MUL];
		if (res[SUM] >= max) {
			sbi.append(sa[i] + " |\n ");
			res[SUM] = 0;
			res[MUL] = 0;
		} else {
			sbi.append(sa[i] + " ");
		}
	}

	/**
	 * @deprecated 有问题
	 */
	private void comment(ActionEvent e) {
		int beg = jtaRes.getSelectionStart();
		int end = jtaRes.getSelectionEnd();
		if (beg == end) return;
		String[] sa = jtaRes.getText().split("\n");
		int len = 0;
		StringBuilder sbi = new StringBuilder();
		for (int i = 0; i < sa.length; i++) {
			if (len >= beg && len <= end) {
				sbi.append("#    " + sa[i] + "\n");
			} else {
				sbi.append(sa[i] + "\n");
			}
			len += sa[i].length();
		}
		jtaRes.setText(sbi.toString());
		ul.addUndo(jtaRes.getText());
	}

	private String add8height(String str, char ch) {
		// if (checkBlank(str)) throw new RuntimeException("format error, need: 15 15q 1 0 , get: " + str);
		int add = 1;
		str = str.toLowerCase(Locale.US);
		if (ch == '-') add = -1;
		StringBuilder ret = new StringBuilder();
		String[] sa = str.split(" +");
		for (int i = 0; i < sa.length; i++) {
			if (sa[i].trim().length() == 0) continue;
			char c = sa[i].charAt(0);
			if (c == '0') {
				ret.append(" " + sa[i] + " ");
				continue;
			}
			// 要是传个8,那也没办法
			if (!JavaUtil.isDigit(c)) throw new RuntimeException("format error, need: 14 16q 1 0 , get: " + str);
			if (sa[i].length() == 1) { // 1
				ret.append(" " + sa[i] + (5 + add) + " ");
				continue;
			}
			c = sa[i].charAt(1);
			if (JavaUtil.isDigit(c)) { // 16q.
				ret.append(" " + sa[i].substring(0, 1) + (c - '0' + add) + sa[i].substring(2) + " ");
				continue;
			}
			if (c == '#' || c == 'b') {
				if (sa[i].length() == 2) { // 2b
					ret.append(" " + sa[i] + (5 + add) + " ");
					continue;
				} else {
					c = sa[i].charAt(2);
					if (JavaUtil.isDigit(c)) { // 2b3
						ret.append(" " + sa[i].substring(0, 2) + (c - '0' + add) + sa[i].substring(3) + " ");
						continue;
					} else { // 2bh.
						ret.append(" " + sa[i].substring(0, 2) + (5 + add) + sa[i].substring(2) + " ");
						continue;
					}
				}
			}
			ret.append(" " + sa[i].substring(0, 1) + (5 + add) + sa[i].substring(1) + " "); // 2h.
		}
		return ret.toString().replaceAll(" +", " ");
	}

	private String addChar(String str, char ch) {
		StringBuilder ret = new StringBuilder();
		char[] ca = str.toCharArray();
		for (char c : ca) {
			if (JavaUtil.isDigit(c)) {
				ret.append(" " + c + ch + " ");
			} else {
				ret.append(c);
			}
		}
		return ret.toString();
	}

	public static void main(String[] args) {
		Log.add(Log.DEBUG | Log.VERBOSE);
		Log.setLogFile();
		Log.reset();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					self = new OpernEditUi();
					// self.test();
					self.frame.setVisible(true);
				} catch (Exception e) {
					Log.e(e.getMessage());
					e.printStackTrace();
				}
			}
		});
	}

	public OpernEditUi() {
		ui();
	}

}
