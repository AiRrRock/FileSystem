package is.this_way.better.filesystem.io.serialization;

import is.this_way.better.filesystem.model.high_level_items.SystemInfo;
import is.this_way.better.filesystem.io.lowlevel.ConstantsIO;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class SystemInfoDeserializer extends Observable{
    private ArrayList<String> systemInfo;
    public SystemInfoDeserializer(ArrayList<String> systemInfo, Observer observer){
        this.systemInfo=systemInfo;
        this.addObserver(observer);
    }

    public SystemInfo deserialize(){
        setChanged();
        notifyObservers();
        SystemInfo sysInfo = new SystemInfo.SystemInfoBuilder(Integer.parseInt(systemInfo.get(0)),Integer.parseInt(systemInfo.get(1))* ConstantsIO.BYTES_IN_KB)
                .setFileStructurePos(Long.parseLong(systemInfo.get(2)))
                .setOffset(Integer.parseInt(systemInfo.get(3)))
                .build();
        return sysInfo;
    }
}
