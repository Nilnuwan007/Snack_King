import java.io.*;
import java.util.Scanner;
import static java.util.Objects.isNull;

public class Main {
    //Set the Number of Pizzas in Stock.
    static int pizzaStockCount = 100;
    //Track the Number of Pizzas Sold.
    static int soldPizzaCount = 0;
    static int onetimeServedPizzaCount = 10;
    //Queues to Add Customers
    String[] Q1 = new String[2];
    String[] Q2 = new String[3];
    String[] Q3 = new String[5];
    //Store Served Customer Data(Multidimensional Array)
    String[][] servedCustomerData = new String[10][4];
    //Control Program Execution
    static boolean runProgram;
    String filename = "data.txt";

    public static void main(String[] args) {
        new Main().runMenu();
    }

    public void runMenu() {
        String choice;

        //Set True to Continue Execution
        runProgram = true;

        while (runProgram) {
            //Print Menu
            printMenu();

            //Get Customer's Choice
            choice = validInput("Enter Your Menu Option : ").toUpperCase();

            //Perform Customers Choice
            switch (choice) {
                case "100":
                case "VFQ":
                    viewAllQueues();
                    break;
                case "101":
                case "VEQ":
                    viewAllEmptyQueues();
                    break;
                case "102":
                case "ACQ":
                    addCustomerToQueue();
                    break;
                case "103":
                case "RCQ":
                    checkRemoveCustomerFromQueue();
                    break;
                case "104":
                case "PCQ":
                    removeServedCustomer();
                    break;
                case "105":
                case "VCS":
                    sortCustomerNames();
                    break;
                case "106":
                    storeProgramData();
                    break;
                case "107":
                    loadStoredData();
                    break;
                case "108":
                case "STK":
                    viewRemainingPizzaStock();
                    break;
                case "109":
                case "AFS":
                    addPizzaToStock();
                    break;
                case "999":
                case "EXT":
                    exit();
                    break;
                default:
                    System.out.println("\nInvalid Menu Option! Please Check the Menu Options and Retry\n");
                    break;
            }
        }
    }

    public void printMenu() {
        //Check the Pizza Count is Enough
        checkSufficientPizzaCount(20);
        //Display Menu
        System.out.println("\n******************************");
        System.out.println("*****    Menu Options    *****");
        System.out.println("******************************");
        System.out.println("\n 100 or VFQ : View All Queues.");
        System.out.println(" 101 or VEQ : View All Empty Queues.");
        System.out.println(" 102 or ACQ : Add customer to a Queue.");
        System.out.println(" 103 or RCQ : Remove a customer from a Queue.");
        System.out.println(" 104 or PCQ : Remove a served customer.");
        System.out.println(" 105 or VCS : View Customers Sorted in Alphabetical Order.");
        System.out.println(" 106 or SPD : Store Program Data into File.");
        System.out.println(" 107 or LPD : Load Program Data from File.");
        System.out.println(" 108 or STK : View Remaining Pizza Stock.");
        System.out.println(" 109 or AFS : Add Pizza to Stock");
        System.out.println(" 999 or EXT : Exit the Program");


    }

    //Validate the Customer's Menu Option
    public static String validInput(String prompt) {
        Scanner scan = new Scanner(System.in);
        String input;

        while (true) {
            System.out.print("\n**********************************************\n" + prompt);
            input = scan.nextLine().strip();

            if (input.matches("[a-zA-Z0-9\\s]+"))
                break;

            System.out.println("\nInvalid Menu Option! Please Check the Menu Options and Retry\n");
        }

        return input;
    }

    public static String validString(String prompt) {
        Scanner scan = new Scanner(System.in);
        String input;

        while (true) {
            System.out.print("\n**********************************************\n" + prompt);
            input = scan.nextLine().strip();

            if (input.matches("[a-zA-Z\\s]+"))
                break;

            System.out.println("\nInvalid Input! Please Check \n");
        }

        return input;
    }

    public static String validNumber(String prompt) {
        Scanner scan = new Scanner(System.in);
        String input;
        int output;

        while (true) {
            System.out.print("\n**********************************************\n" + prompt);
            input = scan.nextLine().strip();

            if (input.matches("[0-9\\s]+")) {
                output = Integer.parseInt(input); // 0
                if (output > 0)
                    break;
            }

            System.out.println("\nInvalid Invalid! Please Check\n");
        }

        return input;
    }

    //Display Queues(100)
    public void viewAllQueues() {
        System.out.println("\n**************");
        System.out.println("*  CASHIERS  *");
        System.out.println("**************");

        for (int i = 0; i < 10; i++) {
            System.out.println("   " + printArrayStatus(Q1, i) + "   " + printArrayStatus(Q2, i) + "   " + printArrayStatus(Q3, i));
        }
    }

