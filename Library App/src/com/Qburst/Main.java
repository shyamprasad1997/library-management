package com.Qburst;
/*
=======================================
==== Library Management App        ====
==== Copyrighted to Shyam Prasad P ====
==== Qburst Technologies, Koratty  ====
==== Email: shyamp@qburst.com      ====
=======================================
*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Main {
    public static void main(String[] args) {
        Main obj = new Main();
        String bookname, username, str;
        int choice, bookid, userid;
        do {
            System.out.println("Enter your choice:");
            System.out.println("1. Issue a book\n2. Return a book\n3. Get details of an issue\n4. I'm new here");
            Scanner scanner = new Scanner(System.in);
            choice = scanner.nextInt();
            switch (choice) {

                case 1:
                    clearScreen();
                    System.out.println("You've decided to issue a new book, please provide the credentials:");
                    System.out.println("Username:");
                    username = scanner.next();
                    //check if he is a valid user
                    if (!obj.checkIfValid(username)) {
                        break;
                    }
                    System.out.println("He's a valid user! Please provide the information of the book to issue:");
                    System.out.println("Book name: ");
                    bookname = scanner.next();
                    bookid = obj.getBook(bookname);
                    //check in file for an book with bookid
                    if(!obj.checkIfBookExist(bookid)) {
                        break;
                    }
                    userid = obj.getUser(username);
                    if(!obj.issueBook(userid, bookid))
                    {
                        break;
                    }
                    System.out.println("Successfully issued the book. Thank you!");
                    break;

                case 2:
                    clearScreen();
                    System.out.println("You've decided to return a book, please provide the credentials:");
                    System.out.println("Username:");
                    username = scanner.next();
                    //check if he is a valid user
                    if (!obj.checkIfValid(username)) {
                        break;
                    }
                    System.out.println("He's a valid user! Please provide the information of the book to return:");
                    System.out.println("Book name: ");
                    bookname = scanner.next();
                    bookid = obj.getBook(bookname);
                    //check in file for an book with bookname
                    if(!obj.checkIfBookExist(bookid)) {
                        break;
                    }
                    userid = obj.getUser(username);
                    if(!obj.returnBook(userid, bookid))
                    {
                        break;
                    }
                    System.out.println("Successfully issued the book. Thank you!");
                    break;

                case 3:
                    clearScreen();
                    System.out.println("Details of issue:");
                    obj.issueDetails();
                    break;

                case 4:
                    clearScreen();
                    System.out.println("Enter name: ");
                    String name = scanner.next();
                    if (!obj.checkIfValid(name)) {
                        System.out.println("Sorry, user already exist.");
                        break;
                    }
                    int memID = 0;
                    try {
                        BufferedReader br = new BufferedReader(new FileReader("memberDetails.csv"));
                        while ((br.readLine()) != null) {
                            memID++;
                        }
                    } catch (Exception e) {
                    }
                    try {
                        memID++;
                        BufferedWriter out = new BufferedWriter(new FileWriter("memberDetails.csv", true));
                        out.write(name + "," + memID + "\n");
                        out.close();
                    } catch (Exception e) {}
                    break;

                default:
                    System.out.println("Not a valid option, please try again!");
            }
            System.out.println("\nDo you want to continue(y/n): ");
            str = scanner.next();
        }while (str.equals("y"));
    }

    public boolean checkIfValid(String username)
    {
        try{
            BufferedReader br = new BufferedReader(new FileReader("memberDetails.csv"));
            String line ="";
            while ((line = br.readLine()) != null) {
                String[] test = line.split(",");
                if(username.equals(test[0]))
                {
                    return true;
                }
            }
        }catch (Exception e){}
        System.out.println("Sorry, Invalid credentials.");
        try{
            Thread.sleep(1000);
        }catch(Exception e){}
        return false;
    }

    public int getUser(String username)
    {
        int id=0;
        try{
            BufferedReader br = new BufferedReader(new FileReader("memberDetails.csv"));
            String line ="";
            while ((line = br.readLine()) != null) {
                String[] test = line.split(",");
                if(username.equals(test[0]))
                {
                    id = Integer.parseInt(test[1]);
                }
            }
        }catch (Exception e){}
        return id;
    }

    public String getUser(int userid)
    {
        String name="";
        try{
            BufferedReader br = new BufferedReader(new FileReader("memberDetails.csv"));
            String line ="";
            while ((line = br.readLine()) != null) {
                String[] test = line.split(",");
                if(userid == Integer.parseInt(test[1]))
                {
                    name = test[0];
                }
            }
        }catch (Exception e){}
        return name;
    }

    public int getBook(String bookname)
    {
        int id=0;
        try{
            BufferedReader br = new BufferedReader(new FileReader("bookDetails.csv"));
            String line ="";
            while ((line = br.readLine()) != null) {
                String[] test = line.split(",");
                if(bookname.equals(test[0]))
                {
                    id = Integer.parseInt(test[1]);
                }
            }
        }catch (Exception e){}
        return id;
    }

    public String getBook(int bookid)
    {
        String name="";
        try{
            BufferedReader br = new BufferedReader(new FileReader("bookDetails.csv"));
            String line ="";
            while ((line = br.readLine()) != null) {
                String[] test = line.split(",");
                if(bookid == Integer.parseInt(test[1]))
                {
                    name = test[0];
                }
            }
        }catch (Exception e){}
        return name;
    }

    public boolean checkIfBookExist(int bookid)
    {
        try{
            BufferedReader br = new BufferedReader(new FileReader("bookDetails.csv"));
            String line ="";
            while ((line = br.readLine()) != null) {
                String[] test = line.split(",");
                if(bookid == Integer.parseInt(test[1]))
                {
                    return true;
                }
            }
        }catch (Exception e){}
        System.out.println("Sorry, No such book exist.");
        try{
            Thread.sleep(1000);
        }catch(Exception e){}
        return false;
    }

    public boolean issueBook(int userid, int bookid)
    {
        String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
        System.out.println(timeStamp);
        try{
            BufferedReader br = new BufferedReader(new FileReader("issueDetails.csv"));
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] test = line.split(",");
                if(bookid == Integer.parseInt(test[1]))
                {
                    System.out.println("Sorry, That book is currently not available.");
                    return false;
                }
                if(userid == Integer.parseInt(test[0]))
                {
                    System.out.println("Sorry, The user has already been issued a book.");
                    return false;
                }
            }
            br.close();
            BufferedWriter bw = new BufferedWriter(new FileWriter("issueDetails.csv",true));
            bw.write(userid + "," + bookid + "," + timeStamp + "\n");
            bw.close();
        }catch (Exception e){}
        return true;
    }

    public boolean returnBook(int userid, int bookid)
    {
        try{
            BufferedReader br = new BufferedReader(new FileReader("issueDetails.csv"));
            BufferedWriter bw = new BufferedWriter(new FileWriter("temp.csv",false));
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] test = line.split(",");
                if(bookid == Integer.parseInt(test[1]))
                {
                    if(userid == Integer.parseInt(test[0]))
                    {
                        continue;
                    }
                }
                bw.write(test[0] + "," + test[1] + "," + test[2] + "\n");
            }
            br.close();
            bw.close();
            File file = new File("temp.csv");
            File file1 = new File("issueDetails.csv");
            file.renameTo(file1);
            }catch (Exception e){}
        return true;
    }

    public void issueDetails()
    {
        String date, username, bookname;
        try{
            BufferedReader br = new BufferedReader(new FileReader("issueDetails.csv"));
            String line = "";
            System.out.println("Username\tBookname\tDate of issue");
            while ((line = br.readLine()) != null) {
                String[] test = line.split(",");
                username = getUser(Integer.parseInt(test[0]));
                bookname = getBook(Integer.parseInt(test[1]));
                System.out.println(username + "\t" + bookname + "\t" + test[2]);
            }
            br.close();
        }catch (Exception e){}
    }

    public static void clearScreen()
    {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}