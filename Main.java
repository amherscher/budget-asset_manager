/*
Program Name: Budget Manager
Program Description:
This program provides a general monthly budget manager for the user.  

Features:
-Graphical User Interface for ease of use.
-Add/Delete Revenues and Expenses by Month.
-Track Long-term Assets and Liabilities with Add/Delete functions.
-View Monthly Report also summarizing monthly cash flow and current net worth.
-Storage in .csv files for easy transfer to spreadsheets/other financial application.

This program is scalable and leaves room for new features and improvements in the future.  

Author: Andrew Herscher
Creation Date: 04.09.2024
Last Modified: 04.23.2024
*/

public class Main {
    public static void main(String[] args) {
        try {
            BudgetManagerGUI gui = new BudgetManagerGUI();  //create new object, function described below
            gui.createAndShowGUI(); //this function runs from the object
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }
}