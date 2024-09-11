public abstract class Long {
    protected String name;
    protected double balance;
    protected int term;
    protected double rate;
    protected double monthRate;

    //Constructor
    public Long(String name, double balance) {
        this.name = name;
        this.balance = balance;
        this.term = 0;
        this.rate = 0.0;
        this.monthRate = rate/12;
    }

    //Getters/Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void setMonthRate(double monthRate) {
        this.monthRate = monthRate;
    }

    public String getName() {
        return this.name;
    }

    public double getBalance() {
        return this.balance;
    }

    public int getTerm() {
        return this.term;
    }

    public double getRate() {
        return this.rate;
    }

    public double getMonthRate() {
        return this.monthRate;
    } 

}
