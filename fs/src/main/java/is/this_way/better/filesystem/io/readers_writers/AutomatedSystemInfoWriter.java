package is.this_way.better.filesystem.io.readers_writers;

import is.this_way.better.filesystem.model.high_level_items.SystemInfo;

import java.util.Observable;
import java.util.Observer;

public class AutomatedSystemInfoWriter implements Observer{
    SystemInfoWriter systemInfoWriter;
    public AutomatedSystemInfoWriter(){
        systemInfoWriter = new SystemInfoWriter();
    };
    @Override
    public void update(Observable observable, Object o) {
        systemInfoWriter.write((SystemInfo) observable);
    }
}
