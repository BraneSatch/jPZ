import java.util.*;

import filijala.Narudzba;


public class Test{
  public static void main(String args[]){
    try{
      Nit a = new Nit();
      a.start();
      Thread.sleep(100);
      a.join();
      System.out.println("main");
    }catch(Exception e){
      e.printStackTrace();
    }
  }
}


class Nit extends Thread{
  @Override
  public void run(){
    System.out.println("brane");
  }
}