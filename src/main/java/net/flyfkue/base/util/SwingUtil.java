package net.flyfkue.base.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.File;
import java.util.Locale;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * @author pengqianyu 绝对定位swing工具
 */
public class SwingUtil {
	private boolean debug = false;

	public JFrame newjf(String title, boolean resizable, JPanel jp, int... xywh) {
		JFrame jf = new JFrame();
		jf.setTitle(title);
		if (xywh.length == 2) {
			jf.setLocation(xywh[0], xywh[1]);
		} else if (xywh.length == 4) {
			jf.setBounds(xywh[0], xywh[1], xywh[2], xywh[3]);
		}
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setResizable(resizable);
		// 窗口居中
		jf.setLocationRelativeTo(null);
		if (jp == null) {
			jf.getContentPane().setLayout(null);
		} else {
			jf.getContentPane().add(jp);
		}
		return jf;
	}

	public JScrollPane newjsp(Container container, JComponent jta, int... xywh) {
		JScrollPane jsp = new JScrollPane(jta);
		if (xywh.length == 2) {
			jsp.setLocation(xywh[0], xywh[1]);
		} else if (xywh.length == 4) {
			jsp.setBounds(xywh[0], xywh[1], xywh[2], xywh[3]);
			jsp.setPreferredSize(new Dimension(xywh[2], xywh[3]));
		}
		add(container, jsp, null);
		return jsp;
	}

	public JList newjlist(int selectionModel, int defaultSelected, //
			DefaultListModel listModel, int... xywhcr) {
		JList jList = new JList();
		if (selectionModel < 0 || selectionModel > 2)
			jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		else
			jList.setSelectionMode(selectionModel);

		if (listModel != null) {
			jList.setModel(listModel);
			if (!listModel.isEmpty() && defaultSelected > -1) {
				int sz_1 = listModel.size() - 1;
				jList.setSelectedIndex(defaultSelected > sz_1 ? sz_1 : defaultSelected);
			}
		}
		if (xywhcr.length == 2) {
			jList.setLocation(xywhcr[0], xywhcr[1]);
		} else if (xywhcr.length == 4) {
			jList.setBounds(xywhcr[0], xywhcr[1], xywhcr[2], xywhcr[3]);
			jList.setPreferredSize(new Dimension(xywhcr[2], xywhcr[3]));
		}
		return jList;
	}

	public JTextArea newjta(String text, TitledBorder titledBorder, Font ft, //
			boolean editable, boolean wrapStyleWord, boolean wrapLine, int... xywhcr) {
		JTextArea jta = new JTextArea();
		if (xywhcr.length == 2) {
			jta.setLocation(xywhcr[0], xywhcr[1]);
		} else if (xywhcr.length == 4) {
			jta.setBounds(xywhcr[0], xywhcr[1], xywhcr[2], xywhcr[3]);
		} else if (xywhcr.length == 5) {
			jta.setBounds(xywhcr[0], xywhcr[1], xywhcr[2], xywhcr[3]);
			jta.setColumns(xywhcr[4]);
		} else if (xywhcr.length == 6) {
			jta.setBounds(xywhcr[0], xywhcr[1], xywhcr[2], xywhcr[3]);
			jta.setColumns(xywhcr[4]);
			jta.setRows(xywhcr[5]);
		}
		if (titledBorder != null)
			jta.setBorder(titledBorder);
		if (ft != null)
			jta.setFont(ft);
		jta.setEditable(editable);
		jta.setWrapStyleWord(wrapStyleWord);
		jta.setLineWrap(wrapLine);
		if (text != null)
			jta.setText(text);
		if (debug)
			setBackground(jta, Color.green);
		return jta;
	}

	public JTextField newjtf(Container container, GridBagConstraints gbc, LimitedDocument ld, String jtftext,
			Boolean editable, int... xywhc) {
		JTextField jtf = this.newjtf(container, jtftext, editable, xywhc);
		jtf.setDocument(ld);
		jtf.setText(jtftext);
		return jtf;
	}

	public JTextField newjtf(Container container, LimitedDocument ld, String jtftext, Boolean editable, int... xywhc) {
		return newjtf(container, null, ld, jtftext, editable, xywhc);
	}

