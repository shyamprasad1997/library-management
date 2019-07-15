package com.Qburst;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.*;

public class Main {

    public static void main(String[] args) {
        Main obj = new Main();
        String issuername, bookname, username, chr;
        int bookNum, choice, bookid, userid;

        System.out.println("Enter your choice:");
        System.out.println("1. Issue a book\n2. Return a book\n3. Get details of an issue\n4. I'm new here");
        Scanner scanner = new Scanner(System.in);
        choice = scanner.nextInt();
        do {
            switch (choice) {
                case 1:
                    System.out.println("You've decided to issue a new book, please provide the credentials:");
                    System.out.println("UserID:");
                    username = scanner.next();
                    //check if he is a valid user
                    if (!obj.checkIfValid(username))
                        break;
                    System.out.println("He's a valid user! Please provide the information of the book to issue:");
                    System.out.println("BookId: ");
                    bookid = scanner.nextInt();
                    //check in file for an book with bookid
                    if(!obj.checkIfBookExist(bookid))
                        break;
                    userid = obj.getUserID(username);
                    obj.issueBook(userid, bookid);

                    break;
                case 2:

                    break;
                case 3:
                    break;
                case 4:
                    System.out.println("Enter name: ");
                    String name = new String(scanner.next());
                    int memID = 0;
                    try {
                        BufferedReader br = new BufferedReader(new FileReader("memberDetails.csv"));
                        String line = "";
                        while ((line = br.readLine()) != null) {
                            memID++;
                        }
                    } catch (Exception e) {
                    }
                    try {
                        memID++;
                        BufferedWriter out = new BufferedWriter(new FileWriter("memberDetails.csv", true));
                        out.write(name + "," + memID + "\n");
                        out.close();
                    } catch (Exception e) {
                    }
                    break;
                default:
                    System.out.println("Not a valid option, please try again!");
            }
            System.out.println("Do you want to continue(y/n): ");
            chr = scanner.next();
        } while (chr.equals("y"));
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
        }catch (Exception e){
        }
        System.out.println("Sorry, Invalid credentials");
        try{
            Thread.sleep(1000);
        }catch(Exception e){}
        return false;
    }

    public int getUserID(String username)
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

    public boolean checkIfBookExist(int bookid)
    {
        try{
            BufferedReader br = new BufferedReader(new FileReader("bookDetails.csv"));
            String line ="";
            while ((line = br.readLine()) != null) {
                String[] test = line.split(",");
                if(bookid == Integer.parseInt(test[0]))
                {
                    return true;
                }
            }
        }catch (Exception e){}
        return false;
    }

    public boolean issueBook(int userid, int bookid)
    {
        try{
            BufferedReader br = new BufferedReader(new FileReader("issueDetails.csv"));
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] test = line.split(",");
                if(bookid == Integer.parseInt(test[0]))
                {
                    return false;
                }
                if(userid == Integer.parseInt(test[1]))
                {
                    return false;
                }
            }
            br.close();
            BufferedWriter bw = new BufferedWriter(new FileWriter("issueDetails.csv"));
            bw.write(""+userid + "," + bookid);
            bw.close();
        }catch (Exception e){}
        return true;
    }
}