package com.org.util.hr.dl.dao;
import com.org.util.hr.dl.exceptions.*;
import com.org.util.hr.dl.interfaces.*;
import com.org.util.hr.dl.dto.*;
import java.io.*;
import java.util.*;
import java.sql.*;
public class DesignationDAO implements DesignationDAOInterface
{
public void add(DesignationDTOInterface designationDTO) throws DAOException
{
if(designationDTO==null)throw new DAOException("DesignationDTO required,null encountered");
String title;
int code;
code=designationDTO.getCode();
title=designationDTO.getTitle();
if(title==null)throw new DAOException("DesignationDTO title required,null encountered");
title=title.trim();
if(title.length()==0)throw new DAOException("length of title is 0,title required");
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement p;
p=connection.prepareStatement("select code from designation where title=?;");
p.setString(1,title);
ResultSet result=p.executeQuery();
if(result.next())
{
p.close();
connection.close();
throw new DAOException(title+" already exists.");
}
p=connection.prepareStatement("insert into designation (title) values(?);",Statement.RETURN_GENERATED_KEYS);
p.setString(1,title);
p.executeUpdate();
result=p.getGeneratedKeys();
result.next();
code=result.getInt(1);
designationDTO.setCode(code);
p.close();
connection.close();
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}
}






public void delete(int code) throws DAOException
{
if(code<=0)throw new DAOException("Invalid code : "+code);
Connection connection=null;
try
{
connection=DAOConnection.getConnection();
PreparedStatement p;
p=connection.prepareStatement("select title from designation where code=?;");
p.setInt(1,code);
ResultSet result=p.executeQuery();
if(!result.next())
{
p.close();
connection.close();
throw new DAOException("Invalid code : "+code);
}
p=connection.prepareStatement("delete from designation where code=?;");
p.setInt(1,code);
p.executeUpdate();
p.close();
connection.close();
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}
}


public void update(DesignationDTOInterface designationDTO) throws DAOException
{
if(designationDTO==null)throw new DAOException("DesignationDTO required,null encountered");
String title;
int code;
code=designationDTO.getCode();
title=designationDTO.getTitle();
if(title==null)throw new DAOException("DesignationDTO title required,null encountered");
title=title.trim();
if(title.length()==0)throw new DAOException("Designation title required,length is zero");
Connection connection=null;
try
{
connection=DAOConnection.getConnection();
PreparedStatement p;
p=connection.prepareStatement("select title from designation where code=?;");
p.setInt(1,code);
ResultSet result=p.executeQuery();
if(!result.next())
{
p.close();
connection.close();
throw new DAOException("Invalid code : "+code);
}
p=connection.prepareStatement("select code from designation where title=?;");
p.setString(1,title);
result=p.executeQuery();
if(result.next())
{
int rCode=result.getInt("code");
if(rCode!=code)
{
p.close();
connection.close();
throw new DAOException(title+" already exists against some other code");
}
}
p=connection.prepareStatement("update designation set title=? where code=?;");
p.setString(1,title);
p.setInt(2,code);
p.executeUpdate();
p.close();
connection.close();
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}
}




public TreeSet<DesignationDTOInterface> getAll() throws DAOException
{
TreeSet<DesignationDTOInterface> treeSet=new TreeSet<>();
Connection connection=null;
try
{
connection=DAOConnection.getConnection();
PreparedStatement p;
p=connection.prepareStatement("select * from designation;");
ResultSet result=p.executeQuery();
DesignationDTOInterface designation;
int code;
String title;
while(result.next())
{
code=result.getInt("code");
title=result.getString("title").trim();
designation=new DesignationDTO();
designation.setCode(code);
designation.setTitle(title);
treeSet.add(designation);
}
p.close();
connection.close();
return treeSet;
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}
}

public DesignationDTOInterface getByCode(int code) throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement p;
p=connection.prepareStatement("select title from designation where code=?;");
p.setInt(1,code);
ResultSet result=p.executeQuery();
if(!result.next())
{
p.close();
connection.close();
throw new DAOException("Invalid code : "+code);
}
String title=result.getString("title").trim();
DesignationDTOInterface designation=new DesignationDTO();
designation.setCode(code);
designation.setTitle(title);
p.close();
connection.close();
return designation;
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}
}


public DesignationDTOInterface getByTitle(String title) throws DAOException
{
if(title==null)throw new DAOException("title required,null passed.");
title=title.trim();
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement p;
p=connection.prepareStatement("select code from designation where title=?;");
p.setString(1,title);
ResultSet result=p.executeQuery();
if(!result.next())
{
p.close();
connection.close();
throw new DAOException("Invalid title : "+title);
}
int code=result.getInt("code");
DesignationDTOInterface designation=new DesignationDTO();
designation.setCode(code);
designation.setTitle(title);
p.close();
connection.close();
return designation;
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}
}



public boolean codeExists(int code) throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement p;
p=connection.prepareStatement("select title from designation where code=?;");
p.setInt(1,code);
ResultSet result=p.executeQuery();
if(!result.next())
{
p.close();
connection.close();
return false;
}
p.close();
connection.close();
return true;
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}
}
public boolean titleExists(String title) throws DAOException
{
if(title==null)return false;
title=title.trim();
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement p;
p=connection.prepareStatement("select code from designation where title=?;");
p.setString(1,title);
ResultSet result=p.executeQuery();
if(!result.next())
{
p.close();
connection.close();
return false;
}
p.close();
connection.close();
return true;
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}
}



public int getCount() throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement p;
p=connection.prepareStatement("select count(*) from designation;");
ResultSet result=p.executeQuery();
if(!result.next())
{
p.close();
connection.close();
return 0;
}
int count=result.getInt(1);
p.close();
connection.close();
return count;
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}
}
}