package effects;

import app.MainWindow;
import processing.core.PVector;

public class Explosion {
	PVector pos;
	float r;

	public Explosion(float x, float y) {
		pos = new PVector(x, y);
		r = 1;
	}

	public boolean update() {
		r *= 1.2;
		return (r >= 255);
	}

	public void show(MainWindow app) {
		app.stroke(255, 200, 15);
		app.fill(255, 100, 20, 255 - r);
		app.ellipse(pos.x, pos.y, r * 2, r * 2);
	}
}