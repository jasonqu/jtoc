/**
 * 
 */
package org.jtoc.convertor.cpunit;

import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.expr.AnnotationExpr;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * used for test
 * 
 * @author Goddamned Qu
 */
public class MethodVisitor extends VoidVisitorAdapter<Object> {

	private static Log logger = LogFactory.getLog(MethodVisitor.class);

	/** Arraylist for the AnnotationExpr */
	ArrayList<AnnotationExpr> annolist = new ArrayList<AnnotationExpr>();

	/** Arraylist for the PreAnnotation */
	ArrayList<PreAnnotation> prelist = new ArrayList<PreAnnotation>();
	/** Arraylist for the PreAnnotation parse exception messages */
	ArrayList<String> prelistmessages = new ArrayList<String>();

	/** Set for the PreAnnotation indexes */
	ArrayList<Integer> preAnnoSet = new ArrayList<Integer>();

	/** the annotation expression counter */
	int index = 0;
	
	/**
	 * add the JtocAnnotations and related error messages into the list
	 */
	@Override
	public void visit(MethodDeclaration n, Object arg) {
		List<AnnotationExpr> list = n.getAnnotations();
		if (list == null) // the method has no annotation.
			return;

		for (AnnotationExpr ae : list) {
			if (PreAnnotation.isInstance(ae)) {
				try {
					PreAnnotation anno = new PreAnnotation(ae);
					anno.parse();
					prelist.add(anno);
					logger.info(anno.toString());
				} catch (JtocFormatException e) {
					logger.error(e.getMessage());
					prelistmessages.add(e.getMessage());
				} finally {
					preAnnoSet.add(index);
				}
			}
			annolist.add(ae);
			index++;
		}
	}
}
