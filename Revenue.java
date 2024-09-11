public class Revenue extends Short{

    public boolean isRepeat;
    public String month;
    
    //Constructor
    public Revenue(String month, double amount, String name, boolean isRepeat){
        super(name, amount);
        this.month = month;
        this.isRepeat = isRepeat;
    }

    public String toString() {
        return String.format("%s - $%,.2f - %s - Repeat: %s", name, amount, month, isRepeat ? "Yes" : "No");
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getMonth() {
        return this.month;
    }

    public void setIsRepeat(boolean isRepeat) {
        this.isRepeat = isRepeat;
    }

    public boolean getIsRepeat() {
        return isRepeat;
    }
}



