package fabrika.racunovodstvo;

import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.DataInputStream;
import java.util.HashMap;
import java.io.InputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Date;
import java.io.DataOutputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;
import java.io.OutputStream;
import java.io.ObjectOutputStream;

import filijala.Narudzba;
import fabrika.proizvodnja.Proizvodnja;
import fabrika.roba.Proizvod;

public class RacunovodstvoRadnik extends Thread{
  private Socket s = null;
  private Proizvodnja p = null;
  private InputStream in = null;
  private String imeFilijale = null;
  private boolean zatvoriAplikaciju = false;
  
  final String sep = File.separator;
  final String putanja = "fabrika" + File.separator;
  
  public RacunovodstvoRadnik(Socket s, Proizvodnja p){
    try{
      this.s = s;
      this.p = p;
      this.in = s.getInputStream();
      this.start();
      File akt = new File(putanja + "aktivni");
      File cekanje = new File(putanja + "cekanje");
      if (!(akt.exists()))
        akt.mkdir();
      if (!(cekanje.exists()))
        cekanje.mkdir();
    }catch(IOException e){
      zatvoriAplikaciju = true;
      System.out.println("Zahtjev se ne moze obraditi.");
    }
  }
  
  private String ucitajIme(){
    String rez = "!!greska!!";
    try{
      DataInputStream br = new DataInputStream(in);
      rez = br.readUTF();
    }catch(IOException e){
      zatvoriAplikaciju = true;
      System.out.println("Ucitavanje imena neuspijelo! Provjerite konekciju sa filijalom " + imeFilijale + "!");
    }
    return rez;
  }
  
  private int ucitajOpciju(){
    int rez = -1;
    try{
      DataInputStream is = new DataInputStream(in);
      rez = is.readInt();
    }catch(IOException e){
      zatvoriAplikaciju = true;
      System.out.println("Provjerite konekciju sa filijalom " + imeFilijale + "!");
    }
    return rez;
  }
  
  private void sacuvajNalog(Narudzba n){
    try{
      File folder = new File(putanja + "nalozi");
      if (!folder.exists())
        folder.mkdir();
      
      PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(putanja + "nalozi" + sep + n.getBrojNarudzbe() + ".txt"))));
      String niz[] = n.toString().split("#");
      if ("vrata".equals(niz[0])){
        pw.println("Proizvod - vrata");
        
        if ("drvo".equals(niz[3]))
          pw.println("Materijal - drvo");
        else
          pw.println("Materijal - pvc");
        
        if ("da".equals(niz[4]))
          pw.println("Imaju staklo");
        
        if ("da".equals(niz[5]))
          pw.println("Imaju resetke");
        
        if ("da".equals(niz[6]))
          pw.println("Vrata imaju sigurnosni mehanizam");
        
        pw.println("Komada " + niz[7]);
        
      }
      if ("prozor".equals(niz[0])){
        pw.println("Proizvod: Prozor");
        
        if ("drvo".equals(niz[3]))
          pw.println("Materijal - drvo");
        else
          pw.println("Materijal - pvc");
        
        if ("da".equals(niz[4]))
          pw.println("Imaju roletne");
        
        if ("da".equals(niz[5]))
          pw.println("Imaju resetke");
        
        if ("da".equals(niz[6]))
          pw.println("Prozori su krovni");
        
        pw.println("Komada " + niz[7]);
      }
      
      pw.close();
    }catch(FileNotFoundException e){
      System.out.println("Nalog broj " + n.getBrojNarudzbe() + " se ne moze sacuvati!");
    }
  }
  
  
  private void prihvatiNalog(){
    try{
      ObjectInputStream ois = new ObjectInputStream(in);
      Narudzba n = (Narudzba)ois.readObject();
      this.sacuvajNalog(n);
      RadniNalog r = this.napraviNalog(s, n);
      //System.out.println(r);
      p.dodajNalog(r);
    }catch(IOException e){
      System.out.println("Ucitavanje naloga neuspijelo! Provjerite konekciju sa filijalom " + imeFilijale + "!");
    }catch(ClassNotFoundException e){
      System.out.println("Nepostojeca klasa (ne moze se pojaviti).");
    }
  }
  
  public RadniNalog napraviNalog(Socket s, Narudzba n){
    String sadrzaj = n.toString();
    RadniNalog r = new RadniNalog(n.getBrojNarudzbe(), s);
    String linije[] = n.toString().split(System.lineSeparator());
    
    for(String i: linije){
      String niz[] = i.split("#");
      
      //DODAVANJE VRATA U RADNI NALOG
      if ("vrata".equals(niz[0])){
        int komada = Integer.parseInt(niz[7]);
        for(int brojac = 0; brojac < komada; brojac++){
          r.dodajLiniju("vrata#" + niz[1] + "#" + niz[2] + "#" + niz[3] + "#" + niz[4] + "#" + niz[5] + "#" + niz[6]);
          r.dodajLiniju(System.lineSeparator());
        }
      }
      
      //DODAVANJE PROZORA U RADNI NALOG
      if ("prozor".equals(niz[0])){
        int komada = Integer.parseInt(niz[7]);
        for(int brojac = 0; brojac < komada; brojac++){     
          r.dodajLiniju("prozor#" + niz[1] + "#" + niz[2] + "#" + niz[3] + "#" + niz[4] + "#" + niz[5] + "#" + niz[6]);
          r.dodajLiniju(System.lineSeparator());
        }
      }
      
    }
    return r;
  }
  
  private void zatvoriVeze(){
    try{
      s.close();
    }catch(IOException e){
      System.out.println("Bezbjedno zatvaranje veze neuspijelo!");
    }
  }
  
  @Override
  public void run(){
    imeFilijale = this.ucitajIme();//PRVI PRIJEM SERVERA
    System.out.println("Filijala " + imeFilijale + " konektovana!");
    System.out.print("> ");
    Upozorenje uprozorivac = new Upozorenje(s);
    int opcija;
    do{
      opcija = this.ucitajOpciju();
      switch(opcija){
        case 1:
          uprozorivac.refresh();
          prihvatiNalog();
          break;
        case -1: case 0:
          try{
          DataOutputStream dis = new DataOutputStream(s.getOutputStream());
          dis.writeInt(-1);
          dis.flush();
          zatvoriAplikaciju = true;
          uprozorivac.zatvori();
          System.out.println("Filijala " + imeFilijale + " zavrsila sa radom.");
          System.out.print("> ");
        }catch(IOException e){
          System.out.println("Greska sa racunovodstvom");
          zatvoriAplikaciju = true;
        }
        break;
      }
      
    }while(!zatvoriAplikaciju);
    zatvoriVeze();
  }
}