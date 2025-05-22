import com.org.util.hr.bl.exceptions.*;
import com.org.util.hr.bl.interfaces.*;
import com.org.util.hr.bl.dto.*;
import com.org.util.hr.bl.dao.*;
import com.org.util.enums.*;
import java.util.*;
import java.math.*;
import java.text.*;
public class EmployeeDTOAddTestCase
{
public static void main(String gg[])
{
System.out.println("Enter the employee Details");
String employeeName;
char employeeGender;
boolean employeeIsIndian=true;
BigDecimal employeeSalary;
String salary;
Date employeeDOB=new Date();
String employeePanCardNumber="";
String employeeAadharCardNumber="";
int employeeDesignationCode;
Scanner scan=new Scanner(System.in);
System.out.print("Enter the name of employee : ");
employeeName=scan.nextLine();
System.out.print("Enter the gender of employee : ");
employeeGender=scan.nextLine().charAt(0);
if(employeeGender!='M'&&employeeGender!='m'&&employeeGender!='f'&&employeeGender!='F')
{
System.out.println("Gender required(M/F ||m/f)");
return;
}
System.out.print("Enter the salary of employee : ");
salary=scan.nextLine();
try
{
employeeSalary=new BigDecimal(salary);
}catch(NumberFormatException nfe)
{
System.out.println("NumberFormatException "+nfe.getMessage());
return;
}
String dateOfBirthString;
System.out.print("Enter the date of birth of employee : ");
dateOfBirthString=scan.nextLine();
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
try
{
employeeDOB=sdf.parse(dateOfBirthString);
}catch(ParseException pe)
{
System.out.println(pe.getMessage());
return;
}

System.out.print("Enter the PANCard Number of employee : ");
employeePanCardNumber=scan.nextLine();

System.out.print("Enter the AadharCard Number of employee : ");
employeeAadharCardNumber=scan.nextLine();

System.out.print("Enter the designation code of employee : ");
employeeDesignationCode=scan.nextInt();
scan.nextLine();

EmployeeManagerInterface employeeManager=new EmployeeManager();
EmployeeDTOInterface employeeDTO=new EmployeeDTO();
employeeDTO.setName(employeeName);
if(employeeGender=='M'||employeeGender=='m')employeeDTO.setGender(GENDER.Male);
else employeeDTO.setGender(GENDER.Female);
employeeDTO.setSalary(employeeSalary);
employeeDTO.setIsIndian(employeeIsIndian);
employeeDTO.setPANCardNumber(employeePanCardNumber);
employeeDTO.setAadharCardNumber(employeeAadharCardNumber);
employeeDTO.setDesignationCode(employeeDesignationCode);
employeeDTO.setDateOfBirth(employeeDOB);

try
{
employeeManager.add(employeeDTO);
System.out.println("Employee added against ID : "+employeeDTO.getEmployeeID());
}catch(BLException blexception)
{
if(blexception.hasExceptions())
{
if(blexception.hasGenericException())System.out.println(blexception.getGenericException());
if(blexception.hasPropertyExceptions())
{
if(blexception.hasPropertyException("Name"))System.out.println(blexception.getPropertyException("Name"));
if(blexception.hasPropertyException("Salary"))System.out.println(blexception.getPropertyException("Salary"));
if(blexception.hasPropertyException("DateOfBirth"))System.out.println(blexception.getPropertyException("DateOfBirth"));
if(blexception.hasPropertyException("DesignationCode"))System.out.println(blexception.getPropertyException("DesignationCode"));
if(blexception.hasPropertyException("PANCardNumber"))System.out.println(blexception.getPropertyException("PANCardNumber"));
if(blexception.hasPropertyException("AadharCardNumber"))System.out.println(blexception.getPropertyException("AadharCardNumber"));
}
}
}
}
}