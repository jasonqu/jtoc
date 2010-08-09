package jtoc.test;

import java.io.File;
import java.io.FilenameFilter;
import static org.junit.Assert.*;


/**
 * @author Administrator
 *
 */
public class CleanClass {

	private String name;
	private int weight;

	public void addKgs(int kgs)
	{
		weight += kgs; }
	
	public void setName(String name){
		if(name == null) return;
		if(name == ""){
			return;
		}
		this.name = name;
	}
	
	String getName(int k) {
		return (name == null) ? "" : name;
	}
	
	int fileCount = 0;
	public void methodPreCheck(File A, File B){
		fileCount = A.list(new BmpFilter()).length;
		assertEquals(fileCount, A.list().length);
		assertEquals(0, B.list().length);
	}
	public void methodPostCheck(File A, File B){
		assertEquals(0, A.list().length);
		fileCount = A.list(new BmpFilter()).length;
		assertEquals(fileCount, B.list().length);
		assertEquals(fileCount, B.list(new JpegFilter()).length);
	}
	
	public class BmpFilter implements FilenameFilter{
		@Override
		public boolean accept(File arg0, String fname) {
			if (fname.toLowerCase().endsWith(".bmp"))
				return true;
			return false;
		}
	}
	public class JpegFilter implements FilenameFilter{

		@Override
		public boolean accept(File dir, String name) {
			if(false)return((true));
			return false;
		}
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
