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
         
        /* System.out.print("Enter the address of the person: ");
         System.out.println();
         person.setAddress(kbd.next());
         
         System.out.print("Enter the book they submitted: ");
         System.out.println();
         person.setBookTitle(kbd.next());*/
         
         directory.add(person);
      }
      //making a copy of the directory so we can remove stuff without
      //losing the information forever
      ArrayList<Individual> copyDir = new ArrayList<Individual>();
      copyDir.addAll(directory);
      
      matchUp(copyDir);
   }
   //@TO-DO
   //Right now, this function doesn't really work if there is an odd number of people
   //Ideally, you would just take that hanging person and swap them with someone
   //who has already been swapped, but that is not possible since we removed
   //everyone who has been swapped from that directory already.
   static void matchUp(ArrayList<Individual> dir)
   {
      ArrayList<Individual> usedIndividual = new ArrayList<Individual>();
      
      while(!(dir.isEmpty()))
      {
         int dirSize = dir.size()-1;
         int person1 = randomWithRange(0, dirSize);
         int person2 = randomWithRange(0, dirSize);
         
         //just making sure no one is getting themselves
         while (person1 == person2)
         {
            person2 = randomWithRange(0, dirSize);
         }
         usedIndividual.add(dir.get(person1));
         
         //@TO-DO
         //This is just superficially printing it. Would like to have that
         //connection actually stored somewhere.
         System.out.printf("%s gives their book to %s", dir.get(person1).getFullName(), dir.get(person2).getFullName());
         System.out.println();
         dir.remove(person1);
      }
   } 
   
   static int randomWithRange(int min, int max)
   {
      int range = (max - min) + 1;
      return (int) (Math.random() * range) + min; 
   }  
}

//Class where we will store all the information about each person
class Individual
{
   private String fullName;
   private String address;
   private String bookTitle;
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
   
   //this is our way of tracking who got who
   public void setGivingTo(Individual person)
   {
      if (fullName != person.getFullName())
      {
         givingTo = person;
      }
      else if (fullName == person.getFullName())
      {
         System.out.println("You tried to give a person the same person.");
      }
   }
   
   public Individual getGivingTo()
   {
      return givingTo;
   }
}