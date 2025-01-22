
package com.mycompany.yachtdicem;

/**
 * [kat]
 * [Programming II]
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class Dice {
    //private gameScreen gs = new gameScreen(null); // this is such a stupid way to do it but its the way im doing it
    
    private ImageView image;
    private int num;
    private boolean saved;
    // these are the image files that the image views acn be ome
    private Image die0Img;
    private Image die1Img;
    private Image die2Img;
    private Image die3Img;
    private Image die4Img;
    private Image die5Img;
    private Image die6Img;
    private final Image[] diceImgs = new Image[]{die0Img, die1Img, die2Img, die3Img, die4Img, die5Img, die6Img};
    
    public Dice(int num){
        this.num = num;
        saved = false;
        
        createDie(diceImgs); // create the dice images
        image = new ImageView(diceImgs[num]);
        int DICESIZE = 40;
        image.setFitHeight(DICESIZE); // make the dice fit
        image.setFitWidth(DICESIZE);
    }

    // rolls the rolls the dice!! //
    public void roll(){
        if (!saved){
            int roll = (int)(Math.random() * 6) + 1;
            //System.out.println(roll);
            setNum(roll);
        }
    }
    /**
     * This assigns the images to the image objects
     * @param di The image object to be assigned an image to
     * @param displayNum The number that will be displayed on the dice
     */
    private void createDie(Image[] diImg){
        for (int i = 0; i < diImg.length; i++){
            var tempDice = drawDie(diImg[i], i);
            if (tempDice != null){ // make sure the file exists before adding it
                diImg[i] = tempDice;
            }
        }
    }
    
    /**
     * Setup Function 
     * @param di
     * @param displayNum
     * @return Returns the image object to be displayed
     * i have to put this here because in the higher up defining it could throw an fnf exception
     */
    private Image drawDie(Image di, int displayNum){
        try{
            if (displayNum > 6){
                displayNum = 6;
            }
            FileInputStream fish = new FileInputStream("dice" + displayNum + ".png"); // get the image
            di = new Image(fish);
            return di;
        }
        catch (FileNotFoundException e){
            System.out.println(e);
            return null;
        }
    }
    
    /**
     * @return the image
     */
    public ImageView getImage() {
        return image;
    }

    /**
     * @param num the number to set the dice to
     */
    public void setNum(int num) {
        if (num > 6){
            num = 6;
        }
        else if (num < 0){
            num = 0;
        }
        this.num = num;
        image.setImage(diceImgs[num]);
    }

    public void incNum(){
        setNum((num + 1) % 7);
    }
    /**
     * @return the num
     */
    public int getNum() {
        return num;
    }
    
    

    /**
     * @return the saved
     */
    public boolean isSaved() {
        return saved;
    }

    /**
     * @param saved the saved to set
     */
    public void setSaved(boolean saved) {
        this.saved = saved;
    }
}
