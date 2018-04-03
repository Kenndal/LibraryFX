package logic;


import exceptions.AddingException;
import exceptions.SearchException;
import exceptions.RemovingException;

import java.util.*;
import java.util.logging.Logger;
import java.io.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;



public class Library implements Serializable {

    public Library() {}

    // zminene podstawowe
    // catalog
    private Catalog catalog = new Catalog();

    // lista czytelnikow
    private ArrayList<Reader> readers = new ArrayList<Reader>();

    // dodatkowe
    private Random random = new Random();
    private static Logger log = Logger.getLogger(Library.class.getName());
    // gettery

    public Catalog getCatalog() {
        return catalog;
    }

    public ArrayList<Reader> getReaders() {
        return readers;
    }

    // metody wypożyczenia, zwroty, dodawanie czytleników

    public void addReader(String firstName, String lastName, String birthday, String email) throws AddingException {


            Reader czytelnik = new Reader(firstName, lastName, birthday, email);
            czytelnik.setBooksHaveStatus(false);
            czytelnik.addIndeksReader(addIndexReader(czytelnik.getFirstName(), czytelnik.getLastName()));
            readers.add(czytelnik);

    }

    public void removeReader(String readerIndex) {
        readers.remove(fingReader(readerIndex));
    }

    private String addIndexReader(String firstName, String lastName) {
        String firstLetterOfFirstName = String.valueOf(firstName.charAt(0));
        String firstLetterOfLastName = String.valueOf(lastName.charAt(0));
        int numer_karty = random.nextInt(10001);
        String indeks;
        indeks = firstLetterOfFirstName + firstLetterOfLastName + Integer.toString(numer_karty);
        return indeks;
    }

    public Reader fingReader(String indeks_czytelnika) {
        for (int i = 0; i < getReaders().size(); i++) {
            if (getReaders().get(i).getIndexReader().equals(indeks_czytelnika))
                return getReaders().get(i);
        }
        return null;
    }

    public void printReaders() {
        int i = 0;
        while (i < getReaders().size()) {
            System.out.println(getReaders().get(i).getFirstName() + " " + getReaders().get(i).getLastName() + " " + getReaders().get(i).getBrithday() + " " + getReaders().get(i).getEmail() + " " + getReaders().get(i).getRentBooks());
            System.out.print(getReaders().get(i));
            i++;
        }
    }

    public void wypisz_stan_katalogu_czytelnika(String readerIndex) {
        fingReader(readerIndex).printReaderBooks();
    }

    public void addNewBook(String title, String genre, String author) throws AddingException {
        Book book = new Book(title, genre, author, true);
        book.addIndexBook(addBookIndex(book.getTitle(), book.getGenre(), book.getAuthor()));
        catalog.addBook(book);
    }

    private String addBookIndex(String title, String genre, String author) {
        String threeFirstLetterTitle = String.valueOf(title.charAt(0)) + String.valueOf(title.charAt(1)) + String.valueOf(title.charAt(2));
        String threeFirstLetterGenre = String.valueOf(genre.charAt(0)) + String.valueOf(genre.charAt(1)) + String.valueOf(genre.charAt(2));
        String threeFirstLetterAutor = String.valueOf(author.charAt(0)) + String.valueOf(author.charAt(1)) + String.valueOf(author.charAt(2));
        int numer = random.nextInt(10001);
        String indeks;
        indeks = threeFirstLetterTitle + threeFirstLetterGenre + threeFirstLetterAutor + Integer.toString(numer);
        return indeks;
    }

    public void removeBook(String bookTitle) throws RemovingException {
        if (findBook(bookTitle).isStatus())
            catalog.removeBook(bookTitle);
        else
            throw new RemovingException("Nie można usunąć wypożyczonej książki!");
    }

    public void rentBook(String bookTitle, String readerIndex) throws SearchException, AddingException {
        int k = 0 ;
        if(!Objects.equals(readerIndex, "")) {
        for (int i = 0; i < getReaders().size(); i++) {
            if (getReaders().get(i).getIndexReader().equals(readerIndex))
                getReaders().get(i).rentBook(bookTitle, findBook(bookTitle));
            else
                k++;
            }
            if(k==getReaders().size())
                throw new SearchException("Nie ma Czytelnika o podanym indeksie!");
        catalog.getBooks().get(bookTitle).rentBook();
        }
        else
            throw new AddingException("Wypełnij pole indeks czytelnika!");

    }

    public void returnBook(String bookTitle, String readerIndex) {
        if (!findBook(bookTitle).isStatus()) {
            catalog.getBooks().get(bookTitle).returnBook();
            for (int i = 0; i < getReaders().size(); i++) {
                if (getReaders().get(i).getIndexReader().equals(readerIndex))
                    getReaders().get(i).returnBook(bookTitle, findBook(bookTitle));
            }
        }
    }

    public void returnAllBooks (String readerIndex){
        for(Map.Entry<String, Book> entry : fingReader(readerIndex).getRentBooks().entrySet()){
            catalog.getBooks().get(entry.getKey()).returnBook();
        }
    }
    public void printCatalog() {
        catalog.printBooks();
    }

    public Book findBook(String bookTitle) {
        return catalog.getBooks().get(bookTitle);
    }


    // zapis katalogu do pliku
    public void zapiszDoPlikuKatalog(String nazwaPliku)throws IOException{
        ObjectOutputStream dozapisu = null;
        try {
            dozapisu = new ObjectOutputStream(new FileOutputStream(nazwaPliku));
            dozapisu.writeObject(catalog);
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
            catalog =(Catalog) doodczytu.readObject();
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
            dozapisu.writeObject(readers);
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
            readers = (ArrayList<Reader>) doodczytu.readObject();
        } catch (EOFException ex) {
            System.out.println("Koniec pliku");
        }
        finally{
            if(doodczytu!=null)
                doodczytu.close();
        }
    }


    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Library biblioteka = new Library();
        biblioteka.zapiszDoPlikuKatalog("biblioteczka.bin");
        biblioteka.zapiszDoPlikuCzytelnicy("czytelnicy.bin");
        biblioteka.odczytKataloguZPliku("biblioteczka.bin");
        biblioteka.odczytCzytelnikowZPliku("czytelnicy.bin");


    }
}