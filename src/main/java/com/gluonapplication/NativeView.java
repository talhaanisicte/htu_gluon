package com.gluonapplication;

import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.Icon;
import com.gluonhq.charm.glisten.control.TextField;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;

public class NativeView extends View {

    private TextArea textArea = new TextArea();

    private void log(String text){
        textArea.appendText(text+"\n");
    }

    private void loadSharedLibrary(String fullPath){
        try {
        System.load(fullPath);

        log("Before createIsolate() ");

        final long iso = createIsolate();

        log("Executed isolate - " + iso);

        final long addr = Long.MAX_VALUE;
        final int size = Integer.MAX_VALUE;

        log("Before run() ");

        final int ret = run(iso, addr, size);

        log("After run() returned - " + ret);
        }catch(Exception e){
            log(e.toString());
        }

    }

    public NativeView() {
        
        Label label = new Label("Enter Shared Library Path:");

        TextField text = new TextField("/root/htu_gluon/nativeImpl/target/libnativeimpl.so");

        Button button = new Button("Load Library");
        button.setGraphic(new Icon(MaterialDesignIcon.LANGUAGE));
        button.setOnMouseClicked(e -> loadSharedLibrary(text.getText()));

        VBox controls = new VBox(15.0, label, text, button, textArea);
        controls.setAlignment(Pos.CENTER);
        
        setCenter(controls);
    }

    @Override
    protected void updateAppBar(AppBar appBar) {
        appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> System.out.println("Menu")));
        appBar.setTitleText("Basic View");
        appBar.getActionItems().add(MaterialDesignIcon.SEARCH.button(e -> System.out.println("Search")));
    }

    private static native long createIsolate();

    private static native int run(long isolateThread, long addr, int size);

}
