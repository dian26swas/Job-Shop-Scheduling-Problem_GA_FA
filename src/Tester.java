
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

//    public static Gen[] arrGen;
//    public static List<Gen> listGen;
    public static Individu[] populasi;

    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
        String lines = reader.readLine();
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
            
            //horiLines.get(count);
            count++;
        }
        ArrayList<Individu> popFit = new ArrayList<Individu>();
        //bangkitkan populasi
        //System.out.println("Jumlah isi Populasi");
        populasi= new Individu[5];
        System.out.println("Generate Populasi :");
        int mk=0;
        Individu fit=null;
        JobShop js= new JobShop( 5, job, mesin);
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
        for (int i = 0; i < 5; i++) {
            
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

            //arrGen[i].getMakespanTemp(); System.out.println("testing");
            Gen[] kromo=js.generateKromosom(arrGen);
            int makespan=js.calcMakespan(kromo);
            double fitnes=js.calcFitness(makespan);           
            Individu individu=new Individu(kromo,fitnes,makespan);
            populasi[i]= individu;
            System.out.println(populasi[i].getFitness());
            
            
        }
        
        Individu id= js.getFittest(populasi);
        popFit.add(id);
        fit = new Individu(id.getKromosom(),id.getFitness(),id.getMakespan());
        System.out.println(id.getFitness());
       
        AlgoritmaGenetik ga= new AlgoritmaGenetik(0.3, 0.8);
        for (int generasi = 0; generasi < 5; generasi++) {
            popFit.add(js.getFittest(populasi));
            System.out.println("Best solution: " + js.getFittest(populasi).getFitness());
            
            //seleksi roulette dipanggil langsung di method cross over
            
            System.out.println("");
            System.out.println("hasil crossover");
            //crossover
            Individu[] popCross = ga.crossover(populasi);
            popCross[0]=popFit.get(popFit.size()-1);
            System.out.println("");
            System.out.println("hasil mutasi");
            // swap mutation
            Individu[] popMut = ga.mutation(popCross);
            popMut[0]=popFit.get(popFit.size()-1);
            Gen[] gener= new Gen[mesin*job];
            for (int i = 0; i < popMut.length; i++) {
                popMut[i].setFitness(0.0);
                popMut[i].setMakespan(0);
                
                for(int j=0;j<popMut[i].getKromosom().length;j++)
                {
                    popMut[i].getKromosom()[j].setMakespanTemp(0);
                    popMut[i].getKromosom()[j].setWaktuMulai(0);
                    for(int k=0;k<popMut[i].getKromosom().length;k++)
                    {
                        if(arrTemp[k].getOperation().equals(popMut[i].getKromosom()[j].getOperation()))
                        {
                            popMut[i].getKromosom()[j].setTime(arrTemp[k].getTime());
                            popMut[i].getKromosom()[j].setNoJob(arrTemp[k].getNoJob());
                            popMut[i].getKromosom()[j].setNoMesin(arrTemp[k].getNoMesin());
                            
                            
                        }
                    }
                    
                    gener[j]= new Gen(popMut[i].getKromosom()[j].getOperation(), popMut[i].getKromosom()[j].getTime(), 
                            -1, -1, popMut[i].getKromosom()[j].getNoMesin(), 0, 0,popMut[i].getKromosom()[j].getNoJob());
                }
                int makespan=js.calcMakespan(gener);
                double fitness=js.calcFitness(makespan);
                popMut[i].setFitness(fitness);
                popMut[i].setMakespan(makespan);
                
            }
           populasi = popMut;
            //masuk algoritma Firefly
            AlgoritmaFirefly fa = new AlgoritmaFirefly(1, 0.1, 1);


            for (int popF = 0; popF < populasi.length - 1; popF++) {
                
                for (int po = popF + 1; po < populasi.length; po++) {
                    if (populasi[popF].getFitness() > populasi[po].getFitness()) {
                        //pergerakan kunang2 intensitas cahayanya lebih kecil ke yang lebih besar
                        populasi[po].setMakespan(0);
                        populasi[po].setFitness(0.0);
                        populasi[po]=fa.alphaStepOS(populasi[po], populasi[popF]);
                        //System.out.println(populasi[po].getMakespan());
                        Gen[] gen= new Gen[job*mesin];
                        for(int i=0;i<populasi[po].getKromosom().length;i++)
                        {
                            for(int j=0;j<populasi[po].getKromosom().length;j++)
                            {
                                if(arrTemp[i].getOperation().equals(populasi[po].getKromosom()[j].getOperation()))
                            {
                                //System.out.println(arrTemp[i].getTime());
                                populasi[po].getKromosom()[j].setTime(arrTemp[i].getTime());
                                populasi[po].getKromosom()[j].setWaktuMulai(0);
                                populasi[po].getKromosom()[j].setNoJob(arrTemp[i].getNoJob());
                                populasi[po].getKromosom()[j].setNoMesin(arrTemp[i].getNoMesin());
                            }
                            }
                            
                            
                            gen[i]=new Gen(populasi[po].getKromosom()[i].getOperation(),
                                    populasi[po].getKromosom()[i].getTime(), -1, -1, 
                                    populasi[po].getKromosom()[i].getNoMesin(), 0, 0,populasi[po].getKromosom()[i].getNoJob());
                           
                        }
                        
                        int makespan=js.calcMakespan(gen);
                        double fitnes=js.calcFitness(makespan);
//                        Individu indiv= new Individu(gen,fitnes,makespan);
//                        populasi[po]= indiv;
                        populasi[po].setMakespan(makespan);
                        populasi[po].setFitness(fitnes);

                    } else if (populasi[popF].getFitness() == populasi[po].getFitness()) {
                        //kunang2 bergerak secara random
                        int max = populasi.length - 1;
                        int rand1 = (int) ((Math.random() * (max - 1)) + 1);
                        int rand2 = (int) ((Math.random() * (max - 1)) + 1);
                        populasi[rand1]=fa.alphaStepOS(populasi[rand1], populasi[rand2]);
                        Gen[] gen= new Gen[job*mesin];
                        for(int i=0;i<populasi[rand1].getKromosom().length;i++)
                        {
                            for(int j=0;j<populasi[rand1].getKromosom().length;j++)
                            {
                                if(arrTemp[i].getOperation().equals(populasi[rand1].getKromosom()[j].getOperation()))
                            {
                                //System.out.println(arrTemp[i].getTime());
                                populasi[po].getKromosom()[j].setTime(arrTemp[i].getTime());
                            }
                            }
                            
                            
                            gen[i]=new Gen(populasi[rand1].getKromosom()[i].getOperation(),
                                    populasi[rand1].getKromosom()[i].getTime(), -1, -1, 
                                    populasi[rand1].getKromosom()[i].getNoMesin(), 0, 0,populasi[rand1].getKromosom()[i].getNoJob());
                           
                        }
                        
                        int makespan=js.calcMakespan(populasi[rand1].getKromosom());
                        double fitnes=js.calcFitness(makespan);
                        populasi[rand1].setMakespan(makespan);
                        populasi[rand1].setFitness(fitnes);
                        System.out.println("kunang-kunang bergerak secara random");
                    }
                }
            }
            
            
        }
         System.out.println("Fittest saat pertama di generate kromosom");
         System.out.println(popFit.get(0).getFitness());
        System.out.println("Fittest :");
        for(int i=1;i<popFit.size();i++)
        {
            System.out.println(popFit.get(i).getFitness());
        }

    }

}
