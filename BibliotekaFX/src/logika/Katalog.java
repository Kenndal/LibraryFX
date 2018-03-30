package logika;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Katalog implements Serializable {

    private Map<String, Ksiazka> ksiazki = new HashMap<String,Ksiazka>();

    // gettery
    public Map<String, Ksiazka> getKsiazki() {
        return ksiazki;
    }


    public void dodajKsiazke(Ksiazka ksiazka){
        ksiazki.put(ksiazka.getNazwa(),ksiazka);
    }

    public void wypiszKsiazki(){
        for(Map.Entry<String,Ksiazka> entry : ksiazki.entrySet()){
            System.out.println(entry.getValue().getNazwa()+ " " + entry.getValue().getAutor()+ " " + entry.getValue().getGatunek()+ " " + entry.getValue().getIndeks_ksiazki()+ " " + entry.getValue().isStan());
            System.out.println(entry.getValue());
        }
    }

    public void skasujKsiazke(String nazwaKsiazki){
        ksiazki.remove(nazwaKsiazki);
    }

    //znajduje ksiazke po indeksie
    public Ksiazka szukajPoIndeksie(String indeksksiazki){
        Ksiazka ksiazka = null;
        for (Map.Entry<String, Ksiazka> entry: ksiazki.entrySet()) {
            if (ksiazki.containsValue(entry))
               ksiazka = entry.getValue();
        }
        return ksiazka;
    }

    protected Ksiazka szukaj(String nazwaKsiazki){
        if(ksiazki.containsKey(nazwaKsiazki))
            return ksiazki.get(nazwaKsiazki);
        /*
        if (ksiazka == ksiazki.get(ksiazka.getNazwa()))
            return ksiazki.get(ksiazka.getNazwa());
        if (ksiazka == ksiazki.get(ksiazka.getAutor()))
            return ksiazki.get(ksiazka.getAutor());
        if (ksiazka == ksiazki.get(ksiazka.getGatunek()))
            return ksiazki.get(ksiazka.getGatunek());
        return
        */
        return null;
    }
}