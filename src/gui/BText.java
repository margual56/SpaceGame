package gui;

import app.MainWindow;
import processing.core.PApplet;
import processing.core.PVector;

class BText {
	private String text = "";
	private float size = 40;
	private float x, y;
	private int col;
	protected MainWindow app;

	public BText(float x, float y, String txt, float size, MainWindow app) {
		this.app = app;
		col = app.color(0);
		this.x = x;
		this.y = y;
		this.text = txt;
		this.size = size;
	}

	public String getText() {
		return text;
	}

	public void setText(String txt) {
		this.text = txt;
	}

	public PVector getPos() {
		return new PVector(x, y);
	}

	public void setPos(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public int getColor() {
		return col;
	}

	public void setColor(float r, float g, float b, float a) {
		r = PApplet.constrain(r, 0, 255);
		g = PApplet.constrain(g, 0, 255);
		b = PApplet.constrain(b, 0, 255);
		a = PApplet.constrain(a, 0, 255);

		col = app.color(r, g, b, a);
	}

	public void setColor(float c) {
		c = PApplet.constrain(c, 0, 255);

		col = app.color(c);
	}

	public void setColor(float c, float a) {
		c = PApplet.constrain(c, 0, 255);
		a = PApplet.constrain(a, 0, 255);

		col = app.color(c, a);
	}

	public void show() {
		app.textSize(size);

		app.fill(col);
		app.text(text, x, y);
	}
}
