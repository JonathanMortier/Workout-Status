package fr.djmojo.workout.database;

import com.heroku.sdk.jdbc.DatabaseUrl;
import feign.Param;
import fr.djmojo.workout.clients.MachineClient;
import fr.djmojo.workout.models.Machine;
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
public final class MachineDAO implements MachineClient{

    private static final MachineDAO INSTANCE = new MachineDAO();

    private final Logger logger = LoggerFactory.getLogger(MachineDAO.class);

    public static final String TABLE_NAME = "machine";
    public static final String ID = "id";
    public static final String NAME = "name";

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" (" +
            ID +" SERIAL PRIMARY KEY," +
            NAME+" varchar(250)," +
            "CONSTRAINT uniqueMachine UNIQUE("+NAME+"))";

    private static final String FIND_ALL_ORDER_BY = "SELECT * FROM "+TABLE_NAME+ " ORDER BY {}";

    private static final String FIND_BY_ID = "SELECT * FROM "+TABLE_NAME +
            " WHERE "+ID+" = {}";

    private MachineDAO() {}

    public static MachineDAO getInstance() { return INSTANCE;}


    @Override
    public List<Machine> findAll() {
        Connection connection = null;
        List<Machine> machineList = new ArrayList<>();
        try {
            connection = DatabaseUrl.extract().getConnection();

            ResultSet rs;
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(CREATE_TABLE);

                rs = stmt.executeQuery(FIND_ALL_ORDER_BY.replace("{}", NAME));

                while (rs.next()) {
                    machineList.add(getMachineFromResultSet(rs));
                }
            }

        } catch (Exception e) {
            logger.error("Exception lors du machineDao.findAll en bdd", e);
        } finally {
            if (connection != null) try{connection.close();} catch(SQLException e){
                logger.error("Exception lors de la cloture de la connexion", e);
            }
        }
        return machineList;
    }

    @Override
    public Machine findById(@Param("id") String id) {
        Connection connection = null;
        Machine machine = null;
        try {
            connection = DatabaseUrl.extract().getConnection();

            ResultSet rs;
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(CREATE_TABLE);

                rs = stmt.executeQuery(FIND_BY_ID.replace("{}", id));

                rs.next();
                machine = getMachineFromResultSet(rs);
            }

        } catch (Exception e) {
            logger.error(String.format("Exception lors du userdto.findById(%s) en bdd", id),  e);
        } finally {
            if (connection != null) try{connection.close();} catch(SQLException e){
                logger.error("Exception lors de la cloture de la connexion", e);
            }
        }
        return machine;
    }

    @Override
    public Machine createMachine(Machine machine) {
        Machine machineCreated = null;
        Connection connection = null;
        try {
            connection = DatabaseUrl.extract().getConnection();

            ResultSet rs;
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(CREATE_TABLE);

                rs = stmt.executeQuery("INSERT INTO " + TABLE_NAME +
                        " (" + NAME + ") " +
                        "VALUES ('" + machine.getName() + "') RETURNING " + ID);

                rs.next();
                String id = rs.getString(ID);

                rs = stmt.executeQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + ID + " ='" + id + "'");

                rs.next();
                machineCreated = getMachineFromResultSet(rs);
            }

        } catch (Exception e) {
            logger.error("Exception lors du machineDao.createMachine en bdd", e);

        } finally {
            if (connection != null) try{connection.close();} catch(SQLException e){
                logger.error("Exception lors de la cloture de la connexion", e);
            }
        }
        return machineCreated;
    }

    private Machine getMachineFromResultSet(ResultSet rs) throws SQLException {
        Machine machine = new Machine();
        machine.setId(rs.getString(ID));
        machine.setName(rs.getString(NAME));

        return machine;
    }

}
