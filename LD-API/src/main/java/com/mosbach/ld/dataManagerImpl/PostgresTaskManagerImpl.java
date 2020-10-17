package com.mosbach.ld.dataManagerImpl;

import com.mosbach.ld.dataManager.TaskManager;
import com.mosbach.ld.model.task.Task;
import org.apache.commons.dbcp.BasicDataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class PostgresTaskManagerImpl implements TaskManager  {

    String databaseURL = "jdbc:postgres://eujrltajivvwzr:d2b486de8e4091ae14d53fa56fc6cbc906e7a2180cc02935f8f91005655b6e0a@ec2-34-254-24-116.eu-west-1.compute.amazonaws.com:5432/d51u5rqpl59pv";
    String username = "eujrltajivvwzr";
    String password = "d2b486de8e4091ae14d53fa56fc6cbc906e7a2180cc02935f8f91005655b6e0a";
    BasicDataSource basicDataSource;

    static PostgresTaskManagerImpl postgresTaskManager = null;

    private PostgresTaskManagerImpl() {
        basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(databaseURL);
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);
    }

    static public PostgresTaskManagerImpl getPostgresTaskManagerImpl() {
        if (postgresTaskManager == null)
            postgresTaskManager = new PostgresTaskManagerImpl();
        return postgresTaskManager;
    }


    @Override
    public Collection<Task> getAllTasks() {

        List<Task> tasks = new ArrayList<>();
        Properties properties = new Properties();
        Statement stmt = null;

        try {
            stmt = basicDataSource.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM tasks");
            while (rs.next()) {
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    @Override
    public void addTask(Task task) {

        List<Task> tasks = new ArrayList<>();
        Properties properties = new Properties();
        Statement stmt = null;

        try {
            stmt = basicDataSource.getConnection().createStatement();
            String udapteSQL = "INSERT into tasks (name, description, priority) VALUES (" +
                    "'" + task.getName() + "', " +
                    "'" + task.getDescription() + "', " +
                    "'" + task.getPriority() + "')";

            stmt.executeUpdate(udapteSQL);
            } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void createTableTask() {

        // Be carefull: It deletes data if table already exists.
        //
        Statement stmt;

        try {
            stmt = basicDataSource.getConnection().createStatement();

            String dropTable = "DROP TABLE tasks";

            String createTable = "CREATE TABLE tasks (" +
                    "id SERIAL PRIMARY KEY, " +
                    "name varchar(100) NOT NULL, " +
                    "description varchar(250) NOT NULL, " +
                    "priority int NOT NULL)";

            stmt.executeUpdate(dropTable);

            stmt.executeUpdate(createTable);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
