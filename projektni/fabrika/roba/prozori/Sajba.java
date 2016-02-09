package fabrika.roba.prozori;

import fabrika.roba.Proizvod;
import fabrika.roba.Poluproizvod;
import fabrika.roba.Materijal;
  
public class Sajba extends Proizvod{
  
  Sajba(double s, double v, Materijal vM, Poluproizvod k[]){
    super(s,v,vM,k);
  }
}