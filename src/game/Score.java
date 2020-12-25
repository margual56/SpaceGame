package game;

import app.MainWindow;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Score{
	  private final PVector speed = new PVector(0, -0.8f);
	  private int amount;
	  private PVector pos;
	  private int col;
	  private MainWindow app;
	  
	  public Score(PVector pos, int amount, MainWindow app){
		this(pos.x, pos.y, amount, app);
	  }
	  
	  public Score(float x, float y, int amount, MainWindow app){
	    this.amount = amount;
	    setPos(new PVector(x, y));
	    
	    col = app.color(255);
	    this.app = app;
	  }
	  
	  public void show(){
	    app.textSize(20);
	    app.textAlign(PConstants.CENTER, PConstants.CENTER);
	    app.fill(col);
	    app.text("+" + amount, pos.x, pos.y);
	  }
	  
	  public boolean update(){
	    pos = PVector.add(pos, speed);
	    
	    col = app.color(app.red(col), app.green(col), app.blue(col), app.alpha(col)*0.99f);
	    
	    return app.alpha(col)<=0.1;
	  }
	  
	  public void setPos(PVector p){
		app.textSize(20);
	    float wid = app.textWidth("+" + amount);
	    
	    if(p.x < wid/2){
	      setPos(new PVector(p.x + wid/2, p.y));
	      return;
	    }
	    
	    if(p.x > app.width - wid/2){
	      setPos(new PVector(p.x - wid/2, p.y));
	      return;
	    }
	    
	    if(p.y < 30){
	      setPos(new PVector(p.x, p.y + 20));
	      return;
	    }
	    
	    if(p.y > app.height-20){
	      setPos(new PVector(p.x, p.y - 20));
	      return;
	    }
	    
	    this.pos = p;      
	  }
	}
