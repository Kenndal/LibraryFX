package graphicInterface.fxml;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.Library;
import logic.Book;
import exceptions.AddingException;
import exceptions.SearchException;

public class ControllerRentPanel {

    @FXML
    private TextField titleTextField;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private Button rentButton;

    @FXML
    private TextField indexReaderTextField;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField indexTextField;

    @FXML
    private TextField authorTextField;



    private Stage dialogStage;
    private boolean rentClicked = false;
    private Book book;
    private Library library;
    @FXML
    private void initialize() {
    }

    // set dialogStage
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setBook(Book book) {
        this.book = book;
        titleTextField.setText(book.getTitle());
        authorTextField.setText(book.getAuthor());
        indexTextField.setText(book.getIndexBook());
    }

    public void setBiblioteka(Library library) {
        this.library = library;
    }

    // return true if the user clicked wypozycz
    public boolean isRentClicked() {
        return rentClicked;
    }

    @FXML
    private void handleOK(){
        try {
            library.rentBook(book.getTitle(), indexReaderTextField.getText());
            rentClicked = true;
            dialogStage.close();
        } catch (AddingException | SearchException e) {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Uwaga!");
            alert.setHeaderText("Żle wypełnione pole");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            rentClicked = false;
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }



}
