import com.org.util.hr.dl.dao.*;
import com.org.util.hr.dl.dto.*;
import com.org.util.hr.dl.exceptions.*;
import com.org.util.hr.dl.interfaces.*;
class DesignationAddTestCase
{
public static void main(String gg[])
{
DesignationDAOInterface designationDAO=new DesignationDAO();
DesignationDTOInterface designationDTO=new DesignationDTO();
designationDTO.setCode(0);
designationDTO.setTitle(gg[0]);
try
{
designationDAO.add(designationDTO);
System.out.println("Designation added against code "+designationDTO.getCode());
}catch(DAOException daoexception)
{
System.out.println(daoexception.getMessage());
}
}
}