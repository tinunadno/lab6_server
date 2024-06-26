package org.lab6.mainClasses;

import org.lab6.Main;
import org.lab6.storedClasses.*;

import java.sql.*;
import java.util.ArrayList;

public class DBParser {
    public static ArrayList<LabWork> parseLabWorkFromDB(){
        Connection connection=LabWorkDAO.getConnection();
        ArrayList<LabWork> labWorkArrayList=new ArrayList<>();
        String query = "SELECT * FROM LabWork";
        PreparedStatement st=null;
        try {
            st = connection.prepareStatement(query);
            ResultSet LabWorkSet = st.executeQuery();

            while(LabWorkSet.next()){

                int lw_id=Integer.parseInt(LabWorkSet.getString("id"));
                int lw_userID=Integer.parseInt(LabWorkSet.getString("user_id"));
                String lw_userName=LabWorkSet.getString("username");
                double lw_price=Double.parseDouble(LabWorkSet.getString("price"));
                String lw_name=LabWorkSet.getString("name");
                String lw_creationDate=LabWorkSet.getString("creationDate");
                double lw_minimalPoint=Double.parseDouble(LabWorkSet.getString("minimalPoint"));
                String lw_description=LabWorkSet.getString("description");
                int lw_tunedInWorks=Integer.parseInt(LabWorkSet.getString("tunedInWorks"));
                Difficulty lw_difficulty=Difficulty.parse(LabWorkSet.getString("difficulty"));

                String cords_id=LabWorkSet.getString("coordinates");
                String cords_query="SELECT * FROM coordinates WHERE(id="+cords_id+")";
                PreparedStatement cords_st=connection.prepareStatement(cords_query);
                ResultSet coordinatesSet=cords_st.executeQuery();
                coordinatesSet.next();
                float cords_x=Float.parseFloat(coordinatesSet.getString("x"));
                int cords_y=Integer.parseInt(coordinatesSet.getString("y"));
                Coordinates coordinates=new Coordinates(cords_x, cords_y);

                String person_id=LabWorkSet.getString("author");
                String person_query="SELECT * FROM person WHERE(id="+person_id+")";
                PreparedStatement person_st=connection.prepareStatement(person_query);
                ResultSet personSet=person_st.executeQuery();
                personSet.next();
                String person_name=personSet.getString("name");
                String person_passportID=personSet.getString("passportId");
                Color color= Color.parse(personSet.getString("eyeColor"));

                String location_id=personSet.getString("location");
                String location_query="SELECT * FROM location WHERE(id="+location_id+")";
                PreparedStatement location_st=connection.prepareStatement(location_query);
                ResultSet locationSet=location_st.executeQuery();
                locationSet.next();
                float location_x=Float.parseFloat(locationSet.getString("x"));
                float location_y=Float.parseFloat(locationSet.getString("y"));
                String location_name=locationSet.getString("name");

                Location location=new Location(location_x, location_y, location_name);

                Person person=new Person(person_name, person_passportID, color ,location);


                LabWork labWork=new LabWork(lw_id, lw_userID,lw_userName,lw_price, lw_name, coordinates, lw_creationDate, lw_minimalPoint, lw_description, lw_tunedInWorks, lw_difficulty, person);
                labWorkArrayList.add(labWork);

            }
            connection.commit();
            connection.close();
        }catch(SQLException e){
            try {
                connection.rollback();
                connection.close();
            }catch (SQLException e1){}
            e.printStackTrace();
        }
        return labWorkArrayList;
    }
}
