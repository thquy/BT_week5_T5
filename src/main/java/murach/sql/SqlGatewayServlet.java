package murach.sql;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class SqlGatewayServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String sqlStatement = request.getParameter("sqlStatement");
        String sqlResult = "";

        try {
            // Load SQL Server JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Kết nối tới SQL Server
            String dbURL = "jdbc:sqlserver://localhost:1434;"
                    + "databaseName=BT_C7;"
                    + "encrypt=true;trustServerCertificate=true;";
            String user = "sa";           // thay bằng user của bạn
            String password = "quy123";   // thay bằng password của bạn

            Connection connection = DriverManager.getConnection(dbURL, user, password);

            Statement statement = connection.createStatement();

            // Nếu chưa nhập thì mặc định select * from Users
            if (sqlStatement == null || sqlStatement.trim().equals("")) {
                sqlStatement = "SELECT * FROM Users";
            }

            sqlStatement = sqlStatement.trim();
            String sqlType = sqlStatement.substring(0, 6).toLowerCase();

            if (sqlType.equals("select")) {
                ResultSet resultSet = statement.executeQuery(sqlStatement);
                sqlResult = SQLUtil.getHtmlTable(resultSet);
                resultSet.close();
            } else {
                int i = statement.executeUpdate(sqlStatement);
                if (i == 0) {
                    sqlResult = "<p>The statement executed successfully.</p>";
                } else {
                    sqlResult = "<p>The statement executed successfully.<br>"
                            + i + " row(s) affected.</p>";
                }
            }

            statement.close();
            connection.close();

        } catch (ClassNotFoundException e) {
            sqlResult = "<p>Error loading the database driver:<br>"
                    + e.getMessage() + "</p>";
        } catch (SQLException e) {
            sqlResult = "<p>Error executing the SQL statement:<br>"
                    + e.getMessage() + "</p>";
        }

        // Đưa kết quả sang JSP
        HttpSession session = request.getSession();
        session.setAttribute("sqlResult", sqlResult);
        session.setAttribute("sqlStatement", sqlStatement);

        String url = "/index.jsp";
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
}
