package logika;


import wyjatki.DodawanieException;
import wyjatki.StanException;
import wyjatki.SzukanieException;
import wyjatki.UsuwanieException;

import java.util.*;
import java.util.logging.Logger;
import java.io.*;
import java.awt.Frame;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;



public class Biblioteka implements Serializable {

    public Biblioteka() {}

    // zminene podstawowe
    // katalog
    private Katalog katalog = new Katalog();

    // lista czytelnikow
    private ArrayList<Czytelnik> czytelnicy = new ArrayList<Czytelnik>();

    // dodatkowe
    private Obserwator obserwator; // tworze obserwatora
    private Random losowa = new Random();
    private static Logger log = Logger.getLogger(Biblioteka.class.getName());
    // gettery (musimy nad nimi pomysleć)(sami musimy odpowiednie zrobic)

    public Katalog getKatalog() {
        return katalog;
    }

    public ArrayList<Czytelnik> getCzytelnicy() {
        return czytelnicy;
    }

    // metody wypożyczenia, zwroty, dodawanie czytleników

    public void dodaj_czytelnika(String imie, String nazwisko, String data_urodzenia, String numer_telefonu) throws DodawanieException {


            Czytelnik czytelnik = new Czytelnik(imie, nazwisko, data_urodzenia, numer_telefonu);
            czytelnik.setPosiadanieKsiazek(false);
            czytelnik.doddaj_indeks_czytelnika(daj_indeks_karty_czytelnika(czytelnik.getImie(), czytelnik.getNazwisko()));
            czytelnicy.add(czytelnik);
            //czytelnik.zapiszDoPlikuDlaCzytelnika("katalog_czytelnikow.txt");

    }

    public void usun_czytelnika(String indeks_czytelnika) {
        czytelnicy.remove(szukaj_czytelnika(indeks_czytelnika));
    }

    private String daj_indeks_karty_czytelnika(String imie, String nazwisko) {
        String pierwsza_litera_imie = String.valueOf(imie.charAt(0));
        String pierwsza_litera_nazwisko = String.valueOf(nazwisko.charAt(0));
        int numer_karty = losowa.nextInt(10001);
        String indeks;
        indeks = pierwsza_litera_imie + pierwsza_litera_nazwisko + Integer.toString(numer_karty);
        return indeks;
    }

    public Czytelnik szukaj_czytelnika(String indeks_czytelnika) {
        for (int i = 0; i < getCzytelnicy().size(); i++) {
            if (getCzytelnicy().get(i).getIndeks_czytelnika().equals(indeks_czytelnika))
                return getCzytelnicy().get(i);
        }
        return null;
    }

    public Czytelnik szukaj_czytelnika_inaczej(String imie, String nazwisko, String data_urodzenia) {
        for (int i = 0; i < getCzytelnicy().size(); i++) {
            if (getCzytelnicy().get(i).getImie().equals(imie) && getCzytelnicy().get(i).getNazwisko().equals(nazwisko) && getCzytelnicy().get(i).getData_urodzenia().equals(data_urodzenia))
                return getCzytelnicy().get(i);
        }
        return null;
    }

    public void wypisz_czytelnikow() {
        int i = 0;
        while (i < getCzytelnicy().size()) {
            System.out.println(getCzytelnicy().get(i).getImie() + " " + getCzytelnicy().get(i).getNazwisko() + " " + getCzytelnicy().get(i).getData_urodzenia() + " " + getCzytelnicy().get(i).getNumer_telefonu() + " " + getCzytelnicy().get(i).getWypozyczone_ksiazki());
            System.out.print(getCzytelnicy().get(i));
            i++;
        }
    }

    public void wypisz_stan_katalogu_czytelnika(String indeks_czytelnika) {
        szukaj_czytelnika(indeks_czytelnika).wypisz_stan();
    }

    public void dodajKsiazkeDoKatalogu(String nazwa, String gatunek, String autor) throws DodawanieException {
        Ksiazka ksiazka = new Ksiazka(nazwa, gatunek, autor, true);
        ksiazka.doddaj_indeks_ksiazki(daj_indeks_karty_ksiazki(ksiazka.getNazwa(), ksiazka.getGatunek(), ksiazka.getAutor()));
        katalog.dodajKsiazke(ksiazka);
        //i to było ale stare
        //zapiszDoPlikuDlaKsiazekStare("biblioteczka1.txt", ksiazka);
    }

    private String daj_indeks_karty_ksiazki(String nazwa, String gatunek, String autor) {
        String trzy_litery_Nazwy = String.valueOf(nazwa.charAt(0)) + String.valueOf(nazwa.charAt(1)) + String.valueOf(nazwa.charAt(2));
        String trzy_litery_Gatunku = String.valueOf(gatunek.charAt(0)) + String.valueOf(gatunek.charAt(1)) + String.valueOf(gatunek.charAt(2));
        String trzy_litery_Autora = String.valueOf(autor.charAt(0)) + String.valueOf(autor.charAt(1)) + String.valueOf(autor.charAt(2));
        int numer = losowa.nextInt(10001);
        String indeks;
        indeks = trzy_litery_Nazwy + trzy_litery_Gatunku + trzy_litery_Autora + Integer.toString(numer);
        return indeks;
    }

