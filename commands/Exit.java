package commands;

public class Exit extends AbstractCommand {

    public Exit(String name) {
        super(name);
    }

    @Override
    public void execute() {
        exit();
    }
}
