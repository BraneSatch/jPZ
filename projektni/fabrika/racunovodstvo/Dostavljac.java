package fabrika.racunovodstvo;

import java.net.Socket;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.zip.ZipOutputStream;
import java.util.zip.ZipEntry;
import java.io.FileInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

import fabrika.roba.Proizvod;
import fabrika.roba.vrata.Vrata;
import fabrika.roba.prozori.Prozor;

public class Dostavljac extends Thread{
  private Socket s;
  private String brNarudzbe;
  private ArrayList<Proizvod> listaProizvoda;
  
  public Dostavljac(Socket s, String brNarudzbe, ArrayList<Proizvod> p){
    this.s = s;
    this.listaProizvoda = p;
    this.brNarudzbe = brNarudzbe;
    this.start();
  }
  
  public String getBrNarudzbe(){
    return this.brNarudzbe;
  }
  
  public Socket getSocket(){
    return this.s;
  }
  
  @Override
  public void run(){
    try{
      DataOutputStream dos = new DataOutputStream(s.getOutputStream());
      PrintWriter p = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())));
      OutputStream os = s.getOutputStream();
      
      dos.writeInt(1);//SALJE JEDINSTVENI KOD (1 ZA SLANJE PROIZVODA)
      dos.writeUTF(brNarudzbe);//SALJE BROJ NARUDZBE
      dos.writeInt(listaProizvoda.size());//SALJE BROJ PROIZVODA
      
      File spisak = new File("spisak.txt");
      PrintWriter fos = new PrintWriter(spisak);
      
      //SALJE PROIZVODE
      ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
      for(int i = 0; i < listaProizvoda.size(); ++i){
        if (listaProizvoda.get(i) instanceof Vrata)
          fos.println("Vrata " + listaProizvoda.get(i).getSerijskiBroj());
        if (listaProizvoda.get(i) instanceof Prozor)
          fos.println("Prozor "  + listaProizvoda.get(i).getSerijskiBroj());
        oos.writeObject(listaProizvoda.get(i));
      }
      oos.flush();
      fos.close();
      
      //ZIPUJE LISTU
      ZipOutputStream zip = new ZipOutputStream(new FileOutputStream("lista " + brNarudzbe + ".zip"));
      ZipEntry lista = new ZipEntry("spisak.txt");
      FileInputStream fis = new FileInputStream("spisak.txt");
      zip.putNextEntry(lista);
      byte[] buffer = new byte[1024 * 1024];
      int len;
      while ((len = fis.read(buffer)) > 0) {
        zip.write(buffer, 0, len);
      }
      fis.close();
      zip.closeEntry();
      zip.close();
      spisak.delete();
      
      File zipovanSpisak = new File("lista " + brNarudzbe + ".zip");
      
      //SALJE DUZINE FAJLA
      p.println(zipovanSpisak.length());
      p.flush();
      InputStream is = new FileInputStream(zipovanSpisak);
      //SALJE FAJL
      while((len = is.read(buffer)) > 0){
        os.write(buffer, 0, len);
      }
      
      is.close();
      zipovanSpisak.delete();
    }catch(IOException e){
      System.out.println("Dostavljac ne moze predati paket.");
    }
  }
}