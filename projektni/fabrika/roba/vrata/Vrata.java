package fabrika.roba.vrata;

import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

import fabrika.roba.Proizvod;
import fabrika.roba.Poluproizvod;
import fabrika.roba.Materijal;


public class Vrata extends Proizvod implements Serializable{
  private boolean staklo;
  private boolean resetke;
  
  public Vrata(double s, double v, Materijal vM, Poluproizvod k[], boolean imaS, boolean imaR){
    super(s,v,vM,k);
    staklo = imaS;
    resetke = imaR;
  }
  
  public boolean imaResetke(){
    return this.resetke;
  }
  
  public boolean imaStaklo(){
    return this.staklo;
  }
  /*
  private void writeObject(ObjectOutputStream oos) throws IOException{
    oos.defaultWriteObject();
    for(int i = 0; i < komponente.length; ++i)
      oos.writeObject(komponente[i]);
  }
  
  private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException{
    ois.defaultReadObject();
    for(int i = 0; i < komponente.length; ++i)
      komponente[i] = (Poluproizvod)ois.readObject();
  }*/
}