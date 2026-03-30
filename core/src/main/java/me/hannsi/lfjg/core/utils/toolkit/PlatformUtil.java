package me.hannsi.lfjg.core.utils.toolkit;

import me.hannsi.lfjg.core.utils.Util;
import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;
import org.apache.logging.log4j.util.OsgiServiceLocator;

public class PlatformUtil extends Util {
    public static OS getOS(){
        String osName = System.getProperty("os.name");

        OS current;
        if(osName.startsWith(OS.WINDOWS.getName())){
            current = OS.WINDOWS;
        }else if(osName.startsWith(OS.FREE_BSD.getName())){
            current = OS.FREE_BSD;
        }else if(osName.startsWith(OS.LINUX.getName())){
            current = OS.LINUX;
        }else if(osName.startsWith(OS.MAC_OS.getName())){
            current = OS.MAC_OS;
        }else {
            throw new LinkageError("Unknown os: " + osName);
        }

        return current;
    }

    public enum OS implements IEnumTypeBase{
        FREE_BSD(0,"FreeBSD"),
        LINUX(1,"Linux"),
        MAC_OS(2,"Mac OS X"),
        WINDOWS(3,"Windows");

        final int id;
        final String name;

        OS(int id,String name){
            this.id = id;
            this.name = name;
        }

        @Override
        public int getId() {
            return id;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}
