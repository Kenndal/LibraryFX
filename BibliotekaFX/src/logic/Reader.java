package logic;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import exceptions.AddingException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Reader implements Serializable {
    // zmienne podstawowe
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty birthday;
    private StringProperty email;
    private StringProperty indexReader;
    private Boolean booksHaveStatus;
    private StringProperty image;
    private Map<String, Book> rentBooks = new HashMap<String, Book>();
    // konstruktor
    public Reader(String firstName, String lastName, String birthday, String email) throws AddingException {
        if(!Objects.equals(firstName, "") && !Objects.equals(lastName, "") && !Objects.equals(birthday, "") && !Objects.equals(email, "")) {
            this.firstName = new SimpleStringProperty(firstName);
            this.lastName = new SimpleStringProperty(lastName);
            this.birthday = new SimpleStringProperty(birthday);
            this.email = new SimpleStringProperty(email);
        }
        else
            throw new AddingException("Wypełnij wszystkie pola!");
    }

    // gettery
    public Map<String, Book> getRentBooks() {
        return rentBooks;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public String getLastName() {
        return lastName.get();
    }

    public String getBrithday() {
        return birthday.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getIndexReader() {
        return indexReader.get();
    }

    public Boolean getBooksHaveStatus() {
        return booksHaveStatus;
    }

    public StringProperty getFirstNameProperty() {
        return firstName;
    }

    public StringProperty getLastNameProperty() {
        return lastName;
    }

    public StringProperty getBrithDayProperty() {
        return birthday;
    }

    public StringProperty getEmailProperty() {
        return email;
    }

    public StringProperty getINdeksReaderProperty() {
        return indexReader;
    }

    public String getImage() {
        return image.get();
    }

    public StringProperty getImageProperty() {
        return image;
    }

    public void setImage(String image) {
        this.image = new SimpleStringProperty(image);
    }

    public void setFirstName(String firstName) throws AddingException {
        if(!Objects.equals(firstName,"")) {
            this.firstName = new SimpleStringProperty(firstName);;
        }
        else
            throw new AddingException("Wypełnij pole Imie!");
    }

    public void setLastName(String lastName) throws AddingException {
        if(!Objects.equals(lastName,"")) {
            this.lastName = new SimpleStringProperty(lastName);
        }
        else
            throw new AddingException("Wypełnij pole Nazwisko!");
    }

    public void setBirthday(String birthday)throws AddingException {
        if(!Objects.equals(birthday,"")) {
            this.birthday = new SimpleStringProperty(birthday);
        }
        else
            throw new AddingException("Wypelnij pole Data Urodzenia!");
    }

    public void setEmail(String numer_telefonu) throws AddingException {
        if(!Objects.equals(numer_telefonu,"")) {
            this.email = new SimpleStringProperty(numer_telefonu);
        }
        else
            throw new AddingException("Wypełnij pole Numer Telefonu!");
    }

    public void setBooksHaveStatus(Boolean booksHaveStatus) {
        this.booksHaveStatus = booksHaveStatus;
    }

    protected void addIndeksReader(String index){
        indexReader = new SimpleStringProperty(index);
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

}