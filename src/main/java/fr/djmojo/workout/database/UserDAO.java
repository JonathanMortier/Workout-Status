package fr.djmojo.workout.database;

import com.heroku.sdk.jdbc.DatabaseUrl;
import fr.djmojo.workout.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DJMojo on 15/05/16.
 */
public final class UserDAO {

    private static final UserDAO INSTANCE = new UserDAO();

    private final Logger logger = LoggerFactory.getLogger(UserDAO.class);

    public static final String TABLE_NAME  = "user_";
    public static final String ID          = "id";
    private static final String LASTNAME    = "lastname";
    private static final String FIRSTNAME   = "firstname";
    private static final String MAIL        = "mail";
    private static final String PASSWORD    = "password";

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" (" +
            ID+" SERIAL PRIMARY KEY," +
            LASTNAME+" varchar(250)," +
            FIRSTNAME+" varchar(250)," +
            MAIL+" varchar(250)," +
            PASSWORD+" varchar(250)," +
            "CONSTRAINT uniqueMail UNIQUE("+MAIL+"))";

    private static final String FIND_BY_ID = "SELECT * FROM "+TABLE_NAME +
            " WHERE "+ID+" = {}";

    private UserDAO() {
    }

    public static UserDAO getInstance() {
        return INSTANCE;
    }

    public List<User> findAll() {
        Connection connection = null;
        List<User> userList = new ArrayList<>();
        try {
            connection = DatabaseUrl.extract().getConnection();

            Statement stmt = connection.createStatement();
            stmt.executeUpdate(CREATE_TABLE);

            ResultSet rs = stmt.executeQuery("SELECT * FROM "+TABLE_NAME);

            while (rs.next()) {
                userList.add(getUserFromResultSet(rs));
            }


        } catch (Exception e) {
            logger.error("Exception lors du userdto.findAll en bdd", e);
        } finally {
            if (connection != null) try{connection.close();} catch(SQLException e){}
        }
        return userList;
    }

    public User findById(String id) {

        Connection connection = null;
        User user = null;
        try {
            connection = DatabaseUrl.extract().getConnection();

            Statement stmt = connection.createStatement();
            stmt.executeUpdate(CREATE_TABLE);

            ResultSet rs = stmt.executeQuery(FIND_BY_ID.replace("{}", id));

            rs.next();
            user = getUserFromResultSet(rs);

        } catch (Exception e) {
            logger.error("Exception lors du userdto.findById("+id+") en bdd", e);
        } finally {
            if (connection != null) try{connection.close();} catch(SQLException e){}
        }
        return user;


    }

    public User create(User user) {

        User userCreated = null;
        Connection connection = null;
        try {
            connection = DatabaseUrl.extract().getConnection();

            Statement stmt = connection.createStatement();
            stmt.executeUpdate(CREATE_TABLE);

            ResultSet rs = stmt.executeQuery("INSERT INTO " + TABLE_NAME + " " +
                    "(" + LASTNAME + ", " + FIRSTNAME + ", " + MAIL + ", " + PASSWORD + ") " +
                    "VALUES ('" + user.getLastname() + "', '" + user.getFirstname()
                    + "', '" + user.getMail() + "', '" + user.getPassword() + "') RETURNING "+ID);

            rs.next();
            String id = rs.getString(ID);

            rs = stmt.executeQuery("SELECT * FROM "+TABLE_NAME+" WHERE " + ID + " ='" + id + "'");

            rs.next();
            userCreated = getUserFromResultSet(rs);

        } catch (Exception e) {
            logger.error("Exception lors du userdto.findAll en bdd", e);

        } finally {
            if (connection != null) try{connection.close();} catch(SQLException e){}
        }
        return userCreated;
    }

    private User getUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getString(ID));
        user.setLastname(rs.getString(LASTNAME));
        user.setFirstname(rs.getString(FIRSTNAME));
        user.setMail(rs.getString(MAIL));
        user.setPassword(rs.getString(PASSWORD));
        return user;
    }
}
