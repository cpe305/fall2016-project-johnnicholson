package api;

import model.PrintRequest.Status;

public class PrintRequestPut {
  public Integer sequence;
  public String description;
  public String fileName;
  public int ownerId;
  public int locationId;
  public Status status;
}
