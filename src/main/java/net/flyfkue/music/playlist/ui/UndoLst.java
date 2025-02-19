package net.flyfkue.music.playlist.ui;

import java.util.LinkedList;

import javax.swing.JTextArea;

import net.flyfkue.base.log.Log;

public class UndoLst {
	private LinkedList<String> lst = new LinkedList<>();
	private int curridx = 0;
	private int sz = 100;
	private boolean addundo = true;

	private void removeInvalidUndo() {
		if (curridx == lst.size()) return;
		if (curridx > lst.size()) throw new RuntimeException("why here?" + curridx + ", " + lst.size());
//		Log.d(curridx + ", " + lst.size());
		while (curridx < lst.size()) {
			lst.removeLast();
		}
	}

	public void addUndo(String str) {
		if (!addundo) return;
		if (str == null) return;
		removeInvalidUndo();
		if (curridx < sz) {
			if (curridx > 0) {
				if (lst.get(curridx - 1).equals(str)) {
					return;
				} else {
					lst.add(str);
					curridx++;
				}
			} else {
				lst.add(str);
				curridx++;
			}
		} else {
			lst.removeFirst();
			curridx--;
			addUndo(str);
		}
	}

	public void redo(JTextArea jtaRes) {
		if (curridx >= lst.size()) return;

		addundo = false;
		jtaRes.setText(lst.get(curridx));
		curridx++;
		addundo = true;
	}

	public void undo(JTextArea jtaRes) {
		if (curridx == 0) return;

		addundo = false;
		jtaRes.setText(lst.get(curridx - 1));
		curridx--;
		addundo = true;
	}

}
