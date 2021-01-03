
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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
public class JobShop {

    
    private int popSize;
    private int jmlJob;
    private int jmlMesin;
    protected ArrayList<Individu> popFit;

    public JobShop(int popSize, int jmlJob, int jmlMesin) {
       
        this.popSize = popSize;
        this.jmlJob = jmlJob;
        this.jmlMesin = jmlMesin;
        popFit = new ArrayList<>();
    }

    public Gen[] generateKromosom(Gen[] arrGen) {

        ArrayList<Gen> listGen = new ArrayList<Gen>();
        listGen.addAll(Arrays.asList(arrGen));
        Collections.shuffle(listGen, new Random());
        Gen[] kromosom = new Gen[arrGen.length];
        
        for (int i = 0; i < arrGen.length; i++) {
            kromosom[i] = listGen.get(i);
            System.out.print(listGen.get(i).getOperation() + " ");

        }
        
        System.out.println("");
        //System.out.println("test");
        //System.out.println(calcMakespan(kromosom));
        return kromosom;

    }

    public Individu[] initialPop(Gen[] arrGen) {
        Individu[] populasi = new Individu[popSize];
        for (int i = 0; i < popSize; i++) {
            populasi[i] = new Individu(generateKromosom(arrGen), 0.0, 0.0);
            //System.out.println(calcMakespan(populasi[i].getKromosom()));
        }
        return populasi;
    }

    public void isiNoJob(Gen[] gener) {
        for (int i = 0; i < gener.length; i++) {
            char strJob = gener[i].getOperation().charAt(1);
            int job = Character.getNumericValue(strJob);
            gener[i].setNoJob(job);
            //System.out.println(gener[i].getNoJob());

        }
    }

    //mencari fitness yang paling besar
    public Individu getFittest(Individu[] populasi) {
        Individu max= new Individu(populasi[0].getKromosom(), populasi[0].getFitness(), populasi[0].getMakespan());
        for(int i=0;i<populasi.length;i++)
        {
            if(max.getFitness()<populasi[i].getFitness())
            {
                max= new Individu(populasi[i].getKromosom(), populasi[i].getFitness(), populasi[i].getMakespan());
            }
        }
        //popFit.add(populasi[0]);
        //System.out.println("Fittest : " + populasi[0].getFitness());
        return max;
    }

