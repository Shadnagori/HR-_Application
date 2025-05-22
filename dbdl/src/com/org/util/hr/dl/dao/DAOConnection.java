package com.org.util.hr.dl.dao;
import java.sql.*;
class DAOConnection
{
private DAOConnection()
{
}
public static Connection getConnection() throws Exception
{
//load the jdbc driver
Class.forName("com.mysql.cj.jdbc.Driver");
//establish the connection with server
Connection connection=null;
connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/hrdb","hr","hr");
return connection;
}
}