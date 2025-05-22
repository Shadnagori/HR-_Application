import com.org.util.hr.dl.exceptions.*;
import com.org.util.hr.dl.interfaces.*;
import com.org.util.hr.dl.dto.*;
import com.org.util.hr.dl.dao.*;
import java.util.*;
import java.math.*;
import java.text.*;


class EmployeeDTOGetAllTestCase
{
public static void main(String gg[])
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
}