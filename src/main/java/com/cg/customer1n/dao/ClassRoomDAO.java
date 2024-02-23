package com.cg.customer1n.dao;

import com.cg.customer1n.model.ClassRoom;
import com.cg.customer1n.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClassRoomDAO implements IClassRoomDAO {
    private static final String INSERT_CLASSROOM = "INSERT INTO classroom (name) VALUES (?)";
    private static final String UPDATE_CLASSROOM_SQL = "UPDATE classroom SET name = ? WHERE id = ?";
    private String jdbcURL = "jdbc:mysql://localhost:3306/student?allowPublicKeyRetrieval=true&useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Raisingthebar123!!/";
    private String SELECT_CLASSROOM_BY_ID = "SELECT * FROM classrooms WHERE id = ?";
    private String SELECT_CLASSROOM = "SELECT * FROM classrooms";

    @Override
    public List<ClassRoom> selectClassRoom() {
        List<ClassRoom> classRoom = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CLASSROOM);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                int classroom_id = rs.getInt("id");
                String classroom_name = rs.getString("classname");
                ClassRoom classRoom1 = new ClassRoom(classroom_id, classroom_name);
                classRoom.add(classRoom1);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }

        return classRoom;
    }

//    @Override
//    public ClassRoom findClassRooomById(int id) {
//        ClassRoom classRoom = null;
//        try (Connection connection = getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PROVINCE_BY_ID)) {
//            preparedStatement.setInt(1, id);
//            ResultSet rs = preparedStatement.executeQuery();
//
//            while (rs.next()) {
//                int classroom_id = rs.getInt("id");
//                String classroom_name = rs.getString("name");
//                classRoom = new ClassRoom(classroom_id, classroom_name);
//            }
//        } catch (SQLException e) {
//            printSQLException(e);
//        }
//        return classRoom;
//    }

    @Override
    public ClassRoom findClassRoomById(int id) {
        ClassRoom classRoom = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CLASSROOM_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int classroom_id = rs.getInt("id");
                String classroom_name = rs.getString("classname");
                classRoom = new ClassRoom(classroom_id, classroom_name);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return classRoom;
    }

    @Override
    public void insertClassRoom(ClassRoom classRoom) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CLASSROOM)) {
            preparedStatement.setString(1, classRoom.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    @Override
    public void updateClassRoom(ClassRoom ClassRoom) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_CLASSROOM_SQL)) {
            statement.setString(1, ClassRoom.getName());
            statement.setInt(2, ClassRoom.getId());
            statement.executeUpdate();
        } catch (SQLException sqlException) {
            printSQLException(sqlException);
        }
    }

    @Override
    public void deleteClassRoom(int id) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM province WHERE id = ?")) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException sqlException) {
            printSQLException(sqlException);
        }
    }



    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}