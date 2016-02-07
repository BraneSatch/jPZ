package fabrika.proizvodnja.masine;

import java.util.LinkedList;
import java.io.File;

import fabrika.roba.Proizvod;
import fabrika.roba.Poluproizvod;

public class Masina implements Runnable{
  protected String oznaka;
  protected int brzina;
  
  public static final String putanja = "fabrika" + File.separator + "proizvodnja" + File.separator + "masine" + File.separator;
  
  public Masina(String ozn, int br){
    oznaka = ozn;
    brzina = br;
  }
  
  public String getOznaka(){
    return this.oznaka;
  }
  
  public int getBrzina(){
    return this.brzina;
  }
  
  @Override
  public void run(){}
}