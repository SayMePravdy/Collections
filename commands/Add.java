package commands;

import collection.MyTreeSet;
import data.Ticket;
import processor.Processor;

public class Add extends AbstractCommand {
    private MyTreeSet myTreeSet;
    private Processor processor;

    public Add(String name, MyTreeSet myTreeSet, Processor processor) {
        super(name);
        this.myTreeSet = myTreeSet;
        this.processor = processor;
    }

    @Override
    public void execute() {
        Ticket ticket = processor.getTicket(myTreeSet);
        if (ticket != null) {
            myTreeSet.add(ticket);
        } else {
            System.out.println("Incorrect data in script");
        }
    }
}
