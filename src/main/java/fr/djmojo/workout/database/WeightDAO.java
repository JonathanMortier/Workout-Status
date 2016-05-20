package fr.djmojo.workout.database;

import com.heroku.sdk.jdbc.DatabaseUrl;
import feign.Param;
import fr.djmojo.workout.clients.WeightClient;
import fr.djmojo.workout.models.Machine;
import fr.djmojo.workout.models.Weight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe de gestion bdd de la table Weight
 * Created by DJMojo on 15/05/16.
 */
public class WeightDAO implements WeightClient {

    private static final WeightDAO INSTANCE = new WeightDAO();

    private final Logger logger = LoggerFactory.getLogger(WeightDAO.class);

    private static final String TABLE_NAME = "weight";
    private static final String USER_ID = "userId";
    private static final String MACHINE_ID = "machineId";
    private static final String WEIGHT = "weight";

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" (" +
            USER_ID +" SERIAL REFERENCES "+UserDAO.TABLE_NAME+" ("+UserDAO.ID+")," +
            MACHINE_ID+" SERIAL REFERENCES "+MachineDAO.TABLE_NAME+" ("+MachineDAO.ID+")," +
            WEIGHT+ " INTEGER, " +
            "PRIMARY KEY ("+USER_ID+", "+MACHINE_ID+"))";

    private static final String FIND_BY_USER_ID = "SELECT "+TABLE_NAME+".* FROM "+TABLE_NAME + ", " + MachineDAO.TABLE_NAME +
            " WHERE "+TABLE_NAME+"."+USER_ID +" = {0} AND "+TABLE_NAME+"."+MACHINE_ID + " = "+ MachineDAO.TABLE_NAME+"."+MachineDAO.ID+
            " ORDER BY "+ MachineDAO.TABLE_NAME+"."+MachineDAO.NAME;

    private static final String EXIST_BY_USER_ID_MACHINE_ID = "SELECT count(*) as rowCount FROM "+TABLE_NAME +
            " WHERE "+USER_ID +" = {0} AND "+MACHINE_ID+" = {1}";

    private static final String FIND_BY_USER_ID_MACHINE_ID = "SELECT * FROM "+TABLE_NAME +
            " WHERE "+USER_ID +" = {0} AND "+MACHINE_ID+" = {1}";

    private static final String UPDATE_WEIGHT_BY_USER_ID_MACHINE_ID = "UPDATE " + TABLE_NAME + " SET " + WEIGHT + " = "+WEIGHT+" + {2} " +
            " WHERE " + USER_ID + " = {0} AND " + MACHINE_ID + " = {1} RETURNING *";

    private WeightDAO() {}

    public static WeightDAO getInstance() { return INSTANCE;}

    @Override
    public List<Weight> findByUserId(@Param("id") String userId) {

        Connection connection = null;
        List<Weight> weightList = new ArrayList<>();
        try {
            connection = DatabaseUrl.extract().getConnection();

            Statement stmt = connection.createStatement();
            stmt.executeUpdate(CREATE_TABLE);

            String query = MessageFormat.format(FIND_BY_USER_ID, userId);
            logger.info("Query : "+query);
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                weightList.add(getWeightFromResultSet(rs));
            }

        } catch (Exception e) {
            logger.error("Exception lors du weightDao.findByUserId en bdd", e);
        } finally {
            if (connection != null) try{connection.close();} catch(SQLException e){}
        }

        return weightList;
    }

    @Override
    public Weight createWeight(Weight weight) {

        Weight weightCreated = null;
        Connection connection = null;
        try {
            connection = DatabaseUrl.extract().getConnection();

            Statement stmt = connection.createStatement();
            stmt.executeUpdate(CREATE_TABLE);

            //Détecter si le couple weight/machine existe déjà ou non.
            ResultSet rs;
            if (!weightMachineExist(weight)) {
                //Si il n'existe pas, elle est passée à côtééé de moii
                rs = stmt.executeQuery("INSERT INTO " + TABLE_NAME +
                        " (" + USER_ID + ", " + MACHINE_ID + ", " + WEIGHT + ") " +
                        "VALUES ('" + weight.getUserId() + "', " + weight.getMachineId() + ", " +
                        "" + weight.getWeight() + ") RETURNING *");
            }
            else {
                //Il existe, on fait un update
                rs = stmt.executeQuery("UPDATE " + TABLE_NAME + " SET " + WEIGHT + " = " + weight.getWeight() +
                        " WHERE " + USER_ID + " = " + weight.getUserId() + " AND " + MACHINE_ID + " = " + weight.getMachineId() + "RETURNING *");
            }

            rs.next();

            weightCreated = getWeightFromResultSet(rs);

        } catch (Exception e) {
            logger.error("Exception lors du weightDao.createWeight en bdd", e);

        } finally {
            if (connection != null) try{connection.close();} catch(SQLException e){}
        }
        return weightCreated;


    }

    @Override
    public Weight addWeight(Weight weight, @Param("mass") int mass) {

        Weight updatedWeight = null;

        if ( weightMachineExist(weight)) {

            Connection connection = null;
            try {
                connection = DatabaseUrl.extract().getConnection();

                Statement stmt = connection.createStatement();
                stmt.executeUpdate(CREATE_TABLE);

                String query = MessageFormat.format(UPDATE_WEIGHT_BY_USER_ID_MACHINE_ID, weight.getUserId(), weight.getMachineId(), mass);

                logger.warn("Query : " + query);

                ResultSet rs = stmt.executeQuery(query);

                rs.next();
                updatedWeight = getWeightFromResultSet(rs);


            } catch (Exception e) {
                logger.error("Exception lors du weightDao.findByUserId en bdd", e);
            } finally {
                if (connection != null) {

                    try{
                        connection.close();
                    }
                    catch(SQLException e){
                        logger.error("SQLException : ", e);
                    }
                }
            }
        }
        else {
            // Can't update a weight that doesn't exist
            //TODO throws 404
        }
        return updatedWeight;
    }

    private boolean weightMachineExist(Weight weight) {

        Connection connection = null;

        try {
            connection = DatabaseUrl.extract().getConnection();

            Statement stmt = connection.createStatement();
            stmt.executeUpdate(CREATE_TABLE);

            String query = MessageFormat.format(EXIST_BY_USER_ID_MACHINE_ID, weight.getUserId(), weight.getMachineId());

            logger.warn("Query : " + query);

            ResultSet rs = stmt.executeQuery(query);

            rs.next();

            int count = rs.getInt("rowCount");
            logger.info("Count(*) = "+ count);
            if (count == 1) {
                return true;
            }

        } catch (Exception e) {
            logger.error("Exception lors du weightDao.findByUserId en bdd", e);
        } finally {
            if (connection != null)
                try{
                    connection.close();
                }
                catch(SQLException e){
                    logger.error("SQLException : ", e);
                }
        }

        return false;


    }

    private Weight getWeightFromResultSet(ResultSet rs) throws SQLException {

        Weight weight = new Weight();
        weight.setMachineId(rs.getString(MACHINE_ID));
        weight.setUserId(rs.getString(USER_ID));
        weight.setWeight(rs.getInt(WEIGHT));

        return weight;
    }
}
