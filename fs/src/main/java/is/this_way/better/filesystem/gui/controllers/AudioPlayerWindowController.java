package is.this_way.better.filesystem.gui.controllers;

import is.this_way.better.filesystem.io.converters.DataConverter;
import is.this_way.better.filesystem.io.readers_writers.FileReader;
import is.this_way.better.filesystem.model.low_level_items.files.File;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;

public class AudioPlayerWindowController {
    private static AudioPlayerWindowController instance;
    private Image playImage;
    private Image pauseImage;
    private ImageView playButtonImage;
    private MediaPlayer mediaPlayer;
    @FXML
    MediaView mediaView;
    @FXML
    TreeView playList;
    @FXML
    Button playButton;
    @FXML
    File currentFile;

    public AudioPlayerWindowController(){
        instance=this;
    }

    public static boolean isInitialised(){
        return instance==null ? false:true;
    }
    public static AudioPlayerWindowController getInstance(){
        return instance;
    }

    public void init (File currentFile) {
        playImage  = new Image(getClass().getResource("/Icons/player_play.png").toExternalForm());
        pauseImage = new Image(getClass().getResource("/Icons/player_pause.png").toExternalForm());
        preparePlayButton();
        this.currentFile=currentFile;
        prepareFile(currentFile);
        String mediaPath="";
        try {
            mediaPath = getClass().getResource("/TemporalDir/current.music").toURI().toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        Media media = new Media(mediaPath);
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaView.setVisible(false);
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
            }
        });
    }

   //TODO implement loading from playlist

    private void prepareFile(File currentFile) {
        byte[] fileData = DataConverter.sectorsToByteArray(FileReader.readFileData(currentFile.getStartingPos()).getData());
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(getClass().getResource("/TemporalDir/current.music").getPath(), "rw")) {
            randomAccessFile.seek(0);
            randomAccessFile.write(fileData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void preparePlayButton(){
        playButtonImage=new ImageView();
        playButtonImage.setVisible(true);
        playButtonImage.setImage(playImage);
        playButton.setGraphic(playButtonImage);
    }

    @FXML
    public void playPause(ActionEvent actionEvent){
        switch (mediaPlayer.getStatus()){
            case READY:
            case PAUSED:
            case STOPPED:
            case UNKNOWN:
            case HALTED:
            case STALLED:
            case DISPOSED:
                mediaPlayer.play();
                playButtonImage.setImage(pauseImage);
                break;
            case PLAYING:
                mediaPlayer.pause();
                playButtonImage.setImage(playImage);
                break;
            default:
                break;

        }

    }
}
