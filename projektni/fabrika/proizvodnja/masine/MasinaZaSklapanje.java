package fabrika.proizvodnja.masine;

import java.util.ArrayList;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

import fabrika.roba.Poluproizvod;
import fabrika.roba.Proizvod;
import fabrika.roba.vrata.*;
import fabrika.roba.prozori.*;
import fabrika.roba.poluproizvodi.*;
import fabrika.roba.Materijal;

public class MasinaZaSklapanje extends Masina{
  private ArrayList<Poluproizvod> stanje;
  
  public MasinaZaSklapanje(String ozn, int br){
    super(ozn, br);
    stanje = new ArrayList<Poluproizvod>();
  }
  
  public Vrata sklopiVrata(double visina, double sirina, Materijal m, boolean st, boolean resetke, boolean sigurna){
    Vrata v;
    
    File konstrukcija = new File(putanja + "konstrukcija.s");
    Konstrukcija k = new Konstrukcija(m);
    //System.out.println(k.getSerijskiBroj());                     
    File mehanizam = new File(putanja + "mehanizam.s");
    Mehanizam me = new Mehanizam();
    
    konstrukcija.delete();
    mehanizam.delete();
    PrintWriter pw;
    
    File staklo;
    Staklo s;
    Poluproizvod[] spisak;
    
    if (st){
      s = new Staklo();
      staklo = new File(putanja + "staklo.s");
      staklo.delete();
      spisak = new Poluproizvod[3];
      spisak[0] = k;
      spisak[1] = me;
      spisak[2] = s;
    
      if (sigurna){
        v = new SigurnosnaVrata(sirina, visina, m, spisak, st, resetke);
      }else{
        v = new Vrata(sirina, visina, m, spisak, st, resetke);
      }
    
    }else{
      spisak = new Poluproizvod[2];
      spisak[0] = k;
      spisak[1] = me;
      
      if (sigurna){
        v = new SigurnosnaVrata(sirina, visina, m, spisak, st, resetke);
      }else{
        v = new Vrata(sirina, visina, m, spisak, st, resetke);
      }
    }

    try{
      pw = new PrintWriter(putanja + this.getOznaka() + File.separator + v.getSerijskiBroj() + ".txt");
    }catch(FileNotFoundException e){
      System.out.println("Masina " + this.getOznaka() + " nije u stanju da evidentira proizvod " + v.getSerijskiBroj());
    }
    
    return v;
  }
  
  public Prozor sklopiProzor(double s, double v, Materijal m, boolean roletne, boolean resetke, boolean krovni, int brKrila){
    Prozor p;
    File konstrukcija = new File(putanja + "konstrukcija.s");
    Konstrukcija k = new Konstrukcija(m);
                                     
    File mehanizam = new File(putanja + "mehanizam.s");
    Mehanizam me;
    if (krovni)
      me = new KrovniMehanizam();
    else
      me = new Mehanizam();

    File staklo = new File(putanja + "staklo.s");
    Staklo st = new Staklo();
    
    konstrukcija.delete();
    mehanizam.delete();
    staklo.delete();
    PrintWriter pw;
    
    Poluproizvod[] spisak;
    spisak = new Poluproizvod[3];
    spisak[0] = k;
    spisak[1] = me;
    spisak[2] = st;
    
    Roletne r = null;
    
    if (roletne)
      r = new Roletne(1);
    
    if (krovni){
      p = new KrovniProzor(s, v, m, spisak, r, resetke, brKrila);
    }else{
      p = new Prozor(s, v, m, spisak, r, resetke, brKrila);
    }
    
    try{
      pw = new PrintWriter(putanja + this.getOznaka() + File.separator + p.getSerijskiBroj() + ".txt");
    }catch(FileNotFoundException e){
      System.out.println("Masina " + this.getOznaka() + " nije u stanju da evidentira proizvod " + p.getSerijskiBroj());
    }
    
    return p;
  }
}