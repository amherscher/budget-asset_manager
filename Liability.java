public class Liability extends Long {

    private String payoffDate;

    public Liability(String name, double balance, int term, double rate){
        super(name, balance);
        this.rate = rate;
        this.term = term;
    }

    public String toString() {
        return String.format("%s - $%.2f - Term: %d years - Rate: %.2f%% - Payoff: %s", getName(), getBalance(), getTerm(), getRate() * 100, getPayoffDate());
    }

    public String getPayoffDate() {
        return this.payoffDate;
    }

    public void setPayoffDate(String date) {
        this.payoffDate = date;
    }

    //MISC. FUNCTIONS

    //creates new expense object based on interest.
    public Expense interestExpense(String month){
        double interestExpense = this.getRate()*this.getBalance();
        return new Expense("Interest", interestExpense, month);
    } 

    //apply payment to principal amount.
    public void payPrincipal(String month, double amount) {
        this.balance = this.getBalance() - amount;
    }
    
}
