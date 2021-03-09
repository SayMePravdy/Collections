package commands;

import collection.MyTreeSet;

public class SumOfDiscount extends AbstractCommand {
    private MyTreeSet myTreeSet;

    public SumOfDiscount(String name, MyTreeSet myTreeSet) {
        super(name);
        this.myTreeSet = myTreeSet;
    }

    @Override
    public void execute() {
        System.out.println(myTreeSet.sumDiscount());
    }
}
