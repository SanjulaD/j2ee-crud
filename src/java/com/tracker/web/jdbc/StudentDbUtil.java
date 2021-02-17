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
}
