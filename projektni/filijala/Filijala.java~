package filijala;

import java.net.Socket;
import java.net.UnknownHostException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;
import java.io.PrintWriter;
import java.io.DataOutputStream;
import java.io.OutputStreamWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.DataInputStream;
import java.io.InputStream;

public class Filijala{
  private String ime;
  private static String putanja = "filijala" + File.separator;
  
  public Filijala(String ime){
    this.ime = ime;
  }
  
  public void setIme(String ime){
    this.ime = ime;
  }
  
  public void posaljiZahtjev(Socket s, Narudzba n){
    try{
      ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
      oos.writeObject(n);
      //oos.close();
    }catch(IOException e){
      System.out.println("Slanje trenutno onemoguceno. Kontaktirajte server.");
    }
  }
  
  public void posaljiOpciju(Socket s, int op){
    try{
      DataOutputStream os = new DataOutputStream(s.getOutputStream());
      os.writeInt(op);
      //os.close();
    }catch(IOException e){
      System.out.println("Slanje trenutno onemoguceno. Kontaktirajte server.");
    }
  }
  
  public void posaljiIme(Socket s){
    try{
      DataOutputStream os = new DataOutputStream(s.getOutputStream());
      os.writeUTF(this.ime);
      //os.close();
    }catch(IOException e){
      System.out.println("Slanje imena trenutno onemoguceno. Kontaktirajte server.");
    }
  }
  
  private static void prikaziMeni(){
    System.out.println("IZABERITE OPCIJU:");
    System.out.println("1. SLANJE NARUDZBE");
    System.out.println("2. PREGLED SPISKA PROIZVODA");
    System.out.println("3. PREGLED NARUDZBI ZA CEKANJE");
    System.out.println("4. PREGLED ZAVRSENIH PROIZVODA");
    System.out.println("0. IZLAZ");
  }
  
  static int vratiIzbor(int max){
    Scanner s = new Scanner(System.in);
    int i;
    i = s.nextInt();
    while(i > max){
      System.out.println("POGRESAN UNOS");
      i = s.nextInt();
    }
    return i;
  }
  
  public static void main(String args[]){
    
    try{
      int izbor = 0;
      
      Socket s = new Socket("127.0.0.1",27017);
      Scanner in = new Scanner(System.in);
      System.out.println("Ime filijale: ");
      Filijala f = new Filijala(in.nextLine());
      f.posaljiIme(s);//PRVA PORUKA SERVERU
      Skladiste skladiste = new Skladiste(s);
      System.out.println("KOMUNIKACIJA SA SERVEROM USPOSTAVLJENA.");
      System.out.println("DOBRO DOSLI U FABRIKU PVC I DRVENE STOLARIJE");
      
      do{
        prikaziMeni();
        System.out.print("> ");
        try{
          izbor = in.nextInt();   
        }catch(InputMismatchException e){
          System.out.println("POGRESAN UNOS");
        }
        
        switch(izbor){
          //1. SLANJE NARUDZBE
          case 1:
            System.out.println("1. NARUCI VRATA");
            System.out.println("2. NARUCI PROZOR");
            System.out.println("0. POCETNI MENI");
            System.out.print("> ");
            f.posaljiOpciju(s, 1);
            int izborNarudzbe = vratiIzbor(2);
            Narudzba n;
            switch(izborNarudzbe){
              //ZAHTJEV ZA VRATA
              case 1:
                System.out.println("UNESITE PODATKE O VRATIMA U SLEDECEM FORMATU:");
                System.out.println("VISINA SIRINA VRSTA_MATERIJALA IMA_LI_STAKLO? IMA_LI_RESETKE? JESU_LI_SIGURNOSNA? KOMADA");
                System.out.print("> ");
                in.nextLine();
                String izborVrata = "vrata " + in.nextLine();
                n = new Narudzba(izborVrata);
                //System.out.println(n);
                f.posaljiZahtjev(s, n);
                System.out.println("=======VAS ZAHTJEV JE POSLAT=======");
                break;
              //ZAHTJEV ZA PROZOR
              case 2:
                System.out.println("UNESITE PODATKE O PROZORU U SLEDECEM FORMATU:");
                System.out.println("VISINA SIRINA VRSTA_MATERIJALA IMA_LI_ROLETNE? IMA_LI_RESETKE? KROVNI_PROZOR? KOMADA");
                System.out.print("> ");
                in.nextLine();
                String izborProzora = "prozor " + in.nextLine();
                n = new Narudzba(izborProzora);
                //System.out.println(n);
                f.posaljiZahtjev(s, n);
                System.out.println("=======VAS ZAHTJEV JE POSLAT=======");
                break;
            }
            
            break;
          case 2:

            break;
          case 3:
            f.posaljiOpciju(s, 3);
            break;
          case 4:
            f.posaljiOpciju(s, 4);
            break;
          case 0:
            f.posaljiOpciju(s, 0);
            break;
          default:
            System.out.println("POGRESAN UNOS");
            break;
            
        }
        
        
        
      }while(izbor != 0);
      f.posaljiOpciju(s, 0);
      //s.close();
    }catch(UnknownHostException e){
      System.out.println("Server trenutno nedostupan. Pokusajte kasnije.");
    }catch(IOException e){
      System.out.println("Server trenutno nedostupan. Pokusajte kasnije. IO");
    }
  }
}