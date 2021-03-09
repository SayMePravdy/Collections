package commands;

import collection.MyTreeSet;

public class Clear extends AbstractCommand{
    private MyTreeSet myTreeSet;

    public Clear(String name, MyTreeSet myTreeSet) {
        super(name);
        this.myTreeSet = myTreeSet;
    }

    @Override
    public void execute() {
        myTreeSet.clear();
    }
}
