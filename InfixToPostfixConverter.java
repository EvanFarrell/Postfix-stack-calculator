import java.util.*;

public class InfixToPostfixConverter{

  public static ArrayList<String> infixOperation = new ArrayList<String>(); //FIX ME
  public static StackReferenceBased myStack = new StackReferenceBased();

  public static void parseUserInput(String input){
    char[] charArray = input.toCharArray();
    for (int i = 0; i<charArray.length; i++){ //note the -1

      //CASE 1 - the char is an operator
      if (isOperator(charArray[i])){ //Put any operators right onto the stack
        if (!myStack.isEmpty()){
          //If the top of the stack is on the same BEDMAS level as the next operator, pop it off first
          if (equalPrecedence(  Character.toString(charArray[i]), Character.toString(myStack.peek().getValue())  )){
            infixOperation.add(Character.toString(myStack.pop().getValue()));
            myStack.push(new StackItem(charArray[i]));
          }

          //If the next char from input is a higher BEDMAS value than the top of the stack, put it
          //onto the end of the postfix operation. If it is LOWER, then put the top stack item
          //into the postfix operation, and push the next input char onto the stack
          else if (greaterPrecedence(  Character.toString(charArray[i]), Character.toString(myStack.peek().getValue()) ))
            myStack.push(new StackItem(charArray[i]));
            //System.out.println("" + Character.toString(charArray[i] + " has greater p than")
          else if (greaterPrecedence(  Character.toString(myStack.peek().getValue()), Character.toString(charArray[i]) )){
            infixOperation.add(Character.toString(myStack.pop().getValue()));
            myStack.push(new StackItem(charArray[i]));
          }


          //Once a ) has been found, all operators inside need to be popped onto the infix operation
          else if(charArray[i] == ')'){
            for(int j=i; myStack.peek().getValue() != '(' && !myStack.isEmpty(); j--){ //TODO J does nothing!
              infixOperation.add(Character.toString(myStack.pop().getValue()));
            }
           myStack.popNoReturn(); //Clear the ( off the top of the stack
          }
          else{
            myStack.push(new StackItem(charArray[i]));
            if (charArray[i] == '(')
              System.out.println("Just added ( " + myStack.toString());
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

        while(waitingForNonInteger){
          if (i+intCounter != charArray.length){
            if ((Character.getNumericValue(charArray[i+intCounter]) >= 0) && (Character.getNumericValue(charArray[i+intCounter]) <= 9) ){
              //infixOperation.add(Character.toString(charArray[i+intCounter])); //TODO convert to string to accept multi digit nums
              multiDigitInt += Character.toString(charArray[i+intCounter]);
              charArray[i+intCounter] = ' '; //Remove values once they have been added, to ensure they aren't added twice
              intCounter++;
            }
            else{
              waitingForNonInteger=false;
              infixOperation.add(multiDigitInt);
            }
          }//endif (length check)
          else{
            infixOperation.add(multiDigitInt);
            waitingForNonInteger = false;

          }
        }//end while
      }
      //CASE 3 - the character is a space, ignore it
    }//end for
    while(!myStack.isEmpty()){ //Add all the remaining operands from the stack to the postfix statement
      infixOperation.add(Character.toString(myStack.pop().getValue()));
    }
  }

  public static double getPostfix(ArrayList<String> postfix){
    StackReferenceBased operandStack = new StackReferenceBased();
    int resultHolder; //USED??
    for (String item: postfix){
      if(item.length() == 1){
        if (isOperator(item.charAt(0))){ //Check if operator
          resultHolder = operate(item.charAt(0), operandStack.pop().getValue(), operandStack.pop().getValue());
          operandStack.push(new StackItem(resultHolder));
        }
      }
      else if (!item.equals(" ")){ //The item must be a number
        operandStack.push(new StackItem(String.valueOf(item)));
      }
    }
  }

  public static double operate (char operator, int operand1, int operand2){
    if (operator == '+')
      return operand1 + operand2;
    else if (operator == '-')
      return operand1 - operand2;
    else if (operator == '/')
      return operand1 / operand2;
    else if (operator == '*')
      return operand1 * operand2;
  }

  public static boolean equalPrecedence(String operand1, String operand2){
    if (isMulOrDivision(operand1) && isMulOrDivision(operand2))
      return true;
    else if (isAddOrSubtraction(operand1) && isAddOrSubtraction(operand2))
      return true;
    else
      return false;
  }

  public static boolean greaterPrecedence(String operand1, String operand2){
    if (isMulOrDivision(operand1) && isAddOrSubtraction(operand2)){
      System.out.println(""+ operand1 + " has greater prec than" + operand2);
      return true;
    }
    else
      return false;
    }

  public static boolean isMulOrDivision(String operand){
    if (operand.equals("+") || operand.equals("-"))
      return true;
    else
      return false;
  }

  public static boolean isAddOrSubtraction(String operand){
    if (operand.equals("+") || operand.equals("-"))
      return true;
    else
      return false;
    }

  public static boolean isOperator(char element){
    if (element == '*' || element == '+' || element == '-'|| element == '/' || element == '(' || element == ')')
      return true;
    else
      return false;
  }

  public static int getIntFromString (String input, int startPosition){
    for (int i = startPosition; i < input.length(); i++){
      try{
        return Character.getNumericValue(input.charAt(i));
      }
      catch(NumberFormatException e){
        System.out.println("NumberFormatException, good!");
        return Integer.parseInt(input.substring(startPosition, i)); //String to int
      }
      catch(NullPointerException e){
        System.out.println("NullPointerException, good!");
        return Integer.parseInt(input.substring(startPosition, i)); //String to int
      }
    }
    return 404;
  }

  public static void main(String[] args){
    String input = new String("(10 + 3 / 4 * 6)");
    parseUserInput(input);
    System.out.println("\nTest1: \nInfix operation: " + input); //TODO change infixoperation to POST (the list name)
    System.out.println("Postfix operation: " + infixOperation + "\nStack of operators: \n" + myStack);
    infixOperation = new ArrayList<String>();

    String input2 = new String("(4+3-4)-45+12");
    parseUserInput(input2);
    System.out.println("\nTest2: \nInfix operation: " + input2);
    System.out.println("Postfix operation: " + infixOperation + "\nStack of operators: \n" + myStack);
    infixOperation = new ArrayList<String>();

    String input3 = new String("3 * ( 17 - (5+2))/(2+3)");
    parseUserInput(input3);
    System.out.println("\nTest3: \nInfix operation: " + input3);
    System.out.println("Postfix operation: " + infixOperation + "\nStack of operators: \n" + myStack);
    infixOperation = new ArrayList<String>();
  }
}
