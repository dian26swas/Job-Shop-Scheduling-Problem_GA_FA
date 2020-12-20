
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

    private Gen[] arrGen;
    private int popSize;
    private int jmlJob;
    private int jmlMesin;

    public JobShop(Gen[] arrGen, int popSize, int jmlJob, int jmlMesin) {
        this.arrGen = arrGen;
        this.popSize = popSize;
        this.jmlJob = jmlJob;
        this.jmlMesin = jmlMesin;
    }

    
    public Gen[] generateKromosom() {

        ArrayList<Gen> listGen = new ArrayList<Gen>();
        listGen.addAll(Arrays.asList(this.arrGen));
        Collections.shuffle(listGen, new Random());
        Gen[] kromosom = new Gen[arrGen.length];
        for (int i = 0; i < arrGen.length; i++) {
            kromosom[i] = listGen.get(i);
            System.out.print(listGen.get(i).getOperation() + " ");

        }
        System.out.println("");

        return kromosom;

    }

    public Individu[] initialPop() {
        Individu[] populasi = new Individu[popSize];
        for (int i = 0; i < popSize; i++) {
            populasi[i] = new Individu(generateKromosom(), 0.0, 0.0);

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

        int jmlKeep = 0;
        int status = 0;
        while (count < kasus.length) {

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
                for (int x = 0; x < kasus.length; x++) {
                    if (status == 1) {
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
                    if (kasus[x].getMakespanTemp() == 0) {
                        //System.out.println(kasus[x].getOperation()+kasus[x].getMakespanTemp());
                        if (kasus[x].getjPred() != -1 && kasus[x].getmPred() == -1) {

                            if (keep.contains(kasus[kasus[x].getjPred()]) && kasus[x].getmPred() == -1) {
                                if (keep.get(keep.size() - 1).getNoMesin() == kasus[x].getNoMesin()
                                        && keep.get(keep.size() - 1).getWaktuMulai() == kasus[x].getWaktuMulai()) {

                                    kasus[x].setMakespanTemp(kasus[x].getTime() + keep.get(keep.size() - 1).getTime());
                                    kasus[x].setTime(kasus[x].getMakespanTemp());
                                    count++;
                                    kasus[x].setWaktuMulai(keep.get(keep.size() - 1).getTime());
                                    keep.add(kasus[x]);

                                } else {
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

                                if (keep.get(keep.size() - 1).getNoMesin() == kasus[x].getNoMesin()
                                        && kasus[x].getWaktuMulai() == keep.get(keep.size() - 1).getWaktuMulai()) {
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

                                } else {
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

        if (status == 1) {
            makespan = Integer.MAX_VALUE;
        } else {

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
}
