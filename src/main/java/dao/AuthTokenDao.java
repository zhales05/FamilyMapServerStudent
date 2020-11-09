package dao;

import model.AuthToken;
import model.User;

import java.sql.*;

/**
 * in between for database and AuthToken class
 */
public class AuthTokenDao {

    private final Connection conn;

    public AuthTokenDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * insert AuthToken into database
     *
     * @param toInsert
     */
    public void insert(AuthToken toInsert) throws DataAccessException {
        String sql = "INSERT INTO AuthTokens (AuthTokenID, PersonID) VALUES(?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, toInsert.getAuthToken());
            stmt.setString(2, toInsert.getPersonID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    /**
     * Used to grab array of tokens associated with the user
     *
     * @param token
     * @return array of all the AuthTokens that are active for that user
     */
    public AuthToken find(String token) throws DataAccessException {
        AuthToken aToken;
        ResultSet rs = null;
        String sql = "SELECT * FROM AuthTokens WHERE AuthTokenID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, token);
            rs = stmt.executeQuery();
            if (rs.next()) {
                aToken = new AuthToken(rs.getString("PersonID"), rs.getString("AuthTokenID"));
                return aToken;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding AuthToken");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;

    }

}
