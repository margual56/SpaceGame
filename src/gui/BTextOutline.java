package gui;

import app.MainWindow;
import processing.core.PConstants;
import processing.core.PVector;

class BTextOutline extends BText {
	private int stroke = 1;
	private int strokeCol = 0;

	public BTextOutline(float x, float y, String txt, float size, MainWindow app) {
		super(x, y, txt, size, app);
	}

	public BTextOutline(float x, float y, String txt, float size, int stroke, MainWindow app) {
		super(x, y, txt, size, app);

		setStrokeWid(stroke);
	}

	public BTextOutline(float x, float y, String txt, float size, int stroke, int c, MainWindow app) {
		super(x, y, txt, size, app);

		setStrokeWid(stroke);
		this.strokeCol = c;
	}

	public int strokeColor() {
		return strokeCol;
	}

	public void strokeColor(float r, float g, float b, float a) {
		strokeCol = app.color(r, g, b, a);
	}

	public int getStroke() {
		return stroke;
	}

	public void setStrokeWid(int s) {
		stroke = s / 2;
	}

	@Override
	public void show() {
		app.textSize(getSize());
		app.textAlign(PConstants.CENTER, PConstants.CENTER);

		PVector p = getPos();

		app.fill(strokeCol);
		for (int i = -stroke; i <= stroke; i++)
			for (int j = -stroke; j <= stroke; j++)
				if (i != j)
					app.text(getText(), p.x + i, p.y + j);

		app.fill(getColor());
		app.text(getText(), p.x, p.y);
	}
}