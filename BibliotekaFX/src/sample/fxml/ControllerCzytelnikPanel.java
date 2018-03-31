package sample.fxml;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import logika.Biblioteka;
import logika.Czytelnik;
import logika.Ksiazka;
import wyjatki.DodawanieException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class ControllerCzytelnikPanel {
    @FXML
    private TextField mailTextField;

    @FXML
    private TextField dataTextField;

    @FXML
    private ImageView imageCz;

    @FXML
    private TextField indeksCzTextField;

    @FXML
    private TableColumn<Ksiazka, String> kolumnaIndeks;

    @FXML
    private TableColumn<Ksiazka, String> kolumnaTytul;

    @FXML
    private Button zamknijButton;

    @FXML
    private TextField imieTextField;

    @FXML
    private Button zapiszButton;

    @FXML
    private TextField nazwiskoTextField;

    @FXML
    private TableView<Ksiazka> tableKsiazki;

    @FXML
    private TableColumn<Ksiazka, String> kolumnaGatunek;

    @FXML
    private TableColumn<Ksiazka, String> kolumnaAutor;

    @FXML
    private Button zwrotButton;


    private Stage dialogStage;
    private boolean zamknijClicked = false;
    private Czytelnik czytelnik;
    private Biblioteka biblioteka;
    private ObservableList<Ksiazka> ksiazkiData = FXCollections.observableArrayList();



    @FXML
    private void initialize() {
        kolumnaTytul.setCellValueFactory(cellData -> cellData.getValue().getNazwaProperty());
        kolumnaAutor.setCellValueFactory(cellData -> cellData.getValue().getAutorProperty());
        kolumnaGatunek.setCellValueFactory(cellData -> cellData.getValue().getGatunekProperty());
        kolumnaIndeks.setCellValueFactory(cellData -> cellData.getValue().getIndeks_ksiazkiProperty());
    }

    // set dialogStage
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    public void setCzytelnik(Czytelnik czytelnik) {
        this.czytelnik = czytelnik;

        imieTextField.setText(czytelnik.getImie());
        nazwiskoTextField.setText(czytelnik.getNazwisko());
        dataTextField.setText(czytelnik.getData_urodzenia());
        indeksCzTextField.setText(czytelnik.getIndeks_czytelnika());
        mailTextField.setText(czytelnik.getEmail());
        try {
            FileInputStream input = new FileInputStream(czytelnik.getZdjecie());
            imageCz.setImage(new Image(input));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        ksiazkiData.addAll(czytelnik.getWypozyczone_ksiazki().values());
        tableKsiazki.setItems(ksiazkiData);

//        biblioteka.wypisz_stan_katalogu_czytelnika(czytelnik.getIndeks_czytelnika());
}

    public void setBiblioteka(Biblioteka biblioteka) {
        this.biblioteka = biblioteka;
    }

    // return true if the user clicked OK
    public boolean isZamknijClicked() {
        return zamknijClicked;
    }

    @FXML
    private void handleZapisz() {
        try {
            czytelnik.setImie(imieTextField.getText());
            czytelnik.setNazwisko(nazwiskoTextField.getText());
            czytelnik.setData_urodzenia(dataTextField.getText());
            czytelnik.setEmail(mailTextField.getText());

        } catch (DodawanieException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Uwaga!");
            alert.setHeaderText("Żle wypełnione pole");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void zwrotHandle(){

    }


    @FXML
    private void handleCancel() {
        dialogStage.close();
        zamknijClicked = true;
    }



    @FXML
    private void handleZwrot(){
        Ksiazka selectedBook = tableKsiazki.getSelectionModel().getSelectedItem();
        biblioteka.zwrocKsiazke(selectedBook.getNazwa(),czytelnik.getIndeks_czytelnika());
        ksiazkiData.remove(selectedBook);
    }
}


