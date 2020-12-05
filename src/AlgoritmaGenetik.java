
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

    private int popSize;
    private double mutationRate;
    private double crossoverRate;
    private int elitism;

    public AlgoritmaGenetik(int popSize, double mutationRate, double crossoverRate, int elitism) {
        this.popSize = popSize;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.elitism = elitism;
    }

    public Gen[] generateKromosom(Gen[] arrGen) {

        ArrayList<Gen> listGen = new ArrayList<Gen>();
        for (int i = 0; i < arrGen.length; i++) {
            listGen.add(arrGen[i]);
        }
        Collections.shuffle(listGen, new Random());
        Gen[] kromosom = new Gen[arrGen.length];
        for (int i = 0; i < arrGen.length; i++) {
            kromosom[i] = listGen.get(i);
            //System.out.print(listGen.get(i).getOperation() + " ");

        }
//        System.out.println("");

        return kromosom;

    }

    public Individu[] initialPop(Gen[] arrGen) {
        Individu[] populasi = new Individu[popSize];
        for (int i = 0; i < popSize; i++) {
            populasi[i] = new Individu(generateKromosom(arrGen), 0.0);

        }
        return populasi;
    }

    public int calcMakespan(Gen[] kasus, Gen[] gener, int jmlMesin) {
        int makespan = 0;

        //cek urutan job berdasakan mesin 
        //masukin predesesor berdasarkan mesin dan job
        for (int k = 0; k < kasus.length; k++) {

            if (k % jmlMesin == 0) {
                kasus[k].setjPred(-1);

            } else {
                kasus[k].setjPred(k - 1);
            }
            for (int g = 0; g < gener.length; g++) {

                if (kasus[k].getOperation().equals(gener[g].getOperation())) {
                    if (g % jmlMesin == 0) {
                        kasus[k].setmPred(-1);
                    } else {
                        for (int kas = 0; kas < kasus.length; kas++) {
                            if (kasus[kas].getOperation().equals(gener[g - 1].getOperation())) {
                                kasus[k].setmPred(kas);
                            }
                        }
                        //kasus[k].setmPred(gener[g - 1]);

                    }
                }

            }

        }
       // print tiap gen dan predesesornya
//        for (int t = 0; t < kasus.length; t++) {
//
//            if (kasus[t].getjPred() == -1 && kasus[t].getmPred() != -1) {
//                System.out.println(kasus[t].getOperation() + " ,jPred:" + " null" + " mPred:" + kasus[t].getmPred());
//                
//            } else if (kasus[t].getjPred() != -1 && kasus[t].getmPred() == -1) {
//                System.out.println(kasus[t].getOperation() + " ,jPred:" + kasus[t].getjPred() + " mPred:" + "null");
//                
//            } else if (kasus[t].getjPred() != -1 && kasus[t].getmPred() != -1) {
//              
//                System.out.println(kasus[t].getOperation() + " ,jPred:" + kasus[t].getjPred() + " mPred:" + kasus[t].getmPred());
//            } else {
//                System.out.println(kasus[t].getOperation() + " ,jPred:" + " null" + " mPred:" + "null");
//            }
//         }
        ArrayList<Gen> keep = new ArrayList<Gen>();
        int count = 0;
        for (int x = 0; x < kasus.length; x++) {
            if (kasus[x].getjPred() == -1 && kasus[x].getmPred() == -1) {
                //cek kalo overlap mesinnya sama dengan yang ada di array keep (gantt chart)
                if (!keep.isEmpty()) {
                    if (keep.get(keep.size() - 1).getNoMesin() == kasus[x].getNoMesin()) {
//                        for (int k = 0; k < kasus.length; k++) {
//                            if (keep.get(keep.size() - 1).equals(kasus[k])) {
//                                kasus[x].setmPred(k);
                                kasus[x].setMakespanTemp(kasus[x].getTime() + keep.get(keep.size() - 1).getTime());
                                kasus[x].setTime(kasus[x].getMakespanTemp());
                                count++;
                                kasus[x].setWaktuMulai(keep.get(keep.size() - 1).getTime());
                                keep.add(kasus[x]);
                                
//                            }
//                        }
                    } else {
                        kasus[x].setMakespanTemp(kasus[x].getTime());
                        kasus[x].setTime(kasus[x].getMakespanTemp());
                        count++;
                        kasus[x].setWaktuMulai(keep.get(keep.size() - 1).getTime());
                        keep.add(kasus[x]);
                        
                    }
                } else {
                    kasus[x].setMakespanTemp(kasus[x].getTime());
                    kasus[x].setTime(kasus[x].getMakespanTemp());
                    count++;
                    keep.add(kasus[x]);
                }
                
            }
        }
        
        int jmlKeep=0;
        int status=0;
        while (count < kasus.length) {
            
            if(jmlKeep==keep.size())
            {
                makespan = Integer.MAX_VALUE;
                status=1;
                System.out.println("makespan tak terhingga");
                break;
            }
            
            jmlKeep=keep.size();
            if (keep.isEmpty()) {
                makespan = Integer.MAX_VALUE;
                System.out.println("makespan tak terhingga");

                break;
            } else {
                for (int x = 0; x < kasus.length; x++) {
                    if(status==1)
                    {
                        break;
                    }
                    if (kasus[x].getjPred() == -1 && kasus[x].getmPred() != -1) {

                        kasus[x].setWaktuMulai(kasus[kasus[x].getmPred()].getTime());
                    } else if (kasus[x].getjPred() != -1 && kasus[x].getmPred() == -1) {
                        
                        kasus[x].setWaktuMulai(kasus[kasus[x].getjPred()].getTime());
                    } else if (kasus[x].getjPred() != -1 && kasus[x].getmPred() != -1) {
                        if (kasus[kasus[x].getjPred()].getTime() > kasus[kasus[x].getmPred()].getTime()) {
                            kasus[x].setWaktuMulai(kasus[kasus[x].getjPred()].getTime());
                        } else {
                            kasus[x].setWaktuMulai(kasus[kasus[x].getmPred()].getTime());
                        }

                    }
                    //System.out.println("1 " +kasus[x].getOperation()+" "+kasus[x].getMakespanTemp());
                    if (kasus[x].getMakespanTemp()==0) {
                        //System.out.println(kasus[x].getOperation()+kasus[x].getMakespanTemp());
                        if (kasus[x].getjPred() != -1 && kasus[x].getmPred() == -1) {

                            if (keep.contains(kasus[kasus[x].getjPred()]) && kasus[x].getmPred() == -1) {
                                if(keep.get(keep.size()-1).getNoMesin()==kasus[x].getNoMesin() && 
                                        keep.get(keep.size()-1).getWaktuMulai()==kasus[x].getWaktuMulai())
                                { 

                                                kasus[x].setMakespanTemp(kasus[x].getTime() + keep.get(keep.size() - 1).getTime());
                                                kasus[x].setTime(kasus[x].getMakespanTemp());
                                                count++;
                                                kasus[x].setWaktuMulai(keep.get(keep.size() - 1).getTime());
                                                keep.add(kasus[x]);
                                                

                                   
                                }
                                else
                                {
                                    kasus[x].setMakespanTemp(kasus[kasus[x].getjPred()].getTime() + kasus[x].getTime());
                                    kasus[x].setTime(kasus[x].getMakespanTemp());
                                    kasus[x].setWaktuMulai(keep.get(keep.size() - 1).getTime());
                                    keep.add(kasus[x]);
                                    count++;
                                }
                                

                            } 
//                            else {
//                                makespan = Integer.MAX_VALUE;
//                                System.out.println("makespan tak terhingga cek1");
//                                count = kasus.length + 1;
//                                break;
//                            }
                        }
                        if (kasus[x].getmPred() != -1 && kasus[x].getjPred() == -1) {
                            
                            if (keep.contains(kasus[kasus[x].getmPred()]) && kasus[x].getjPred() == -1) {
                                        
                                if(keep.get(keep.size()-1).getNoMesin()==kasus[x].getNoMesin() && 
                                        kasus[x].getWaktuMulai()==keep.get(keep.size()-1).getWaktuMulai())
                                { 
//                                    System.out.println("test"+ kasus[x].getOperation()+" "+kasus[kasus[x].getmPred()].getOperation()+" "+
//                                    kasus[kasus[x].getmPred()].getTime());
//                                    System.out.println("mpred"+ kasus[kasus[x].getmPred()].getOperation()+
//                                            " keep akhir :"+keep.get(keep.size()-2).getOperation());
//                                        for (int k = 0; k < kasus.length; k++) {
//                                            if (keep.get(keep.size() - 1).equals(kasus[k])) {
//                                                kasus[x].setmPred(k);
                                                kasus[x].setMakespanTemp(kasus[x].getTime() + keep.get(keep.size() - 1).getTime());
                                                kasus[x].setTime(kasus[x].getMakespanTemp());
                                                count++;
                                                kasus[x].setWaktuMulai(keep.get(keep.size() - 1).getTime());
                                                keep.add(kasus[x]);
//                                            }
//                                        }
                                   
                                }
                                else
                                {
//                                    System.out.println("test "+ kasus[x].getOperation()+" "+kasus[kasus[x].getmPred()].getOperation()+" "+
//                                    kasus[kasus[x].getmPred()].getTime());
                                    kasus[x].setMakespanTemp(kasus[kasus[x].getmPred()].getTime() + kasus[x].getTime());
                                    kasus[x].setTime(kasus[x].getMakespanTemp());
                                    kasus[x].setWaktuMulai(keep.get(keep.size() - 1).getTime());
                                    keep.add(kasus[x]);
                                    count++;
                                }
                            } 
//                            else {
//                                makespan = Integer.MAX_VALUE;
//                                System.out.println("makespan tak terhingga cek 2");
//                                count = kasus.length + 1;
//                                break;
//                            }
                        }
                        if (kasus[x].getjPred() != -1 && kasus[x].getmPred() != -1) {
                            //System.out.println("Cek "+kasus[x].getOperation()+" wa: "+kasus[x].getWaktuMulai());
                            if (keep.contains(kasus[kasus[x].getmPred()]) && keep.contains(kasus[kasus[x].getjPred()])) {
//                                for (int i = 0; i < keep.size(); i++) {
//                                    System.out.println("keep : " + keep.get(i));
//                                }
//                                
//                                System.out.println("Cek1 "+kasus[x].getOperation()+" wa: "+kasus[x].getWaktuMulai());
//                                System.out.println("Cek2 "+keep.get(keep.size() - 1).getOperation()+" wa: "+keep.get(keep.size() - 1).getWaktuMulai());
                                if (keep.get(keep.size() - 1).getNoMesin() == kasus[x].getNoMesin() 
                                        && kasus[x].getWaktuMulai() == keep.get(keep.size() - 1).getWaktuMulai()) {
                                    //System.out.println("O31 masuksini ga "+kasus[x].getOperation());
//                                    for (int k = 0; k < kasus.length; k++) {
//                                        if (keep.get(keep.size() - 1).equals(kasus[k])) {
//                                            kasus[x].setmPred(k);
                                            kasus[x].setMakespanTemp(kasus[x].getTime() + keep.get(keep.size() - 1).getTime());
                                            kasus[x].setTime(kasus[x].getMakespanTemp());
                                            count++;
                                            kasus[x].setWaktuMulai(keep.get(keep.size() - 1).getTime());
                                            keep.add(kasus[x]);
//                                        }
//                                    }

                                } else {
                                    if (kasus[kasus[x].getmPred()].getTime() > kasus[kasus[x].getjPred()].getTime()) {

                                        kasus[x].setMakespanTemp(kasus[kasus[x].getmPred()].getTime() + kasus[x].getTime());
                                        kasus[x].setTime(kasus[x].getMakespanTemp());
                                        kasus[x].setWaktuMulai(keep.get(keep.size() - 1).getTime());
                                        keep.add(kasus[x]);
                                        count++;

                                    } else {
                                        kasus[x].setMakespanTemp(kasus[kasus[x].getjPred()].getTime() + kasus[x].getTime());
                                        kasus[x].setTime(kasus[x].getMakespanTemp());
                                        kasus[x].setWaktuMulai(keep.get(keep.size() - 1).getTime());
                                        keep.add(kasus[x]);
                                        count++;
                                    }
                                }  
                            } 
                        }

                    }
                    //System.out.println("2 " +kasus[x].getOperation()+" "+kasus[x].getMakespanTemp());
                }
                
            }
        }
        
        if(status==1)
        {
            makespan=Integer.MAX_VALUE;
        }
        else
        {
            
            Integer[] arrMakespan = new Integer[kasus.length];
                for (int a = 0; a < kasus.length; a++) {
                    //System.out.println(kasus[a].getOperation() + "makspan temp:" + kasus[a].getTime());
                    arrMakespan[a] = kasus[a].getTime();
                }
                makespan = Collections.max(Arrays.asList(arrMakespan));
        }

        System.out.println("hasil makespan :" + makespan);
        
        
        //ngecek apabila cycle ato memang pedesensi tidak sesuai
        //System.out.println("makespan: " + makespan);
        return makespan;
    }

    public double calcFitness(int makespan) {
        return 1.0 / (makespan + 1.0);
    }

    //mencari fitness yang paling besar
    public Individu getFittest(Individu[] populasi) {
        Arrays.sort(populasi, new Comparator<Individu>() {
            @Override
            public int compare(Individu o1, Individu o2) {
                if (o1.getFitness() > o2.getFitness()) {
                    return -1;
                } else if (o1.getFitness() < o2.getFitness()) {
                    return 1;
                }
                return 0;
            }

        });
        System.out.println("Fittest : " + populasi[0].getFitness());
        return populasi[0];
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

//                for (int j = 0; j < populasi[i].getKromosom().length; j++) {
//                    System.out.print(populasi[i].getKromosom()[j].getOperation() + " ");
//
//                }
//               System.out.println();
//                System.out.println("fitness: " + populasi[i].getFitness());
                result = populasi[i];
                break;

            }
        }

        //System.out.println();
        return result;
    }

    //one point crossover
    //pertama cari kromosom yang fitness paling besar menggunakan method getFittest()
    //cari kromosom menggunakan roulette wheel
    //kedua kromosom tersebut di crossover dengan memilih random point pembaginya
    public Individu[] crossover(Individu[] populasi) {

        Individu[] newPop = new Individu[populasi.length];
        Individu parent1 = getFittest(populasi);
        Individu parent2 = rouletteWheel(populasi);
        for (int i = 0; i < populasi.length; i++) {

            if (this.crossoverRate > Math.random()) {

                Gen[] offspring = new Gen[parent1.getKromosom().length];

                int max = parent1.getKromosom().length - 2;
                int rand = (int) ((Math.random() * (max - 1)) + 1);
                System.out.println("random :" + rand);
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
                newPop[i] = new Individu(offspring, 0.0);
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
        for (int i = 0; i < populasi.length; i++) {
            Individu indv1 = getFittest(populasi);
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
                newPop[i] = new Individu(indv1.getKromosom(), 0.0);
            } else {
                newPop[i] = populasi[i];
            }
        }
        return newPop;
    }

}
