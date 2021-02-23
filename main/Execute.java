package main;

import collection.MyTreeSet;
import collection.TicketIdComparator;
import data.Ticket;
import exceptions.InvalidArgument;
import processor.ConsoleProcessor;
import processor.FileProcessor;
import processor.Processor;

import static resources.Resources.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.NavigableSet;
import java.util.Scanner;
import java.util.TreeSet;


/**
 * @autor Соснило Михаил, P3113
 * Класс отвечающий за выполнение интерактивного режима
 */
public class Execute {
    /**
     * Переменная окружения выходного файла
     */
    private static String path;
    private static NavigableSet<String> scripts = new TreeSet<>();

    public static void main(String[] args) throws InvalidArgument {

        path = System.getenv().get("LAB5");


        MyTreeSet treeSet = new MyTreeSet();

        if (path != null) {
            FileProcessor fileProcessor = new FileProcessor(path, false);
            fileProcessor.readData(treeSet);
        } else {
            System.out.println("File not found");
        }


        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a command");
        String command = scanner.nextLine();

        while (true) {
            if (doCommand(command, treeSet, new ConsoleProcessor())) {
                break;
            }
            System.out.println("Enter a command");
            command = scanner.nextLine();
        }
    }

    /**
     * Метод исполнения введенной команды
     */
    public static boolean doCommand(String command, MyTreeSet treeSet, Processor processor) {
        boolean exit = false;
        switch (command) {
            case "help":
                System.out.println(HELP);
                break;
            case "info":
                treeSet.showInfo();
                break;
            case "show":
                treeSet.print();
                break;
            case "add":
                Ticket ticket = processor.getTicket(treeSet);
                if (ticket != null) {
                    treeSet.add(ticket);
                } else {
                    System.out.println("Incorrect data in script");
                }
                break;
            case "update":
                int id = processor.getId();
                if (id != -1) {
                    if (!treeSet.remove(id)) {
                        System.out.println("Element with your id not found");
                    } else {
                        ticket = processor.getTicket(treeSet, id);
                        if (ticket != null)
                            treeSet.add(ticket);
                        else {
                            System.out.println("Incorrect data in script");
                        }
                    }
                } else {
                    System.out.println("Incorrect id");
                }
                break;
            case "remove_by_id":
                id = processor.getId();
                if (id != -1) {
                    if (!treeSet.remove(id)) {
                        System.out.println("Element with your id not found");
                    }
                } else {
                    System.out.println("Incorrect id");
                }
                break;
            case "clear":
                treeSet.clear();
                break;
            case "add_if_max":
                ticket = processor.getTicket(treeSet);
                if (ticket != null){
                    if (treeSet.isMax(ticket)) {
                        treeSet.add(ticket);
                    } else {
                        System.out.println("Element isn't maximal");
                    }
                } else {
                    System.out.println("Incorrect data in script");
                }

                break;
            case "add_if_min":
                ticket = processor.getTicket(treeSet);
                if (ticket != null){
                    if (treeSet.isMin(ticket)) {
                        treeSet.add(ticket);
                    } else {
                        System.out.println("Element isn't maximal");
                    }
                } else {
                    System.out.println("Incorrect data in script");
                }
                break;
            case "remove_greater":
                ticket = processor.getTicket(treeSet);
                if (ticket != null) {
                    treeSet.headSet(ticket, false);
                }else{
                    System.out.println("Incorrect data in script");
                }
                break;
            case "sum_of_discount":
                System.out.println(treeSet.sumDiscount());
                break;
            case "max_by_comment":
                System.out.println(treeSet.maxComment());
                break;
            case "print_unique_price":
                for (Float price : treeSet.uniquePrices()) {
                    System.out.println(price);
                }
                break;
            case "execute_script":
                String file = processor.getName();
                if (scripts.contains(file)) {
                    System.out.println("Error! Scripts call each other");
                } else {
                    scripts.add(file);
                    FileProcessor fileProcessor = new FileProcessor(file, true);
                    fileProcessor.readData(treeSet);
                    if (fileProcessor.isExit()) {
                        exit = true;
                    }
                    scripts.remove(file);
                }
                break;
            case "save":
                try {
                    FileWriter fileWriter = new FileWriter(path);
                    treeSet.save(fileWriter);
                    fileWriter.close();
                    System.out.println("Data is saved in " + path);
                } catch (IOException e) {
                    System.out.println("You have no rights");
                } catch (NullPointerException e){
                    System.out.println("File not found");
                }
                break;
            case "exit":
                exit = true;
                break;
            default:
                System.out.println("Command not found");
                break;
        }
        return exit;
    }


}
