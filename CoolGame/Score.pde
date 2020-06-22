class Score{
  private final PVector speed = new PVector(0, -0.8);
  private int amount;
  private PVector pos;
  private color col = color(255);
  
  public Score(PVector pos, int amount){
    this.amount = amount;
    setPos(pos);
  }
  
  public Score(float x, float y, int amount){
    this.amount = amount;
    setPos(new PVector(x, y));
  }
  
  public void show(){
    textSize(20);
    textAlign(CENTER, CENTER);
    fill(col);
    text("+" + amount, pos.x, pos.y);
  }
  
  public boolean update(){
    pos = PVector.add(pos, speed);
    
    col = color(red(col), green(col), blue(col), alpha(col)*0.99);
    
    return alpha(col)<=0.1;
  }
  
  public void setPos(PVector p){
    textSize(20);
    float wid = textWidth("+" + amount);
    
    if(p.x < wid/2){
      setPos(new PVector(p.x + wid/2, p.y));
      return;
    }
    
    if(p.x > width - wid/2){
      setPos(new PVector(p.x - wid/2, p.y));
      return;
    }
    
    if(p.y < 30){
      setPos(new PVector(p.x, p.y + 20));
      return;
    }
    
    if(p.y > height-20){
      setPos(new PVector(p.x, p.y - 20));
      return;
    }
    
    this.pos = p;      
  }
}
