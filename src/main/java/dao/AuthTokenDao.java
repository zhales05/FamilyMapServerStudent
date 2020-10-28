package dao;

import model.AuthToken;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * in between for database and AuthToken class
 */
public class AuthTokenDao {

    private final Connection conn;

    public AuthTokenDao(Connection conn)
    {
        this.conn = conn;
    }
    /**
     * insert AuthToken into database
     * @param toInsert
     */
    public void insert(AuthToken toInsert) throws DataAccessException {
        String sql = "INSERT INTO AuthTokens (AuthTokenID, PersonID) VALUES(?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, toInsert.getAuthToken());
            stmt.setString(2, toInsert.getPersonID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    /**
     * delete authtoken from database
     * @param authToken
     */
    public void delete(AuthToken authToken) throws DataAccessException {
        String sql = "DELETE FROM AuthTokens WHERE AuthTokenID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authToken.getAuthToken());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while deleting person");
        }
    }

    /**
     * Used to grab array of tokens associated with the user
     * @param personID
     * @return array of all the AuthTokens that are active for that user
     */
    public AuthToken[] find(String personID) {
        return null;
    }

    /**
     * clears the AuthToken table
     * @throws DataAccessException
     */
    public void clear() throws DataAccessException{
        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM AuthTokens";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing AuthTokens");
        }
    }
}