    public void checkSufficientPizzaCount(int MinimumPizzaAmount) {
        if (pizzaStockCount <= MinimumPizzaAmount) {
            System.out.println("\nReached the Minimum Amount of " + MinimumPizzaAmount + " Pizza in Stock!! So Add Some Pizzas to Continue\n");
        }
    }

    //Check Given Index of the Array is Null or Not and Return "X" or "O"
    public static String printArrayStatus(String[] array, int arrayIndex) {
        if (arrayIndex >= array.length) {
            return " ";
        } else if (array[arrayIndex] == null) {
            return "X";
        } else {
            return "O";
        }
    }

    //Display Empty Queues(101)
    public void viewAllEmptyQueues() {
        if (checkEmptySlot(Q1)) {
            System.out.print("\nQueue 1 : ");
            viewAllEmptySlots(Q1);
        }
        if (checkEmptySlot(Q2)) {
            System.out.print("Queue 2 : ");
            viewAllEmptySlots(Q2);
        }
        if (checkEmptySlot(Q3)) {
            System.out.print("Queue 3 : ");
            viewAllEmptySlots(Q3);
        }
        if (!checkEmptySlot(Q1) && !checkEmptySlot(Q2) && !checkEmptySlot(Q3)) {
            System.out.println("Queues Don't Have Empty Slots");
        }
    }
    //Check Empty Slot
    public void viewAllEmptySlots(String[] queue) {
        String output = "";
        for (int i = 0; i < queue.length; i++) {
            if (queue[i] == null) {
                output += i + 1;
                if (i != queue.length - 1) {
                    output += ", ";
                }
            }
        }
        System.out.println(output);
    }

    //Check the Availability of Queue
    public static boolean checkEmptySlot(String[] queue) {
        return queue[queue.length - 1] == null;
    }

    //Add Customer to Queue(102)
    public void addCustomerToQueue() {
        String queueNum = validNumber("Press 1 , 2 or 3 to Select Queue : ");
        switch (queueNum) {
            case ("1"):
                queueAvailability(Q1);
                break;
            case ("2"):
                queueAvailability(Q2);
                break;
            case ("3"):
                queueAvailability(Q3);
                break;
            default: {
                System.out.println("\nInvalid Queue Number! Please Check");
            }
        }
    }

    //Check Queue Availability to Add Customer to Queue
    public void queueAvailability(String[] queue) {
        if (!checkEmptySlot(queue)) {
            System.out.println("\nThis Queue is Full!! Choose Another Queue");
        } else {
            addCustomerName(queue);
        }
    }

    //Get Customer Name When Add to Queue
    public static void addCustomerName(String[] queue) {
        String custName = validString("Enter Customer's Name : ");
        for (int i = 0; i < queue.length; i++) {
            if (queue[i] == null) {
                queue[i] = custName;
                System.out.println(custName + " Add Successfully!");
                break;
            }
        }
    }
    //


    //Remove Customer From Queue(103)
    public void checkRemoveCustomerFromQueue() {
        String choice = validNumber("Enter Queue Number to Remove Customer : ");
        switch (choice) {
            case ("1"):
                removeCustomerFromSlot(Q1);
                break;
            case ("2"):
                removeCustomerFromSlot(Q2);
                break;
            case ("3"):
                removeCustomerFromSlot(Q3);
                break;
            default: {
                System.out.println("Invalid Queue Number!! Please Check");
                checkRemoveCustomerFromQueue();
                break;
            }
        }
    }

    //Remove Customer From Given Slot
    public void removeCustomerFromSlot(String[] queue) {
        int slot = Integer.parseInt(validNumber("Enter Slot Number to Remove Customer : ")) -1;
        try {
            if (queue[slot] == null) {
                System.out.println("There is no Customer in this Slot!! Please Check");
                checkRemoveCustomerFromQueue();
            } else {
                queue[slot] = null;
                System.out.println("Customer Removed from Queue");
                moveCustomerToUpSlot(queue);
            }
        } catch (Exception IndexOutOfBondsException) {
            String choice = validString("Not Existing Slot Number" + "Press 'y' to Select New Slot Number. Press 'n' for Back to Menu : ").toLowerCase();
            if (choice.equals("y")) {
                removeCustomerFromSlot(queue);
            }
        }
    }

