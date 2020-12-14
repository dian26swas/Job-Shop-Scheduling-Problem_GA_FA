/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author ASUS
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label labelAlpha;
    @FXML
    private Label labelInput;
    @FXML
    private Label label;
    @FXML
    private Button menu;
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws Exception {
        Main main= new Main();
        
        Stage stage= new Stage();
        //main.chooseFile(stage);
        FileChooser fil_chooser = new FileChooser();
        File file = fil_chooser.showOpenDialog(stage); 
        if (file != null) { 
                      
                    label.setText(file.getAbsolutePath()  
                                        + "  selected"); 
                } 
        BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
        String[] currentLine = reader.readLine().split("\\s+");
        int job=Integer.parseInt(currentLine[0]);
        int mesin=Integer.parseInt(currentLine[1]);
        //System.out.println(currentLine);
        System.out.println("job "+job+" mesin "+mesin);
        
       
    }
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
