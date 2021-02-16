package com.tracker.web.jdbc;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class StudentControllerServlet extends HttpServlet {
    
    private StudentDbUtil studentDbUtil;
    
    @Resource(name = "jdbc/web_student_tracker")
    private DataSource dataSource;
    
    @Override
    public void init() throws ServletException {
        super.init();
        
        try {
            studentDbUtil = new StudentDbUtil((dataSource));
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        try {
            listStudents(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(StudentControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void listStudents(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        
        List<Student> students = studentDbUtil.getStudents();
        
        request.setAttribute("STUDENT_LIST", students);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/list-student.jsp");
        
        dispatcher.forward(request, response);
    }
    
}
