import java.util.Scanner;

public class Main {
    //Set the Number of Pizzas in Stock.
    static int pizzaStockCount = 100;
    //Track the Number of Pizzas Sold.
    static int soldPizzaCount = 0;
    //Queues to Add Customers
    String[] Q1 = new String[2];
    String[] Q2 = new String[3];
    String[] Q3 = new String[5];
    //Store Served Customer Data(Multidimensional Array)
    Object[][] servedCustomerData = new Object[10][4];
    //Control Program Execution
    static boolean runProgram;

    public static void main(String[] args) {
        new Main().runMenu();
    }

    public void runMenu() {
        String choice;
        //Set True to Continue Execution
        runProgram = true;

        do {
            //Print Menu
            printMenu();
            //Get Customer's Choice
            choice = validInput("Enter Your Menu Option : ").toUpperCase();

            //Perform Customer;s Choice
            switch (choice) {
                case "100", "VFQ":
                    viewAllQueues();
                    break;
                case "101", "VEQ":
                    viewAllEmptyQueues();
                    break;
                case "102", "ACQ":
                    addCustomerToQueue();
                    break;
                case "103", "RCQ":
                    checkRemoveCustomerFromQueue();
                    runMenu();
                    break;
                default:
                    System.out.println("\nInvalid Menu Option! Please Check the Menu Options and Retry\n");
            }
        } while (runProgram);
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

        do {
            System.out.print("\n**********************************************\n" + prompt);
            input = scan.nextLine().strip();

            if (!input.matches("[a-zA-Z0-9\\s]+")) {
                System.out.println("\nInvalid Menu Option! Please Check the Menu Options and Retry\n");
            }
        } while (!input.matches("[a-zA-Z0-9\\s]+"));
        return input;
    }

    public static String validString(String prompt) {
        Scanner scan = new Scanner(System.in);
        String input;

        do {
            System.out.print("\n**********************************************\n" + prompt);
            input = scan.nextLine().strip();

            if (!input.matches("[a-zA-Z\\s]+")) {
                System.out.println("\nInvalid Input! Please Check \n");
            }
        } while (!input.matches("[a-zA-Zw\\s]+"));
        return input;
    }

    public static String validNumber(String prompt) {
        Scanner scan = new Scanner(System.in);
        String input;

        do {
            System.out.print("\n**********************************************\n" + prompt);
            input = scan.nextLine().strip();
            if (!input.matches("[0-9\\s]+")) {
                System.out.println("\nInvalid Invalid! Please Check\n");
            }
        } while (!input.matches("[0-9\\s]+"));
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
            System.out.println("\nQueue One Has Empty Slots");
        }
        if (checkEmptySlot(Q2)) {
            System.out.println("Queue Two Has Empty Slots");
        }
        if (checkEmptySlot(Q3)) {
            System.out.println("Queue Three Has Empty Slots");
        }
        if (!checkEmptySlot(Q1) && !checkEmptySlot(Q2) && !checkEmptySlot(Q3)) {
            System.out.println("Queues Don't Have Empty Slots");
        }
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
            System.out.println("This Queue is Full!! Choose Another Queue");
            ;
            runMenu();
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


    //
    public void checkRemoveCustomerFromQueue() {
        String choice = validNumber("Enter Queue Number to Remove Customer : ");
        switch (choice) {
            case ("1"):
                removeCustomerFromSlot(Q1);
            case ("2"):
                removeCustomerFromSlot(Q2);
            case ("3"):
                removeCustomerFromSlot(Q3);
            default: {
                System.out.println("Invalid Queue Number!! Please Check");
                checkRemoveCustomerFromQueue();
            }
        }
    }

    //
    public void removeCustomerFromSlot(String[] queue) {
        int slot = Integer.parseInt(validNumber("Enter Slot Number to Remove Customer : "));
        try {
            if (queue[slot] == null) {
                String choice = validString("There is no Customer in this Slot!! Please Check");
                removeCustomerFromSlot(queue);
            } else {
                queue[slot] = null;
                System.out.println("Customer Removed from Queue");
                moveCustomerToUpSlot(queue);
            }
        } catch (Exception IndexOutOfBondsException) {
            String choice = validString("Not Existing Slot Number" + "Press 'y' to Select New Slot Number. Press 'n' for Back to Menu");
            if (choice.equals("y")) {
                removeCustomerFromSlot(queue);
            }
            runMenu();
        }
        //
        public void moveCustomerToUpSlot(String[] queue){
            for (int i = 0; i < queue.length; i++) {
                try {
                    if (queue[i] == null) {
                        queue[i] = queue[i + 1];
                        queue[i + 1] = null;
                    }
                } catch (Exception IndexoutofBoundsException) {
                    break;
                }
            }
        }
    }
}