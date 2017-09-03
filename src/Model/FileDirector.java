package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;

public class FileDirector {
	private ObservableList<String> listOfCreations = FXCollections.observableArrayList();
	private String path;
	private static FileDirector fileDirector;
	private String fileSeperator = File.separator;
	private String currentItem;


	//Singleton Constructor
	public static FileDirector instance() {
		if (fileDirector == null) {
			fileDirector = new FileDirector();
		}
		return fileDirector;
	}

	//Private Constructor
	private FileDirector() {
		path = System.getProperty("user.dir");
		directoryTest();
		populateList();
	}

	//Sets current item from MainViewController when a creation is clicked on
	public void setCurrentItem(String item) {
		currentItem = item;
	}

	//Used in PlayViewController for getting the current creation so its video can be played
	public String getCurrentItem() {
		return currentItem;
	}

	//Performs the directory test to ensure the folder Data exists, if it doesnt exist it creates it
	public void directoryTest() {
		if (!(new File(path + fileSeperator + "data").exists())) {
			File dir = new File(path + fileSeperator + "data");
			dir.mkdir();
		}
	}

	//Called from the constructor of FileDirector, this means it is called on launch of the program to
	//populate a list of creations which were already saved before start up
	public void populateList() {
		File dir = new File(path + fileSeperator + "data");
		File[] files = dir.listFiles();
		for (File file: files) {
			if (file.isDirectory() && !listOfCreations.contains(file.getName()) && new File(file.getAbsolutePath() + fileSeperator + "video.mp4").exists()) {
				listOfCreations.add(file.getName());
			}
		}
	}

	//Called from multiple different controllers whenever a creation needs to be deleted
	public void deleteDirectory() {
		if (currentItem != null && !currentItem.isEmpty()) {
			File dirPath = new File(path + fileSeperator + "data" + fileSeperator + currentItem);
			File[] contents = dirPath.listFiles();
			if (contents != null) {
				for (File f : contents) {
					f.delete();
				}
			}
			dirPath.delete();
			listOfCreations.remove(currentItem);
		}

	}

	//Called from CreateMenuController when a creation is being created
	public boolean createDirectory(String creation) {
		if (!(new File(path + fileSeperator + "data" + fileSeperator + creation).exists())) {
			File dir = new File(path + fileSeperator + "data" + fileSeperator + creation);
			dir.mkdir();
		}
		else {
			return false;
		}
		return true;
	}

	//Called from MainViewController's initialize method to set the ListView to the list of creations on start up
	public ObservableList<String> getList() {
		return listOfCreations;
	}

	//Called from the "cancel" method in recordView to test whether the creation was overwriting another
	public boolean wasOverwriting() {
		if (new File(path + fileSeperator + "data" + fileSeperator + currentItem + fileSeperator + "video.mp4").exists()) {
			return true;
		}
		return false;
	}

	//Called from "CreateMenuController" when a creation is being made
	public void addToList(String creation) {
		listOfCreations.add(creation);
	}


}
