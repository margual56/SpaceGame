import java.io.FileWriter;

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

void setup() {
  orientation(LANDSCAPE);
  size(displayWidth, displayHeight);

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
void draw() {
  if (tutorial) {
    tutorial();
  } else if (!lost && !won) {  
    game();

    won = countEnemies(enemies)==0;
    
    if(won){
      difficulty+=1.36;
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

void mousePressed() {
  go = true;
  frameStart = frameCount;
}

void keyPressed() {
  switch(keyCode) {
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



void mouseReleased() {
  count = 0;
}
