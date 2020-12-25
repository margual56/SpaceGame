package app;

import processing.core.PApplet;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import effects.*;
import game.*;
import gui.*;

public class MainWindow extends PApplet {

	Player p;
	Enemy[] enemies;
	Boolean lost, won, go, tutorial, tutorialGUI;
	ArrayList<Explosion> deaths;
	ArrayList<Score> scores;
	File record;
	Timer timer;

	int count, frameStart;
	int score = 0;
	float difficulty, tutorialTime = 10;

	public void settings() {
		orientation(LANDSCAPE);
		size(displayWidth, displayHeight);
	}

	public void setup() {
		frameRate(60);

		record = dataFile("record.txt");
		difficulty = 0;

		if (!record.isFile()) {
			startTutorial();
			try {
				record.createNewFile();
			} catch (IOException e) {
			}
		} else {
			startGame(difficulty);
		}
	}

	public void draw() {
		if (tutorial) {
			tutorial();
		} else if (!lost && !won) {
			game();

			won = countEnemies(enemies) == 0;

			if (won) {
				difficulty += 1.36;
				timer.reset();
			}
		} else if (lost) {
			difficulty = 1;
			score = 0;

			lost();
		} else {
			won();
		}
	}

	public void mousePressed() {
		go = true;
		frameStart = frameCount;
	}

	public void keyPressed() {
		switch (keyCode) {
		case ENTER:
			// p.go = !p.go;
			break;
		case BACKSPACE:
			/*
			 * p = new Player(width/2, height/2, 20); e = new Enemy(random(width),
			 * random(height)); lost = false;
			 */
			frameCount = -1;
			break;
		}
	}

	public void mouseReleased() {
		count = 0;
	}

	public void startTutorial() {
		p = new Player(width / 2.0f, height / 2.0f, 20, this);

		enemies = new Enemy[0];

		deaths = new ArrayList<Explosion>();
		scores = new ArrayList<Score>();

		lost = false;
		won = false;
		go = false;
		tutorial = true;
		tutorialGUI = true;
		count = 0;
		frameStart = 0;

		timer = new Timer();
	}

	public void startGame(float difficulty) {
		p = new Player(width / 2.0f, height / 2.0f, 20, this);

		enemies = new Enemy[parseNumberEnemies(difficulty)];
		for (int i = 0; i < enemies.length; i++) {
			float x, y;

			do {
				x = random(width);
			} while (abs(p.getPos().x - x) < p.getSize() * 2);

			do {
				y = random(height);
			} while (abs(p.getPos().y - y) < p.getSize() * 2);

			enemies[i] = new Enemy(x, y, this);
		}

		deaths = new ArrayList<Explosion>();
		scores = new ArrayList<Score>();

		lost = false;
		won = false;
		go = false;
		tutorial = false;
		tutorialGUI = false;
		count = 0;
		frameStart = 0;
	}

	public int parseNumberEnemies(float n) {
		return round(map(n, 1, 500, 1, 500));
	}

	public void tutorial() {
		if (!tutorialGUI)
			timer.update(frameRate);

		background(0);
		stroke(255, 0, 0);
		strokeWeight(10);
		noFill();
		rect(0, 0, width, height);
		strokeWeight(1);
		if (go && timer.time() < 9.9)
			p.update(enemies);

		p.show(this);

		if (lost)
			startTutorial();

		if (touches.length > 0 && go) {
			if (touches.length > 1) {
				p.vel.mult(0.9);
			} else if (touches.length > 0) {
				if (touches[touches.length - 1].x < width / 2) {
					p.addA(-0.1f);
				} else {
					p.addA(0.1f);
				}
			}

			if (tutorialGUI && timer.update(frameRate) > 5) {
				tutorialGUI = false;
				timer.reset();
			}
		}

		if (!tutorialGUI && timer.time() >= 15) {
			difficulty++;
			startGame(difficulty);
			timer.reset();
		}

		showGUI();

		if (tutorialGUI)
			tutorialGUI();
		else if (timer.time() < 10) {
			tutorialPlayGUI(tutorialTime - timer.time());
		} else {
			tutorialPass();
		}
	}

