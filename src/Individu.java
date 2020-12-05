class Individu  {

    private Gen[] kromosom;
    private double fitness;
    protected double probability; // dipakai untuk roulette wheel
    

    public Individu(Gen[] kromosom, Double fitness) {
        this.kromosom = kromosom;
        this.fitness = fitness;
    }


    public Gen[] getKromosom() {
        return kromosom;
    }

    public void setKromosom(Gen[] kromosom) {
        this.kromosom = kromosom;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
    
    public void setProbability(double total, double fitness)
    {
        this.probability=fitness/total;
    }

//    @Override
//    public int compareTo(Individu o) {
//        if (this.fitness != o.getFitness()) {
//            return 0;
//        }
//        return this.kromosom.compareTo(o.getKromosom());
//    }

}
