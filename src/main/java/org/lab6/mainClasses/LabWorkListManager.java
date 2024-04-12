package org.lab6.mainClasses;

import org.lab6.Main;
import org.lab6.storedClasses.Difficulty;
import org.lab6.storedClasses.LabWork;
import org.lab6.storedClasses.Person;

import java.io.FileWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

public class LabWorkListManager {
	private static ArrayList<LabWork> list;
	private static java.time.LocalDate creationDate;

	/**
	 * initialize LabWork ArrayList
	 */
	public static void init(){
		list=DBParser.parseLabWorkFromDB();
	}

	/**
	 * add LabWork Object to LabWork List
	 * @param lw
	 */
	
	public static void append(LabWork lw, int userID){
		lw.setUserID(userID);

		try{
			DBLabWorkManipulator.addLabWork(lw);
			list.add(lw);
			ResponseManager.append("successfully added new instance");
		}catch(SQLException e){
			ResponseManager.append("SERVER_ERROR:can't add LabWork instance to DataBase");
			e.printStackTrace();
		}
	}


	/**
	 * replace LabWork object in ArrayList by its id in ArrayList
	 * @param id
	 * @param lw
	 */
	public static void set(int id, LabWork lw){
		list.set(id, lw);
		ResponseManager.append("successfully updated instance with id: "+id);
	}

	/**
	 * removes LabWork object from ArrayList by its id
	 * @param id
	 */
	
	public static void remove(int id){

		try{
			list.remove(id);
			ResponseManager.append("successfully removed instance with index " + id);
		}
		catch(IndexOutOfBoundsException e){
			ResponseManager.append("this index doesnt exists");
		}
	}

	/**
	 * clear ArrayList
	 */
	public static void clear(int userID){
		try {

			DBLabWorkManipulator.clear(userID);

			System.out.println("cleared");
			Person.clearPassportBase();
			list.removeIf(lw->(lw.getUserID()==userID));
			ResponseManager.append("successfully cleared LabWork Base");
		}catch(SQLException e){
			ResponseManager.append("SERVER_ERROR:can't clear LabWork instances of current user");
			e.printStackTrace();
		}
	}

	/**
	 * save ArrayList as json file
	 * @param way
	 */
	public static void save(String way){
		try (var fw = new FileWriter(way)) {
			fw.write(toJson());
			ResponseManager.append("successfully saved current LabWork List on server");
		} catch (Exception e) {
			ResponseManager.append("bad file name");
		}
	}

	/**
	 * add LabWork object if minimal point field is max
	 * @param lw
	 */
	public static void addIfMax(LabWork lw, int userID){
		boolean flag=true;
		for(LabWork labwork: list)
			flag=!(labwork.getMinimalPoint()>lw.getMinimalPoint());
		if(flag)append(lw, userID);
	}

	/**
	 * removes LabWork object with max minimalPoint
	 * @param val
	 */
	public static void RemoveGreater(float val){
		for(int i=0;i<list.size();i++)
			if(list.get(i).getMinimalPoint()>val)remove(i);
		ResponseManager.append("remove greater");
	}

	/**
	 * shows LabWork objects with desired description
	 * @param description
	 */
	public static void FilterByDescription(String description){
		String ret="";
		for(int i=0;i<list.size();i++)
			if(list.get(i).getDescription().equals(description))ret+=(list.get(i).toString()+"\n")+"\n";
		ResponseManager.append(ret);
	}

	/**
	 * print all LabWork objects sorted by difficulty
	 */
	public static void printFieldAscendingDifficulty(){
		String ret="";
		for(Difficulty diff:Difficulty.values())
			for(int i=0;i<list.size();i++)
				if(list.get(i).getDifficulty()==diff)ret+=(list.get(i).toString()+"\n")+"\n";
		ResponseManager.append(ret);
	}

	/**
	 * print LabWork object with minimal name
	 */
	public static void printMinByName(){
		if(list.size()!=0 && list!=null){
			LabWork lw=list.get(0);
			for(int i=1;i<list.size();i++)
				if(lw.getName().length()>list.get(i).getName().length())lw=list.get(i);
			ResponseManager.append(lw.toString());
		}
		else {
			ResponseManager.append("list is empty");
		}
	}

	/**
	 * sort objects in ArrayList
	 */
	public static void sort(){
		Collections.sort(list);
	}

	/**
	 * print collection info (size, type, init date)
	 * @return
	 */
	public static String getCollectionInfo(){
		return "Collection info:[size:"+list.size()+", type:"+list.getClass()+", init date:"+creationDate+"]";
	}

	/**
	 * get Collection as String
	 * @return
	 */
	public static String getCollectionAsString(){
		if(list.size()==0)return "list is empty";
		String ret="";
		for (LabWork value : list) {
			ret+=(value.toString()+"\n\n");
		}
		return ret;
	}

	/**
	 * get Collection as String in json format
	 * @return
	 */
	public static String toJson(){
		String ret="{\n\"LabWorks\": [\n";
		for(int i=0;i<list.size()-1;i++)
			ret+=list.get(i).toJson()+",\n";
		ret+=list.get(list.size()-1).toJson()+"\n]\n}";
		return ret;
	}
}