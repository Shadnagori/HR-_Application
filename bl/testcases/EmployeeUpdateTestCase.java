import com.org.util.hr.dl.exceptions.*;
import com.org.util.hr.dl.interfaces.*;
import com.org.util.hr.dl.dto.*;
import com.org.util.hr.dl.dao.*;
import com.org.util.enums.*;
import java.util.*;
import java.math.*;
import java.text.*;


class psp
{


public static void display()
{
Set<EmployeeDTOInterface> set=null;
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
try
{
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
int x=1;
set=employeeDAO.getAll();
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
}catch(DAOException daoexception)
{
System.out.println(daoexception.getMessage());
}
}



public static void main(String gg[])
{
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
try
{
EmployeeDTOInterface employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeID(gg[0]);
employeeDTO.setName(gg[1]);
if(gg[2].charAt(0)!='M'&&gg[2].charAt(0)!='m'&&gg[2].charAt(0)!='f'&&gg[2].charAt(0)!='F')
{
System.out.println("Gender required(M/F || m/f)");
return;
}
if(gg[2].charAt(0)=='M'||gg[2].charAt(0)=='m')employeeDTO.setGender(GENDER.Male);
else employeeDTO.setGender(GENDER.Female);
employeeDTO.setSalary(new BigDecimal(gg[3]));
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
try
{
employeeDTO.setDateOfBirth(sdf.parse(gg[4]));
}catch(ParseException pe)
{
System.out.println(pe.getMessage());
return;
}
employeeDTO.setIsIndian(Boolean.parseBoolean(gg[5]));
employeeDTO.setDesignationCode(Integer.parseInt(gg[6]));
employeeDTO.setPANCardNumber(gg[7]);
employeeDTO.setAadharCardNumber(gg[8]);
employeeDAO.update(employeeDTO);
System.out.println("Employee Updated");
System.out.println("List of Employees");
display();
}catch(DAOException daoexception)
{
System.out.println(daoexception.getMessage());
}
}
}