package commands;

import collection.MyTreeSet;

public class Info extends AbstractCommand{
    private MyTreeSet myTreeSet;

    public Info(String name, MyTreeSet myTreeSet) {
        super(name);
        this.myTreeSet = myTreeSet;
    }

    @Override
    public void execute() {
        myTreeSet.showInfo();
    }
}
