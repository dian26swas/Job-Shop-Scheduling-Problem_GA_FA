
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
    private int elitism;

    public AlgoritmaGenetik( double mutationRate, double crossoverRate, int elitism) {
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.elitism = elitism;
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
        Individu result = populasi[0];
        double partialSum = 0;
        double roulette = 0;
        roulette = (double) (Math.random());

        for (int i = 0; i < populasi.length; i++) {
            partialSum += populasi[i].probability;

            if (partialSum >= roulette) {

                result = populasi[i];
                break;

            }
        }

        //System.out.println();
        return result;
    }

    //one point crossover
    //cari 2 parent kromosom menggunakan roulette wheel
    //kedua kromosom tersebut di crossover dengan memilih random point pembaginya
    public Individu[] crossover(Individu[] populasi) {

        
        Individu[] newPop = new Individu[populasi.length];
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

                int max = parent1.getKromosom().length - 2;
                int rand = (int) ((Math.random() * (max - 1)) + 1);
                
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

}
