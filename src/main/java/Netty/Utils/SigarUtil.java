package Netty.Utils;

import java.io.File;

import org.hyperic.sigar.win32.EventLog;
import org.hyperic.sigar.win32.Win32Exception;

public class SigarUtil {

    /**
     * set sigar package system variable
     */
    public static final void setSystemVariable() {
        try {
            String classPath = System.getProperty("user.dir") + File.separator + "sigarConf";
            String path = System.getProperty("java.library.path");
//            if (OsCheck.getOperatingSystemType() == OsCheck.OSType.Windows) {
                path += ";" + classPath;
//            } else {
//                path += ":" + classPath;
//            }
            System.setProperty("java.library.path", path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
