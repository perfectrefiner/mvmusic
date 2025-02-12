package net.flyfkue.base.util;

/**
 * ��Android�н����滻Ϊ android.graphics.Rect
 * 
 * @author Administrator
 * 
 */
public class RectF {
	public RectF() {
	}

	public RectF(Rect rect) {
		left = rect.left;
		right = rect.right;
		top = rect.top;
		bottom = rect.bottom;
	}

	public float left;
	public float right;
	public float top;
	public float bottom;

}
