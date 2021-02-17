package com.tracker.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class StudentDbUtil {

    private DataSource dataSource;

    public StudentDbUtil(DataSource theDataSource) {
        dataSource = theDataSource;
    }

    public List<Student> getStudents() throws SQLException {

        List<Student> students = new ArrayList<>();

        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        try {

            con = dataSource.getConnection();

            String sql = "select * from student order by last_name";

            st = con.createStatement();

            rs = st.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");

                Student tempStudent = new Student(id, firstName, lastName, email);

                students.add(tempStudent);
            }

            return students;
        } finally {
            close(con, st, rs);
        }
    }

    private void close(Connection con, Statement st, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }

            if (st != null) {
                st.close();
            }

            if (con != null) {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void addStudent(Student theStudent) throws Exception {
        // create sql for insert
        Connection con = null;
        PreparedStatement pst = null;

        try {
            con = dataSource.getConnection();

            String sql = "insert into student" + "(first_name, last_name, email)" + "values (?,?,?)";

            // set the param values
            pst = con.prepareStatement(sql);
            pst.setString(1, theStudent.getFirstName());
            pst.setString(2, theStudent.getLastName());
            pst.setString(3, theStudent.getEmail());

            // execute sql
            pst.execute();

        } finally {
            // clean up JDBC
            close(con, pst, null);
        }
    }

    Student loadStudent(String studentId) {
        Student student = null;

        //CONNECT TO DATABASE
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            //GET A CONNECTION
            conn = dataSource.getConnection();

            //CREATE SQL STATEMENT
            String sql = "select * from student where id=?";

            //CREATE PREPARED STATEMENT
            stmt = conn.prepareStatement(sql);

            //SET PARAMS
            stmt.setInt(1, Integer.parseInt(studentId));

            //EXECUTE QUERY
            rs = stmt.executeQuery();

            //GET DATA
            if (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");

                student = new Student(firstName, lastName, email);
                student.setId(Integer.parseInt(studentId));
            } else {
                throw new Exception("Couln't find the student id:" + studentId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, stmt, rs);
        }

        return student;

    }

    void updateStudent(Student student) {
        //DECLARE CONNECTION VARIABLES
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            //GET A CONNECTION
            conn = dataSource.getConnection();

            //PREPARE SQL QUERY
            String sql = "update student "
                    + "set first_name = ?,last_name = ?,email = ? "
                    + "where id=?";

            //CREATE PREPARED STATEMENT
            stmt = conn.prepareStatement(sql);

            //SET PARAMS
            stmt.setString(1, student.getFirstName());
            stmt.setString(2, student.getLastName());
            stmt.setString(3, student.getEmail());
            stmt.setInt(4, student.getId());

            //EXECUTE QUERY
            stmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, stmt, null);
        }
    }
}
