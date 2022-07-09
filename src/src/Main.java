package src;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.Optional;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("EWB-UMass Auction Checkout System");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.setResizable(false);
        primaryStage.show();

        //Thank You StackOverflow for this little addition
        primaryStage.setOnCloseRequest(event -> {

            if (!Controller.guests.isEmpty() || !Controller.items.isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.initOwner(primaryStage);
                alert.initModality(Modality.APPLICATION_MODAL);

                alert.setHeaderText("Exit Without Saving?");
                alert.setContentText("If You Exit Now, Your Current Data Will Be Lost. \nContinue?");

                alert.getDialogPane().getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> optional = alert.showAndWait();

                if (optional.isPresent() && optional.get() == ButtonType.YES) {
                    return;
                }

                event.consume();
            }
        });


    }


    public static void main(String[] args) throws FileNotFoundException {
        launch(args);
    }
}
