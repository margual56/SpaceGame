class Player {
  PVector pos, acc, vel = new PVector(0, 0);
  float a = 0;
  float spd =0.3;//*18/60;
  float size;
  Boolean go = true;

  Player(float x, float y, float size) {
    pos = new PVector(x, y);

    this.size = size;

    acc = PVector.fromAngle(a, new PVector(1, 1).mult(spd));
  }

  void update(Enemy[] enem) {
    if (!lost) {
      if (go) {
        acc = PVector.fromAngle(a, acc).setMag(spd);
        vel.add(acc);
      } else {
        vel.mult(0.6);
      }

      pos.add(vel);

      lost = pos.x < 0 || pos.x> width || pos.y < 0 || pos.y > height || collidedWith(enem);
    }
  }

  boolean collidedWith(Enemy[] list) {
    for (Enemy n : list) {
      if (PVector.dist(pos, n.pos)<size*1.5)
        return true;
    }

    return false;
  }

  boolean collidedWith(ArrayList<Enemy> list) {
    for (Enemy n : list) {
      if (PVector.dist(pos, n.pos)<size*1.5)
        return true;
    }

    return false;
  }

  void update(Enemy[] en, float spdMult) {
    spd += spdMult;

    if (!lost) {
      if (go) {
        acc = PVector.fromAngle(a, acc).setMag(spd);
        vel.add(acc);
      } else {
        vel.mult(0.6);
      }

      pos.add(vel);

      lost = pos.x < 0 || pos.x> width || pos.y < 0 || pos.y > height || collidedWith(en);
    }
  }

  void show() {
    stroke(255);
    fill(100);
    pushMatrix();
    translate(pos.x, pos.y);
    rotate(a+PI/2);
    triangle(0, -size*2, size, size, -size, size);
    popMatrix();
  }
}
