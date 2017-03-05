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
          //Once a ) has been found, all operators inside need to be popped onto the infix operation
          else if(charArray[i] == ')'){ // || charArray[i].equals(']') || charArray[i].equals('}')){
            for(int j=i; myStack.peek().getValue() != '(' && !myStack.isEmpty(); j--)
                infixOperation.add(Character.toString(myStack.pop().getValue()));
          }
          else
            myStack.push(new StackItem(charArray[i]));
        }
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
              System.out.println("Passed checks for int: " + charArray[i+intCounter]);
              charArray[i+intCounter] = ' '; //Remove values once they have been added, to ensure they aren't added twice
              intCounter++;
            }
            else{
              System.out.println("Int to add: "+ multiDigitInt);
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
    }

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
    String input = new String("4+3*45/12");
    parseUserInput(input);
    System.out.println("ArrayList of operands: " + infixOperation + "\nStack of operators: \n" + myStack);
  }
}
