package fabrika.proizvodnja;

import java.util.ArrayList;
import java.util.LinkedList;
import java.net.Socket;
import java.io.File;

import fabrika.proizvodnja.masine.Masina;
import fabrika.proizvodnja.masine.MasinaZaSklapanje;
import fabrika.roba.poluproizvodi.*;
import fabrika.roba.Materijal;
import fabrika.roba.Proizvod;
import fabrika.roba.vrata.*;
import fabrika.roba.prozori.*;
import fabrika.racunovodstvo.*;

public class Proizvodnja extends Thread{
  private ArrayList<Masina> masine;
  private LinkedList<RadniNalog> redNaloga;
  private Racunovodstvo r;
  private boolean prazanRed;
  
  public Proizvodnja(){
    masine = new ArrayList<Masina>();
    redNaloga = new LinkedList<RadniNalog>();
    prazanRed = true;
    this.start();
  }
  
  public void dodajMasinu(Masina m){
    masine.add(m);
    File f = new File(Masina.putanja + m.getOznaka());
    if (!f.exists())
      f.mkdir();
  }
  
  private Masina nadjiMasinu(String oznaka){
    int size = masine.size();
    for(int i = 0 ; i < size; ++i)
      if (masine.get(i).getOznaka().contains(oznaka))
      return masine.get(i);
    return null;
  }
  
  public synchronized void dodajNalog(RadniNalog r){
    System.out.println("dodan");
    redNaloga.add(r);
    prazanRed = false;
    
  }
  
  private void doNothing(){
    try{
    this.sleep(50);
    }catch(Exception e){
      e.printStackTrace();
    }
  }
  
  @Override
  public void run(){
    System.out.println("Proizvodnja radi");
    while(true){
      doNothing();
      if (!prazanRed){
        RadniNalog r = redNaloga.poll();
        
        ArrayList<Proizvod> gotoviProizvodi = new ArrayList<Proizvod>();
        gotoviProizvodi.clear();
        
        if (redNaloga.size() == 0) prazanRed = true;
        System.out.println("usao u red");
        
        String linije[] = r.toString().split(System.lineSeparator());
        
        for(String i: linije){
          String niz[] = i.split("#");
          double visina = Double.parseDouble(niz[1]);
          double sirina = Double.parseDouble(niz[2]);
          Materijal m = "drvo".equals(niz[3].toLowerCase()) ? Materijal.DRVO : Materijal.PVC;
          boolean resetke = "da".equals(niz[5].toLowerCase()) ? true : false;
          
          Thread konstrukcijskaNit = null;
          Thread nitZaStaklo = null;
          Thread nitZaMehanizam = null;
          
          if ("vrata".equals(niz[0])){
            //VRATA
            boolean staklo = "da".equals(niz[4].toLowerCase()) ? true : false;
            boolean sigurnosna = "da".equals(niz[6].toLowerCase()) ? true : false;
            
            //POKRECEMO MASINE
            if (m == Materijal.DRVO){
              Runnable kon = this.nadjiMasinu("konDRVO");
              konstrukcijskaNit = new Thread(kon);
              konstrukcijskaNit.start();
            }else{
              Runnable kon = this.nadjiMasinu("konPVC");
              konstrukcijskaNit = new Thread(kon);
              konstrukcijskaNit.start();
            }
            
            if (staklo){
              Runnable stak = this.nadjiMasinu("st");
              nitZaStaklo = new Thread(stak);
              nitZaStaklo.start();
            }
            
            Runnable meh = this.nadjiMasinu("mh");
            nitZaMehanizam = new Thread(meh);
            nitZaMehanizam.start();
            
            //CEKA SE DA SVAKA MASINA ZAVRSI SVOJ DIO
            try{
              konstrukcijskaNit.join();
              if (nitZaStaklo != null) nitZaStaklo.join();
              nitZaMehanizam.join();
            }catch(Exception e){
              e.printStackTrace();
            }
            
            //SKLAPA SE PROIZVOD I DODAJE U LISTU PROIZVODA KOJI SE SALJU
            gotoviProizvodi.add(((MasinaZaSklapanje)this.nadjiMasinu("ass")).sklopiVrata(visina, sirina, m, staklo, resetke, sigurnosna));
            
          }else{
            //PROZOR
            boolean roletne = "da".equals(niz[4].toLowerCase()) ? true : false;
            boolean krovni = "da".equals(niz[6].toLowerCase()) ? true : false;
            
            //POKRECU SE MASINE
            if (m == Materijal.DRVO){
              Runnable kon = this.nadjiMasinu("konDRVO");
              konstrukcijskaNit = new Thread(kon);
              konstrukcijskaNit.start();
            }else{
              Runnable kon = this.nadjiMasinu("konPVC");
              konstrukcijskaNit = new Thread(kon);
              konstrukcijskaNit.start();
            }
            
            Runnable stak = this.nadjiMasinu("st");
            nitZaStaklo = new Thread(stak);
            nitZaStaklo.start();
            
            Runnable meh = this.nadjiMasinu("mh");
            nitZaMehanizam = new Thread(meh);
            nitZaMehanizam.start();
            
            //CEKA SE DA SVAKA MASINA ZAVRSI SVOJ DIO
            try{
              konstrukcijskaNit.join();
              if (nitZaStaklo != null) nitZaStaklo.join();
              nitZaMehanizam.join();
            }catch(Exception e){
              e.printStackTrace();
            }
            
            //SKLAPA SE PROIZVOD I DODAJE U LISTU PROIZVODA KOJI SE SALJU
            gotoviProizvodi.add(((MasinaZaSklapanje)this.nadjiMasinu("ass")).sklopiProzor(sirina, visina, m, roletne, resetke, krovni, 2));
          }
          
        }
        
        //INSTANCIRA SE DOSTAVLJAC KOJI CE PREDATI LISTU PROIZVODA FILIJALI PREMA NALOGU
        Dostavljac dostava = new Dostavljac(r.getSocket(),r.getBrNalog(), gotoviProizvodi);
      }
    }
  }
}