	public JTextField newjtf(Container container, String jtftext, GridBagConstraints gbc, Boolean editable,
			int... xywhc) {
		JTextField jtf = new JTextField();
		if (xywhc.length == 2) {
			jtf.setLocation(xywhc[0], xywhc[1]);
		} else if (xywhc.length == 4) {
			jtf.setBounds(xywhc[0], xywhc[1], xywhc[2], xywhc[3]);
		} else if (xywhc.length == 5) {
			jtf.setBounds(xywhc[0], xywhc[1], xywhc[2], xywhc[3]);
			jtf.setColumns(xywhc[4]);
		}
		jtf.setText(jtftext);
		if (editable != null)
			jtf.setEditable(editable);
		add(container, jtf, gbc);
		return jtf;
	}

	public JTextField newjtf(Container container, String jtftext, Boolean editable, int... xywhc) {
		return newjtf(container, jtftext, null, editable, xywhc);
	}

	/**
	 * @param selectedIdx
	 *            ex.0b11101表示第0,2,3,4选中
	 */
	public JCheckBox[] newjcb(Container container, boolean singleSelect, int selectedIdx, String... text) {
		JCheckBox[] ret = new JCheckBox[text.length];
		ButtonGroup bg = new ButtonGroup();
		for (int i = 0; i < text.length; i++) {
			JCheckBox jcb = new JCheckBox(text[i]);
			if (singleSelect)
				bg.add(jcb);
			ret[i] = jcb;
			add(container, jcb, null);
		}
		if (text.length < 32) {
			for (int i = 0; i < text.length; i++) {
				if (((selectedIdx >> (text.length - i - 1)) & 0x1) == 1) {
					ret[i].setSelected(true);
				}
			}
		} else {
			System.err.println("Will not set default selections because of too many jcbs: " + text.length);
		}
		return ret;
	}

	public JRadioButton[] newjrb(Container container, int selectedIdx, String... text) {
		JRadioButton[] ret = new JRadioButton[text.length];
		ButtonGroup bg = new ButtonGroup();
		for (int i = 0; i < text.length; i++) {
			JRadioButton jrb = new JRadioButton(text[i]);
			bg.add(jrb);
			ret[i] = jrb;
			add(container, jrb, null);
			if (i == selectedIdx)
				jrb.setSelected(true);
		}
		return ret;
	}

	public JComboBox newjcbb(Container container, int selectedIdx, String... vals) {
		Vector<String> vHigh = new Vector<String>();
		for (String s : vals) {
			vHigh.add(s);
		}
		JComboBox jcb = new JComboBox(vHigh);
		jcb.setSelectedIndex(selectedIdx);
		add(container, jcb, null);
		return jcb;
	}

	public JComboBox newjcbb(Container container, int beg, int end, int selectedIdx) {
		Vector<Integer> vHigh = new Vector<Integer>();
		for (int i = beg; i < end; i++) {
			vHigh.add(i);
		}
		JComboBox jcb = new JComboBox(vHigh);
		jcb.setSelectedIndex(selectedIdx);
		add(container, jcb, null);
		return jcb;
	}

	public JCheckBox newjcb(Container container, String jcbtext, boolean defaultSelected, int... xywh) {
		JCheckBox jcb = new JCheckBox(jcbtext);
		jcb.setSelected(defaultSelected);
		if (xywh.length == 2) {
			jcb.setLocation(xywh[0], xywh[1]);
		} else if (xywh.length == 4) {
			jcb.setBounds(xywh[0], xywh[1], xywh[2], xywh[3]);
		}
		add(container, jcb, null);
		return jcb;
	}

	public JLabel newjl(Container container, GridBagConstraints gbc, int align, String jltext, int... xywh) {
		JLabel jl = new JLabel(jltext);
		if (xywh.length == 2) {
			jl.setLocation(xywh[0], xywh[1]);
		} else if (xywh.length == 4) {
			jl.setBounds(xywh[0], xywh[1], xywh[2], xywh[3]);
		}
		if (align >= 0 && align <= 4)
			jl.setHorizontalAlignment(align);
		add(container, jl, gbc);
		return jl;
	}

	public JLabel newjl(Container container, GridBagConstraints gbc, String jltext, int... xywh) {
		if (gbc == null)
			return newjl(container, gbc, SwingConstants.LEFT, jltext, xywh);
		return newjl(container, gbc, SwingConstants.RIGHT, jltext, xywh);
	}

	public JLabel newjl(Container container, String jltext, int... xywh) {
		return newjl(container, null, jltext, xywh);
	}

