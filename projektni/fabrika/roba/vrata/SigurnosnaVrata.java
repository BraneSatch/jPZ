package fabrika.roba.vrata;

import java.io.Serializable;

import fabrika.roba.Materijal;
import fabrika.roba.Poluproizvod;

public class SigurnosnaVrata extends Vrata implements Serializable{
   public SigurnosnaVrata(double s, double v, Materijal vM, Poluproizvod k[], boolean imaS, boolean imaR){
    super(s,v,vM,k,imaS,imaR);
   }
}