package jtoc.test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.jtoc.InnerTest;
import org.jtoc.Post;
import org.jtoc.Pre;

@InnerTest(objectNames = { "test" })
public class SmallPerson {
	private String name;
	private int weight;

	@Pre(testMethod ="", testObject = "inner",
			parameters = "kgs")
	@Post
	public void addKgs(int kgs)
	{
		weight += kgs; }
	
	@Pre
	@Deprecated
	@Post
	public void setName(String name){
		if(name == null) return;
		if(name == ""){
			return;
		}
		this.name = name;
	}
	
	@Post(testMethod = "postName")
	String getName(int k) {
		return (name == null) ? "" : name;
	}
	
	@Post//(parameters = "k")
	ActionListener getCom(){
		if(false) return null;
		this .name = "";
		return new
        ActionListener()
        {
           public void actionPerformed(ActionEvent event)
           { 
              System.out.println("At the tone, the time is ");
           }
        };
	}
	
	@Pre
	//@Post(parameters = "k")
	int testPostWithParas(){
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
}