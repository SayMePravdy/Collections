package commands;

import collection.MyTreeSet;

public class PrintUniquePrice extends AbstractCommand {
    private MyTreeSet myTreeSet;

    public PrintUniquePrice(String name, MyTreeSet myTreeSet) {
        super(name);
        this.myTreeSet = myTreeSet;
    }

    @Override
    public void execute() {
        System.out.println(myTreeSet.uniquePrices());
    }
}
