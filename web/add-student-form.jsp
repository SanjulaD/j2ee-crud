<%-- 
    Document   : add-student-form
    Created on : Feb 17, 2021, 11:25:22 AM
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="text/css" rel="stylesheet" href="css/style.css">
        <link type="text/css" rel="stylesheet" href="css/add-student-style.css">
        <title>Add Student</title>
    </head>
    <body>

        <div id="wrapper">
            <div id="wrapper">
                <h2>My University</h2>
            </div>
        </div>

        <div id="container">
            <h3>Add Student</h3>
            <form name="add-student" action="StudentControllerServlet" method="GET">
                <input type="hidden" name="command" value="ADD"/>
                <table>
                    <tbody>
                        <tr>
                            <td><label>First Name:</label></td>
                            <td><input type="text" name="firstName"/></td>
                        </tr>
                        <tr>
                            <td><label>Last Name:</label></td>
                            <td><input type="text" name="lastName"/></td>
                        </tr>
                        <tr>
                            <td><label>Email:</label></td>
                            <td><input type="text" name="email"/></td>
                        </tr>
                        <tr>
                            <td><label></label></td>
                            <td><input type="submit" value="Save" class="save"/></td>
                        </tr>
                    </tbody>
                </table>
            </form>
            <div style="clear: both;"></div>
            <p><a href="StudentControllerServlet">Back to List</a></p>
        </div>

    </body>
</html>
