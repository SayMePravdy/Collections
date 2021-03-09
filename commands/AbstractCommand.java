package commands;

public abstract class AbstractCommand implements Command{
    private String name;
    protected boolean exit = false;

    public AbstractCommand(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isExit() {
        return exit;
    }

    public void exit() {
        exit = true;
    }
}
