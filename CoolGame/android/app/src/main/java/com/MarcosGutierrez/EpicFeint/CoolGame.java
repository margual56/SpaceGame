package com.MarcosGutierrez.EpicFeint;

import processing.core.*;
import processing.data.*;
import processing.event.*;
import processing.opengl.*;

import java.io.FileWriter;

import java.util.HashMap;
import java.util.ArrayList;
import java.io.File;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

public class CoolGame extends PApplet {
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

    public void setup() {
        orientation(LANDSCAPE);


        frameRate(60);

        record = dataFile("record.txt");
        difficulty = 0;

        if (true || !record.isFile()) {
            startTutorial();
    /*try {
     record.createNewFile();
     }
     catch(IOException e) {
     }*/
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
                difficulty += 1.36f;
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
    /*p = new Player(width/2, height/2, 20);
     e = new Enemy(random(width), random(height));
     lost = false;*/
                frameCount = -1;
                break;
        }
    }


    public void mouseReleased() {
        count = 0;
    }

    class BText {
        private String text = "";
        private float size = 40;
        private float x, y;
        private int col = color(0);

        public BText(float x, float y, String txt, float size) {
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
            r = constrain(r, 0, 255);
            g = constrain(g, 0, 255);
            b = constrain(b, 0, 255);
            a = constrain(a, 0, 255);

            col = color(r, g, b, a);
        }

        public void setColor(float c) {
            c = constrain(c, 0, 255);

            col = color(c);
        }

        public void setColor(float c, float a) {
            c = constrain(c, 0, 255);
            a = constrain(a, 0, 255);

            col = color(c, a);
        }

        public void show() {
            textSize(size);

            fill(col);
            text(text, x, y);
        }
    }

    class BTextOutline extends BText {
        private int stroke = 1;
        private int strokeCol = color(0);

        public BTextOutline(float x, float y, String txt, float size) {
            super(x, y, txt, size);
        }

        public BTextOutline(float x, float y, String txt, float size, int stroke) {
            super(x, y, txt, size);

            setStrokeWid(stroke);
        }

        public BTextOutline(float x, float y, String txt, float size, int stroke, int c) {
            super(x, y, txt, size);

            setStrokeWid(stroke);
            this.strokeCol = c;
        }

        public int strokeColor() {
            return strokeCol;
        }

        public void strokeColor(float r, float g, float b, float a) {
            strokeCol = color(r, g, b, a);
        }

        public int getStroke() {
            return stroke;
        }

        public void setStrokeWid(int s) {
            stroke = s / 2;
        }

        @Override
        public void show() {
            textSize(getSize());
            textAlign(CENTER, CENTER);

            PVector p = getPos();

            fill(strokeCol);
            for (int i = -stroke; i <= stroke; i++)
                for (int j = -stroke; j <= stroke; j++)
                    if (i != j)
                        text(getText(), p.x + i, p.y + j);

            fill(getColor());
            text(getText(), p.x, p.y);
        }
    }

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

    class Enemy {
        PVector pos, acc, vel = new PVector(0, 0);
        float a = 0;
        float spd = random(0.05f, 0.5f);
        float prevDist = Float.POSITIVE_INFINITY;
        Boolean dead = false;

        Enemy(float x, float y) {
            pos = new PVector(x, y);
            spd = random(minSpd(), maxSpd());
            acc = PVector.fromAngle(a, new PVector(1, 1).mult(spd));
        }

        public void update(Player pl) {
            float d = PVector.dist(pos, pl.pos);
            a = atan2(pl.pos.y - pos.y, pl.pos.x - pos.x);
            if (d < prevDist) {
                acc = PVector.fromAngle(a, acc).setMag(spd);
            } else {
                vel.mult(0.95f);
                acc = PVector.fromAngle(a, acc).setMag(spd * 2);
            }
            vel.add(acc);
            pos.add(vel);
            prevDist = d;

            dead = pos.x < 0 || pos.x > width || pos.y < 0 || pos.y > height;
            if (dead) {
                deaths.add(new Explosion(constrain(pos.x, 0, width), constrain(pos.y, 0, height)));
                scores.add(new Score(constrain(pos.x, 0, width), constrain(pos.y, 0, height), (int) (spd * 1000)));
                score += (int) (spd * 1000);
            }
        }

        public void show(float size) {
            stroke(255, 0, 0);
            fill(255, 0, 0);
            pushMatrix();
            translate(pos.x, pos.y);
            rotate(a + PI / 2);
            triangle(0, -size * 2, size, size, -size, size);
            popMatrix();
        }

