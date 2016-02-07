package fabrika.roba.prozori;

import java.io.Serializable;

public class Roletne implements Serializable{
  private int brojDijelova;
  
  public Roletne(int br){
    brojDijelova = br;
  }
  
  public int getBrojDijelova(){
    return this.brojDijelova;
  }
  
}