package graphicInterface;

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
import logic.Library;
import logic.Book;
import logic.Reader;
import graphicInterface.fxml.ControllerReaderPanel;
import graphicInterface.fxml.ControllerBookEditPanel;
import graphicInterface.fxml.ControllerMainPanel;
import graphicInterface.fxml.ControllerRentPanel;
import exceptions.AddingException;

import java.io.IOException;
import java.io.Serializable;

public class MyApp extends Application implements Serializable {


    private Stage primaryStage;
    private BorderPane rootLayout;
    private Library library = new Library();
    private ObservableList<Book> booksData = FXCollections.observableArrayList();
    private ObservableList<Reader> readersData = FXCollections.observableArrayList();
    private FilteredList<Book> filteredDataBook = new FilteredList<>(booksData, p -> true);
    private FilteredList<Reader> filteredDataReader = new FilteredList<>(readersData, p -> true);
    @Override
    public void start(Stage primaryStage) {


        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Library");
        initRootLayout();
        showLibraryOverview();
    }


    public MyApp() throws IOException, ClassNotFoundException {
        try {
            library.addNewBook("kek","kek","kek");
            library.addNewBook("kek1","kek1","kek1");
            library.addNewBook("kek2","kek2","kek2");
            library.addReader("Pan Koala","Z Australii","10.10.2010","PanKoala@gmail.com");
            library.getReaders().get(library.getReaders().size()-1).setImage("koala.jpg");
            booksData.addAll(library.getCatalog().getBooks().values());
            readersData.addAll(library.getReaders());

        } catch (AddingException e) {
            e.printStackTrace();
        }

    }

    public FilteredList<Book> getFilteredDataBook() {
        return filteredDataBook;
    }

    public FilteredList<Reader> getFilteredDataReader() {
        return filteredDataReader;
    }

    public ObservableList<Book> getBooksData() {
        return booksData;
    }

    public ObservableList<Reader> getReadersData() {
        return readersData;
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
            loader.setLocation(MyApp.class.getResource("fxml/MainPanel.fxml"));
            AnchorPane libraryOverView = (AnchorPane) loader.load();

            ControllerMainPanel controllerMainPanel = loader.getController();
            controllerMainPanel.setMyApp(this);
            controllerMainPanel.setBiblioteka(library);
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
     * @param book the person object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showBookEditDialog(Book book) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MyApp.class.getResource("fxml/BookEditPanel.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edytuj Książke");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            ControllerBookEditPanel controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setBook(book);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean showBookRentDialog(Book book) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MyApp.class.getResource("fxml/RentPanel.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Wypożycz Książke");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            ControllerRentPanel controller = loader.getController();
            controller.setDialogStage(dialogStage);

            controller.setBiblioteka(library);
            controller.setBook(book);
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isRentClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean showReaderDialog(Reader reader) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MyApp.class.getResource("fxml/ReaderPanel.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Panel Czytelnika");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            ControllerReaderPanel controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setLibrary(library);
            controller.setReader(reader);


            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isCloseClicked();
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

