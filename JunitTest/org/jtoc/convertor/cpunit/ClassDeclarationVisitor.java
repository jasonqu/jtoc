package org.jtoc.convertor.cpunit;

import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.expr.AnnotationExpr;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * used for test
 * 
 * @author Goddamned Qu
 */
public class ClassDeclarationVisitor extends VoidVisitorAdapter<Object> {

	private static Log logger = LogFactory.getLog(MethodVisitor.class);

	/** Arraylist for the AnnotationExpr */
	ArrayList<AnnotationExpr> annolist = new ArrayList<AnnotationExpr>();

	/** ArrayList for the PostAnnotation indexes */
	ArrayList<Integer> innerAnnoSet = new ArrayList<Integer>();
	
	/** the annotation expression counter */
	int index = 0;
	
	@Override
	public void visit(ClassOrInterfaceDeclaration n, Object arg) {
		List<AnnotationExpr> list = n.getAnnotations();
		if (list == null) // the method has no annotation.
			return;
		
		for (AnnotationExpr ae : list) {
			if (InnerTestAnnotation.isInstance(ae)) {
				try {
					InnerTestAnnotation i = new InnerTestAnnotation(ae);
					i.parse();
					logger.info(i.toString());
				} catch (JtocFormatException e) {
					logger.error(e.getMessage());
				}
				innerAnnoSet.add(index);
			}
			
			annolist.add(ae);
			index++;
		}

	}
}
