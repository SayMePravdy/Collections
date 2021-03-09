package commands;

import collection.MyTreeSet;

public class Show extends AbstractCommand {
    private MyTreeSet myTreeSet;

    public Show(String name, MyTreeSet myTreeSet) {
        super(name);
        this.myTreeSet = myTreeSet;
    }

    @Override
    public void execute() {
        myTreeSet.print();
    }
}
