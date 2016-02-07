package fabrika.roba.poluproizvodi;

import java.io.Serializable;

import fabrika.roba.Poluproizvod;
import fabrika.roba.Materijal;

public class Konstrukcija extends Poluproizvod implements Serializable{
  Materijal mat;
  
  public Konstrukcija(Materijal m){
    super();
    mat = m;
  }
  
  public Materijal getMaterijal(){
    return this.mat;
  }
}