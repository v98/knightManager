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

	public int getx(){
		return x;
	}
	public int gety(){
		return y;
	}

	public int isEqual(Coordinate c2){
		if (x==c2.getx() && y==c2.gety()){
			return 1;
		}
		else{
			return 0;
		}
	}


}

class Knight implements Comparable<Knight>{
	static PriorityQueue<Knight> main;
	String name;
	int xk;
	int yk;
	List<Element> box;
	int alive;
	int size;
	int xQ;
	int yQ;

	public Knight(String name ,int coordx,int coordy,List<Element> magicbox,int size,PriorityQueue<Knight> lol,int xq,int yq){
		this.xk=coordx;
		this.yk=coordy;
		this.name=name;
		this.box=magicbox;
		this.alive=1;
		this.size=size;
		this.main=lol;
		this.xQ=xq;
		this.yQ=yq;
		// for (Element lol :box ) {
		// 	lol.display();
			
		// }
	}
	public int getx(){
		return xk;
	}
	public int gety(){
		return yk;
	}
		
	public String getname(){
		return name;
		
	}

	public String toString(){
		return name+" "+xk+" "+yk;
	}


	@Override
	public int compareTo(Knight k2){
		return this.getname().compareTo(k2.getname());
	}
	

	public void dead(){
		alive=0;
	}

	public void isCoordinate(String str2) throws NonCoordinateException{
		if(!(str2.equals("Coordinate"))){
			throw new NonCoordinateException("NonCoordinateException: Not a coordinate Exception");
		}

	}

	public void emptybox() throws StackEmptyException{
		if(size<0){
			this.dead();
			throw new StackEmptyException("StackEmptyException: Stack Empty exception");
		}

	}

	public void isOverlap() throws OverlapException{
		for(Knight lol : main){
			if(getx()==lol.getx() && gety()==lol.gety()&& (!(name.equals(lol.getname())))){
				lol.dead();
				throw new OverlapException("OverlapException: Knights overlap Exception "+lol.getname()); 
			}
		}
	}

	public void foundQueen()throws QueenFoundException{
		if(getx()==xQ &&gety()==yQ){
			throw new QueenFoundException("QueenFoundException: Queeen has been found.Abort!");
		}
	}
	public int popbox()throws IOException{
		int val=0;
		size-=1;
		
		try{
			emptybox();
			Element e=box.get(size);
			isCoordinate(e.getType());
			Coordinate temp=(Coordinate)e.getvalue();
			xk=temp.getx();
			yk=temp.gety();
			isOverlap();
			foundQueen();
			BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", true));
			writer.write("No exception "+xk+" "+yk+"\n");
			writer.close();

		}
		catch(StackEmptyException m){
			BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", true));
			writer.write(m.getMessage()+"\n");
			writer.close();

		}
		catch(NonCoordinateException m){
			BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", true));
			writer.write(m.getMessage()+" "+box.get(size).getvalue()+"\n");
			writer.close();
		}
		catch(OverlapException m){
			BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", true));
			writer.write(m.getMessage()+"\n");
			writer.close();
		}
		catch(QueenFoundException m){
			val=1;
			BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", true));
			writer.write(m.getMessage()+"\n");
			writer.close();
		}
		return val;

	}

	public boolean isalive(){
		if(alive==1){
			return true;
		}
		else{
			return false;
		}
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
    public String getType(){
    	return name;
    }
    public void display(){
    	System.out.println(" "+this.value);
    }

    public Object getvalue(){
    	return value;
    }
}

public class knightManager{
	public static void main(String[] args) throws IOException{
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter number of knights: ");
		int n=sc.nextInt();
		System.out.println("Enter total number of iterations: ");
		int n_itr=sc.nextInt();
		System.out.println("Enter Coordinates of the Queen: ");
		int xQ=sc.nextInt();
		int yQ=sc.nextInt();

		Coordinate queen=new Coordinate(xQ,yQ);

		PriorityQueue<Knight> q1=new PriorityQueue<Knight>();

		for(int i=1;i<=n;i++){
			BufferedReader rd=new BufferedReader(new FileReader("./"+i+".txt"));
			String name=rd.readLine();
			String[] coords=rd.readLine().split(" ");
			int k=Integer.parseInt(rd.readLine());

			List<Element> magicbox=new ArrayList<Element>();

			for(int j=0;j<k;j++){
				String[] data=rd.readLine().split(" ");
				if("String".equals(data[0])){
					magicbox.add(new Element("String",String.class,data[1]));
				}
				else if("Integer".equals(data[0])){
					magicbox.add(new Element("Integer",Integer.class,Integer.parseInt(data[1])));

				}
				else if("Float".equals(data[0])){
					magicbox.add(new Element("Float",Float.class,Float.parseFloat(data[1])));

				}
				else{
					Coordinate temp=new Coordinate(Integer.parseInt(data[1]),Integer.parseInt(data[2]));
					magicbox.add(new Element("Coordinate",Coordinate.class,temp));
				}
			}

			Knight kt=new Knight(name,Integer.parseInt(coords[0]),Integer.parseInt(coords[1]),magicbox,k,q1,xQ,yQ);
			q1.add(kt);
		}

		// for (Knight kill : q1 ) {
		// 	System.out.println(kill);
		// }
		//BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", true));
		for(int lol=1;lol<=n_itr;lol++){
			int val=0;
			//System.out.print(lol+" ");
			for (Knight kill : q1 ) {
				if(kill.isalive()){
					BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", true));
					writer.write(lol+" "+kill+"\n");
					writer.close();
					int t=kill.popbox();
					if(t==1){
						val=1;
						break;
					}	
				}
					
			}
			if(val==1){
				break;
			}
		}
		//writer.close();
		
	}
	

}

//------------------------------------------------------------------------------------------

class NonCoordinateException extends Exception	{	
	public NonCoordinateException(String message){	
		super(message);	
	}	
}

class StackEmptyException extends Exception	{	
	public StackEmptyException(String message){	
		super(message);	
	}	
}
class OverlapException extends Exception	{	
	public OverlapException(String message){	
		super(message);	
	}	
}
class QueenFoundException extends Exception	{	
	public QueenFoundException(String message){	
		super(message);	
	}	
}

