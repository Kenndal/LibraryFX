package sample.fxml;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logika.Ksiazka;
import wyjatki.DodawanieException;

public class ControllerKsiazkaEditPanel {

    @FXML
    private Button anulujButton;

    @FXML
    private Button zapiszButton;

    @FXML
    private TextField tytulTextField;

    @FXML
    private TextField autorTextField;

    @FXML
    private TextField indeksTextField;

    @FXML
    private TextField gatunekTextField;

    @FXML
    private TextField dostepnoscTextField;

    private Stage dialogStage;
    private boolean okClicked = false;
    private Ksiazka ksiazka;


    @FXML
    private void initialize() {
    }

    // set dialogStage
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    public void setKsiazka(Ksiazka ksiazka){
        this.ksiazka = ksiazka;
        tytulTextField.setText(ksiazka.getNazwa());
        autorTextField.setText(ksiazka.getAutor());
        gatunekTextField.setText(ksiazka.getGatunek());
        indeksTextField.setText(ksiazka.getIndeks_ksiazki());
        dostepnoscTextField.setText(ksiazka.isStanString());
    }

    // return true if the user clicked OK
    public boolean isOkClicked() {
        return okClicked;
    }


    @FXML
    private void handleOK(){
        try {
            ksiazka.setNazwa(tytulTextField.getText());
            ksiazka.setAutor(autorTextField.getText());
            ksiazka.setGatunek(autorTextField.getText());
        } catch (DodawanieException e) {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Uwaga!");
            alert.setHeaderText("Żle wypełnione pole");
            alert.setContentText(e.getMessage());
            okClicked = false;
        }
        okClicked = true;
        dialogStage.close();
    }


    @FXML
    private void handleCancel() {
        dialogStage.close();
    }









}
