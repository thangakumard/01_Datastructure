package coreJava.collections;

abstract class Animal{
	   //abstract method
	   public abstract void sound();
	}
public class Abstract_Cat extends Animal {

	public void sound(){
		System.out.println("Miyaaww");
	   }
	   public static void main(String args[]){
		Animal obj = new Abstract_Cat();
		obj.sound();
	   }
}
