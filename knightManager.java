import java.util.*;
import java.lang.*;
import java.io.*;

class Coordinate{
	int x;
	int y;
	public Coordinate(int x,int y){
		this.x=x;
		this.y=y;
	}
}

class Element{
	private String name;
    private Class clazz;
    private Object value;

    public Element(String name,Class clazz,Object value){
    	this.name=name;
    	this.clazz=clazz;
    	this.value=value;
    }

    public void display(){
    	System.out.println(this.name+" : "+this.value);
    }
}

public class knightManager{
	

}