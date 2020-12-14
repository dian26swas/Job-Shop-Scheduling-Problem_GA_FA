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
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
       Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        
        stage.setScene(scene);
        stage.show();
    }

    public void chooseFile(Stage stage) throws Exception
    {
          try { 
  
//        // set title for the stage 
//        stage.setTitle("FileChooser"); 
  
        // create a File chooser 
        FileChooser fil_chooser = new FileChooser(); 
  
//        // create a Label 
//        Label label = new Label("no files selected"); 
//  
//        // create a Button 
//        Button button = new Button("Show open dialog"); 
  
        // create an Event Handler 
        EventHandler<ActionEvent> event =  
        new EventHandler<ActionEvent>() { 
  
            public void handle(ActionEvent e) 
            { 
  
                // get the file selected 
                File file = fil_chooser.showOpenDialog(stage); 
  
//                if (file != null) { 
//                      
//                    label.setText(file.getAbsolutePath()  
//                                        + "  selected"); 
//                } 
            } 
        }; 
  
//        button.setOnAction(event); 
//  
//        // create a Button 
//        Button button1 = new Button("Show save dialog"); 
//  
//        // create an Event Handler 
//        EventHandler<ActionEvent> event1 =  
//         new EventHandler<ActionEvent>() { 
//  
//            public void handle(ActionEvent e) 
//            { 
//  
//                // get the file selected 
//                File file = fil_chooser.showSaveDialog(stage); 
//  
//                if (file != null) { 
//                    label.setText(file.getAbsolutePath()  
//                                        + "  selected"); 
//                } 
//            } 
//        }; 
  
//        button1.setOnAction(event1); 
//  
//        // create a VBox 
//        VBox vbox = new VBox(30, label, button, button1); 
//  
//        // set Alignment 
//        vbox.setAlignment(Pos.CENTER); 
  
        // create a scene 
//        Scene scene = new Scene(vbox, 800, 500); 
//        
//        // set the scene 
//        stage.setScene(scene); 
//  
        stage.show(); 
    } 
  
    catch (Exception e) { 
  
        System.out.println(e.getMessage()); 
    }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
