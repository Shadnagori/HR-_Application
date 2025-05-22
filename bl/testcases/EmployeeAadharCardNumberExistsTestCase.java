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
System.out.println(employeeDAO.aadharCardNumberExists(gg[0]));
}catch(DAOException daoexception)
{
System.out.println(daoexception.getMessage());
}
}
}