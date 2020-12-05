class Gen
{
    private String operation;
    private int time;
    private int jPred;
    private int mPred;
    private int noMesin;
    private int makespanTemp;
    private int waktuMulai;

    public Gen(String operation, int time, int jPred, int mPred, int noMesin, int makespanTemp, int waktuMulai) {
        this.operation = operation;
        this.time = time;
        this.jPred = jPred;
        this.mPred = mPred;
        this.noMesin = noMesin;
        this.makespanTemp = makespanTemp;
        this.waktuMulai = waktuMulai;
    }

    public int getWaktuMulai() {
        return waktuMulai;
    }

    public void setWaktuMulai(int waktuMulai) {
        this.waktuMulai = waktuMulai;
    }

    

    public int getMakespanTemp() {
        return makespanTemp;
    }

    public void setMakespanTemp(int makespanTemp) {
        this.makespanTemp = makespanTemp;
    }

    

    public int getNoMesin() {
        return noMesin;
    }

    public void setNoMesin(int noMesin) {
        this.noMesin = noMesin;
    }

    public int getjPred() {
        return jPred;
    }

    public void setjPred(int jPred) {
        this.jPred = jPred;
    }

    public int getmPred() {
        return mPred;
    }

    public void setmPred(int mPred) {
        this.mPred = mPred;
    }


    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
    

    
    
}