import java.util.*;
import java.io.*;

/**
* Class designed to convert a user-provided infix operation to its postfix form,
* then evaluate the postfix operation. Both convertion and calculation of the postfix
* operation are acheived via the use of StackReferenceBased
*
* @author  Evan Farrell - 101009799
* @version 1.0
*/
public class Infix2PostfixConverter{

  //used to store the postfix version of the operation specified by the user
  public static ArrayList<String> postfixOperation = new ArrayList<String>();
  //the stack used for infix to postfix convertion
  public static StackReferenceBased myStack = new StackReferenceBased();

  /**
  * Converts and infix expression to its postfix equvalent. Operands are sequentially
  * added to postfixOperation. Once a operator is found via traversal, it is added to the
  * stack, and is popped into the postfixOperation arraylist once a another operator of
  * equal or lower BEDMAS precedence is encountered. Brackets and spaces are removed
  * from the original statement.
  *
  * @param input User-defined infix expression to be converted to postfix
  */
  public static void convertPostfix(String input){

    //The user input is converted to a character array to be iterated over
    char[] charArray = input.toCharArray();
    for (int i = 0; i<charArray.length; i++){

      //CASE 1 - the char is an operator (note, brackets are included)
      if (isOperator(charArray[i])){ //Put any operators right onto the stack
        if (!myStack.isEmpty()){
          //If the top of the stack is on the same BEDMAS level as the next operator, pop it off first
          if (equalPrecedence(  Character.toString(charArray[i]), Character.toString(myStack.peek().getValue())  )){
            postfixOperation.add(Character.toString(myStack.pop().getValue()));
            myStack.push(new StackItem(charArray[i]));
          }

          //If the next char from input is a higher BEDMAS value than the top of the stack, put it
          //onto the end of the postfix operation. If it is LOWER, then put the top stack item
          //into the postfix operation, and push the next input char onto the stack
          else if (greaterPrecedence(  Character.toString(charArray[i]), Character.toString(myStack.peek().getValue()) ))
            myStack.push(new StackItem(charArray[i]));

          //Check if the top of the stack is of higher precedence than the current char in the array
          else if (greaterPrecedence(  Character.toString(myStack.peek().getValue()), Character.toString(charArray[i]) )){
            postfixOperation.add(Character.toString(myStack.pop().getValue()));
            myStack.push(new StackItem(charArray[i]));
          }

          //Once a ) has been found, all operators inside need to be popped onto the infix operation
          else if(charArray[i] == ')'){
            while(myStack.peek().getValue() != '(' && !myStack.isEmpty()){
              postfixOperation.add(Character.toString(myStack.pop().getValue()));
            }
           myStack.popNoReturn(); //Clear the ( off the top of the stack
          }

          else{
            myStack.push(new StackItem(charArray[i]));
            }
        } //end if (isEmpty)
        else
          myStack.push(new StackItem(charArray[i]));
      }//end if (isOperator)

      //CASE 2 - the char is an operand
      if (charArray[i] != ' ' && !isOperator(charArray[i])){ //if conditional is entered, the char is a variable
        boolean waitingForNonInteger = true;
        int intCounter = 0;
        String multiDigitInt = new String();

        //check next elements in the array until a non-integer is NOT encountered
        //This allows [3, 5, 7] to be correctly parsed as 357
        while(waitingForNonInteger){
          if (i+intCounter != charArray.length){
            //While there's room in the array, check if the next indexes (counted by intCounter) are ints
            if ((Character.getNumericValue(charArray[i+intCounter]) >= 0) && (Character.getNumericValue(charArray[i+intCounter]) <= 9) ){
              multiDigitInt += Character.toString(charArray[i+intCounter]);
              charArray[i+intCounter] = ' '; //Remove values once they have been added, to ensure they aren't added twice
              intCounter++;
            }
            else{
              //The final multiple digit integer can be added to the postfix operation
              waitingForNonInteger=false;
              postfixOperation.add(multiDigitInt);
            }
          }//endif (length check)
          else{
            postfixOperation.add(multiDigitInt);
            waitingForNonInteger = false;

          }
        }//end while
      }
    }//end for

    //CASE 3 - the character is a space, ignore it
    while(!myStack.isEmpty()){ //Add all the remaining operands from the stack to the postfix statement
      if (myStack.peek().getValue() == '(' || myStack.peek().getValue() == ')' || myStack.peek().getValue() == ' ')
        myStack.popNoReturn();
      else
        postfixOperation.add(Character.toString(myStack.pop().getValue()));
    }
  }


