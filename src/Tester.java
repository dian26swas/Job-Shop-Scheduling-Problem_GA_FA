
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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

    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader("E:/Skripsi_JSP_GA_FA/benchmark.txt"));
        
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
            if(lines!=null)
            {
            currentLine=lines.split(" ");
            }
            horiLines.add(currentLine);
            
            for(int i=0;i<currentLine.length;i++)
            {
                if(i%2==1)
                {
                    arrWaktu[pjgWaktu]=Integer.parseInt(horiLines.get(count)[i]);
                    pjgWaktu+=1;
                    
                }
                else
                {
                    arrMesin[pjgMesin]=Integer.parseInt(horiLines.get(count)[i]);
                    pjgMesin++;
                }
            }
            
            
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
        
        
//        Scanner sc = new Scanner(System.in);
//        System.out.println("Masukan jumlah job :");
//        int jmlJob = sc.nextInt();
//        System.out.println("Masukan jumlah mesin :");
//        int jmlMesin = sc.nextInt();
//        arrGen = new Gen[jmlMesin * jmlJob];
//        System.out.println("Masukan elemen kromosom dan waktu pengerjaan tiap operasi: ");
//
//
//        for (int e = 0; e < arrGen.length; e++) {
//            arrGen[e] = new Gen(sc.next(), sc.nextInt(),-1,-1,sc.nextInt(),0,0);
//        }
        AlgoritmaGenetik ga = new AlgoritmaGenetik( 0.3, 0.8, 0);
        JobShop js=new JobShop(arrGen,5,job,mesin);
        //Gen[] krom = js.generateKromosom();
        //bangkitkan populasi
        populasi = js.initialPop();
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
        int bisa=0;//cek kromosom yang bukan tak terhingga
        for (int generasi = 0; generasi < 20; generasi++) {

                
            for (int i = 0; i < populasi.length; i++) {

                    double fitness=0.0;
                    
                    fitness=ga.calcFitness(js.calcMakespan(arrGen,populasi[i].getKromosom(), mesin));
                    System.out.println("fitness "+fitness);
                    if(fitness>0.001)
                    {
                        bisa+=1;
                    }

            }
            System.out.println("yg bisa:"+bisa);
            bisa=0;
            System.out.println("Best solution: " + ga.getFittest(populasi).getKromosom());
            System.out.println("Hasil roulette wheel :");
            Individu rwRes= ga.rouletteWheel(populasi);
//            while(ga.getFittest(populasi)==ga.rouletteWheel(populasi))
//            {
//                rwRes=ga.rouletteWheel(populasi);
//            }
            for (int j = 0; j < rwRes.getKromosom().length; j++) {
                    System.out.print(rwRes.getKromosom()[j].getOperation() + " ");

                }
            //seleksi roulette
            
            System.out.println("");
            System.out.println("hasil crossover");
            //crossover
            Individu[] popCross = ga.crossover(populasi);
            for (int i = 0; i < popCross.length; i++) {
                System.out.println("fitness" + ga.calcFitness(js.calcMakespan(arrGen, popCross[i].getKromosom(), mesin)));
            }
            System.out.println("");
            System.out.println("hasil mutasi");
            // swap mutation
            Individu[] popMut = ga.mutation(popCross);
            for (int i = 0; i < popMut.length; i++) {
                System.out.println("fitness" + popMut[i].getFitness());
            }
            populasi = popMut;
            for(int pop=0;pop<populasi.length;pop++)
            {
                populasi[pop].setMakespan(js.calcMakespan(arrGen, populasi[pop].getKromosom(), mesin));
                for(int popu=0;popu<populasi[pop].getKromosom().length;popu++)
                {
                    System.out.print(populasi[pop].getKromosom()[popu].getOperation()+" ");
                }
                System.out.println("");
            }
            //Masuk Algoritma Firefly
            // jarak antar kunang-kunang
            
            AlgoritmaFirefly fa= new AlgoritmaFirefly(1, 0.0001, 0.05);
            System.out.println("Jarak yang paling dekat :");
            
            //System.out.println("testmsp:"+idvTest.getMakespan());
            for(int popF=0;popF<populasi.length;popF++)
            {
                for(int po=0;po<populasi.length;po++)
                {
//                    System.out.println("moveTest: "+fa.pergerakan(populasi[popF],idvTest));
//                    System.out.println("move: "+fa.pergerakan(populasi[popF],populasi[po]));
                    if(populasi[popF].getFitness()>populasi[po].getFitness())
                    {
                        System.out.println("move: "+fa.pergerakan(populasi[popF],populasi[po]));
                    }
                    else
                    {
                        System.out.println("tidak ada pergerakan");
                    }
                }
            }
            System.out.println("");
        }
        System.out.println("total bisa"+bisa);

    }

}
