public void startTutorial() {
  p = new Player(width/2, height/2, 20);

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
  p = new Player(width/2, height/2, 20);

  enemies = new Enemy[parseNumberEnemies(difficulty)];
  for (int i = 0; i<enemies.length; i++) {
    float x, y;

    do {
      x = random(width);
    } while (abs(p.pos.x-x)<p.size*2);

    do {
      y = random(height);
    } while (abs(p.pos.y-y)<p.size*2);

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
  if (go && timer.time()<9.9)
    p.update(enemies, 0.01/frameRate);

  p.show();

  if (lost)
    startTutorial();


  if (touches.length>0 && go) {
    if (touches.length > 1) {
      p.vel.mult(0.9);
    } else if (touches.length > 0) {
      if (touches[touches.length-1].x < width/2) {
        p.a -= 0.1;
      } else {
        p.a += 0.1;
      }
    }

    if (tutorialGUI && timer.update()>5) {
      tutorialGUI = false;
      timer.reset();
    }
  }

  if (!tutorialGUI && timer.time()>=15) {
    difficulty++;
    startGame(difficulty);
    timer.reset();
  }


  showGUI();

  if (tutorialGUI)
    tutorialGUI();
  else if (timer.time()<10) {
    tutorialPlayGUI(tutorialTime-timer.time());
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
    p.update(enemies, 0.01/frameRate);

  for (Enemy e : enemies)
    if (!e.dead && go)
      e.update(p);

  p.show();

  for (Enemy e : enemies)
    if (e.isAlive())
      e.show(10);

  for (int i = deaths.size()-1; i>=0; i--) {
    if (!deaths.get(i).update()) {
      deaths.get(i).show();
    } else {
      deaths.remove(i);
    }
  }

  for (int i = scores.size()-1; i>=0; i--)
    if (!scores.get(i).update())
      scores.get(i).show();
    else
      scores.remove(i);

  if (touches.length>0 && go) {
    if (touches.length > 1) {
      p.vel.mult(0.9);
    } else if (touches.length > 0) {
      if (touches[touches.length-1].x < width/2) {
        p.a -= 0.1;
      } else {
        p.a += 0.1;
      }
    }
  }

  showGUI();
}

public void tutorialGUI() {
  fill(255);
  textSize(25);
  textAlign(RIGHT, CENTER);
  text("Press here\nto turn left", width/4, height/1.5);
  textAlign(LEFT, CENTER);
  text("Press here\nto turn right", width/1.5, height/1.5);

  textAlign(CENTER, CENTER);
  textSize(35);
  text("Press both to break", width/2, height/1.5);

  stroke(255, 100);
  line(width/2, 0, width/2, height);
}

public void tutorialPlayGUI(float time) {
  textSize(60);
  fill(255, 0, 0);
  textAlign(CENTER, CENTER);
  text("SURVIVE FOR: " + nfc(time, 1) + "s\nto pass the tutorial", width/2, height/2);
}

public void tutorialPass() {
  noStroke();
  fill(0, 200);
  rect(0, 0, width, height);

  fill(255);
  textSize(60);
  textAlign(CENTER, CENTER);
  text("CONGRATULATIONS!\nTutorial passed", width/2, height/4);
  
  fill(255, 0, 0);
  text("MAKE THE ENEMIES EXPLODE TO WIN!", width/2, height/2);
}

public void showGUI() {
  if (go) {
    fill(255);
    textSize(35);
    textAlign(LEFT, CENTER);
    text("Score: " + score, 20, 50);
    textAlign(RIGHT, CENTER);
    text("Level: " + (int)difficulty, width-20, 50);
  } else {
    fill(255, 150);
    rect(0, 0, width, height);
    
    fill(0);
    textSize(40);
    textAlign(CENTER, CENTER);
    text("Press anywhere to start", width/2, height/8);
  }
}

public void lost() {
  background(count);
  textAlign(CENTER, CENTER);

  fill(255);
  int stroke = 1;
  for (int i = -stroke/2; i<=stroke/2; i++)
    for (int j = -stroke/2; j<=stroke/2; j++)
      text("YOU LOST", width/2+i, height/2+j);

  fill(255, 0, 0);
  text("YOU LOST", width/2, height/2);

  textSize(40);
  text("Score: " + score, width/2, height/2+100);
  if (touches.length>0) {
    count +=3;

    if (count>=255)
      startGame(difficulty);
  } else {
    count = 0;
  }
}

public void won() {
  if(timer.update()>=6){
    startGame(difficulty);
    return;
  }
  
  background(255);
  fill(255, 0, 0);
  textSize(50);
  textAlign(CENTER, CENTER);
  text("YOU WON!\n\nAdvancing to level " + (int)difficulty, width/2, height/2);
}

public <T extends Enemy> int countEnemies(T[] en) {
  int count = 0;

  for (Enemy e : en)
    if (e.isAlive())
      count++;

  return count;
}
