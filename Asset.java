public class Asset extends Long{

    private String openedDate = "0000-00-00";

    //Constructor
    public Asset(String name, double balance){
        super(name, balance);
    
    }

    public String toString() {
        return String.format("%s - $%,.2f - Acquired: %s", getName(), getBalance(), getOpenedDate());
    }

    //Getters/Setters
    public String getOpenedDate(){
        return this.openedDate;
    }

    public void setOpenedDate(String openedDate){
        this.openedDate = openedDate;
    }

    //MISC. FUNCTIONS

    //creates new revenue object based on interest earned.
    public Revenue interestEarned(String month){
        double amountEarned = this.getRate()*this.getBalance();
        return new Revenue("Interest", amountEarned, month, false);
    } 
}
