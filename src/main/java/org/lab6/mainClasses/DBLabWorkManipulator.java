package org.lab6.mainClasses;

import org.lab6.Main;
import org.lab6.storedClasses.LabWork;

import java.beans.PropertyEditorSupport;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBLabWorkManipulator {
    public static void addLabWork(LabWork lw) throws SQLException{
        int locationId=0;
        int personId=0;
        int coordinatesId=0;
        int labWorkId=0;
        String location_query="INSERT INTO location(x, y, name) VALUES "+lw.getAuthor().getLocation().getFieldsAsTuple();
        String person_query="INSERT INTO person(name, passportID, eyeColor, location) VALUES "+lw.getAuthor().getFieldsAsTuple();
        String coordinates_query="INSERT INTO coordinates(x, y) VALUES"+lw.getCoordinates().getFieldsAsTuple();
        String labWork_query="INSERT INTO labWork(user_id, name, coordinates, minimalPoint, description, tunedInWorks, difficulty, author) VALUES"+lw.getFieldsAsTuple();

        Statement location_st= Main.getConnection().createStatement();
        location_st.execute(location_query, Statement.RETURN_GENERATED_KEYS);
        ResultSet locationKeys = location_st.getGeneratedKeys();
        locationKeys.next();
        locationId = locationKeys.getInt(1);

        Statement person_st=Main.getConnection().createStatement();
        person_st.execute(person_query.replaceAll("%1", locationId+""), Statement.RETURN_GENERATED_KEYS);
        ResultSet personKeys=person_st.getGeneratedKeys();
        personKeys.next();
        personId=personKeys.getInt(1);

        Statement coordinates_st=Main.getConnection().createStatement();
        coordinates_st.execute(coordinates_query, Statement.RETURN_GENERATED_KEYS);
        ResultSet coordinatesKeys=coordinates_st.getGeneratedKeys();
        coordinatesKeys.next();
        coordinatesId=coordinatesKeys.getInt(1);

        Statement labWork_st=Main.getConnection().createStatement();
        labWork_st.execute(labWork_query.replaceAll("%1", coordinatesId+"").replaceAll("%2", personId+""), Statement.RETURN_GENERATED_KEYS);
        ResultSet labWorkKeys=labWork_st.getGeneratedKeys();
        labWorkKeys.next();
        labWorkId=labWorkKeys.getInt(1);
        lw.setID(labWorkId);

        String query = "SELECT creationDate FROM LabWork WHERE(id="+labWorkId+")";
        PreparedStatement s = Main.getConnection().prepareStatement(query);
        ResultSet creationDateSet = s.executeQuery();
        creationDateSet.next();
        lw.setCreationDate(creationDateSet.getString(1));
    }

    public static void clear(int userID) throws SQLException{
        System.out.println("trying to delete");

        int personID=0;
        int locationID=0;
        int coordinatesID=0;

        String query="SELECT coordinates,author FROM labwork WHERE(user_id="+userID+")";
        PreparedStatement s = Main.getConnection().prepareStatement(query);
        ResultSet lw_KeysSet = s.executeQuery();

        String lw_delete_query = "DELETE FROM labwork WHERE(user_id=" + userID + ")";
        Statement lw_st = Main.getConnection().createStatement();
        lw_st.execute(lw_delete_query);

        System.out.println(userID);
        while(lw_KeysSet.next()) {
            System.out.println("getting ids");
            personID = Integer.parseInt(lw_KeysSet.getString("author"));
            coordinatesID = Integer.parseInt(lw_KeysSet.getString("coordinates"));
            System.out.println("crds "+coordinatesID);
            System.out.println("prs "+personID);

            query = "SELECT location FROM person WHERE(id=" + personID + ")";
            PreparedStatement person_s = Main.getConnection().prepareStatement(query);
            ResultSet person_KeysSet = person_s.executeQuery();
            person_KeysSet.next();
            locationID = Integer.parseInt(person_KeysSet.getString("location"));


            System.out.println("loc "+locationID);
            System.out.println("user "+userID);
            String pers_delete_query = "DELETE FROM person WHERE(id=" + personID + ")";
            String coordinates_delete_query = "DELETE FROM coordinates WHERE(id=" + coordinatesID + ")";
            String location_delete_query = "DELETE FROM location WHERE(id=" + locationID + ")";
            Statement person_st = Main.getConnection().createStatement();
            person_st.execute(pers_delete_query);
            person_st.execute(coordinates_delete_query);
            person_st.execute(location_delete_query);
        }
    }
}
