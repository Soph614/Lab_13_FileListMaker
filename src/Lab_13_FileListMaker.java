import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.IOException;

public class Lab_13_FileListMaker {
    static ArrayList<String> list = new ArrayList<>();

    public static void main(String[] args) {
        Scanner pipe = new Scanner(System.in);
        boolean surelyDone = false;
        boolean needsToBeSaved = false;
        do {
            displayMenu();
            String menuChoice = SafeInput.getRegExString(pipe, "Choose what you would like to do:", "[AaCcDdIiMmOoSsVvQq]");
            switch(menuChoice) {
                case "A":
                case "a":
                    addToList(pipe, "What would you like to add to the list?");
                    needsToBeSaved = true;
                    break;
                case "C":
                case "c":
                    clearList();
                    break;
                case "D":
                case "d":
                    deleteItem(pipe);
                    needsToBeSaved = true;
                    break;
                case "I":
                case "i":
                    insertItem(pipe);
                    needsToBeSaved = true;
                    break;
                case "M":
                case "m":
                    break;
                case "O":
                case "o":
                    if(needsToBeSaved) {
                        openListCheck(pipe, true);
                    }
                    else {
                        openListCheck(pipe, false);
                    }
                    break;
                case "S":
                case "s":
                    saveList(pipe);
                    break;
                case "V":
                case "v":
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

    private static void clearList() {
        list.removeAll(list);
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
        System.out.print("C - Clear the list\n");
        System.out.print("D – Delete an item from the list\n");
        System.out.print("I – Insert an item into the list\n");
        System.out.print("M - Move an item to another list\n");
        System.out.print("O - Open a list\n");
        System.out.print("S - Save current list\n");
        System.out.print("V – View the list\n");
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

    private static void openList(Scanner pipe) {
        System.out.println("What list would you like to open? ");
        String fileToOpen = pipe.nextLine();
        File f = new File(fileToOpen);
        if(f.exists() && !f.isDirectory()) {
            try(Scanner myReader = new Scanner(f)) {
                /* while(myReader.hasNextLine()) {
                    String listContent = myReader.nextLine();
                    System.out.println(listContent);
                }
                 */
                list = new ArrayList<>(Files.readAllLines(Paths.get(fileToOpen)));
            } catch (FileNotFoundException e) {
                System.out.println("There is no file named" + f);
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void openListCheck(Scanner pipe, boolean needsToBeSaved) {
        if (!needsToBeSaved) {
            openList(pipe);
        }
        else {
            System.out.println("If you open a new list now, your current list will be lost.");
            String userChoice = SafeInput.getRegExString(pipe,"Press S to save the list and continue, press Q to continue without saving.", "[SsQq]");
            if(userChoice.equals("S")) {
                saveList(pipe);
                openList(pipe);
            }
            else {
                openList(pipe);
            }
        }
    }

    private static void saveList(Scanner pipe) {
        System.out.println("What would you like to name the file?");
        String fileName = pipe.nextLine();
        Path path = Paths.get(fileName);
        try {
            Files.write(path, list);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static void listWithLocation() {
        int num = -1;
        for(String l : list) {
            num = num + 1;
            System.out.println(num + " " + l);
        }
    }
}