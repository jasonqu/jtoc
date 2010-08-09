package jtoc.test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.jtoc.InnerTest;
import org.jtoc.Post;
import org.jtoc.Pre;

public class SmallPerson {
	private String name;
	private int weight;

	public void addKgs(int kgs)
	{
		inner.addKgsPreCheck(kgs);
		weight += kgs; 
		innerTest.addKgsPostCheck(kgs);
	}
	
	@Deprecated
	public void setName(String name){
		innerTest.setNamePreCheck(name);
		if(name == null) 
		{innerTest.setNamePostCheck(name); return;}
		if(name == ""){
			{innerTest.setNamePostCheck(name); return;}
		}
		this.name = name;
		innerTest.setNamePostCheck(name);
	}
	
	String getName(int k) {
		return innerTest.postName(( (name == null) ? "" : name), k);
	}
	
	ActionListener getCom(){
		if(false) return innerTest.getComPostCheck(( null));
		this .name = "";
		return innerTest.getComPostCheck(( new
        ActionListener()
        {
           public void actionPerformed(ActionEvent event)
           { 
              System.out.println("At the tone, the time is ");
           }
        }));
	}
	
	//@Post(parameters = "k")
	int testPostWithParas(){
		innerTest.testPostWithParasPreCheck();
		int k = 3;
		return 	k;
	}
	
	public static void main(String[] args) {
		SmallPerson s = new SmallPerson();
		{
			System.out.println("could work");
		}
	}
	
	class InnerTest{}

	InnerTest test = new InnerTest();
}
