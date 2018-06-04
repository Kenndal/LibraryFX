package graphicInterface.fxml;

import graphicInterface.MyApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class TopKekController {

    @FXML
    private Button topKek;

    @FXML
    private ImageView image;


    Image image2;
    Stage dialogStage;
    private Boolean isOkClicked = false;
    private MyApp myApp;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setMyApp(MyApp myApp) {
        this.myApp = myApp;
        image.setImage(myApp.getTopKekImage());
    }

    @FXML
    public Boolean isOkClicked() {
        dialogStage.close();
        return isOkClicked;
    }
}
