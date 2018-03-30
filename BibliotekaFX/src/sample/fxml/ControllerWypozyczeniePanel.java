package sample.fxml;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logika.Biblioteka;
import logika.Ksiazka;
import wyjatki.DodawanieException;
import wyjatki.SzukanieException;

public class ControllerWypozyczeniePanel {

    @FXML
    private TextField imieTextField;

    @FXML
    private TextField tytulTextField;

    @FXML
    private TextField nazwiskoTextField;

    @FXML
    private TextField autorTextField;

    @FXML
    private TextField indeksTextField;

    @FXML
    private TextField indeksCzTextField;

    @FXML
    private Button wypozyczButton;

    @FXML
    private Button anulujButton;


    private Stage dialogStage;
    private boolean wypozyczClicked = false;
    private Ksiazka ksiazka;
    private Biblioteka biblioteka;
    @FXML
    private void initialize() {
    }

    // set dialogStage
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setKsiazka(Ksiazka ksiazka) {
        this.ksiazka = ksiazka;
        tytulTextField.setText(ksiazka.getNazwa());
        autorTextField.setText(ksiazka.getAutor());
        indeksTextField.setText(ksiazka.getIndeks_ksiazki());
    }

    public void setBiblioteka(Biblioteka biblioteka) {
        this.biblioteka = biblioteka;
    }

    // return true if the user clicked wypozycz
    public boolean isWypozyczClicked() {
        return wypozyczClicked;
    }

    @FXML
    private void handleOK(){
        try {
            biblioteka.wypozyczKsiazke(ksiazka.getNazwa(), indeksCzTextField.getText());
            wypozyczClicked = true;
            dialogStage.close();
        } catch (DodawanieException | SzukanieException e) {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Uwaga!");
            alert.setHeaderText("Żle wypełnione pole");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            wypozyczClicked = false;
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }



}