    public int calcMakespan(Gen[] gener) {
        int makespan = 0;
        
        //isiNoJob(gener);
 
        //cek urutan job berdasakan mesin 
        //masukin predesesor berdasarkan mesin dan job
        ArrayList<Integer> urutMesin = new ArrayList<Integer>();
        int mesin = 1;
        int counter = gener.length;
        while (counter > 0) {
            for (int j = 0; j < gener.length; j++) {
                if (gener[j].getNoMesin() == mesin) {
                    urutMesin.add(j);
                    counter--;

                }
            }
            mesin++;
            
        }
        for (int m = 0; m < urutMesin.size(); m++) {
            if (m % jmlMesin == 0) {
                gener[urutMesin.get(m)].setmPred(-1);
            } else {
                gener[urutMesin.get(m)].setmPred(urutMesin.get(m - 1));
            }
        }
        ArrayList<Integer> urutJob = new ArrayList<Integer>();
        int job = 1;
        int count = gener.length;
        while (count > 0) {
            for (int j = 0; j < gener.length; j++) {
                if (gener[j].getNoJob() == job) {
                    urutJob.add(j);
                    count--;
 
                }
            }
            job++;

        }
        for (int m = 0; m < urutMesin.size(); m++) {
            if (m % jmlMesin == 0) {
                gener[urutJob.get(m)].setjPred(-1);
            } else {
                gener[urutJob.get(m)].setjPred(urutJob.get(m - 1));
            }
        }
//        for (int j = 0; j < gener.length; j++) {
//            System.out.println(gener[j].getOperation() + ", jPred: " + gener[j].getjPred() + ", mPred: " + gener[j].getmPred());
//        }

        ArrayList<Gen> keep = new ArrayList<Gen>();
        int count1 = 0;
        //masukin ke keep yang tidak mempunyai depedency
        for (int x = 0; x < gener.length; x++) {
            if (gener[x].getjPred() == -1 && gener[x].getmPred() == -1) {
                //cek kalo overlap mesinnya sama dengan yang ada di array keep (gantt chart)
                if (!keep.isEmpty()) {
                    if (keep.get(keep.size() - 1).getNoMesin() == gener[x].getNoMesin()) {

                        gener[x].setMakespanTemp(gener[x].getTime() + keep.get(keep.size() - 1).getTime());
                        gener[x].setTime(gener[x].getMakespanTemp());
                        count1++;
                        gener[x].setWaktuMulai(keep.get(keep.size() - 1).getTime());
                        keep.add(gener[x]);

                    } else {
                        gener[x].setMakespanTemp(gener[x].getTime());
                        gener[x].setTime(gener[x].getMakespanTemp());
                        count1++;
                        gener[x].setWaktuMulai(keep.get(keep.size() - 1).getTime());
                        keep.add(gener[x]);

                    }
                } else {
                    gener[x].setMakespanTemp(gener[x].getTime());
                    gener[x].setTime(gener[x].getMakespanTemp());
                    count1++;
                    keep.add(gener[x]);
                }

            }
        }

        int jmlKeep = 0;
        int status = 0;
        while (count1 < gener.length) {

            //kalau diawal tidak ada yang masuk artinya cyclic depedencynya
            if (jmlKeep == keep.size()) {
                makespan = Integer.MAX_VALUE;
                status = 1;
                System.out.println("makespan tak terhingga");
                break;
            }

            jmlKeep = keep.size();
            if (keep.isEmpty()) {
                makespan = Integer.MAX_VALUE;
                System.out.println("makespan tak terhingga");

                break;
            } else {
                for (int x = 0; x < gener.length; x++) {
                    
                    if (status == 1) {
                        break;
                    }
                    if (gener[x].getjPred() == -1 && gener[x].getmPred() != -1) {

                        gener[x].setWaktuMulai(gener[gener[x].getmPred()].getTime());
                    } else if (gener[x].getjPred() != -1 && gener[x].getmPred() == -1) {

                        gener[x].setWaktuMulai(gener[gener[x].getjPred()].getTime());
                    } else if (gener[x].getjPred() != -1 && gener[x].getmPred() != -1) {
                        if (gener[gener[x].getjPred()].getTime() > gener[gener[x].getmPred()].getTime()) {
                            gener[x].setWaktuMulai(gener[gener[x].getjPred()].getTime());
                        } else {
                            gener[x].setWaktuMulai(gener[gener[x].getmPred()].getTime());
                        }

                    }
                    //cek yang belum masuk ke keep
                    if (gener[x].getMakespanTemp() == 0) {
                        // cek kalau jpred tidka null dan mpred null
                        
                        if (gener[x].getjPred() != -1 && gener[x].getmPred() == -1) {
                            // cek apakah jpred dan mprednya sudah masuk ke keep
                            if (keep.contains(gener[gener[x].getjPred()]) && gener[x].getmPred() == -1) {
                                //cek yang terakhir di keep no mesinnya sama atau tidak
                                if (keep.get(keep.size() - 1).getNoMesin() == gener[x].getNoMesin()
                                        && keep.get(keep.size() - 1).getWaktuMulai() == gener[x].getWaktuMulai()) {

                                    gener[x].setMakespanTemp(gener[x].getTime() + keep.get(keep.size() - 1).getTime());
                                    gener[x].setTime(gener[x].getMakespanTemp());
                                    
                                    gener[x].setWaktuMulai(keep.get(keep.size() - 1).getTime());
                                    keep.add(gener[x]);
                                    count1++;

                                } else {
                                    gener[x].setMakespanTemp(gener[gener[x].getjPred()].getTime() + gener[x].getTime());
                                    gener[x].setTime(gener[x].getMakespanTemp());
                                    gener[x].setWaktuMulai(keep.get(keep.size() - 1).getTime());
                                    keep.add(gener[x]);
                                    count1++;
                                }

                            }

                        }
                        //cek kalai mpred tidak null dna jpred null
                        if (gener[x].getmPred() != -1 && gener[x].getjPred() == -1) {
                            // cek apakah mpred jpred sudah ada di keep
                            if (keep.contains(gener[gener[x].getmPred()]) && gener[x].getjPred() == -1) {
                                //cek di keep terakhir nomer mesinnya sama atau tidak
                                if (keep.get(keep.size() - 1).getNoMesin() == gener[x].getNoMesin()
                                        && gener[x].getWaktuMulai() == keep.get(keep.size() - 1).getWaktuMulai()) {

                                    gener[x].setMakespanTemp(gener[x].getTime() + keep.get(keep.size() - 1).getTime());
                                    gener[x].setTime(gener[x].getMakespanTemp());
                                    
                                    gener[x].setWaktuMulai(keep.get(keep.size() - 1).getTime());
                                    keep.add(gener[x]);
                                    count1++;

                                } else {

                                    gener[x].setMakespanTemp(gener[gener[x].getmPred()].getTime() + gener[x].getTime());
                                    gener[x].setTime(gener[x].getMakespanTemp());
                                    gener[x].setWaktuMulai(keep.get(keep.size() - 1).getTime());
                                    keep.add(gener[x]);
                                    count1++;
                                }
                            }

                        }
                        //cek kalo jpred mpred tidak null
                        if (gener[x].getjPred() != -1 && gener[x].getmPred() != -1) {

                            //cek apakah jpred dan mpred nya sudah di keep
                            if (keep.contains(gener[gener[x].getmPred()]) && keep.contains(gener[gener[x].getjPred()])) {
                               
                                //cek apakah gen yang terakhir di keep nomer mesinnya sama atau tidak dan waktu mulainya sama atu tidak agar tidak overlap
                                if (keep.get(keep.size() - 1).getNoMesin() == gener[x].getNoMesin()
                                        && gener[x].getWaktuMulai() == keep.get(keep.size() - 1).getWaktuMulai()) {

                                    gener[x].setMakespanTemp(gener[x].getTime() + keep.get(keep.size() - 1).getTime());
                                    gener[x].setTime(gener[x].getMakespanTemp());
                                    count1++;
                                    gener[x].setWaktuMulai(keep.get(keep.size() - 1).getTime());
                                    keep.add(gener[x]);

                                } else {
                                    if (gener[gener[x].getmPred()].getTime() > gener[gener[x].getjPred()].getTime()) {

                                        gener[x].setMakespanTemp(gener[gener[x].getmPred()].getTime() + gener[x].getTime());
                                        gener[x].setTime(gener[x].getMakespanTemp());
                                        gener[x].setWaktuMulai(keep.get(keep.size() - 1).getTime());
                                        keep.add(gener[x]);
                                        count1++;

                                    } else {
                                        gener[x].setMakespanTemp(gener[gener[x].getjPred()].getTime() + gener[x].getTime());
                                        gener[x].setTime(gener[x].getMakespanTemp());
                                        gener[x].setWaktuMulai(keep.get(keep.size() - 1).getTime());
                                        keep.add(gener[x]);
                                        count1++;
                                    }
                                }
                            }
                        }

                    }
                    //System.out.println("2 " +kasus[x].getOperation()+" "+kasus[x].getMakespanTemp());
                }

            }
        }

        if (status == 1) {
            makespan = Integer.MAX_VALUE;
        } else {
            makespan=0;
            int[] arrMakespan = new int[gener.length];
            for (int a = 0; a < gener.length; a++) {
                //System.out.println(kasus[a].getOperation() + "makspan temp:" + kasus[a].getTime());
                arrMakespan[a] = gener[a].getTime();
                if(makespan<arrMakespan[a])
                {
                    makespan=arrMakespan[a];
                }
            }
            //makespan = Collections.max(Arrays.asList(arrMakespan));
        }

        System.out.println("hasil makespan :" + makespan);
        return makespan;
    }

    public double calcFitness(int makespan) {
        return 1.0 / (makespan + 1.0);
    }
}
