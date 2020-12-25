package gui;

import processing.core.PVector;

class Button {
	private float x1, y1, x2, y2;
	private BText text = null;

	public Button(float x, float y, float w, float h) {
		this.x1 = x - w / 2;
		this.y1 = y - h / 2;
		this.x2 = x + w / 2;
		this.y2 = y + h / 2;
	}

	public Button(float x, float y, float w, float h, BText t) {
		this.x1 = x - w / 2;
		this.y1 = y - h / 2;
		this.x2 = x + w / 2;
		this.y2 = y + h / 2;

		this.text = t;
	}

	public void show() {
		if (text != null)
			text.show();
	}

	public float getWidth() {
		return x1 - x2;
	}

	public void setWidth(float wid) {
		wid -= getWidth();

		x1 -= wid / 2;
		x2 += wid / 2;
	}

	public float getHeight() {
		return y1 - y2;
	}

	public void setHeight(float hei) {
		hei -= getHeight();

		y1 -= hei / 2;
		y2 += hei / 2;
	}

	public float getX() {
		return x1 + getWidth() / 2;
	}

	public void setX(float newX) {
		newX -= getX();

		x1 += newX;
		x2 += newX;
	}

	public float getY() {
		return y1 + getHeight() / 2;
	}

	public void setY(float newY) {
		newY -= getY();

		y1 += newY;
		y2 += newY;
	}

	public PVector getPos() {
		return new PVector(getX(), getY());
	}

	public void setPos(PVector newPos) {
		setX(newPos.x);
		setY(newPos.y);
	}

	public BText getText() {
		return text;
	}

	public void setText(BText t) {
		this.text = t;
	}
}