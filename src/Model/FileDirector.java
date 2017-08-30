package Model;

import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;
import javafx.collections.FXCollections;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileDirector {
    private List<String> _listOfCreations = FXCollections.observableArrayList();
    private String _path;
    private static FileDirector fileDirector;
    public String _fileSeperator = File.separator;
    private String _currentItem;


    public static FileDirector instance() {
        if (fileDirector == null) {
            fileDirector = new FileDirector();
        }
        return fileDirector;
    }

    private FileDirector() {
        _path = System.getProperty("user.dir");
        directoryTest();
        populateList();
    }

    public void setCurrentItem(String item) {
        _currentItem = item;
    }

    public String getCurrentItem() {
        return _currentItem;
    }

    public void directoryTest() {
        if (!(new File(_path + _fileSeperator + "data").exists())) {
            File dir = new File(_path + _fileSeperator + "data");
            dir.mkdir();
        }
    }

    public void populateList() {
        File dir = new File(_path + _fileSeperator + "data");
        File[] files = dir.listFiles();
        for (File file: files) {
            if (file.isDirectory() && !_listOfCreations.contains(file.getName())) {
                _listOfCreations.add(file.getName());
            }
        }
    }

    public void deleteDirectory() {
        if (_currentItem != null && !_currentItem.isEmpty()) {
            File dirPath = new File(_path + _fileSeperator + "data" + _fileSeperator + _currentItem);
            File[] contents = dirPath.listFiles();
            if (contents != null) {
                for (File f : contents) {
                    f.delete();
                }
            }
            dirPath.delete();
            _listOfCreations.remove(_currentItem);
        }

    }

    public boolean createDirectory(String creation) {
        if (!(new File(_path + _fileSeperator + "data" + _fileSeperator + creation).exists())) {
            File dir = new File(_path + _fileSeperator + "data" + _fileSeperator + creation);
            dir.mkdir();
        }
        else {
            return false;
        }
        return true;
    }

    public List<String> getList() {
        return _listOfCreations;
    }

    public void addToList(String creation) {
        _listOfCreations.add(creation);
    }


}
