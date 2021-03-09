package commands;

import collection.MyTreeSet;
import processor.FileProcessor;
import processor.Processor;

import java.io.File;
import java.io.IOException;
import java.util.NavigableSet;
import java.util.TreeSet;

public class ExecuteScript extends AbstractCommand {
    private MyTreeSet myTreeSet;
    private Processor processor;
    private NavigableSet<File> scripts = new TreeSet<>();
    //private boolean exit = false;

    public ExecuteScript(String name, MyTreeSet myTreeSet, Processor processor, NavigableSet<File> scripts) {
        super(name);
        this.myTreeSet = myTreeSet;
        this.processor = processor;
        this.scripts = scripts;
    }

    @Override
    public void execute() {
        String str = processor.getName();
        File file = new File(str);
        if (scripts.contains(file)) {
            System.out.println("Error! Scripts call each other");
        } else {
            scripts.add(file);
            FileProcessor fileProcessor = null;
            try {
                fileProcessor = new FileProcessor(file.getPath(), true);
                fileProcessor.readData(myTreeSet);
                if (fileProcessor.isExit()) {
                    exit = true;
                }
            } catch (IOException | NullPointerException e) {
                System.out.println("Something wrong with the file which you enter");
            }
            scripts.remove(file);
        }
    }

}
