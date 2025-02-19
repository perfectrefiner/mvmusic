package net.flyfkue.base.util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class LimitedDocument extends PlainDocument {

	private int		_maxLength			= -1;
	private String	_allowCharAsString	= null;

	public LimitedDocument() {
		super();
	}

	public LimitedDocument(int maxLength) {
		super();
		this._maxLength = maxLength;
	}

	public void insertString(int offset, String str, AttributeSet attrSet) throws BadLocationException {
		if (str == null) {
			return;
		}
		if (_allowCharAsString != null) {
			if (str.length() == 1) {
				if (_allowCharAsString.indexOf(str) == -1) {
					return;
				}
			} else {
				StringBuffer newstr = new StringBuffer();
				char[] ca = str.toCharArray();
				for (int i = 0; i < ca.length; i++) {
					if (_allowCharAsString.indexOf(ca[i]) != -1) {
						newstr.append(ca[i]);
					}
				}
				if (newstr.length() == 0) {
					return;
				}
				str = newstr.toString();
			}

		}
		char[] charVal = str.toCharArray();
		String strOldValue = getText(0, getLength());
		byte[] tmp = strOldValue.getBytes();
		if (_maxLength != -1 && (tmp.length + charVal.length > _maxLength)) {
			return;
		}
		super.insertString(offset, str, attrSet);
	}

	public void setAllowChar(String str) {
		_allowCharAsString = str;
	}
}