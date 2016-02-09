package fabrika.proizvodnja.masine;

import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.File;
import java.io.PrintWriter;

import fabrika.roba.poluproizvodi.Mehanizam;
import fabrika.roba.Poluproizvod;
import fabrika.roba.Materijal;

public class MasinaZaMehanizamOtvaranja extends Masina{
  
  public MasinaZaMehanizamOtvaranja(String ozn, int br){
    super(ozn, br);
  }
  
  @Override
  public void run(){
    try{
      String s = File.separator;
      ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(putanja + "mehanizam.s")));
      Mehanizam m = new Mehanizam();
      
      PrintWriter pw = new PrintWriter(putanja + this.getOznaka() + File.separator + m.getSerijskiBroj() + ".txt");
      pw.write("Mehanizam");
      
      oos.writeObject(m);
      oos.flush();
      
      Thread.sleep(brzina);
      oos.close();
      System.out.println("Mehanizam napravljen. ");
    }catch(IOException e){
      //e.printStackTrace();
      System.out.println("Masina za mehanizam ne moze napraviti.");
    }catch(InterruptedException e){
      System.out.println("Masina prekinuta.");
    }
  }
}