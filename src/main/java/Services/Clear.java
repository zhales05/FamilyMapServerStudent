package Services;

import Services.familycreation.EventGenerator;
import dao.DataAccessException;
import dao.Database;

import java.sql.Connection;

/**
 * clears databases
 */

public class Clear {
    Database db;
    Connection conn;
    /**
     * constructor creates a connection through database, might need to add deconstructor that ends connection.
     */
    public Clear() {
        db = new Database();
        try {
            conn = db.getConnection();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }


    /**
     * wipe database
     * @return whether operation was successful
     */
    public ClearResult clearData() {
        try {
            db.clearTables();
            ClearResult cr = new ClearResult(true, "Clear succeeded.");
            db.closeConnection(true);
            return cr;
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        ClearResult cr = new ClearResult(false, "database error");
        try {
            db.closeConnection(false);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return cr;
    };
}
