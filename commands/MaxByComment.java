package commands;

import collection.MyTreeSet;

public class MaxByComment extends AbstractCommand {
    private MyTreeSet myTreeSet;

    public MaxByComment(String name, MyTreeSet myTreeSet) {
        super(name);
        this.myTreeSet = myTreeSet;
    }

    @Override
    public void execute() {
        System.out.println(myTreeSet.maxComment());
    }
}
