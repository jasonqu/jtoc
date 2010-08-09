package org.jtoc.ant;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.MatchingTask;
import org.jtoc.convertor.ProjectConvertor;

public class ConvertTask extends MatchingTask{
	private File srcDir;
	private File destDir;

	public void setSrcDir(File srcDir) {
		this.srcDir = srcDir;
	}

	public void setDestDir(File destDir) {
		this.destDir = destDir;
	}

	private boolean rewrite = false;
	
	public void setRewrite(boolean rewrite) {
		this.rewrite = rewrite;
	}

	public boolean isRewrite() {
		return rewrite;
	}
	
	public void execute() throws BuildException {
		if (srcDir == null) {
			throw new BuildException("srcdir must be specified");
		}
		log("srcDir = " + srcDir, Project.MSG_DEBUG);

		if (destDir == null) {
			throw new BuildException("destdir must be specified");
		}
		log("destDir = " + destDir, Project.MSG_DEBUG);

		try {
			ProjectConvertor.convertProject(srcDir, destDir, rewrite);
		} catch (Exception e) {
			throw new BuildException(e);
		}

		srcDir = null;
		destDir = null;
		this.setRewrite(false);
	}
}
