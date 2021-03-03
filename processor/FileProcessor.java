package processor;

import collection.MyTreeSet;
import data.Coordinates;
import data.Event;
import data.Ticket;
import exceptions.InvalidArgument;
import exceptions.WrongArgumentCount;
import exceptions.NullTicketArgument;
import main.Execute;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.ZonedDateTime;

import static resources.Resources.*;

/**
 * Класс, отвечающий за работу с входными данными из файла
 */

public class FileProcessor extends Processor {
    final private String path;
    private boolean script;
    private String[] dataFile;
    private int lineNum = 0;
    private boolean exit = false;

    public FileProcessor(String path, boolean script) throws IOException {
        this.path = path;
        this.script = script;
        dataFile = read().split("\n");
        for (int i = 0; i < dataFile.length; i++) {
            dataFile[i] = dataFile[i].replace("\r", "");
        }
    }

    public boolean isExit() {
        return exit;
    }

    public String read() throws IOException {
        StringBuilder dataFile = new StringBuilder();

        InputStreamReader reader = new InputStreamReader(new FileInputStream(path));
        int x = reader.read();
        while (x != -1) {
            dataFile.append((char) x);
            x = reader.read();
        }

        reader.close();
        return dataFile.toString();
    }

    @Override
    public void readData(MyTreeSet treeSet) {
        if (script) {
            while (lineNum < dataFile.length) {
                Execute.doCommand(dataFile[lineNum++], treeSet, this);
            }
        } else {
            int num = 1;
            for (String data : dataFile) {
                Ticket ticket = getTicketFromCSV(treeSet, data);
                if (ticket != null) {
                    treeSet.add(ticket);
                } else {
                    System.out.println("Incorrect data in line: " + num);
                }
                num++;
            }
        }
    }

    private Ticket getTicketFromCSV(MyTreeSet treeSet, String data) {
        String[] args = data.split(",");
        try {
            checkName(args[0]);
            event = null;
            ticketType = null;
            ticketName = args[0];
            x = Double.parseDouble(args[1]);
            y = checkY(args[2]);
            price = checkPrice(args[3]);
            discount = checkDiscount(args[4]);
            checkComment(args[5]);
            comment = args[5];
            if (args.length > 6) {
                if (args.length == 7) {
                    ticketType = checkTicketType(args[6]);
                } else {
                    if (args.length == 10) {
                        treeSet.incrementEvent();
                        ticketType = checkTicketType(args[6]);
                        checkName(args[7]);
                        eventName = args[7];
                        minAge = Integer.parseInt(args[8]);
                        ticketsCount = checkTicketsCount(args[9]);
                        event = new Event(FIRST_EVENT_ID + treeSet.getCntEvent(), eventName, minAge, ticketsCount);
                    } else {
                        if (args.length == 9) {
                            treeSet.incrementEvent();
                            checkName(args[6]);
                            eventName = args[6];
                            minAge = Integer.parseInt(args[7]);
                            ticketsCount = checkTicketsCount(args[8]);
                            event = new Event(FIRST_EVENT_ID + treeSet.getCntEvent(), eventName, minAge, ticketsCount);
                        } else {
                            throw new WrongArgumentCount("");
                        }
                    }
                }
            }
        } catch (InvalidArgument | NullTicketArgument | NumberFormatException | WrongArgumentCount | ArrayIndexOutOfBoundsException e) {
            return null;
        }
        return new Ticket(FIRST_TICKET_ID + treeSet.size(), ticketName, new Coordinates(x, y), ZonedDateTime.now(), price, discount, comment, ticketType, event);
    }


    @Override
    public Ticket getTicket(MyTreeSet treeSet) {
        int skip = 0;
        boolean mayNull = false;
        try {
            checkName(dataFile[lineNum]);
            event = null;
            ticketType = null;
            ticketName = dataFile[lineNum++];
            x = Double.parseDouble(dataFile[lineNum++]);
            y = checkY(dataFile[lineNum++]);
            price = checkPrice(dataFile[lineNum++]);
            discount = checkDiscount(dataFile[lineNum++]);
            checkComment(dataFile[lineNum++]);
            skip++;
            mayNull = true;
            ticketType = checkTicketType(dataFile[lineNum++]);
            checkName(dataFile[lineNum]);
            eventName = dataFile[lineNum++];
            skip++;
            minAge = Integer.parseInt(dataFile[lineNum++]);
            skip++;
            ticketsCount = checkTicketsCount(dataFile[lineNum++]);
            treeSet.incrementEvent();
            event = new Event(FIRST_EVENT_ID + treeSet.getCntEvent(), eventName, minAge, ticketsCount);
        } catch (NullTicketArgument | ArrayIndexOutOfBoundsException e) {
            //nullTicketArgument.printStackTrace();
            lineNum -= skip;
            return new Ticket(FIRST_TICKET_ID + treeSet.size(), ticketName, new Coordinates(x, y), ZonedDateTime.now(), price, discount, comment, ticketType, event);
        } catch (InvalidArgument e) {
            return null;
        } catch (NumberFormatException e) {
            if (mayNull) {
                lineNum -= skip;
                return new Ticket(FIRST_TICKET_ID + treeSet.size(), ticketName, new Coordinates(x, y), ZonedDateTime.now(), price, discount, comment, ticketType, event);
            } else {
                return null;
            }
        }
        return new Ticket(FIRST_TICKET_ID + treeSet.size(), ticketName, new Coordinates(x, y), ZonedDateTime.now(), price, discount, comment, ticketType, event);
    }

    @Override
    public int getId() {
        try {
            int id = Integer.parseInt(dataFile[lineNum++]);
            return id;
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return -1;
        }
    }

    @Override
    public String getName() {
        try {
            return dataFile[lineNum++];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error");
        }
        return "";
    }

}
