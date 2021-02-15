package processor;

import collection.MyTreeSet;
import data.Event;
import data.Ticket;
import data.TicketType;
import exceptions.InvalidArgument;

import static resources.Resources.*;
import static resources.Resources.MIN;

public abstract class Processor {
    protected String ticketName, eventName, comment;
    protected float price, y;
    protected long discount;
    protected int minAge;
    protected double x;
    protected Integer ticketsCount;
    protected TicketType ticketType = null;
    protected Event event = null;

    public abstract Ticket getTicket(MyTreeSet treeSet);
    public Ticket getTicket(MyTreeSet treeSet, int id){
        Ticket ticket = getTicket(treeSet);
        ticket.setId(id);
        return ticket;
    }
    public abstract int getId();
    public abstract String getName();

    public static int checkId(String data) {
        try {
            int id = Integer.parseInt(data);
            return id;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    static void checkName(String data) throws InvalidArgument {
        if (data.isEmpty()){
            throw new InvalidArgument("Incorrect name");
        }
    }

    static float checkY(String data) throws InvalidArgument, NumberFormatException {
        float y = Float.parseFloat(data);
        if (y > MAX_Y) {
            throw new InvalidArgument(String.format("y-coordinate must be less than %f", MAX_Y));
        }
        return y;
    }

    static float checkPrice(String data) throws InvalidArgument, NumberFormatException {
        float price = Float.parseFloat(data);
        if (price <= MIN) {
            throw new InvalidArgument(String.format("Price must be more then %f", MIN));
        }
        return price;
    }

    static long checkDiscount(String data) throws InvalidArgument, NumberFormatException {
        long discount = Long.parseLong(data);
        if (discount <= MIN || discount > MAX_DISCOUNT) {
            throw new InvalidArgument(String.format("Discount can be from %d to %d", MIN, MAX_DISCOUNT));
        }
        return discount;
    }

    static void checkComment(String data) throws InvalidArgument {
        if (data.isEmpty()) {
            throw new InvalidArgument("Incorrect comment");
        }

    }

    static int checkTicketsCount(String data) throws NumberFormatException, InvalidArgument {
        int ticketsCount = Integer.parseInt(data);
        if (ticketsCount <= MIN) {
            throw new InvalidArgument(String.format("Tickets count must be more than %d", MIN));
        }
        return ticketsCount;
    }

    static TicketType checkTicketType(String data) throws InvalidArgument {
        switch (data) {
            case "VIP":
                return TicketType.VIP;
            case "USUAL":
                return TicketType.USUAL;
            case "BUDGETARY":
                return TicketType.BUDGETARY;
            case "CHEAP":
                return TicketType.CHEAP;
            default:
                throw new InvalidArgument("Could not find this ticket type");
        }
    }
}
