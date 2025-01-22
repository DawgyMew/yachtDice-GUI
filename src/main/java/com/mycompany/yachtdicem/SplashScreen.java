/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.yachtdicem;

import java.io.FileInputStream;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileNotFoundException;
import javafx.geometry.Pos;
/**
 * The screen that pops up when the user launches yacht dice.
 * [kat]
 * [Programming II]
 */
public class SplashScreen extends Application{
    private Label title = new Label("Yacht Dice !!!");
    private Button newGame = new Button("New Game");
    private Button continueGame = new Button("Continue");
    private Button scoresButton = new Button("Scores");
    private Button exitButton = new Button("Exit");
    private Label wet = new Label("Mitchell Bennett 2024 - Programming II");
    
    private Image leftImgSrc;
    private Image rightImgSrc;
    
    private ImageView leftImg;
    private ImageView rightImg;
    
    private Stage main;
    public static Scene mainMenu;
    @Override 
    public void start(Stage stage){
        main = stage;
        // change title
        stage.setTitle("Yacht Dice");
        // definately know what the 20 does
        VBox menu = new VBox(20);
        // i dont feel like calling this 30 times
        var menuC = menu.getChildren();
        
        // title and photos //
        assignPhotos();
        HBox titleBox = new HBox(15);
        titleBox.getChildren().add(leftImg);
        titleBox.getChildren().add(title);
        titleBox.getChildren().add(rightImg);
        titleBox.setAlignment(Pos.CENTER);
        
        menuC.add(titleBox);
        // Button Layer 1 //
        HBox buttonBox1 = new HBox(10);
        newGame.setOnAction(event -> newGame());
        buttonBox1.getChildren().add(newGame);
        continueGame.setDisable(true);
        //buttonBox1.getChildren().add(continueGame);
        buttonBox1.getChildren().add(scoresButton);
        scoresButton.setOnAction(event -> scoreButton());
        buttonBox1.setAlignment(Pos.CENTER);
        menuC.add(buttonBox1);
        
        HBox exitBox = new HBox(5);
        exitButton.setOnAction(event -> exit());
        exitBox.getChildren().add(exitButton);
        exitBox.setAlignment(Pos.TOP_CENTER);
        menuC.add(exitBox);
        
        // buidld the menu //
        
        menu.setPadding(new Insets(10, 10, 10, 10));
        mainMenu = new Scene(menu);
        
        
        stage.setScene(mainMenu);
        stage.show();
    }
    
    private void assignPhotos(){
        try{
            leftImgSrc = new Image(new FileInputStream("redDice.jpg"));
            leftImg = new ImageView(leftImgSrc);
            leftImg.setFitHeight(100);
            leftImg.setFitWidth(100);
            rightImgSrc = new Image(new FileInputStream("kitty.jpg"));
            rightImg = new ImageView(rightImgSrc);
            rightImg.setFitHeight(100);
            rightImg.setFitWidth(100);
        }
        catch (FileNotFoundException e){
            leftImg = null;
            rightImg = null;
        }
    }
    public static void showMenu(){
        launch();
    }
    
    // buttons //
    private void newGame(){
        gameScreen gs = new gameScreen(main);
        main.setScene(gs.scene);
    }
    private void scoreButton(){
        var scoreScreen = new ScoreScreen(main);
        main.setScene(scoreScreen.scene);
    }
    private static void exit(){
        System.exit(0); // goodnight
    }
}
