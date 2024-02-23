package com.cg.customer1n.dao;

import com.cg.customer1n.model.Student;
import com.cg.customer1n.model.ClassRoom;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO implements IStudentDAO {
    private static final String SEARCHBYKW = "SELECT * FROM student c join classRoom p  on c.classRoom_id = p.id where c.name like (?) or p.name like (?) ";
    private String jdbcURL = "jdbc:mysql://localhost:3306/student?allowPublicKeyRetrieval=true&useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Raisingthebar123!!/";

    private String SELECT_STUDENT = "SELECT c.*, p.classname as classRoom_name \n" +
            "FROM students c join classrooms p on c.classromm_id = p.id;";
    private String INSERT_STUDENT = "INSERT INTO `students` (`name`, `address`, `email`, `phone`, `classromm_id`) \n" +
            "VALUES (?, ?, ?, ?, ?);";
    private String DELETE_STUDENT_SQL ="delete from students where id = ?;";
    private String UPDATE_STUDENT_SQL = "update students set name = ?,address= ?, email =?, phone =?, classromm_id =? where id = ?;";
    private String SELECT_STUDENT_BY_ID = "select c.*, p.classname as classRoom_name from students c join classrooms p on c.classromm_id = p.id  where c.id =?";




    @Override
    public void insertStudent(Student student) {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STUDENT);
            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getAddress());
            preparedStatement.setString(3, student.getEmail());
            preparedStatement.setString(4, student.getPhone());
            preparedStatement.setInt(5, student.getClassRoom().getId());

            System.out.println("insertStudent: " + preparedStatement);
            preparedStatement.execute();

        } catch (SQLException e) {
            printSQLException(e);
        }

    }

    @Override
    public Student selectStudent(int id) {
        Student student = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STUDENT_BY_ID);) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String adress = rs.getString("address");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                int classRoom_id = rs.getInt("classromm_id");
                String classRoom_name = rs.getString("classRoom_name");
                student = new Student(id,name,adress,email,phone,new ClassRoom(classRoom_id,classRoom_name));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return student;
    }

    @Override
    public List<Student> selectAllStudent() {
        List<Student> student = new ArrayList<>();
        try {
            Connection connection = getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STUDENT);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                int classRoom_id = rs.getInt("classromm_id");
                String classRoom_name = rs.getString("classRoom_name");

                Student student1 = new Student(id, name, address, email, phone,
                        new ClassRoom(classRoom_id, classRoom_name));
                student.add(student1);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }

        return student;
    }

    @Override
    public boolean deleteStudent(int id) {
            boolean rowDeleted = false;
            try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement( DELETE_STUDENT_SQL);) {
                statement.setInt(1, id);
                rowDeleted = statement.executeUpdate() > 0;
            } catch (SQLException sqlException) {
                printSQLException(sqlException);
            }
            return rowDeleted;
    }

    @Override
    public boolean updateStudent(Student student) {
        boolean rowUpdated = false;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE_STUDENT_SQL);) {
            statement.setString(1, student.getName());
            statement.setString(2, student.getAddress());
            statement.setString(3, student.getEmail());
            statement.setString(4, student.getPhone());
            statement.setInt(5, student.getClassRoom().getId());
            statement.setInt(6, student.getId());
            rowUpdated = statement.executeUpdate() > 0;

            System.out.println("updateStudent: " +  statement);
        } catch (SQLException sqlException) {
            printSQLException(sqlException);
        }
        return rowUpdated;
    }

    @Override
    public List<Student> searchByKw(String kw) {
        List<Student> student = new ArrayList<>();
        try {
            Connection connection = getConnection();

            String searchQuery = "SELECT c.*, p.name AS classRoom_name " +
                    "FROM customer c JOIN classRoom p ON c.classRoom_id = p.id " +
                    "WHERE c.name LIKE ? OR p.name LIKE ?";

            PreparedStatement preparedStatement = connection.prepareStatement(searchQuery);
            String searchParam = "%" + kw + "%";
            preparedStatement.setString(1, searchParam);
            preparedStatement.setString(2, searchParam);


            System.out.println("searchByKw: " + preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                int classRoom_id = rs.getInt("classRoom_id");
                String classRoom_name = rs.getString("classRoom_name");

                Student customer = new Student(id, name, address, email, phone,
                        new ClassRoom(classRoom_id, classRoom_name));
                student.add(customer);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }

        return student;
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
    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