	public GridBagConstraints setGbc(GridBagConstraints gbc, int... gwf) {
		gbc.anchor = GridBagConstraints.WEST;
		gbc.weightx = 1.0; // 水平权重
		gbc.weighty = 1.0; // 垂直权重
		if (gwf.length == 2) {
			gbc.gridx = gwf[0]; // 列索引
			gbc.gridy = gwf[1]; // 行索引
			gbc.gridwidth = 1;
			gbc.gridheight = 1;
		} else if (gwf.length == 4) {
			gbc.gridx = gwf[0];
			gbc.gridy = gwf[1];
			gbc.gridwidth = gwf[2];
			gbc.gridheight = gwf[3];
		}
		if (gwf.length == 5) {
			if (gwf[4] >= 0 && gwf[4] <= 3)
				gbc.fill = gwf[4]; // 填充整个单元格
		} else {
			gbc.fill = GridBagConstraints.BOTH; // 填充整个单元格
		}
		return gbc;
	}

	public void add(Container container, JComponent jc, GridBagConstraints gbc) {
		if (container == null || jc == null)
			return;
		if (gbc == null) {
			container.add(jc);
		} else {
			container.add(jc, gbc);
		}
	}

	/**
	 * @param jth
	 * @param font
	 *            字体
	 * @param foregroudColor
	 *            前景色
	 * @param backgroundColor
	 *            背景色
	 * @param resizingAllowed
	 *            是否允许手动改变列宽
	 * @param reorderingAllowed
	 *            是否允许拖动重新排序各列
	 */
	public void setJtHeaderStyle(JTableHeader jth, Font font, Color foregroudColor, Color backgroundColor, //
			Boolean resizingAllowed, Boolean reorderingAllowed) {
		// 设置表头
		if (font != null)
			jth.setFont(font);
		if (foregroudColor != null)
			jth.setForeground(foregroudColor);
		if (backgroundColor != null)
			jth.setBackground(backgroundColor);
		if (resizingAllowed != null)
			jth.setResizingAllowed(resizingAllowed);
		if (reorderingAllowed != null)
			jth.setReorderingAllowed(reorderingAllowed);
	}

	/**
	 * @param jt
	 * @param font
	 *            字体
	 * @param foregroudColor
	 *            前景色
	 * @param backgroundColor
	 *            背景色
	 * @param gridColor
	 *            网格色
	 * @param selectionForegroundColor
	 *            选中行前景色
	 * @param selectionBackgroundColor
	 *            选中行背景色
	 */
	public void setJtStyle(JTable jt, Font font, Color foregroudColor, Color backgroundColor, //
			Color gridColor, Color selectionForegroundColor, Color selectionBackgroundColor) {
		if (foregroudColor != null)
			jt.setForeground(foregroudColor);
		if (backgroundColor != null)
			jt.setBackground(backgroundColor);
		if (font != null)
			jt.setFont(font);
		if (selectionForegroundColor != null)
			jt.setSelectionForeground(selectionForegroundColor);
		if (selectionBackgroundColor != null)
			jt.setSelectionBackground(selectionBackgroundColor);
		if (gridColor != null)
			jt.setGridColor(gridColor);
	}

	/**
	 * @param container
	 * @param rowHeight
	 *            行高
	 * @param setRowSorter
	 *            是否排序
	 * @param mtm
	 *            TableModel
	 * @param columnMinWidth
	 *            最小宽度
	 * @param columnMaxWidth
	 *            最大宽度
	 * @param xywh
	 *            bounds
	 * @return
	 */
	public JTable newjt(Container container, int rowHeight, boolean setRowSorter, MyTableModel mtm, //
			int[] columnMinWidth, int[] columnMaxWidth, int... xywh) {
		JTable jt = new JTable(mtm);
		// 设置行高
		if (rowHeight > 0)
			jt.setRowHeight(rowHeight);
		if (columnMinWidth != null) {
			for (int i = 0; i < columnMinWidth.length; i++) {
				if (columnMinWidth[i] > 0) {
					jt.getColumnModel().getColumn(i).setMinWidth(columnMinWidth[i]);
				}
			}
		}
		if (columnMaxWidth != null) {
			for (int i = 0; i < columnMaxWidth.length; i++) {
				if (columnMaxWidth[i] > 0) {
					jt.getColumnModel().getColumn(i).setMaxWidth(columnMaxWidth[i]);
				}
			}
		}
		if (setRowSorter) {
			RowSorter<TableModel> rowSorter = new TableRowSorter<TableModel>(mtm);
			jt.setRowSorter(rowSorter);
		}
		newjsp(container, jt, xywh);
		// JScrollPane scrollPane = new JScrollPane(jt);
		// if (xywh.length == 2) {
		// scrollPane.setLocation(xywh[0], xywh[1]);
		// } else if (xywh.length == 4) {
		// scrollPane.setBounds(xywh[0], xywh[1], xywh[2], xywh[3]);
		// }
		// if (container != null)
		// container.add(scrollPane);
		return jt;
	}

