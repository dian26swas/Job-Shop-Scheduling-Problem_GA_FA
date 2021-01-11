
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ASUS
 */
public class GeneticFirefly {
    
    private double mutationRate;
    private double crossoverRate;
    private double beta;
    private double gamma;
    private double alpha;

    public GeneticFirefly(double mutationRate, double crossoverRate, double beta, double gamma, double alpha) {
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.beta = beta;
        this.gamma = gamma;
        this.alpha = alpha;
    }
 
     public Individu[] gafaStep(Individu[] populasi,Gen[] arrTemp,int generasi,JobShop js,ArrayList<Individu> popFit)
    {
        //arrTemp itu nyimpen smeua informasi awal dari gen
        AlgoritmaGenetik ga= new AlgoritmaGenetik(this.mutationRate, this.crossoverRate);
        AlgoritmaFirefly fa= new AlgoritmaFirefly(this.beta, this.gamma, this.alpha);
        
        for (int g = 0; g < generasi; g++) {
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
                popMut[i].setFitness(fitness);
                popMut[i].setMakespan(makespan);
                
            }
           populasi = popMut;
            //masuk algoritma Firefly
           
            for (int popF = 0; popF < populasi.length - 1; popF++) {
                
                for (int po = popF + 1; po < populasi.length; po++) {
                    if (populasi[popF].getFitness() > populasi[po].getFitness()) {
                        //pergerakan kunang2 intensitas cahayanya lebih kecil ke yang lebih besar
                        populasi[po].setMakespan(0);
                        populasi[po].setFitness(0.0);
                        populasi[po]=fa.alphaStepOS(populasi[po], populasi[popF]);
                        //System.out.println(populasi[po].getMakespan());
                        Gen[] gen= new Gen[arrTemp.length];
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

                        populasi[po].setMakespan(makespan);
                        populasi[po].setFitness(fitnes);

                    } else if (populasi[popF].getFitness() == populasi[po].getFitness()) {
                        //kunang2 bergerak secara random
                        int max = populasi.length - 1;
                        int rand1 = (int) ((Math.random() * (max - 1)) + 1);
                        int rand2 = (int) ((Math.random() * (max - 1)) + 1);
                        populasi[rand1]=fa.alphaStepOS(populasi[rand1], populasi[rand2]);
                        Gen[] gen= new Gen[arrTemp.length];
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
        Individu[] result= new Individu[popFit.size()];
        for(int i=0;i<popFit.size();i++)
        {
            result[i]=popFit.get(i);
            System.out.println(popFit.get(i).getFitness());
        }
        return result;
        
    
    }
    
}
