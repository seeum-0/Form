// File: src/RegisterServlet.java

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:database/users.db");

            String createTable = "CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT, username TEXT, password TEXT)";
            Statement stmt = conn.createStatement();
            stmt.execute(createTable);
            stmt.close();

            String sql = "INSERT INTO users (email, username, password) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, username);
            ps.setString(3, password);
            ps.executeUpdate();

            ps.close();
            conn.close();

            out.println("<h1>Registration successful!</h1>");
        } catch (Exception e) {
            e.printStackTrace(out);
        } finally {
            out.close();
        }
    }
}