    public void skasujKsiazke(String nazwaKsiazki) throws UsuwanieException {
        // kiedy mozna skasowac ksiazke? Przeciez nie moze jej wtedy miec czytelnik u siebie!!!!
        if (szukajKsiazki(nazwaKsiazki).isStan())
            katalog.skasujKsiazke(nazwaKsiazki);
        else
            throw new UsuwanieException("Nie można usunąć wypożyczonej książki!");
    }

    public void wypozyczKsiazke(String nazwaKsiazki, String indeks_czytelnika) throws SzukanieException, DodawanieException{
        int k = 0 ;
        if(!Objects.equals(indeks_czytelnika, "")) {
        for (int i = 0; i < getCzytelnicy().size(); i++) {
            if (getCzytelnicy().get(i).getIndeks_czytelnika().equals(indeks_czytelnika))
                getCzytelnicy().get(i).wypozyczenie(nazwaKsiazki, szukajKsiazki(nazwaKsiazki));
            else
                k++;
            }
            if(k==getCzytelnicy().size())
                throw new SzukanieException("Nie ma Czytelnika o podanym indeksie!");
        katalog.getKsiazki().get(nazwaKsiazki).wypozyczenie();
        }
        else
            throw new DodawanieException("Wypełnij pole indeks czytelnika!");

        obserwator.informuj(); // odświerzenie jtable
    }

    public void zwrocKsiazke(String nazwaKsiazki, String indeks_czytelnika) {
        if (!szukajKsiazki(nazwaKsiazki).isStan()) {
            katalog.getKsiazki().get(nazwaKsiazki).zwrot();
            for (int i = 0; i < getCzytelnicy().size(); i++) {
                if (getCzytelnicy().get(i).getIndeks_czytelnika().equals(indeks_czytelnika))
                    getCzytelnicy().get(i).zwrot(nazwaKsiazki, szukajKsiazki(nazwaKsiazki));
            }
            obserwator.informuj(); // odświerzenie jtable
        }
    }

    public void zwrotWszystkichKsiazek (String indeks_czytelnika){
        for(Map.Entry<String,Ksiazka> entry : szukaj_czytelnika(indeks_czytelnika).getWypozyczone_ksiazki().entrySet()){
            katalog.getKsiazki().get(entry.getKey()).zwrot();
        }
    }
    public void wypisz_stan_katalogu() {
        katalog.wypiszKsiazki();
    }

    public Ksiazka szukajKsiazki(String nazwaKsiazki_lub_indeks) {
        return katalog.szukaj(nazwaKsiazki_lub_indeks);
    }


    // zapis katalogu do pliku
    public void zapiszDoPlikuKatalog(String nazwaPliku)throws IOException{
        ObjectOutputStream dozapisu = null;
        try {
            dozapisu = new ObjectOutputStream(new FileOutputStream(nazwaPliku));
            dozapisu.writeObject(katalog);
        }catch (IOException ex) {
            System.out.println("Blad w zapisie do pliku '" + nazwaPliku + "'");
        }
        finally {
            if(dozapisu!=null)
                dozapisu.close();
        }
    }

    // odczyt katalogu z pliku
    public void odczytKataloguZPliku(String nazwaPliku)throws IOException,ClassNotFoundException{
        ObjectInputStream doodczytu=null;
        try{
            doodczytu=new ObjectInputStream(new FileInputStream(nazwaPliku));
            katalog=(Katalog) doodczytu.readObject();
        } catch (EOFException ex) {
            System.out.println("Koniec pliku");
        }
        finally{
            if(doodczytu!=null)
                doodczytu.close();
        }
    }
    // zapis Czytelnikow do pliku
    public void zapiszDoPlikuCzytelnicy(String nazwaPliku)throws IOException{
        ObjectOutputStream dozapisu = null;
        try {
            dozapisu = new ObjectOutputStream(new FileOutputStream(nazwaPliku));
            dozapisu.writeObject(czytelnicy);
        }catch (IOException ex) {
            System.out.println("Blad w zapisie do pliku '" + nazwaPliku + "'");
        }
        finally {
            if(dozapisu!=null)
                dozapisu.close();
        }
    }
    // odczyt czytelnikow z pliku
    public void odczytCzytelnikowZPliku(String nazwaPliku)throws IOException,ClassNotFoundException{
        ObjectInputStream doodczytu=null;
        try{
            doodczytu=new ObjectInputStream(new FileInputStream(nazwaPliku));
            czytelnicy = (ArrayList<Czytelnik>) doodczytu.readObject();
        } catch (EOFException ex) {
            System.out.println("Koniec pliku");
        }
        finally{
            if(doodczytu!=null)
                doodczytu.close();
        }
    }


    // logika Obserwator subskrpcja
    public void subskrybuj(Obserwator O) {
        this.obserwator = O;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Biblioteka biblioteka = new Biblioteka();
        biblioteka.zapiszDoPlikuKatalog("biblioteczka.bin");
        biblioteka.zapiszDoPlikuCzytelnicy("czytelnicy.bin");
        biblioteka.odczytKataloguZPliku("biblioteczka.bin");
        biblioteka.odczytCzytelnikowZPliku("czytelnicy.bin");


    }
}