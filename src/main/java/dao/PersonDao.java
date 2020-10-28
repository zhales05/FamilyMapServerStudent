package dao;

import model.AuthToken;
import model.Person;
import model.User;

import java.sql.*;

/**
 * in between for database and Person class
 */
public class PersonDao {

    private final Connection conn;

    public PersonDao(Connection conn)
    {
        this.conn = conn;
    }

    /**
     * insert into database
     * @param toInsert
     *
     */
    public void insert(Person toInsert) throws DataAccessException {
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO Persons (PersonID, AssociatedUsername, firstName, lastName, gender, " +
                "fatherID, motherID, spouseID) VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, toInsert.getPersonID());
            stmt.setString(2, toInsert.getUsername());
            stmt.setString(3, toInsert.getFirstName());
            stmt.setString(4, toInsert.getLastName());
            stmt.setString(5, toInsert.getGender());
            stmt.setString(6, toInsert.getFatherID());
            stmt.setString(7, toInsert.getMotherID());
            stmt.setString(8, toInsert.getSpouseID());


            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    /**
     * find a person based on ID.
     * @param personID
     * @return
     * @throws DataAccessException
     */
    public Person find(String personID) throws DataAccessException {
        Person person;
        ResultSet rs = null;
        String sql = "SELECT * FROM Persons WHERE personID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new Person(rs.getString("personID"), rs.getString("associatedUsername"),
                        rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("gender"), rs.getString("fatherID"), rs.getString("motherID"),
                        rs.getString("spouseID"));
                return person;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding person");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    /**
     * clears the entire person table
     */
    public void clear() throws DataAccessException{
       try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM Persons";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing Persons");
        }
    }
    /**
     * delete person object from database
     * @param personID
     * @return if operation was successful
     */
    public void delete(String personID) throws DataAccessException {
        String sql = "DELETE FROM Persons WHERE PersonID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while deleting person");
        }
    }
}
