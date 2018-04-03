package logic;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import exceptions.AddingException;
import exceptions.StatusException;

import java.io.Serializable;
import java.util.Objects;

public class Book implements Serializable {
    // podstatwowe zmienne
    private StringProperty title;
    private StringProperty genre;
    private StringProperty author;
    private boolean status;
    private StringProperty indexBook;  // nie dostaje na początku, dopiero przy daniu na pólkę

// zmienne dodatkowe

    // konstruktor książki
    public Book(String title, String gatunek, String author, boolean status) throws AddingException {
        if(!Objects.equals(title, "") && !Objects.equals(author, "") && !Objects.equals(gatunek, "") ) {
            this.title = new SimpleStringProperty(title);
            this.genre = new SimpleStringProperty(gatunek);
            this.author = new SimpleStringProperty(author);
            this.status = status;
        }
        else
            throw new AddingException("Wypełnij wszystkie pola!");
    }

    // gettery do zmiennych podstawowych
    public String getTitle() {
        return title.get();
    }

    public String getGenre() {
        return genre.get();
    }

    public String getAuthor() {
        return author.get();
    }

    public Boolean isStatus() {
        return status;
    }

    public StringProperty getTitleProperty() { return title; }

    public StringProperty getGenreProperty() {
        return genre;
    }

    public StringProperty getAuthorProperty() {
        return author;
    }

    public String getIndexBook() {
        return indexBook.get();
    }

    public StringProperty getIndexBookProperty() {
        return indexBook;
    }

    // settery

    public void setTitle(String title) throws AddingException {
        if(!Objects.equals(title,"")) {
            this.title.set(title);
        }
        else
            throw new AddingException("Wypełnij pole Tytuł!");
    }

    public void setGenre(String genre) throws AddingException {
        if(!Objects.equals(genre,"")) {
            this.genre.set(genre);
        }
        else
            throw new AddingException("Wypełnij pole Gatunek!");

    }

    public void setAuthor(String author) throws AddingException {
        if(!Objects.equals(author,"")) {
            this.author.set(author);
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
        this.indexBook= new SimpleStringProperty(indexBook);
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