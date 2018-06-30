package coreJava.collections;

public class MathTeacher extends Teacher{
	public MathTeacher(){
		System.out.println("Constructor of MathTeacher");
	}
	   String mainSubject = "Maths";
	   
	   public void does(){
		   System.out.println("Teaching Math !");
		   super.does();
	   }
	   
	   public static void main(String args[]){
	      MathTeacher obj = new MathTeacher();
	      System.out.println(obj.college);
	      System.out.println(obj.designation);
	      System.out.println(obj.mainSubject);
	      obj.does();
	   }
}


