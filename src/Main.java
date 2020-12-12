/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 *
 * @author ASUS
 */
public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        //Creating a menu
      Menu fileMenu = new Menu("File");
      //Creating menu Items
      MenuItem item = new MenuItem("Open Image");
      fileMenu.getItems().addAll(item);
      //Creating a File chooser
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Open Image");
      fileChooser.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"));
      //Adding action on the menu item
      item.setOnAction(new EventHandler<ActionEvent>() {
         @Override
         public void handle(ActionEvent event) {
            //Opening a dialog box
            fileChooser.showOpenDialog(stage);
      }});
      //Creating a menu bar and adding menu to it.
      MenuBar menuBar = new MenuBar(fileMenu);
      menuBar.setTranslateX(3);
      menuBar.setTranslateY(3);
      //Setting the stage
      Group root = new Group(menuBar);
      Scene scene = new Scene(root, 595, 355, Color.BEIGE);
      stage.setTitle("File Chooser Example");
      stage.setScene(scene);
      stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
