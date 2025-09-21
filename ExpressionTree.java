package cs2321;
import cs2321util.ArrayList;
import net.datastructures.*;

public class ExpressionTree {
	
	//Given a binary tree associated with an arithmetic expressions with operators: +,-,*,/ and decimal numbers
	//Evaluate the result and return the result. You may assume the given tree is in correct form. 
	
	//Example 1:
	//    +
	//  /  \
	// 2  4.5
	//
	// (2+4.5) = 6.5
	// Expected output: 6.5
	
	
	//Example 2:
	//
	//        +
	//      /   \
	//    *       *
	//  /  \     /  \
	//  2   -   3   2
	//     / \
	//     5  1 
	//
	// ((2*(5-1))+(3*2)) = 14
	// Expected output: 14
	//
	// Use Double.parseDouble(string) to convert string to double. 
	// For example to convert string "6.5" to double 6.5. 
	//
	
	public static double eval(BinaryTree<String> tree) {
		Position<String> root = tree.root();
		return postOrderEval(tree, root);
	}
	
	/*
	 * A recursive helper method that travels through the tree in a postOrder fashion
	 * as it does this it checks the element of the node and then performs the operations
	 * on the left and right subtrees as it goes, returning the final value at the end of the recursive calls
	 */
	private static double postOrderEval(BinaryTree<String> tree, Position<String> node) {
		if(node == null) {
			return 0;
		}
		
		if(tree.left(node) == null && tree.right(node) == null) {
			return Double.parseDouble(node.getElement());
		}
		
		double leftVal = 0;
		double rightVal = 0;
		if (tree.left(node) != null) {
            leftVal = postOrderEval(tree, tree.left(node));
        }
        if (tree.right(node) != null) {
            rightVal = postOrderEval(tree, tree.right(node));
        }
		
		if(node.getElement().equals("+")) {
			return leftVal + rightVal;
		}
		if(node.getElement().equals("-")) {
			return leftVal - rightVal;
		}
		if(node.getElement().equals("*")) {
			return leftVal * rightVal;
		}
		if(node.getElement().equals("/")) {
			return leftVal / rightVal;
		}
		
		return 0;
	}
	
	//Given a binary tree associated with an arithmetic expressions with operators: +,-,*,/ 
	//and decimal numbers or variables
	
	// Generate the expression with parenthesis around all sub expressions except the leave nodes.  
	// You may assume the given tree is in correct form. 
	// Example:
	//        +
	//      /   \
	//    *       *
	//  /  \     /  \
	//  2   -   3   b
	//     / \
	//     a  1 
	//
	// Expected output: ((2*(a-1))+(3*b)) 
	
	
	public static String toExpression(BinaryTree<String> tree) {
		Position<String> root = tree.root();
		return inOrderEval(tree, root);
	}
	
	/*
	 * A recursive helper method that travels through the tree in an In-order fashion
	 * it slowly builds the string as it moves through the left and right subtrees, with
	 * an explicit call for the root node
	 */
	private static String inOrderEval(BinaryTree<String> tree, Position<String> node) {
		if(node == null) {
			return "";
		}
		
		if(tree.left(node) == null && tree.right(node) == null) {
			return node.getElement();
		}
		
		String left = inOrderEval(tree,tree.left(node));
		String right = inOrderEval(tree, tree.right(node));
		
		return "(" + left + node.getElement() + right + ")";
	}
}