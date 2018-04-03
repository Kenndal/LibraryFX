package graphicInterface.fxml;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.Book;
import exceptions.AddingException;

public class ControllerBookEditPanel {

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField genreTextField;

    @FXML
    private Button closeButton;

    @FXML
    private TextField statusTextField;

    @FXML
    private Button saveButton;

    @FXML
    private TextField indexTextField;

    @FXML
    private TextField authorTextField;

    private Stage dialogStage;
    private boolean okClicked = false;
    private Book book;


    @FXML
    private void initialize() {
    }

    // set dialogStage
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    public void setBook(Book book){
        this.book = book;
        titleTextField.setText(book.getTitle());
        authorTextField.setText(book.getAuthor());
        genreTextField.setText(book.getGenre());
        indexTextField.setText(book.getIndexBook());
        statusTextField.setText(book.isStatusString());
    }

    // return true if the user clicked OK
    public boolean isOkClicked() {
        return okClicked;
    }


    @FXML
    private void handleOK(){
        try {
            book.setTitle(titleTextField.getText());
            book.setGenre(genreTextField.getText());
            book.setAuthor(authorTextField.getText());
            okClicked = true;
            dialogStage.close();
        } catch (AddingException e) {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Uwaga!");
            alert.setHeaderText("Żle wypełnione pole");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            okClicked = false;
        }

    }


    @FXML
    private void handleCancel() {
        dialogStage.close();
    }









}
