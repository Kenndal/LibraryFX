package graphicInterface.fxml;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import logic.Library;
import logic.Book;
import logic.Reader;
import exceptions.AddingException;
import graphicInterface.MyApp;
import exceptions.RemovingException;

import java.util.Optional;


public class ControllerMainPanel {

    private Library library;


    @FXML
    private TextField mailTextField;

    @FXML
    private TextField genreTextField;

    @FXML
    private Button removeReaderButton;

    @FXML
    private TableColumn<Reader, String> firstNameColumn;

    @FXML
    private Button rentButton;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private Button addNewReaderButton;

    @FXML
    private TableColumn<Reader, String> indexReaderColumn;

    @FXML
    private TextField birthdayTextField;

    @FXML
    private TableView<Reader> readerTable;

    @FXML
    private TableColumn<Book, String> statusColumn;

    @FXML
    private TableColumn<Reader, String> arrearsCoulmn;

    @FXML
    private TableColumn<Book, String> genreColumn;

    @FXML
    private TextField authorTextField;

    @FXML
    private TextField searchBookTextField;

    @FXML
    private Button addBookButton;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private Button detailsButton;

    @FXML
    private TextField titleTextField;

    @FXML
    private TableColumn<Book, String> titleColumn;

    @FXML
    private TableView<Book> booksTable;

    @FXML
    private TableColumn<Book, String> indexColumn;

    @FXML
    private Button editButton;

    @FXML
    private TableColumn<Book, String> authorColumn;

    @FXML
    private TableColumn<Reader, String> lastNameColumn;

    @FXML
    private TextField searchReaderTextField;

    @FXML
    private Button removeBookButton;


    private MyApp myApp;

    FilteredList<Book> filteredDataBook;
    FilteredList<Reader> filteredDataReader;

