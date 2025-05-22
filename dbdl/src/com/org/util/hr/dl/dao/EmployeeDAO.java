package com.org.util.hr.dl.dao;
import com.org.util.hr.dl.exceptions.*;
import com.org.util.hr.dl.interfaces.*;
import com.org.util.hr.dl.dto.*;
import com.org.util.enums.*;
import java.util.*;
import java.sql.*;
import java.math.*;
import java.io.*;
import java.text.*;
public class EmployeeDAO implements EmployeeDAOInterface
{

public void add(EmployeeDTOInterface employeeDTO) throws DAOException
{
try
{
if(employeeDTO==null)throw new DAOException("EmployeeDTO required,encountered null");
String employeeID="A1000000000";
String generatedID="";
String name=employeeDTO.getName();
char gender=employeeDTO.getGender();
String panCardNumber=employeeDTO.getPANCardNumber();
String aadharCardNumber=employeeDTO.getAadharCardNumber();
BigDecimal salary=employeeDTO.getSalary();
java.util.Date dateOfBirth=employeeDTO.getDateOfBirth();
boolean isIndian=employeeDTO.getIsIndian();
int designationCode=employeeDTO.getDesignationCode();
if(name==null)throw new DAOException("Name required");
name=name.trim();
if(name.length()==0)throw new DAOException("Name required");
if(name.length()>32)throw new DAOException("length of name cannot exceeds by 32");
if(gender==' ')throw new DAOException("Gender required");
if(dateOfBirth==null)throw new DAOException("Date of birth required.");
if(salary==null)throw new DAOException("salary required");
if(salary.signum()==-1)
{
throw new DAOException("Invalid salary "+salary.toPlainString());
}
String eSalary=salary.toPlainString();
if(panCardNumber==null&&aadharCardNumber==null)
{
throw new DAOException("PANCardNumber and AadharCardNumber required");
}
if(panCardNumber==null)throw new DAOException("PANCard Number required");
if(aadharCardNumber==null)throw new DAOException("AadharCard Number required");
panCardNumber=panCardNumber.trim().toUpperCase();
aadharCardNumber=aadharCardNumber.trim();
if(panCardNumber.length()==0&&aadharCardNumber.length()==0)
{
throw new DAOException("PANCardNumber and AadharCardNumber required");
}
if(panCardNumber.length()==0)
{
throw new DAOException("PANCardNumber required");
}
if(aadharCardNumber.length()==0)
{
throw new DAOException("AadharCardNumber required");
}
if(aadharCardNumber.length()>12&&panCardNumber.length()>12)
{
throw new DAOException("PANCardNumber and AadharCardNumber length cannot exceeds by 12");
}
if(aadharCardNumber.length()>12)
{
throw new DAOException("AadharCardNumber length cannot exceeds by 12");
}
if(panCardNumber.length()>12)
{
throw new DAOException("PANCardNumber length cannot exceeds by 12");
}
for(int i=0;i<aadharCardNumber.length();i++)
{
if(!(aadharCardNumber.charAt(i)>=48&&aadharCardNumber.charAt(i)<=57))throw new DAOException("AadharCardNumber should be numeric");
}
DesignationDAOInterface designationDAO=new DesignationDAO();
if(designationCode<=0)throw new DAOException("Invalid code : "+designationCode);
if(!designationDAO.codeExists(designationCode))
{
throw new DAOException("Invalid Designation code : "+designationCode);
}
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
String dateOfBirthString=sdf.format(dateOfBirth);
/*
employee_id
name
gender
date_of_birth
is_indian
basic_salary
pan_number
aadhar_number
designation_code
*/
String pan_number,aadhar_number;
Connection connection=DAOConnection.getConnection();
PreparedStatement p;
p=connection.prepareStatement("select gender from employee where pan_number=? and aadhar_number=?;");
p.setString(1,panCardNumber);
p.setString(2,aadharCardNumber);
ResultSet result=p.executeQuery();
if(result.next())
{
result.close();
p.close();
connection.close();
throw new DAOException("PANCardNumber("+panCardNumber+") and AadharCardNumber("+aadharCardNumber+") already exists.");
}
result.close();
p.close();
p=connection.prepareStatement("select gender from employee where pan_number=?;");
p.setString(1,panCardNumber);
result=p.executeQuery();
if(result.next())
{
result.close();
p.close();
connection.close();
throw new DAOException("PANCardNumber("+panCardNumber+") already exists.");
}
result.close();
p.close();
p=connection.prepareStatement("select gender from employee where aadhar_number=?;");
p.setString(1,aadharCardNumber);
result=p.executeQuery();
if(result.next())
{
result.close();
p.close();
connection.close();
throw new DAOException("AadharCardNumber("+aadharCardNumber+") already exists.");
}
result.close();
p.close();
p=connection.prepareStatement("insert into employee (name,gender,date_of_birth,is_indian,basic_salary,pan_number,aadhar_number,designation_code) values(?,?,?,?,?,?,?,?);",Statement.RETURN_GENERATED_KEYS);
java.sql.Date sqlDateOfBirth=new java.sql.Date(dateOfBirth.getYear(),dateOfBirth.getMonth(),dateOfBirth.getDate());
p.setString(1,name);
p.setString(2,String.valueOf(gender));
p.setDate(3,sqlDateOfBirth);
p.setBoolean(4,isIndian);
p.setBigDecimal(5,salary);
p.setString(6,panCardNumber);
p.setString(7,aadharCardNumber);
p.setInt(8,designationCode);
p.executeUpdate();
result=p.getGeneratedKeys();
result.next();
String lastGeneratedCodeString=String.valueOf(result.getInt(1));
generatedID=employeeID.substring(0,employeeID.length()-lastGeneratedCodeString.length()).concat(lastGeneratedCodeString);
employeeDTO.setEmployeeID(generatedID);
result.close();
p.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}



public void delete(String employeeID) throws DAOException
{
try
{
if(employeeID==null)
{
throw new DAOException("Invalid EmployeeID : "+employeeID);
}
employeeID=employeeID.trim();
if(employeeID.length()==0||employeeID.length()>11)
{
throw new DAOException("Invalid EmployeeID : "+employeeID);
}
int i=0;
for(i=0;i<employeeID.length();i++)
{
if(employeeID.charAt(i)=='0')break;
}
String empID=employeeID.substring(i+1);
int employee_id=Integer.parseInt(empID);
Connection connection;
connection=DAOConnection.getConnection();
PreparedStatement p;
p=connection.prepareStatement("select employee_id from employee where employee_id=?;");
p.setInt(1,employee_id);
ResultSet result=p.executeQuery();
if(!result.next())
{
result.close();
p.close();
connection.close();
}
result.close();
p.close();
p=connection.prepareStatement("delete from employee where employee_id=?;");
p.setInt(1,employee_id);
p.executeUpdate();
p.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}



public void update(EmployeeDTOInterface employeeDTO) throws DAOException
{
if(employeeDTO==null)
{
throw new DAOException("Employee required,encountered null");
}
try
{
char employeeGender;
String employeeID,employeeName;
BigDecimal employeeSalary;
java.util.Date employeeDOB;
boolean employeeIsIndian;
int employeeDesignationCode;
String employeePanCardNumber,employeeAadharCardNumber;
employeeID=employeeDTO.getEmployeeID();
employeeName=employeeDTO.getName();
employeeGender=employeeDTO.getGender();
employeeSalary=employeeDTO.getSalary();
employeeDOB=employeeDTO.getDateOfBirth();
employeeIsIndian=employeeDTO.getIsIndian();
employeeDesignationCode=employeeDTO.getDesignationCode();
employeePanCardNumber=employeeDTO.getPANCardNumber();
employeeAadharCardNumber=employeeDTO.getAadharCardNumber();
if(employeeID==null)
{
throw new DAOException("Invalid EmployeeID");
}
employeeID=employeeID.trim();
if(employeeID.length()==0)
{
throw new DAOException("Invalid EmployeeID : "+employeeID);
}
if(employeeID.length()>11)
{
throw new DAOException("Invalid EmployeeID : "+employeeID);
}
if(employeeName==null)throw new DAOException("Name required");
employeeName=employeeName.trim();
if(employeeName.length()==0) throw new DAOException("Name required");
if(employeeName.length()>31) throw new DAOException("length of name cannot exceeds by 31");
if(employeeGender==' ')throw new DAOException("Gender required");
if(employeeSalary==null)
{
throw new DAOException("Salary required");
}
if(employeeSalary.signum()==-1)
{
throw new DAOException("Invalid salary "+employeeSalary.toPlainString());
}
if(employeeDOB==null)
{
throw new DAOException("Date of birth required");
}
if(employeePanCardNumber==null&&employeeAadharCardNumber==null)
{
throw new DAOException("PANCard Number and AadharCard Number required");
}
if(employeePanCardNumber==null)throw new DAOException("PANCard Number required");
if(employeeAadharCardNumber==null)throw new DAOException("AadharCard Number required");
employeePanCardNumber=employeePanCardNumber.trim().toUpperCase();
employeeAadharCardNumber=employeeAadharCardNumber.trim();
if(employeePanCardNumber.length()==0&&employeeAadharCardNumber.length()==0)
{
throw new DAOException("PANCardNumber and AadharCardNumber required");
}
if(employeePanCardNumber.length()==0)
{
throw new DAOException("PANCardNumber required");
}
if(employeeAadharCardNumber.length()==0)
{
throw new DAOException("AadharCardNumber required");
}
if(employeeAadharCardNumber.length()>12&&employeePanCardNumber.length()>12)
{
throw new DAOException("PANCardNumber and AadharCardNumber length cannot exceeds by 12");
}
if(employeeAadharCardNumber.length()>12)
{
throw new DAOException("AadharCardNumber length cannot exceeds by 12");
}
if(employeePanCardNumber.length()>12)
{
throw new DAOException("PANCardNumber length cannot exceeds by 12");
}
for(int i=0;i<employeeAadharCardNumber.length();i++)
{
if(!(employeeAadharCardNumber.charAt(i)>=48&&employeeAadharCardNumber.charAt(i)<=57))throw new DAOException("AadharCardNumber should be numeric");
}
DesignationDAOInterface designationDAO=new DesignationDAO();
if(!designationDAO.codeExists(employeeDesignationCode))
{
throw new DAOException("Invalid designation code : "+employeeDesignationCode);
}
String empID;
int i=0;
for(i=0;i<employeeID.length();i++)if(employeeID.charAt(i)=='0')break;
empID=employeeID.substring(i+1);
int employee_id=Integer.parseInt(empID);
Connection connection=DAOConnection.getConnection();
PreparedStatement p;
p=connection.prepareStatement("select employee_id from employee where employee_id=?;");
p.setInt(1,employee_id);
ResultSet result=p.executeQuery();
if(!result.next())
{
result.close();
p.close();
connection.close();
throw new DAOException("Invalid EmployeeID : "+employeeID);
}
result.close();
p.close();
p=connection.prepareStatement("select employee_id from employee where pan_number=? and aadhar_number=?;");
p.setString(1,employeePanCardNumber);
p.setString(2,employeeAadharCardNumber);
result=p.executeQuery();
if(result.next())
{
if(result.getInt(1)!=employee_id)
{
result.close();
p.close();
connection.close();
throw new DAOException("PANCardNumber("+employeePanCardNumber+") and AadharCardNumber("+employeeAadharCardNumber+") already exists against some other employee");
}
result.close();
p.close();
}
else 
{
result.close();
p.close();
p=connection.prepareStatement("select employee_id from employee where pan_number=?;");
p.setString(1,employeePanCardNumber);
result=p.executeQuery();
if(result.next())
{
if(result.getInt(1)!=employee_id)
{
result.close();
p.close();
connection.close();
throw new DAOException("PANCardNumber("+employeePanCardNumber+") already exists against some other employee");
}
result.close();
p.close();
}
else
{
result.close();
p.close();
}
p=connection.prepareStatement("select employee_id from employee where aadhar_number=?;");
p.setString(1,employeeAadharCardNumber);
result=p.executeQuery();
if(result.next())
{
if(result.getInt(1)!=employee_id)
{
result.close();
p.close();
connection.close();
throw new DAOException("AadharCardNumber("+employeeAadharCardNumber+") already exists against some other employee");
}
result.close();
p.close();
}
else
{
result.close();
p.close();
}
}
p=connection.prepareStatement("update employee set name=?,gender=?,date_of_birth=?,is_indian=?,basic_salary=?,pan_number=?,aadhar_number=?,designation_code=? where employee_id=?;");
java.sql.Date sqlDateOfBirth=new java.sql.Date(employeeDOB.getYear(),employeeDOB.getMonth(),employeeDOB.getDate());
sqlDateOfBirth.setYear(employeeDOB.getYear());
sqlDateOfBirth.setMonth(employeeDOB.getMonth());
sqlDateOfBirth.setDate(employeeDOB.getDate());
p.setString(1,employeeName);
p.setString(2,String.valueOf(employeeGender));
p.setDate(3,sqlDateOfBirth);
p.setBoolean(4,employeeIsIndian);
p.setBigDecimal(5,employeeSalary);
p.setString(6,employeePanCardNumber);
p.setString(7,employeeAadharCardNumber);
p.setInt(8,employeeDesignationCode);
p.setInt(9,employee_id);
p.executeUpdate();
result.close();
p.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}


public Set<EmployeeDTOInterface> getAll() throws DAOException
{
try
{
Set<EmployeeDTOInterface> employeeSet=new TreeSet<>();
EmployeeDTOInterface employeeDTO=null;
String employeeID="A1000000000";
String generatedID="";
String lastGeneratedCodeString=null;
String name;
int employee_id;
char gender;
java.util.Date dd;
java.sql.Date date_of_birth;
boolean is_indian;
BigDecimal basic_salary;
String pan_number,aadhar_number;
int designation_code;
Connection connection=DAOConnection.getConnection();
PreparedStatement p;
p=connection.prepareStatement("select * from employee;");
ResultSet result=p.executeQuery();
while(result.next())
{
employee_id=result.getInt("employee_id");
name=result.getString("name").trim();
gender=result.getString("gender").trim().charAt(0);
date_of_birth=result.getDate("date_of_birth");
is_indian=result.getBoolean("is_indian");
basic_salary=result.getBigDecimal("basic_salary");
pan_number=result.getString("pan_number").trim();
aadhar_number=result.getString("aadhar_number").trim();
designation_code=result.getInt("designation_code");
employeeDTO=new EmployeeDTO();
lastGeneratedCodeString=String.valueOf(employee_id);
generatedID=employeeID.substring(0,employeeID.length()-lastGeneratedCodeString.length()).concat(lastGeneratedCodeString);
employeeDTO.setEmployeeID(generatedID);//do something
employeeDTO.setName(name);
if(gender=='M')employeeDTO.setGender(GENDER.Male);
else employeeDTO.setGender(GENDER.Female);
dd=new java.util.Date();
dd.setYear(date_of_birth.getYear());
dd.setMonth(date_of_birth.getMonth());
dd.setDate(date_of_birth.getDate());
employeeDTO.setDateOfBirth(dd);
employeeDTO.setIsIndian(is_indian);
employeeDTO.setSalary(basic_salary);
employeeDTO.setPANCardNumber(pan_number);
employeeDTO.setAadharCardNumber(aadhar_number);
employeeDTO.setDesignationCode(designation_code);
employeeSet.add(employeeDTO);
}
result.close();
p.close();
connection.close();
return employeeSet;
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}


public int getCountByDesignation(int code) throws DAOException
{
if(code<=0)throw new DAOException("Invalid code : "+code);
try
{
DesignationDAOInterface designationDAO=new DesignationDAO();
if(designationDAO.codeExists(code)==false)throw new DAOException("Invalid code : "+code);
Connection connection=DAOConnection.getConnection();
PreparedStatement p;
p=connection.prepareStatement("select count(*) from employee where employee.designation_code=?;");
p.setInt(1,code);
ResultSet result=p.executeQuery();
if(!result.next())
{
result.close();
p.close();
connection.close();
return 0;
}
int count=result.getInt(1);
result.close();
p.close();
connection.close();
return count;
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}


public boolean designationAlloted(int code) throws DAOException
{
if(code<=0)return false;
try
{
DesignationDAOInterface designationDAO=new DesignationDAO();
if(designationDAO.codeExists(code)==false)return false;
Connection connection=DAOConnection.getConnection();
PreparedStatement p;
p=connection.prepareStatement("select employee_id from employee where employee.designation_code=?;");
p.setInt(1,code);
ResultSet result=p.executeQuery();
if(!result.next())
{
result.close();
p.close();
connection.close();
return false;
}
result.close();
p.close();
connection.close();
return true;
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}



public Set<EmployeeDTOInterface> getByDesignation(int code) throws DAOException
{
if(code<=0)throw new DAOException("Invalid code : "+code);
DesignationDAOInterface designationDAO=new DesignationDAO();
if(!designationDAO.codeExists(code))
{
throw new DAOException("Invalid code : "+code);
}
try
{
Set<EmployeeDTOInterface> employeeSet=new TreeSet<>();
EmployeeDTOInterface employeeDTO=null;
String employeeID="A1000000000";
String generatedID="";
String lastGeneratedCodeString=null;
String name;
int employee_id;
char gender;
java.util.Date dd;
java.sql.Date date_of_birth;
boolean is_indian;
BigDecimal basic_salary;
String pan_number,aadhar_number;
int designation_code;
Connection connection=DAOConnection.getConnection();
PreparedStatement p;
p=connection.prepareStatement("select * from employee where employee.designation_code=?;");
p.setInt(1,code);
ResultSet result=p.executeQuery();
while(result.next())
{
employee_id=result.getInt("employee_id");
name=result.getString("name").trim();
gender=result.getString("gender").trim().charAt(0);
date_of_birth=result.getDate("date_of_birth");
is_indian=result.getBoolean("is_indian");
basic_salary=result.getBigDecimal("basic_salary");
pan_number=result.getString("pan_number").trim();
aadhar_number=result.getString("aadhar_number").trim();
designation_code=result.getInt("designation_code");
employeeDTO=new EmployeeDTO();
lastGeneratedCodeString=String.valueOf(employee_id);
generatedID=employeeID.substring(0,employeeID.length()-lastGeneratedCodeString.length()).concat(lastGeneratedCodeString);
employeeDTO.setEmployeeID(generatedID);//do something
employeeDTO.setName(name);
if(gender=='M')employeeDTO.setGender(GENDER.Male);
else employeeDTO.setGender(GENDER.Female);
dd=new java.util.Date();
dd.setYear(date_of_birth.getYear());
dd.setMonth(date_of_birth.getMonth());
dd.setDate(date_of_birth.getDate());
employeeDTO.setDateOfBirth(dd);
employeeDTO.setIsIndian(is_indian);
employeeDTO.setSalary(basic_salary);
employeeDTO.setPANCardNumber(pan_number);
employeeDTO.setAadharCardNumber(aadhar_number);
employeeDTO.setDesignationCode(designation_code);
employeeSet.add(employeeDTO);
}
result.close();
p.close();
connection.close();
return employeeSet;
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}

public EmployeeDTOInterface getByEmployeeID(String employeeID) throws DAOException
{
if(employeeID==null||employeeID.length()==0)
{
throw new DAOException("Invalid EmployeeID");
}
employeeID=employeeID.trim();
if(employeeID.length()>11)
{
throw new DAOException("Invalid EmployeeID : "+employeeID);
}
try
{
String empID;
int i=0;
for(i=0;i<employeeID.length();i++)if(employeeID.charAt(i)=='0')break;
empID=employeeID.substring(i+1);
int employee_id=Integer.parseInt(empID);
Connection connection=null;
connection=DAOConnection.getConnection();
PreparedStatement p;
p=connection.prepareStatement("select * from employee where employee_id=?;");
p.setInt(1,employee_id);
ResultSet result=p.executeQuery();
if(!result.next())
{
result.close();
p.close();
connection.close();
throw new DAOException("Invalid EmployeeId : "+employeeID);
}
EmployeeDTOInterface employeeDTO=new EmployeeDTO();
String name;
char gender;
java.util.Date dd;
java.sql.Date date_of_birth;
boolean is_indian;
BigDecimal basic_salary;
String pan_number,aadhar_number;
int designation_code;
employee_id=result.getInt("employee_id");
name=result.getString("name").trim();
gender=result.getString("gender").trim().charAt(0);
date_of_birth=result.getDate("date_of_birth");
is_indian=result.getBoolean("is_indian");
basic_salary=result.getBigDecimal("basic_salary");
pan_number=result.getString("pan_number").trim();
aadhar_number=result.getString("aadhar_number").trim();
designation_code=result.getInt("designation_code");
employeeDTO.setEmployeeID(employeeID);
employeeDTO.setName(name);
if(gender=='M')employeeDTO.setGender(GENDER.Male);
else employeeDTO.setGender(GENDER.Female);
dd=new java.util.Date();
dd.setYear(date_of_birth.getYear());
dd.setMonth(date_of_birth.getMonth());
dd.setDate(date_of_birth.getDate());
employeeDTO.setDateOfBirth(dd);
employeeDTO.setIsIndian(is_indian);
employeeDTO.setSalary(basic_salary);
employeeDTO.setPANCardNumber(pan_number);
employeeDTO.setAadharCardNumber(aadhar_number);
employeeDTO.setDesignationCode(designation_code);
result.close();
p.close();
connection.close();
return employeeDTO;
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}


public EmployeeDTOInterface getByPANCardNumber(String panCardNumber) throws DAOException
{
if(panCardNumber==null||panCardNumber.length()==0)
{
throw new DAOException("Invalid PANCardNumber");
}
panCardNumber=panCardNumber.trim();
if(panCardNumber.length()>12)
{
throw new DAOException("Invalid PANCardNumber : "+panCardNumber);
}
try
{
Connection connection=null;
connection=DAOConnection.getConnection();
PreparedStatement p;
p=connection.prepareStatement("select * from employee where pan_number=?;");
p.setString(1,panCardNumber);
ResultSet result=p.executeQuery();
if(!result.next())
{
result.close();
p.close();
connection.close();
throw new DAOException("Invalid PANCardNumber : "+panCardNumber);
}
EmployeeDTOInterface employeeDTO=new EmployeeDTO();
int employee_id;
String name;
char gender;
java.util.Date dd;
java.sql.Date date_of_birth;
boolean is_indian;
BigDecimal basic_salary;
String pan_number,aadhar_number;
int designation_code;
employee_id=result.getInt("employee_id");
name=result.getString("name").trim();
gender=result.getString("gender").trim().charAt(0);
date_of_birth=result.getDate("date_of_birth");
is_indian=result.getBoolean("is_indian");
basic_salary=result.getBigDecimal("basic_salary");
pan_number=result.getString("pan_number").trim();
aadhar_number=result.getString("aadhar_number").trim();
designation_code=result.getInt("designation_code");
String employeeID="A1000000000";
String generatedID="";
String lastGeneratedCodeString=String.valueOf(employee_id);
generatedID=employeeID.substring(0,employeeID.length()-lastGeneratedCodeString.length()).concat(lastGeneratedCodeString);
employeeDTO.setEmployeeID(generatedID);//do something
employeeDTO.setName(name);
if(gender=='M')employeeDTO.setGender(GENDER.Male);
else employeeDTO.setGender(GENDER.Female);
dd=new java.util.Date();
dd.setYear(date_of_birth.getYear());
dd.setMonth(date_of_birth.getMonth());
dd.setDate(date_of_birth.getDate());
employeeDTO.setDateOfBirth(dd);
employeeDTO.setSalary(basic_salary);
employeeDTO.setIsIndian(is_indian);
employeeDTO.setPANCardNumber(pan_number);
employeeDTO.setAadharCardNumber(aadhar_number);
employeeDTO.setDesignationCode(designation_code);
result.close();
p.close();
connection.close();
return employeeDTO;
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}

public EmployeeDTOInterface getByAadharCardNumber(String aadharCardNumber) throws DAOException
{
if(aadharCardNumber==null||aadharCardNumber.length()==0)
{
throw new DAOException("Invalid AadharCardNumber");
}
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()>12)
{
throw new DAOException("Invalid AadharCardNumber : "+aadharCardNumber);
}
try
{
Connection connection=null;
connection=DAOConnection.getConnection();
PreparedStatement p;
p=connection.prepareStatement("select * from employee where aadhar_number=?;");
p.setString(1,aadharCardNumber);
ResultSet result=p.executeQuery();
if(!result.next())
{
result.close();
p.close();
connection.close();
throw new DAOException("Invalid AadharCardNumber : "+aadharCardNumber);
}
EmployeeDTOInterface employeeDTO=new EmployeeDTO();
int employee_id;
String name;
char gender;
java.util.Date dd;
java.sql.Date date_of_birth;
boolean is_indian;
BigDecimal basic_salary;
String pan_number,aadhar_number;
int designation_code;
employee_id=result.getInt("employee_id");
name=result.getString("name").trim();
gender=result.getString("gender").trim().charAt(0);
date_of_birth=result.getDate("date_of_birth");
is_indian=result.getBoolean("is_indian");
basic_salary=result.getBigDecimal("basic_salary");
pan_number=result.getString("pan_number").trim();
aadhar_number=result.getString("aadhar_number").trim();
designation_code=result.getInt("designation_code");
String employeeID="A1000000000";
String generatedID="";
String lastGeneratedCodeString=String.valueOf(employee_id);
generatedID=employeeID.substring(0,employeeID.length()-lastGeneratedCodeString.length()).concat(lastGeneratedCodeString);
employeeDTO.setEmployeeID(generatedID);//do something
employeeDTO.setName(name);
if(gender=='M')employeeDTO.setGender(GENDER.Male);
else employeeDTO.setGender(GENDER.Female);
dd=new java.util.Date();
dd.setYear(date_of_birth.getYear());
dd.setMonth(date_of_birth.getMonth());
dd.setDate(date_of_birth.getDate());
employeeDTO.setDateOfBirth(dd);
employeeDTO.setSalary(basic_salary);
employeeDTO.setIsIndian(is_indian);
employeeDTO.setPANCardNumber(pan_number);
employeeDTO.setAadharCardNumber(aadhar_number);
employeeDTO.setDesignationCode(designation_code);
result.close();
p.close();
connection.close();
return employeeDTO;
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}

public boolean employeeIDExists(String employeeID) throws DAOException
{
if(employeeID==null)return false;
employeeID=employeeID.trim();
if(employeeID.length()==0||employeeID.length()>11)return false;
String empID;
int i=0;
for(i=0;i<employeeID.length();i++)if(employeeID.charAt(i)=='0')break;
empID=employeeID.substring(i+1);
int employee_id=Integer.parseInt(empID);
try
{
Connection connection=null;
connection=DAOConnection.getConnection();
PreparedStatement p;
p=connection.prepareStatement("select employee_id from employee where employee_id=?;");
p.setInt(1,employee_id);
ResultSet result=p.executeQuery();
if(!result.next())
{
result.close();
p.close();
connection.close();
return false;
}
result.close();
p.close();
connection.close();
return true;
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}

public boolean panCardNumberExists(String panCardNumber) throws DAOException
{
if(panCardNumber==null)return false;
panCardNumber=panCardNumber.trim();
if(panCardNumber.length()==0||panCardNumber.length()>12)return false;
try
{
Connection connection=null;
connection=DAOConnection.getConnection();
PreparedStatement p;
p=connection.prepareStatement("select pan_number from employee where pan_number=?;");
p.setString(1,panCardNumber);
ResultSet result=p.executeQuery();
if(!result.next())
{
result.close();
p.close();
connection.close();
return false;
}
result.close();
p.close();
connection.close();
return true;
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}

public boolean aadharCardNumberExists(String aadharCardNumber) throws DAOException
{
if(aadharCardNumber==null)return false;
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0||aadharCardNumber.length()>12)return false;
try
{
Connection connection=null;
connection=DAOConnection.getConnection();
PreparedStatement p;
p=connection.prepareStatement("select aadhar_number from employee where aadhar_number=?;");
p.setString(1,aadharCardNumber);
ResultSet result=p.executeQuery();
if(!result.next())
{
result.close();
p.close();
connection.close();
return false;
}
result.close();
p.close();
connection.close();
return true;
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}

public int getCount() throws DAOException
{
try
{
Connection connection;
connection=DAOConnection.getConnection();
PreparedStatement p;
p=connection.prepareStatement("select count(*) from employee;");
ResultSet result=p.executeQuery();
if(!result.next())
{
result.close();
p.close();
connection.close();
return 0;
}
int count=result.getInt(1);
result.close();
p.close();
connection.close();
return count;
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}


}
