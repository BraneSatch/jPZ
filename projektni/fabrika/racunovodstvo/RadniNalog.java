package fabrika.racunovodstvo;

import java.net.Socket;

public class RadniNalog{
  private String brojNaloga;
  private String sadrzaj = "";
  private Socket s;
  
  RadniNalog(String br, Socket s){
    brojNaloga = br;
    this.s = s;
  }
  
  public String getBrNalog(){
    return this.brojNaloga;
  }
  
  public Socket getSocket(){
    return this.s;
  }
  
  public void dodajLiniju(String s){
    if (sadrzaj == "")
      sadrzaj = s;
    else 
      sadrzaj = sadrzaj + s;
  }
  
  @Override
  public String toString(){
    return sadrzaj;
  }
}