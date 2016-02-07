package fabrika.roba.prozori;

import java.io.Serializable;

import fabrika.roba.Proizvod;
import fabrika.roba.Poluproizvod;
import fabrika.roba.Materijal;

public class Prozor extends Proizvod implements Serializable{
  private Roletne roletne;
  private boolean resetke;
  private int brojKrila;
  private boolean krovniProzor;
  
  public Prozor(double s, double v, Materijal vM, Poluproizvod k[], Roletne r, boolean re, int brKrila){
    super(s,v,vM,k);
    roletne = r;
    resetke = re;
    brojKrila = brKrila;
  }
  
  public void dodajRoletne(Roletne r){
    this.roletne = r;
  }
}