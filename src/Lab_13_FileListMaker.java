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
            try {
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
                        needsToBeSaved = true;
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
                        moveItem(pipe);
                        needsToBeSaved = true;
                        break;
                    case "O":
                    case "o":
                        if(needsToBeSaved) {
                            safeToOpenCheck(pipe, true);
                        }
                        else {
                            safeToOpenCheck(pipe, false);
                        }
                        needsToBeSaved = false;
                        break;
                    case "S":
                    case "s":
                        saveList(pipe);
                        needsToBeSaved = false;
                        break;
                    case "V":
                    case "v":
                        displayList();
                        break;
                    case "Q":
                    case "q":
                        if(needsToBeSaved) {
                            surelyDone = quit(pipe, true);
                        }
                        else {
                            surelyDone = quit(pipe, false);
                        }
                        break;
                }
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } catch (Throwable e) {
                throw new RuntimeException(e);
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
            int userInt = SafeInput.getRangedInt(pipe, "Type in the location of the item you want removed [0-" + realSize + "]. Location ", "There is no item at that location.", 0, realSize);
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
        System.out.print("--------------- MENU ----------------\n");
        System.out.print("A – Add an item to the list\n");
        System.out.print("C - Clear the list\n");
        System.out.print("D – Delete an item from the list\n");
        System.out.print("I – Insert an item into the list\n");
        System.out.print("M - Move an item to another location\n");
        System.out.print("O - Open a list\n");
        System.out.print("S - Save current list\n");
        System.out.print("V – View the list\n");
        System.out.print("Q – Quit the program\n");
        System.out.print("-------------------------------------\n");
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
        String fileToOpenWithExtension = fileToOpen + ".txt";
        File withExtension = new File(fileToOpenWithExtension);
        if(f.exists() && !f.isDirectory()) {
            try(Scanner myReader = new Scanner(f)) {
                list = new ArrayList<>(Files.readAllLines(Paths.get(fileToOpen)));
                displayList();
            } catch (FileNotFoundException e) {
                System.out.println("There is no file named" + f);
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else if(withExtension.exists() && !withExtension.isDirectory()) {
            System.out.println("The file you tried to open was saved as a text file, so it has the file extension, '.txt.");
            System.out.println("Enter the name of the file again, with the '.txt' extension.");
            openList(pipe);
        }
        else {
            System.out.println("No such file found.");
        }
    }

    private static void moveItem(Scanner pipe) {
        Integer currentSizeOfList = list.size();
        int locationOfItem = SafeInput.getRangedInt(pipe, "What is the current location of the item you want moved? ", "There is no item at that location", 0, currentSizeOfList);
        String toBeAdded = list.get(locationOfItem);
        int userInt = SafeInput.getRangedInt(pipe, "Where would you like to add '" + toBeAdded + "'? Location ", "There is no item at that location.", 0, currentSizeOfList);
        list.remove(locationOfItem);
        list.add(userInt, toBeAdded);
        pipe.nextLine();
    }

    private static void safeToOpenCheck(Scanner pipe, boolean needsToBeSaved) {
        if (!needsToBeSaved) {
            openList(pipe);
        }
        else {
            System.out.println("If you open a new list now, your current list will be lost.");
            String userChoice = SafeInput.getRegExString(pipe,"Press S to save the list and continue, press Q to continue without saving.", "[SsQq]");
            if(userChoice.equalsIgnoreCase("S")) {
                saveList(pipe);
                openList(pipe);
            }
            else { // (if they press Q or q)
                openList(pipe);
            }
        }
    }

    private static void saveList(Scanner pipe) {
        System.out.println("What would you like to name the file?");
        String fileName = pipe.nextLine();
        String fileNameWithExtension = fileName + ".txt";
        Path path = Paths.get(fileNameWithExtension);
        try {
            Files.write(path, list);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean quit(Scanner pipe, boolean needsToBeSaved) {
        boolean done = false;
        if (needsToBeSaved) {
            System.out.println("If you quit the program now, you will the updates you have made to your list.");
            String userChoice = SafeInput.getRegExString(pipe,"Press S to save the list before closing, press Q to quit without saving.", "[SsQq]");
            if(userChoice.equalsIgnoreCase("S")) {
                saveList(pipe);
                done = true;
            }
            else {
                done = true;
            }
        }
        else {
            System.out.println("Goodbye!");
            done = true;
        }
        return done;
    }
}