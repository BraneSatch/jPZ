package fabrika.proizvodnja.masine;

import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.File;
import java.io.PrintWriter;

import fabrika.roba.poluproizvodi.Konstrukcija;
import fabrika.roba.Poluproizvod;
import fabrika.roba.Materijal;

public class MasinaZaKonstrukciju extends Masina{
  
  public MasinaZaKonstrukciju(String ozn, int br){
    super(ozn, br);
  }
  
  @Override
  public void run(){
    try{
      ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(putanja + "konstrukcija.s")));
      Konstrukcija k;
      if (oznaka.contains("DRVO"))
       k = new Konstrukcija(Materijal.DRVO);
      else
       k = new Konstrukcija(Materijal.PVC);
      PrintWriter pw = new PrintWriter(putanja + this.getOznaka() + File.separator + k.getSerijskiBroj() + ".txt");
      pw.write("Konstrukcija " + k.getMaterijal());
      pw.close();
      oos.writeObject(k);
      oos.flush();
      Thread.sleep(brzina);
      oos.close();
      System.out.println("Konstrukcija napravljena. ");
    }catch(IOException e){
      //e.printStackTrace();
      System.out.println("Masina za konstrukciju ne moze napraviti.");
    }catch(InterruptedException e){
      System.out.println("Masina prekinuta.");
    }
  }
  
}