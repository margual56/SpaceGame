package game;

import java.util.ArrayList;

import app.MainWindow;
import processing.core.PConstants;
import processing.core.PVector;

public class Player {
	PVector pos, acc, vel = new PVector(0, 0);
	float a = 0;
	float spd = 0.3f;// *18/60;
	float size;
	Boolean go = true;
	private MainWindow app;

	public Player(float x, float y, float size, MainWindow app) {
		this.app = app;
		
		pos = new PVector(x, y);

		this.size = size;

		acc = PVector.fromAngle(a, new PVector(1, 1).mult(spd));
	}

	public void update(Enemy[] enem) {
		if (!app.getLost()) {
			if (go) {
				acc = PVector.fromAngle(a, acc).setMag(spd);
				vel.add(acc);
			} else {
				vel.mult(0.6f);
			}

			pos.add(vel);

			app.setLost(pos.x < 0 || pos.x > app.width || pos.y < 0 || pos.y > app.height || collidedWith(enem));
		}
	}

	boolean collidedWith(Enemy[] list) {
		for (Enemy n : list) {
			if (PVector.dist(pos, n.getPos()) < size * 1.5)
				return true;
		}

		return false;
	}

	boolean collidedWith(ArrayList<Enemy> list) {
		for (Enemy n : list) {
			if (PVector.dist(pos, n.getPos()) < size * 1.5)
				return true;
		}

		return false;
	}

	void update(Enemy[] en, float spdMult, MainWindow app) {
		spd += spdMult;

		if (!app.getLost()) {
			if (go) {
				acc = PVector.fromAngle(a, acc).setMag(spd);
				vel.add(acc);
			} else {
				vel.mult(0.6f);
			}

			pos.add(vel);

			app.setLost(pos.x < 0 || pos.x > app.width || pos.y < 0 || pos.y > app.height || collidedWith(en));
		}
	}

	public void show(MainWindow app) {
		app.stroke(255);
		app.fill(100);
		app.pushMatrix();
		app.translate(pos.x, pos.y);
		app.rotate(a + PConstants.PI / 2);
		app.triangle(0, -size * 2, size, size, -size, size);
		app.popMatrix();
	}
	
	public PVector getPos() {
		return pos;
	}

	public void setPos(PVector pos) {
		this.pos = pos;
	}

	public PVector getAcc() {
		return acc;
	}

	public void setAcc(PVector acc) {
		this.acc = acc;
	}

	public PVector getVel() {
		return vel;
	}

	public void setVel(PVector vel) {
		this.vel = vel;
	}

	public float getA() {
		return a;
	}

	public void setA(float a) {
		this.a = a;
	}

	public float getSpd() {
		return spd;
	}

	public void setSpd(float spd) {
		this.spd = spd;
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public void addA(float f) {
		a += f;
	}

	public void multVel(float f) {
		vel.mult(f);
	}
}
