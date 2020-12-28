
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

    public void isiOS(Individu idv)
    {
        for(int i=0;i<idv.getKromosom().length;i++)
        {
            char strJob=idv.getKromosom()[i].getOperation().charAt(1);
            int job= Character.getNumericValue(strJob);
            idv.getKromosom()[i].setNoJob(job);
            
            
            System.out.println(idv.getKromosom()[i].getOperation()+ " MS :"+idv.getKromosom()[i].getNoMesin()+
                    ", OS:"+idv.getKromosom()[i].getNoJob());
        }
    }

    public int jarakMS(Individu idv1, Individu idv2)
    {
        int result=0;
        for(int i=0;i<idv1.getKromosom().length;i++)
        {
            if(idv1.getKromosom()[i].getNoMesin()!=idv2.getKromosom()[i].getNoMesin())
            {
                System.out.println("masuk sini ga?");
                result+=1;
            }
        }
        
       //masukan ke dms
       dms= new int[result];
       int count=0;
       for(int i=0;i<idv1.getKromosom().length;i++)
        {
            if(idv1.getKromosom()[i].getNoMesin()!=idv2.getKromosom()[i].getNoMesin())
            {
                dms[count]=i;
                count++;
            }
        }
        System.out.println("Jarak MS :"+result);
        return result;
    }
    public int jarakOS(Individu idv1, Individu idv2)
    {
        int result=0;
        Individu idvTemp1= idv1;
        Individu idvTemp2=idv2;
        this.dos= new ArrayList<>();
        for(int i=0;i<idv1.getKromosom().length;i++)
        {
            if(idvTemp1.getKromosom()[i].getNoJob()!=idvTemp2.getKromosom()[i].getNoJob())
            {
                for(int j=i;j<idv2.getKromosom().length;j++)
                {
                    if(idvTemp2.getKromosom()[i].getNoJob()==idvTemp1.getKromosom()[j].getNoJob())
                    {
                        //swap
                        String str=i+","+j;
                        this.dos.add(str);
                        
                        Gen temp= idvTemp1.getKromosom()[i];
                        idvTemp1.getKromosom()[i]=idvTemp1.getKromosom()[j];
                        idvTemp1.getKromosom()[j]=temp;
                        result+=1;
                        break;
                    }
                }
                
                
            }
        }
 
        System.out.println("Jarak OS :"+result);
        return result;
    }
    
    public double keatraktifanMS(int jarak)
    {
        double result=0.0;
        result=this.beta0/(1+Math.exp(-this.gamma*Math.pow(jarak, 2)));
        System.out.println("Keatraktifan MS:"+result);
        return result;
    }
    
    public double keatraktifanOS(int jarak)
    {
        double result=0.0;
        result=this.beta0/(1+Math.exp(-this.gamma*Math.pow(jarak, 2)));
        System.out.println("Keatraktifan OS:"+result);
        return result;
    }
    
    public Individu betaStepMS(Individu idv1,Individu idv2)
    {
        
        System.out.println("Sebelum beta step MS :");
        for(int i=0;i<idv1.getKromosom().length;i++)
        {
            System.out.print(idv1.getKromosom()[i].getNoMesin()+" ");
        }
        // isi nilai random ke dms
        System.out.println("");
        int jarak=jarakMS(idv1, idv2);
        double[] random= new double[jarak];
        double beta= keatraktifanMS(jarak);
        for(int i=0;i<dms.length;i++)
        {
            double rand = (double) ((Math.random() * (1 - 0)) + 0);
            random[i]=rand;
            if(random[i]<=beta)
            {
                idv1.getKromosom()[dms[i]].setNoMesin(idv2.getKromosom()[dms[i]].getNoMesin());
            }
        }
        System.out.println("Setelah beta step MS :");
        for(int i=0;i<idv1.getKromosom().length;i++)
        {
            System.out.print(idv1.getKromosom()[i].getNoMesin()+" ");
        }
        System.out.println("");
       return idv1;
    }
    
     public Individu betaStepOS(Individu idv1,Individu idv2)
    {
        
        System.out.println("Sebelum beta step OS :");
        for(int i=0;i<idv1.getKromosom().length;i++)
        {
            System.out.print(idv1.getKromosom()[i].getNoJob()+" ");
        }
        System.out.println("");
        int jarak=jarakOS(idv1, idv2);
        double[] random= new double[jarak];
        double beta= keatraktifanOS(jarak);

        // isi nilai random ke dms
        for(int i=0;i<random.length;i++)
        {
            double rand = (double) ((Math.random() * (1 - 0)) + 0);
            random[i]=rand;
            if(random[i]<=beta)
            {
                int temp1= Character.getNumericValue(dos.get(i).charAt(0));
                int temp2= Character.getNumericValue(dos.get(i).charAt(2));
                int temp= idv1.getKromosom()[temp1].getNoJob();
                idv1.getKromosom()[temp1].setNoJob(idv1.getKromosom()[temp2].getNoJob());
                idv1.getKromosom()[temp2].setNoJob(temp);
            }
        }
        System.out.println("Setelah beta step OS :");
        for(int i=0;i<idv1.getKromosom().length;i++)
        {
            System.out.print(idv1.getKromosom()[i].getNoJob()+" ");
        }
        System.out.println("");
       return idv1;
    }
    
    public Individu alphaStepMS(Individu idv1, Individu idv2)
    {
        System.out.println("Sebelum alpha step MS :");
        for(int i=0;i<idv1.getKromosom().length;i++)
        {
            System.out.print(idv1.getKromosom()[i].getNoMesin()+" ");
        }
        System.out.println("");
       idv1= betaStepMS(idv1, idv2);
        int max = idv1.getKromosom().length - 1;
                int rand1 = (int) ((Math.random() * (max - 1)) + 1);
                int rand2 = (int) ((Math.random() * (max - 1)) + 1);
                int temp = idv1.getKromosom()[rand1].getNoJob();
                idv1.getKromosom()[rand1].setNoMesin(idv1.getKromosom()[rand2].getNoMesin()); ;
                idv1.getKromosom()[rand2].setNoMesin(temp);
        System.out.println("Setelah alpha step MS :");
        for(int i=0;i<idv1.getKromosom().length;i++)
        {
            System.out.print(idv1.getKromosom()[i].getNoMesin()+" ");
        }
        System.out.println("");
        return idv1;
    }
    
    public Individu alphaStepOS(Individu idv1, Individu idv2)
    {
        System.out.println("Sebelum alpha step OS :");
        for(int i=0;i<idv1.getKromosom().length;i++)
        {
            System.out.print(idv1.getKromosom()[i].getNoJob()+" ");
        }
        System.out.println("");
       idv1= betaStepOS(idv1, idv2);
        int max = idv1.getKromosom().length - 1;
                int rand1 = (int) ((Math.random() * (max - 1)) + 1);
                int rand2 = (int) ((Math.random() * (max - 1)) + 1);
                int temp = idv1.getKromosom()[rand1].getNoJob();
                idv1.getKromosom()[rand1].setNoJob(idv1.getKromosom()[rand2].getNoJob());;
                idv1.getKromosom()[rand2].setNoJob(temp);
        System.out.println("Setelah alpha step OS :");
        for(int i=0;i<idv1.getKromosom().length;i++)
        {
            System.out.print(idv1.getKromosom()[i].getNoJob()+" ");
        }
        System.out.println("");
        return idv1;
        
    }
 
}
