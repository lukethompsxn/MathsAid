package Controller;

import MathsAid.Main;
import Model.FileDirector;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class RecordViewController {
    String _fileSeperator = File.separator;
    FileDirector model = FileDirector.instance();
    MediaPlayer mediaPlayer;

    @FXML
    private Button recordBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private ProgressBar progressBar;

    class VideoInBackground extends Task<Integer> {

        @Override
        protected Integer call() throws Exception {
            String generateCmd = "ffmpeg -y -f lavfi -i color=c=pink:s=480x360:d=3 -vf \"drawtext=fontsize=50:fontcolor=white:x=(w-text_w)/2:y=(h-text_h)/2:text=" + model.getCurrentItem() + "\" data" + _fileSeperator + model.getCurrentItem() + _fileSeperator + "video.mp4";
            String combineCmd = "ffmpeg -y -i data" + _fileSeperator + model.getCurrentItem() + _fileSeperator + "video.mp4" + " -i data" + _fileSeperator + model.getCurrentItem() + _fileSeperator + "audio.mp3 -c copy data" + _fileSeperator + model.getCurrentItem() + _fileSeperator + "combinedVideo.mp4";
            String createThumbnail = "ffmpeg -ss 0.1 -i data" + _fileSeperator + model.getCurrentItem() + _fileSeperator + "video.mp4 -t 1 -s 480x400 -f mjpeg data" + _fileSeperator + model.getCurrentItem() + _fileSeperator + "thumbnail.jpg";

            runInBash(generateCmd);
            runInBash(combineCmd);
            runInBash(createThumbnail);
            return 0;
        }
    }

    class AudioInBackground extends  Task<Integer> {

        @Override
        protected Integer call() throws Exception {
            String cmd = "ffmpeg -y -f alsa -i default -t 3 -acodec libmp3lame data" + _fileSeperator + model.getCurrentItem() + _fileSeperator + "audio.mp3";
            runInBash(cmd);
            return 0;
        }
    }

    class Timer extends  Task<Integer> {

        @Override
        protected Integer call() throws Exception {
            for (int i = 0; i < 30; i++) {
                updateProgress(i, 29);
                Thread.sleep(100);
            }
            progressBar.progressProperty().unbind();
            return 0;
        }
    }

    //Action for "Record" button, loads process into bash and uses ffmpeg to record audio and generate video
    public void record() throws InterruptedException {
        disableBtns(true);
        AudioInBackground task = new AudioInBackground();
        task.setOnSucceeded((WorkerStateEvent event) -> {
            playbackAlert();
        });
        Timer timer = new Timer();
        progressBar.progressProperty().bind(timer.progressProperty());

        new Thread(timer).start();
        new Thread(task).start();


    }


    //Action for the "Cancel" button, returns to main menu
    public void cancel() {
        model.deleteDirectory();
        setPane("MainView");
    }

    //Helper method for setting the pane, takes the pane name as an argument
    private void setPane(String name) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(_fileSeperator + "View" + _fileSeperator + name + ".fxml"));
            Main.mainPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void playbackAlert() {
        Alert pbAlert = new Alert(Alert.AlertType.CONFIRMATION);
        pbAlert.setTitle("Would you like to hear your recording back?");
        pbAlert.setHeaderText("Would you like to hear your recording back?");
        pbAlert.setContentText("Press \"Yes\" to hear your recording back, or \"No\" to continue");
        pbAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        ButtonType yes = new ButtonType("Yes");
        ButtonType no = new ButtonType("No");
        pbAlert.getButtonTypes().setAll(yes, no);

        Optional<ButtonType> result = pbAlert.showAndWait();
        if (result.get() == yes) {
            File file = new File(System.getProperty("user.dir") + _fileSeperator + "data" + _fileSeperator + model.getCurrentItem() + _fileSeperator + "audio.mp3");
            Media media = new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.setOnEndOfMedia(() -> {
                Alert redoAlert = new Alert(Alert.AlertType.CONFIRMATION);
                redoAlert.setTitle("Do you want to record the audio again?");
                redoAlert.setHeaderText("Do you want to record the audio again?");
                redoAlert.setContentText("Please click \"Redo\" to record the audio again, or \"Continue\" to finish");
                redoAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                ButtonType redo = new ButtonType("Redo");
                ButtonType cont = new ButtonType("Continue");
                redoAlert.getButtonTypes().setAll(redo, cont);

                Optional<ButtonType> redoResult = redoAlert.showAndWait();
                if (redoResult.get() == cont) {
                    makeAndReturn();
                } else {
                    disableBtns(false);
                    progressBar.setProgress(0.0);
                }
            });
        }
        else {
            makeAndReturn();
        }

    }

    private void runInBash(String cmd) {
        Process process;
        ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", "-c", cmd);
        try {
            process = processBuilder.start();
        } catch (IOException excep) {
            excep.printStackTrace();
            return;
        }
        try {
            process.waitFor();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
            return;
        }
    }

    private void makeAndReturn() {
        VideoInBackground task = new VideoInBackground();
        new Thread(task).start();
        setPane("MainView");
    }

    private void disableBtns(Boolean bool) {
        recordBtn.setDisable(bool);
        cancelBtn.setDisable(bool);
    }
}



