package cs2321;

public class PostfixExpression {

	/**
	 * Evaluate a postfix expression. 
	 * Postfix expression notation has operands first, following by the operations.
	 * For example:
	 *    13 5 *           is same as 13 * 5 
	 *    4 20 5 + * 6 -   is same as 4 * (20 + 5) - 6  
	 *    
	 * In this homework, expression in the argument only contains
	 *     integer, +, -, *, / and a space between every number and operation. 
	 * You may assume the result will be integer as well. 
	 * 
	 * @param exp The postfix expression. You may assume the expression is valid. 
	 * @return the result of the expression. 
	 */
	public static int evaluate(String exp) {
		LinkedListStack<Integer> stack = new LinkedListStack<Integer>(); //the stack used
		int num1; //an instance variable to track the first value popped off the stack
		int num2; //an instance variable to track the second value popped off the stack
		int result; //an instance variable to track the result of the operation performed on the values that are popped off the stack
		String [] tokens = exp.split(" "); //creating the array of the tokens from the given string parameter
		/*
		 * for each loop to handle each token in the array, pushing numbers on and popping them off if an expression
		 * is found.
		 */
		for(String token : tokens) {
			if(token.equals("+")) {
				num1 = stack.pop();
				num2 = stack.pop();
				result = num1+num2;
				stack.push(result);
			}
			else if(token.equals("-")) {
				num1 = stack.pop();
				num2 = stack.pop();
				result = num2 - num1;
				stack.push(result);
			}
			else if(token.equals("*")){
				num1 = stack.pop();
				num2 = stack.pop();
				result = num1 * num2;
				stack.push(result);
			}
			else if(token.equals("/")) {
				num1 = stack.pop();
				num2 = stack.pop();
				result = num2 / num1;
				stack.push(result);
			}
			else {
				stack.push(Integer.parseInt(token));
			}
		}
		return stack.pop();
	}			
}
