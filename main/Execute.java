package main;

import collection.MyTreeSet;
import collection.TicketIdComparator;
import commands.*;
import data.Ticket;
import exceptions.CommandNotFoundException;
import exceptions.InvalidArgument;
import processor.ConsoleProcessor;
import processor.FileProcessor;
import processor.Processor;

import static resources.Resources.*;

import java.io.File;
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
    private static NavigableSet<File> scripts = new TreeSet<>();

    public static void main(String[] args) throws InvalidArgument {

        path = System.getenv().get("LAB5");


        MyTreeSet treeSet = new MyTreeSet();

        if (path != null) {
            FileProcessor fileProcessor = null;
            try {
                fileProcessor = new FileProcessor(path, false);
            } catch (IOException e) {
                System.out.println("Something wrong with the file: " + path);
            }
            fileProcessor.readData(treeSet);
        } else {
            System.out.println("File not found");
        }

        ConsoleProcessor consoleProcessor = new ConsoleProcessor();
        consoleProcessor.readData(treeSet);

    }

    public static Command instruction(String command, MyTreeSet treeSet, Processor processor) throws CommandNotFoundException {
        switch (command) {
            case "help":
                return new Help("help");
            case "info":
                return new Info("info", treeSet);
            case "show":
                return new Show("show", treeSet);
            case "add":
                return new Add("add", treeSet, processor);
            case "update":
                return new Update("update", treeSet, processor);
            case "remove_by_id":
                return new RemoveById("remove_by_id", treeSet, processor);
            case "clear":
                return new Clear("clear", treeSet);
            case "add_if_max":
                return new AddIfMax("add_if_max", treeSet, processor);
            case "add_if_min":
                return new AddIfMin("add_if_min", treeSet, processor);
            case "remove_greater":
                return new RemoveGreater("remove_greater", treeSet, processor);
            case "sum_of_discount":
                return new SumOfDiscount("sum_of_discount", treeSet);
            case "max_by_comment":
                return new MaxByComment("max_by_comment", treeSet);
            case "print_unique_price":
                return new PrintUniquePrice("print_unique_price", treeSet);
            case "execute_script":
                return new ExecuteScript("execute_script", treeSet, processor, scripts);
            case "save":
                return new Save("save", treeSet, path);
            case "exit":
                return new Exit("exit");
            default:
                throw new CommandNotFoundException("Command \"" + command + "\" doesn't exist");
        }
    }

    /**
     * Метод исполнения введенной команды
     */
    public static boolean doCommand(String command, MyTreeSet treeSet, Processor processor) {
        boolean exit = false;
        try {
            Command com  = instruction(command, treeSet, processor);
            com.execute();
            if (com.isExit() || processor.isExit()) {
                exit = true;
            }
        } catch (CommandNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return exit;
    }


}
