package org.lab6.storedClasses;

import java.io.Serializable;

@SuppressWarnings({"removal"})
public class Coordinates implements Serializable {
	private Float x;
	private Integer y;
	public Coordinates(float x, int y){
		if(x<=-9 || y<=-550)throw new WrongValueException("x must be >-9 and y must be>-550");
			this.x=new Float(x);
			this.y=new Integer(y);
		if(this.x==null && this.y==null)
		throw new NullPointerException("coordinates can't be null");
	}
	public void setX(float x){this.x=new Float(x);}
	public void setX(int y){this.y=new Integer(y);}
	
	public Float getX(){return x;}
	public Integer getY(){return y;}
	
	public String toString(){return "[x:"+x.toString()+", y:"+y.toString()+"]";}
	public String toJson(){
		return "\"Coordinates\": {\n\"x\": "+getX()+",\n\"y\": "+getY()+"\n}";
	}
}