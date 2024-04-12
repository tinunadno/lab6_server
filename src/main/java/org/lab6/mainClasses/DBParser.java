package org.lab6.mainClasses;

import org.lab6.Main;
import org.lab6.storedClasses.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBParser {
    public static ArrayList<LabWork> parseLabWorkFromDB(){
        ArrayList<LabWork> labWorkArrayList=new ArrayList<>();
        String query = "SELECT * FROM LabWork";
        PreparedStatement st=null;
        try {
            st = Main.getConnection().prepareStatement(query);
            ResultSet LabWorkSet = st.executeQuery();

            while(LabWorkSet.next()){

                int lw_id=Integer.parseInt(LabWorkSet.getString("id"));
                int lw_userID=Integer.parseInt(LabWorkSet.getString("user_id"));
                String lw_name=LabWorkSet.getString("name");
                String lw_creationDate=LabWorkSet.getString("creationDate");
                double lw_minimalPoint=Double.parseDouble(LabWorkSet.getString("minimalPoint"));
                String lw_description=LabWorkSet.getString("description");
                int lw_tunedInWorks=Integer.parseInt(LabWorkSet.getString("tunedInWorks"));
                Difficulty lw_difficulty=Difficulty.parse(LabWorkSet.getString("difficulty"));

                String cords_id=LabWorkSet.getString("coordinates");
                String cords_query="SELECT * FROM coordinates WHERE(id="+cords_id+")";
                PreparedStatement cords_st=Main.getConnection().prepareStatement(cords_query);
                ResultSet coordinatesSet=cords_st.executeQuery();
                coordinatesSet.next();
                float cords_x=Float.parseFloat(coordinatesSet.getString("x"));
                int cords_y=Integer.parseInt(coordinatesSet.getString("y"));
                Coordinates coordinates=new Coordinates(cords_x, cords_y);

                String person_id=LabWorkSet.getString("author");
                String person_query="SELECT * FROM person WHERE(id="+person_id+")";
                PreparedStatement person_st=Main.getConnection().prepareStatement(person_query);
                ResultSet personSet=person_st.executeQuery();
                personSet.next();
                String person_name=personSet.getString("name");
                String person_passportID=personSet.getString("passportId");
                Color color= Color.parse(personSet.getString("eyeColor"));

                String location_id=personSet.getString("location");
                String location_query="SELECT * FROM location WHERE(id="+location_id+")";
                PreparedStatement location_st=Main.getConnection().prepareStatement(location_query);
                ResultSet locationSet=location_st.executeQuery();
                locationSet.next();
                float location_x=Float.parseFloat(locationSet.getString("x"));
                float location_y=Float.parseFloat(locationSet.getString("y"));
                String location_name=locationSet.getString("name");

                Location location=new Location(location_x, location_y, location_name);

                Person person=new Person(person_name, person_passportID, color ,location);


                LabWork labWork=new LabWork(lw_id, lw_userID, lw_name, coordinates, lw_creationDate, lw_minimalPoint, lw_description, lw_tunedInWorks, lw_difficulty, person);
                System.out.println(labWork.getID());
                labWorkArrayList.add(labWork);

            }

        }catch(SQLException e){
            System.out.println("bad querry");
            e.printStackTrace();
        }
        return labWorkArrayList;
    }
}
