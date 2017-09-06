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
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class RecordViewController {
    String fileSeperator = File.separator;
    FileDirector model = FileDirector.instance();
    MediaPlayer mediaPlayer;

    @FXML
    private Button recordBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private ProgressBar progressBar;

    //Extends Task to perform work on a worker thread for creating the video, combining and creating the thumbnail.
    class VideoInBackground extends Task<Void> {

        @Override
        protected Void call() throws Exception {
            String generateCmd = "ffmpeg -y -f lavfi -i color=c=pink:s=480x360:d=3 -vf \"drawtext=fontsize=50:fontcolor=white:x=(w-text_w)/2:y=(h-text_h)/2:text=" + model.getCurrentItem() + "\" data" + fileSeperator + model.getCurrentItem() + fileSeperator + "video.mp4";
            String combineCmd = "ffmpeg -y -i data" + fileSeperator + model.getCurrentItem() + fileSeperator + "video.mp4" + " -i data" + fileSeperator + model.getCurrentItem() + fileSeperator + "audio.mp3 -c copy data" + fileSeperator + model.getCurrentItem() + fileSeperator + "combinedVideo.mp4";
            String createThumbnail = "ffmpeg -ss 0.1 -i data" + fileSeperator + model.getCurrentItem() + fileSeperator + "video.mp4 -t 1 -s 480x400 -f mjpeg data" + fileSeperator + model.getCurrentItem() + fileSeperator + "thumbnail.jpg";

            runInBash(generateCmd);
            runInBash(combineCmd);
            runInBash(createThumbnail);
            return null;
        }
    }

    //Extends Task to perform work on a worker thread for recording the audio
    class AudioInBackground extends Task<Void> {

        @Override
        protected Void call() throws Exception {
            String cmd = "ffmpeg -y -f alsa -i default -t 3 -acodec libmp3lame data" + fileSeperator + model.getCurrentItem() + fileSeperator + "audio.mp3";
            runInBash(cmd);
            return null;
        }
    }

    //Extends Task to perform work on a worker thread for a timer used to show progress in a progress bar
    class Timer extends Task<Void> {

        @Override
        protected Void call() throws Exception {
            for (int i = 0; i < 60; i++) {
                updateProgress(i, 59);
                Thread.sleep(50);
            }
            return null;
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

        ColorAdjust adjust = new ColorAdjust();
        adjust.setHue(-0.4);
        progressBar.setEffect(adjust);

        new Thread(timer).start();
        new Thread(task).start();
    }


    //Action for the "Cancel" button, returns to main menu
    public void cancel() {
        if (!model.wasOverwriting()) {
            model.deleteDirectory();
        }
        setPane("MainView");
    }

    //Helper method for setting the pane, takes the pane name as an argument
    private void setPane(String name) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fileSeperator + "View" + fileSeperator + name + ".fxml"));
            Main.mainPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Helper method for displaying the alert for whether the user wants to hear back audio, then if you want to redo
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
            Timer timer = new Timer();
            progressBar.progressProperty().bind(timer.progressProperty());
            ColorAdjust adjust = new ColorAdjust();
            adjust.setHue(0);
            progressBar.setEffect(adjust);
            new Thread(timer).start();

            File file = new File(System.getProperty("user.dir") + fileSeperator + "data" + fileSeperator + model.getCurrentItem() + fileSeperator + "audio.mp3");
            Media media = new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.setOnEndOfMedia(() -> {
            	mediaPlayer.dispose();
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
                    progressBar.progressProperty().unbind();
                    progressBar.setProgress(0.0);
                }
            });
        }
        else {
            makeAndReturn();
        }

    }

    //Helper method for making the process and process builder so commands can be executed in bash
    private void runInBash(String cmd) {
        Process process;
        ProcessBuilder processBuilder = new ProcessBuilder(fileSeperator +"bin" + fileSeperator + "bash", "-c", cmd);
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

    //Helper method to run the video tasks on the worker thread and return to main menu
    private void makeAndReturn() {
        VideoInBackground task = new VideoInBackground();
        new Thread(task).start();
        setPane("MainView");
    }

    //Helper method for disabling and enabling the buttons
    private void disableBtns(Boolean bool) {
        recordBtn.setDisable(bool);
        cancelBtn.setDisable(bool);
    }
}



