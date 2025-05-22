import com.org.util.hr.bl.exceptions.*;
import com.org.util.hr.bl.interfaces.*;
import com.org.util.hr.bl.dao.*;
import com.org.util.hr.bl.dto.*;
import java.util.*;
class DesignationTestCase
{
public static void main(String gg[])
{
DesignationManagerInterface designationManager=new DesignationManager();
byte ch;
Scanner scan=new Scanner(System.in);
while(true)
{
System.out.println("1.Add designation");
System.out.println("2.Get list of designations");
System.out.println("3.Delete designation by code");
System.out.println("4.Update designation");
System.out.println("5.Get designation by code");
System.out.println("6.Get designation by title");
System.out.println("7.Is Designation code exists");
System.out.println("8.Is Designation title exists");
System.out.println("9.Get designation counts");
System.out.println("10.Exit");
System.out.print("Enter your choice : ");
ch=scan.nextByte();
scan.nextLine();
if(ch>=1&&ch<=10)
{
if(ch==1)
{
String title;
DesignationDTOInterface designationDTO=new DesignationDTO();
designationDTO.setCode(0);
System.out.print("Enter the title : ");
title=scan.nextLine();
designationDTO.setTitle(title);
try
{
designationManager.addDesignation(designationDTO);
System.out.println("Designation title ("+title+") added against code "+designationDTO.getCode());
}catch(BLException blexception)
{
if(blexception.hasGenericException())System.out.println(blexception.getGenericException());
if(blexception.hasPropertyException("code"))System.out.println(blexception.getPropertyException("code"));
if(blexception.hasPropertyException("title"))System.out.println(blexception.getPropertyException("title"));
}
}
if(ch==2)
{
TreeSet<DesignationDTOInterface> treeSet=null;
try
{
treeSet=designationManager.getDesignations();
treeSet.forEach((j)->{System.out.println("Code : "+j.getCode()+","+"Title : "+j.getTitle());});
}catch(BLException blexception)
{
if(blexception.hasGenericException())System.out.println(blexception.getGenericException());
if(blexception.hasPropertyException("code"))System.out.println(blexception.getPropertyException("code"));
if(blexception.hasPropertyException("title"))System.out.println(blexception.getPropertyException("title"));
}
}
if(ch==3)
{
int code;
System.out.print("Enter the code : ");
code=scan.nextInt();
scan.nextLine();
try
{
designationManager.deleteDesignation(code);
System.out.println("Designation deleted");
}catch(BLException blexception)
{
if(blexception.hasGenericException())System.out.println(blexception.getGenericException());
if(blexception.hasPropertyException("code"))System.out.println(blexception.getPropertyException("code"));
if(blexception.hasPropertyException("title"))System.out.println(blexception.getPropertyException("title"));
}
}
if(ch==4)
{
int code;
System.out.print("Enter the code against you want updation : ");
code=scan.nextInt();
scan.nextLine();
String title;
System.out.print("Enter the unique title : ");
title=scan.nextLine();
try
{
DesignationDTOInterface designationDTO=new DesignationDTO();
designationDTO.setCode(code);
designationDTO.setTitle(title);
designationManager.updateDesignation(designationDTO);
System.out.println("Designation updated");
}catch(BLException blexception)
{
if(blexception.hasGenericException())System.out.println(blexception.getGenericException());
if(blexception.hasPropertyException("code"))System.out.println(blexception.getPropertyException("code"));
if(blexception.hasPropertyException("title"))System.out.println(blexception.getPropertyException("title"));
}
}
if(ch==5)
{
int code;
System.out.print("Enter the code : ");
code=scan.nextInt();
scan.nextLine();
try
{
DesignationDTOInterface designationDTO=designationManager.getDesignationByCode(code);
System.out.println("Title against code "+code+" is "+designationDTO.getTitle());
}catch(BLException blexception)
{
if(blexception.hasGenericException())System.out.println(blexception.getGenericException());
if(blexception.hasPropertyException("code"))System.out.println(blexception.getPropertyException("code"));
if(blexception.hasPropertyException("title"))System.out.println(blexception.getPropertyException("title"));
}
}
if(ch==6)
{
String title;
System.out.print("Enter the title : ");
title=scan.nextLine();
try
{
DesignationDTOInterface designationDTO=designationManager.getDesignationByTitle(title);
System.out.println("Code against title ("+title+") is "+designationDTO.getCode());
}catch(BLException blexception)
{
if(blexception.hasGenericException())System.out.println(blexception.getGenericException());
if(blexception.hasPropertyException("code"))System.out.println(blexception.getPropertyException("code"));
if(blexception.hasPropertyException("title"))System.out.println(blexception.getPropertyException("title"));
}
}
if(ch==7)
{
int code;
System.out.print("Enter the code : ");
code=scan.nextInt();
scan.nextLine();
try
{
System.out.println(designationManager.designationCodeExists(code));
}catch(BLException blexception)
{
if(blexception.hasGenericException())System.out.println(blexception.getGenericException());
if(blexception.hasPropertyException("code"))System.out.println(blexception.getPropertyException("code"));
if(blexception.hasPropertyException("title"))System.out.println(blexception.getPropertyException("title"));
}
}
if(ch==8)
{
String title;
System.out.print("Enter the title : ");
title=scan.nextLine();
try
{
System.out.println(designationManager.designationTitleExists(title));
}catch(BLException blexception)
{
if(blexception.hasGenericException())System.out.println(blexception.getGenericException());
if(blexception.hasPropertyException("code"))System.out.println(blexception.getPropertyException("code"));
if(blexception.hasPropertyException("title"))System.out.println(blexception.getPropertyException("title"));
}
}
if(ch==9)
{
try
{
System.out.println("Total Designation count is : "+designationManager.getDesignationCount());
}catch(BLException blexception)
{
if(blexception.hasGenericException())System.out.println(blexception.getGenericException());
if(blexception.hasPropertyException("code"))System.out.println(blexception.getPropertyException("code"));
if(blexception.hasPropertyException("title"))System.out.println(blexception.getPropertyException("title"));
}
}
if(ch==10)break;
}
else
{
System.out.println("Invalid choice : "+ch);
continue;
}
}
}
}