package is.this_way.better.filesystem.gui.elements;

import java.util.Observable;
import java.util.Observer;
import java.util.TreeSet;

public class ProcessList extends Observable  {
    private static ProcessList selfRepresentation;
    private TreeSet<String> processes;
    private ProcessLog processLog;

    public static ProcessList getSelfRepresentation(){
        return selfRepresentation;
    }

    public ProcessList(Observer observer){
        this.processes = new TreeSet<>();
        selfRepresentation = this;
        this.addObserver(observer);
    }
    public void addProcess(String process){
        processes.add(process);
        setChanged();
        notifyObservers();
    }
    public void removeProcess(Object process){
        processes.remove(process);
        setChanged();
        notifyObservers();
    }

    public boolean isEmpty(){
        return processes.size() == 0 ? true : false;
    }

}
