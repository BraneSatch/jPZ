package fabrika.roba;

import java.io.Serializable;

public class Poluproizvod implements Serializable{
  private static int redniBroj = 1000;
  private String serijskiBroj;
  
  public Poluproizvod(){
    serijskiBroj = "ppr" + redniBroj++;
  }
  
  public String getSerijskiBroj(){
    return this.serijskiBroj;
  }
}