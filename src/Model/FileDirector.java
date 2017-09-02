package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.List;

public class FileDirector {
    private ObservableList<String> _listOfCreations = FXCollections.observableArrayList();
    private String _path;
    private static FileDirector fileDirector;
    private String _fileSeperator = File.separator;
    private String _currentItem;


    //Singleton Constructor
    public static FileDirector instance() {
        if (fileDirector == null) {
            fileDirector = new FileDirector();
        }
        return fileDirector;
    }

    //Private Constructor
    private FileDirector() {
        _path = System.getProperty("user.dir");
        directoryTest();
        populateList();
    }

    //Sets current item from MainViewController when a creation is clicked on
    public void setCurrentItem(String item) {
        _currentItem = item;
    }

    //Used in PlayViewController for getting the current creation so its video can be played
    public String getCurrentItem() {
        return _currentItem;
    }

    //Performs the directory test to ensure the folder Data exists, if it doesnt exist it creates it
    public void directoryTest() {
        if (!(new File(_path + _fileSeperator + "data").exists())) {
            File dir = new File(_path + _fileSeperator + "data");
            dir.mkdir();
        }
    }

    //Called from the constructor of FileDirector, this means it is called on launch of the program to
    //populate a list of creations which were already saved before start up
    public void populateList() {
        File dir = new File(_path + _fileSeperator + "data");
        File[] files = dir.listFiles();
        for (File file: files) {
            if (file.isDirectory() && !_listOfCreations.contains(file.getName())) {
                _listOfCreations.add(file.getName());
            }
        }
    }

    //Called from multiple different controllers whenever a creation needs to be deleted
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

    //Called from CreateMenuController when a creation is being created
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

    //Called from MainViewController's initialize method to set the ListView to the list of creations on start up
    public ObservableList<String> getList() {
        return _listOfCreations;
    }

    //Called from the "cancel" method in recordView to test whether the creation was overwriting another
    public boolean wasOverwriting() {
        if (new File(_path + _fileSeperator + "data" + _fileSeperator + _currentItem + _fileSeperator + "video.mp4").exists()) {
            return true;
        }
        return false;
    }

    //Called from "CreateMenuController" when a creation is being made
    public void addToList(String creation) {
        _listOfCreations.add(creation);
    }

    public void removeFromList(String creation) {
        _listOfCreations.remove(creation);
    }




}
