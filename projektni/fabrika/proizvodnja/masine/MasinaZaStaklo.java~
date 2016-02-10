package fabrika.proizvodnja.masine;

import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.File;
import java.io.PrintWriter;

import fabrika.roba.poluproizvodi.Staklo;
import fabrika.roba.Poluproizvod;
import fabrika.roba.Materijal;

public class MasinaZaStaklo extends Masina{
  
  public MasinaZaStaklo(String ozn, int br){
    super(ozn, br);
  }
  
  @Override
  public void run(){
    try{
      ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(putanja + "staklo.s")));
      Staklo s = new Staklo();
      
      PrintWriter pw = new PrintWriter(putanja + this.getOznaka() + File.separator + s.getSerijskiBroj() + ".txt");
      pw.write("Staklo ");
      
      oos.writeObject(s);
      oos.flush();
      
      Thread.sleep(brzina);
      oos.close();
      //System.out.println("Staklo napravljeno. ");
    }catch(IOException e){
      //e.printStackTrace();
      System.out.println("Masina za staklo ne moze napraviti.");
    }catch(InterruptedException e){
      System.out.println("Masina prekinuta.");
    }
  }
}