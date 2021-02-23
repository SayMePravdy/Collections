package processor;

import collection.MyTreeSet;
import data.Event;
import data.Ticket;
import data.TicketType;
import exceptions.InvalidArgument;
import java.time.ZonedDateTime;
import java.util.Scanner;

import data.Coordinates;

import static resources.Resources.*;

/**
 * Класс отвечающий за работу с данными из консоли
 */
public class ConsoleProcessor extends Processor {

    public Ticket getTicket(MyTreeSet treeSet) {

        Scanner scanner = new Scanner(System.in);

        ticketName = getTicketName(scanner);
        x = getX(scanner);
        y = getY(scanner);

        price = getPrice(scanner);

        discount = getDiscount(scanner);

        comment = getComment(scanner);

        ticketType = getTicketType(scanner);

        event = getEvent(scanner, treeSet);


        return new Ticket(FIRST_TICKET_ID + treeSet.size(), ticketName, new Coordinates(x, y), ZonedDateTime.now(), price, discount, comment, ticketType, event);
    }

    private TicketType getTicketType(Scanner scanner) {
        String data;
        TicketType ticketType = null;
        System.out.println("Enter ticket type. Choose one of:VIP, USUAL, BUDGETARY, CHEAP. Don't enter anything if you don't want");
        data = scanner.nextLine();
        if (!data.isEmpty()) {
            while (true) {
                try {
                    ticketType = checkTicketType(data);
                    break;
                } catch (InvalidArgument e){
                    System.out.println(e.getMessage());
                }
                data = scanner.nextLine();
            }

        }
        return ticketType;
    }

    private Event getEvent(Scanner scanner, MyTreeSet treeSet) {
        String data;
        Event event = null;
        String eventName = "";
        int minAge, ticketsCount;
        System.out.println("Do you want to enter an event? Enter yes or no?");
        data = scanner.nextLine();
        while (!data.equals("yes") && !data.equals("no")){
            System.out.println("Incorrect data");
            data = scanner.nextLine();
        }
        if (data.equals("yes")) {
            treeSet.incrementEvent();

            System.out.println("Enter event name");
            while (true) {
                eventName = scanner.nextLine();
                try {
                    checkName(eventName);
                    break;
                }catch (InvalidArgument e){
                    System.out.println(e.getMessage());
                }
            }

            System.out.println("Enter minimal age");
            while (true) {
                data = scanner.nextLine();
                try {
                    minAge = Integer.parseInt(data);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Incorrect data. Please enter int argument");
                }
            }

            System.out.println("Enter tickets count");
            while (true) {
                data = scanner.nextLine();
                try {
                    ticketsCount = checkTicketsCount(data);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Incorrect data. Please enter int argument");
                } catch (InvalidArgument e) {
                    System.out.println(e.getMessage());
                }
            }

            event = new Event(FIRST_EVENT_ID + treeSet.getCntEvent(), eventName, minAge, ticketsCount);
        }
        return event;
    }

    private String getComment(Scanner scanner) {
        String comment = "";
        System.out.println("Enter comment");
        while (comment.isEmpty()) {
            comment = scanner.nextLine();
            if (comment.isEmpty()) {
                System.out.println("Incorrect comment");
            }
        }
        return comment;
    }

    private long getDiscount(Scanner scanner) {
        String data;
        long discount;
        System.out.println("Enter discount");
        while (true) {
            data = scanner.nextLine();
            try {
                discount = checkDiscount(data);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Incorrect data. Please enter long argument");
            } catch (InvalidArgument e) {
                System.out.println(e.getMessage());
            }
        }
        return discount;
    }

    private float getPrice(Scanner scanner) {
        String data;
        float price;
        System.out.println("Enter ticket price");
        while (true) {
            data = scanner.nextLine();
            try {
                price = checkPrice(data);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Incorrect data. Please enter float argument");
            } catch (InvalidArgument e) {
                System.out.println(e.getMessage());
            }
        }
        return price;
    }

    private float getY(Scanner scanner) {
        float y;
        String data;

        System.out.println("Enter y-coordinate");
        while (true) {
            data = scanner.nextLine();
            try {
                y = checkY(data);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Incorrect data. Please enter float argument");
            } catch (InvalidArgument e) {
                System.out.println(e.getMessage());
            }
        }
        return y;
    }

    private double getX(Scanner scanner) {
        double x;
        String data;

        System.out.println("Enter x-coordinate");
        while (true) {
            data = scanner.nextLine();
            try {
                x = Double.parseDouble(data);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Enter a double argument");
            }
        }
        return x;
    }

    public int getId(){
        System.out.println("Enter id");
        Scanner scanner = new Scanner(System.in);
        return checkId(scanner.nextLine());
    }

    public String getName() {
        System.out.println("Enter file name");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private String getTicketName(Scanner scanner) {
        String ticketName = "";

        System.out.println("Enter ticket name");
        while (true) {
            ticketName = scanner.nextLine();
            try {
                checkName(ticketName);
                break;
            } catch (InvalidArgument e) {
                System.out.println(e.getMessage());
            }
        }
        return ticketName;
    }


}