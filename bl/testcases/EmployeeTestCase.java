import com.org.util.hr.bl.exceptions.*;
import com.org.util.hr.bl.interfaces.*;
import com.org.util.hr.bl.dto.*;
import com.org.util.hr.bl.dao.*;
import com.org.util.enums.*;
import java.util.*;
import java.math.*;
import java.text.*;
public class EmployeeTestCase
{
public static void main(String gg[])
{
EmployeeManagerInterface manager=new EmployeeManager();
Scanner scan=new Scanner(System.in);
int ch=0;
while(true)
{
System.out.println("1.Add Employee");
System.out.println("2.Delete Employee");
System.out.println("3.Update Employee");
System.out.println("4.Get List of employees");
System.out.println("5.Get Employees By Designation");
System.out.println("6.Get Employee By ID");
System.out.println("7.Get Employee By PAN");
System.out.println("8.Get Employee By Aadhar");
System.out.println("9.EmployeeID exists");
System.out.println("10.EmployeePAN exists");
System.out.println("11.EmployeeAadhar exists");
System.out.println("12.Get count of Employees");
System.out.println("13.Get count of Employees by designation");
System.out.println("14.Is Designation Alloted");
System.out.println("15.Exit");
System.out.print("Enter your choice : ");
ch=scan.nextInt();
scan.nextLine();
if(ch>=1&&ch<=15)
{
if(ch==1)
{
System.out.println("Enter the details of employee to add(Name,Gender,Salary,Nationality,DateOfBirth,Designation,PAN,Aadhar) : ");
String name=scan.nextLine();
char gender=scan.nextLine().charAt(0);
BigDecimal salary=null;
try
{
salary=new BigDecimal(scan.nextLine());
}catch(NumberFormatException nfe)
{
System.out.println(nfe.getMessage());
return;
}
boolean isIndian=Boolean.parseBoolean(scan.nextLine());
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
Date dateOfBirth=null;
try
{
dateOfBirth=sdf.parse(scan.nextLine());
}catch(ParseException pe)
{
System.out.println(pe.getMessage());
return;
}
int designationCode=scan.nextInt();
scan.nextLine();
String panCardNumber=scan.nextLine();
String aadharCardNumber=scan.nextLine();
EmployeeDTOInterface employee=new EmployeeDTO();
employee.setName(name);
if(gender=='M')employee.setGender(GENDER.Male);
else employee.setGender(GENDER.Female);
employee.setSalary(salary);
employee.setIsIndian(isIndian);
employee.setDateOfBirth(dateOfBirth);
employee.setDesignationCode(designationCode);
employee.setPANCardNumber(panCardNumber);
employee.setAadharCardNumber(aadharCardNumber);
try
{
manager.add(employee);
System.out.println("Employee added against ID("+employee.getEmployeeID()+")");
}catch(BLException blexception)
{
if(blexception.hasGenericException())System.out.println(blexception.getGenericException());
if(blexception.hasPropertyException("EmployeeID"))System.out.println(blexception.getPropertyException("EmployeeID"));
if(blexception.hasPropertyException("Name"))System.out.println(blexception.getPropertyException("Name"));
if(blexception.hasPropertyException("Gender"))System.out.println(blexception.getPropertyException("Gender"));
if(blexception.hasPropertyException("Salary"))System.out.println(blexception.getPropertyException("Salary"));
if(blexception.hasPropertyException("DateOfBirth"))System.out.println(blexception.getPropertyException("DateOfBirth"));
if(blexception.hasPropertyException("DesignationCode"))System.out.println(blexception.getPropertyException("DesignationCode"));
if(blexception.hasPropertyException("PANCardNumber"))System.out.println(blexception.getPropertyException("PANCardNumber"));
if(blexception.hasPropertyException("AadharCardNumber"))System.out.println(blexception.getPropertyException("AadharCardNumber"));
}
}
else if(ch==2)
{
System.out.println("Enter EmployeeID : ");
String employeeID=scan.nextLine();
try
{
manager.delete(employeeID);
System.out.println("Employee deleted with ID("+employeeID+")");
}catch(BLException blexception)
{
if(blexception.hasGenericException())System.out.println(blexception.getGenericException());
if(blexception.hasPropertyException("EmployeeID"))System.out.println(blexception.getPropertyException("EmployeeID"));
if(blexception.hasPropertyException("Name"))System.out.println(blexception.getPropertyException("Name"));
if(blexception.hasPropertyException("Gender"))System.out.println(blexception.getPropertyException("Gender"));
if(blexception.hasPropertyException("Salary"))System.out.println(blexception.getPropertyException("Salary"));
if(blexception.hasPropertyException("DateOfBirth"))System.out.println(blexception.getPropertyException("DateOfBirth"));
if(blexception.hasPropertyException("DesignationCode"))System.out.println(blexception.getPropertyException("DesignationCode"));
if(blexception.hasPropertyException("PANCardNumber"))System.out.println(blexception.getPropertyException("PANCardNumber"));
if(blexception.hasPropertyException("AadharCardNumber"))System.out.println(blexception.getPropertyException("AadharCardNumber"));
}
}
else if(ch==3)
{
System.out.println("Enter the details of employee to update(EmployeeID,Name,Gender,Salary,Nationality,DateOfBirth,Designation,PAN,Aadhar) : ");
String employeeID=scan.nextLine();
String name=scan.nextLine();
char gender=scan.nextLine().charAt(0);
BigDecimal salary=null;
try
{
salary=new BigDecimal(scan.nextLine());
}catch(NumberFormatException nfe)
{
System.out.println(nfe.getMessage());
return;
}
boolean isIndian=Boolean.parseBoolean(scan.nextLine());
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
Date dateOfBirth=null;
try
{
dateOfBirth=sdf.parse(scan.nextLine());
}catch(ParseException pe)
{
System.out.println(pe.getMessage());
return;
}
int designationCode=scan.nextInt();
scan.nextLine();
String panCardNumber=scan.nextLine();
String aadharCardNumber=scan.nextLine();
EmployeeDTOInterface employee=new EmployeeDTO();
employee.setEmployeeID(employeeID);
employee.setName(name);
if(gender=='M')employee.setGender(GENDER.Male);
else employee.setGender(GENDER.Female);
employee.setSalary(salary);
employee.setIsIndian(isIndian);
employee.setDateOfBirth(dateOfBirth);
employee.setDesignationCode(designationCode);
employee.setPANCardNumber(panCardNumber);
employee.setAadharCardNumber(aadharCardNumber);
try
{
manager.update(employee);
System.out.println("Employee updated with ID("+employee.getEmployeeID()+")");
}catch(BLException blexception)
{
if(blexception.hasGenericException())System.out.println(blexception.getGenericException());
if(blexception.hasPropertyException("EmployeeID"))System.out.println(blexception.getPropertyException("EmployeeID"));
if(blexception.hasPropertyException("Name"))System.out.println(blexception.getPropertyException("Name"));
if(blexception.hasPropertyException("Gender"))System.out.println(blexception.getPropertyException("Gender"));
if(blexception.hasPropertyException("Salary"))System.out.println(blexception.getPropertyException("Salary"));
if(blexception.hasPropertyException("DateOfBirth"))System.out.println(blexception.getPropertyException("DateOfBirth"));
if(blexception.hasPropertyException("DesignationCode"))System.out.println(blexception.getPropertyException("DesignationCode"));
if(blexception.hasPropertyException("PANCardNumber"))System.out.println(blexception.getPropertyException("PANCardNumber"));
if(blexception.hasPropertyException("AadharCardNumber"))System.out.println(blexception.getPropertyException("AadharCardNumber"));
}
}
else if(ch==4)
{
Set<EmployeeDTOInterface> set=null;
EmployeeManagerInterface employeeManager=new EmployeeManager();
try
{
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
int x=1;
set=employeeManager.getAll();
Iterator<EmployeeDTOInterface> iter;
iter=set.iterator();
while(iter.hasNext())
{
EmployeeDTOInterface employeeDTO=iter.next();
System.out.println("\n\nEmployee "+x+" details");
System.out.println("ID          : "+employeeDTO.getEmployeeID());
System.out.println("Name        : "+employeeDTO.getName());
System.out.println("Gender      : "+employeeDTO.getGender());
System.out.println("Indian      : "+employeeDTO.getIsIndian());
System.out.println("Salary      : "+employeeDTO.getSalary().toPlainString());
System.out.println("DOB         : "+sdf.format(employeeDTO.getDateOfBirth()));
System.out.println("Design_code : "+employeeDTO.getDesignationCode());
System.out.println("AadharNo    : "+employeeDTO.getAadharCardNumber());
System.out.println("PAN_No      : "+employeeDTO.getPANCardNumber());
x++;
}
}catch(BLException blexception)
{
System.out.println(blexception.getGenericException());
}
}
else if(ch==5)
{
System.out.print("Enter the designation code : ");
int designationCode=scan.nextInt();
Set<EmployeeDTOInterface> set=null;
EmployeeManagerInterface employeeManager=new EmployeeManager();
try
{
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
int x=1;
set=employeeManager.getByDesignation(designationCode);
Iterator<EmployeeDTOInterface> iter;
iter=set.iterator();
while(iter.hasNext())
{
EmployeeDTOInterface employeeDTO=iter.next();
System.out.println("\n\nEmployee "+x+" details");
System.out.println("ID          : "+employeeDTO.getEmployeeID());
System.out.println("Name        : "+employeeDTO.getName());
System.out.println("Gender      : "+employeeDTO.getGender());
System.out.println("Indian      : "+employeeDTO.getIsIndian());
System.out.println("Salary      : "+employeeDTO.getSalary().toPlainString());
System.out.println("DOB         : "+sdf.format(employeeDTO.getDateOfBirth()));
System.out.println("Design_code : "+employeeDTO.getDesignationCode());
System.out.println("AadharNo    : "+employeeDTO.getAadharCardNumber());
System.out.println("PAN_No      : "+employeeDTO.getPANCardNumber());
x++;
}
}catch(BLException blexception)
{
if(blexception.hasPropertyException("DesignationCode"))System.out.println(blexception.getPropertyException("DesignationCode"));
}
}
else if(ch==6)
{
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
System.out.print("Enter the employeeID : ");
String employeeID=scan.nextLine();
try
{
EmployeeDTOInterface employeeDTO;
employeeDTO=manager.getByEmployeeID(employeeID);
System.out.println("\n\nEmployee details");
System.out.println("ID          : "+employeeDTO.getEmployeeID());
System.out.println("Name        : "+employeeDTO.getName());
System.out.println("Gender      : "+employeeDTO.getGender());
System.out.println("Indian      : "+employeeDTO.getIsIndian());
System.out.println("Salary      : "+employeeDTO.getSalary().toPlainString());
System.out.println("DOB         : "+sdf.format(employeeDTO.getDateOfBirth()));
System.out.println("Design_code : "+employeeDTO.getDesignationCode());
System.out.println("AadharNo    : "+employeeDTO.getAadharCardNumber());
System.out.println("PAN_No      : "+employeeDTO.getPANCardNumber());
}catch(BLException blexception)
{
if(blexception.hasGenericException())System.out.println(blexception.getGenericException());
if(blexception.hasPropertyException("EmployeeID"))System.out.println(blexception.getPropertyException("EmployeeID"));
if(blexception.hasPropertyException("Name"))System.out.println(blexception.getPropertyException("Name"));
if(blexception.hasPropertyException("Gender"))System.out.println(blexception.getPropertyException("Gender"));
if(blexception.hasPropertyException("Salary"))System.out.println(blexception.getPropertyException("Salary"));
if(blexception.hasPropertyException("DateOfBirth"))System.out.println(blexception.getPropertyException("DateOfBirth"));
if(blexception.hasPropertyException("DesignationCode"))System.out.println(blexception.getPropertyException("DesignationCode"));
if(blexception.hasPropertyException("PANCardNumber"))System.out.println(blexception.getPropertyException("PANCardNumber"));
if(blexception.hasPropertyException("AadharCardNumber"))System.out.println(blexception.getPropertyException("AadharCardNumber"));
}
}
else if(ch==7)
{
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
System.out.print("Enter the PANCard Number : ");
String employeePANCardNumber=scan.nextLine();
try
{
EmployeeDTOInterface employeeDTO;
employeeDTO=manager.getByPANCardNumber(employeePANCardNumber);
System.out.println("\n\nEmployee details");
System.out.println("ID          : "+employeeDTO.getEmployeeID());
System.out.println("Name        : "+employeeDTO.getName());
System.out.println("Gender      : "+employeeDTO.getGender());
System.out.println("Indian      : "+employeeDTO.getIsIndian());
System.out.println("Salary      : "+employeeDTO.getSalary().toPlainString());
System.out.println("DOB         : "+sdf.format(employeeDTO.getDateOfBirth()));
System.out.println("Design_code : "+employeeDTO.getDesignationCode());
System.out.println("AadharNo    : "+employeeDTO.getAadharCardNumber());
System.out.println("PAN_No      : "+employeeDTO.getPANCardNumber());
}catch(BLException blexception)
{
if(blexception.hasGenericException())System.out.println(blexception.getGenericException());
if(blexception.hasPropertyException("EmployeeID"))System.out.println(blexception.getPropertyException("EmployeeID"));
if(blexception.hasPropertyException("Name"))System.out.println(blexception.getPropertyException("Name"));
if(blexception.hasPropertyException("Gender"))System.out.println(blexception.getPropertyException("Gender"));
if(blexception.hasPropertyException("Salary"))System.out.println(blexception.getPropertyException("Salary"));
if(blexception.hasPropertyException("DateOfBirth"))System.out.println(blexception.getPropertyException("DateOfBirth"));
if(blexception.hasPropertyException("DesignationCode"))System.out.println(blexception.getPropertyException("DesignationCode"));
if(blexception.hasPropertyException("PANCardNumber"))System.out.println(blexception.getPropertyException("PANCardNumber"));
if(blexception.hasPropertyException("AadharCardNumber"))System.out.println(blexception.getPropertyException("AadharCardNumber"));
}
}
else if(ch==8)
{
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
System.out.print("Enter the AadharCard Number : ");
String employeeAadharCardNumber=scan.nextLine();
try
{
EmployeeDTOInterface employeeDTO;
employeeDTO=manager.getByAadharCardNumber(employeeAadharCardNumber);
System.out.println("\n\nEmployee details");
System.out.println("ID          : "+employeeDTO.getEmployeeID());
System.out.println("Name        : "+employeeDTO.getName());
System.out.println("Gender      : "+employeeDTO.getGender());
System.out.println("Indian      : "+employeeDTO.getIsIndian());
System.out.println("Salary      : "+employeeDTO.getSalary().toPlainString());
System.out.println("DOB         : "+sdf.format(employeeDTO.getDateOfBirth()));
System.out.println("Design_code : "+employeeDTO.getDesignationCode());
System.out.println("AadharNo    : "+employeeDTO.getAadharCardNumber());
System.out.println("PAN_No      : "+employeeDTO.getPANCardNumber());
}catch(BLException blexception)
{
if(blexception.hasGenericException())System.out.println(blexception.getGenericException());
if(blexception.hasPropertyException("EmployeeID"))System.out.println(blexception.getPropertyException("EmployeeID"));
if(blexception.hasPropertyException("Name"))System.out.println(blexception.getPropertyException("Name"));
if(blexception.hasPropertyException("Gender"))System.out.println(blexception.getPropertyException("Gender"));
if(blexception.hasPropertyException("Salary"))System.out.println(blexception.getPropertyException("Salary"));
if(blexception.hasPropertyException("DateOfBirth"))System.out.println(blexception.getPropertyException("DateOfBirth"));
if(blexception.hasPropertyException("DesignationCode"))System.out.println(blexception.getPropertyException("DesignationCode"));
if(blexception.hasPropertyException("PANCardNumber"))System.out.println(blexception.getPropertyException("PANCardNumber"));
if(blexception.hasPropertyException("AadharCardNumber"))System.out.println(blexception.getPropertyException("AadharCardNumber"));
}
}
else if(ch==9)
{
System.out.print("Enter the employeeID : ");
String employeeID=scan.nextLine();
try
{
System.out.println(manager.employeeIDExists(employeeID));
}catch(BLException blexception)
{
if(blexception.hasGenericException())System.out.println(blexception.getGenericException());
if(blexception.hasPropertyException("EmployeeID"))System.out.println(blexception.getPropertyException("EmployeeID"));
}
}
else if(ch==10)
{
System.out.print("Enter the PANCard Number : ");
String employeePANCardNumber=scan.nextLine();
try
{
System.out.println(manager.panCardNumberExists(employeePANCardNumber));
}catch(BLException blexception)
{
if(blexception.hasGenericException())System.out.println(blexception.getGenericException());
if(blexception.hasPropertyException("PANCardNumber"))System.out.println(blexception.getPropertyException("PANCardNumber"));
}
}
else if(ch==11)
{
System.out.print("Enter the AadharCard Number : ");
String employeeAadharCardNumber=scan.nextLine();
try
{
System.out.println(manager.aadharCardNumberExists(employeeAadharCardNumber));
}catch(BLException blexception)
{
if(blexception.hasGenericException())System.out.println(blexception.getGenericException());
if(blexception.hasPropertyException("AadharCardNumber"))System.out.println(blexception.getPropertyException("AadharCardNumber"));
}
}
else if(ch==12)
{
try
{
System.out.println("Total Employees count is : "+manager.getCount());
}catch(BLException blexception)
{
if(blexception.hasGenericException())System.out.println(blexception.getGenericException());
}
}
else if(ch==13)
{
System.out.print("Enter the Designation Code : ");
int designationCode=scan.nextInt();
scan.nextLine();
try
{
System.out.println("Total count of employees with designation("+designationCode+") is "+manager.getCountByDesignation(designationCode));
}catch(BLException blexception)
{
if(blexception.hasGenericException())System.out.println(blexception.getGenericException());
if(blexception.hasPropertyException("DesignationCode"))System.out.println(blexception.getPropertyException("DesignationCode"));
}
}
else if(ch==14)
{
System.out.print("Enter the Designation Code : ");
int designationCode=scan.nextInt();
scan.nextLine();
try
{
System.out.println(manager.designationAlloted(designationCode));
}catch(BLException blexception)
{
if(blexception.hasGenericException())System.out.println(blexception.getGenericException());
if(blexception.hasPropertyException("DesignationCode"))System.out.println(blexception.getPropertyException("DesignationCode"));
}
}
else if(ch==15)break;
}
else
{
System.out.println("Invalid choice");
continue;
}
}
}
}


/*
public void add(EmployeeDTOInterface employeeDTO) throws BLException;
public void delete(String employeeID) throws BLException;
public void update(EmployeeDTOInterface employeeDTO) throws BLException;
public Set<EmployeeDTOInterface> getAll() throws BLException;
public Set<EmployeeDTOInterface> getByDesignation(int code) throws BLException;
public EmployeeDTOInterface getByEmployeeID(String employeeID) throws BLException;
public EmployeeDTOInterface getByPANCardNumber(String panCardNumber) throws BLException;
public EmployeeDTOInterface getByAadharCardNumber(String aadharCardNumber) throws BLException;
public boolean employeeIDExists(String employeeID) throws BLException;
public boolean panCardNumberExists(String panCardNumber) throws BLException;
public boolean aadharCardNumberExists(String aadharCardNumber) throws BLException;
public int getCount() throws BLException;
public int getCountByDesignation(int code) throws BLException;
public boolean designationAlloted(int code) throws BLException;
*/