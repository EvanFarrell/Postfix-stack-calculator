/**
Helper class used to form a reference based linked list

@author Evan Farrell - 101009799
@version 1.0 
*/
public class StackItem {

  //Instance variables
  private char value;
  //Second data field, used  only for calcuating postfix equations
  private double numberValue;
  private StackItem lowerItem;

  /**
  Consructor for stack item with only a value peram
  *
  * @param  value  initial value for the value field
  */
  public StackItem (char value){
    this.value = value;
  }
  /**
  Constructor for Stack item with only a numberValue param
  *
  * @param  value initial value for the numberValue field
  */
  public StackItem (double value){
    numberValue = value;
  }

  /**
  * Consructor for stack item with no initial values
  */
  public StackItem (){
  }

  /**
  * Getter for the char value field
  *
  * @return  current value field for this instance
  */
  public char getValue(){
    return value;
  }

  /**
  * Getter for the next item in the stack
  *
  * @return  the stackitem underneath this one in a stack
  */
  public StackItem getLowerItem(){
    return lowerItem;
  }

  /**
  * Setter for the char value field
  *
  * @param  newChar value to change the value field to
  */
  public void setValue(char newChar){
    value = newChar;
  }

  /**
  * Setter for the lowerItem field
  *
  * @param  newLowerItem  new stackitem to put underneath this one
  */
  public void setLowerItem(StackItem newLowerItem){
    lowerItem = newLowerItem;
  }

  /**
  * Getter for the numbervalue field
  *
  * @return  current double stored by this stack item
  */
  public double getNumberValue(){
    return numberValue;
  }

  /**
  * Setter for the numbervalue field
  *
  * @param value new double for the value field
  */
  public void setNumberValue(double value){
    numberValue = value;
  }

}
