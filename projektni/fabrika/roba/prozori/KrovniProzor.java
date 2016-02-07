package fabrika.roba.prozori;

import java.io.Serializable;

import fabrika.roba.Materijal;
import fabrika.roba.Poluproizvod;

public class KrovniProzor extends Prozor implements Serializable{
  
  public KrovniProzor(double s, double v, Materijal vM, Poluproizvod k[], Roletne r, boolean re, int brKrila){
    super(s, v, vM, k, r, re, brKrila);
  }
  
}