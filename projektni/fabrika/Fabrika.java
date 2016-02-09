package fabrika;

import java.util.Scanner;
import java.net.Socket;
import java.io.File;

import fabrika.proizvodnja.Proizvodnja;
import fabrika.racunovodstvo.Racunovodstvo;
import fabrika.racunovodstvo.RacunovodstvoRadnik;
import filijala.Narudzba;
import fabrika.proizvodnja.masine.*;

public class Fabrika{
  private Racunovodstvo r;
  private Proizvodnja p;
  private boolean zatvori = false;
  
  public Fabrika(Racunovodstvo r, Proizvodnja p){
    this.r = r;
    this.r.setProizvodnju(p);
    this.p = p;
  }
  
  public Proizvodnja getProizvodnju(){
    return this.p;
  }
  
  public Racunovodstvo getRacunovodstvo(){
    return this.r;
  }
  
  public void zatvoriFabriku(){
    this.zatvori = true;
  }
  
  private int prikaziMeni(){
    int izbor = 0;
    System.out.println("1.PRIKAZI ZAHTJEVE U OBRADI");
    System.out.println("2.PRIKAZI GOTOVE PROIZVODE");
    System.out.println("3.PRIKAZI ZAHTJEVE U REDU");
    System.out.println("0.PREKID RADA FABRIKE");
    Scanner ulaz = new Scanner(System.in);
    do{
      System.out.print("> ");
      izbor = ulaz.nextInt();
      switch(izbor){
        case 1:
          break;
        case 2:
          break;
        case 3:
          break;
        case 0:
          return 0;
        default:
          System.out.println("Birali ste nekorektnu opciju");
          break;
      }
    }while((izbor < 0) || (izbor > 3));
    return izbor;
  }
  
  public static void main(String args[]){
    System.out.println("===================================================");
    System.out.println("SERVER UP!");
    Fabrika f = new Fabrika(new Racunovodstvo(27017), new Proizvodnja());
    f.getProizvodnju().dodajMasinu(new MasinaZaKonstrukciju("konDRVO100",3000));
    f.getProizvodnju().dodajMasinu(new MasinaZaKonstrukciju("konPVC100",4000));
    f.getProizvodnju().dodajMasinu(new MasinaZaStaklo("st100",5000));
    f.getProizvodnju().dodajMasinu(new MasinaZaMehanizamOtvaranja("mh100",2000));
    f.getProizvodnju().dodajMasinu(new MasinaZaSklapanje("ass100",3000));
    System.out.println("Masine dodane!");
    
    f.getRacunovodstvo().start();//POKRENI RACUNOVODSTVO
    
    int izbor;
    while(!f.zatvori){
      izbor = f.prikaziMeni();
      switch(izbor){
        case 0:
          f.getRacunovodstvo().zatvori();
          f.getProizvodnju().zatvori();
          f.zatvoriFabriku();
          break;
        case 1:
          try{
          File aktivan = new File(Proizvodnja.putanja + "aktivan").listFiles()[0];
          String ime = aktivan.getName();
          int pozicija = ime.lastIndexOf('.');
          System.out.println("Narudzba u obradi: " + ime.substring(0,pozicija)); 
        }catch(ArrayIndexOutOfBoundsException e){
          System.out.println("Trenutno nema aktivnih narudzbi!");
        }
          break;
        case 2:
          File[] nalozi = new File(Proizvodnja.putanja + "nalozi").listFiles();
          if (nalozi.length > 0){
            System.out.println("Zavrseni nalozi: ");
            for(File fa: nalozi){
              String ime = fa.getName();
              int pozicija = ime.lastIndexOf('.');
              System.out.println(ime.substring(0,pozicija));
            }
          }else{
            System.out.println("Trenutno nema gotovih proizvoda!");
          }
        break;
        case 3:
          File[] redCekanja = new File(Proizvodnja.putanja + "cekanje").listFiles();
          if (redCekanja.length > 0){
            System.out.println("Narudzbe u redu: ");
            for(File fa: redCekanja){
              String ime = fa.getName();
              int pozicija = ime.lastIndexOf('.');
              System.out.println(ime.substring(0,pozicija));
            }
          }else{
            System.out.println("Trenutno nema naloga u redu!");
          }
        break;
      }
    }
    System.out.println("Fabrika zatvorena!");
  }
}