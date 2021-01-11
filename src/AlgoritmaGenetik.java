
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ASUS
 */
public class AlgoritmaGenetik {
    private double mutationRate;
    private double crossoverRate;
   

    public AlgoritmaGenetik( double mutationRate, double crossoverRate) {
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        
    }

    //roulettewheel selection
    public Individu rouletteWheel(Individu[] populasi) {

        double totalSum = 0.0;

        for (int i = 0; i < populasi.length; i++) {
            totalSum += populasi[i].getFitness();
        }

        for (int i = 0; i < populasi.length; i++) {
            populasi[i].setProbability(totalSum, populasi[i].getFitness());
        }
        
        double partialSum = 0;
        Individu result= new Individu(populasi[0].getKromosom(), populasi[0].getFitness(), populasi[0].getMakespan());;
        
        for (int i = 0; i < populasi.length; i++) {
            partialSum += populasi[i].probability;
            double max = totalSum;
            double roulette =((Math.random() * (max - 0)) + 0);
            if (partialSum >= roulette) {

                result= new Individu(populasi[i].getKromosom(), populasi[i].getFitness(), populasi[i].getMakespan());
                
                break;

            }
        }
        return result;
    }

    //one point crossover
    //cari 2 parent kromosom menggunakan roulette wheel
    //kedua kromosom tersebut di crossover dengan memilih random point pembaginya
    public Individu[] crossover(Individu[] populasi) {
        Individu[] newPop = new Individu[populasi.length];
        //sorting
        for(int i=0;i<populasi.length-1;i++)
        {
            for(int j=i+1;j<populasi.length;j++)
            {
                if(populasi[i].getFitness()<populasi[j].getFitness())
                {
                    Individu temp= populasi[i];
                    populasi[i]= populasi[j];
                    populasi[j]=populasi[i];
                }
            }
        }
        
        //select 2 parent
        Individu parent1 = rouletteWheel(populasi);
        Individu parent2 = rouletteWheel(populasi);
        System.out.println("p1 :");
        for(int p=0;p<parent1.getKromosom().length;p++)
        {
            System.out.print(parent1.getKromosom()[p].getOperation()+ " ");
            
            
        }
        System.out.println("");
        System.out.println("p2 :");
        for(int k=0;k<parent1.getKromosom().length;k++)
            {
                System.out.print(parent2.getKromosom()[k].getOperation()+ " ");
               
            }
        System.out.println("");
        for (int i = 1; i < populasi.length; i++) {

            if (this.crossoverRate > Math.random()) {

                Gen[] offspring = new Gen[parent1.getKromosom().length];

                int maxKrom = parent1.getKromosom().length - 2;
                int rand = (int) ((Math.random() * (maxKrom - 1)) + 1);
                
                for (int j = 0; j < rand; j++) {

                    offspring[j] = parent1.getKromosom()[j];
                }
                for (int k = rand; k < parent1.getKromosom().length; k++) {

                    offspring[k] = parent2.getKromosom()[k];
                }
                System.out.println("offspring :");
                for (int o = 0; o < offspring.length; o++) {

                    System.out.print(offspring[o].getOperation() + " ");
                }
                System.out.println("");
                newPop[i] = new Individu(offspring, 0.0,0.0);
                System.out.println("newPop crossover : ");
                int l = newPop[i].getKromosom().length;
                for (int h = 0; h < l; h++) {
                    System.out.print(newPop[i].getKromosom()[h].getOperation() + " ");
                }

            } else {
                newPop[i] = populasi[i];
            }

        }
        return newPop;
    }

    //swap mutation
    public Individu[] mutation(Individu[] populasi) {
        Individu[] newPop = new Individu[populasi.length];
        for (int i = 1; i < populasi.length; i++) {
            Individu indv1 = populasi[i];
            if (this.mutationRate > Math.random()) {
                //Gen[]  = new Gen[indv1.getKromosom().length];
                System.out.println("sebelum dimutasi");
                for (int k = 0; k < indv1.getKromosom().length; k++) {
                    System.out.print(indv1.getKromosom()[k].getOperation() + " ");
                }
                int max = indv1.getKromosom().length - 1;
                int rand1 = (int) ((Math.random() * (max - 1)) + 1);
                int rand2 = (int) ((Math.random() * (max - 1)) + 1);
                Gen temp = indv1.getKromosom()[rand1];
                indv1.getKromosom()[rand1] = indv1.getKromosom()[rand2];
                indv1.getKromosom()[rand2] = temp;
                System.out.println("");
                System.out.println("setelah dimutasi");
                for (int k = 0; k < indv1.getKromosom().length; k++) {
                    System.out.print(indv1.getKromosom()[k].getOperation() + " ");
                }
                newPop[i] = new Individu(indv1.getKromosom(), 0.0,0.0);
            } else {
                newPop[i] = populasi[i];
            }
        }
        return newPop;
    }
    
    public Individu[] geneticStep(Individu[] populasi,Gen[] arrTemp,int generasi,JobShop js,ArrayList<Individu> popFit)
    {
        //arrTemp itu nyimpen smeua informasi awal dari gen
        
        
        for (int g = 0; g < generasi; g++) {
            
            System.out.println("Best solution: " + js.getFittest(populasi).getFitness());
           
            System.out.println("hasil crossover");
            //crossover
            Individu[] popCross = crossover(populasi);
            popCross[0]=popFit.get(popFit.size()-1);
            System.out.println("");
            System.out.println("hasil mutasi");
            // swap mutation
            Individu[] popMut = mutation(popCross);
            popMut[0]=popFit.get(popFit.size()-1);
            Gen[] gener= new Gen[arrTemp.length];
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
        Individu[] result= new Individu[popFit.size()];
        for(int i=0;i<popFit.size();i++)
        {
            result[i]=popFit.get(i);
            System.out.println(popFit.get(i).getFitness());
        }
        return result;
    
    }

}
