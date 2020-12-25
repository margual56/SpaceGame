package game;

import app.MainWindow;
import effects.Explosion;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Enemy {
	PVector pos, acc, vel = new PVector(0, 0);
	float a = 0;
	float spd;
	float prevDist = Float.POSITIVE_INFINITY;
	Boolean dead = false;

	public Enemy(float x, float y, MainWindow app) {
		pos = new PVector(x, y);
		spd = app.random(minSpd(app), maxSpd(app));
		acc = PVector.fromAngle(a, new PVector(1, 1).mult(spd));
	}

	public void update(Player pl, MainWindow app) {
		float d = PVector.dist(pos, pl.pos);
		a = PApplet.atan2(pl.pos.y - pos.y, pl.pos.x - pos.x);
		if (d < prevDist) {
			acc = PVector.fromAngle(a, acc).setMag(spd);
		} else {
			vel.mult(0.95f);
			acc = PVector.fromAngle(a, acc).setMag(spd * 2);
		}
		vel.add(acc);
		pos.add(vel);
		prevDist = d;

		dead = pos.x < 0 || pos.x > app.width || pos.y < 0 || pos.y > app.height;
		if (dead) {
			app.addDeathEffect(new Explosion(PApplet.constrain(pos.x, 0, app.width), PApplet.constrain(pos.y, 0, app.height)));
			app.addScoreEffect(new Score(PApplet.constrain(pos.x, 0, app.width), PApplet.constrain(pos.y, 0, app.height), (int) (spd * 1000), app));
			app.addScore((int) (spd * 1000));
		}
	}

	public void show(float size, MainWindow app) {
		app.stroke(255, 0, 0);
		app.fill(255, 0, 0);
		app.pushMatrix();
		app.translate(pos.x, pos.y);
		app.rotate(a + PConstants.PI / 2);
		app.triangle(0, -size * 2, size, size, -size, size);
		app.popMatrix();
	}

	public boolean isAlive() {
		return !dead;
	}

	private float minSpd(MainWindow app) {
		return PApplet.map(app.getDifficulty(), 1, 200, 0.2f, 0.005f);
	}

	private float maxSpd(MainWindow app) {
		return PApplet.map(app.getDifficulty(), 1, 200, 0.6f, 0.0056f);
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

	public float getPrevDist() {
		return prevDist;
	}

	public void setPrevDist(float prevDist) {
		this.prevDist = prevDist;
	}

	public Boolean isDead() {
		return dead;
	}

	public void setDead(Boolean dead) {
		this.dead = dead;
	}
	
	
}
