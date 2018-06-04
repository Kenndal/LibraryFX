package logic;


import exceptions.AddingException;
import exceptions.SearchException;
import exceptions.RemovingException;
import exceptions.StatusException;

import java.nio.file.Files;
import java.util.*;
import java.io.*;
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
    // files to remove after close
    private ArrayList<String> fileToRemove =  new ArrayList<>();
    // gettery


    public ArrayList<String> getFileToRemove() {
        return fileToRemove;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public ArrayList<Reader> getReaders() {
        return readers;
    }

    // metody wypożyczenia, zwroty, dodawanie czytleników

    public void addReader(String firstName, String lastName, String birthday, String email) throws AddingException {
        Reader reader = new Reader(firstName, lastName, birthday, email);
        reader.setBooksHaveStatus(false);
        reader.addIndeksReader(addIndexReader(reader.getFirstName(), reader.getLastName()));
        readers.add(reader);
    }

    public void removeReader(String readerIndex) throws IOException {
        fileToRemove.add(fingReader(readerIndex).getImagePath());
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
            if (getReaders().get(i).getIndexReader().equals(indeks_czytelnika)) {
                return getReaders().get(i);
            }
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
        book.setIndexBook(addBookIndex(book.getTitle(), book.getGenre(), book.getAuthor()));
        catalog.addBook(book);
    }

    public String addBookIndex(String title, String genre, String author) {
        String firstLettersTitle = "";
        String firstLettersGenre = "";
        String firstLettersAutor = "";
        for(int i=0; i<title.length(); i++){
            if(i < 3) {
                firstLettersTitle += String.valueOf(title.charAt(i));
            }else
                break;
        }
        for(int i=0;i<genre.length();i++) {
            if(i < 3){
                firstLettersGenre += String.valueOf(genre.charAt(i));
            }else break;
        }
        for(int i=0;i<author.length();i++) {
            if (i < 3) {
                firstLettersAutor += String.valueOf(author.charAt(i));
            }else break;
        }
        int numer = random.nextInt(10001);
        String indeks;
        indeks = firstLettersTitle + firstLettersGenre + firstLettersAutor + Integer.toString(numer);
        return indeks;
    }

    public void removeBook(String bookTitle) throws RemovingException {
        if (findBook(bookTitle).isStatus())
            catalog.removeBook(bookTitle);
        else
            throw new RemovingException("Nie można usunąć wypożyczonej książki!");
    }

    public void rentBook(String bookTitle, String readerIndex) throws SearchException, AddingException, StatusException {
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
        catalog.getBooks().get(bookTitle).isStatusCheck();
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
    public void saveCatalogToFile()throws IOException{
        ObjectOutputStream toSave = null;
        File file = new File(System.getProperty("user.home") + "/BibliotekaFX/catalog.bin");
        if(!file.exists()){
            file.createNewFile();
        }
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            toSave = new ObjectOutputStream(outputStream);
            toSave.writeObject(catalog);
        }catch (IOException ex) {
            System.out.println(System.getProperty("user.home") + "/BibliotekaFX/catalog.bin");
            System.out.println("Blad w zapisie do pliku 'catalog.bin'");
            ex.getMessage();
        }
        finally {
            if(toSave!=null)
                toSave.close();
        }
    }

    // odczyt katalogu z pliku
    public void loadCatalog()throws IOException,ClassNotFoundException{
        File file = new File(System.getProperty("user.home") + "/BibliotekaFX/catalog.bin");
        ObjectInputStream toLoad = null;
        try{
            FileInputStream inputStream = new FileInputStream(file);
            toLoad = new ObjectInputStream(inputStream);
            catalog =(Catalog) toLoad.readObject();
        } catch (EOFException ex) {
            System.out.println("Koniec pliku");
        }
        finally{
            if(toLoad!=null)
                toLoad.close();
        }
    }

    // zapis Czytelnikow do pliku
    public void saveReadersToFile()throws IOException{
        ObjectOutputStream toSave = null;
        File file = new File(System.getProperty("user.home") + "/BibliotekaFX/readers.bin");
        if(!file.exists()){
            file.createNewFile();
        }
        try {
            OutputStream outputStream = new FileOutputStream(file);
            toSave = new ObjectOutputStream(outputStream);
            toSave.writeObject(readers);
        }catch (IOException ex) {
            System.out.println("Blad w zapisie do pliku 'readers.bin'");
        }
        finally {
            if(toSave!=null)
                toSave.close();
        }
    }

    // odczyt czytelnikow z pliku
    public void loadReaders()throws IOException,ClassNotFoundException{
        File file = new File(System.getProperty("user.home") + "/BibliotekaFX/readers.bin");
        ObjectInputStream toLoad=null;
        try{
            FileInputStream inputStream = new FileInputStream(file);
            toLoad = new ObjectInputStream(inputStream);
            readers = (ArrayList<Reader>) toLoad.readObject();
        } catch (EOFException ex) {
            System.out.println("Koniec pliku");
        }
        finally{
            if(toLoad!=null)
                toLoad.close();
        }
    }

    public void libraryClose(){
        for (String aFileToRemove : fileToRemove) {
            File file = new File(aFileToRemove);
            try {
                Files.delete(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<String> allIndexes(){
        ArrayList<String> indexes = new ArrayList<>();
        for (Reader reader : readers) {
            indexes.add(reader.getIndexReader());
        }

        return indexes;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Library library = new Library();
        library.saveCatalogToFile();
        library.saveReadersToFile();
        library.loadCatalog();
        library.loadReaders();
    }
}