    //Move Customer To Upper Slot
    public void moveCustomerToUpSlot(String[] queue){
        for (int i = 0; i < queue.length - 1; i++) {
            try {
                if (queue[i] == null) {
                    queue[i] = queue[i + 1];
                    queue[i + 1] = null;
                }
            } catch (Exception IndexoutofBoundsException) {
                System.out.println("index unbound error");
                break;
            }
        }
    }
    //Remove Served Customer(104)
    public void removeServedCustomer() {
        String choice = validNumber("Enter Queue Number of the Served Customer : ");
        switch (choice) {
            case ("1"):
                removeServedCustomerSlot(choice, Q1);
                break;
            case ("2"):
                removeServedCustomerSlot(choice, Q2);
                break;
            case ("3"):
                removeServedCustomerSlot(choice, Q3);
                break;
            default: {
                System.out.println("Invalid Queue Number!! Please Check");
                checkRemoveCustomerFromQueue();
                break;
            }
        }
    }
    //Remove Served Customer from Slot
    public void removeServedCustomerSlot(String queueName, String[] queue) {
        int slot = Integer.parseInt(validNumber("Enter Slot Number of Served Customer : ")) -1;
        try {
            if (queue[slot] == null) {
                System.out.println("There is no Customer in this Slot!! Please Check");
                removeServedCustomer();
            } else {
                pizzaStockCount -= onetimeServedPizzaCount;
                soldPizzaCount += onetimeServedPizzaCount;
                addServedCustomerData(queueName, queue[slot]);
                queue[slot] = null;
                System.out.println("Served and Removed the Customer ");
                moveCustomerToUpSlot(queue);
            }
        } catch (Exception IndexOutOfBondsException) {
            String choice = validString("Not Existing Slot Number" + "Press 'y' to Select New Slot Number. Press 'n' for Back to Menu : ").toLowerCase();
            if (choice.equals("y")) {
                removeServedCustomerSlot(queueName, queue);
            }
        }
    }
    //Add the Served Customer Data to Array
    public void addServedCustomerData(String queueName, String customerName){
        for (int i = 0; i < servedCustomerData.length; i++){
            if (servedCustomerData[i][0] == null){
                servedCustomerData[i][0] = customerName;
                servedCustomerData[i][1] = queueName;
                break;
            }
        }
    }
    //Display Customer's Names Before Sort or After
    public void printCustomers() {
        for (int i = 0; i < servedCustomerData.length; i++) {
            if (!isNull(servedCustomerData[i][0]))
                System.out.println(servedCustomerData[i][0]);
        }
    }
    //Sort Customer's Names in Alphabetical Order(105)
    public void sortCustomerNames() {
        for (int i = 0; i < servedCustomerData.length; i++) {
            for (int j = 0; j < servedCustomerData.length; j++) {
                if (i == j)
                    continue;
                if (isNull(servedCustomerData[i][0]) || isNull(servedCustomerData[j][0]))
                    continue;

                // get the min length of the given two words
                int min = Math.min(servedCustomerData[i][0].length(), servedCustomerData[j][0].length());

                for (int k = 0; k < min; k++) {
                    if (servedCustomerData[i][0].charAt(k) == servedCustomerData[0][0].charAt(k))
                        continue;
                    if (servedCustomerData[i][0].charAt(0) < servedCustomerData[j][0].charAt(0)) {
                        String temp = servedCustomerData[i][0];
                        servedCustomerData[i][0] = servedCustomerData[j][0];
                        servedCustomerData[j][0] = temp;
                        break;
                    }
                }
            }
        }
        printCustomers();
    }
    //
    public void storeProgramData() {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(filename));
            for (int i = 0; i < servedCustomerData.length; i++) {
                if (servedCustomerData[i][0] != null) {
                    writer.println(servedCustomerData[i][0] + ", " + servedCustomerData[i][1] + ", " + onetimeServedPizzaCount);
                }
            }
            writer.close();
            System.out.println("Data saved to the file");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    //
    public void loadStoredData(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String l;
            while ((l = reader.readLine()) != null) {
                System.out.println(l);
            }
            reader.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    //Display Remaining Pizza Stock(108)
    public void viewRemainingPizzaStock(){
        System.out.println("\n* There are " + pizzaStockCount + " Pizzas in Stock");
    }
    //Add Pizzas to Stock(109)
    public void addPizzaToStock() {
        while (true) {
            int n = Integer.parseInt(validNumber("Enter the pizza count : ")) ;
            if (n + pizzaStockCount <= 100) {
                pizzaStockCount += n;
                System.out.println("\n* Add to Stock " + n + " Pizzas");
                System.out.println("* There are "+ pizzaStockCount + " Pizzas in Stock Now");
                break;
            }
            System.out.println("\nInvalid Pizza Count");
        }
    }
    //Exit Programme
    public void exit(){
        String choice = validString("Press 'Y' If you Want to Exit or Press 'N' for Back to Menu : ").toUpperCase();
        switch (choice){
            case ("Y"):
                runProgram = false;
                break;
            case ("N"):
                runMenu();
                break;
            default:
                System.out.println("\nInvalid Choice! Please Check");
        }
    }
}