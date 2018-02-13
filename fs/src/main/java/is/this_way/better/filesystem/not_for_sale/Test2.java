package is.this_way.better.filesystem.not_for_sale;

import is.this_way.better.filesystem.gui.controllers.MainWindowController;
import is.this_way.better.filesystem.gui.elements.FolderImageView;
import is.this_way.better.filesystem.gui.elements.FolderTreeItem;
import is.this_way.better.filesystem.io.lowlevel.ThreadSafeIO;
import is.this_way.better.filesystem.io.serialization.FileSystemDeserializer;
import is.this_way.better.filesystem.model.low_level_items.files.File;
import is.this_way.better.filesystem.model.high_level_items.FileSystem;
import is.this_way.better.filesystem.model.low_level_items.files.FileTypes;
import is.this_way.better.filesystem.model.low_level_items.folders.Folder;
import is.this_way.better.filesystem.model.low_level_items.folders.RootFolder;
import is.this_way.better.filesystem.model.high_level_items.IndexTable;
import is.this_way.better.filesystem.model.high_level_items.SystemInfo;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.*;

public class Test2 extends Application{
    Stage primaryStage;
    Parent root;
    @FXML
    TreeView hierarchy;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainWindow.fxml"));
        root = loader.load();
        MainWindowController window = loader.getController();


        RandomAccessFile rf = new RandomAccessFile(getClass().getResource("/TemporalDir/current.music").getPath(),"rw");
        rf.write(lol2());


        Media media = new Media(getClass().getResource("/TemporalDir/current.music").toURI().toString());
        MediaPlayer mp = new MediaPlayer(media);

        FileSystemDeserializer fileSystemDeserializer=new FileSystemDeserializer("C:\\Users\\aborichev\\Desktop\\try31.txt");
      //  window.init(fileSystemDeserializer.deserialize());

        Scene scene = new Scene(root, 540, 110);
        scene.getStylesheets().add(getClass().getResource("/CSS/MainWindowTheme.css").toExternalForm());

        Stage mainWindow = new Stage();
        mainWindow.initStyle(StageStyle.UTILITY);
        mainWindow.setTitle("MainWindow");
        mainWindow.setScene(scene);
        mainWindow.show();


    }
    //TODO REMOVE THIS !!!!!

    public Image aaa(byte[] b){
        Image img = new Image(new ByteArrayInputStream(b));
        return img;
    }
    public byte[] lol() throws IOException{
        RandomAccessFile rf = new RandomAccessFile("C:/Users/aborichev/Desktop/waterfall_acoustic.mp3", "r");
        byte[] ar =new byte[(int) rf.length()];
        rf.read(ar);
            return ar;

    }
    public byte[] lol2() throws IOException {
        RandomAccessFile rf = new RandomAccessFile("C:/Users/aborichev/Desktop/waterfall_acoustic.mp3", "r");
        byte[] ar =new byte[(int) rf.length()];
        rf.read(ar);
        return ar;
    }

    public static FileSystem initFS(){
        SystemInfo sI = new SystemInfo.SystemInfoBuilder(20,1024).setFileStructurePos(40).setOffset(40).build();
        IndexTable ind = new IndexTable();
/*        ind.addSector(40);
        ind.addSector(1064);
        ind.addSector(2088);
        ind.addSector(3116);*/
        RootFolder rootFolder = new RootFolder();
        Folder folder1 = new Folder("aaa");
        Folder folder2 = new Folder("bbb");
        is.this_way.better.filesystem.model.low_level_items.files.File file1   = new is.this_way.better.filesystem.model.low_level_items.files.File("f1", FileTypes.TEXT);
        is.this_way.better.filesystem.model.low_level_items.files.File file2   = new File("f2",FileTypes.VIDEO);
        folder1.add(folder2);
        folder1.add(file1);
        folder2.add(file2);
        rootFolder.add(folder1);

        FileSystem fileSystem = new FileSystem.FileSystemBuilder(true).setSystemInfo(sI).setIndexTable(ind).setRootFolder(rootFolder).build();
        FolderTreeItem rooti= new FolderTreeItem(rootFolder);
        FolderImageView folderImageView = new FolderImageView(rooti);
        rooti.setGraphic(folderImageView);
        rooti.getChildren().add(new FolderTreeItem(folder1));
        ThreadSafeIO.init("C:\\Users\\aborichev\\Desktop\\try31.txt", 1024);



        return fileSystem;
    }
}
