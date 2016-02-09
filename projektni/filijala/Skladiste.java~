package filijala;

import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;

import fabrika.roba.Proizvod;

public class Skladiste extends Thread{
  Socket s;
  boolean zatvoriRadnju = false;
  
  final static String putanja = "filijala" + File.separator;
  
  public Skladiste(Socket s){
    this.s = s;
    this.start();
  }
  
  private void doNothing(){
    try{
      Thread.sleep(50);
      }catch(InterruptedException e){
        e.printStackTrace();
      }
  }
  
  private int prihvatiIzbor(){
    int ret = -1;
    try{
      DataInputStream dis = new DataInputStream(s.getInputStream());
      ret = dis.readInt();
    }catch(IOException e){
      System.out.println("Veza sa serverom diskonektovana! Proizvodi nisu kompletirani!");
    }
    return ret;
  }
  
  public void zatvori(){
    zatvoriRadnju = true;
    this.interrupt();
  }
  
  private void prihvatiProizvode(){
    try{

      DataInputStream dis = new DataInputStream(s.getInputStream());
      BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
      InputStream is = s.getInputStream();
      
     
      String brojNarudzbe = dis.readUTF();//PRIHVATA BROJ NARUDZBE
      int broj = dis.readInt();//CITA BROJ PROIZVODA
      File folder = new File(putanja + brojNarudzbe);
      folder.mkdir();
      
      ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
      for(int i = 0; i < broj; ++i){
        Proizvod p = (Proizvod)ois.readObject();
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(putanja + brojNarudzbe + File.separator + p.getSerijskiBroj()));
        oos.writeObject(p);
        System.out.println(p.getSerijskiBroj());
      }
      
      int len;
      int ocitano = 0;
      

      FileOutputStream fos = new FileOutputStream(putanja + brojNarudzbe + File.separator + "lista" + brojNarudzbe + ".zip");
      String ocekivano = br.readLine();//PRIHVATA DUZINU FAJLA
      //System.out.println(brojNarudzbe);
      int ukupno = Integer.parseInt(ocekivano);
      byte[] buffer = new byte[1024 * 1024];
      while((len = is.read(buffer)) > 0){
        fos.write(buffer, 0, len);
        ocitano += len;
        if (ocitano == ukupno)
          break;
      }
      fos.close();
    }catch(ClassNotFoundException e){
      System.out.println("PAZNJA!!!!!!Ovo se ne moze pojaviti");
    }catch(IOException e){
      e.printStackTrace();
      System.out.println("Veza sa serverom diskonektovana! Proizvodi nisu kompletirani!");
    }
  }
  
  @Override
  public void run(){
    while(!zatvoriRadnju){
      doNothing();
      int izbor = prihvatiIzbor();
      switch(izbor){
        case 1:
          prihvatiProizvode();
          break;
        case -1:
          zatvoriRadnju = true;
          break;
      }   
    }
  }
}