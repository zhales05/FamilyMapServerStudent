package Services;

import Services.jsonhandler.InfoGeneration;
import dao.AuthTokenDao;
import dao.DataAccessException;
import dao.Database;
import dao.UserDao;
import model.AuthToken;
import model.User;

import java.sql.Connection;

/**
 * handles login requests
 */
public class Login {
    InfoGeneration ig = new InfoGeneration();
    Database db;
    Connection conn;
     /**
     * constructor creates a connection through database, might need to add deconstructor that ends connection.
     */
    public Login() {
        db = new Database();
        try {
            conn = db.getConnection();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param r
     * @return
     */
    public LoginResult login(LoginRequest r) {
        User activeUser;
        LoginResult result = new LoginResult("error");
        UserDao uDao = new UserDao(conn);
        AuthTokenDao aDao = new AuthTokenDao(conn);
        try {
            activeUser = uDao.find(r.getUsername());
            if (activeUser == null) {
                result = new LoginResult("error no user with this username");
                db.closeConnection(false);
                return result;
            }
            if (!activeUser.getPassword().equals(r.getPassword())) {
                result = new LoginResult("error wrong password");
                db.closeConnection(false);
                return result;
            }
            AuthToken token = new AuthToken(activeUser.getPersonID(),"rr"+ ig.assignRandomID() );
            aDao.insert(token);

             result = new LoginResult(token.getAuthToken(), r.getUsername(), activeUser.getPersonID());
             db.closeConnection(true);
            return result;

        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        try {
            db.closeConnection(false);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return result;
    }
}
