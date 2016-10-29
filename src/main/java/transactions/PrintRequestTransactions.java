package transactions;

import model.PrintRequest;

public class PrintRequestTransactions {

  public static class PostRequest extends Transaction<Integer> {

    private PrintRequest preq;
    
    public PostRequest(PrintRequest preq) {
      this.preq = preq;
    }
    
    @Override
    public Integer action() {
      
      return null;
    }
    
  }
  
}
