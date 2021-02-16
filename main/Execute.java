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
import java.util.Scanner;


/**
 * @autor Соснило Михаил, P3113
 * Класс отвечающий за выполнение интерактивного режима
*/
public class Execute {
    /**
     * Переменная окружения выходного файла
     */
    private final static String output = "C:\\ИТМО\\Прога\\Laba5\\src\\resources\\Output.txt";

    public static void main(String[] args) throws InvalidArgument {
        String path = "C:\\ИТМО\\Прога\\Laba5\\src\\resources\\File.csv";

        MyTreeSet treeSet = new MyTreeSet();

        FileProcessor fileProcessor = new FileProcessor(path, false);
        fileProcessor.readData(treeSet);

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
                }
                break;
            case "update":
                int id = processor.getId();
                if (id != -1) {
                    if (!treeSet.remove(id)) {
                        System.out.println("Element with your id not found");
                    } else {
                        treeSet.add(processor.getTicket(treeSet, id));
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
                if (treeSet.isMax(ticket)) {
                    treeSet.add(ticket);
                } else {
                    System.out.println("Element isn't maximal");
                }
                break;
            case "add_if_min":
                ticket = processor.getTicket(treeSet);
                if (treeSet.isMin(ticket)) {
                    treeSet.add(ticket);
                } else {
                    System.out.println("Element isn't minimal");
                }
                break;
            case "remove_greater":
                ticket = processor.getTicket(treeSet);
                treeSet.headSet(ticket, false);
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
                String path = processor.getName();
                FileProcessor fileProcessor = new FileProcessor(path, true);
                fileProcessor.readData(treeSet);
                if (fileProcessor.isExit()){
                    exit = true;
                }
                break;
            case "save":
                try {
                    FileWriter fileWriter = new FileWriter(output);
                    treeSet.save(fileWriter);
                    fileWriter.close();
                } catch (IOException e){
                    System.out.println("Output file is missing");
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
