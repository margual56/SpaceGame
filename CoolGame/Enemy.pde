class Enemy {
  PVector pos, acc, vel = new PVector(0, 0);
  float a = 0;
  float spd = random(0.05, 0.5);
  float prevDist = Float.POSITIVE_INFINITY;
  Boolean dead = false;

  Enemy(float x, float y) {
    pos = new PVector(x, y);
    spd = random(minSpd(), maxSpd());
    acc = PVector.fromAngle(a, new PVector(1, 1).mult(spd));
  }
  
  void update(Player pl) {
    float d = PVector.dist(pos, pl.pos);
    a = atan2(pl.pos.y-pos.y, pl.pos.x-pos.x);
    if (d<prevDist) {
      acc = PVector.fromAngle(a, acc).setMag(spd);
    } else {
      vel.mult(0.95);
      acc = PVector.fromAngle(a, acc).setMag(spd*2);
    }
    vel.add(acc);
    pos.add(vel);
    prevDist = d;

    dead = pos.x < 0 || pos.x > width || pos.y < 0 || pos.y > height;
    if (dead) {
      deaths.add(new Explosion(constrain(pos.x, 0, width), constrain(pos.y, 0, height)));
      scores.add(new Score(constrain(pos.x, 0, width), constrain(pos.y, 0, height), (int)(spd*1000)));
      score += (int)(spd*1000);
    }
  }
  
  void show(float size) {
    stroke(255, 0, 0);
    fill(255, 0, 0);
    pushMatrix();
    translate(pos.x, pos.y);
    rotate(a+PI/2);
    triangle(0, -size*2, size, size, -size, size);
    popMatrix();
  }
  
  public boolean isAlive(){
    return !dead;
  }
  
  private float minSpd(){
    return map(difficulty, 1, 200, 0.2, 0.005);
  }
  
  private float maxSpd(){
    return map(difficulty, 1, 200, 0.6, 0.0056);
  }
}
