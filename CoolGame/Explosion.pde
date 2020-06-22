class Explosion {
  PVector pos;
  float r;

  Explosion(float x, float y) {
    pos = new PVector(x, y);
    r = 1;
  }

  boolean update() {
    r *= 1.2;
    return (r>=255);
  }

  void show() {
    stroke(255, 200, 15);
    fill(255, 100, 20, 255-r);
    ellipse(pos.x, pos.y, r*2, r*2);
  }
}