  /**
  * Calculates the value of a provided postfix expression. The arraylist is traversed;
  * If an operand is encountered, it is pushed onto a stack. If an operator is encountered,
  * The stack is popped twice for the two operators, and the operation is completed. Result
  * is put back onto the stack. The final result will be the last and only item on the stack
  *
  * @param postfix  ArrayList made by convertPostfix containing a postfix expression
  */
  public static double getPostfix(ArrayList<String> postfix){
    StackReferenceBased operandStack = new StackReferenceBased();
    double resultHolder; //USED??


    for (String item: postfix){
      //If operator, then pop twice and operate on those values, then put the result on the stack
      if(item.length() == 1 && isOperator(item.charAt(0))){ //check if operator
        double secondValue = operandStack.pop().getNumberValue();
        double firstValue = operandStack.pop().getNumberValue();
        resultHolder = operate(item.charAt(0), firstValue, secondValue);
        operandStack.push(new StackItem(resultHolder));
        }

      else if (!item.equals(" ")){ //The item must be an integer operand, push it onto the stack
        operandStack.push(new StackItem(Integer.parseInt(item)));
      }
    }//end for

    return operandStack.peek().getNumberValue();
  }

  /**
  * This method is used to perform basic arithmetic with character operators.
  *
  * @param operator   character mathematical operator for the operation
  * @param operand1   first operand for the operation
  * @param operand2   second operand for the operation
  */
  public static double operate (char operator, double operand1, double operand2){
    if (operator == '+')
      return operand1 + operand2;
    else if (operator == '-')
      return operand1 - operand2;
    else if (operator == '/')
      return operand1 / operand2;
    else if (operator == '*')
      return operand1 * operand2;
    else{
      System.out.println("A non-operator operator was passed: " + operator + "\n op1:" + operand1 + "\nop2: " + operand2);
      return 0;
    }
  }

  /**
  * This method checks if two operands have the same BEDMAS precedence
  *
  * @param operand1   first operand for the comparison
  * @param operand2   second operand for the comparison
  * @return           true if the two operands have the same precedence
  */
  public static boolean equalPrecedence(String operand1, String operand2){
    if (isMulOrDivision(operand1) && isMulOrDivision(operand2))
      return true;
    else if (isAddOrSubtraction(operand1) && isAddOrSubtraction(operand2))
      return true;
    else
      return false;
  }

  /**
  * This method checks if one operand has a greater BEDMAS precedence than the other
  *
  * @param operand1   first operand for the comparison
  * @param operand2   second operand for the comparison
  * @return           true if the first operand has a greater precedence than the second
  */
  public static boolean greaterPrecedence(String operand1, String operand2){
    if (isMulOrDivision(operand1) && isAddOrSubtraction(operand2)){
      return true;
    }
    else
      return false;
    }

  /**
  * Checks if a character is a multiplication/division sign
  *
  * @param    operand the character to be checked
  * @return           true iff the operand is either '*' or '/'
  */
  public static boolean isMulOrDivision(String operand){
    if (operand.equals("*") || operand.equals("/"))
      return true;
    else
      return false;
  }

  /**
  * Checks if a character is a addition/subtraction sign
  *
  * @param    operand the character to be checked
  * @return           true iff the operand is either '-' or '+'
  */
  public static boolean isAddOrSubtraction(String operand){
    if (operand.equals("+") || operand.equals("-"))
      return true;
    else
      return false;
    }

  /**
  * Checks if a character is an arithmetic operator
  *
  * @param    element the character to be checked
  * @return           true iff the operand is either *,+,-,/,(,)
  */
  public static boolean isOperator(char element){
    if (element == '*' || element == '+' || element == '-'|| element == '/' || element == '(' || element == ')')
      return true;
    else
      return false;
  }

  //main method
  public static void main(String[] args){

    //The user is prompted for their infix expression
    Scanner sc = new Scanner(System.in);

    String userInfix = new String();
    boolean waitingForInput = true;
    System.out.println("Please enter your entire infix expression: ");
    userInfix = sc.nextLine();

    //The expression is converted to postfix and evaluated
    System.out.println("\n\n\nYour infix expression:   " + userInfix);
    convertPostfix(userInfix);
    System.out.println("Your postfix expression: " + postfixOperation);
    System.out.println("Final answer = " + getPostfix(postfixOperation));


  }
}
