package filijala;

import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import fabrika.roba.Proizvod;

public class Skladiste extends Thread{
  Socket s;
  boolean zatvoriRadnju = false;
  
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
      int broj = dis.readInt();//CITA BROJ PROIZVODA
      ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
      for(int i = 0; i < broj; ++i){
        Proizvod p = (Proizvod)ois.readObject();
        System.out.println(p.getSerijskiBroj());
        System.out.println(p.getKomponente()[0].getSerijskiBroj());
      }
    }catch(ClassNotFoundException e){
      System.out.println("PAZNJA!!!!!!Ovo se ne moze pojaviti");
    }catch(IOException e){
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