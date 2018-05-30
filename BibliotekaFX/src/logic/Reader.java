package logic;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import exceptions.AddingException;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Reader implements Serializable {
    // zmienne podstawowe
    private String firstName;
    private String lastName;
    private String birthday;
    private String email;
    private String indexReader;
    private Boolean booksHaveStatus;
    private String imagePath;
    private Map<String, Book> rentBooks = new HashMap<String, Book>();
    // konstruktor
    public Reader(String firstName, String lastName, String birthday, String email) throws AddingException {
        if(!Objects.equals(firstName, "") && !Objects.equals(lastName, "") && !Objects.equals(birthday, "") && !Objects.equals(email, "")) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.birthday = birthday;
            this.email = email;
        }
        else
            throw new AddingException("Wypełnij wszystkie pola!");
    }

    // gettery
    public Map<String, Book> getRentBooks() {
        return rentBooks;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBrithday() {
        return birthday;
    }

    public String getEmail() {
        return email;
    }

    public String getIndexReader() {
        return indexReader;
    }

    public Boolean getBooksHaveStatus() {
        return booksHaveStatus;
    }

    public StringProperty getFirstNameProperty() {
        return new SimpleStringProperty(firstName);
    }

    public StringProperty getLastNameProperty() {
        return new SimpleStringProperty(lastName);
    }

    public StringProperty getBrithDayProperty() {
        return new SimpleStringProperty(birthday);
    }

    public StringProperty getEmailProperty() {
        return new SimpleStringProperty(email);
    }

    public StringProperty getINdeksReaderProperty() {
        return new SimpleStringProperty(indexReader);
    }

    public String getImagePath() {
        return imagePath;
    }

    public StringProperty getImageProperty() {
        return new SimpleStringProperty(imagePath);
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setFirstName(String firstName) throws AddingException {
        if(!Objects.equals(firstName,"")) {
            this.firstName = firstName;
        }
        else
            throw new AddingException("Wypełnij pole Imie!");
    }

    public void setLastName(String lastName) throws AddingException {
        if(!Objects.equals(lastName,"")) {
            this.lastName = lastName;
        }
        else
            throw new AddingException("Wypełnij pole Nazwisko!");
    }

    public void setBirthday(String birthday)throws AddingException {
        if(!Objects.equals(birthday,"")) {
            this.birthday = birthday;
        }
        else
            throw new AddingException("Wypelnij pole Data Urodzenia!");
    }

    public void setEmail(String email) throws AddingException {
        if(!Objects.equals(email,"")) {
            this.email = email;
        }
        else
            throw new AddingException("Wypełnij pole Numer Telefonu!");
    }

    public void setBooksHaveStatus(Boolean booksHaveStatus) {
        this.booksHaveStatus = booksHaveStatus;
    }

    protected void addIndeksReader(String index){
        indexReader = index;
    }

    // metody (wypozyczenia i zwroty)

    public void rentBook(String bookTitle, Book book) {
        rentBooks.put(bookTitle, book);
        setBooksHaveStatus();
    }

    public void returnBook (String bookTitle, Book book){
        rentBooks.remove(bookTitle, book);
        setBooksHaveStatus();
    }

    public void printReaderBooks(){
        for(int i=0; i<getRentBooks().size(); i++){
            System.out.println(getRentBooks().get(i).getTitle()+ " " + getRentBooks().get(i).getAuthor()+ " " + getRentBooks().get(i).getGenre()+ " " + getRentBooks().get(i).getIndexBook());
            System.out.print(getRentBooks().get(i));
        }
    }

    // trzeba sprawdzac po kazdym wypozyczeniu/oddaniu
    public void setBooksHaveStatus(){
        if (rentBooks.isEmpty())
            booksHaveStatus = false;
        else
            booksHaveStatus = true;
    }

    public String getBooksHaveStatusString(){
        if(booksHaveStatus){
            return "Tak";
        }
        else
            return "Nie";
    }

    public StringProperty getBooksHaveStatusStringProperty(){
        if(booksHaveStatus){
            return new SimpleStringProperty("Tak");
        }
        else
            return new SimpleStringProperty("Nie");
    }

    public void copyImage(String sourcePath, String destPath) throws IOException, AddingException {
        File source = new File(sourcePath);
        File dest = new File(destPath + "/" + "img" +  source.getName());
        if(dest.exists()){
           throw new AddingException("Zmień nazwe zdjęcia, ta nie jest możliwa.");
        }
        try {
            Files.copy(source.toPath(),dest.toPath());
            imagePath = dest.getPath();
        } catch (IOException e) {
            System.out.println("Coś poszło nie tak :(");
            e.printStackTrace();
        }
    }
}