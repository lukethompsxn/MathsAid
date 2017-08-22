package sample;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileDirector {
    protected static ArrayList<String> _creationList;
    protected String _path;

    public FileDirector() {
        _path = System.getProperty("user.dir");
    }

    public void directoryTest() {
        _creationList = new ArrayList<>();
        if (!(new File(_path + "/data").exists())) {
            File dir = new File(_path + "/data");
            dir.mkdir();
        }
    }

    public ArrayList<String> populateList() {
        File dir = new File(_path + "/data");
        File[] files = dir.listFiles();
        for (File file: files) {
            if (file.isDirectory() && !_creationList.contains(file.getName())) {
                _creationList.add(file.getName());
            }
        }
        return _creationList;
    }

    public void deleteDirectory(String creation) {
        File dirPath = new File(_path + "/data/" + creation);
        File[] contents = dirPath.listFiles();
        if (contents != null) {
            for (File f : contents) {
                f.delete();
            }
        }
        dirPath.delete();
    }

    public boolean createDirectory(String creation) {
        if (!(new File(_path + "/data/" + creation).exists())) {
            File dir = new File(_path + "/data/" + creation);
            dir.mkdir();
        }
        else {
            return false;
        }
        return true;
    }


}
