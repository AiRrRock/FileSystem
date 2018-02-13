package is.this_way.better.filesystem.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageWindowController {
    @FXML
    private ImageView imageView;
    public void init(Image image){
        imageView.setImage(image);
        imageView.setVisible(true);
    }

}
