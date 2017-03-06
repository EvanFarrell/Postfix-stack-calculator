/**
* Referenced-based stack that used stack items which
* reference each other to form the stack itself.
*
* @author Evan Farrell - 101009799
* @version 1.0
*/
public class StackReferenceBased{

  //instance variables
  private StackItem top; //References the top item in the stack
  private int items; //Counts the number of items in the stack

  /**
  * Constructor for the StackReferenceBased class
  */
  public StackReferenceBased(){
    createStack();
  }

  /**
  * This method is called in the constructor for the class
  * The 'top' stackitem is used to reference to the top
  * item in the stack at all times
  *
  * @return  current value field for this instance
  */
  private void createStack(){ //note private
    top = new StackItem();
    items = 0;
  }

  /**
  * Adds a StackItem to the top of the stack
  *
  * @param  newItem new stack item to push to the top of the stack
  */
  public void push (StackItem newItem){
    newItem.setLowerItem(top);
    top = newItem;
    items++;
  }

  /**
  * Removes the top item of the stack
  *
  * @return  StackItem instance which was on the top of the stack
  */
  public StackItem pop(){
    StackItem retTop = top;
    top = top.getLowerItem();
    items--;
    return retTop;
  }

  /**
  * Removes the top item of the stack, without returning the object
  */
  public void popNoReturn(){
    top = top.getLowerItem();
    items--;
  }

  /**
  * Gets the top item in the stack without removing it
  *
  * @return  top item in the stack
  */
  public StackItem peek(){
    return top;
  }

  /**
  * Checks if the stack has any items in it
  *
  * @return  true IFF the stack is empty (items = 0)
  */
  public boolean isEmpty(){
    if (items == 0)
      return true;
    else
      return false;
  }

  /**
  * Removes all items from the stack
  */
  public void popAll(){
    for (int i = 0; i < items; i++){
        popNoReturn();
    }
  }

  /**
  * Used to print the stack and its items
  *
  * @return  Each stack item's character value inside a [ ], arranged vertically
  */
  public String toString(){
    String retString = new String("");
    for (StackItem curr = peek(); curr != null; curr = curr.getLowerItem()){
      retString += ("[" + Character.toString(curr.getValue()) + "]\n");
    }
    return retString;
  }
}
