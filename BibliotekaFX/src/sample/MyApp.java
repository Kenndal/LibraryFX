package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logika.Biblioteka;
import logika.Czytelnik;
import logika.Ksiazka;
import sample.fxml.ControllerCzytelnikPanel;
import sample.fxml.ControllerKsiazkaEditPanel;
import sample.fxml.ControllerPanelGlowny;
import sample.fxml.ControllerWypozyczeniePanel;
import wyjatki.DodawanieException;

import java.io.IOException;
import java.io.Serializable;

public class MyApp extends Application implements Serializable {


    private Stage primaryStage;
    private BorderPane rootLayout;
    private Biblioteka biblioteka = new Biblioteka();
    private ObservableList<Ksiazka> ksiazkiData = FXCollections.observableArrayList();
    private ObservableList<Czytelnik> czytelnicyData = FXCollections.observableArrayList();
    private FilteredList<Ksiazka> filteredDataBook = new FilteredList<>(ksiazkiData, p -> true);
    private FilteredList<Czytelnik> filteredDataReader = new FilteredList<>(czytelnicyData, p -> true);
    @Override
    public void start(Stage primaryStage) {


        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Biblioteka");
        initRootLayout();
        showLibraryOverview();
    }


    public MyApp() throws IOException, ClassNotFoundException {
        try {
            biblioteka.dodajKsiazkeDoKatalogu("kek","kek","kek");
            biblioteka.dodajKsiazkeDoKatalogu("kek1","kek1","kek1");
            biblioteka.dodajKsiazkeDoKatalogu("kek2","kek2","kek2");
            biblioteka.dodaj_czytelnika("Pan Koala","Z Australii","10.10.2010","PanKoala@gmail.com");
            biblioteka.getCzytelnicy().get(biblioteka.getCzytelnicy().size()-1).setZdjecie("koala.jpg");
            ksiazkiData.addAll(biblioteka.getKatalog().getKsiazki().values());
            czytelnicyData.addAll(biblioteka.getCzytelnicy());

        } catch (DodawanieException e) {
            e.printStackTrace();
        }

    }

    public FilteredList<Ksiazka> getFilteredDataBook() {
        return filteredDataBook;
    }

    public FilteredList<Czytelnik> getFilteredDataReader() {
        return filteredDataReader;
    }

    public ObservableList<Ksiazka> getKsiazkiData() {
        return ksiazkiData;
    }

    public ObservableList<Czytelnik> getCzytelnicyData() {
        return czytelnicyData;
    }

    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MyApp.class.getResource("fxml/RootLayout.fxml"));
            rootLayout =(BorderPane) loader.load();
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showLibraryOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MyApp.class.getResource("fxml/GlownyPanel.fxml"));
            AnchorPane libraryOverView = (AnchorPane) loader.load();

            ControllerPanelGlowny controllerPanelGlowny = loader.getController();
            controllerPanelGlowny.setMyApp(this);
            controllerPanelGlowny.setBiblioteka(biblioteka);
            // Set person overview into the center of root layout.
            rootLayout.setCenter(libraryOverView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens a dialog to edit details for the specified person. If the user
     * clicks OK, the changes are saved into the provided person object and true
     * is returned.
     *
     * @param ksiazka the person object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showBookEditDialog(Ksiazka ksiazka) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MyApp.class.getResource("fxml/KsiazkaEditPanel.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edytuj Książke");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            ControllerKsiazkaEditPanel controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setKsiazka(ksiazka);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean showBookWypozyczenieDialog(Ksiazka ksiazka) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MyApp.class.getResource("fxml/WypozyczeniePanel.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Wypożycz Książke");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            ControllerWypozyczeniePanel controller = loader.getController();
            controller.setDialogStage(dialogStage);

            controller.setBiblioteka(biblioteka);
            controller.setKsiazka(ksiazka);
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isWypozyczClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean showCzytlenikDialog(Czytelnik czytelnik) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MyApp.class.getResource("fxml/CzytelnikPanel.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Panel Czytelnika");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            ControllerCzytelnikPanel controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setBiblioteka(biblioteka);
            controller.setCzytelnik(czytelnik);


            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isZamknijClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }






    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

