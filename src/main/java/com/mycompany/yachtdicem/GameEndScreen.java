
package com.mycompany.yachtdicem;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * The program will autoredirect to this screen once the gamescreen runs out of categories to fill
 * [kat]
 * [Programming II]
 */
public class GameEndScreen extends Application{
    private VBox split;
    // top
    private TextArea scoreBox = new TextArea();
    private TextField subtotalText = new TextField("Subtotal: */63  + &!"); // either 0 or 35
    private TextField totalText = new TextField("Total: *!");
    public Scene scene;
    private int[] gameScores;
    
    // bottom 
    private VBox nameSaveBox;
    
    private TextField nameField = new TextField();
    private Button saveButton = new Button("Submit");
    private Button exitButton = new Button("Exit");
    
    private VBox continueBox;
    private Button newGame = new Button("New Game");
    private Button scoresButton = new Button("Scores");
    
    private Stage stage;
    public GameEndScreen(Stage s, int[] gs){
        gameScores = gs;
        stage = s;
        scene = build(s);
        
    }
    
    
    private Scene build(Stage s){
        // splitscreen
        split = new VBox(10);
        
        // scores on top half //
        VBox scores = new VBox(10);
        Label gameOver = new Label("Game Over!!!");
        
        scores.getChildren().add(gameOver);
        scoreBox.setEditable(false);
        // fill the text in //
        scoreBox.setText(generateText());
        scoreBox.setMinWidth(700);
        scoreBox.setMaxHeight(80);
        
        var subtotal = Scoring.getSubtotal(gameScores);
        var bonus = Scoring.subtotalBonus(subtotal);
        subtotalText.setText("Subtotal: " + subtotal + "/63\t +" + bonus + "!");
        
        var total = Scoring.getTotal(gameScores, bonus);
        totalText.setText("Score: " + total + "!");
        scores.getChildren().add(scoreBox);
        
        HBox flavourText = new HBox(10);
        subtotalText.setEditable(false);
        totalText.setEditable(false);
        flavourText.getChildren().add(subtotalText);
        flavourText.getChildren().add(totalText);
        flavourText.setAlignment(Pos.CENTER);
        scores.getChildren().add(flavourText);
        scores.setAlignment(Pos.CENTER);
        split.getChildren().add(scores);
        
        // bottom half //
        // first box that appears //
        nameSaveBox = new VBox(10);
        HBox nameBox = new HBox(10);
        nameBox.getChildren().add(new Label("Enter Name: "));
        nameBox.getChildren().add(nameField);
        nameBox.setAlignment(Pos.CENTER);
        nameSaveBox.getChildren().add(nameBox);
        
        saveButton.setOnAction(event -> submitButton());
        nameSaveBox.getChildren().add(saveButton);
        nameSaveBox.setAlignment(Pos.CENTER);
        
        
        split.getChildren().add(nameSaveBox);
        
        // second box that appears after the other one //
        continueBox = new VBox(10);
        
        var top = new HBox(10);
        newGame.setOnAction(event -> newGame());
        top.getChildren().add(newGame);
        scoresButton.setOnAction(event -> scores());
        top.getChildren().add(scoresButton);
        top.setAlignment(Pos.CENTER);
        continueBox.getChildren().add(top);
        continueBox.setAlignment(Pos.CENTER);
        
        
        // save this box for later
        
        exitButton.setOnAction(event -> exit());
        split.getChildren().add(exitButton);
        split.setAlignment(Pos.TOP_CENTER);
        split.setPadding(new Insets(10, 10, 10, 10));
        Scene scene = new Scene(split);
        return scene;
    }
    
    @Override
    public void start(Stage s){
        build(s);
    }
    
    // submit the name to the the the score sheet :3
    private void submitButton(){
        var io = new IOManager();
        String name = nameField.getText().toUpperCase();
        if (name.isBlank()){name = "UNKNOWN";}
        io.write(name, gameScores);
        split.getChildren().set(1, continueBox);
        //split.getChildren().add();
    }
    
    private static void exit(){
        System.exit(0); // goodnight
    }
    
    private void newGame(){
        gameScreen gs = new gameScreen(stage);
        stage.setScene(gs.scene);
    }
    
    private void scores(){
        ScoreScreen.callScoreScreen(stage);
    }
    private String generateText(){
        String line1 = "Categories: ";
        String line2 = "Scores: \t";
        for (int i = 0; i < gameScreen.CATEGORIES.length; i++){
            line1 += gameScreen.CATEGORIES[i] + " | ";
            line2 += gameScores[i] + "\t  ";
            if (i > 6){
                line2 += "\t"; // make the spacing longer for the longer categories
            }
        }
        var msg = line1 + "\n" + line2;
        return msg;
    }
    
    /*
    public static void callGameEnd(Stage s){
        var scoreScreen = new GameEndScreen(s);
        s.setScene(scoreScreen.scene);
    } 
    */
}
