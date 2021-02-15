package processor;

import collection.MyTreeSet;
import data.Coordinates;
import data.Event;
import data.Ticket;
import exceptions.InvalidArgument;
import exceptions.WrongArgumentCount;
import main.Main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.ZonedDateTime;

import static resources.Resources.*;

public class FileProcessor extends Processor {

    final private String path;
    private String[] data;
    private int lineNum = 0;
    private boolean script;
    private int skip = 0;
    private boolean exit = false;

    public FileProcessor(String path, boolean script) {
        this.path = path;
        this.script = script;
        if (script)
            skip = 1;
    }

    public String[] getData() {
        return data;
    }

    public boolean isExit() {
        return exit;
    }

    public void setData(String[] data) {
        this.data = data;
    }

    public void readCommand(MyTreeSet treeSet, String line) {
        String[] args = line.split(",");
        String command = args[0];
        String[] data = new String[args.length - 1];
        for (int i = 0; i < data.length; i++) {
            data[i] = args[i + 1];
        }
        setData(data);
        if (Main.doCommand(command, treeSet, this))
            exit = true;
    }

    public void readData(MyTreeSet treeSet) {
        try {
            String[] data = read().split("\\r\\n");
            if (script && !exit) {
                for (String line : data) {
                    readCommand(treeSet, line);
                }
            } else {
                int num = 0;
                for (String line : data) {
                    String[] args = line.split(",");
                    num++;
                    Ticket ticket = getTicket(treeSet);
                    if (ticket != null) {
                        treeSet.add(ticket);
                    } else {
                        System.out.println("Incorrect data in file on line " + num);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("File not found!");
        }


    }

    public String read() throws IOException {
        StringBuilder dataFile = new StringBuilder();

        InputStreamReader reader = new InputStreamReader(new FileInputStream(path));
        int x = reader.read();
        while (x != -1) {
            dataFile.append((char) x);
            x = reader.read();
        }

        return dataFile.toString();
    }

    public int getId() {
        skip = 2;
        try {
            int id = Integer.parseInt(data[0]);
            return id;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public String getName() {
        return data[0];
    }

    public Ticket getTicket(MyTreeSet treeSet) {
        try {
            String line = read().split("\\r\\n")[lineNum];
            lineNum++;
            String[] args;
            if (script) {
                String[] data = line.split(",");
                args = new String[data.length - skip];
                for (int i = 0; i < args.length; i++) {
                    args[i] = data[i + skip];
                }
            } else {
                args = line.split(",");
            }

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
                        } else {
                            if (args.length == 9) {
                                treeSet.incrementEvent();
                                checkName(args[6]);
                                eventName = args[6];
                                minAge = Integer.parseInt(args[7]);
                                ticketsCount = checkTicketsCount(args[8]);
                            } else {
                                throw new WrongArgumentCount("Wrong number of arguments");
                            }
                        }
                    }
                }
            } catch (InvalidArgument | NumberFormatException | WrongArgumentCount | ArrayIndexOutOfBoundsException e) {
                return null;
            }

            return new Ticket(FIRST_TICKET_ID + treeSet.size(), ticketName, new Coordinates(x, y), ZonedDateTime.now(), price, discount, comment, ticketType, new Event(FIRST_EVENT_ID + treeSet.getCntEvent(), eventName, minAge, ticketsCount));
        } catch (IOException e) {
            return null;
        }

    }


}
