import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;


public class BudgetManagerGUI {
    private JFrame frame;
    private JPanel cards; // panel that will use CardLayout
    private CardLayout cardLayout;
    private String[] monthsList = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    public void createAndShowGUI() { //here is the main construction of the GUI object
        frame = new JFrame("Budget Manager"); //created a frame as the most base level
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //when user "closes" frame, it will exit the program
        frame.setSize(500, 300); //size of frame

        cardLayout = new CardLayout(); //card system works like a deck of cards. so multiple menus can be used Called "LAYOUT MANAGER"
        cards = new JPanel(cardLayout); // panel is second layer, on top of frame.  this cards variable is a new panel using the created card layout manager instance. "CONTAINER"  

        JPanel card1 = createExpensePanel(); // Create subpanels
        JPanel card2 = createRevenuePanel();
        JPanel card3 = createViewReportsPanel();
        JPanel card4 = createAssetReportPanel();
        JPanel card5 = createLiabilityReportPanel();
        JPanel mainMenuPanel = new JPanel(); // This is the main menu panel.  If I don't put in an argument, it defaults to double buffer/flow layout

        //Main Menu
        // Adding navigation buttons
        JButton reportsButton = new JButton("View Reports"); //each one of these buttons should access a different menu option
        reportsButton.addActionListener(e -> cardLayout.show(cards, "Reports")); // so the addActionListener() method registers an Action Listener to the button,  this "listens" for something to happen
        mainMenuPanel.add(reportsButton); //(continued from above) e - > .. e represents the "ActionEvent" passed to the method.  Arrow shows what should be performed after event occurs

        JButton expenseButton = new JButton("Manage Expenses");
        expenseButton.addActionListener(e -> cardLayout.show(cards, "Expenses"));
        mainMenuPanel.add(expenseButton);

        JButton revenueButton = new JButton("Manage Revenues");
        revenueButton.addActionListener(e -> cardLayout.show(cards, "Revenues"));
        mainMenuPanel.add(revenueButton);

        JButton assetButton = new JButton("Manage Long-term Assets");
        assetButton.addActionListener(e -> cardLayout.show(cards, "Assets"));
        mainMenuPanel.add(assetButton);

        JButton liabilityButton = new JButton("Manage Outstanding Liabilities");
        liabilityButton.addActionListener(e -> cardLayout.show(cards, "Liabilities"));
        mainMenuPanel.add(liabilityButton);

        //Add Subpanels (Cards) to Panel ==================
        cards.add(mainMenuPanel, "Main Menu");
        cards.add(card1, "Expenses");
        cards.add(card2, "Revenues");
        cards.add(card3, "Reports");
        cards.add(card4, "Assets");
        cards.add(card5, "Liabilities");

        frame.getContentPane().add(cards);//layer to hold objects
        frame.setVisible(true);

        cardLayout.show(cards, "Main Menu"); // Start with the main menu
    }

//Panel creation====================

//EXPENSES==============================

