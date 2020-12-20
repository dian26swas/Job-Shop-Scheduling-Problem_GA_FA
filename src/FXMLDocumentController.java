/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
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
    private TextField label;
    @FXML
    private Button menu;
    @FXML
    private Button btnResult;
    @FXML
    private TextField jmlGenerasi;
    @FXML
    private TextField jmlPopulasi;
    @FXML
    private TextField nilaiMr;
    @FXML
    private TextField nilaiCr;
    @FXML
    private TextField nilaiAlpha;
    @FXML
    private TextField nilaiBeta;
    @FXML
    private TextField nilaiGamma;
    @FXML
    private TextField result;
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws Exception {
       
        Stage stage= new Stage();
        //main.chooseFile(stage);
        FileChooser fil_chooser = new FileChooser();
        File file = fil_chooser.showOpenDialog(stage); 
        if (file != null) { 
                      
                    label.setText(file.getAbsolutePath()  
                                        + "  selected"); 
                } 
        BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
        
        String lines= reader.readLine();
        String[] currentLine = lines.split("\\s+");
        int job=Integer.parseInt(currentLine[0]);
        int mesin=Integer.parseInt(currentLine[1]);
        ArrayList<String[]> horiLines= new ArrayList<String[]>();
        //System.out.println(currentLine);
        System.out.println("job "+job+" mesin "+mesin);
        int count=0;
        int[] arrMesin= new int[mesin*job];
        int[] arrWaktu= new int[mesin*job];
        int pjgMesin=0;
        int pjgWaktu=0;
        for(int j=0;j<job;j++)
        {
            lines=reader.readLine();
            currentLine=lines.split("\\s+");
            horiLines.add(currentLine);
            for(int i=0;i<horiLines.get(count).length;i++)
            {
                if(i%2==1)
                {
                    
                    arrWaktu[pjgWaktu]=Integer.parseInt(horiLines.get(count)[i]);
                    pjgWaktu++;
                    //System.out.println(arrMesin[pjgMesin]);
                }
                else
                {
                    arrMesin[pjgMesin]=Integer.parseInt(horiLines.get(count)[i]);
                    pjgMesin++;
                }
                System.out.print(horiLines.get(count)[i]+" ");
            }
            System.out.println("");
            //horiLines.get(count);
            count++;
            
        }

        Gen[] arrGen= new Gen[job*mesin];
        String[][] operation=new String[job][mesin];
        int counter=0;
        for(int j=1;j<=job;j++)
        {
            for(int o=1;o<=mesin;o++)
            {
                operation[j-1][o-1]="O"+j+o+"";
                arrGen[counter]=new Gen(operation[j-1][o-1], arrWaktu[counter], -1, -1, arrMesin[counter], 0, 0);
                counter++;
            }
        }
        for(int g=0;g<arrGen.length;g++)
        {
            
            System.out.println(arrGen[g].getOperation()+" "+ arrGen[g].getTime()+ " "+arrGen[g].getNoMesin());
        }
    }
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
