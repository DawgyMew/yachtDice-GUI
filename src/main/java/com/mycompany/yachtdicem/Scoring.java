package com.mycompany.yachtdicem;

/**
 *
 * [kat]
 * [Programming II]
 */
import java.util.ArrayList;
import java.util.Collections;
public class Scoring {
    /**
     * Runs each check for each category
     * @param di the array of dice to parse
     * @return an array with all the possible scores for each category
     */
    //private int[] values;
    private ArrayList<Integer> values;
    public Scoring(Dice[] di){
        values = getDiValues(di);
    }
    
    private ArrayList<Integer> getDiValues(Dice[] di){
        var arr = new ArrayList<Integer>();
        // add the values to the list
        for (var dice : di){
            arr.add(dice.getNum());
        }
        Collections.sort(arr);
        System.out.println(arr.toString());
        return arr;
    }
    public int[] checks(){
        // stores the di's rolls //
        //int[] values = new int[5];
        
        int[] scores = new int[]{
                countNum(n -> n == 1) * 1,
                countNum(n -> n == 2) * 2,
                countNum(n -> n == 3) * 3,
                countNum(n -> n == 4) * 4,
                countNum(n -> n == 5) * 5,
                countNum(n -> n == 6) * 6,
                choiceScore(),
                checkFourKind(),
                checkFullHouse(),
                checkStraight(false),
                checkStraight(true),
                checkYacht(),
                };
        
        
        return scores;
    }
    
    
    
    // tests a specific thing 
    private interface NumTest{
        boolean test(int n);
    }
    
    private int countNum(NumTest numTest){
        int count = 0;
        for (var num : values){
            if (numTest.test(num)){
                count += 1;
            }
        }
        return count;
    }
    // added values of just one type of number //
    // should be usable for each individual categories of 1-6 //
    /*
    private int checkNum(NumTest numTest){
        int total = 0;
        for (var num : values){
            if (numTest.test(num)){
                total += num;
            }
        }
        return total;
    }
    */
    // the combined value of all five di //
    private int choiceScore(){
        int total = 0;
        for (var num : values){
            total += num;
        }
        return total;
    }
    
    // gives a score if there is 4 di of the same kind //
    private int checkFourKind(){
        for (int i = 0; i < values.size(); i++){
            var num = values.get(i);
            var count = countNum(n -> n == num);
            //System.out.println(num + ": count of " + count);
            if (count >= 4){
                return (num * 4);
            }
            // continue
        }
        return 0;
    }
    
    // gives a score of di for a combination of 3 of one di and 2 of another di, or 5 of one di.
    private int checkFullHouse(){
        int bigNum = 0;
        int smallNum = 0;
        var invalid = false;
        for (var num : values){
            var count = countNum(n -> n == num);
            switch(count){
                // 5 technically counts as a full house
                case 5: 
                    bigNum = num; 
                    smallNum = num;
                    break;
                // standard full house
                case 3:
                    bigNum = num;
                    break;
                case 2:
                    smallNum = num;
                    break;
                // no possible ways for a full house to be formed
                case 4:
                case 1:
                default:
                    invalid = true;
                    break;
            }
            if (invalid){break;}
        }
        return ((bigNum * 3) + (smallNum * 2));
    }
    /**
     * 
     * @param large Boolean for the size of the straight
     * @return int 0, 15, or 30 depending on size
     */
    private int checkStraight(boolean large){
        int streak = 0; // have a streak of incrementing numbers to determin if straight
        int highStreak = 0; // the highest streak reached
        for (int i = 1; i < values.size(); i++){
            int dif = values.get(i) - values.get(i - 1);
            if (dif == 1){
               streak += 1; 
               //System.out.println(streak);
            }
            else{
                if (streak > highStreak){
                    highStreak = streak;
                }
            }
        }
        
        // distribute points //
        if (streak == 4 && large){
            return 30; // return 30 points for a large straight
        }
        else if(streak >= 3 && !large){
            return 15; // return 15 points for a small straight
        }
        else{
            return 0;
        }
    }
    // check if all the numbers are the same //
    private int checkYacht(){
        for (var val : values){
            if (values.get(0) != val){
                return 0;
            }
        }
        return (50);
    }
    
    // scoring related junk that can be accessed anywhere anytime anywhere anytime //
    public static int getTotal(int[] gameScores, int sub){
        var total = 0;
        for (var score : gameScores){
            total += score;
        }
        total += sub;
        return total;
    }
    public static int getSubtotal(int[] gameScores){
        int total = 0;
        for (int i = 0; i < 6; i++){ // first six categories are added for subbtotal bonus
            total += gameScores[i];
        }
        return total;
    }
    // i wrote this too many times so i am making it a function :3
    public static int subtotalBonus(int sub){
        if (sub >= 61){return 35;}
        return 0;
    }
}
