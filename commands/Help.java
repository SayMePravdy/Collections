package commands;

import static resources.Resources.HELP;

public class Help extends AbstractCommand {

    public Help(String name) {
        super(name);
    }

    @Override
    public void execute() {
        System.out.println(HELP);
    }
}