        public boolean isAlive() {
            return !dead;
        }

        private float minSpd() {
            return map(difficulty, 1, 200, 0.2f, 0.005f);
        }

        private float maxSpd() {
            return map(difficulty, 1, 200, 0.6f, 0.0056f);
        }
    }

    class Explosion {
        PVector pos;
        float r;

        Explosion(float x, float y) {
            pos = new PVector(x, y);
            r = 1;
        }

        public boolean update() {
            r *= 1.2f;
            return (r >= 255);
        }

        public void show() {
            stroke(255, 200, 15);
            fill(255, 100, 20, 255 - r);
            ellipse(pos.x, pos.y, r * 2, r * 2);
        }
    }

    class IButton extends Button {
        private PImage img = null;

        public IButton(float x, float y, float w, float h) {
            super(x, y, w, h);
        }

        public IButton(float x, float y, float w, float h, BText t) {
            super(x, y, w, h, t);
        }

        public IButton(float x, float y, float w, float h, BText t, PImage img) {
            super(x, y, w, h, t);

            this.img = img;
        }

        public void ishow() {
            PVector pos = getPos();
            image(img, pos.x, pos.y, getWidth(), getHeight());

            if (getText() != null)
                getText().show();
        }

        public PImage getImage() {
            return img;
        }

        public void setImage(PImage img) {
            this.img = img;
        }
    }

    class Player {
        PVector pos, acc, vel = new PVector(0, 0);
        float a = 0;
        float spd = 0.3f;//*18/60;
        float size;
        Boolean go = true;

        Player(float x, float y, float size) {
            pos = new PVector(x, y);

            this.size = size;

            acc = PVector.fromAngle(a, new PVector(1, 1).mult(spd));
        }

        public void update(Enemy[] enem) {
            if (!lost) {
                if (go) {
                    acc = PVector.fromAngle(a, acc).setMag(spd);
                    vel.add(acc);
                } else {
                    vel.mult(0.6f);
                }

                pos.add(vel);

                lost = pos.x < 0 || pos.x > width || pos.y < 0 || pos.y > height || collidedWith(enem);
            }
        }

        public boolean collidedWith(Enemy[] list) {
            for (Enemy n : list) {
                if (PVector.dist(pos, n.pos) < size * 1.5f)
                    return true;
            }

            return false;
        }

        public boolean collidedWith(ArrayList<Enemy> list) {
            for (Enemy n : list) {
                if (PVector.dist(pos, n.pos) < size * 1.5f)
                    return true;
            }

            return false;
        }

        public void update(Enemy[] en, float spdMult) {
            spd += spdMult;

            if (!lost) {
                if (go) {
                    acc = PVector.fromAngle(a, acc).setMag(spd);
                    vel.add(acc);
                } else {
                    vel.mult(0.6f);
                }

                pos.add(vel);

                lost = pos.x < 0 || pos.x > width || pos.y < 0 || pos.y > height || collidedWith(en);
            }
        }

        public void show() {
            stroke(255);
            fill(100);
            pushMatrix();
            translate(pos.x, pos.y);
            rotate(a + PI / 2);
            triangle(0, -size * 2, size, size, -size, size);
            popMatrix();
        }
    }

    class Score {
        private final PVector speed = new PVector(0, -0.8f);
        private int amount;
        private PVector pos;
        private int col = color(255);

        public Score(PVector pos, int amount) {
            this.amount = amount;
            setPos(pos);
        }

        public Score(float x, float y, int amount) {
            this.amount = amount;
            setPos(new PVector(x, y));
        }

        public void show() {
            textSize(20);
            textAlign(CENTER, CENTER);
            fill(col);
            text("+" + amount, pos.x, pos.y);
        }

        public boolean update() {
            pos = PVector.add(pos, speed);

            col = color(red(col), green(col), blue(col), alpha(col) * 0.99f);

            return alpha(col) <= 0.1f;
        }

        public void setPos(PVector p) {
            textSize(20);
            float wid = textWidth("+" + amount);

            if (p.x < wid / 2) {
                setPos(new PVector(p.x + wid / 2, p.y));
                return;
            }

            if (p.x > width - wid / 2) {
                setPos(new PVector(p.x - wid / 2, p.y));
                return;
            }

            if (p.y < 30) {
                setPos(new PVector(p.x, p.y + 20));
                return;
            }

            if (p.y > height - 20) {
                setPos(new PVector(p.x, p.y - 20));
                return;
            }

            this.pos = p;
        }
    }

    class Timer {
        private float count = 0;

        public float update() {
            count += 1 / frameRate;
            return count;
        }

