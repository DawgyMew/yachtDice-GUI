package com.mycompany.yachtdicem;

/**
 *
 * @author Me
 */
import java.io.*;
public class IOManager {
    private final String FILENAME;
    // empty and not empty constructor //
    public IOManager(String fn){
        FILENAME = fn;
    }
    public IOManager(){
        this("scores.txt");
    }
    public void write(String name, int[] scores){
        try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(FILENAME, true)))){
            var subtotal = Scoring.getSubtotal(scores);
            var msg = name + "\t" + Scoring.getTotal(scores, subtotal) + "\t";
            
            // add each of the each //
            for (var score : scores){
                msg += score + ",";
            }
            var bonus = Scoring.subtotalBonus(subtotal);
            msg += "\t" + bonus;
            out.println(msg);
            out.close();
        }
        catch (IOException e){
            System.err.println(e);
        }
    }
    public String readScores(){
        var msg = "";
        try(BufferedReader in = new BufferedReader(new FileReader(FILENAME))){
            String line = in.readLine();
            
            while (line != null){
                String[] scoreInfo = line.split("\t");
                for (var h : scoreInfo){
                    System.out.println(h + ", ");
                }
                var displayLine = scoreInfo[0] + ": " + scoreInfo[1];
                
                msg += displayLine + "\n";
                line = in.readLine();
            }
            in.close();
        }
        catch (FileNotFoundException e){
            msg = "No Scores Yet.\nPlay some games for scores to appear! :)";
        }
        catch(IOException e){
            System.err.println(e);
        }
        return (msg);
    }
    public String readText(){
        var msg = "";
        try(BufferedReader in = new BufferedReader(new FileReader(FILENAME))){
            String line = in.readLine();
            while (line != null){
                msg += line + "\n";
                
                line = in.readLine();
            }
            in.close();
        }
        catch(IOException e){
            msg = "guh?? " + e;
        }
        return (msg);
    }
}
