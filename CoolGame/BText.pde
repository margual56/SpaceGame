class BText{
  private String text = "";
  private float size = 40;
  private float x, y;
  private color col = color(0);
  
  public BText(float x, float y, String txt, float size){
    this.x = x;
    this.y = y;
    this.text = txt;
    this.size = size;
  }
  
  public String getText(){
    return text;
  }
  
  public void setText(String txt){
    this.text = txt;
  }
  
  public PVector getPos(){
    return new PVector(x, y);
  }
  
  public void setPos(float x, float y){
    this.x = x;
    this.y = y;
  }
  
  public float getSize(){
    return size;
  }
  
  public void setSize(float size){
    this.size = size;
  }
  
  public color getColor(){
    return col;
  }
  
  public void setColor(float r, float g, float b, float a){
    r = constrain(r, 0, 255);
    g = constrain(g, 0, 255);
    b = constrain(b, 0, 255);
    a = constrain(a, 0, 255);
    
    col = color(r, g, b, a);
  }
  
  public void setColor(float c){
    c = constrain(c, 0, 255);
    
    col = color(c);
  }
  
  public void setColor(float c, float a){
    c = constrain(c, 0, 255);
    a = constrain(a, 0, 255);
    
    col = color(c, a);
  }
  
  public void show(){
    textSize(size);
    
    fill(col);
    text(text, x, y);
  }
}
