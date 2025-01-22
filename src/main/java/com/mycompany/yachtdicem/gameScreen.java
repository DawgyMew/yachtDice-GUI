package com.mycompany.yachtdicem;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ComboBox;
/**
 * [kat]
 * [Programming II]
 */
public class gameScreen extends Application{
    public static final String[] CATEGORIES = new String[] {"Aces", "Deuces", "Threes", "Fours", "Fives", "Sixes", "Choice", "Four of a Kind", "Full House", "S. Straight", "L. Straight", "Yacht"};
    
    /// CONTROLS ///
    // dice buttons //
    private CheckBox dice0Check = new CheckBox("");
    private CheckBox dice1Check = new CheckBox("");
    private CheckBox dice2Check = new CheckBox("");
    private CheckBox dice3Check = new CheckBox("");
    private CheckBox dice4Check = new CheckBox("");
    private CheckBox[] checks = new CheckBox[]{dice0Check, dice1Check, dice2Check, dice3Check, dice4Check};
    
    // other buttons
    private Button helpButton = new Button("?");
    private Button exitButton = new Button("X");
    private Button rollButton = new Button("Roll!");
    private Button saveSelection = new Button("Save Selection");
    
    // text fields //
    private TextField scoreText0 = new TextField(); 
    private TextField scoreText1 = new TextField();
    private TextField scoreText2 = new TextField();
    private TextField scoreText3 = new TextField();
    private TextField scoreText4 = new TextField();
    private TextField scoreText5 = new TextField();
    private TextField scoreText6 = new TextField();
    private TextField scoreText7 = new TextField();
    private TextField scoreText8 = new TextField();
    private TextField scoreText9 = new TextField();
    private TextField scoreText10 = new TextField();
    private TextField scoreText11 = new TextField();
    private TextField[] scoreTexts = new TextField[]{scoreText0,scoreText1,scoreText2,scoreText3,scoreText4,scoreText5,scoreText6,
            scoreText7,scoreText8,scoreText9,scoreText10,scoreText11};
    private TextField totalScoreText = new TextField();
    
    
    // dice //
    //private FileInputStream die0 = new FileInputStream("dice0.png");

    private Dice dice0;
    private Dice dice1;
    private Dice dice2;
    private Dice dice3;
    private Dice dice4;
    private Dice[] diceArr = new Dice[]{dice0, dice1, dice2, dice3, dice4};
    
    // other //
    private Label rollsRemainingLabel = new Label("Rolls Remaining: 0");
    private ComboBox catBox = new ComboBox<>();
    
    // actual variables shocker //
    private int rollsRemaining;
    
    public Scene scene;
    private Stage stage;
    // create a new gamescreen //
    public gameScreen(Stage s){
        rollsRemaining = 3;
        scene = build(s);
        stage = s;
    }
    private GridPane drawScoreSheet(){
        
        // scoreboard 
        GridPane scoreBoard = new GridPane();
        // add each of the categories and their textboxes //
        int displace = 0; // when adding an item that is not apart of the array
        for (int i = 0; i < CATEGORIES.length; i++){
            Label catLabel = new Label(CATEGORIES[i] + ": ");
            var scoreText = scoreTexts[i];
            scoreText.setEditable(false); // i can disable the boxes once the category is selected
                scoreText.setMaxWidth(50);
            scoreText.setText("0");
            scoreBoard.add(catLabel, 0, i + displace);
            scoreBoard.add(scoreText, 1, i + displace);
            if (i == 5 || i == 11){ // end of number section
                //System.out.println("add seperator");
                displace += 1;
                Label seperator = new Label("-".repeat(25));
                scoreBoard.add(seperator, 0, i + displace, 2, 1);
            }
        }
        // add the total score box //
        Label scoreLabel = new Label("Score: ");
        totalScoreText.setEditable(false);
        totalScoreText.setMaxWidth(50);
        totalScoreText.setText("0");
        scoreBoard.add(scoreLabel, 0, CATEGORIES.length + displace);
        scoreBoard.add(totalScoreText, 1, CATEGORIES.length + displace);

        return scoreBoard;
    }
    