	public void game() {
		background(0);
		stroke(255, 0, 0);
		strokeWeight(10);
		noFill();
		rect(0, 0, width, height);
		strokeWeight(1);
		if (go)
			p.update(enemies);// , 0.01 / frameRate);

		for (Enemy e : enemies)
			if (!e.isDead() && go)
				e.update(p, this);

		p.show(this);

		for (Enemy e : enemies)
			if (e.isAlive())
				e.show(10, this);

		for (int i = deaths.size() - 1; i >= 0; i--) {
			if (!deaths.get(i).update()) {
				deaths.get(i).show(this);
			} else {
				deaths.remove(i);
			}
		}

		for (int i = scores.size() - 1; i >= 0; i--)
			if (!scores.get(i).update())
				scores.get(i).show();
			else
				scores.remove(i);

		if (touches.length > 0 && go) {
			if (touches.length > 1) {
				p.multVel(0.9f);
			} else if (touches.length > 0) {
				if (touches[touches.length - 1].x < width / 2) {
					p.addA(-0.1f);
				} else {
					p.addA(0.1f);
				}
			}
		}

		showGUI();
	}

	public void tutorialGUI() {
		fill(255);
		textSize(25);
		textAlign(RIGHT, CENTER);
		text("Press here\nto turn left", width / 4.0f, height / 1.5f);
		textAlign(LEFT, CENTER);
		text("Press here\nto turn right", width / 1.5f, height / 1.5f);

		textAlign(CENTER, CENTER);
		textSize(35);
		text("Press both to break", width / 2.0f, height / 1.5f);

		stroke(255, 100);
		line(width / 2.0f, 0, width / 2.0f, height);
	}

	public void tutorialPlayGUI(float time) {
		textSize(60);
		fill(255, 0, 0);
		textAlign(CENTER, CENTER);
		text("SURVIVE FOR: " + nfc(time, 1) + "s\nto pass the tutorial", width / 2.0f, height / 2.0f);
	}

	public void tutorialPass() {
		noStroke();
		fill(0, 200);
		rect(0, 0, width, height);

		fill(255);
		textSize(60);
		textAlign(CENTER, CENTER);
		text("CONGRATULATIONS!\nTutorial passed", width / 2.0f, height / 4.0f);

		fill(255, 0, 0);
		text("MAKE THE ENEMIES EXPLODE TO WIN!", width / 2.0f, height / 2.0f);
	}

	public void showGUI() {
		if (go) {
			fill(255);
			textSize(35);
			textAlign(LEFT, CENTER);
			text("Score: " + score, 20, 50);
			textAlign(RIGHT, CENTER);
			text("Level: " + (int) difficulty, width - 20, 50);
		} else {
			fill(255, 150);
			rect(0, 0, width, height);

			fill(0);
			textSize(40);
			textAlign(CENTER, CENTER);
			text("Press anywhere to start", width / 2.0f, height / 8.0f);
		}
	}

	public void lost() {
		background(count);
		textAlign(CENTER, CENTER);

		fill(255);
		int stroke = 1;
		for (int i = -stroke / 2; i <= stroke / 2; i++)
			for (int j = -stroke / 2; j <= stroke / 2; j++)
				text("YOU LOST", width / 2.0f + i, height / 2.0f + j);

		fill(255, 0, 0);
		text("YOU LOST", width / 2, height / 2);

		textSize(40);
		text("Score: " + score, width / 2.0f, height / 2.0f + 100);
		if (touches.length > 0) {
			count += 3;

			if (count >= 255)
				startGame(difficulty);
		} else {
			count = 0;
		}
	}

	public void won() {
		if (timer.update(frameRate) >= 6) {
			startGame(difficulty);
			return;
		}

		background(255);
		fill(255, 0, 0);
		textSize(50);
		textAlign(CENTER, CENTER);
		text("YOU WON!\n\nAdvancing to level " + (int) difficulty, width / 2.0f, height / 2.0f);
	}

	public <T extends Enemy> int countEnemies(T[] en) {
		int count = 0;

		for (Enemy e : en)
			if (e.isAlive())
				count++;

		return count;
	}

	public static void main(String[] args) {
		String[] processingArgs = { "Turing Machine" };
		MainWindow mySketch = new MainWindow();
		PApplet.runSketch(processingArgs, mySketch);
	}

	public boolean getLost() {
		return lost;
	}

	public void addDeathEffect(Explosion explosion) {
		// TODO Auto-generated method stub

	}

	public void addScoreEffect(Score score2) {
		scores.add(score2);
	}

	public float getDifficulty() {
		return difficulty;
	}

	public void addScore(int i) {
		score += i;
	}

	public void setLost(boolean b) {
		lost = b;
	}
}