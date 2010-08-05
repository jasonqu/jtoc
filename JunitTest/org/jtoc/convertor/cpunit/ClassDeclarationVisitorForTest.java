package org.jtoc.convertor.cpunit;

import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.expr.AnnotationExpr;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * used for InnerTestAnnotation, ClassInfo's test
 */
public class ClassDeclarationVisitorForTest extends VoidVisitorAdapter<Object> {

	private static Log logger = LogFactory.getLog(MethodVisitor.class);

	/** Arraylist for the AnnotationExpr */
	ArrayList<AnnotationExpr> annolist = new ArrayList<AnnotationExpr>();

	/** ArrayList for the PostAnnotation indexes */
	ArrayList<Integer> innerAnnoSet = new ArrayList<Integer>();

	/** ArrayList for the InnerTestAnnotation */
	ArrayList<InnerTestAnnotation> innerAnnoList = new ArrayList<InnerTestAnnotation>();

	/** the annotation expression counter */
	int index = 0;
	
	/** ArrayList that contains the ClassInfos */
	ArrayList<ClassInfo> classInfos = new ArrayList<ClassInfo>();
	/** Arraylist for the ClassInfo parse exception messages */
	ArrayList<String> classErrMessages = new ArrayList<String>();
	
	/** ArrayList for the right parsed ClassInfo's indexes */
	ArrayList<Integer> classIndexSet = new ArrayList<Integer>();

	/** the class counter */
	int count = 0;
	
	@Override
	public void visit(ClassOrInterfaceDeclaration n, Object arg) {
		// Code for the ClassInfo test
		try {
			ClassInfo ci = new ClassInfo(n);
			this.classInfos.add(ci);
			ci.parse();
			this.classIndexSet.add(count);
		} catch (JtocException e) {
			classErrMessages.add(e.getMessage());
		} finally {
			count++;
		}
		
		// Code for the InnerTest test
		List<AnnotationExpr> list = n.getAnnotations();
		if (list == null) // the method has no annotation.
			return;
		
		for (AnnotationExpr ae : list) {
			if (InnerTestAnnotation.isInstance(ae)) {
				try {
					InnerTestAnnotation ita = new InnerTestAnnotation(ae);
					ita.parse();
					innerAnnoList.add(ita);
					logger.info(ita.toString());
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