	// 下面的三个参数来自JFileChooser.java,多写一遍增加易读性
	public static final int FILES_ONLY = JFileChooser.FILES_ONLY;
	public static final int DIRECTORIES_ONLY = JFileChooser.DIRECTORIES_ONLY;
	public static final int FILES_AND_DIRECTORIES = JFileChooser.FILES_AND_DIRECTORIES;

	private boolean checkSCE(String fname, int fileNameLenLow, int fileNameLenHigh, //
			final String[] acceptStarts, final String[] acceptContains, final String[] acceptEnds) {
		// 先判断文件名长度是否在[fileNameLenLow, fileNameLenHigh]区间
		int len = fname.length();
		if (fileNameLenLow != -1 && len < fileNameLenLow)
			return false;
		if (fileNameLenHigh != -1 && len > fileNameLenHigh)
			return false;

		// 再判断文件名结构是否符合条件
		if (acceptEnds != null) {
			for (String ae : acceptEnds) {
				if (fname.endsWith(ae)) {
					return true;
				}
			}
			return false;
		}
		if (acceptStarts != null) {
			for (String as : acceptStarts) {
				if (fname.startsWith(as)) {
					return true;
				}
			}
			return false;
		}
		if (acceptContains != null) {
			for (String as : acceptContains) {
				if (fname.contains(as)) {
					return true;
				}
			}
			return false;
		}
		return true;
	}

	// 只遍历直接子目录(1层)
	public File[] listFiles(File currDir, final int mode, final int fileNameLenLow, final int fileNameLenHigh, //
			final String[] acceptStarts, final String[] acceptContains, final String[] acceptEnds) {
		File[] fl = currDir.listFiles(new java.io.FileFilter() {
			@Override
			public boolean accept(File f) {
				if (mode == FILES_ONLY && !f.isFile())
					return false;
				if (mode == DIRECTORIES_ONLY && !f.isDirectory())
					return false;
				// 待测试
				if (acceptStarts == null && acceptContains == null && acceptEnds == null)
					return true;
				return checkSCE(f.getName().toLowerCase(Locale.US), fileNameLenLow, fileNameLenHigh, //
						acceptStarts, acceptContains, acceptEnds);
			}
		});
		return fl;
	}

	public JFileChooser newjfc(int mode, Boolean multiSelectionEnabled, File currDir, final String desc, //
			final String[] acceptStarts, final String[] acceptContains, final String[] acceptEnds) {
		return newjfc(mode, multiSelectionEnabled, currDir, desc, -1, -1, //
				acceptStarts, acceptContains, acceptEnds);
	}

	public JFileChooser newjfc(int mode, Boolean multiSelectionEnabled, File currDir, final String desc, //
			final int fileNameLenLow, final int fileNameLenHigh, //
			final String[] acceptStarts, final String[] acceptContains, final String[] acceptEnds) {
		JFileChooser jfc = new JFileChooser();
		if (mode < 0) {
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		} else {
			jfc.setFileSelectionMode(mode);
		}

		if (multiSelectionEnabled != null)
			jfc.setMultiSelectionEnabled(multiSelectionEnabled);

		if (currDir == null) {
			jfc.setCurrentDirectory(new File("."));
		} else {
			jfc.setCurrentDirectory(currDir);
		}
		jfc.setFileFilter(new javax.swing.filechooser.FileFilter() {
			@Override
			public String getDescription() {
				return desc;
			}

			@Override
			public boolean accept(File f) {
				if (f.isDirectory())
					return true;
				return checkSCE(f.getName().toLowerCase(Locale.US), fileNameLenLow, fileNameLenHigh, //
						acceptStarts, acceptContains, acceptEnds);
			}
		});
		// 不能在这里执行,在这里执行的结果外面不会收到值,会导致无意义的打开一次选择框
		// jfc.showOpenDialog(frame);
		return jfc;
	}

