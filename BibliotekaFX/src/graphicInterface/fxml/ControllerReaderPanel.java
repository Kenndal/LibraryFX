package graphicInterface.fxml;

import graphicInterface.MyApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logic.Library;
import logic.Book;
import logic.Reader;
import exceptions.AddingException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class ControllerReaderPanel {
    @FXML
    private TextField mailTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField readerIndexTextField;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TableColumn<Book, String> titleColumn;

    @FXML
    private TextField birthdayTextField;

    @FXML
    private ImageView readerImage;

    @FXML
    private TableView<Book> booksTable;

    @FXML
    private TableColumn<Book, String> indexColumn;

    @FXML
    private Button closeButton;

    @FXML
    private TableColumn<Book, String> authorColumn;

    @FXML
    private Button saveButton;

    @FXML
    private Button returnButton;

    @FXML
    private TableColumn<Book, String> genreColumn;


    private Stage dialogStage;
    private boolean closeClicked = false;
    private Reader reader;
    private Library library;
    private ObservableList<Book> booksData = FXCollections.observableArrayList();



    @FXML
    private void initialize() {
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().getTitleProperty());
        authorColumn.setCellValueFactory(cellData -> cellData.getValue().getAuthorProperty());
        genreColumn.setCellValueFactory(cellData -> cellData.getValue().getGenreProperty());
        indexColumn.setCellValueFactory(cellData -> cellData.getValue().getIndexBookProperty());


    }

    // set dialogStage
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    public void setReader(Reader reader) {
        this.reader = reader;

        firstNameTextField.setText(reader.getFirstName());
        lastNameTextField.setText(reader.getLastName());
        birthdayTextField.setText(reader.getBrithday());
        readerIndexTextField.setText(reader.getIndexReader());
        mailTextField.setText(reader.getEmail());

            if (reader.getImage() != null) {
                try {
                    FileInputStream input = new FileInputStream(reader.getImage());
                    readerImage.setImage(new Image(input));
                } catch (FileNotFoundException e) {
                    alertImage();
            }
            }



        booksData.addAll(reader.getRentBooks().values());
        booksTable.setItems(booksData);
}

    public void setLibrary(Library library) {
        this.library = library;
    }

    // return true if the user clicked OK
    public boolean isCloseClicked() {
        return closeClicked;
    }

    @FXML
    private void handleSave() {
        try {
            reader.setFirstName(firstNameTextField.getText());
            reader.setLastName(lastNameTextField.getText());
            reader.setBirthday(birthdayTextField.getText());
            reader.setEmail(mailTextField.getText());

        } catch (AddingException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Uwaga!");
            alert.setHeaderText("Żle wypełnione pole");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void handleCancel() {
        dialogStage.close();
        closeClicked = true;
    }



    @FXML
    private void handleReturn(){

        Book selectedBook = booksTable.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            library.returnBook(selectedBook.getTitle(),reader.getIndexReader());
            booksData.remove(selectedBook);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(dialogStage);
            alert.setTitle("Uwaga");
            alert.setHeaderText("Nie wybrano książki.");
            alert.setContentText("Proszę najpierw wybrać książkę z tabeli.");
            alert.showAndWait();
        }

    }

    @FXML
    private void handleAddImage(){
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "jpg files (*.jpg)", "*.jpg");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showOpenDialog(this.dialogStage);

        if(file != null){
              reader.setImage(file.toString());
                try {
                    FileInputStream input = new FileInputStream(reader.getImage());
                    readerImage.setImage(new Image(input));
                } catch (FileNotFoundException e) {
                    alertImage();
                }
        }

    }

    private void alertImage(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(this.dialogStage);
        alert.setTitle("Uwaga!");
        alert.setHeaderText("Nie można odnaleźć zdjęcia");
        alert.setContentText("Prawdopodobnie zdjęcie zostało usunięte.\n" +
                "Proszę o dodanie zdjecie od nowa.");

        alert.setWidth(600);
        alert.setHeight(400);
        alert.showAndWait();

        reader.setImage("");
    }
}


