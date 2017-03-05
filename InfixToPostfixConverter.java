import java.util.*;

public class InfixToPostfixConverter{

  public static ArrayList<String> infixOperation = new ArrayList<String>(); //FIX ME
  public static StackReferenceBased myStack = new StackReferenceBased();

  public static void parseUserInput(String input){
    char[] charArray = input.toCharArray();
    for (int i = 0; i<charArray.length; i++){ //note the -1

      if (isOperator(charArray[i])){ //Put any operators right onto the stack
        if (!myStack.isEmpty()){
          //If the top of the stack is on the same BEDMAS level as the next operator, pop it off first
          if (equalPrecedence(Character.toString(charArray[i]), myStack.peek()){
            infixOperation.add(myStack.pop());
            myStack.push(new StackItem(charArray[i]));
          }
          //Once a ) has been found, all operators inside need to be popped onto the infix operation
          else if(charArray[i].equals(')')){ // || charArray[i].equals(']') || charArray[i].equals('}')){
            for(int j=i; myStack.peek() != '('; j--)
                infixOperation.add(myStack.pop());
          }
          else
            myStack.push(new StackItem(charArray[i]));
        }
        else
          myStack.push(new StackItem(charArray[i]));
      }

      if (charArray[i] != ' ' && !isOperator(charArray[i])){ //if conditional is entered, the char is a variable
        boolean waitingForNonInteger = true;
        int intCounter = 0;
        while(waitingForNonInteger){
          if ((Character.getNumericValue(charArray[i+intCounter]) >= 0) && (Character.getNumericValue(charArray[i+intCounter]) <= 9)){
            infixOperation.add(charArray[i+intCounter]); //TODO convert to string to accept multi digit nums
            intCounter++;
          }
          else{
            System.out.println("Numeric value: " + Character.getNumericValue(charArray[i+1]) + "Actual value: " + charArray[i]);
            waitingForNonInteger=false;
          }
        }//end while
      }
      //else, the character is a space, ignore it
    }
  }

  public static boolean equalPrecedence(String operand1, String operand2){
    if (isMulOrDivision(operand1) && isMulOrDivision(operand2))
      return true;
    else if (isAddOrSubtraction(operand1) && isAddOrSubtraction(operand2))
      return true;
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

  public static boolean isOperator(char element){
    if (element == '*' || element == '+' || element == '-'|| element == '/')
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
    String input = new String("4+3*45");
    parseUserInput(input);
    System.out.println("ArrayList of operands: " + infixOperation + "\nStack of operators: \n" + myStack);
  }
}
