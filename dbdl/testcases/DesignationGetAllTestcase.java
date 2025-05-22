import com.org.util.hr.dl.dao.*;
import com.org.util.hr.dl.dto.*;
import com.org.util.hr.dl.exceptions.*;
import com.org.util.hr.dl.interfaces.*;
import java.util.*;
class Main
{
public static void main(String gg[])
{
DesignationDAOInterface designationDAO=new DesignationDAO();
try
{
TreeSet<DesignationDTOInterface> treeSet=designationDAO.getAll();
treeSet.forEach((j)->{System.out.println("Code : "+j.getCode()+","+"Title : "+j.getTitle());});
}catch(DAOException daoexception)
{
System.out.println(daoexception.getMessage());
}
}
}