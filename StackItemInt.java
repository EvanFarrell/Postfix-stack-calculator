
public class StackItemInt extends StackItem {

  private int intValue;
  private char value;
  private StackItemInt lowerItem;

  public StackItemInt(int intValue){
    this.intValue = intValue;
  }
  public StackItemInt(char value){
    this.value = value;
  }
  public StackItemInt(){
  }

  public int getIntValue(){
    return intValue;
  }



}