    @FXML
    private void initialize() {
        // Initialize the book table with the five columns.
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().getTitleProperty());
        authorColumn.setCellValueFactory(cellData -> cellData.getValue().getAuthorProperty());
        genreColumn.setCellValueFactory(cellData -> cellData.getValue().getGenreProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().getStatusStringProperty());
        indexColumn.setCellValueFactory(cellData -> cellData.getValue().getIndexBookProperty());
        // Initialize the person table with the four columns.
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());
        indexReaderColumn.setCellValueFactory(cellData -> cellData.getValue().getINdeksReaderProperty());
        arrearsCoulmn.setCellValueFactory(cellData -> cellData.getValue().getBooksHaveStatusStringProperty());

        // 2. Set the filter Predicate whenever the filter changes.
        searchBookTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredDataBook.setPredicate(book -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (book.getTitle().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches title.
                } else if (book.getAuthor().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches author.
                }else if (book.getGenre().toLowerCase().contains(lowerCaseFilter)){
                    return true; // Filter type author.
                }else if (book.getIndexBook().toLowerCase().contains(lowerCaseFilter)){
                    return true; //Filter index author.
                }
                return false; // Does not match.
            });
        });


        searchReaderTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredDataReader.setPredicate(reader -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (reader.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (reader.getLastName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }else if (reader.getIndexReader().toLowerCase().contains(lowerCaseFilter)){
                    return true; // Filter matches index.
                }else
                return false; // Does not match.
            });
        });

        statusColumn.setCellFactory(column ->{
            return new TableCell<Book,String>(){
                @Override
                protected void updateItem(String item, boolean empty){
                    super.updateItem(item,empty);

                    if(item == null || empty){
                        setText(null);
                        setStyle("");
                    }else{
                        setText(item);
                        if(item.equals("Dostępna")){
                            setTextFill(Color.GREEN);
                        }else{
                            setTextFill(Color.RED);
                        }
                    }
                }
            };
        });

        arrearsCoulmn.setCellFactory(column ->{
            return new TableCell<Reader,String>(){
                @Override
                protected void updateItem(String item,boolean empty){
                    super.updateItem(item, empty);

                    if(item == null || empty){
                        setText(null);
                        setStyle("");
                    }else{
                        setText(item);
                        if(item.equals("Tak")){
                            setTextFill(Color.YELLOW);
                        }else{
                            setTextFill(Color.GREEN);
                        }
                    }
                }
            };
        });
    }


    public void setMyApp(MyApp myApp) {
        this.myApp = myApp;
        booksTable.setItems(myApp.getBooksData());
        readerTable.setItems(myApp.getReadersData());
        filteredDataBook = myApp.getFilteredDataBook();
        filteredDataReader = myApp.getFilteredDataReader();
    }

    public void setBiblioteka(Library library) {
        this.library = library;
    }

    @FXML
     private void delateBook(){
        int selectIndex = booksTable.getSelectionModel().getFocusedIndex();
        Book selectedBook = booksTable.getSelectionModel().getSelectedItem();
        try {
            if(selectedBook != null) {
                library.removeBook(booksTable.getItems().get(selectIndex).getTitle());
                myApp.getBooksData().remove(selectedBook);
            }
            else {
                alertToSelectBook();
            }
        } catch (RemovingException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(myApp.getPrimaryStage());
            alert.setTitle("Uwaga!");
            alert.setHeaderText(e.getMessage());
            alert.setContentText("Proszę najpierw zwrócić książkę, a potem dopiero usuń.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleEditBook(){
         Book selectedBook = booksTable.getSelectionModel().getSelectedItem();
         if(selectedBook != null){
             boolean okClicked = myApp.showBookEditDialog(selectedBook);
             if(okClicked){
                 myApp.getBooksData().remove(selectedBook);
                 myApp.getBooksData().add(selectedBook);
                 myApp.getBooksData().sorted();
             }
         }else {
                alertToSelectBook();
         }
    }

    @FXML
    private void handleRentBook(){
        Book selectedBook = booksTable.getSelectionModel().getSelectedItem();
        if(selectedBook != null){
            boolean okClicked = myApp.showBookRentDialog(selectedBook);
            if(okClicked){
                refreshTable();
            }
        }else {
            alertToSelectBook();
        }
    }

    @FXML
    private void handleAddBook(){
        try {
            library.addNewBook(titleTextField.getText(),genreTextField.getText(),authorTextField.getText());
            myApp.getBooksData().add(library.getCatalog().getBooks().get(titleTextField.getText()));
            titleTextField.clear();
            genreTextField.clear();
            authorTextField.clear();
        } catch (AddingException e) {
            alertToFillAllTextFields(e.getMessage());
        }
    }

    @FXML
    private void handleAddReader(){
        try {
            library.addReader(firstNameTextField.getText(),lastNameTextField.getText(),birthdayTextField.getText(),mailTextField.getText());
            myApp.getReadersData().add(library.getReaders().get(library.getReaders().size()-1));
            firstNameTextField.clear();
            lastNameTextField.clear();
            birthdayTextField.clear();
            mailTextField.clear();
        } catch (AddingException e) {
            alertToFillAllTextFields(e.getMessage());
        }
    }


    @FXML
    private void delateReader(){
        int selectIndex = readerTable.getSelectionModel().getFocusedIndex();
        Reader selectedReader = readerTable.getSelectionModel().getSelectedItem();
        if(selectedReader != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(myApp.getPrimaryStage());
            alert.setTitle("Uwaga!");
            alert.setHeaderText("Czy jesteś pewien ten operacji?");
            alert.setContentText("Proszę upewnić się, że czytelnik zwrócił wszystkie książki");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK) {
                library.returnAllBooks(selectedReader.getIndexReader());
                library.removeReader(selectedReader.getIndexReader());
                myApp.getReadersData().remove(selectedReader);
                ObservableList<Book> booksData = myApp.getBooksData().sorted();
                myApp.getBooksData().removeAll();
                myApp.getBooksData().setAll(booksData);
            }
        }
        else {
            alertToSelectReader();
        }
    }



    @FXML
    private void handleReader() {
        Reader selectedReader = readerTable.getSelectionModel().getSelectedItem();
        if(selectedReader != null) {
            boolean closeClicked = myApp.showReaderDialog(selectedReader);
            if(closeClicked){
                refreshTable();
            }
        }else alertToSelectReader();
    }

    @FXML
    private void searchingBook(){
        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Book> sortedData = new SortedList<>(filteredDataBook);
        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(booksTable.comparatorProperty());
        // 5. Add sorted (and filtered) data to the table.
        booksTable.setItems(sortedData);
    }

    @FXML
    private void searchingReader(){
        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Reader> sortedData = new SortedList<>(filteredDataReader);
        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(readerTable.comparatorProperty());
        // 5. Add sorted (and filtered) data to the table.
        readerTable.setItems(sortedData);
    }



    private void refreshTable(){
        ObservableList<Book> booksData = myApp.getBooksData().sorted();
        myApp.getBooksData().removeAll();
        myApp.getBooksData().setAll(booksData);
        ObservableList<Reader> readersData = myApp.getReadersData().sorted();
        myApp.getReadersData().removeAll();
        myApp.getReadersData().setAll(readersData);
    }

    private void alertToSelectBook(){
        // Nothing selected.
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(myApp.getPrimaryStage());
        alert.setTitle("Uwaga");
        alert.setHeaderText("Nie wybrano książki.");
        alert.setContentText("Proszę najpierw wybrać książkę z tabeli.");
        alert.showAndWait();
    }

    private void alertToSelectReader(){
        // Nothing selected.
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(myApp.getPrimaryStage());
        alert.setTitle("Uwaga");
        alert.setHeaderText("Nie wybrano Czytelnika.");
        alert.setContentText("Proszę najpierw wybrać Czytelnika z tabeli.");
        alert.showAndWait();
    }


    private void alertToFillAllTextFields(String exceptionInformations){
        // Show the error message.
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(myApp.getPrimaryStage());
        alert.setTitle("Uwaga!");
        alert.setHeaderText("Żle wypełnione pole");
        alert.setContentText(exceptionInformations);
        alert.showAndWait();
    }


}

