import java.io.*;
import java.util.ArrayList;

public class Storage {
    private static final String EXPENSE_FILENAME = "expenses.csv";
    private static final String REVENUE_FILENAME = "revenues.csv";
    private static final String ASSET_FILENAME = "assets.csv";
    private static final String LIABILITY_FILENAME = "liabilities.csv";



    //SAVING & LOADING METHODS=====================================

    //EXPENSES
    public static void saveExpenses(ArrayList<Expense> expenses) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(EXPENSE_FILENAME))) { // Using true to append data
            for (Expense e : expenses) {
                String expenseData = e.getMonth() + "," + e.getAmount() + "," + e.getName();
                writer.println(expenseData);
                System.out.println("Saving expense: " + expenseData); 
            }
        } catch (IOException e) {
            System.out.println("Error saving expenses: " + e.getMessage());
        }
    }

    public static ArrayList<Expense> loadExpenses() {
        ArrayList<Expense> expenses = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(EXPENSE_FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String month = parts[0].trim();
                    double amount = Double.parseDouble(parts[1].trim());
                    String name = parts[2].trim();
                    expenses.add(new Expense(month, amount, name));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading expenses: " + e.getMessage());
        }
        return expenses;
    }

    //REVENUES

     public static void saveRevenues(ArrayList<Revenue> revenues) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(REVENUE_FILENAME))) {
            for (Revenue r : revenues) {
                writer.println(r.getMonth() + "," + r.getAmount() + "," + r.getName() + "," + r.getIsRepeat());
            }
        } catch (IOException e) {
            System.out.println("Error saving revenues: " + e.getMessage());
        }
    }

    public static ArrayList<Revenue> loadRevenues() {
        ArrayList<Revenue> revenues = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(REVENUE_FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    String month = parts[0].trim();
                    double amount = Double.parseDouble(parts[1].trim());
                    String name = parts[2].trim();
                    boolean isRepeat = Boolean.parseBoolean(parts[3].trim());
                    revenues.add(new Revenue(month, amount, name, isRepeat));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading revenues: " + e.getMessage());
        }
        return revenues;
    }

    //ASSETS
    public static void saveAssets(ArrayList<Asset> assets) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ASSET_FILENAME))) {
            for (Asset asset : assets) {
                // Write the asset's name, balance, and opened date to the file
                writer.println(asset.getName() + "," + asset.getBalance() + "," + asset.getOpenedDate());
            }
        } catch (IOException e) {
            System.out.println("Error saving assets: " + e.getMessage());
        }
    }

    public static ArrayList<Asset> loadAssets() {
        ArrayList<Asset> assets = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ASSET_FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {  // Check for correct number of parts
                    String name = parts[0].trim();
                    double balance = Double.parseDouble(parts[1].trim());
                    String openedDate = parts[2].trim();
                    
                    Asset asset = new Asset(name, balance);
                    asset.setOpenedDate(openedDate);  // Set the opened date
                    assets.add(asset);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading assets: " + e.getMessage());
        }
        return assets;
    }

    //LIABILITIES
    public static void saveLiabilities(ArrayList<Liability> liabilities) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LIABILITY_FILENAME))) {
            for (Liability liability : liabilities) {
                // Write all the necessary properties of each liability to the file
                writer.println(liability.getName() + "," + liability.getBalance() + "," + liability.getTerm() + "," + liability.getRate() + "," + liability.getPayoffDate());
            }
        } catch (IOException e) {
            System.out.println("Error saving liabilities: " + e.getMessage());
        }
    }

    public static ArrayList<Liability> loadLiabilities() {
        ArrayList<Liability> liabilities = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(LIABILITY_FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {  // Ensure all parts are present
                    String name = parts[0].trim();
                    double balance = Double.parseDouble(parts[1].trim());
                    int term = Integer.parseInt(parts[2].trim());
                    double rate = Double.parseDouble(parts[3].trim());
                    String payoffDate = parts[4].trim();
                    
                    Liability liability = new Liability(name, balance, term, rate);
                    liability.setPayoffDate(payoffDate);
                    liabilities.add(liability);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading liabilities: " + e.getMessage());
        }
        return liabilities;
    }


    //REPORT BUILDING METHODS=============================================

    public static String getExpensesReport(String month) {
        ArrayList<Expense> allExpenses = loadExpenses();
        StringBuilder report = new StringBuilder();
        report.append("Expenses:" + "\n");
        for (Expense expense : allExpenses) {
            if (expense.getMonth().equalsIgnoreCase(month)) {
                report.append(String.format("  %s - $%,.2f\n", expense.getName(), expense.getAmount()));
            }
        }
        return report.toString();
    }

    public static String getRevenuesReport(String month) {
        ArrayList<Revenue> revenues = loadRevenues(); 
        StringBuilder report = new StringBuilder();
        report.append("Revenues:" + "\n");
        for (Revenue revenue : revenues) {
            if (revenue.getMonth().equalsIgnoreCase(month)) {
            report.append(String.format("  %s - $%,.2f - %s\n", revenue.getName(), revenue.getAmount(), revenue.getMonth()));
            }
        }
        return report.toString();
    }

    public static String getCashflowReport(String month) {
        ArrayList<Revenue> revenues = loadRevenues();
        ArrayList<Expense> expenses = loadExpenses();
        double totalRevenues = 0;
        double totalExpenses = 0;
        double cashflow = 0;
        StringBuilder report = new StringBuilder();
        for (Revenue revenue: revenues) {
            if (revenue.getMonth().equalsIgnoreCase(month)) {
            totalRevenues = totalRevenues + revenue.getAmount();
            }
        }
        for (Expense expense: expenses) {
            if (expense.getMonth().equalsIgnoreCase(month)) {
            totalExpenses = totalExpenses + expense.getAmount();
            }
        }
        cashflow = totalRevenues - totalExpenses;
        report.append(String.format("          Cash Flow (Revenues - Expenses): $%,.2f\n", cashflow));
        return report.toString();
        
    }

    public static String getAssetsReport() {
        ArrayList<Asset> assets = loadAssets();
        StringBuilder report = new StringBuilder();
        report.append("Long-term Assets Report:\n");
        for (Asset asset : assets) {
            report.append(String.format("  %s - $%,.2f - Acquired: %s\n", asset.getName(), asset.getBalance(), asset.getOpenedDate()));
        }
        return report.toString();
    }

    public static String getLiabilitiesReport() {
        ArrayList<Liability> liabilities = loadLiabilities();
        StringBuilder report = new StringBuilder();
        report.append("Outstanding Liabilities Report:\n");
        for (Liability liability : liabilities) {
            report.append(String.format("  %s - $%,.2f - Payoff: %s\n", liability.getName(), liability.getBalance(), liability.getPayoffDate()));
        }
        return report.toString();
    }

    public static String getNetWorthReport() {
        ArrayList<Asset> assets = loadAssets();
        ArrayList<Liability> liabilities = loadLiabilities();
        double totalAssets = 0;
        double totalLiabilities = 0;
        double netWorth;
        StringBuilder report = new StringBuilder();
        for (Asset asset : assets) {
            totalAssets = totalAssets + asset.getBalance();
        }
        for (Liability liability : liabilities) {
            totalLiabilities = totalLiabilities + liability.getBalance();
        }
        netWorth = totalAssets - totalLiabilities;
        report.append(String.format("          %s $%,.2f", "Current Net Worth (Assets - Liabilities):", netWorth));
        return report.toString();
    }
}