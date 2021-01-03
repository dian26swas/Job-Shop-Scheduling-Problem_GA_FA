
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

        //System.out.println("Jarak OS :"+result);
        return result;
    }

    public double keatraktifanOS(int jarak) {
        double result = 0.0;
        result = this.beta0 / (1 + Math.exp(-this.gamma * Math.pow(jarak, 2)));
        //System.out.println("Keatraktifan OS:"+result);
        return result;
    }

    public Individu betaStepOS(Individu idv1, Individu idv2) {

//        System.out.println("Sebelum beta step OS :");
//        for(int i=0;i<idv1.getKromosom().length;i++)
//        {
//            System.out.print(idv1.getKromosom()[i].getOperation()+" ");
//        }
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
//        System.out.println("Setelah beta step OS :");
//        for(int i=0;i<idv1.getKromosom().length;i++)
//        {
//            System.out.print(idv1.getKromosom()[i].getOperation()+" ");
//        }
//        System.out.println("");
        return idv1;
    }

    public Individu alphaStepOS(Individu idv1, Individu idv2) {
        System.out.println("Sebelum alpha step OS :");
        for (int i = 0; i < idv1.getKromosom().length; i++) {
            System.out.print(idv1.getKromosom()[i].getOperation() + " ");
        }
        System.out.println("");
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

}
