/**
 * 
 */
package org.jtoc.convertor.cpunit;

import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.expr.AnnotationExpr;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * used for MethodInfo, PreAnnotation, PostAnnotation's test
 * 
 * @author Goddamned Qu
 */
public class MethodVisitor extends VoidVisitorAdapter<Object> {

	/** Arraylist for the AnnotationExpr */
	ArrayList<AnnotationExpr> annolist = new ArrayList<AnnotationExpr>();

	/** Arraylist for the PreAnnotation */
	ArrayList<PreAnnotation> prelist = new ArrayList<PreAnnotation>();
	/** Arraylist for the PreAnnotation parse exception messages */
	ArrayList<String> prelistmessages = new ArrayList<String>();

	/** ArrayList for the PreAnnotation indexes */
	ArrayList<Integer> preAnnoSet = new ArrayList<Integer>();
	/** ArrayList for the PostAnnotation indexes */
	ArrayList<Integer> postAnnoSet = new ArrayList<Integer>();

	/** the annotation expression counter */
	int index = 0;
	
	/** Arraylist for the MethodInfo */
	ArrayList<MethodInfo> methodlist = new ArrayList<MethodInfo>();
	
	/** Arraylist for the PostAnnotation */
	ArrayList<PostAnnotation> postlist = new ArrayList<PostAnnotation>();
	/** Arraylist for the PostAnnotation parse exception messages */
	ArrayList<String> postlistmessages = new ArrayList<String>();
	
	/**
	 * add the JtocAnnotations and related error messages into the list
	 */
	@Override
	public void visit(MethodDeclaration md, Object arg) {
		try {
			methodlist.add(MethodInfo.getMethodInfoFromMethodDecl(md));
		} catch (JtocFormatException e1) {
			// do nothing here
		}
		
		List<AnnotationExpr> list = md.getAnnotations();
		if (list == null) // the method has no annotation.
			return;

		for (AnnotationExpr ae : list) {
			if (PreAnnotation.isInstance(ae)) {
				preAnnoSet.add(index);
				try {
					prelist.add(PreAnnotation.getPreAnnotationFromAnnoExpr(ae));
				} catch (JtocFormatException e) {
					prelistmessages.add(e.getMessage());
				}
			} else if (PostAnnotation.isInstance(ae)) {
				postAnnoSet.add(index);
				try {
					postlist.add(PostAnnotation
							.getPostAnnotationFromAnnoExpr(ae));
				} catch (JtocFormatException e) {
					postlistmessages.add(e.getMessage());
				}
			}
			annolist.add(ae);
			index++;
		}
	}
}
