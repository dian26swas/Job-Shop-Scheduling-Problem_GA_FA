
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ASUS
 */
public class GeneticTester {
     public static Gen[] arrGen;
    public static List<Gen> listGen;
    public static Individu[] populasi;

    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
        
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
        int[] arrJob= new int[mesin*job];
        int pjgMesin=0;
        int pjgWaktu=0;
        int pjgJob=0;
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
                    arrJob[pjgJob]=j;
                    pjgJob++;
                    
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

        ArrayList<Individu> popFit = new ArrayList<Individu>();
        //bangkitkan populasi
        //System.out.println("Jumlah isi Populasi");
        populasi= new Individu[5];
        System.out.println("Generate Populasi :");
        
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

            //arrGen[i].getMakespanTemp();
           
            Gen[] kromo=js.generateKromosom(arrGen);
            int makespan=js.calcMakespan(kromo);
            double fitnes=js.calcFitness(makespan);
            Individu individu=new Individu(kromo,fitnes,makespan);
            populasi[i]= individu;
            System.out.println(populasi[i].getFitness());
            
            
        }
        Individu id= js.getFittest(populasi);
        popFit.add(id);
        fit= new Individu(id.getKromosom(),id.getFitness(),id.getMakespan());
        
        System.out.println(id.getFitness());
        
       
        AlgoritmaGenetik ga= new AlgoritmaGenetik(0.3, 0.8, 0);
        for (int generasi = 0; generasi < 5; generasi++) {
            
            System.out.println("Best solution: " + js.getFittest(populasi).getFitness());
            System.out.println("Hasil roulette wheel :");
            Individu rwRes= ga.rouletteWheel(populasi);

            for (int j = 0; j < rwRes.getKromosom().length; j++) {
                    System.out.print(rwRes.getKromosom()[j].getOperation() + " ");

                }
            //seleksi roulette
            
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
                System.out.println(fitness);
                popMut[i].setFitness(fitness);
                popMut[i].setMakespan(makespan);
                
            }
            populasi = popMut;
            popFit.add(js.getFittest(populasi));
            
        }
        System.out.println("Fittest saat pertama di generate kromosom");
        System.out.println(fit.getFitness());
        System.out.println("Fittest :");
        for(int i=0;i<popFit.size();i++)
        {
            System.out.println(popFit.get(i).getFitness());
        }
        
    }
}
