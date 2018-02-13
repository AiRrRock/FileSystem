package is.this_way.better.filesystem.model.high_level_items;

import java.util.ArrayList;

public class ProcessLog {
    private ArrayList<ProcessData> processes;
    private int currentPID;
    public ProcessLog(){
        currentPID = 0;
        processes = new ArrayList<>();
    }
    public int addProcess(String name){
        currentPID++;
        processes.add(new ProcessData(name,currentPID,false));
        return currentPID;
    }
    public void updateProcesses(int pid, boolean finished){
        if (processes.contains(pid)){
            int indexOfProcess = processes.indexOf(pid);
            ProcessData pd =processes.get(indexOfProcess);
            processes.remove(indexOfProcess);
            processes.add(new ProcessData(pd.processName,pid,finished));
        }

    }
    private class ProcessData implements Comparable{
        private String processName;
        private int pid;
        private boolean finisehed;
        private ProcessData(String processName, int pid, boolean finished){
            this.processName= processName;
            this.pid = pid;
            this.finisehed=finished;
        }
        @Override
        public int compareTo(Object o) {
            int compereTo = (int) o ;
            return pid == compereTo ? 0 : pid < compereTo ? -1 : 1;
        }


    }
}
