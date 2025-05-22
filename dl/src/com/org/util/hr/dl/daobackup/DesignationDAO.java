package com.org.util.hr.dl.dao;
import com.org.util.hr.dl.exceptions.*;
import com.org.util.hr.dl.interfaces.*;
import com.org.util.hr.dl.dto.*;
import java.io.*;
import java.util.*;
public class DesignationDAO implements DesignationDAOInterface
{
private static String DATA_FILE="c:\\javaprojects\\hr\\dl\\src\\com\\org\\util\\hr\\dl\\dao\\Designation.data";
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
File file=new File(DATA_FILE);
RandomAccessFile randomAccessFile=null;
try
{
randomAccessFile=new RandomAccessFile(file,"rw");
String recordCountString="0         ";
String lastGeneratedCodeString="0         ";
int lastGeneratedCode=0;
int recordCount=0;
if(file.length()==0)
{
randomAccessFile.writeBytes(lastGeneratedCodeString+"\r\n");
randomAccessFile.writeBytes(recordCountString+"\r\n");
}
else
{
lastGeneratedCodeString=randomAccessFile.readLine().trim();
recordCountString=randomAccessFile.readLine().trim();
lastGeneratedCode=Integer.parseInt(lastGeneratedCodeString);
recordCount=Integer.parseInt(recordCountString);
}
String fCode,fTitle;
int y=1;
while(y<=recordCount)
{
fCode=randomAccessFile.readLine();
fTitle=randomAccessFile.readLine();
if(title.equalsIgnoreCase(fTitle))
{
randomAccessFile.close();
throw new DAOException("Designation title : "+title+" already exists.");
}
y++;
}
lastGeneratedCode++;
recordCount++;
code=lastGeneratedCode;
randomAccessFile.writeBytes(String.valueOf(code)+"\r\n");
randomAccessFile.writeBytes(title+"\r\n");
designationDTO.setCode(code);
lastGeneratedCodeString=String.valueOf(lastGeneratedCode);
recordCountString=String.valueOf(recordCount);
while(lastGeneratedCodeString.length()<10)lastGeneratedCodeString+=" ";
while(recordCountString.length()<10)recordCountString+=" ";
randomAccessFile.seek(0);
randomAccessFile.writeBytes(lastGeneratedCodeString+"\r\n");
randomAccessFile.writeBytes(recordCountString+"\r\n");
randomAccessFile.close();
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
}
public void delete(int code) throws DAOException
{
if(code<=0)throw new DAOException("Invalid code : "+code);
File file=new File(DATA_FILE);
if(file.exists()==false)throw new DAOException("Invalid code : "+code);
RandomAccessFile randomAccessFile=null;
try
{
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid code : "+code);
}
long recordCountInitialPosition=0;
String lastGeneratedCodeString="";
String recordCountString="";
int recordCount=0;
lastGeneratedCodeString=randomAccessFile.readLine().trim();//read lastGeneratedCodeString
recordCountString=randomAccessFile.readLine().trim();
recordCount=Integer.parseInt(recordCountString);
if(recordCount==0)
{
randomAccessFile.close();
throw new DAOException("Invalid code : "+code);
}
recordCountInitialPosition=randomAccessFile.getFilePointer();
String fCode="";
String fTitle="";
int y=1;
while(y<=recordCount)
{
fCode=randomAccessFile.readLine();
fTitle=randomAccessFile.readLine();
if(code==Integer.parseInt(fCode))break;
y++;
}
if(new EmployeeDAO().designationAlloted(code)==true)
{
randomAccessFile.close();
throw new DAOException("Employee Designation exists with title : "+fTitle+" against this code");
}
if(y>recordCount)
{
randomAccessFile.close();
throw new DAOException("Invalid code : "+code);
}
File tempFile=new File("designation.tmp");
RandomAccessFile randomAccessTempFile=new RandomAccessFile(tempFile,"rw");
randomAccessTempFile.setLength(0);
randomAccessFile.seek(recordCountInitialPosition);
y=1;
while(y<=recordCount)
{
fCode=randomAccessFile.readLine();
fTitle=randomAccessFile.readLine();
if(code!=Integer.parseInt(fCode))
{
randomAccessTempFile.writeBytes(fCode+"\r\n");
randomAccessTempFile.writeBytes(fTitle+"\r\n");
}
y++;
}
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
fCode=randomAccessTempFile.readLine();
fTitle=randomAccessTempFile.readLine();
randomAccessFile.writeBytes(fCode+"\r\n");
randomAccessFile.writeBytes(fTitle+"\r\n");
}
randomAccessTempFile.close();
randomAccessFile.close();
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
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
File file=new File(DATA_FILE);
if(file.exists()==false)throw new DAOException("Invalid Designation code "+code);
RandomAccessFile randomAccessFile;
try
{
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid Designation code "+code);
}
long recordCountInitialPosition=0;
int recordCount=0;
String recordCountString="";
String lastGeneratedCodeString="";
lastGeneratedCodeString=randomAccessFile.readLine().trim();//read lastGeneratedCode
recordCountString=randomAccessFile.readLine().trim();
recordCount=Integer.parseInt(recordCountString);
if(recordCount==0)
{
randomAccessFile.close();
throw new DAOException("Invalid Designation code "+code);
}
String fCode,fTitle;
recordCountInitialPosition=randomAccessFile.getFilePointer();
int y=1;
while(y<=recordCount)
{
fCode=randomAccessFile.readLine();
fTitle=randomAccessFile.readLine();
if(code==Integer.parseInt(fCode))break;
y++;
}
if(y>recordCount)
{
randomAccessFile.close();
throw new DAOException("Invalid Designation code "+code);
}
randomAccessFile.seek(recordCountInitialPosition);
y=1;
while(y<=recordCount)
{
fCode=randomAccessFile.readLine();
fTitle=randomAccessFile.readLine();
if((title.equalsIgnoreCase(fTitle))&&code!=Integer.parseInt(fCode))
{
randomAccessFile.close();
throw new DAOException("Designation title already exists against some other code");
}
y++;
}

File tempFile=new File("designation.tmp");
RandomAccessFile randomAccessTempFile=new RandomAccessFile(tempFile,"rw");
randomAccessTempFile.setLength(0);
randomAccessFile.seek(recordCountInitialPosition);
y=1;
while(y<=recordCount)
{
fCode=randomAccessFile.readLine();
fTitle=randomAccessFile.readLine();
if(code==Integer.parseInt(fCode))
{
randomAccessTempFile.writeBytes(fCode+"\r\n");
randomAccessTempFile.writeBytes(title+"\r\n");
}
else
{
randomAccessTempFile.writeBytes(fCode+"\r\n");
randomAccessTempFile.writeBytes(fTitle+"\r\n");
}
y++;
}
randomAccessFile.setLength(0);
while(recordCountString.length()<10)recordCountString+=" ";
while(lastGeneratedCodeString.length()<10)lastGeneratedCodeString+=" ";
randomAccessFile.writeBytes(lastGeneratedCodeString+"\r\n");
randomAccessFile.writeBytes(recordCountString+"\r\n");
randomAccessTempFile.seek(0);
while(randomAccessTempFile.getFilePointer()<randomAccessTempFile.length())
{
fCode=randomAccessTempFile.readLine();
fTitle=randomAccessTempFile.readLine();
randomAccessFile.writeBytes(fCode+"\r\n");
randomAccessFile.writeBytes(fTitle+"\r\n");
}
randomAccessTempFile.close();
randomAccessFile.close();
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
}
public TreeSet<DesignationDTOInterface> getAll() throws DAOException
{
File file=new File(DATA_FILE);
if(file.exists()==false)throw new DAOException("No DesignationDTO exists.");
RandomAccessFile randomAccessFile=null;
try
{
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("No DesignationDTO exists.");
}
int recordCount=0;
String recordCountString="";
randomAccessFile.readLine();//reading lastGeneratedCodeString
recordCountString=randomAccessFile.readLine().trim();
recordCount=Integer.parseInt(recordCountString);
if(recordCount==0)
{
randomAccessFile.close();
throw new DAOException("No DesignationDTO exists.");
}
TreeSet<DesignationDTOInterface> treeSet=new TreeSet<>();
String fCode,fTitle;
DesignationDTOInterface designationDTO;
int y=1;
while(y<=recordCount)
{
fCode=randomAccessFile.readLine();
fTitle=randomAccessFile.readLine();
designationDTO=new DesignationDTO();
designationDTO.setCode(Integer.parseInt(fCode));
designationDTO.setTitle(fTitle);
treeSet.add(designationDTO);
y++;
}
randomAccessFile.close();
return treeSet;
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
}
public DesignationDTOInterface getByCode(int code) throws DAOException
{
File file=new File(DATA_FILE);
if(file.exists()==false)throw new DAOException("Invalid code : "+code);
RandomAccessFile randomAccessFile=null;
try
{
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid code : "+code);
}
int recordCount=0;
String recordCountString="";
randomAccessFile.readLine();//reading lastGeneratedCodeString
recordCountString=randomAccessFile.readLine().trim();
recordCount=Integer.parseInt(recordCountString);
if(recordCount==0)
{
randomAccessFile.close();
throw new DAOException("Invalid code : "+code);
}
String fCode,fTitle;
int y=1;
while(y<=recordCount)
{
fCode=randomAccessFile.readLine();
fTitle=randomAccessFile.readLine();
if(code==Integer.parseInt(fCode))
{
DesignationDTOInterface designationDTO=new DesignationDTO();
designationDTO.setCode(code);
designationDTO.setTitle(fTitle);
randomAccessFile.close();
return designationDTO;
}
y++;
}
randomAccessFile.close();
throw new DAOException("Invalid code : "+code);
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
}
public DesignationDTOInterface getByTitle(String title) throws DAOException
{
if(title==null)throw new DAOException("Title required,null passed.");
title=title.trim();
File file=new File(DATA_FILE);
if(file.exists()==false)throw new DAOException("Invalid title : "+title);
RandomAccessFile randomAccessFile=null;
try
{
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid title : "+title);
}
int recordCount=0;
String recordCountString="";
randomAccessFile.readLine();//reading lastGeneratedCodeString
recordCountString=randomAccessFile.readLine().trim();
recordCount=Integer.parseInt(recordCountString);
if(recordCount==0)
{
randomAccessFile.close();
throw new DAOException("Invalid title : "+title);
}
String fCode,fTitle;
int y=1;
while(y<=recordCount)
{
fCode=randomAccessFile.readLine();
fTitle=randomAccessFile.readLine();
if(title.equalsIgnoreCase(fTitle))
{
DesignationDTOInterface designationDTO=new DesignationDTO();
designationDTO.setCode(Integer.parseInt(fCode));
designationDTO.setTitle(fTitle);
randomAccessFile.close();
return designationDTO;
}
y++;
}
randomAccessFile.close();
throw new DAOException("Invalid title : "+title);
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
}
public boolean codeExists(int code) throws DAOException
{
File file=new File(DATA_FILE);
if(file.exists()==false)return false;
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
randomAccessFile.readLine();//read lastGeneratedCodeString
recordCountString=randomAccessFile.readLine().trim();
recordCount=Integer.parseInt(recordCountString);
if(recordCount==0)return false;
String fCode,fTitle;
int y=1;
while(y<=recordCount)
{
fCode=randomAccessFile.readLine();
fTitle=randomAccessFile.readLine();
if(code==Integer.parseInt(fCode))
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
public boolean titleExists(String title) throws DAOException
{
if(title==null)return false;
title=title.trim();
File file=new File(DATA_FILE);
if(file.exists()==false)return false;
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
randomAccessFile.readLine();//read lastGeneratedCodeString
recordCountString=randomAccessFile.readLine().trim();
recordCount=Integer.parseInt(recordCountString);
if(recordCount==0)return false;
String fCode,fTitle;
int y=1;
while(y<=recordCount)
{
fCode=randomAccessFile.readLine();
fTitle=randomAccessFile.readLine();
if(title.equalsIgnoreCase(fTitle))
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
if(file.exists()==false)return 0;
RandomAccessFile randomAccessFile=null;
try
{
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return 0;
}
int recordCount=0;
String recordCountString="";
randomAccessFile.readLine();//read lastGeneratedCodeString
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