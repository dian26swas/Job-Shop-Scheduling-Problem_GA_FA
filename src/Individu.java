class Individu  {

    private Gen[] kromosom;
    private double fitness;
    protected double probability; // dipakai untuk roulette wheel
    private double makespan;// dipakai untuk firefly algorithm

    public Individu(Gen[] kromosom, double fitness, double makespan) {
        this.kromosom = kromosom;
        this.fitness = fitness;
        this.makespan = makespan;
    }

    public double getMakespan() {
        return makespan;
    }

    public void setMakespan(double makespan) {
        this.makespan = makespan;
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
