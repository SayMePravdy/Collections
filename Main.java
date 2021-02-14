import data.Coordinates;
import data.Event;
import data.Ticket;
import data.TicketType;
import exceptions.InvalidArgument;

import static resources.Resources.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.ZonedDateTime;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InvalidArgument {
        String path = "C:\\ИТМО\\Прога\\Laba5\\src\\File.csv";
        StringBuilder dataFile = new StringBuilder();
        try{
            InputStreamReader reader = new InputStreamReader(new FileInputStream(path));
            int x = reader.read();
            while (x != -1) {
                dataFile.append((char) x);
                x = reader.read();
            }
        } catch (FileNotFoundException e){
            System.out.println("File not found. Collection is empty");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(dataFile);

        MyTreeSet treeSet = new MyTreeSet();

        for (String data : dataFile.toString().split("\\n")){

            treeSet.add(getElement(treeSet));
        }


        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();



        while (command != "exit") {
            doCommand(command, treeSet, false);
            command = scanner.nextLine();
        }
    }

    public static void doCommand(String command, MyTreeSet treeSet, boolean file) throws InvalidArgument {
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
                treeSet.add(getElement(treeSet));
                break;
            case "update id":
                System.out.println("Enter id");
                Scanner scanner = new Scanner(System.in);
                int id = checkId(scanner.nextLine());
                if (id != -1) {
                    if (!treeSet.remove(id)) {
                        System.out.println("Element with your id not found");
                    }
                    treeSet.add(getElement(treeSet));
                } else {
                    System.out.println("Incorrect id");
                }
                break;
            case "remove_by_id id":
                System.out.println("Enter id");
                scanner = new Scanner(System.in);
                id = checkId(scanner.nextLine());
                if (id != -1) {
                    if (!treeSet.remove(id)) {
                        System.out.println("Element with your id not found");
                    }
                }
                break;
            case "clear":
                treeSet.clear();
                break;
            case "add_if_max":
                Ticket ticket = getElement(treeSet);
                if (treeSet.isMax(ticket)) {
                    treeSet.add(ticket);
                } else {
                    System.out.println("Element isn't maximal");
                }
                break;
            case "add_if_min":
                ticket = getElement(treeSet);
                if (treeSet.isMin(ticket)) {
                    treeSet.add(ticket);
                } else {
                    System.out.println("Element isn't minimal");
                }
                break;
            case "remove_greater":
                ticket = getElement(treeSet);
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
            default:
                System.out.println("Command not found");
                break;
        }
    }

    static Ticket getElement(MyTreeSet treeSet, String path) {

        return null;
    }

    public static Ticket getElement(MyTreeSet treeSet) {
        String ticketName, eventName, comment;
        float price, y;
        long discount;
        int minAge;
        double x;
        Integer ticketsCount;
        TicketType ticketType = null;
        Event event = null;
        Scanner scanner = new Scanner(System.in);

        String data;

        System.out.println("Enter ticket name");
        while (true) {
            data = scanner.nextLine();
            try {
                checkName(data);
                break;
            } catch (InvalidArgument e) {
                System.out.println(e.getMessage());
            }
        }
        ticketName = data;

        System.out.println("Enter x-coordinate");
        while (true) {
            data = scanner.nextLine();
            try {
                checkX(data);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Incorrect data. Please enter double argument");
            }
        }
        x = Double.parseDouble(data);

        System.out.println("Enter y-coordinate");
        while (true) {
            data = scanner.nextLine();
            try {
                checkY(data);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Incorrect data. Please enter float argument");
            } catch (InvalidArgument e) {
                System.out.println(e.getMessage());
            }
        }
        y = Float.parseFloat(data);

        Coordinates coordinates = new Coordinates(x, y);

        System.out.println("Enter ticket price");
        while (true) {
            data = scanner.nextLine();
            try {
                checkPrice(data);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Incorrect data. Please enter int argument");
            } catch (InvalidArgument e) {
                System.out.println(e.getMessage());
            }
        }
        price = Float.parseFloat(data);

        System.out.println("Enter discount");
        while (true) {
            data = scanner.nextLine();
            try {
                checkDiscount(data);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Incorrect data. Please enter long argument");
            } catch (InvalidArgument e) {
                System.out.println(e.getMessage());
            }
        }
        discount = Long.parseLong(data);

        System.out.println("Enter comment");
        while (true) {
            data = scanner.nextLine();
            try {
                checkComment(data);
                break;
            } catch (InvalidArgument e) {
                System.out.println(e.getMessage());
            }
        }
        comment = data;

        System.out.println("Enter ticket type. Don't enter anything if you don't want");
        data = scanner.nextLine();
        if (!data.isEmpty()) {
            while (true) {
                data = scanner.nextLine();
                try {
                    checkTicketType(data);
                    break;
                } catch (InvalidArgument e) {
                    System.out.println(e.getMessage());
                }
            }
            switch (data) {
                case "VIP":
                    ticketType = TicketType.VIP;
                    break;
                case "USUAL":
                    ticketType = TicketType.USUAL;
                    break;
                case "BUDGETARY":
                    ticketType = TicketType.BUDGETARY;
                    break;
                case "CHEAP":
                    ticketType = TicketType.CHEAP;
                    break;
            }
        }

        System.out.println("Do you want to enter an event? Don't enter anything if you don't want");
        data = scanner.nextLine();
        if (!data.isEmpty()) {
            treeSet.incrementEvent();

            System.out.println("Enter event name");
            while (true) {
                data = scanner.nextLine();
                try {
                    checkName(data);
                    break;
                } catch (InvalidArgument e) {
                    System.out.println(e.getMessage());
                }
            }
            eventName = data;

            System.out.println("Enter minimal age");
            while (true) {
                data = scanner.nextLine();
                try {
                    checkMinAge(data);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Incorrect data. Please enter int argument");
                }
            }
            minAge = Integer.parseInt(data);

            System.out.println("Enter tickets count");
            while (true) {
                data = scanner.nextLine();
                try {
                    checkTicketsCount(data);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Incorrect data. Please enter int argument");
                } catch (InvalidArgument e) {
                    System.out.println(e.getMessage());
                }
            }
            ticketsCount = Integer.parseInt(data);

            event = new Event(FIRST_EVENT_ID + treeSet.getCntEvent(), eventName, minAge, ticketsCount);
        }

        return new Ticket(FIRST_TICKET_ID + treeSet.size(), ticketName, coordinates, ZonedDateTime.now(), price, discount, comment, ticketType, event);

    }

    public static Ticket getElementFromFile(MyTreeSet treeSet) {
        return null;
    }

    static int checkId(String data) {
        try {
            int id = Integer.parseInt(data);
            return id;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    static void checkName(String data) throws InvalidArgument {
        if (data.isEmpty())
            throw new InvalidArgument("Incorrect name");
    }

    static void checkX(String data) throws NumberFormatException {
        Double.parseDouble(data);
    }

    static void checkY(String data) throws InvalidArgument, NumberFormatException {
        if (Float.parseFloat(data) > MAX_Y) {
            throw new InvalidArgument(String.format("y-coordinate must be less than %f", MAX_Y));
        }
    }

    static void checkPrice(String data) throws InvalidArgument, NumberFormatException {
        if (Float.parseFloat(data) <= MIN) {
            throw new InvalidArgument(String.format("Price must be more then %f", MIN));
        }
    }

    static void checkDiscount(String data) throws InvalidArgument, NumberFormatException {
        long discount = Long.parseLong(data);
        if (discount <= MIN || discount > MAX_DISCOUNT) {
            throw new InvalidArgument(String.format("Discount can be from %d to %d", MIN, MAX_DISCOUNT));
        }
    }

    static void checkComment(String data) throws InvalidArgument {
        if (data.isEmpty()) {
            throw new InvalidArgument("Incorrect comment");
        }
    }

    static void checkMinAge(String data) throws NumberFormatException {
        Integer.parseInt(data);
    }

    static void checkTicketsCount(String data) throws NumberFormatException, InvalidArgument {
        if (Integer.parseInt(data) <= MIN) {
            throw new InvalidArgument(String.format("Tickets count must be more than %d", MIN));
        }
    }

    static void checkTicketType(String data) throws InvalidArgument {
        if (!data.isEmpty() && !data.equals("CHEAP") && !data.equals("VIP") && !data.equals("USUAL") && !data.equals("BUDGETARY")) {
            throw new InvalidArgument("Could not find this ticket type");
        }
    }

}
