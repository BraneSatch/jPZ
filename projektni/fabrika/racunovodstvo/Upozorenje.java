package fabrika.racunovodstvo;

import java.util.Date;
import java.net.Socket;
import java.io.DataOutputStream;
import java.io.IOException;

public class Upozorenje extends Thread{
  private Socket s;
  private boolean upozori = false;
  private long trenutno;
  private boolean zatvoriApp = false;
  
  Upozorenje(Socket s){
    this.s = s;
    this.start();
    this.trenutno = new Date().getTime();
  }
  
  public void zatvori(){
    zatvoriApp = true;
  }
  
  public void refresh(){
    trenutno = new Date().getTime();
  }
  
  @Override
  public void run(){
    while(!zatvoriApp){
      if (upozori == true){
        try{
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        dos.writeInt(5);
        upozori = false;
        }catch(IOException e){
          System.out.println("Pokusalj slanja upozorenja neuspio!");
        }
      }
      if ((new Date().getTime() - trenutno) > 120000){
        upozori = true;
        trenutno = new Date().getTime();
      }
    }
  }
}