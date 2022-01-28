/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package localexam;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.sicut.db.Configurator;
import org.sicut.db.Preferences;
import static localexam.Settings.*;

/**
 *
 * @author Sicut
 */
public class LocalExam extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root;
        Scene scene;
        Configurator config = new Configurator();
        config.prepare(PREF_DB_PATH, DB_FOLDER_PATH, getClass().getResource("/localexam/preferences/source.sql").openStream());
        PREF_BUNDLE = new Preferences(PREF_DB_PATH);
        root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        scene = new Scene(root);
        scene.setNodeOrientation( NodeOrientation.RIGHT_TO_LEFT );
        stage.setScene(scene);
        stage.setTitle(APP_NAME + " " + APP_YEAR);
        stage.setMaximized("1".equals(PREF_BUNDLE.get("MAXIMIZED")));
        stage.setOnCloseRequest(evt -> {
            PREF_BUNDLE.commit();
        });
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/localexam/icons/16.png")));
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/localexam/icons/32.png")));
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/localexam/icons/48.png")));
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/localexam/icons/64.png")));
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/localexam/icons/128.png")));
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/localexam/icons/256.png")));
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
