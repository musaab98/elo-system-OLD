import java.util.Scanner;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class elo{

   private static Scanner keyboard;
   public static Player allPlayers[];
   public static int numPlayers;
   private static int Score, Score2, KA, KB, MA, MB;
   private static double SA, SB, TA, TB, QA, QB, EA, EB;
   private static String Save, Name, Name2, Winner;
   private static Boolean namecheck, samecheck, donecheck;
   public static JFrame frame;

// Main Method
   
   public static void main(String args[]){
   
      // Loads players
      keyboard = new Scanner(System.in);
      try {
         loadPlayers();
      }
      catch (Exception e){
         System.out.println("Could not load room or load data file. Exiting.");
         System.exit(0);
      }
      int menuSelect = 0;
      
   // Main Menu
      System.out.println("Elo Ranking System");   
      while (true){
         // display the main menu options
         System.out.println("");
         System.out.println("--------------------------------------------------------");
         System.out.println("");
         System.out.println("Select an option:");
         System.out.println("");
         System.out.println("1. Print Players");
         System.out.println("2. Raw Print");
         System.out.println("3. Add new players");
         System.out.println("4. Edit a player's score");
         System.out.println("5. Remove players");
         System.out.println("6. Input 1 vs 1 Match");
         System.out.println("7. Input double elimination bracket");
         System.out.println("0. Save and Quit");
         System.out.println("");
         System.out.println("--------------------------------------------------------");
         System.out.println("");
         // execute each option
         try{
            menuSelect = Integer.parseInt(keyboard.nextLine());
            switch (menuSelect){
               case 1: printPlayers();
                  break;
               case 2: rawPrint();
                  break;
               case 3: newPlayer();
                  break;
               case 4: editPlayer();
                  break;
               case 5: removePlayer();
                  break;
               case 6: newMatch();
                  break;
               case 7: newBracket();
                  break;
               case 0: savePlayers();
                  break;
               default:
                  System.out.println("Invalid Input. Pick an option below");
                  System.out.println("");
                  break;
            }
         }
         catch (Exception e){
            System.out.println("Invalid Input. Pick an option below");
            System.out.println("");
            continue;
         }
      } 
   }
   
// Load players from the players.txt file
   public static void loadPlayers()throws Exception {
      numPlayers = 0;
      allPlayers = new Player[200];
      Scanner infile = new Scanner(new File("players.txt"));
      infile.useDelimiter("%");
      //read the text file into the array     
      while (infile.hasNext()){
         allPlayers[numPlayers] = new Player(infile.next(), infile.nextInt(), infile.nextInt());
         infile.nextLine();
         numPlayers++;
      }
   }
// Print players with score/rank
   public static void printPlayers()throws Exception {
   // Bubble Sort
      int tempScore, tempMatches;
      String temp;
      for(int z = 0; z < numPlayers; z++)
      {
         for(int i = 0; i < numPlayers - 1; i++)
         {
            if(allPlayers[i].getScore() < allPlayers[i+1].getScore() && !allPlayers[i+1].getName().equalsIgnoreCase("EMPTY"))
            {
               temp = allPlayers[i + 1].getName();
               tempScore = allPlayers[i + 1].getScore();
               tempMatches = allPlayers[i + 1].getMatches();
               allPlayers[i + 1].setName(allPlayers[i].getName());
               allPlayers[i + 1].setScore(allPlayers[i].getScore());
               allPlayers[i + 1].setMatches(allPlayers[i].getMatches());
               allPlayers[i].setName(temp);
               allPlayers[i].setScore(tempScore);
               allPlayers[i].setMatches(tempMatches);
            }
         }
      }
      // Print
      System.out.println("Printing Players");
      System.out.println();
      for(int x = 0; x < numPlayers; x++){
         if(!allPlayers[x].getName().equals("EMPTY")){
            System.out.println("" + (x+1) + ": " + allPlayers[x].getName() + ": " + allPlayers[x].getScore());
         }
      }
   }
// Print players without score/rank
   public static void rawPrint()throws Exception {
   // Bubble Sort
      int tempScore, tempMatches;
      String temp;
      for(int z = 0; z < numPlayers; z++)
      {
         for(int i = 0; i < numPlayers - 1; i++)
         {
            if(allPlayers[i].getScore() < allPlayers[i+1].getScore() && !allPlayers[i+1].getName().equalsIgnoreCase("EMPTY"))
            {
               temp = allPlayers[i + 1].getName();
               tempScore = allPlayers[i + 1].getScore();
               tempMatches = allPlayers[i + 1].getMatches();
               allPlayers[i + 1].setName(allPlayers[i].getName());
               allPlayers[i + 1].setScore(allPlayers[i].getScore());
               allPlayers[i + 1].setMatches(allPlayers[i].getMatches());
               allPlayers[i].setName(temp);
               allPlayers[i].setScore(tempScore);
               allPlayers[i].setMatches(tempMatches);
            }
         }
      }
      // Print
      System.out.println("Raw Print");
      System.out.println();
      for(int x = 0; x < numPlayers; x++){
         if(!allPlayers[x].getName().equals("EMPTY")){
            System.out.println("" + allPlayers[x].getName());
         }
      }
   }
// Add new player to system
   public static void newPlayer()throws Exception {
      System.out.println("Please enter a name:");
      namecheck = false; samecheck = false;
      while(namecheck == false){
         Name = keyboard.nextLine();
         for(int x = 0; x < numPlayers; x++){
            if(Name.equalsIgnoreCase(allPlayers[x].getName())){
               samecheck = true;
            }
         }
         if("EMPTY".equalsIgnoreCase(Name)){
            System.out.println("Pick a different name!");
            samecheck = false;
         }
         else if(samecheck == true){
            System.out.println("Name already taken, try again");
            samecheck = false;
         }
         else
            namecheck = true;
      }
      for(int x = 0; x < numPlayers; x++){
         if(allPlayers[x].getName().equalsIgnoreCase("EMPTY")){
            allPlayers[x].setName(Name);
            System.out.println("" + Name + " has been added to the system.");
            System.out.println();
            x = numPlayers;
         }
      }
   }
// Edit a player's score
   public static void editPlayer()throws Exception {
      Score = 0;
      samecheck = false;
      System.out.println("Please enter a player:");
      Name = keyboard.nextLine();
      for(int x = 0; x < numPlayers; x++){
         if("EMPTY".equalsIgnoreCase(Name)){
            System.out.println("Choose a different name!");
            x = numPlayers;
            samecheck = true;
         }
         if(samecheck == false && Name.equalsIgnoreCase(allPlayers[x].getName())){
            System.out.println("Please enter a new score:");
            Score = Integer.parseInt(keyboard.nextLine());
            if(Score == 0){
               System.out.println("Score cannot equal 0!");
               System.out.println();
            }
            else
               allPlayers[x].setScore(Score);
            x = numPlayers;
            samecheck = true;
         }
      }
      if(samecheck == false){
         System.out.println("Player not found.");
      }
   }
// Remove player
   public static void removePlayer()throws Exception {
      samecheck = false;
      System.out.println("Who would you like to remove?");
      Name = keyboard.nextLine();
      for(int x = 0; x < numPlayers; x++){
         if("EMPTY".equalsIgnoreCase(Name)){
            System.out.println("Choose a different name!");
            x = numPlayers;
            samecheck = true;
         }
         if(samecheck == false && Name.equalsIgnoreCase(allPlayers[x].getName())){
            System.out.println("" + allPlayers[x].getName() + " was successfully removed.");
            allPlayers[x].setName("EMPTY");
            allPlayers[x].setScore(1600);
            allPlayers[x].setMatches(0);
            x = numPlayers;
            samecheck = true;
         }
      }
      if(samecheck == false){
         System.out.println("Player not found.");
      }
   }
// 1vs1 match
   public static void newMatch()throws Exception {
      samecheck = false;
      System.out.println("Who won the match?");
      // player 1
      Name = keyboard.nextLine();
      for(int x = 0; x < numPlayers; x++){
         if("EMPTY".equalsIgnoreCase(Name)){
            System.out.println("Choose a different name!");
            x = numPlayers;
            samecheck = true;
         }
         if(samecheck == false && Name.equalsIgnoreCase(allPlayers[x].getName())){
            System.out.println("Who did " + allPlayers[x].getName() + " defeat?");
            
            // player 2
            Name2 = keyboard.nextLine();
            for(int y = 0; y < numPlayers; y++){
               if("EMPTY".equalsIgnoreCase(Name2)){
                  System.out.println("Choose a different name!");
                  y = numPlayers;
                  samecheck = true;
               }
               if(samecheck == false && Name2.equalsIgnoreCase(allPlayers[y].getName())){
               // elo formula
                  System.out.println("" + Name + " defeated " + Name2 + ".");
                  Score = allPlayers[x].getScore(); Score2 = allPlayers[y].getScore();
                  SA = Score; SB = Score2;
                  MA = allPlayers[x].getMatches(); MB = allPlayers[y].getMatches();
                  KA = 16 - MA; KB = 16 - MB;
                  if(KA < 8)
                     KA = 8;
                  if(KB < 8)
                     KB = 8;
                  TA = SA/400; TB = SB/400;
                  QA = Math.pow(10, TA); QB = Math.pow(10, TB);
                  EA = QA/(QA + QB); EB = QB/(QB + QA);
                  System.out.println("" + Name + "'s Old Score: " + Score + ".   " + Name2 + "'s Old Score: " + Score2);
                  SA = SA + KA*(2 - EA);
                  SB = SB - KB*(2 - EA);
                  if(SA < 1)
                     SA = 1;
                  if(SB < 1)
                     SB = 1;
                  Score = (int) Math.round(SA); Score2 = (int) Math.round(SB);
                  MA++; MB++;
                  allPlayers[x].setMatches(MA); allPlayers[y].setMatches(MB);
                  allPlayers[x].setScore(Score); allPlayers[y].setScore(Score2);
                  System.out.println("" + Name + "'s New Score: " + Score + ".   " + Name2 + "'s New Score: " + Score2);
                  System.out.println("" + Name + " Played " + MA + " Games.   " + Name2 + " Played " + MB + " Games.");
                  y = numPlayers;
                  samecheck = true;
               }
            }
            if(samecheck == false){
               System.out.println("Player not found.");
            }
            x = numPlayers;
            samecheck = true;
         }
      }
      if(samecheck == false){
         System.out.println("Player not found.");
      }
   }
// Open bracket GUI
   public static void newBracket()throws Exception {
      System.out.println("newB");
      frame = new JFrame("Bracket");
      //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
      frame.setSize(1200, 900);
      frame.setLocation(40, 50);
      frame.setContentPane(new Bracket());
      frame.setVisible(true);
   }
// Save and exit
   public static void savePlayers()throws Exception {
   // Bubble Sort
      int tempScore, tempMatches;
      String temp;
      for(int z = 0; z < numPlayers; z++)
      {
         for(int i = 0; i < numPlayers - 1; i++)
         {
            if(allPlayers[i].getScore() < allPlayers[i+1].getScore() && !allPlayers[i+1].getName().equalsIgnoreCase("EMPTY"))
            {
               temp = allPlayers[i + 1].getName();
               tempScore = allPlayers[i + 1].getScore();
               tempMatches = allPlayers[i + 1].getMatches();
               allPlayers[i + 1].setName(allPlayers[i].getName());
               allPlayers[i + 1].setScore(allPlayers[i].getScore());
               allPlayers[i + 1].setMatches(allPlayers[i].getMatches());
               allPlayers[i].setName(temp);
               allPlayers[i].setScore(tempScore);
               allPlayers[i].setMatches(tempMatches);
            }
         }
      }
      System.out.println("Press s to save, press anything else to quit.");
      System.out.println();
      Save = keyboard.nextLine();
      // prints data to players.txt
      if("s".equalsIgnoreCase(Save)){
         PrintWriter outfile = null;
         try{
            outfile = new PrintWriter(new FileWriter("players.txt"));
         }
         catch(Exception e){
            System.out.println("Cannot find players.txt, not saving.");
         }
         System.out.println("Saving...");
         System.out.println();
         try{
            for(int x = 0; x < numPlayers; x++){
            //System.out.println("" + allPlayers[x].getName() + " saved!");
               outfile.write("" + allPlayers[x].getName() + "%" + allPlayers[x].getScore() + "%" + allPlayers[x].getMatches() + "%");
               outfile.println();
            }
         }
         catch(Exception e){
            System.out.print("ERROR");
         }
         outfile.close();
         System.out.print("Done!");
         System.exit(0);
      }
      else{
         System.out.println("Not Saving...");
         System.exit(0);
      }
   }
}
