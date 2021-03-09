package commands;

import collection.MyTreeSet;
import processor.Processor;

public class RemoveById extends AbstractCommand {
    private MyTreeSet myTreeSet;
    private Processor processor;

    public RemoveById(String name, MyTreeSet myTreeSet, Processor processor) {
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
            }
        } else {
            System.out.println("Incorrect id");
        }
    }
}
