import java.util.ArrayList;
import java.util.Scanner;

public class Lab_13_FileListMaker {
    static ArrayList<String> list = new ArrayList<>();

    public static void main(String[] args) {
        Scanner pipe = new Scanner(System.in);
        boolean surelyDone = false;
        do {
            displayMenu();
            String menuChoice = SafeInput.getRegExString(pipe, "Choose what you would like to do:", "[AaDdIiPpQq]");
            switch(menuChoice) {
                case "A":
                case "a":
                    addToList(pipe, "What would you like to add to the list?");
                    break;
                case "D":
                case "d":
                    deleteItem(pipe);
                    break;
                case "I":
                case "i":
                    insertItem(pipe);
                    break;
                case "P":
                case "p":
                    displayList();
                    break;
                case "Q":
                case "q":
                    surelyDone = SafeInput.getYNConfirm(pipe, "Quitting the program will automatically delete your list. Are you sure you want to quit? ");
                    break;
            }
        }while(!surelyDone);
    }

    // METHODS
    private static void addToList(Scanner pipe, String prompt) {
        System.out.print(prompt + " ");
        list.add(pipe.nextLine());
    }

    private static void deleteItem(Scanner pipe) {
        Integer offSize = list.size();
        Integer realSize = offSize - 1;
        if(list.isEmpty()) {
            System.out.println("You can't remove anything because there is nothing in your list!");
        }
        else {
            int userInt = SafeInput.getRangedInt(pipe, "Type in the location of the item you want removed [0-" + realSize + "]. Location", "There is no item at that location.", 0, realSize);
            list.remove(userInt);
            pipe.nextLine();
        }
    }

    private static void displayList() {
        int num = -1;
        if(list.isEmpty()) {
            System.out.println("Your list is empty!");
        }
        else {
            System.out.println("---- LIST ----");
            for(String l : list) {
                num = num + 1;
                System.out.println(num + " " + l);
            }
            System.out.println("--------------");
        }
    }

    private static void displayMenu() {
        System.out.print("------------- MENU --------------\n");
        System.out.print("A – Add an item to the list\n");
        System.out.print("D – Delete an item from the list\n");
        System.out.print("I – Insert an item into the list\n");
        System.out.print("P – Print the list\n");
        System.out.print("Q – Quit the program\n");
        System.out.print("---------------------------------\n");
    }

    private static void insertItem(Scanner pipe) {
        Integer currentSizeOfList = list.size();
        System.out.print("What would you like to add to the list? ");
        String toBeAdded = pipe.nextLine();
        int userInt = SafeInput.getRangedInt(pipe, "Where would you like to add '" + toBeAdded + "'? Location", "There is no item at that location.", 0, currentSizeOfList);
        list.add(userInt, toBeAdded);
        pipe.nextLine();
    }

    private static void listWithLocation() {
        int num = -1;
        for(String l : list) {
            num = num + 1;
            System.out.println(num + " " + l);
        }
    }
}