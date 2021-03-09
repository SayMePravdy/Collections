package commands;

import collection.MyTreeSet;
import data.Ticket;
import processor.Processor;

public class Update extends AbstractCommand{
    private MyTreeSet myTreeSet;
    private Processor processor;

    public Update(String name, MyTreeSet myTreeSet, Processor processor) {
        super(name);
        this.myTreeSet = myTreeSet;
        this.processor = processor;
    }

    @Override
    public void execute() {
        int id = processor.getId();
        if (id != -1) {
            if (!myTreeSet.remove(id)) {
                System.out.println("Element with your id not found");
            } else {
                Ticket ticket = processor.getTicket(myTreeSet, id);
                if (ticket != null)
                    myTreeSet.add(ticket);
                else {
                    System.out.println("Incorrect data in script");
                }
            }
        } else {
            System.out.println("Incorrect id");
        }
    }
}
