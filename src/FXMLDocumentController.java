/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
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
import javafx.scene.control.TextArea;
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
    private TextField label;
    @FXML
    private Button choose;
    @FXML
    private Button btnRes;
    @FXML
    private Button btnReset;
    
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
    private TextArea resultGA;
    @FXML
    private TextArea resultFA;
    @FXML
    private TextArea resultGAFA;
    
    private Individu[] populasi;
    private BufferedReader reader;
    
    @FXML
    public void buttonFileChooser(ActionEvent event) throws FileNotFoundException
    {
        Stage stage= new Stage();
        //main.chooseFile(stage);
        FileChooser fil_chooser = new FileChooser();
        File file = fil_chooser.showOpenDialog(stage); 
        if (file != null) { 
                      
                    label.setText(file.getAbsolutePath()  
                                        + "  selected"); 
                } 
        this.reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
    }
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws Exception {
 
        String lines = this.reader.readLine();
        String[] currentLine = lines.split("\\s+");
        int job = Integer.parseInt(currentLine[0]);
        int mesin = Integer.parseInt(currentLine[1]);
        ArrayList<String[]> horiLines = new ArrayList<String[]>();

        System.out.println("job " + job + " mesin " + mesin);
        int count = 0;
        int[] arrMesin = new int[mesin * job];
        int[] arrWaktu = new int[mesin * job];
        int[] arrJob = new int[mesin * job];
        
        int pjgMesin = 0;
        int pjgWaktu = 0;
        int pjgJob=0;
        for (int j = 0; j < job; j++) {
            lines = reader.readLine();
            if (lines != null) {
                currentLine = lines.split(" ");
            }
            horiLines.add(currentLine);

            for (int i = 0; i < currentLine.length; i++) {
                if (i % 2 == 1) {
                    arrWaktu[pjgWaktu] = Integer.parseInt(horiLines.get(count)[i]);
                    pjgWaktu += 1;
                    arrJob[pjgJob]=j;
                    pjgJob++;
                } else {
                    arrMesin[pjgMesin] = Integer.parseInt(horiLines.get(count)[i]);
                    pjgMesin++;
                }
                
            }

            count++;
        }
        ArrayList<Individu> popFit = new ArrayList<Individu>();
        //bangkitkan populasi
        int jmlPop=Integer.parseInt(this.jmlPopulasi.getText());
        populasi= new Individu[jmlPop];
        System.out.println("Generate Populasi :");
        
        
        JobShop js= new JobShop(jmlPop, job, mesin);
        Gen[] arrGen = new Gen[job * mesin];
        Gen[] arrTemp = new Gen[job * mesin];
        
        String[][] op= new String[job][mesin];
            int counter = 0;
            for (int j = 1; j <= job; j++) {
                for (int o = 1; o <= mesin; o++) {
                    op[j - 1][o - 1] = "O" + j + o + "";
                    
                    arrTemp[counter] = new Gen(op[j - 1][o - 1], arrWaktu[counter], -1, -1, arrMesin[counter], 0, 0,arrJob[counter]);
                    arrTemp[counter].setNoJob(j);
                    counter++;
                }
            }
        for (int i = 0; i < jmlPop; i++) {
            
            String[][] operation = new String[job][mesin];
            counter = 0;
            for (int j = 1; j <= job; j++) {
                for (int o = 1; o <= mesin; o++) {
                    operation[j - 1][o - 1] = "O" + j + o + "";
                    arrGen[counter] = new Gen(operation[j - 1][o - 1], arrWaktu[counter], -1, -1, arrMesin[counter], 0, 0,arrJob[counter]);
                    arrGen[counter].setNoJob(j);
                    counter++;
                }
            }

            Gen[] kromo=js.generateKromosom(arrGen);
            int makespan=js.calcMakespan(kromo);
            double fitnes=js.calcFitness(makespan);           
            Individu individu=new Individu(kromo,fitnes,makespan);
            populasi[i]= individu;
            System.out.println(populasi[i].getFitness());
            
            
        }
        
        Individu id= js.getFittest(populasi);
        popFit.add(id);
        
        System.out.println(id.getFitness());
        double mutationRate=Double.parseDouble(this.nilaiMr.getText());
        double crossoverRate=Double.parseDouble(this.nilaiCr.getText());
            AlgoritmaGenetik ga= new AlgoritmaGenetik(mutationRate
                    ,crossoverRate);
            int generasi=Integer.parseInt(this.jmlGenerasi.getText());
        Individu[] hasilGA=ga.geneticStep(populasi, arrTemp, generasi, js, popFit);
        Individu bestGA= js.getFittest(hasilGA);
        String resAll="";
        for(int i=0;i<hasilGA.length;i++)
        {
            resAll+="Generasi ke-"+i+"\n";
            for(int j=0;j<hasilGA[i].getKromosom().length;j++)
            {
                resAll+=hasilGA[i].getKromosom()[j].getOperation()+" ";
            }
            resAll+="\n"+hasilGA[i].getFitness()+""+"\n";  
        }
        resAll+="BEST :"+"\n";
        for(int j=0;j<bestGA.getKromosom().length;j++)
            {
                resAll+=bestGA.getKromosom()[j].getOperation()+" ";
            }
            resAll+="\n"+bestGA.getFitness()+""+"\n";  
       
        this.resultGA.setText(resAll);
        //FA
        double beta= Double.parseDouble(this.nilaiBeta.getText());
        double gamma= Double.parseDouble(this.nilaiGamma.getText());
        double alpha= Double.parseDouble(this.nilaiAlpha.getText());
        popFit = new ArrayList<Individu>();
        AlgoritmaFirefly fa= new AlgoritmaFirefly(beta,gamma ,alpha );
        
        Individu[] hasilFA=fa.fireflyStep(populasi, arrTemp, generasi, js, popFit);
        Individu bestFA= js.getFittest(hasilFA);
        String resFA="";
        for(int i=0;i<hasilFA.length;i++)
        {
            resFA+="Generasi ke-"+i+"\n";
            for(int j=0;j<hasilFA[i].getKromosom().length;j++)
            {
                resFA+=hasilFA[i].getKromosom()[j].getOperation()+" ";
            }
            resFA+="\n"+hasilFA[i].getFitness()+""+"\n";  
        }
        resFA+="BEST :"+"\n";
        for(int j=0;j<bestFA.getKromosom().length;j++)
            {
                resFA+=bestFA.getKromosom()[j].getOperation()+" ";
            }
            resFA+="\n"+bestFA.getFitness()+""+"\n";  
       
        this.resultFA.setText(resFA);
        //GAFA
        popFit = new ArrayList<Individu>();
        GeneticFirefly gf = new GeneticFirefly(mutationRate,crossoverRate,beta,gamma,alpha);
        Individu[] hasilGAFA=gf.gafaStep(populasi, arrTemp, generasi, js, popFit);
        String resGAFA="";
        Individu best=js.getFittest(hasilGAFA);
        for(int i=0;i<hasilGAFA.length;i++)
        {
            resGAFA+="Generasi ke-"+i+"\n";
            for(int j=0;j<hasilGAFA[i].getKromosom().length;j++)
            {
                resGAFA+=hasilGAFA[i].getKromosom()[j].getOperation()+" ";
            }
            resGAFA+="\n"+hasilGAFA[i].getFitness()+""+"\n";  
        }
        resGAFA+="BEST :"+"\n";
        for(int j=0;j<best.getKromosom().length;j++)
            {
                resGAFA+=best.getKromosom()[j].getOperation()+" ";
            }
            resGAFA+="\n"+best.getFitness()+""+"\n";  
        this.resultGAFA.setText(resGAFA);
        
    }
   
    @FXML
    public void actionPerformed(ActionEvent ae) throws Exception {

        Stage stage= new Stage();
        
        Main main= new Main();
        main.status=true;
        //main.stage.close();
        main.start(stage);
   }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
