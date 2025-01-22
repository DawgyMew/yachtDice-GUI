package com.mycompany.yachtdicem;

/**
 * 
 * [kat]
 * [Programming II]
 */
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TutorialScreen extends Application{
    private Button back = new Button("<");
    private Button exit = new Button("X");
    
    private Stage stage; // the window that all this is held on
    public Scene scene; // the contents of this screen 
    private Scene gameScene; // should let me go back to the prior game
    public TutorialScreen(Stage s, Scene gs){
        stage = s;
        scene = build(s);
        gameScene = gs;
    }
    
    @Override
    public void start(Stage s){
        build(s);
    }
    
    private Scene build(Stage s){
        VBox page = new VBox(10);
        HBox buttons = new HBox(10);
        back.setOnAction(event -> back());
        exit.setOnAction(event -> exit());
        buttons.getChildren().add(back);
        buttons.getChildren().add(new Label("How to Play!"));
        buttons.getChildren().add(exit);
        buttons.setAlignment(Pos.CENTER);
        
        page.getChildren().add(buttons);
        
        TextArea scoreArea = new TextArea();
        scoreArea.setText(showText());
        scoreArea.setEditable(false);
        page.getChildren().add(scoreArea);
        page.setAlignment(Pos.CENTER);
        page.setPadding(new Insets(10, 10, 10, 10));
        return (new Scene(page));
    }
    
    private String showText(){
        var io = new IOManager("tutorial.txt");
        //System.out.println("io made");
        var msg = "";
        msg += io.readText();
        return msg;
    }
    // zzzzz
    private void exit(){
        System.exit(0);
    }
    
    // returns back to the current game
    private void back(){
        stage.setScene(gameScene);
    }
    
}
