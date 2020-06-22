class BTextOutline extends BText {
  private int stroke = 1;
  private color strokeCol = color(0);

  public BTextOutline(float x, float y, String txt, float size) {
    super(x, y, txt, size);
  }

  public BTextOutline(float x, float y, String txt, float size, int stroke) {
    super(x, y, txt, size);

    setStrokeWid(stroke);
  }

  public BTextOutline(float x, float y, String txt, float size, int stroke, color c) {
    super(x, y, txt, size);

    setStrokeWid(stroke);
    this.strokeCol = c;
  }

  public color strokeColor() {
    return strokeCol;
  }

  public void strokeColor(float r, float g, float b, float a) {
    strokeCol = color(r, g, b, a);
  }

  public int getStroke() {
    return stroke;
  }

  public void setStrokeWid(int s) {
    stroke = s/2;
  }

  @Override
    public void show() {
    textSize(getSize());
    textAlign(CENTER, CENTER);

    PVector p = getPos();

    fill(strokeCol);
    for (int i = -stroke; i<=stroke; i++)
      for (int j = -stroke; j<=stroke; j++)
        if (i != j)
          text(getText(), p.x+i, p.y+j);

    fill(getColor());
    text(getText(), p.x, p.y);
  }
}
