public class StackReferenceBased{

  private StackItem top;
  private int items;

  public StackReferenceBased(){
    createStack();
  }

  private void createStack(){ //note private
    top = new StackItem();
    items = 0;
  }

  public void push (StackItem newItem){
    newItem.setLowerItem(top);
    top = newItem;
    items++;
  }

  public StackItem pop(){
    top = top.getLowerItem();
    items--;
    return top;
  }

  public StackItem peek(){
    return top;
  }

  public boolean isEmpty(){
    if (items == 0)
      return true;
    else
      return false;
  }

  public void popAll(){
    for (int i = 0; i < items; i++){
        StackItem returnVal = pop();
    }
  }

  public String toString(){
    String retString = new String("");
    for (StackItem curr = peek(); curr != null; curr = curr.getLowerItem()){
      retString += ("[" + Character.toString(curr.getValue()) + "]\n");
    }
    return retString;
  }


}
