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
  
  public void ishow(){
    PVector pos = getPos();
    image(img, pos.x, pos.y, getWidth(), getHeight());
    
    if(getText() != null)
      getText().show();
  }
  
  public PImage getImage(){
    return img;
  }
  
  public void setImage(PImage img){
    this.img = img;
  }
}
