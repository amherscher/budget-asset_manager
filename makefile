all: Main.java Asset.java BudgetManagerGUI.java Expense.java Liability.java Long.java Revenue.java Short.java Storage.java
	javac Main.java
	javac BudgetManagerGUI.java
	javac Expense.java
	javac Liability.java
	javac Long.java
	javac Revenue.java
	javac Short.java
	javac Storage.java
	
run:
	java Main

jar: all 
	jar cfm BudgetManager.jar Manifest.txt *.class
	java -jar BudgetManager.jar



clean: 
	$(RM) *.class 
	$(RM) *.ser