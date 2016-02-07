package filijala;

import java.io.Serializable;

public class Narudzba implements Serializable{
  private String brojNarudzbe;
  private String sadrzaj = "";
  private static int redniBr = 100;
  
  public Narudzba(String st){
    brojNarudzbe = "nar" + redniBr++;
    
    String niz[] = st.split(" ");
    //FORMAT NARUDZBE
    /*
     * vrata#visina#sirina#materijal#ima_li_staklo#ima_li_resetke#jesu_li_sigurnosna#komada
     * prozor#visina#sirina#materijal#ima_li_roletne#ima_li_resetke#da_li_je_krovni#komada
    */
    if ("vrata".equals(niz[0]))
      sadrzaj += "vrata#" + niz[1] + "#" + niz[2] + "#" + niz[3].toLowerCase() + "#" + niz[4] + "#" + niz[5] + "#" + niz[6] + "#" + niz[7];
    else if ("prozor".equals(niz[0]))
      sadrzaj += "prozor#" + niz[1] + "#" + niz[2] + "#" + niz[3].toLowerCase() + "#" + niz[4] + "#" + niz[5] + "#" + niz[6] + "#" + niz[7];
  }
  
  public String getBrojNarudzbe(){
    return this.brojNarudzbe;
  }
  
  public void dodajLiniju(String s){
    if (sadrzaj == "")
      sadrzaj = s;
    else 
      sadrzaj = sadrzaj + System.lineSeparator() + s;
  }
  
  @Override
  public String toString(){
    return sadrzaj;
  }
}