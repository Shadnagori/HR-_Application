import com.org.util.hr.dl.exceptions.*;
import com.org.util.hr.dl.interfaces.*;
import com.org.util.hr.dl.dto.*;
import com.org.util.hr.dl.dao.*;
import java.util.*;
import java.math.*;
import java.text.*;


class psp
{
public static void main(String gg[])
{
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
try
{
EmployeeDTOInterface employeeDTO=employeeDAO.getByAadharCardNumber(gg[0]);
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
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
}catch(DAOException daoexception)
{
System.out.println(daoexception.getMessage());
}
}
}