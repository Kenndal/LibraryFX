package logika;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import wyjatki.DodawanieException;
import wyjatki.StanException;

import java.io.Serializable;
import java.util.Objects;

public class Ksiazka implements Serializable {
    // podstatwowe zmienne
    private StringProperty nazwa;
    private StringProperty gatunek;
    private StringProperty autor;
    private boolean stan;
    private StringProperty indeks_ksiazki;  // nie dostaje na początku, dopiero przy daniu na pólkę

// zmienne dodatkowe

    // konstruktor książki
    public Ksiazka(String nazwa, String gatunek, String autor, boolean stan) throws DodawanieException {
        if(!Objects.equals(nazwa, "") && !Objects.equals(autor, "") && !Objects.equals(gatunek, "") ) {
            this.nazwa = new SimpleStringProperty(nazwa);
            this.gatunek = new SimpleStringProperty(gatunek);
            this.autor = new SimpleStringProperty(autor);
            this.stan = stan;
        }
        else
            throw new DodawanieException("Wypełnij wszystkie pola!");
    }

    // gettery do zmiennych podstawowych
    public String getNazwa() {
        return nazwa.get();
    }

    public String getGatunek() {
        return gatunek.get();
    }

    public String getAutor() {
        return autor.get();
    }

    public Boolean isStan() {
        return stan;
    }

    public StringProperty getNazwaProperty() {
        return nazwa;
    }

    public StringProperty getGatunekProperty() {
        return gatunek;
    }

    public StringProperty getAutorProperty() {
        return autor;
    }

    public String getIndeks_ksiazki() {
        return indeks_ksiazki.get();
    }

    public StringProperty getIndeks_ksiazkiProperty() {
        return indeks_ksiazki;
    }

    // settery

    public void setNazwa(String nazwa) throws DodawanieException {
        if(!Objects.equals(nazwa,"")) {
            this.nazwa.set(nazwa);
        }
        else
            throw new DodawanieException("Wypełnij pole Tytuł!");
    }

    public void setGatunek(String gatunek) throws DodawanieException {
        if(!Objects.equals(gatunek,"")) {
            this.gatunek.set(gatunek);
        }
        else
            throw new DodawanieException("Wypełnij pole Gatunek!");

    }

    public void setAutor(String autor) throws DodawanieException {
        if(!Objects.equals(autor,"")) {
            this.autor.set(autor);
        }
        else
            throw new DodawanieException("Wypełnij pole Autor!");
    }


    // ewentualne metody

    public void isStanCheck() throws StanException{
        if(!stan){
            throw new StanException("Książka jest już wypożyczona!");
        }
    }



    protected void doddaj_indeks_ksiazki(String indeks){
        indeks_ksiazki= new SimpleStringProperty(indeks);
    }

    public void wypozyczenie(){
        if(stan) {
           stan =false;
        }
    }

    public void zwrot(){
        stan = true;
    }

    public String isStanString(){
        if(!stan){
            return "Wypożyczona";
        }
        else
            return "Dostępna";
    }

    public StringProperty getStanStringProperty(){
        if(!stan){
            return new SimpleStringProperty("Wypożyczona");
        }
        else
            return new SimpleStringProperty("Dostępna");
    }
}