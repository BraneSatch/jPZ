package fabrika.racunovodstvo;

import java.net.Socket;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import fabrika.roba.Proizvod;

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
      dos.writeInt(1);//SALJE JEDINSTVENI KOD (1 ZA SLANJE PROIZVODA)
      dos.writeInt(listaProizvoda.size());//SALJE BROJ PROIZVODA
      ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
      for(int i = 0; i < listaProizvoda.size(); ++i)
        oos.writeObject(listaProizvoda.get(i));
    }catch(IOException e){
      System.out.println("Dostavljac ne moze predati paket.");
    }
  }
}