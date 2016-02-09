package fabrika.racunovodstvo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.net.SocketException;

import filijala.Narudzba;
import fabrika.proizvodnja.Proizvodnja;

public class Racunovodstvo extends Thread{
  private ServerSocket ss;
  private Proizvodnja p;
  private boolean zatvoriFabriku = false;
  
  public Racunovodstvo(int port){
    this.p = p;
    try{
      ss = new ServerSocket(port);
    }catch(IOException e){
      System.out.println("Nije moguce pokrenuti server. Pokusajte kasnije.");
    }
  }
  
  public void setProizvodnju(Proizvodnja p){
    this.p = p;
  }
  
  public void zatvori(){
    try{
      ss.close();
    }catch(IOException e){
    }
  }
  
  public void prihvatiZahtjeve(){
    try{
      Socket s = ss.accept();
      RacunovodstvoRadnik t = new RacunovodstvoRadnik(s, p);
    }catch(SocketException e){
      this.zatvoriFabriku = true;
    }catch(IOException e){
      System.out.println("Server trenutno nije u mogucnosti da obradi korisnicke zahtjeve.");
      
    }
  }
  
  @Override
  public void run(){
    while(!zatvoriFabriku){
      this.prihvatiZahtjeve();
    }
  }
}
