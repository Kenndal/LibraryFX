package logic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Catalog implements Serializable {

    private Map<String, Book> books = new HashMap<String, Book>();

    // gettery
    public Map<String, Book> getBooks() {
        return books;
    }


    public void addBook(Book book){
        books.put(book.getTitle(), book);
    }

    public void printBooks(){
        for(Map.Entry<String, Book> entry : books.entrySet()){
            System.out.println(entry.getValue().getTitle()+ " " + entry.getValue().getAuthor()+ " " + entry.getValue().getGenre()+ " " + entry.getValue().getIndexBook()+ " " + entry.getValue().isStatus());
            System.out.println(entry.getValue());
        }
    }

    public void removeBook(String bookTitle){
        books.remove(bookTitle);
    }


    protected Book search(String bookTitle){
        if(books.containsKey(bookTitle))
            return books.get(bookTitle);
        return null;
    }
}