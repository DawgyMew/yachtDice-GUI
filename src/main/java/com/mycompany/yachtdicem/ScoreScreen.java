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

public class ScoreScreen extends Application{
    private Button mainMenu = new Button("Main Menu");
    private Button exit = new Button("Exit");
    
    private Stage stage; // the window that all this is held on
    public Scene scene; // the contents of this screen 
    public ScoreScreen(Stage s){
        stage = s;
        scene = build(s);
//        s.setScene(scene); // i realized like 16 ways i couldve done the scene switch better but here we are
    }
    
    @Override
    public void start(Stage s){
        build(s);
    }
    
    private Scene build(Stage s){
        VBox page = new VBox(10);
        HBox buttons = new HBox(10);
        mainMenu.setOnAction(event -> mainMenuPressed());
        exit.setOnAction(event -> exit());
        buttons.getChildren().add(mainMenu);
        buttons.getChildren().add(new Label("Scores!"));
        buttons.getChildren().add(exit);
        buttons.setAlignment(Pos.CENTER);
        
        page.getChildren().add(buttons);
        
        TextArea scoreArea = new TextArea();
        scoreArea.setText(showScores());
        scoreArea.setEditable(false);
        page.getChildren().add(scoreArea);
        page.setAlignment(Pos.CENTER);
        page.setPadding(new Insets(10, 10, 10, 10));
        return (new Scene(page));
    }
    
    private String showScores(){
        var io = new IOManager();
        //System.out.println("io made");
        var msg = "";
        msg += io.readScores();
        return msg;
    }
    // zzzzz
    private void exit(){
        System.exit(0);
    }
    
    private void mainMenuPressed(){
        stage.setScene(SplashScreen.mainMenu);
    }
    
    public static void callScoreScreen(Stage s){
        var scoreScreen = new ScoreScreen(s);
        s.setScene(scoreScreen.scene);
    } 
}
