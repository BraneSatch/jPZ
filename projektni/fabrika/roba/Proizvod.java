package fabrika.roba;

import java.io.Serializable;

public class Proizvod implements Serializable{
  private static int redniBroj = 1000;
  
  protected double sirina;
  protected double visina;
  protected String serijskiBroj;
  protected Materijal vrstaMaterijala;
  protected Poluproizvod komponente[];

  public Proizvod(double s, double v, Materijal vM, Poluproizvod k[]){
    sirina = s;
    visina = v;
    vrstaMaterijala = vM;
    komponente = k;
    serijskiBroj = "pr" + redniBroj++;
  }
  
  public String getSerijskiBroj(){
    return this.serijskiBroj;
  }
  
  public Poluproizvod[] getKomponente(){
    return this.komponente;
  }
}