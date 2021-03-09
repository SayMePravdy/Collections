package commands;

import collection.MyTreeSet;
import processor.Processor;

import java.io.FileWriter;
import java.io.IOException;

public class Save extends AbstractCommand {
    private MyTreeSet myTreeSet;
    private String path;

    public Save(String name, MyTreeSet myTreeSet,String path) {
        super(name);
        this.myTreeSet = myTreeSet;
        this.path = path;
    }

    @Override
    public void execute() {
        try (FileWriter fileWriter = new FileWriter(path)){
            myTreeSet.save(fileWriter);
            System.out.println("Data is saved in " + path);
        } catch (IOException e) {
            System.out.println("You have no rights");
        } catch (NullPointerException e) {
            System.out.println("File not found");
        }
    }
}
