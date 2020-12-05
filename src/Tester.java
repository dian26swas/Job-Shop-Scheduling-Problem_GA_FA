
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ASUS
 */
public class Tester {

    public static Gen[] arrGen;
    public static List<Gen> listGen;
    public static Individu[] populasi;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Masukan jumlah job :");
        int jmlJob = sc.nextInt();
        System.out.println("Masukan jumlah mesin :");
        int jmlMesin = sc.nextInt();
        arrGen = new Gen[jmlMesin * jmlJob];
        System.out.println("Masukan elemen kromosom dan waktu pengerjaan tiap operasi: ");

//urutan pengerjaan operasi di setiapp job
//       

        for (int e = 0; e < arrGen.length; e++) {
            arrGen[e] = new Gen(sc.next(), sc.nextInt(),-1,-1,sc.nextInt(),0,0);
        }
        AlgoritmaGenetik ga = new AlgoritmaGenetik(50, 0.3, 0.8, 0);
//       
        Gen[] krom = ga.generateKromosom(arrGen);
        //bangkitkan populasi
        populasi = ga.initialPop(krom);
        System.out.println("Generate Population :");
        for(int i=0;i<populasi.length;i++)
        {
            for(int j=0;j<populasi[i].getKromosom().length;j++)
            {
                System.out.print(populasi[i].getKromosom()[j].getOperation()+" ");
            }
            System.out.println();
        }
        //hitung makespan
        //contoh kromosom yang terbentuk
//        Gen[] gener= new Gen[arrGen.length];
//        gener[0] = new Gen("O11",10,0,0,1,0);
//        gener[1] = new Gen("O21",8,0,0,1,0);
//        gener[2] = new Gen("O31",8,0,0,1,0);
//        gener[3] = new Gen("O32",11,0,0,2,0);
//        gener[4] = new Gen("O12",9,0,0,2,0);
//        gener[5] = new Gen("O22",7,0,0,2,0);
//        gener[6] = new Gen("O33",10,0,0,3,0);
//        gener[7] = new Gen("O23",9,0,0,3,0);
//        gener[8] = new Gen("O13",8,0,0,3,0);
        
//        Gen[] gener= new Gen[arrGen.length];
//        gener[0] = new Gen("O11",10,0,0,1,0);
//        gener[1] = new Gen("O21",8,0,0,1,0);
//        gener[2] = new Gen("O31",8,0,0,1,0);
//        gener[3] = new Gen("O12",9,0,0,2,0);
//        gener[4] = new Gen("O22",7,0,0,2,0);
//        gener[5] = new Gen("O32",11,0,0,2,0);
//        gener[6] = new Gen("O13",8,0,0,3,0);
//        gener[7] = new Gen("O23",9,0,0,3,0);
//        gener[8] = new Gen("O33",10,0,0,3,0);
        
//        Gen[] gener= new Gen[arrGen.length];
//        gener[0] = new Gen("O33",10,0,0,3,0);
//        gener[1] = new Gen("O11",10,0,0,1,0);
//        gener[2] = new Gen("O21",8,0,0,1,0);
//        gener[3] = new Gen("O23",9,0,0,3,0);
//        gener[4] = new Gen("O12",9,0,0,2,0);
//        gener[5] = new Gen("O22",7,0,0,2,0);
//        gener[6] = new Gen("O31",8,0,0,1,0);
//        gener[7] = new Gen("O32",11,0,0,2,0);
//        gener[8] = new Gen("O13",8,0,0,3,0);

//        Gen[] gener= new Gen[arrGen.length];
//        gener[0] = new Gen("O11",10,0,0,1,0);
//        gener[1] = new Gen("O23",9,0,0,3,0);
//        gener[2] = new Gen("O21",8,0,0,1,0);
//        gener[3] = new Gen("O31",8,0,0,1,0);
//        gener[4] = new Gen("O22",7,0,0,2,0);
//        gener[5] = new Gen("O13",8,0,0,3,0);
//        gener[6] = new Gen("O33",10,0,0,3,0);
//        gener[7] = new Gen("O32",11,0,0,2,0);
//        gener[8] = new Gen("O12",9,0,0,2,0);
      
//        Gen[] gener= new Gen[arrGen.length];
//        gener[0] = new Gen("O33",10,0,0,3,0);
//        gener[1] = new Gen("O11",10,0,0,1,0);
//        gener[2] = new Gen("O32",11,0,0,2,0);
//        gener[3] = new Gen("O21",8,0,0,1,0);
//        gener[4] = new Gen("O12",9,0,0,2,0);
//        gener[5] = new Gen("O31",8,0,0,1,0);
//        gener[6] = new Gen("O23",9,0,0,3,0);
//        gener[7] = new Gen("O22",7,0,0,2,0);
//        gener[8] = new Gen("O13",8,0,0,3,0);
//        populasi[0].setFitness(0.0);
//       populasi[0].setKromosom(gener);

//        Gen[] gener= new Gen[arrGen.length];
//        gener[0] = new Gen("O23",9,0,0,3,0);
//        gener[1] = new Gen("O11",10,0,0,1,0);
//        gener[2] = new Gen("O21",8,0,0,1,0);
//        gener[3] = new Gen("O12",9,0,0,2,0);
//        gener[4] = new Gen("O22",7,0,0,2,0);
//        gener[5] = new Gen("O13",8,0,0,3,0);
//        gener[6] = new Gen("O33",10,0,0,3,0);
//        gener[7] = new Gen("O31",8,0,0,1,0);
//        gener[8] = new Gen("O32",11,0,0,2,0);

//        Gen[] gener= new Gen[arrGen.length];
//        gener[0] = new Gen("O23",9,0,0,3,0,0);
//        gener[1] = new Gen("O33",10,0,0,3,0,0);
//        gener[2] = new Gen("O21",8,0,0,1,0,0);
//        gener[3] = new Gen("O12",9,0,0,2,0,0);
//        gener[4] = new Gen("O32",11,0,0,2,0,0);
//        gener[5] = new Gen("O31",8,0,0,1,0,0);
//        gener[6] = new Gen("O11",10,0,0,1,0,0);
//        gener[7] = new Gen("O13",8,0,0,3,0,0);
//        gener[8] = new Gen("O22",7,0,0,2,0,0);

//        Gen[] gener= new Gen[arrGen.length];
//        gener[0] = new Gen("O21",8,0,0,1,0,0);
//        gener[1] = new Gen("O11",10,0,0,1,0,0);
//        gener[2] = new Gen("O22",7,0,0,2,0,0);
//        gener[3] = new Gen("O23",9,0,0,3,0,0);
//        gener[4] = new Gen("O33",10,0,0,3,0,0);
//        gener[5] = new Gen("O12",9,0,0,2,0,0);
//        gener[6] = new Gen("O32",11,0,0,2,0,0);
//        gener[7] = new Gen("O13",8,0,0,3,0,0);
//        gener[8] = new Gen("O31",8,0,0,1,0,0);
        for (int generasi = 0; generasi < 1; generasi++) {

                int bisa=0;
            for (int i = 0; i < populasi.length; i++) {
                

                //if (populasi[0].getFitness() == 0.0) {
                                                                                //populasi[i].getKromosom()
                    //populasi[0].setFitness(
                    double fitness=0.0;
                    
                    fitness=ga.calcFitness(ga.calcMakespan(arrGen,populasi[i].getKromosom(), jmlMesin));
                    System.out.println("fitness "+fitness);
                    if(fitness<4.0)
                    {
                        bisa+=1;
                    }
                            //populasi[0].getFitness());
                //}

            }
            System.out.println("yg bisa:"+bisa);
//            System.out.println("Best solution: " + ga.getFittest(populasi));
//            System.out.println("hasil roulette wheel :");
//            Individu rwRes= ga.rouletteWheel(populasi);
//            while(ga.getFittest(populasi)==ga.rouletteWheel(populasi))
//            {
//                rwRes=ga.rouletteWheel(populasi);
//            }
//            for (int j = 0; j < rwRes.getKromosom().length; j++) {
//                    System.out.print(rwRes.getKromosom()[j].getOperation() + " ");
//
//                }
//            //seleksi roulette
//            
//            System.out.println("");
//            System.out.println("hasil crossover");
//            //crossover
//            Individu[] popCross = ga.crossover(populasi);
//            for (int i = 0; i < popCross.length; i++) {
//                System.out.println("fitness" + ga.calcFitness(ga.calcMakespan(arrGen, popCross[i].getKromosom(), jmlMesin)));
//            }
//            System.out.println("");
//            System.out.println("hasil mutasi");
//            //mutation
//            Individu[] popMut = ga.mutation(popCross);
//            for (int i = 0; i < popMut.length; i++) {
//                System.out.println("fitness" + popMut[i].getFitness());
//            }
//            populasi = popMut;
        }

    }

}
