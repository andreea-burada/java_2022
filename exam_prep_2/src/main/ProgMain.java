package main;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class ProgMain {

	public static void main(String[] args) {
		// reading from .txt file
		String fileName = "pisici.txt";
		String splitdelimiters = "[ ,]+|[ ;]+";
		List<String> catList = new ArrayList<String>();
		File file = new File(fileName);
		int lineCount = 0;
		try {
			RandomAccessFile rafFile = new RandomAccessFile(file, "r");
			while (rafFile.getFilePointer() < rafFile.length()) {
				lineCount++;
				System.out.println("\nLine " + lineCount + ":\n");
				// parse line by line
				String[] currentLine = rafFile.readLine().split(splitdelimiters);
				for (String cat : currentLine) {
					catList.add(cat);
					System.out.println(cat + "*");
				}
			}
			rafFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ioex) {
			ioex.printStackTrace();
		}

		
		
		// writing to binary file
		String binaryOutName = "pisici.bin";
		DataOutputStream binaryOut = null;
		try {
			binaryOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(binaryOutName)));
			// write all names
			for (String cat : catList) {
				binaryOut.writeUTF(cat);
			}
			binaryOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ioex) {
			ioex.printStackTrace();
		}

		
		
		// reading from binary file
		String binaryInName = "pisici.bin";
		DataInputStream binaryIn = null;
		try {
			binaryIn = new DataInputStream(new BufferedInputStream(new FileInputStream(binaryInName)));
			String catsString = "";
			// read line by line
			try {
				while (true) {
					catsString += binaryIn.readUTF() + " * ";
				}
			} catch (EOFException eofex) {
				System.out.println("\nBinary reading done.\n");
				binaryIn.close();
				
				System.out.println(catsString);
			}
		} catch (FileNotFoundException e) {
			// e.printStackTrace();
			System.out.println("File \"" + binaryInName + "\" could not be found");
		} catch (IOException ioex) {
			ioex.printStackTrace();
		}
		
		
		// DB playing
		Connection dbConnection = null;
		try {
			Class.forName("org.sqlite.JDBC");
			dbConnection = DriverManager.getConnection("jdbc:sqlite:test.db");
			dbConnection.setAutoCommit(false);
			
			// create table
			Statement query = dbConnection.createStatement();
			ResultSet result = null;
			try {
				query.executeUpdate("drop table CATS");
			}
			catch (SQLException sqlex)
			{
				System.out.println("Table did not exist\n");
			}
			query.executeUpdate("create table CATS (id INTEGER PRIMARY KEY, cat_name TEXT)");
			dbConnection.commit();
			
			String insertQuery = "";
			// add to table the list
			int id = 1;
			for(String cat : catList)
			{
				insertQuery = "insert into CATS (id, cat_name) values (" + Integer.toString(id) + ", \'" 
						+ cat + "\')";
				query.executeUpdate(insertQuery);
				id++;
			}
			dbConnection.commit();
			
			// get no cats in table
			result = query.executeQuery("select count(*) as all_cats from CATS");
			System.out.println("\nIn DB we have " + result.getString("all_cats") + " cats\n");
			
			// update one cat
			int toChangeId = 1;
			query.executeUpdate("update CATS set cat_name = 'El Mustachio' where id = " + toChangeId);
			dbConnection.commit();
			
			// get all cats
			result = query.executeQuery("select * from CATS");
			String DBstring = "";
			JSONObject currCat, catJSONArray = new JSONObject();
			while (result.next() == true)
			{
				currCat = new JSONObject();
				currCat.put("id", result.getInt(1));
				currCat.put("cat_name", result.getInt(2));
				
				catJSONArray.append("cat", currCat);
				
				System.out.println("Id: " + result.getInt(1) + "; Cat Name: " 
						+ result.getString("cat_name"));
				DBstring += result.getString("id") + ":" + result.getString("cat_name") + "\r\n";
				
			}
			
			//JSONObject json = JSONObject.quote(binaryInName);
			System.out.println(catJSONArray + "\n");
			System.out.println(catJSONArray.toString() + "\n");
			System.out.println(JSONObject.quote(catJSONArray.toString()) + "\n");
			
			query.close();
			dbConnection.close();
			
			System.out.println("\n" + DBstring);
		} catch (ClassNotFoundException | SQLException | JSONException e) {
			e.printStackTrace();
		}
		
		
		
	}	// main end

}
