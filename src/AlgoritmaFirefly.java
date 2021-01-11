
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
public class AlgoritmaFirefly {

    private double beta0;
    private double gamma;
    private double alpha; //sepertinya tidak terlalu berpengaruh
    private int[] dms;
    private ArrayList<String> dos;

    public AlgoritmaFirefly(double beta0, double gamma, double alpha) {
        this.beta0 = beta0;
        this.gamma = gamma;
        this.alpha = alpha;
    }

    public int jarakOS(Individu idv1, Individu idv2) {
        int result = 0;
        Individu idvTemp1 = idv1;
        Individu idvTemp2 = idv2;
        this.dos = new ArrayList<>();
        for (int i = 0; i < idv1.getKromosom().length; i++) {
            if (idvTemp1.getKromosom()[i].getNoJob() != idvTemp2.getKromosom()[i].getNoJob()) {
                for (int j = i; j < idv2.getKromosom().length; j++) {
                    if (idvTemp2.getKromosom()[i].getNoJob() == idvTemp1.getKromosom()[j].getNoJob()) {
                        //swap
                        String str = i + "," + j;
                        this.dos.add(str);

                        Gen temp = idvTemp1.getKromosom()[i];
                        idvTemp1.getKromosom()[i] = idvTemp1.getKromosom()[j];
                        idvTemp1.getKromosom()[j] = temp;
                        result += 1;
                        break;
                    }
                }

            }
        }

        return result;
    }

    public double keatraktifanOS(int jarak) {
        double result = 0.0;
        result = this.beta0 / (1 + (this.gamma * Math.pow(jarak, 2)));
        return result;
    }

    public Individu betaStepOS(Individu idv1, Individu idv2) {

        System.out.println("");
        int jarak = jarakOS(idv1, idv2);
        double[] random = new double[jarak];
        double beta = keatraktifanOS(jarak);

        // isi nilai random ke dms
        for (int i = 0; i < random.length; i++) {
            double rand = (double) ((Math.random() * (1 - 0)) + 0);
            random[i] = rand;
            if (random[i] <= beta) {
                String[] arrStr = dos.get(i).split(",");
                int temp1 = Integer.parseInt(arrStr[0]);
                int temp2 = Integer.parseInt(arrStr[1]);
                Gen temp = idv1.getKromosom()[temp1];
                idv1.getKromosom()[temp1] = idv1.getKromosom()[temp2];
                idv1.getKromosom()[temp2] = temp;
            }
        }

        return idv1;
    }

    public Individu alphaStepOS(Individu idv1, Individu idv2) {
        System.out.println("");
        System.out.println("Sebelum alpha step OS :");
        for (int i = 0; i < idv1.getKromosom().length; i++) {
            System.out.print(idv1.getKromosom()[i].getOperation() + " ");
        }

        idv1 = betaStepOS(idv1, idv2);
        int max = idv1.getKromosom().length - 1;
        int rand1 = (int) ((Math.random() * (max - 1)) + 1);
        int rand2 = (int) ((Math.random() * (max - 1)) + 1);
        Gen temp = idv1.getKromosom()[rand1];
        idv1.getKromosom()[rand1] = idv1.getKromosom()[rand2];
        idv1.getKromosom()[rand2] = temp;
        System.out.println("Setelah alpha step OS :");

        for (int i = 0; i < idv1.getKromosom().length; i++) {
            System.out.print(idv1.getKromosom()[i].getOperation() + " ");

        }
        System.out.println("");

        return idv1;

    }

    public Individu[] fireflyStep(Individu[] populasi, Gen[] arrTemp, int generasi, JobShop js, ArrayList<Individu> popFit) {
        for (int g = 0; g < generasi; g++) {
            for (int popF = 0; popF < populasi.length - 1; popF++) {

                for (int po = popF + 1; po < populasi.length; po++) {
                    if (populasi[popF].getFitness() > populasi[po].getFitness()) {
                        //pergerakan kunang2 intensitas cahayanya lebih kecil ke yang lebih besar
                        populasi[po].setMakespan(0);
                        populasi[po].setFitness(0.0);
                        populasi[po] = alphaStepOS(populasi[po], populasi[popF]);

                        Gen[] gen = new Gen[arrTemp.length];
                        for (int i = 0; i < populasi[po].getKromosom().length; i++) {
                            for (int j = 0; j < populasi[po].getKromosom().length; j++) {
                                if (arrTemp[i].getOperation().equals(populasi[po].getKromosom()[j].getOperation())) {

                                    populasi[po].getKromosom()[j].setTime(arrTemp[i].getTime());
                                    populasi[po].getKromosom()[j].setWaktuMulai(0);
                                    populasi[po].getKromosom()[j].setNoJob(arrTemp[i].getNoJob());
                                    populasi[po].getKromosom()[j].setNoMesin(arrTemp[i].getNoMesin());
                                }
                            }

                            gen[i] = new Gen(populasi[po].getKromosom()[i].getOperation(),
                                    populasi[po].getKromosom()[i].getTime(), -1, -1,
                                    populasi[po].getKromosom()[i].getNoMesin(), 0, 0, populasi[po].getKromosom()[i].getNoJob());

                        }

                        int makespan = js.calcMakespan(gen);
                        double fitnes = js.calcFitness(makespan);
                        System.out.println("hasil fitness: " + fitnes);

                        populasi[po].setMakespan(makespan);
                        populasi[po].setFitness(fitnes);

                    } else if (populasi[popF].getFitness() == populasi[po].getFitness()) {
                        //kunang2 bergerak secara random
                        int max = populasi.length - 1;
                        int rand1 = (int) ((Math.random() * (max - 1)) + 1);
                        int rand2 = (int) ((Math.random() * (max - 1)) + 1);
                        populasi[rand1] = alphaStepOS(populasi[rand1], populasi[rand2]);
                        Gen[] gen = new Gen[arrTemp.length];
                        for (int i = 0; i < populasi[rand1].getKromosom().length; i++) {
                            for (int j = 0; j < populasi[rand1].getKromosom().length; j++) {
                                if (arrTemp[i].getOperation().equals(populasi[rand1].getKromosom()[j].getOperation())) {
                                    
                                    populasi[po].getKromosom()[j].setTime(arrTemp[i].getTime());
                                }
                            }

                            gen[i] = new Gen(populasi[rand1].getKromosom()[i].getOperation(),
                                    populasi[rand1].getKromosom()[i].getTime(), -1, -1,
                                    populasi[rand1].getKromosom()[i].getNoMesin(), 0, 0, populasi[rand1].getKromosom()[i].getNoJob());

                        }

                        int makespan = js.calcMakespan(populasi[rand1].getKromosom());
                        double fitnes = js.calcFitness(makespan);
                        System.out.println("hasil fitness: " + fitnes);
                        populasi[rand1].setMakespan(makespan);
                        populasi[rand1].setFitness(fitnes);
                        System.out.println("kunang-kunang bergerak secara random");
                    }
                }

            }
            Individu idv = js.getFittest(populasi);
            popFit.add(idv);
          
        }
        Individu[] result = new Individu[popFit.size()];
        for (int i = 0; i < popFit.size(); i++) {
            result[i] = popFit.get(i);
            System.out.println(popFit.get(i).getFitness());
        }
        return result;

    }

}
