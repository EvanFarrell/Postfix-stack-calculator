public class StackItem {

  private char value;
  private StackItem lowerItem;


  public StackItem (char value){
    this.value = value;
  }
  public StackItem (){
  }

  public char getValue(){
    return value;
  }
  public StackItem getLowerItem(){
    return lowerItem;
  }
  public void setValue(char newChar){
    value = newChar;
  }
  public void setLowerItem(StackItem newLowerItem){
    lowerItem = newLowerItem;
  }

}
