import com.org.util.hr.dl.exceptions.*;
import com.org.util.hr.dl.interfaces.*;
import com.org.util.hr.dl.dto.*;
import com.org.util.hr.dl.dao.*;
import com.org.util.enums.*;
import java.util.*;
import java.math.*;
import java.text.*;
class Main
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

EmployeeDAOInterface employeeDAO=new EmployeeDAO();
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
employeeDAO.add(employeeDTO);
System.out.println("Employee added against ID : "+employeeDTO.getEmployeeID());
}catch(DAOException daoexception)
{
System.out.println(daoexception.getMessage());
}
}
}