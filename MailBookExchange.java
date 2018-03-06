/*This program is intended to be used for a mail order book exchange.
The user inputs the name, address, and book title of all the participants.
The program then shuffles the information so that everyone gets
gets a different book from someone else.

Written by Kevin Carney
*/

/* EXPANDED FUNCTIONALITY TO DO:
- Pretty GUI
- Save each person's information in a text file so that
repeat participants can be easily accesed.
*/

import java.util.Scanner;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class MailBookExchange
{
   public static void main(String[] args)
   {
      //instantiate an ArrayList for storing all the people
      ArrayList<Individual> directory = new ArrayList<Individual>();
      Scanner kbd = new Scanner(System.in);
   
      System.out.print("How many people are there? ");
      int numberOfPeople = kbd.nextInt();
      System.out.println();
      
      //@CLEANUP
      //this is a hack because I don't know how to dynamically make more
      //objects as the inputs come in. But it works. 
      for (int i=1; i <= numberOfPeople; i++)
      {
         Individual person = new Individual();
         System.out.print("Enter the full name of the person: ");
         System.out.println();
         person.setFullName(kbd.next());
         
         System.out.print("Enter the address of the person: ");
         System.out.println();
         person.setAddress(kbd.next());
         
         System.out.print("Enter the book they submitted: ");
         System.out.println();
         person.setBookTitle(kbd.next());
         
         directory.add(person);
      }
      //making a copy of the directory so we can remove stuff without
      //losing the information forever
      //ArrayList<Individual> copyDir = new ArrayList<Individual>();
      //copyDir.addAll(directory);
      
      matchUp(directory, numberOfPeople);
      System.out.print("all done!");
   }

   
   static void matchUp(ArrayList<Individual> dir, int numOfParticipants)
   {
      ArrayList<Individual> usedIndividual = new ArrayList<Individual>();
      
      for (int i = 1; i <= numOfParticipants; i++)
      {
         int dirSize = dir.size()-1;
         int giver = i-1;
         int receiver = randomWithRange(0, dirSize);
         boolean uniquePair = false;         
      
         //check to see if the receiver has already gotten a book
         while (dir.get(receiver).getHasReceived() == true)
         {
            receiver = randomWithRange(0, dirSize);
            //just making sure no one is getting themselves		 
            while (giver == receiver)
            {
               receiver = randomWithRange(0, dirSize);
            }
         }
       
		//@CLEANUP
		//This is our solution of what to do in an odd number person scenario.
		//We are making sure that no one is getting the same book from the person they are
		//giving to. This stops one person from being left out. Ideally though, anyone could 
		//get anyone and it would still work.
         while(!uniquePair)
         {
            if (dir.get(receiver).getGivingTo() == dir.get(giver))
            {
               receiver = randomWithRange(0, dirSize);
			   //Since we're generating a new number, we have to check again
			   //that we've not giving a person themselves
               while (giver == receiver)
               {
                  receiver = randomWithRange(0, dirSize);
               }
            }
            else
            {
               dir.get(giver).setGivingTo(dir.get(receiver));
               uniquePair = true;				
            }				
         }
      
         dir.get(giver).setGivingTo(dir.get(receiver));
         dir.get(receiver).setHasReceived(true);
         
         System.out.printf("%s gives their book to %s", dir.get(giver).getFullName(), dir.get(giver).getGivingTo().getFullName());
         System.out.println();
      }
   } 
   
   static int randomWithRange(int min, int max)
   {
      int range = (max - min) + 1;
      int output = (int) (Math.random() * range) + min; 
      return output;
   }  
}

//Class where we will store all the information about each person
class Individual
{
   private String fullName;
   private String address;
   private String bookTitle;
   private boolean hasReceived = false;
   private Individual givingTo;

   
   //requisite getters and setters
   public void setFullName(String newName)
   {
      fullName = newName;
   }
   
   public String getFullName()
   {
      return fullName;
   }
   
   public void setAddress(String newAddress)
   {
      address = newAddress;
   }
   
   public String getAddress()
   {
      return address;
   }
   
   public void setBookTitle(String newBook)
   {
      bookTitle = newBook;
   }
   
   public String getBookTitle()
   {
      return bookTitle;
   }
   
   public void setHasReceived(boolean receiving)
   {
      hasReceived = receiving;
   }
   
   public boolean getHasReceived()
   {
      return hasReceived;
   }
   
   
   //@CLEANUP
   //In a perfect world, this would throw an error if you tried
   //to give someone themselves
   public void setGivingTo(Individual person)
   {
      givingTo = person;
   }
   
   public Individual getGivingTo()
   {
      return givingTo;
   }
}

class GuiConstructor
{
	
}