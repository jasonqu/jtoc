package org.jtoc.convertor.cpunit;

import org.jtoc.Post;
import org.jtoc.Pre;

public class PreAnnoTest {

	@Pre
	@Post
	public void methodMarker(){	}
	
	@Post
	@Pre
	public void methodMarker(String name){	}
}
