package graphicInterface.fxml;


import java.io.IOException;

import graphicInterface.MyApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;



public class RootLayoutController {
    private MyApp myApp;

    public void setMyApp(MyApp myApp) {
        this.myApp = myApp;
    }

    @FXML
    private void handleSave(){
        try {
            myApp.getLibrary().saveCatalogToFile();
            myApp.getLibrary().saveReadersToFile();
            Alert alert = new Alert(AlertType.INFORMATION);
            ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(myApp.getIconImage());
            alert.setTitle("Biblioteka");
            alert.setHeaderText("Zapisano.");
            alert.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void handleAbout(){
        Alert alert = new Alert(AlertType.INFORMATION);
        ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(myApp.getIconImage());
        alert.setTitle("Biblioteka");
        alert.setHeaderText("Informacje");
        alert.setContentText("Jedno wielkie: KEK \nBo przecież kochamy to KEK <3 \nJak nie bedzie z tego 5.0 to będę smutny :(" +
                "\n Autorzy: Jakub Przybyło i Filip Woźniak");

        alert.setHeight(200);
        alert.setWidth(400);
        alert.showAndWait();
    }


    @FXML
    private void handleExit(){
        handleSave();
        System.exit(0);
    }
}
