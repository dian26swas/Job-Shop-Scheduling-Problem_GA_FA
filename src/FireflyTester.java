
import java.io.FileNotFoundException;
import java.io.IOException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ASUS
 */
public class FireflyTester {
     public static void main(String[] args) throws FileNotFoundException, IOException {
         AlgoritmaFirefly fa= new AlgoritmaFirefly(1, 0.1, 1);
        Gen[] gener1= new Gen[9];
        gener1[0] = new Gen("O11",10,0,0,1,0,0);
        gener1[1] = new Gen("O21",8,0,0,1,0,0);
        gener1[2] = new Gen("O31",8,0,0,1,0,0);
        gener1[3] = new Gen("O32",11,0,0,2,0,0);
        gener1[4] = new Gen("O12",9,0,0,2,0,0);
        gener1[5] = new Gen("O22",7,0,0,2,0,0);
        gener1[6] = new Gen("O33",10,0,0,3,0,0);
        gener1[7] = new Gen("O23",9,0,0,3,0,0);
        gener1[8] = new Gen("O13",8,0,0,3,0,0);
        
        Gen[] gener2= new Gen[9];
        gener2[0] = new Gen("O11",10,0,0,1,0,0);
        gener2[1] = new Gen("O21",8,0,0,1,0,0);
        gener2[2] = new Gen("O31",8,0,0,1,0,0);
        gener2[3] = new Gen("O12",9,0,0,2,0,0);
        gener2[4] = new Gen("O22",7,0,0,2,0,0);
        gener2[5] = new Gen("O32",11,0,0,2,0,0);
        gener2[6] = new Gen("O13",8,0,0,3,0,0);
        gener2[7] = new Gen("O23",9,0,0,3,0,0);
        gener2[8] = new Gen("O33",10,0,0,3,0,0);
         Individu idv1= new Individu(gener1,0.0,0.0);
         Individu idv2= new Individu(gener2,0.0,0.0);
         System.out.println("OSMS Individu 1 :");
         fa.isiOS(idv1);
         System.out.println("OSMS Individu 2 :");
         fa.isiOS(idv2);
        fa.alphaStepMS(idv1, idv2);
       fa.alphaStepOS(idv1, idv2);
              
         
     }
}
