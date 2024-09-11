import java.util.HashMap;

public class Expense extends Short {

public String month;
public int totalMonths;
public HashMap<String, Double> previousMonths = new HashMap<String, Double>();


    //Constructor
    public Expense(String month, double amount, String name){
        super(name, amount);
        this.month = month;
    }

    public String toString() {
        return String.format("%s - $%.2f - %s", name, amount, month);
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setTotalMonths(int totalMonths) {
        this.totalMonths = totalMonths;
    }

    public String getMonth() {
        return this.month;
    }  

    public int getTotalMonths() {
        return this.totalMonths;
    }

    //returns previous value for certain month
    public String getPrevious(String month) {
        Double value = previousMonths.get(month);
        if (value !=null) {
            return value.toString();
        } else {
            return "No data this month.";
        }
    }

    //calculate average of months
    public double average() {
        double value = allTimeAmount/totalMonths;
        return value;
    } 
    
}
