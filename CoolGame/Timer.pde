class Timer{
  private float count = 0;
  
  public float update(){
    count += 1/frameRate;
    return count;
  }
  
  public void reset(){
    count = 0;
  }
  
  public float time(){
    return count;
  }
}
