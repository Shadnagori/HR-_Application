package com.org.util.hr.dl.dao;
import com.org.util.hr.dl.exceptions.*;
import com.org.util.hr.dl.interfaces.*;
import com.org.util.hr.dl.dto.*;
import com.org.util.enums.*;
import java.util.*;
import java.math.*;
import java.io.*;
import java.text.*;
public class EmployeeDAO implements EmployeeDAOInterface
{
private static String DATA_FILE="employee.ddd";
public void add(EmployeeDTOInterface employeeDTO) throws DAOException
{
if(employeeDTO==null)throw new DAOException("EmployeeDTO required,encountered null");
String employeeID="A1000000000";
String generatedID="";
String name=employeeDTO.getName();
char gender=employeeDTO.getGender();
String panCardNumber=employeeDTO.getPANCardNumber();
String aadharCardNumber=employeeDTO.getAadharCardNumber();
BigDecimal salary=employeeDTO.getSalary();
Date dateOfBirth=employeeDTO.getDateOfBirth();
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
File file=new File(DATA_FILE);
RandomAccessFile randomAccessFile=null;
String lastGeneratedCodeString="";
String recordCountString="";
int lastGeneratedCode,recordCount;
try
{
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
lastGeneratedCodeString="0         ";
recordCountString="0         ";
randomAccessFile.writeBytes(lastGeneratedCodeString+"\r\n");
randomAccessFile.writeBytes(recordCountString+"\r\n");
}
randomAccessFile.seek(0);
lastGeneratedCodeString=randomAccessFile.readLine().trim();
recordCountString=randomAccessFile.readLine().trim();
recordCount=Integer.parseInt(recordCountString);
lastGeneratedCode=Integer.parseInt(lastGeneratedCodeString);
String fPanCardNumber,fAadharCardNumber;
int y=1;
while(y<=recordCount)
{
randomAccessFile.readLine();//reading employeeID
randomAccessFile.readLine();//reading employeeName
randomAccessFile.readLine();//reading employeeGender
randomAccessFile.readLine();//reading employeeIsIndian
randomAccessFile.readLine();//reading employeeSalary
randomAccessFile.readLine();//reading employeeDateOfBirth
randomAccessFile.readLine();//reading employeeDesignationCode
fPanCardNumber=randomAccessFile.readLine();//reading employeePAN
fAadharCardNumber=randomAccessFile.readLine();//reading employeeAadhar
if(fPanCardNumber.equalsIgnoreCase(panCardNumber)&&fAadharCardNumber.equalsIgnoreCase(aadharCardNumber))
{
randomAccessFile.close();
throw new DAOException("PANCardNumber("+panCardNumber+") and AadharCardNumber("+aadharCardNumber+") already exists.");
}
if(fPanCardNumber.equalsIgnoreCase(panCardNumber))
{
randomAccessFile.close();
throw new DAOException("PANCardNumber("+panCardNumber+") already exists.");
}
if(fAadharCardNumber.equalsIgnoreCase(aadharCardNumber))
{
randomAccessFile.close();
throw new DAOException("AdharCardNumber("+aadharCardNumber+") already exists.");
}
y++;
}

lastGeneratedCode++;
lastGeneratedCodeString=String.valueOf(lastGeneratedCode);
recordCount++;
recordCountString=String.valueOf(recordCount);
generatedID=employeeID.substring(0,employeeID.length()-lastGeneratedCodeString.length()).concat(lastGeneratedCodeString);
randomAccessFile.writeBytes(generatedID+"\r\n");//writing employeeID
randomAccessFile.writeBytes(name+"\r\n");//writing employeeName
randomAccessFile.writeBytes(gender+"\r\n");//writing employeeGender
randomAccessFile.writeBytes(isIndian+"\r\n");//writing employeeIsIndian
randomAccessFile.writeBytes(eSalary+"\r\n");//writing employeeSalary
randomAccessFile.writeBytes(dateOfBirthString+"\r\n");//writing employeeDateOfBirth
randomAccessFile.writeBytes(designationCode+"\r\n");//writing employeeDesignationCode
randomAccessFile.writeBytes(panCardNumber+"\r\n");//writing employeePAN
randomAccessFile.writeBytes(aadharCardNumber+"\r\n");//writing employeeAadhar
randomAccessFile.seek(0);
while(lastGeneratedCodeString.length()<10)lastGeneratedCodeString+=" ";
while(recordCountString.length()<10)recordCountString+=" ";
randomAccessFile.writeBytes(lastGeneratedCodeString+"\r\n");
randomAccessFile.writeBytes(recordCountString+"\r\n");
employeeDTO.setEmployeeID(generatedID);
randomAccessFile.close();
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
}
public void delete(String employeeID) throws DAOException
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

File file=new File(DATA_FILE);
RandomAccessFile randomAccessFile=null;
try
{
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid EmployeeID : "+employeeID);
}
String recordCountString="";
String lastGeneratedCodeString="";
int recordCount=0;
int lastGeneratedCode=0;
lastGeneratedCodeString=randomAccessFile.readLine().trim();
recordCountString=randomAccessFile.readLine().trim();
recordCount=Integer.parseInt(recordCountString);
if(recordCount==0)
{
randomAccessFile.close();
throw new DAOException("Invalid EmployeeID : "+employeeID);
}
long recordCountInitialPosition=0;
String fEmployeeID,employeeName;
String employeeGender,employeeIsIndian;
String employeeSalary,employeeDOB;
String employeeDesignationCode;
String employeePanCardNumber,employeeAadharCardNumber;
recordCountInitialPosition=randomAccessFile.getFilePointer();
int y=1;
while(y<=recordCount)
{
fEmployeeID=randomAccessFile.readLine();//reading employeeID
randomAccessFile.readLine();//reading employeeName
randomAccessFile.readLine();//reading employeeGender
randomAccessFile.readLine();//reading employeeIsIndian
randomAccessFile.readLine();//reading employeeSalary
randomAccessFile.readLine();//reading employeeDateOfBirth
randomAccessFile.readLine();//reading employeeDesignationCode
randomAccessFile.readLine();//reading employeePAN
randomAccessFile.readLine();//reading employeeAadhar
if(employeeID.equals(fEmployeeID))break;
y++;
}
if(y>recordCount)
{
randomAccessFile.close();
throw new DAOException("Invalid EmployeeID : "+employeeID);
}
randomAccessFile.seek(recordCountInitialPosition);//move internal pointer to the first of record to read again and copy to temp file
File tempFile=new File("employee.tmp");
RandomAccessFile randomAccessTempFile=new RandomAccessFile(tempFile,"rw");
randomAccessTempFile.setLength(0);
y=1;
while(y<=recordCount)
{
fEmployeeID=randomAccessFile.readLine();//reading employeeID
employeeName=randomAccessFile.readLine();//reading employeeName
employeeGender=randomAccessFile.readLine();//reading employeeGender
employeeIsIndian=randomAccessFile.readLine();//reading employeeIsIndian
employeeSalary=randomAccessFile.readLine();//reading employeeSalary
employeeDOB=randomAccessFile.readLine();//reading employeeDateOfBirth
employeeDesignationCode=randomAccessFile.readLine();//reading employeeDesignationCode
employeePanCardNumber=randomAccessFile.readLine();//reading employeePAN
employeeAadharCardNumber=randomAccessFile.readLine();//reading employeeAadhar
if(fEmployeeID.equals(employeeID)==false)
{
randomAccessTempFile.writeBytes(fEmployeeID+"\r\n");
randomAccessTempFile.writeBytes(employeeName+"\r\n");
randomAccessTempFile.writeBytes(employeeGender+"\r\n");
randomAccessTempFile.writeBytes(employeeIsIndian+"\r\n");
randomAccessTempFile.writeBytes(employeeSalary+"\r\n");
randomAccessTempFile.writeBytes(employeeDOB+"\r\n");
randomAccessTempFile.writeBytes(employeeDesignationCode+"\r\n");
randomAccessTempFile.writeBytes(employeePanCardNumber+"\r\n");
randomAccessTempFile.writeBytes(employeeAadharCardNumber+"\r\n");
}
y++;
}
randomAccessFile.seek(0);
randomAccessFile.setLength(0);
recordCount--;
recordCountString=String.valueOf(recordCount);
while(recordCountString.length()<10)recordCountString+=" ";
while(lastGeneratedCodeString.length()<10)lastGeneratedCodeString+=" ";
randomAccessFile.writeBytes(lastGeneratedCodeString+"\r\n");
randomAccessFile.writeBytes(recordCountString+"\r\n");
randomAccessTempFile.seek(0);
while(randomAccessTempFile.getFilePointer()<randomAccessTempFile.length())
{
fEmployeeID=randomAccessTempFile.readLine();//reading employeeID
employeeName=randomAccessTempFile.readLine();//reading employeeName
employeeGender=randomAccessTempFile.readLine();//reading employeeGender
employeeIsIndian=randomAccessTempFile.readLine();//reading employeeIsIndian
employeeSalary=randomAccessTempFile.readLine();//reading employeeSalary
employeeDOB=randomAccessTempFile.readLine();//reading employeeDateOfBirth
employeeDesignationCode=randomAccessTempFile.readLine();//reading employeeDesignationCode
employeePanCardNumber=randomAccessTempFile.readLine();//reading employeePAN
employeeAadharCardNumber=randomAccessTempFile.readLine();//reading employeeAadhar
randomAccessFile.writeBytes(fEmployeeID+"\r\n");
randomAccessFile.writeBytes(employeeName+"\r\n");
randomAccessFile.writeBytes(employeeGender+"\r\n");
randomAccessFile.writeBytes(employeeIsIndian+"\r\n");
randomAccessFile.writeBytes(employeeSalary+"\r\n");
randomAccessFile.writeBytes(employeeDOB+"\r\n");
randomAccessFile.writeBytes(employeeDesignationCode+"\r\n");
randomAccessFile.writeBytes(employeePanCardNumber+"\r\n");
randomAccessFile.writeBytes(employeeAadharCardNumber+"\r\n");
}
randomAccessFile.close();
randomAccessTempFile.close();
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
}
public void update(EmployeeDTOInterface employeeDTO) throws DAOException
{
if(employeeDTO==null)
{
throw new DAOException("Employee required,encountered null");
}

char employeeGender;
String employeeID,employeeName;
BigDecimal employeeSalary;
Date employeeDOB;
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
try
{
File file=new File(DATA_FILE);
RandomAccessFile randomAccessFile=null;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid EmployeeID : "+employeeID);
}
String recordCountString="";
String lastGeneratedCodeString="";
int recordCount=0;
int lastGeneratedCode=0;
lastGeneratedCodeString=randomAccessFile.readLine().trim();
recordCountString=randomAccessFile.readLine().trim();
recordCount=Integer.parseInt(recordCountString);
if(recordCount==0)
{
randomAccessFile.close();
throw new DAOException("Invalid EmployeeID : "+employeeID);
}
long recordCountInitialPosition=0;
String fEmployeeID,fEmployeeName;
String fEmployeeGender,fEmployeeIsIndian;
String fEmployeeSalary,fEmployeeDOB;
String fEmployeeDesignationCode;
String fEmployeePanCardNumber,fEmployeeAadharCardNumber;
recordCountInitialPosition=randomAccessFile.getFilePointer();
int y=1;
while(y<=recordCount)
{
fEmployeeID=randomAccessFile.readLine();//reading employeeID
randomAccessFile.readLine();//reading employeeName
randomAccessFile.readLine();//reading employeeGender
randomAccessFile.readLine();//reading employeeIsIndian
randomAccessFile.readLine();//reading employeeSalary
randomAccessFile.readLine();//reading employeeDateOfBirth
randomAccessFile.readLine();//reading employeeDesignationCode
randomAccessFile.readLine();//reading employeePAN
randomAccessFile.readLine();//reading employeeAadhar
if(fEmployeeID.equals(employeeID))break;
y++;
}
if(y>recordCount)
{
randomAccessFile.close();
throw new DAOException("Invalid EmployeeID : "+employeeID);
}
randomAccessFile.seek(recordCountInitialPosition);//move internal pointer to the first of record to read again and copy to temp file
y=1;
while(y<=recordCount)
{
fEmployeeID=randomAccessFile.readLine();
randomAccessFile.readLine();//reading employeeName
randomAccessFile.readLine();//reading employeeGender
randomAccessFile.readLine();//reading employeeIsIndian
randomAccessFile.readLine();//reading employeeSalary
randomAccessFile.readLine();//reading employeeDateOfBirth
randomAccessFile.readLine();//reading employeeDesignationCode
fEmployeePanCardNumber=randomAccessFile.readLine();
fEmployeeAadharCardNumber=randomAccessFile.readLine();
if(fEmployeePanCardNumber.equals(employeePanCardNumber)&&fEmployeeAadharCardNumber.equals(employeeAadharCardNumber))
{
if(fEmployeeID.equals(employeeID)==false)
{
randomAccessFile.close();
throw new DAOException("PANCardNumber("+fEmployeePanCardNumber+") and AadharCardNumber("+fEmployeeAadharCardNumber+") already exists against some other employeeID");
}
}
if(fEmployeePanCardNumber.equals(employeePanCardNumber)&&fEmployeeID.equals(employeeID)==false)
{
randomAccessFile.close();
throw new DAOException("PANCardNumber("+fEmployeePanCardNumber+") already exists against some other employeeID");
}
if(fEmployeeAadharCardNumber.equals(employeeAadharCardNumber)&&fEmployeeID.equals(employeeID)==false)
{
randomAccessFile.close();
throw new DAOException("AadharCardNumber("+fEmployeeAadharCardNumber+") already exists against some other employeeID");
}
y++;
}
randomAccessFile.seek(recordCountInitialPosition);//move internal pointer to the first of record to read again and copy to temp file
File tempFile=new File("employee.tmp");
RandomAccessFile randomAccessTempFile=new RandomAccessFile(tempFile,"rw");
randomAccessTempFile.setLength(0);
y=1;
while(y<=recordCount)
{
fEmployeeID=randomAccessFile.readLine();//reading employeeID
fEmployeeName=randomAccessFile.readLine();//reading employeeName
fEmployeeGender=randomAccessFile.readLine();//reading employeeGender
fEmployeeIsIndian=randomAccessFile.readLine();//reading employeeIsIndian
fEmployeeSalary=randomAccessFile.readLine();//reading employeeSalary
fEmployeeDOB=randomAccessFile.readLine();//reading employeeDateOfBirth
fEmployeeDesignationCode=randomAccessFile.readLine();//reading employeeDesignationCode
fEmployeePanCardNumber=randomAccessFile.readLine();//reading employeePAN
fEmployeeAadharCardNumber=randomAccessFile.readLine();//reading employeeAadhar
if(fEmployeeID.equals(employeeID)==false)
{
randomAccessTempFile.writeBytes(fEmployeeID+"\r\n");
randomAccessTempFile.writeBytes(fEmployeeName+"\r\n");
randomAccessTempFile.writeBytes(fEmployeeGender+"\r\n");
randomAccessTempFile.writeBytes(fEmployeeIsIndian+"\r\n");
randomAccessTempFile.writeBytes(fEmployeeSalary+"\r\n");
randomAccessTempFile.writeBytes(fEmployeeDOB+"\r\n");
randomAccessTempFile.writeBytes(fEmployeeDesignationCode+"\r\n");
randomAccessTempFile.writeBytes(fEmployeePanCardNumber+"\r\n");
randomAccessTempFile.writeBytes(fEmployeeAadharCardNumber+"\r\n");
}
else
{
randomAccessTempFile.writeBytes(employeeID+"\r\n");
randomAccessTempFile.writeBytes(employeeName+"\r\n");
randomAccessTempFile.writeBytes(employeeGender+"\r\n");
randomAccessTempFile.writeBytes(String.valueOf(employeeIsIndian)+"\r\n");
randomAccessTempFile.writeBytes(employeeSalary.toPlainString()+"\r\n");
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
randomAccessTempFile.writeBytes(sdf.format(employeeDOB)+"\r\n");
randomAccessTempFile.writeBytes(String.valueOf(employeeDesignationCode)+"\r\n");
randomAccessTempFile.writeBytes(employeePanCardNumber+"\r\n");
randomAccessTempFile.writeBytes(employeeAadharCardNumber+"\r\n");
}
y++;
}
randomAccessFile.seek(0);
randomAccessFile.setLength(0);
while(recordCountString.length()<10)recordCountString+=" ";
while(lastGeneratedCodeString.length()<10)lastGeneratedCodeString+=" ";
randomAccessFile.writeBytes(lastGeneratedCodeString+"\r\n");
randomAccessFile.writeBytes(recordCountString+"\r\n");
randomAccessTempFile.seek(0);
while(randomAccessTempFile.getFilePointer()<randomAccessTempFile.length())
{
fEmployeeID=randomAccessTempFile.readLine();//reading employeeID
fEmployeeName=randomAccessTempFile.readLine();//reading employeeName
fEmployeeGender=randomAccessTempFile.readLine();//reading employeeGender
fEmployeeIsIndian=randomAccessTempFile.readLine();//reading employeeIsIndian
fEmployeeSalary=randomAccessTempFile.readLine();//reading employeeSalary
fEmployeeDOB=randomAccessTempFile.readLine();//reading employeeDateOfBirth
fEmployeeDesignationCode=randomAccessTempFile.readLine();//reading employeeDesignationCode
fEmployeePanCardNumber=randomAccessTempFile.readLine();//reading employeePAN
fEmployeeAadharCardNumber=randomAccessTempFile.readLine();//reading employeeAadhar
randomAccessFile.writeBytes(fEmployeeID+"\r\n");
randomAccessFile.writeBytes(fEmployeeName+"\r\n");
randomAccessFile.writeBytes(fEmployeeGender+"\r\n");
randomAccessFile.writeBytes(fEmployeeIsIndian+"\r\n");
randomAccessFile.writeBytes(fEmployeeSalary+"\r\n");
randomAccessFile.writeBytes(fEmployeeDOB+"\r\n");
randomAccessFile.writeBytes(fEmployeeDesignationCode+"\r\n");
randomAccessFile.writeBytes(fEmployeePanCardNumber+"\r\n");
randomAccessFile.writeBytes(fEmployeeAadharCardNumber+"\r\n");
}
randomAccessFile.close();
randomAccessTempFile.close();
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
}
public Set<EmployeeDTOInterface> getAll() throws DAOException
{
File file=new File(DATA_FILE);
RandomAccessFile randomAccessFile=null;
try
{
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("No employeee exists.");
}
String recordCountString="";
int recordCount=0;
randomAccessFile.readLine();
recordCountString=randomAccessFile.readLine().trim();
recordCount=Integer.parseInt(recordCountString);
if(recordCount==0)
{
randomAccessFile.close();
throw new DAOException("No employeee exists.");
}
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
String employeeID,employeeName;
String employeeGender,employeeIsIndian;
String employeeSalary,employeeDOB;
String employeeDesignationCode;
String employeePanCardNumber,employeeAadharCardNumber;
EmployeeDTOInterface employeeDTO;
Set<EmployeeDTOInterface> set=new TreeSet<>();
int y=1;
while(y<=recordCount)
{
employeeID=randomAccessFile.readLine();//reading employeeID
employeeName=randomAccessFile.readLine();//reading employeeName
employeeGender=randomAccessFile.readLine();//reading employeeGender
employeeIsIndian=randomAccessFile.readLine();//reading employeeIsIndian
employeeSalary=randomAccessFile.readLine();//reading employeeSalary
employeeDOB=randomAccessFile.readLine();//reading employeeDateOfBirth
employeeDesignationCode=randomAccessFile.readLine();//reading employeeDesignationCode
employeePanCardNumber=randomAccessFile.readLine();//reading employeePAN
employeeAadharCardNumber=randomAccessFile.readLine();//reading employeeAadhar
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeID(employeeID);
employeeDTO.setName(employeeName);
if(employeeGender.charAt(0)=='M')employeeDTO.setGender(GENDER.Male);
else employeeDTO.setGender(GENDER.Female);
employeeDTO.setIsIndian(Boolean.parseBoolean(employeeIsIndian));
employeeDTO.setSalary(new BigDecimal(employeeSalary));
try
{
employeeDTO.setDateOfBirth(sdf.parse(employeeDOB));
}catch(ParseException pe)
{
randomAccessFile.close();
throw new DAOException(pe.getMessage());
}
employeeDTO.setDesignationCode(Integer.parseInt(employeeDesignationCode));
employeeDTO.setPANCardNumber(employeePanCardNumber);
employeeDTO.setAadharCardNumber(employeeAadharCardNumber);
set.add(employeeDTO);
y++;
}
randomAccessFile.close();
return set;
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
}

