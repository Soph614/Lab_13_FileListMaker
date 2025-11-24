import java.util.Scanner;

public class SafeInput {
    public static void main(String[] args) {
        Scanner pipe = new Scanner(System.in);
    }

    public static String getNonZeroLenString(Scanner pipe, String prompt) {
        String retString = "";
        do {
            System.out.print("\n" + prompt + " ");
            retString = pipe.nextLine();
        }while(retString.length() == 0);
        return retString;
    }

    public static int getInt(Scanner pipe, String prompt) {
        boolean done = false;
        do {
            prompt = "Please give an integer";
            System.out.print("\n" + prompt);
            if (pipe.hasNextInt()) {
                int userInt = pipe.nextInt();
                System.out.println("Your integer is " + userInt + ".");
                done = true;
            } else if (pipe.hasNextDouble()) {
                double doubleTrash = pipe.nextDouble();
                System.out.println(doubleTrash + " is not an integer.");
            } else {
                String trash = pipe.nextLine();
                System.out.println(trash + " is not an integer.");
            }
        } while (!done);
        return 0;
    }

    public static double getDouble(Scanner pipe, String prompt) {
        boolean done = false;
        prompt = "Please give a double value";
        do {
            System.out.print("\n" + prompt);
            if (pipe.hasNextInt()) {
                int intTrash = pipe.nextInt();
                System.out.println("[" + intTrash + "] is not a double value.");
                pipe.nextLine();
            }
            else if (pipe.hasNextDouble()) {
                double userDouble = pipe.nextDouble();
                System.out.println("Your double value is " + userDouble + ".");
                done = true;
            }
            else {
                String trash = pipe.nextLine();
                System.out.println("[" + trash + "] is not a double value.");
                // pipe.nextLine();
            }
        } while (!done);
        return 0;
    }

    public static int getRangedInt(Scanner pipe, String prompt, String errorMessage, int low, int high) {
        boolean done = false;
        int userInt = 0;
        do {
            System.out.print(prompt);
            if (pipe.hasNextInt()) {
                userInt = pipe.nextInt();
                if(userInt >= low && userInt <= high) {
                    done = true;
                }
                else {
                    System.out.println(errorMessage);
                }

            }
            else if (pipe.hasNextDouble()) {
                double doubleTrash = pipe.nextDouble();
                System.out.println("'" + doubleTrash + "' is not an integer at all.");
                pipe.nextLine(); // clears the buffer
            }
            else {
                String trash = pipe.nextLine();
                System.out.println("'" + trash + "' is not an integer at all.");
                // pipe.nextLine();
            }

        } while(!done);

        return userInt;
    }

    public static double getRangedDouble(Scanner pipe, String prompt, double low, double high) {
        boolean done = false;
        do {
            System.out.print("\n" + prompt);
            if (pipe.hasNextInt()) {
                int trash = pipe.nextInt();
                System.out.println("[" + trash + "] is not a double value at all.");
                pipe.nextLine();

            }
            else if (pipe.hasNextDouble()) {
                double userDouble = pipe.nextDouble();
                if(userDouble >= 1 && userDouble <= 100) {
                    System.out.println("You chose " + userDouble + ".");
                    done = true;
                }
                else {
                    System.out.println("That double value is not between " + low + " and " + high + " inclusive.");
                }
            }
            else {
                String trash = pipe.nextLine();
                System.out.println("[" + trash + "] is not a double value at all.");
                // pipe.nextLine();
            }

        } while(!done);
        return low;
    }

    public static boolean getYNConfirm(Scanner pipe, String prompt) {
        boolean done = true;
        String response = "";
        boolean gotAVal = false;

        do {
            System.out.print("\n" + prompt + " [Y/N]: ");
            response = pipe.nextLine();
            if(response.equalsIgnoreCase("Y")) {
                gotAVal = true;
                done = true;
            }
            else if(response.equalsIgnoreCase("N")) {
                gotAVal = true;
                done = false;
            }
            else {
                System.out.println("You must answer Y or N (" + response + "). Try again: ");
            }
        } while(!gotAVal);

        return done;
    }
    public static String getRegExString(Scanner pipe, String prompt, String regEx) {
        String userNumber = "";
        boolean gotCorrectValue = false;
        // pipe.nextLine();
        do {
            System.out.print(prompt + " ");
            userNumber = pipe.nextLine();
            if(userNumber.matches(regEx)) {
                gotCorrectValue = true;
            }
            else {
                System.out.println("[" + userNumber + "] is incorrect. Please match the pattern " + regEx);
                // pipe.nextLine();
            }
        } while(!gotCorrectValue);
        return userNumber;
    }
}