    // draws the game menu //
    private VBox drawGameMenu(){
        VBox game = new VBox(10);
        
        // first layer: help and exit buttons //
        HBox assistBox = new HBox(10);
        assistBox.getChildren().add(helpButton);
        helpButton.setOnAction(event -> helpClicked());
        //helpButton.setAlignment(Pos.CENTER_LEFT);
        assistBox.getChildren().add(exitButton);
        exitButton.setOnAction(event -> exitClicked());
        assistBox.setAlignment(Pos.CENTER_RIGHT);
        game.getChildren().add(assistBox);
        
        
        // second & third layer: 5 di & di save checkboxes//
        HBox dieBox = new HBox(10);
        for (int i = 0; i < diceArr.length; i++){
            VBox diSaveBox = new VBox(10);
            // the die //
            diceArr[i] = new Dice(0);
            diSaveBox.getChildren().add(diceArr[i].getImage());
            // the checkbox //
            diSaveBox.getChildren().add(checks[i]);
            diSaveBox.setAlignment(Pos.CENTER);
            dieBox.getChildren().add(diSaveBox);
        }
        
        
        game.getChildren().add(dieBox);
        // third layer: di save checkboxes //
        
        /*HBox saveBox = new HBox(10);
        for (int i = 0; i < checks.length; i++){
            //System.out.println(box.toString());
            
            saveBox.getChildren().add(checks[i]);
        }
        game.getChildren().add(saveBox);
        */
        
        // fourth layer: rolls baby!! //
        HBox rollBox = new HBox(10);
        rollBox.getChildren().add(rollButton);
        rollButton.setPrefWidth(100);
        rollButton.setOnAction(event -> rollDie());
        rollBox.setAlignment(Pos.CENTER);
        game.getChildren().add(rollBox);
        
        // fifth layer: rolls remaining //
        HBox rollLeftBox = new HBox(10);
        rollsRemainingLabel.setText("Rolls Remaining: " + rollsRemaining);
        rollLeftBox.getChildren().add(rollsRemainingLabel);
        rollLeftBox.setAlignment(Pos.CENTER);
        game.getChildren().add(rollLeftBox);
        // sixth layer: choose category //
        HBox chooseBox = new HBox(10);
        catBox.setPromptText("Category");
        for (var cat : CATEGORIES){
            catBox.getItems().add(cat);
        }
        
        chooseBox.getChildren().add(catBox);
        chooseBox.getChildren().add(saveSelection);
        saveSelection.setOnAction(event -> saveClicked());
        saveSelection.setDisable(true);
        catBox.setOnAction(event -> updateSaveButton());
        chooseBox.setAlignment(Pos.CENTER);
        game.getChildren().add(chooseBox);
        
//        game.setAlignment(Pos.CENTER);//put it in a prettier spot
        return game;
    }
    public static void showMenu() {
        //System.out.println("ngiawenghioweaj");
        launch();
    }
    //@Override
    public Scene build(Stage stage){
        //System.out.println(getClass().getResource("/Images/Dice0.png") + "");
        // set up stage //
        stage.setTitle("Yacht Dice");
        //System.out.println("yo waddup");
        // splitscreen //
        HBox split = new HBox(10);
        
        // add scoresheet //
        split.getChildren().add(drawScoreSheet());
        
        // seperator //
        Separator sep = new Separator();
        sep.setOrientation(Orientation.VERTICAL);
        split.getChildren().add(sep);
        // main game menu //
        split.getChildren().add(drawGameMenu());
        
        // show the scene
        split.setPadding(new Insets(10, 10, 10, 10));
        Scene scene = new Scene(split);
        rollDie();
        //stage.setScene(scene);
        //stage.show();
        return scene;
    }
    @Override
    public void start(Stage stage){
        build(stage);
    }
    
    
    private void rollDie(){
        for (int i = 0; i < checks.length; i++){
            checkBoxPressed(i); // check the checkboxes for changes
        }
        
        // update the save status of the di
        if (rollsRemaining > 0){
            for (var dice : diceArr){
                dice.roll(); // ROLLS BABY!!!!
            }
            setRollCount(rollsRemaining - 1);
            // disable check boxes on rolls 0
            if (rollsRemaining == 0){
                for (var box : checks){
                    box.setDisable(true);
                    rollButton.setDisable(true);
                }
                rollDie();
            }
        }
        
        showScores();
    }
    
    // test functionW
    private void incrementDice(){
        for (var dice : diceArr){
            dice.incNum();
        }
    }
    private void helpClicked(){
        var help = new TutorialScreen(stage, scene);
        stage.setScene(help.scene);
    }
    
    private void roundEnd(){
        //incrementDice();
        var ges = new GameEndScreen(stage, getScores());
        stage.setScene(ges.scene);
    }
    public void exitClicked(){System.exit(0);} // bye
    
    private void saveClicked(){
        String chosenCat = catBox.getSelectionModel().getSelectedItem().toString();
        //System.out.println(chosenCat);
        // make it save to the selected option
        // disable that textfield
        // reset the rolls
        // reset the rolls remaining
        catBox.getItems().remove(chosenCat);
        catBox.setPromptText("Category");
        
        var catNum = indexOf(CATEGORIES, chosenCat);
        scoreTexts[catNum].setDisable(true);
        
        // reenable the disabled options //
        setRollCount(3);
        for (int i = 0; i < checks.length; i++){
            checks[i].setSelected(false);
            checks[i].setDisable(false);
            diceArr[i].setSaved(false);
            diceArr[i].setNum(0);
        }
        
        rollButton.setDisable(false);
        saveSelection.setDisable(catBox.getItems().size() != 1); //disable box unless its the last one
        showScores(); // could probably more efficiently reset the textboxes but 
        rollDie();
        if (catBox.getItems().isEmpty()){
            roundEnd();
        }
    }
    
    // displays the score numbers on the scoreboard //
    private void showScores(){
        var s = new Scoring(diceArr);
        var scores = s.checks();
        int total = 0;
        // dispaly the numbers :3 //
        for (int i = 0; i < CATEGORIES.length; i++){
            if (!scoreTexts[i].isDisabled()){
                scoreTexts[i].setText(scores[i] + "");
            }
            else{
                total += Integer.parseInt(scoreTexts[i].getText());
            }
        }
        totalScoreText.setText(total + "");
    }
    
    private int[] getScores(){
        var scores = new int[12];
        for (int i = 0; i < scoreTexts.length; i++){
            if (scoreTexts[i].isDisabled()){
                scores[i] = Integer.parseInt(scoreTexts[i].getText());
            }
            else{
                scores[i] = 0;
            }
//            System.out.println(scores[i]);
        }
//        System.out.println(Arrays.toString(scores));
        return scores;
    }
    // find the index of a specified 
    private int indexOf(String[] arr, String str){
        for (int i = 0; i < arr.length; i++){
            if (arr[i].equals(str)){
                return i;
            }
        }
        return -1;
    }
    
    private void setRollCount(int num){
        rollsRemaining = num;
        rollsRemainingLabel.setText("Rolls Remaining: " + rollsRemaining);
    }
    
    private void updateSaveButton(){
        // it only fires when the combo box is updated
        saveSelection.setDisable(false);
    }
    
    // 
    private void checkBoxPressed(int diceNum){
        diceArr[diceNum].setSaved(checks[diceNum].isSelected());
    }
    
    
}
