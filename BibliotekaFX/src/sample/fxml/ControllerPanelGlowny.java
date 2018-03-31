package sample.fxml;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import logika.Biblioteka;
import logika.Czytelnik;
import logika.Ksiazka;
import wyjatki.DodawanieException;
import sample.MyApp;
import wyjatki.UsuwanieException;

import java.util.Optional;


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
    private TextField textFieldMail;

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

    FilteredList<Ksiazka> filteredDataBook;
    FilteredList<Czytelnik> filteredDataReader;

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

        // 2. Set the filter Predicate whenever the filter changes.
        textFieldSzukajKsiazki.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredDataBook.setPredicate(ksiazka -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (ksiazka.getNazwa().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches title.
                } else if (ksiazka.getAutor().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches author.
                }else if (ksiazka.getGatunek().toLowerCase().contains(lowerCaseFilter)){
                    return true; // Filter type author.
                }else if (ksiazka.getIndeks_ksiazki().toLowerCase().contains(lowerCaseFilter)){
                    return true; //Filter index author.
                }
                return false; // Does not match.
            });
        });


        textFieldSzukajCzytelnika.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredDataReader.setPredicate(czytelnik -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (czytelnik.getImie().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (czytelnik.getNazwisko().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }else if (czytelnik.getIndeks_czytelnika().toLowerCase().contains(lowerCaseFilter)){
                    return true; // Filter matches index.
                }else
                return false; // Does not match.
            });
        });


    }

    public void setMyApp(MyApp myApp) {
        this.myApp = myApp;
        tableKsiazki.setItems(myApp.getKsiazkiData());
        tableCzytelnicy.setItems(myApp.getCzytelnicyData());
        filteredDataBook = myApp.getFilteredDataBook();
        filteredDataReader = myApp.getFilteredDataReader();

    }

    public void setBiblioteka(Biblioteka biblioteka) {
        this.biblioteka = biblioteka;
    }

    @FXML
     private void delateBook(){
        int selectIndex = tableKsiazki.getSelectionModel().getFocusedIndex();
        Ksiazka selectedBook = tableKsiazki.getSelectionModel().getSelectedItem();
        try {
            if(selectedBook != null) {
                biblioteka.skasujKsiazke(tableKsiazki.getItems().get(selectIndex).getNazwa());
                myApp.getKsiazkiData().remove(selectedBook);
            }
            else {
                alertToSelect();
            }
        } catch (UsuwanieException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(myApp.getPrimaryStage());
            alert.setTitle("Uwaga!");
            alert.setHeaderText(e.getMessage());
            alert.setContentText("Proszę najpierw zwrócić książkę, a potem usunąć.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleEditBook(){
         Ksiazka selectedBook = tableKsiazki.getSelectionModel().getSelectedItem();
         if(selectedBook != null){
             boolean okClicked = myApp.showBookEditDialog(selectedBook);
             if(okClicked){
                 myApp.getKsiazkiData().remove(selectedBook);
                 myApp.getKsiazkiData().add(selectedBook);
                 myApp.getKsiazkiData().sorted();
             }
         }else {
                alertToSelect();
         }
    }

    @FXML
    private void handleWypozyczenieKsiazki(){
        Ksiazka selectedBook = tableKsiazki.getSelectionModel().getSelectedItem();
        if(selectedBook != null){
            boolean okClicked = myApp.showBookWypozyczenieDialog(selectedBook);
            if(okClicked){
                refreshTable();
            }
        }else {
            alertToSelect();
        }
    }

    @FXML
    private void handleAddBook(){
        try {
            biblioteka.dodajKsiazkeDoKatalogu(textFieldTytul.getText(),textFieldGatunek.getText(),textFieldAutor.getText());
            myApp.getKsiazkiData().add(biblioteka.getKatalog().getKsiazki().get(textFieldTytul.getText()));
            textFieldTytul.clear();
            textFieldGatunek.clear();
            textFieldAutor.clear();
        } catch (DodawanieException e) {
            alertToFillAllTextFields(e.getMessage());
        }
    }

    @FXML
    private void handleAddReader(){
        try {
            biblioteka.dodaj_czytelnika(textFieldImie.getText(),textFieldNazwisko.getText(),textFieldData.getText(),textFieldMail.getText());
            myApp.getCzytelnicyData().add(biblioteka.getCzytelnicy().get(biblioteka.getCzytelnicy().size()-1));
            textFieldImie.clear();
            textFieldNazwisko.clear();
            textFieldData.clear();
            textFieldMail.clear();
        } catch (DodawanieException e) {
            alertToFillAllTextFields(e.getMessage());
        }
    }


    @FXML
    private void delateReader(){
        int selectIndex = tableCzytelnicy.getSelectionModel().getFocusedIndex();
        Czytelnik selectedReader = tableCzytelnicy.getSelectionModel().getSelectedItem();
        if(selectedReader != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(myApp.getPrimaryStage());
            alert.setTitle("Uwaga!");
            alert.setHeaderText("Czy jesteś pewien ten operacji?");
            alert.setContentText("Proszę upewnić się, że czytelnik zwrócił wszystkie książki");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK) {
                biblioteka.zwrotWszystkichKsiazek(selectedReader.getIndeks_czytelnika());
                biblioteka.usun_czytelnika(selectedReader.getIndeks_czytelnika());
                myApp.getCzytelnicyData().remove(selectedReader);
                ObservableList<Ksiazka> ksiazkiData = myApp.getKsiazkiData().sorted();
                myApp.getKsiazkiData().removeAll();
                myApp.getKsiazkiData().setAll(ksiazkiData);
            }
        }
        else {
            alertToSelect();
        }
    }



    @FXML
    private void handleReader() {
        Czytelnik selectedReader = tableCzytelnicy.getSelectionModel().getSelectedItem();
        if(selectedReader != null) {
            boolean zamknijClicked = myApp.showCzytlenikDialog(selectedReader);
            if(zamknijClicked){
                refreshTable();
            }
        }else alertToSelect();
    }

    @FXML
    private void searchingBook(){
        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Ksiazka> sortedData = new SortedList<>(filteredDataBook);
        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(tableKsiazki.comparatorProperty());
        // 5. Add sorted (and filtered) data to the table.
        tableKsiazki.setItems(sortedData);
    }

    @FXML
    private void searchingReader(){
        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Czytelnik> sortedData = new SortedList<>(filteredDataReader);
        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(tableCzytelnicy.comparatorProperty());
        // 5. Add sorted (and filtered) data to the table.
        tableCzytelnicy.setItems(sortedData);
    }



    private void refreshTable(){
        ObservableList<Ksiazka> ksiazkiData = myApp.getKsiazkiData().sorted();
        myApp.getKsiazkiData().removeAll();
        myApp.getKsiazkiData().setAll(ksiazkiData);
        ObservableList<Czytelnik> czytelnicyData = myApp.getCzytelnicyData().sorted();
        myApp.getCzytelnicyData().removeAll();
        myApp.getCzytelnicyData().setAll(czytelnicyData);
    }

    private void alertToSelect(){
        // Nothing selected.
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(myApp.getPrimaryStage());
        alert.setTitle("Uwaga");
        alert.setHeaderText("Nie wybrano książki.");
        alert.setContentText("Proszę najpierw wybrać książkę z tabeli.");
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