public int getCountByDesignation(int code) throws DAOException
{
if(code<=0)throw new DAOException("Invalid code : "+code);
DesignationDAOInterface designationDAO=new DesignationDAO();
if(designationDAO.codeExists(code)==false)throw new DAOException("Invalid code : "+code);
File file=new File(DATA_FILE);
try
{
RandomAccessFile randomAccessFile=null;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid code : "+code);
}
randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
int y,count,fCode;
count=0;
y=1;
while(y<=recordCount)
{
randomAccessFile.readLine();//reading EmployeeID
randomAccessFile.readLine();//reading EmployeeName
randomAccessFile.readLine();//reading EmployeeGender
randomAccessFile.readLine();//reading EmployeeIsIndian
randomAccessFile.readLine();//reading EmployeeSalary
randomAccessFile.readLine();//reading EmployeeDOB
fCode=Integer.parseInt(randomAccessFile.readLine());//reading EmployeeDesignationCode
randomAccessFile.readLine();//reading EmployeePANCardNumber
randomAccessFile.readLine();//reading EmployeeAadharCardNumber
if(fCode==code)count++;
y++;
}
randomAccessFile.close();
return count;
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
}
public boolean designationAlloted(int code) throws DAOException
{
if(code<=0)return false;
DesignationDAOInterface designationDAO=new DesignationDAO();
if(designationDAO.codeExists(code)==false)return false;
File file=new File(DATA_FILE);
try
{
RandomAccessFile randomAccessFile=null;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
int y,fCode;
y=1;
while(y<=recordCount)
{
randomAccessFile.readLine();//reading EmployeeID
randomAccessFile.readLine();//reading EmployeeName
randomAccessFile.readLine();//reading EmployeeGender
randomAccessFile.readLine();//reading EmployeeIsIndian
randomAccessFile.readLine();//reading EmployeeSalary
randomAccessFile.readLine();//reading EmployeeDOB
fCode=Integer.parseInt(randomAccessFile.readLine());//reading EmployeeDesignationCode
randomAccessFile.readLine();//reading EmployeePANCardNumber
randomAccessFile.readLine();//reading EmployeeAadharCardNumber
if(fCode==code)break;
y++;
}
randomAccessFile.close();
if(y>recordCount)return false;
return true;
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
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
File file=new File(DATA_FILE);
RandomAccessFile randomAccessFile=null;
try
{
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("No employee exists against code : "+code);
}
String recordCountString="";
int recordCount=0;
randomAccessFile.readLine();
recordCountString=randomAccessFile.readLine().trim();
recordCount=Integer.parseInt(recordCountString);
if(recordCount==0)
{
randomAccessFile.close();
throw new DAOException("No employee exists against code : "+code);
}

SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
String employeeID,employeeName;
String employeeGender,employeeIsIndian;
String employeeSalary,employeeDOB;
String employeeDesignationCode;
String employeePanCardNumber,employeeAadharCardNumber;
EmployeeDTOInterface employeeDTO;
Set<EmployeeDTOInterface> set=new TreeSet<>();
int y=1;
while(y<=recordCount)
{
employeeID=randomAccessFile.readLine();//reading employeeID
employeeName=randomAccessFile.readLine();//reading employeeName
employeeGender=randomAccessFile.readLine();//reading employeeGender
employeeIsIndian=randomAccessFile.readLine();//reading employeeIsIndian
employeeSalary=randomAccessFile.readLine();//reading employeeSalary
employeeDOB=randomAccessFile.readLine();//reading employeeDateOfBirth
employeeDesignationCode=randomAccessFile.readLine();//reading employeeDesignationCode
employeePanCardNumber=randomAccessFile.readLine();//reading employeePAN
employeeAadharCardNumber=randomAccessFile.readLine();//reading employeeAadhar
if(Integer.parseInt(employeeDesignationCode)!=code)
{
y++;
continue;
}
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeID(employeeID);
employeeDTO.setName(employeeName);
if(employeeGender.charAt(0)=='M')employeeDTO.setGender(GENDER.Male);
else employeeDTO.setGender(GENDER.Female);
employeeDTO.setIsIndian(Boolean.parseBoolean(employeeIsIndian));
employeeDTO.setSalary(new BigDecimal(employeeSalary));
try
{
employeeDTO.setDateOfBirth(sdf.parse(employeeDOB));
}catch(ParseException pe)
{
randomAccessFile.close();
throw new DAOException(pe.getMessage());
}
employeeDTO.setDesignationCode(Integer.parseInt(employeeDesignationCode));
employeeDTO.setPANCardNumber(employeePanCardNumber);
employeeDTO.setAadharCardNumber(employeeAadharCardNumber);
set.add(employeeDTO);
y++;
}
randomAccessFile.close();
if(set.size()==0)throw new DAOException("No employee exists against code : "+code);
return set;
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
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
File file=new File(DATA_FILE);
RandomAccessFile randomAccessFile=null;
try
{
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid EmployeeID : "+employeeID);
}
String recordCountString="";
int recordCount=0;
randomAccessFile.readLine();
recordCountString=randomAccessFile.readLine().trim();
recordCount=Integer.parseInt(recordCountString);
if(recordCount==0)
{
randomAccessFile.close();
throw new DAOException("Invalid EmployeeID : "+employeeID);
}

SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
String fEmployeeID,employeeName;
String employeeGender,employeeIsIndian;
String employeeSalary,employeeDOB;
String employeeDesignationCode;
String employeePanCardNumber,employeeAadharCardNumber;
EmployeeDTOInterface employeeDTO;
int y=1;
while(y<=recordCount)
{
fEmployeeID=randomAccessFile.readLine();//reading employeeID
employeeName=randomAccessFile.readLine();//reading employeeName
employeeGender=randomAccessFile.readLine();//reading employeeGender
employeeIsIndian=randomAccessFile.readLine();//reading employeeIsIndian
employeeSalary=randomAccessFile.readLine();//reading employeeSalary
employeeDOB=randomAccessFile.readLine();//reading employeeDateOfBirth
employeeDesignationCode=randomAccessFile.readLine();//reading employeeDesignationCode
employeePanCardNumber=randomAccessFile.readLine();//reading employeePAN
employeeAadharCardNumber=randomAccessFile.readLine();//reading employeeAadhar
if(fEmployeeID.equals(employeeID))
{
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeID(fEmployeeID);
employeeDTO.setName(employeeName);
if(employeeGender.charAt(0)=='M')employeeDTO.setGender(GENDER.Male);
else employeeDTO.setGender(GENDER.Female);
employeeDTO.setIsIndian(Boolean.parseBoolean(employeeIsIndian));
employeeDTO.setSalary(new BigDecimal(employeeSalary));
try
{
employeeDTO.setDateOfBirth(sdf.parse(employeeDOB));
}catch(ParseException pe)
{
randomAccessFile.close();
throw new DAOException(pe.getMessage());
}
employeeDTO.setDesignationCode(Integer.parseInt(employeeDesignationCode));
employeeDTO.setPANCardNumber(employeePanCardNumber);
employeeDTO.setAadharCardNumber(employeeAadharCardNumber);
randomAccessFile.close();
return employeeDTO;
}
y++;
}
randomAccessFile.close();
throw new DAOException("Invalid EmployeeID : "+employeeID);
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
}


public EmployeeDTOInterface getByPANCardNumber(String panCardNumber) throws DAOException
{
if(panCardNumber==null||panCardNumber.length()==0)
{
throw new DAOException("Invalid PANCardNumber");
}
panCardNumber=panCardNumber.trim().toUpperCase();
if(panCardNumber.length()>12)
{
throw new DAOException("Invalid PANCardNumber : "+panCardNumber);
}
File file=new File(DATA_FILE);
RandomAccessFile randomAccessFile=null;
try
{
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid PANCardNumber : "+panCardNumber);
}
String recordCountString="";
int recordCount=0;
randomAccessFile.readLine();
recordCountString=randomAccessFile.readLine().trim();
recordCount=Integer.parseInt(recordCountString);
if(recordCount==0)
{
randomAccessFile.close();
throw new DAOException("Invalid PANCardNumber : "+panCardNumber);
}
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
String employeeID,employeeName;
String employeeGender,employeeIsIndian;
String employeeSalary,employeeDOB;
String employeeDesignationCode;
String fPanCardNumber,employeeAadharCardNumber;
EmployeeDTOInterface employeeDTO;
int y=1;
while(y<=recordCount)
{
employeeID=randomAccessFile.readLine();//reading employeeID
employeeName=randomAccessFile.readLine();//reading employeeName
employeeGender=randomAccessFile.readLine();//reading employeeGender
employeeIsIndian=randomAccessFile.readLine();//reading employeeIsIndian
employeeSalary=randomAccessFile.readLine();//reading employeeSalary
employeeDOB=randomAccessFile.readLine();//reading employeeDateOfBirth
employeeDesignationCode=randomAccessFile.readLine();//reading employeeDesignationCode
fPanCardNumber=randomAccessFile.readLine();//reading employeePAN
employeeAadharCardNumber=randomAccessFile.readLine();//reading employeeAadhar
if(fPanCardNumber.equals(panCardNumber))
{
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeID(employeeID);
employeeDTO.setName(employeeName);
if(employeeGender.charAt(0)=='M')employeeDTO.setGender(GENDER.Male);
else employeeDTO.setGender(GENDER.Female);
employeeDTO.setIsIndian(Boolean.parseBoolean(employeeIsIndian));
employeeDTO.setSalary(new BigDecimal(employeeSalary));
try
{
employeeDTO.setDateOfBirth(sdf.parse(employeeDOB));
}catch(ParseException pe)
{
randomAccessFile.close();
throw new DAOException(pe.getMessage());
}
employeeDTO.setDesignationCode(Integer.parseInt(employeeDesignationCode));
employeeDTO.setPANCardNumber(fPanCardNumber);
employeeDTO.setAadharCardNumber(employeeAadharCardNumber);
randomAccessFile.close();
return employeeDTO;
}
y++;
}
randomAccessFile.close();
throw new DAOException("Invalid PANCardNumber : "+panCardNumber);
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
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
File file=new File(DATA_FILE);
RandomAccessFile randomAccessFile=null;
try
{
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid AadharCardNumber : "+aadharCardNumber);
}
String recordCountString="";
int recordCount=0;
randomAccessFile.readLine();
recordCountString=randomAccessFile.readLine().trim();
recordCount=Integer.parseInt(recordCountString);
if(recordCount==0)
{
randomAccessFile.close();
throw new DAOException("Invalid AadharCardNumber : "+aadharCardNumber);
}
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
String employeeID,employeeName;
String employeeGender,employeeIsIndian;
String employeeSalary,employeeDOB;
String employeeDesignationCode;
String employeePanCardNumber,fAadharCardNumber;
EmployeeDTOInterface employeeDTO;
int y=1;
while(y<=recordCount)
{
employeeID=randomAccessFile.readLine();//reading employeeID
employeeName=randomAccessFile.readLine();//reading employeeName
employeeGender=randomAccessFile.readLine();//reading employeeGender
employeeIsIndian=randomAccessFile.readLine();//reading employeeIsIndian
employeeSalary=randomAccessFile.readLine();//reading employeeSalary
employeeDOB=randomAccessFile.readLine();//reading employeeDateOfBirth
employeeDesignationCode=randomAccessFile.readLine();//reading employeeDesignationCode
employeePanCardNumber=randomAccessFile.readLine();//reading employeePAN
fAadharCardNumber=randomAccessFile.readLine();//reading employeeAadhar
if(fAadharCardNumber.equals(aadharCardNumber))
{
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeID(employeeID);
employeeDTO.setName(employeeName);
if(employeeGender.charAt(0)=='M')employeeDTO.setGender(GENDER.Male);
else employeeDTO.setGender(GENDER.Female);
employeeDTO.setIsIndian(Boolean.parseBoolean(employeeIsIndian));
employeeDTO.setSalary(new BigDecimal(employeeSalary));
try
{
employeeDTO.setDateOfBirth(sdf.parse(employeeDOB));
}catch(ParseException pe)
{
randomAccessFile.close();
throw new DAOException(pe.getMessage());
}
employeeDTO.setDesignationCode(Integer.parseInt(employeeDesignationCode));
employeeDTO.setPANCardNumber(employeePanCardNumber);
employeeDTO.setAadharCardNumber(fAadharCardNumber);
randomAccessFile.close();
return employeeDTO;
}
y++;
}
randomAccessFile.close();
throw new DAOException("Invalid AadharCardNumber : "+aadharCardNumber);
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
}

public boolean employeeIDExists(String employeeID) throws DAOException
{
if(employeeID==null)return false;
employeeID=employeeID.trim();
if(employeeID.length()==0||employeeID.length()>11)return false;
File file=new File(DATA_FILE);
RandomAccessFile randomAccessFile=null;
try
{
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return false;
}
String recordCountString="";
int recordCount=0;
randomAccessFile.readLine();
recordCountString=randomAccessFile.readLine().trim();
recordCount=Integer.parseInt(recordCountString);
if(recordCount==0)
{
randomAccessFile.close();
return false;
}
String fEmployeeID="";
int y=1;
while(y<=recordCount)
{
fEmployeeID=randomAccessFile.readLine();//reading employeeID
randomAccessFile.readLine();//reading employeeName
randomAccessFile.readLine();//reading employeeGender
randomAccessFile.readLine();//reading employeeIsIndian
randomAccessFile.readLine();//reading employeeSalary
randomAccessFile.readLine();//reading employeeDateOfBirth
randomAccessFile.readLine();//reading employeeDesignationCode
randomAccessFile.readLine();//reading employeePAN
randomAccessFile.readLine();//reading employeeAadhar
if(fEmployeeID.equals(employeeID))
{
randomAccessFile.close();
return true;
}
y++;
}
randomAccessFile.close();
return false;
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
}

public boolean panCardNumberExists(String panCardNumber) throws DAOException
{
if(panCardNumber==null)return false;
panCardNumber=panCardNumber.trim().toUpperCase();
if(panCardNumber.length()==0||panCardNumber.length()>12)return false;
File file=new File(DATA_FILE);
RandomAccessFile randomAccessFile=null;
try
{
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return false;
}
String recordCountString="";
int recordCount=0;
randomAccessFile.readLine();
recordCountString=randomAccessFile.readLine().trim();
recordCount=Integer.parseInt(recordCountString);
if(recordCount==0)
{
randomAccessFile.close();
return false;
}
String fPanCardNumber="";
int y=1;
while(y<=recordCount)
{
randomAccessFile.readLine();//reading employeeID
randomAccessFile.readLine();//reading employeeName
randomAccessFile.readLine();//reading employeeGender
randomAccessFile.readLine();//reading employeeIsIndian
randomAccessFile.readLine();//reading employeeSalary
randomAccessFile.readLine();//reading employeeDateOfBirth
randomAccessFile.readLine();//reading employeeDesignationCode
fPanCardNumber=randomAccessFile.readLine();//reading employeePAN
randomAccessFile.readLine();//reading employeeAadhar
if(fPanCardNumber.equalsIgnoreCase(panCardNumber))
{
randomAccessFile.close();
return true;
}
y++;
}
randomAccessFile.close();
return false;
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
}

public boolean aadharCardNumberExists(String aadharCardNumber) throws DAOException
{
if(aadharCardNumber==null)return false;
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0||aadharCardNumber.length()>12)return false;
File file=new File(DATA_FILE);
RandomAccessFile randomAccessFile=null;
try
{
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return false;
}
String recordCountString="";
int recordCount=0;
randomAccessFile.readLine();
recordCountString=randomAccessFile.readLine().trim();
recordCount=Integer.parseInt(recordCountString);
if(recordCount==0)
{
randomAccessFile.close();
return false;
}
String fAadharCardNumber="";
int y=1;
while(y<=recordCount)
{
randomAccessFile.readLine();//reading employeeID
randomAccessFile.readLine();//reading employeeName
randomAccessFile.readLine();//reading employeeGender
randomAccessFile.readLine();//reading employeeIsIndian
randomAccessFile.readLine();//reading employeeSalary
randomAccessFile.readLine();//reading employeeDateOfBirth
randomAccessFile.readLine();//reading employeeDesignationCode
randomAccessFile.readLine();//reading employeePAN
fAadharCardNumber=randomAccessFile.readLine();//reading employeeAadhar
if(fAadharCardNumber.equalsIgnoreCase(aadharCardNumber))
{
randomAccessFile.close();
return true;
}
y++;
}
randomAccessFile.close();
return false;
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
}

public int getCount() throws DAOException
{
File file=new File(DATA_FILE);
RandomAccessFile randomAccessFile=null;
try
{
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return 0;
}
String recordCountString="";
int recordCount=0;
randomAccessFile.readLine();
recordCountString=randomAccessFile.readLine().trim();
recordCount=Integer.parseInt(recordCountString);
randomAccessFile.close();
return recordCount;
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
}
}
