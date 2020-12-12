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

    public AlgoritmaFirefly(double beta0, double gamma, double alpha) {
        this.beta0 = beta0;
        this.gamma = gamma;
        this.alpha = alpha;
    }

    public double intesitasCahaya(AlgoritmaGenetik ag,Gen[] kasus, Gen[] popMut, int jmlMesin)
    {
        return ag.calcMakespan(kasus, popMut,jmlMesin );
    }
    public int jarak(Individu idv1, Individu idv2)
    {
        int result=0;
        for(int i=0;i<idv1.getKromosom().length;i++)
        {
            if(idv1.getKromosom()[i].getOperation()!=idv2.getKromosom()[i].getOperation())
            {
                result+=1;
            }
        }
        
        return result;
    }
    
    public double keatraktifan(Individu idv1, Individu idv2)
    {
        double result=0.0;
        result=this.beta0*Math.exp(-this.gamma*Math.pow(jarak(idv1,idv2), 2));
        return result;
    }
    
    public double pergerakan(Individu idv1, Individu idv2)
    {
        double result=0.0;
        int max = 1;
        double rand = (double) ((Math.random() * (max - 0)) + 0);
        //rand antara 0-1
        result=idv1.getMakespan()+keatraktifan(idv1, idv2)*(Math.abs(idv1.getMakespan()-idv2.getMakespan())+(alpha*(0.5*rand)));
        return result;
    }
    
    
    
}