    private JPanel createExpensePanel() {
        JPanel panel = new JPanel(new BorderLayout()); //create new panel for each page.  this one used for popup messages
        //2 main page panels: inputPanel AND scrollPane(expenseList)
        JPanel inputPanel = new JPanel(new GridLayout(0, 2)); 
    
        JTextField nameField = new JTextField(10); //inputPanel has two text fields for entry and a new JComboBox for months choice
        JTextField amountField = new JTextField(10);
        JComboBox<String> monthsBox = new JComboBox<>(monthsList);
        
        DefaultListModel<String> model = new DefaultListModel<>(); //DefaultListModel is used with JList components.  Creates way to add/remove/update list items
        JList<String> expenseList = new JList<>(model);  //so here we make the JList and it is using "model" as the manager of the list
        expenseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  //this makes it so we can only select one item of the JList at a time
        JScrollPane scrollPane = new JScrollPane(expenseList); //JScrollPane is what allows you to have the scroll sidebars on the component.  So we have to put the JList in this scrollPane
    
        JButton addButton = new JButton("Add Expense"); //like main menu we add buttons for functions
        addButton.addActionListener(e -> { 
            try {
                String month = (String) monthsBox.getSelectedItem(); //we have to typecast the month as a String because it is coming from JComboBox
                String name = nameField.getText();
                double amount = Double.parseDouble(amountField.getText());
    
                Expense newExpense = new Expense(month, amount, name); //ultimately adding new Expense object here
                ArrayList<Expense> expenses = Storage.loadExpenses(); //load existing expense objects first
                expenses.add(newExpense);   
                Storage.saveExpenses(expenses); //save after adding new one
    
                updateExpenseList(model, month); // update list model
                nameField.setText(""); //resets JTextFields to be empty
                amountField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Please enter a valid number for the amount. (Do not use $)", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    
        JButton deleteButton = new JButton("Delete Expense"); //this deletes a selected expense utilizing the available ListSelectionModel
        deleteButton.addActionListener(e -> {
            int selectedIndex = expenseList.getSelectedIndex();
            if (selectedIndex != -1) { //if no item is selected, it will return -1.  So this is how we make sure something is selected
                int confirm = JOptionPane.showConfirmDialog(panel, "Are you sure you want to delete this expense?", "Delete Expense", JOptionPane.YES_NO_OPTION); //showConfirmDialog with JOptionPane is a tool for popups with selection options
                if (confirm == JOptionPane.YES_OPTION) {
                    ArrayList<Expense> expenses = Storage.loadExpenses();
                    expenses.remove(selectedIndex);
                    Storage.saveExpenses(expenses); //updates expense file after selected expense removed
                    updateExpenseList(model, (String) monthsBox.getSelectedItem()); //updates ListSelectionModel when deleting just as if adding a new item
                }
            }
        });
    
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(cards, "Main Menu")); //the back button is easy, just navigates to Main Menu panel again
    
        //this is the area where the inputPanel is constructed. Labels are added as well as fields and buttons.  
        //Remember, panel contains inputPanel and scrollPane(scrollPane contains JList expense list, which is managed by ListSelectionModel)
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Amount:"));
        inputPanel.add(amountField);
        inputPanel.add(new JLabel("Month:"));
        inputPanel.add(monthsBox);
        inputPanel.add(addButton);
        inputPanel.add(deleteButton);
        inputPanel.add(backButton);
    
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
    
        updateExpenseList(model, (String) monthsBox.getSelectedItem()); // initializes the JList with current month expenses when panel is first shown
        monthsBox.addActionListener(e -> updateExpenseList(model, (String) monthsBox.getSelectedItem())); //this part updates the JList if user changes the month JComboBox
    
        return panel;
    }

    //this manages the displayed list of expenses (DefaultListModel) and is used to update after adding a new expense
    private void updateExpenseList(DefaultListModel<String> model, String month) {
        model.clear(); // Clear the list model
        ArrayList<Expense> expenses = Storage.loadExpenses();
        for (Expense expense : expenses) {
            if (expense.getMonth().equals(month)) {
                model.addElement(expense.toString()); //this adds the new expense to the end of the list
            }
        }
    }

//REVENUES=========================
    private JPanel createRevenuePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(0, 2));

        JTextField nameField = new JTextField(10);
        JTextField amountField = new JTextField(10);
        JComboBox<String> monthsBox = new JComboBox<>(monthsList);
        JCheckBox repeatable = new JCheckBox("Repeats?");

        DefaultListModel<Revenue> listModel = new DefaultListModel<>();
        JList<Revenue> revenueList = new JList<>(listModel);
        revenueList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(revenueList);

        JButton addButton = new JButton("Add Revenue");
        addButton.addActionListener(e -> {
            try {
                String month = (String) monthsBox.getSelectedItem();
                String name = nameField.getText();
                double amount = Double.parseDouble(amountField.getText());
                boolean isRepeat = repeatable.isSelected();

                Revenue newRevenue = new Revenue(month, amount, name, isRepeat);
                ArrayList<Revenue> revenues = Storage.loadRevenues();
                revenues.add(newRevenue);
                Storage.saveRevenues(revenues);

                updateRevenueList(listModel, month);
                nameField.setText("");
                amountField.setText("");
                repeatable.setSelected(false);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Please enter a valid number for the amount. (Do not use $)", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton deleteButton = new JButton("Delete Revenue");
        deleteButton.addActionListener(e -> {
            Revenue selectedRevenue = revenueList.getSelectedValue();
            if (selectedRevenue != null) {
                ArrayList<Revenue> revenues = Storage.loadRevenues();
                revenues.remove(selectedRevenue);
                Storage.saveRevenues(revenues);
                updateRevenueList(listModel, (String) monthsBox.getSelectedItem());
            }
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(cards, "Main Menu"));

        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Amount:"));
        inputPanel.add(amountField);
        inputPanel.add(new JLabel("Month:"));
        inputPanel.add(monthsBox);
        inputPanel.add(repeatable);
        inputPanel.add(addButton);
        inputPanel.add(deleteButton);
        inputPanel.add(backButton);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        updateRevenueList(listModel, (String) monthsBox.getSelectedItem());
        monthsBox.addActionListener(e -> updateRevenueList(listModel, (String) monthsBox.getSelectedItem()));

        return panel;
    }

    private void updateRevenueList(DefaultListModel<Revenue> listModel, String month) {
        listModel.clear(); // Clear the list model
        ArrayList<Revenue> revenues = Storage.loadRevenues();
        for (Revenue revenue : revenues) {
            if (revenue.getMonth().equals(month)) {
                listModel.addElement(revenue);
            }
        }
    }

//ASSETS==========================================
    public JPanel createAssetReportPanel() {
        JPanel panel = new JPanel(new BorderLayout());
    
        JPanel inputPanel = new JPanel(new GridLayout(0, 2));
        JTextField nameField = new JTextField(10);
        JTextField amountField = new JTextField(10);
        JButton addButton = new JButton("Add Asset");
        JButton deleteButton = new JButton("Delete Asset");
    
        DefaultListModel<String> assetListModel = new DefaultListModel<>();
        JList<String> assetList = new JList<>(assetListModel);
        assetList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(assetList);
    
        addButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                double amount = Double.parseDouble(amountField.getText());
                String todayDate = LocalDate.now().toString();  
    
                Asset newAsset = new Asset(name, amount);
                newAsset.setOpenedDate(todayDate);
    
                ArrayList<Asset> assets = Storage.loadAssets();
                assets.add(newAsset);
                Storage.saveAssets(assets);
                updateAssetList(assetListModel);
                nameField.setText("");
                amountField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Please enter valid numbers for amount. (Do not use $)", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    
        deleteButton.addActionListener(e -> {
            int selectedIndex = assetList.getSelectedIndex();
            if (selectedIndex != -1) {
                ArrayList<Asset> assets = Storage.loadAssets();
                assets.remove(selectedIndex);
                Storage.saveAssets(assets);
                updateAssetList(assetListModel);
            }
        });
    
        // Back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(cards, "Main Menu"));
    
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Value:"));
        inputPanel.add(amountField);
        inputPanel.add(addButton);
        inputPanel.add(deleteButton);
        inputPanel.add(backButton);
    
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
    
        updateAssetList(assetListModel); // Initially populate the list when the panel is loaded
    
        return panel;
    }
    
    private void updateAssetList(DefaultListModel<String> assetListModel) {
        ArrayList<Asset> assets = Storage.loadAssets();
        assetListModel.clear();
        for (Asset asset : assets) {
            assetListModel.addElement(asset.toString());
        }
    }
    //LIABILITIES ===========
    public JPanel createLiabilityReportPanel() {
        JPanel panel = new JPanel(new BorderLayout());
    
        JPanel inputPanel = new JPanel(new GridLayout(0, 2));
        JTextField nameField = new JTextField(10);
        JTextField balanceField = new JTextField(10);
        JTextField termField = new JTextField(10);
        JTextField rateField = new JTextField(10);
        JTextField payoffDateField = new JTextField(10);
        JButton addButton = new JButton("Add Liability");
        JButton deleteButton = new JButton("Delete Liability");
    
        DefaultListModel<String> liabilityListModel = new DefaultListModel<>();
        JList<String> liabilityList = new JList<>(liabilityListModel);
        liabilityList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(liabilityList);
    
        addButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                double balance = Double.parseDouble(balanceField.getText());
                // Handle empty term field by setting to 0
                int term;
                    if (termField.getText().isEmpty()) {
                        term = 0;
                    } else {
                        term = Integer.parseInt(termField.getText());
                    }   
                double rate = Double.parseDouble(rateField.getText()) / 100; // Convert percentage input to decimal
                String payoffDate; 
                    if (payoffDateField.getText().isEmpty()) {
                        payoffDate = "N/A";
                    } else {
                        payoffDate = payoffDateField.getText();
                    }
                Liability newLiability = new Liability(name, balance, term, rate);
                newLiability.setPayoffDate(payoffDate);
    
                ArrayList<Liability> liabilities = Storage.loadLiabilities();
                liabilities.add(newLiability);
                Storage.saveLiabilities(liabilities);
                updateLiabilityList(liabilityListModel);
    
                nameField.setText("");
                balanceField.setText("");
                termField.setText("");
                rateField.setText("");
                payoffDateField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Please enter valid numbers for balance and rate. (Do not use $ or %)", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    
        deleteButton.addActionListener(e -> {
            int selectedIndex = liabilityList.getSelectedIndex();
            if (selectedIndex != -1) {
                ArrayList<Liability> liabilities = Storage.loadLiabilities();
                liabilities.remove(selectedIndex);
                Storage.saveLiabilities(liabilities);
                updateLiabilityList(liabilityListModel);
            }
        });
    
        // Back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(cards, "Main Menu"));
    
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Balance:"));
        inputPanel.add(balanceField);
        inputPanel.add(new JLabel("Term (Months):"));
        inputPanel.add(termField);
        inputPanel.add(new JLabel("Rate (%):"));
        inputPanel.add(rateField);
        inputPanel.add(new JLabel("Payoff Date (YYYY-MM-DD):"));
        inputPanel.add(payoffDateField);
        inputPanel.add(addButton);
        inputPanel.add(deleteButton);
        inputPanel.add(backButton);
    
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
    
        updateLiabilityList(liabilityListModel); // Initially populate the list when the panel is loaded
    
        return panel;
    }
    
    private void updateLiabilityList(DefaultListModel<String> liabilityListModel) {
        ArrayList<Liability> liabilities = Storage.loadLiabilities();
        liabilityListModel.clear();
        for (Liability liability : liabilities) {
            liabilityListModel.addElement(liability.toString());
        }
    }

    //VIEW REPORTS ============

    public JPanel createViewReportsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
    
        JTextArea reportArea = new JTextArea(15, 50);
        reportArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(reportArea);
        panel.add(scrollPane, BorderLayout.CENTER);
    
        JPanel controlPanel = new JPanel();
    
        JLabel dateLabel = new JLabel("Select month:");
        JComboBox<String> monthBox = new JComboBox<>(monthsList);
        monthBox.setEditable(false); // Make the combo box non-editable
    
        JButton filterButton = new JButton("Submit");
        filterButton.addActionListener(e -> {
            String month = (String) monthBox.getSelectedItem();
            if (month != null) {
                reportArea.setText(loadReportData(month)); // Refresh the reports based on selected month
            }
        });
    
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(cards, "Main Menu"));
    
        controlPanel.add(dateLabel);
        controlPanel.add(monthBox);
        controlPanel.add(filterButton);
        controlPanel.add(backButton);
        panel.add(controlPanel, BorderLayout.SOUTH);
    
        return panel;
    }
    
    private String loadReportData(String month) {
        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append("*** " + month + " Financial Report ***" + "\n\n");
        reportBuilder.append(Storage.getExpensesReport(month)).append("\n");
        reportBuilder.append(Storage.getRevenuesReport(month)).append("\n");
        reportBuilder.append(Storage.getCashflowReport(month)).append("\n");
        reportBuilder.append("-------------------------\n\n");
        reportBuilder.append(Storage.getAssetsReport()).append("\n");
        reportBuilder.append(Storage.getLiabilitiesReport());
        reportBuilder.append("\n");
        reportBuilder.append(Storage.getNetWorthReport());
        reportBuilder.append("\n\n - End of " + month + " Financial Report -");
        return reportBuilder.toString();
    }

}