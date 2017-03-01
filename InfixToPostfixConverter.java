import java.util.*;

public class InfixToPostfixConverter{

  public static ArrayList<Character> infixOperation = new ArrayList<Character>(); //FIX ME
  public static StackReferenceBased myStack = new StackReferenceBased();

  public static void parseUserInput(String input){
    char[] charArray = input.toCharArray();
    for (int i = 0; i<charArray.length; i++){

      if (isOperator(charArray[i])){
        myStack.push(new StackItem(charArray[i]));
      }
      if (charArray[i] != ' ' && !isOperator(charArray[i])){
        infixOperation.add(charArray[i]);
      }
      //else, the character is a space, ignore it

    }
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
    String input = new String("4+3*45");
    parseUserInput(input);
    System.out.println("ArrayList of operands: " + infixOperation + "Stack of operators: \n" + myStack);
  }
}