        public void reset() {
            count = 0;
        }

        public float time() {
            return count;
        }
    }

    public void startTutorial() {
        p = new Player(width / 2, height / 2, 20);

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
        p = new Player(width / 2, height / 2, 20);

        enemies = new Enemy[parseNumberEnemies(difficulty)];
        for (int i = 0; i < enemies.length; i++) {
            float x, y;

            do {
                x = random(width);
            } while (abs(p.pos.x - x) < p.size * 2);

            do {
                y = random(height);
            } while (abs(p.pos.y - y) < p.size * 2);

            enemies[i] = new Enemy(x, y);
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

    public void tutorial() {
        if (!tutorialGUI)
            timer.update();

        background(0);
        stroke(255, 0, 0);
        strokeWeight(10);
        noFill();
        rect(0, 0, width, height);
        strokeWeight(1);
        if (go && timer.time() < 9.9f)
            p.update(enemies, 0.01f / frameRate);

        p.show();

        if (lost)
            startTutorial();


        if (touches.length > 0 && go) {
            if (touches.length > 1) {
                p.vel.mult(0.9f);
            } else if (touches.length > 0) {
                if (touches[touches.length - 1].x < width / 2) {
                    p.a -= 0.1f;
                } else {
                    p.a += 0.1f;
                }
            }

            if (tutorialGUI && timer.update() > 5) {
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
            p.update(enemies, 0.01f / frameRate);

        for (Enemy e : enemies)
            if (!e.dead && go)
                e.update(p);

        p.show();

        for (Enemy e : enemies)
            if (e.isAlive())
                e.show(10);

        for (int i = deaths.size() - 1; i >= 0; i--) {
            if (!deaths.get(i).update()) {
                deaths.get(i).show();
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
                p.vel.mult(0.9f);
            } else if (touches.length > 0) {
                if (touches[touches.length - 1].x < width / 2) {
                    p.a -= 0.1f;
                } else {
                    p.a += 0.1f;
                }
            }
        }

        showGUI();
    }

    public void tutorialGUI() {
        fill(255);
        textSize(25);
        textAlign(RIGHT, CENTER);
        text("Press here\nto turn left", width / 4, height / 1.5f);
        textAlign(LEFT, CENTER);
        text("Press here\nto turn right", width / 1.5f, height / 1.5f);

        textAlign(CENTER, CENTER);
        textSize(35);
        text("Press both to break", width / 2, height / 1.5f);

        stroke(255, 100);
        line(width / 2, 0, width / 2, height);
    }

    public void tutorialPlayGUI(float time) {
        textSize(60);
        fill(255, 0, 0);
        textAlign(CENTER, CENTER);
        text("SURVIVE FOR: " + nfc(time, 1) + "s\nto pass the tutorial", width / 2, height / 2);
    }

    public void tutorialPass() {
        noStroke();
        fill(0, 200);
        rect(0, 0, width, height);

        fill(255);
        textSize(60);
        textAlign(CENTER, CENTER);
        text("CONGRATULATIONS!\nTutorial passed", width / 2, height / 4);

        fill(255, 0, 0);
        text("MAKE THE ENEMIES EXPLODE TO WIN!", width / 2, height / 2);
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
            text("Press anywhere to start", width / 2, height / 8);
        }
    }

    public void lost() {
        background(count);
        textAlign(CENTER, CENTER);

        fill(255);
        int stroke = 1;
        for (int i = -stroke / 2; i <= stroke / 2; i++)
            for (int j = -stroke / 2; j <= stroke / 2; j++)
                text("YOU LOST", width / 2 + i, height / 2 + j);

        fill(255, 0, 0);
        text("YOU LOST", width / 2, height / 2);

        textSize(40);
        text("Score: " + score, width / 2, height / 2 + 100);
        if (touches.length > 0) {
            count += 3;

            if (count >= 255)
                startGame(difficulty);
        } else {
            count = 0;
        }
    }

    public void won() {
        if (timer.update() >= 6) {
            startGame(difficulty);
            return;
        }

        background(255);
        fill(255, 0, 0);
        textSize(50);
        textAlign(CENTER, CENTER);
        text("YOU WON!\n\nAdvancing to level " + (int) difficulty, width / 2, height / 2);
    }

    public <T extends Enemy> int countEnemies(T[] en) {
        int count = 0;

        for (Enemy e : en)
            if (e.isAlive())
                count++;

        return count;
    }

    public int parseNumberEnemies(float n) {
        return round(map(n, 1, 500, 1, 500));
    }

    public void settings() {
        size(displayWidth, displayHeight);
    }
}
