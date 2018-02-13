package is.this_way.better.filesystem.gui.elements;

import javafx.scene.control.TreeCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public final class CustomTreeCell extends TreeCell<String> {

    public CustomTreeCell(){
        this.setOnMouseClicked(event -> {
            MouseEvent mouseEvent =  event;
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount()>=2 ){
                if (this.getTreeItem() instanceof FileTreeItem){
                    FileTreeItem fileTreeItem = (FileTreeItem)this.getTreeItem();
                    fileTreeItem.getMenu().getItems().get(0).fire();
                }
            }
        });
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(getItem() == null ? "" : getItem().toString());
            setGraphic(getTreeItem().getGraphic());
            setContextMenu(((AbstractTreeItem) getTreeItem()).getMenu());
        }
    }
}