	public JSeparator newjs(Container container, boolean vertical, int... xywh) {
		JSeparator js = new JSeparator();
		if (vertical) {
			js.setOrientation(JSeparator.VERTICAL);
		} else {
			js.setOrientation(JSeparator.HORIZONTAL);
		}
		if (xywh.length == 2) {
			js.setLocation(xywh[0], xywh[1]);
		} else if (xywh.length == 4) {
			js.setBounds(xywh[0], xywh[1], xywh[2], xywh[3]);
		}
		add(container, js, null);
		return js;
	}

	public LimitedDocument newld(int maxLength, String allowChars) {
		LimitedDocument ld = new LimitedDocument(maxLength);
		if (allowChars != null)
			ld.setAllowChar(allowChars);
		return ld;
	}

	public JButton newjb(Container container, String jbtext, int... xywh) {
		return newjb(container, null, jbtext, null, xywh);
	}

	public JButton newjb(Container container, GridBagConstraints gbc, String jbtext, int... xywh) {
		return newjb(container, gbc, jbtext, null, xywh);
	}

	public JButton newjb(Container container, String jbtext, String tipText, int... xywh) {
		return newjb(container, null, jbtext, tipText, xywh);
	}

	public JButton newjb(Container container, GridBagConstraints gbc, String jbtext, String tipText, int... xywh) {
		JButton jb = new JButton(jbtext);
		if (tipText != null)
			jb.setToolTipText(tipText);
		if (xywh.length == 2) {
			jb.setLocation(xywh[0], xywh[1]);
		} else if (xywh.length == 4) {
			jb.setBounds(xywh[0], xywh[1], xywh[2], xywh[3]);
			jb.setPreferredSize(new Dimension(xywh[2], xywh[3]));
		}
		add(container, jb, gbc);
		return jb;
	}

	public void setBackgroundOfContentPane(JFrame sub, Color color) {
		// sub.setOpacity(0.0f);
		// sub.getRootPane().setBackground(color); // 无效
		sub.getContentPane().setBackground(color);
	}

	public void setBackground(JComponent sub, Color color) {
		// 测试通过的:jlabel,jbutton
		// sub.setOpaque(true);
		sub.setBackground(color);
	}

	public JPanel[] addLayout(Container con, String layouts, String type) {
		return addLayout(con, null, layouts, type, null);
	}

	public JPanel[] addLayout(Container con, String layouts, String type, Color c) {
		return addLayout(con, null, layouts, type, null);
	}

	public JPanel[] addLayout(Container con, GridBagConstraints gbc, String layouts, String type) {
		return addLayout(con, gbc, layouts, type, null);
	}

	public JPanel[] addLayout(Container con, GridBagConstraints gbc, String layouts, String type, Color c) {
		char[] ca = layouts.toUpperCase(Locale.US).toCharArray();
		type = type.toUpperCase(Locale.US);
		JPanel[] ret = new JPanel[ca.length];
		for (int i = 0; i < ret.length; i++) {
			switch (ca[i]) {
			case 'B': // BorderLayout
				ret[i] = new JPanel(new BorderLayout());
				if (c != null)
					ret[i].setBackground(c);
				break;
			case 'F': // FlowLayout
				ret[i] = new JPanel(new FlowLayout());
				if (c != null)
					ret[i].setBackground(c);
				break;
			case 'G': // GridLayout
				ret[i] = new JPanel(new GridLayout());
				if (c != null)
					ret[i].setBackground(c);
				break;
			case 'C': // GridBagLayout+GridBagConstraints
				ret[i] = new JPanel(new GridBagLayout());
				if (c != null)
					ret[i].setBackground(c);
				break;
			default:
				ret[i] = null;
				break;
			}
		}
		char tp = type.charAt(0);
		String[] sa = new String[] { BorderLayout.NORTH, BorderLayout.WEST, //
				BorderLayout.SOUTH, BorderLayout.EAST, BorderLayout.CENTER };
		for (int i = 0; i < ret.length; i++) {
			if (ret[i] == null)
				continue;
			switch (tp) {
			case 'B': // BorderLayout
				con.add(ret[i], sa[i]);
				break;
			case 'C': // BorderLayout
				if (gbc == null)
					con.add(ret[i]);
				else
					add(con, ret[i], gbc);
				break;
			default:
				con.add(ret[i]);
				break;
			}
		}
		return ret;
	}

}
