package org.lab6.mainClasses;

import org.lab6.Main;
import org.lab6.storedClasses.LabWork;

import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LabWorkDAO {
    public static synchronized void addLabWork(LabWork lw) throws SQLException{
        int locationId=0;
        int personId=0;
        int coordinatesId=0;
        int labWorkId=0;
        String location_query="INSERT INTO location(x, y, name) VALUES "+lw.getAuthor().getLocation().getFieldsAsTuple();
        String person_query="INSERT INTO person(name, passportID, eyeColor, location) VALUES "+lw.getAuthor().getFieldsAsTuple();
        String coordinates_query="INSERT INTO coordinates(x, y) VALUES"+lw.getCoordinates().getFieldsAsTuple();
        String labWork_query="INSERT INTO labWork(user_id,username,price, name, coordinates, minimalPoint, description, tunedInWorks, difficulty, author) VALUES"+lw.getFieldsAsTuple();

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

    public static synchronized void clear(int userID) throws SQLException{

        int personID=0;
        int locationID=0;
        int coordinatesID=0;

        String query="SELECT id FROM labwork WHERE(user_id="+userID+")";
        PreparedStatement s = Main.getConnection().prepareStatement(query);
        ResultSet lw_IDSet = s.executeQuery();
        while(lw_IDSet.next()){
            try{
                remove(lw_IDSet.getInt(1), userID);
            }catch (IllegalUserAccessException e){}
        }

    }

    public static synchronized void remove(int lwID, int userID) throws SQLException, IllegalUserAccessException{
        int user_id=0;
        int coordinates_id=0;
        int person_id=0;
        int location_id=0;

        String lw_query="SELECT user_id, author, coordinates FROM labwork WHERE(id="+lwID+")";
        PreparedStatement s = Main.getConnection().prepareStatement(lw_query);
        ResultSet lw_KeysSet = s.executeQuery();
        lw_KeysSet.next();
        if(userID!=Integer.parseInt(lw_KeysSet.getString("user_id")))throw new IllegalUserAccessException("you have no access to this LabWork instance with index "+lwID+" it have user with id "+user_id);
        coordinates_id=Integer.parseInt(lw_KeysSet.getString("coordinates"));
        person_id=Integer.parseInt(lw_KeysSet.getString("author"));

        String person_query="SELECT location FROM person WHERE(id="+person_id+")";
        PreparedStatement person_s = Main.getConnection().prepareStatement(person_query);
        ResultSet personKeysSet = person_s.executeQuery();
        personKeysSet.next();
        location_id=Integer.parseInt(personKeysSet.getString("location"));

        String lw_delete_query="DELETE FROM labwork WHERE(id="+lwID+")";
        String coordinates_delete_query="DELETE FROM coordinates WHERE(id="+coordinates_id+")";
        String person_delete_query="DELETE FROM person WHERE(id="+person_id+")";
        String location_delete_query="DELETE FROM location WHERE(id="+location_id+")";

        Statement delete_st = Main.getConnection().createStatement();
        delete_st.execute(lw_delete_query);
        delete_st.execute(coordinates_delete_query);
        delete_st.execute(person_delete_query);
        delete_st.execute(location_delete_query);
    }

    public static synchronized void update(int lwID, int userID, LabWork labWork) throws SQLException{
        int user_id=0;
        int coordinates_id=0;
        int person_id=0;
        int location_id=0;

        String lw_query="SELECT user_id, author, coordinates FROM labwork WHERE(id="+lwID+")";
        PreparedStatement s = Main.getConnection().prepareStatement(lw_query);
        ResultSet lw_KeysSet = s.executeQuery();
        lw_KeysSet.next();
        if(userID!=Integer.parseInt(lw_KeysSet.getString("user_id")))throw new IllegalUserAccessException(
                "you have no access to this LabWork instance with index "+lwID+" it have user with id "+user_id);
        coordinates_id=Integer.parseInt(lw_KeysSet.getString("coordinates"));
        person_id=Integer.parseInt(lw_KeysSet.getString("author"));

        String person_query="SELECT location FROM person WHERE(id="+person_id+")";
        PreparedStatement person_s = Main.getConnection().prepareStatement(person_query);
        ResultSet personKeysSet = person_s.executeQuery();
        personKeysSet.next();
        location_id=Integer.parseInt(personKeysSet.getString("location"));

        String location_update_query="UPDATE location SET x="+labWork.getAuthor().getLocation().getX()+", y="+labWork.getAuthor().getLocation().getY()+
                ", name='"+labWork.getAuthor().getLocation().getName()+"' WHERE(id="+location_id+")";
        String coordinates_update_query="UPDATE coordinates SET x="+labWork.getCoordinates().getX()+", y="
                +labWork.getCoordinates().getY()+" WHERE(id="+coordinates_id+")";
        String  person_update_query="UPDATE person SET name='"+labWork.getAuthor().getName()+"', passportID='"+
                labWork.getAuthor().getPassportId()+"', eyeColor='"+labWork.getAuthor().geteyeColor()+"' WHERE(id="+person_id+")";
        String labwork_update_query="UPDATE labwork SET price="+labWork.getPrice()+", name='"+labWork.getName()+"', minimalPoint="+labWork.getMinimalPoint()+", description='"+
                labWork.getDescription()+"', tunedInWorks="+labWork.getTunedInWorks()+", difficulty='"+labWork.getDifficulty()+"' WHERE(id="+lwID+")";
        Statement delete_st = Main.getConnection().createStatement();
        delete_st.execute(location_update_query);
        delete_st.execute(coordinates_update_query);
        delete_st.execute(person_update_query);
        delete_st.execute(labwork_update_query);
    }
    public static synchronized double getMoneyCount(int userID) throws SQLException{
        String wallet_query="SELECT wallet FROM users WHERE(id=0"+userID+")";
        PreparedStatement s = Main.getConnection().prepareStatement(wallet_query);
        ResultSet wallet_KeysSet = s.executeQuery();
        wallet_KeysSet.next();
        double currentWallet=Double.parseDouble(wallet_KeysSet.getString("wallet"));
        return currentWallet;
    }
    public static synchronized void insertMoney(int userID, double moneyAmount) throws SQLException{
        double currentWallet=getMoneyCount(userID);
        currentWallet+=moneyAmount;


        String update_query="UPDATE users SET wallet="+currentWallet+" WHERE(id="+userID+")";
        Statement update_st = Main.getConnection().createStatement();
        update_st.execute(update_query);
    }
}
