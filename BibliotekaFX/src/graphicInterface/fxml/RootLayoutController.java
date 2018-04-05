package graphicInterface.fxml;

import java.io.File;
import java.io.IOException;

import graphicInterface.MyApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;


public class RootLayoutController {
    private MyApp myApp;

    public void setMyApp(MyApp myApp) {
        this.myApp = myApp;
    }

    @FXML
    private void handleSave(){
        try {
            myApp.getLibrary().saveCatalogToFile("biblioteczka.bin");
            myApp.getLibrary().saveReadersToFile("czytelnicy.bin");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void handleAbout(){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Biblioteka");
        alert.setHeaderText("Informacje");
        alert.setContentText("Jedno wielkie: KEK\n Bo przecież kochamy to KEK <3 \n Jak nie bedzie z tego 5.0 to będę smutny :(");

        alert.showAndWait();
    }


    @FXML
    private void handleExit(){
        handleSave();
        System.exit(0);
    }
}
