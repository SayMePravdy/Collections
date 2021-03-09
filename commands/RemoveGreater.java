package commands;

import collection.MyTreeSet;
import data.Ticket;
import processor.Processor;

public class RemoveGreater extends AbstractCommand {
    private MyTreeSet myTreeSet;
    private Processor processor;

    public RemoveGreater(String name, MyTreeSet myTreeSet, Processor processor) {
        super(name);
        this.myTreeSet = myTreeSet;
        this.processor = processor;
    }

    @Override
    public void execute() {
        Ticket ticket = processor.getTicket(myTreeSet);
        if (ticket != null) {
            myTreeSet.removeGreater(ticket);
        } else {
            System.out.println("Incorrect data in script");
        }
    }
}
