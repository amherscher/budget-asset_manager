import java.text.DecimalFormat;

public abstract class Short {
    protected String name;
    protected double amount;
    protected static double allTimeAmount;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    //Constructor
    public Short(String name, double amount) {
        this.name = name;
        this.amount = formatAmount(amount);
    }

    private double formatAmount(double amount) {
        return Double.parseDouble(df.format(amount));
    }

    //Getters/Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(double amount) {
        this.amount = formatAmount(amount);
    }

    public static void setAllTimeAmount(double newAllTimeAmount) {
        allTimeAmount = newAllTimeAmount;
    }

    public String getName() {
        return this.name;
    }

    public double getAmount() {
        return this.amount;
    }

    public String getDecimalAmount() {
        return df.format(amount);
    }

    public double getAllTimeAmount() {
        return allTimeAmount;
    }
}
