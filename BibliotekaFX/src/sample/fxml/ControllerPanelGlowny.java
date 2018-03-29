package sample.fxml;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import logika.Biblioteka;
import logika.Czytelnik;
import logika.Ksiazka;
import wyjatki.DodawanieException;
import javafx.event.ActionEvent;
import sample.MyApp;
import wyjatki.UsuwanieException;

public class ControllerPanelGlowny {

    private Biblioteka biblioteka;


    @FXML
    private TextField textFieldSzukajCzytelnika;

    @FXML
    private TextField textFieldGatunek;

    @FXML
    private TextField textFieldAutor;

    @FXML
    private TableColumn<Czytelnik, String> kolumnaIndeksCZ;

    @FXML
    private TableColumn<Ksiazka, String> kolumnaAutor;

    @FXML
    private TableColumn<Czytelnik, String> kolumnaNazwisko;

    @FXML
    private TextField textFieldImie;

    @FXML
    private TextField textFieldSzukajKsiazki;

    @FXML
    private TextField textFieldNazwisko;

    @FXML
    private TableColumn<Ksiazka, String> kolumnaTytul;

    @FXML
    private Button buttonDodajKsiazke;

    @FXML
    private TableColumn<Ksiazka, String> kolumnaGatunek;

    @FXML
    private TableColumn<Czytelnik, String> kolumnaZaleglosci;

    @FXML
    private TextField textFieldData;

    @FXML
    private TableView<Czytelnik> tableCzytelnicy;

    @FXML
    private TableColumn<Ksiazka, String> kolumnaIndeks;

    @FXML
    private TableColumn<Ksiazka, String> kolumnaStan;

    @FXML
    private Button buttonDodajCzytelnika;

    @FXML
    private TextField textFieldTytul;

    @FXML
    private TextField textFieldTelefon;

    @FXML
    private TableColumn<Czytelnik, String> kolumnaImie;

    @FXML
    private TableView<Ksiazka> tableKsiazki;

    @FXML
    private Button wypozyczButton;

    @FXML
    private Button pokazSzczegolyButton;

    @FXML
    private Button usunCzytelnikaButton;

    @FXML
    private Button usunKsiazkeButton;

    @FXML
    private Button edytujButton;

    private MyApp myApp;

    @FXML
    private void initialize() {
        // Initialize the book table with the five columns.
        kolumnaTytul.setCellValueFactory(cellData -> cellData.getValue().getNazwaProperty());
        kolumnaAutor.setCellValueFactory(cellData -> cellData.getValue().getAutorProperty());
        kolumnaGatunek.setCellValueFactory(cellData -> cellData.getValue().getGatunekProperty());
        kolumnaStan.setCellValueFactory(cellData -> cellData.getValue().getStanStringProperty());
        kolumnaIndeks.setCellValueFactory(cellData -> cellData.getValue().getIndeks_ksiazkiProperty());
        // Initialize the person table with the four columns.
        kolumnaImie.setCellValueFactory(cellData -> cellData.getValue().getImieProperty());
        kolumnaNazwisko.setCellValueFactory(cellData -> cellData.getValue().getNazwiskoProperty());
        kolumnaIndeksCZ.setCellValueFactory(cellData -> cellData.getValue().getIndeks_czytelnikaProperty());
        kolumnaZaleglosci.setCellValueFactory(cellData -> cellData.getValue().getPosiadanieKsiazekStringProperty());


    }

    public void setMyApp(MyApp myApp) {
        this.myApp = myApp;
        tableKsiazki.setItems(myApp.getKsiazkiData());
        tableCzytelnicy.setItems(myApp.getCzytelnicyData());
    }

    public void setBiblioteka(Biblioteka biblioteka) {
        this.biblioteka = biblioteka;
    }

    @FXML
     void dodajKsiazke(ActionEvent event) {
        try {
            biblioteka.dodajKsiazkeDoKatalogu(textFieldTytul.getText(),textFieldGatunek.getText(),textFieldGatunek.getText());
            myApp.getKsiazkiData().add(biblioteka.getKatalog().getKsiazki().get(textFieldTytul));
            textFieldTytul.setText("");
            textFieldGatunek.setText("");
            textFieldAutor.setText("");
        } catch (DodawanieException e) {
            e.printStackTrace();
        }
    }

    @FXML
     private void delateBook(){
        int selectIndex = tableKsiazki.getSelectionModel().getFocusedIndex();
        try {
            if(selectIndex < 0) {
                biblioteka.skasujKsiazke(tableKsiazki.getItems().get(selectIndex).getNazwa());
                tableKsiazki.getItems().remove(selectIndex);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(myApp.getPrimaryStage());
                alert.setTitle("Uwaga!");
                alert.setHeaderText("Nie wybrano książki do usunięcia.");
                alert.setContentText("Proszę wybrać ksiażkę z tabeli.");
                alert.showAndWait();
            }
        } catch (UsuwanieException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(myApp.getPrimaryStage());
            alert.setTitle("Uwaga!");
            alert.setHeaderText(e.getMessage());
            alert.setContentText("Proszę najpierw zwrócić książkę, a potem usunąć.");
        }
    }

    @FXML
    private void handleEditBook(){
         Ksiazka selectedBook = tableKsiazki.getSelectionModel().getSelectedItem();
         Ksiazka bookTemporary = selectedBook;
         if(selectedBook != null){
             //myApp.getKsiazkiData().remove(selectedBook);
             boolean okClicked = myApp.showBookEditDialog(selectedBook);
             if(okClicked){
                 myApp.getKsiazkiData().remove(selectedBook);
                 myApp.getKsiazkiData().add(selectedBook);
             }
         }else {
             // Nothing selected.
             Alert alert = new Alert(Alert.AlertType.WARNING);
             alert.initOwner(myApp.getPrimaryStage());
             alert.setTitle("Uwaga");
             alert.setHeaderText("Nie wybrano książki.");
             alert.setContentText("Proszę najpierw wybrać książkę z tabeli.");

             alert.showAndWait();
         }
         bookTemporary = null;
    }



}

