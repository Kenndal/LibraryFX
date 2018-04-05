package logic;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import exceptions.AddingException;
import exceptions.StatusException;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.Objects;

public class Book implements Serializable {
    // podstatwowe zmienne
    private String title;
    private String genre;
    private String author;
    private String indexBook;  // nie dostaje na początku, dopiero przy daniu na pólkę
    private boolean status = false;

// zmienne dodatkowe

    // konstruktor książki
    public Book(String title, String gatunek, String author, boolean status) throws AddingException {
        if(!Objects.equals(title, "") && !Objects.equals(author, "") && !Objects.equals(gatunek, "") ) {
            this.title = title;
            this.genre = gatunek;
            this.author = author;
            this.status = status;
        }
        else
            throw new AddingException("Wypełnij wszystkie pola!");
    }

    // gettery do zmiennych podstawowych
    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public String getAuthor() {
        return author;
    }

    public Boolean isStatus() {
        return status;
    }

    public StringProperty getTitleProperty() { return new SimpleStringProperty(title); }

    public StringProperty getGenreProperty() {
        return new SimpleStringProperty(genre);
    }

    public StringProperty getAuthorProperty() {
       return new SimpleStringProperty(author);
    }

    public String getIndexBook() {
        return indexBook;
    }

    public StringProperty getIndexBookProperty() {
        return new SimpleStringProperty(indexBook);
    }

    // settery

    public void setTitle(String title) throws AddingException {
        if(!Objects.equals(title,"")) {
            this.title = title;
        }
        else
            throw new AddingException("Wypełnij pole Tytuł!");
    }

    public void setGenre(String genre) throws AddingException {
        if(!Objects.equals(genre,"")) {
            this.genre = genre;
        }
        else
            throw new AddingException("Wypełnij pole Gatunek!");

    }

    public void setAuthor(String author) throws AddingException {
        if(!Objects.equals(author,"")) {
            this.author = author;
        }
        else
            throw new AddingException("Wypełnij pole Autor!");
    }


    // ewentualne metody

    public void isStatusCheck() throws StatusException {
        if(!status){
            throw new StatusException("Książka jest już wypożyczona!");
        }
    }



    protected void addIndexBook(String indexBook){
        this.indexBook= indexBook;
    }

    public void rentBook(){
        if(status) {
            status =false;
        }
    }

    public void returnBook(){
        status = true;
    }

    public String isStatusString(){
        if(!status){
            return "Wypożyczona";
        }
        else
            return "Dostępna";
    }

    public StringProperty getStatusStringProperty(){
        if(!status){
            return new SimpleStringProperty("Wypożyczona");
        }
        else
            return new SimpleStringProperty("Dostępna");
    }

}