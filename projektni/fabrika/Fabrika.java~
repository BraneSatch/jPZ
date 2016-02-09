package fabrika;

import java.util.Scanner;
import java.net.Socket;

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
      }
    }
    System.out.println("Fabrika zatvorena!");
  }
}