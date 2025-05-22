import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import com.org.util.hr.bl.exceptions.*;
import com.org.util.hr.bl.interfaces.*;
import com.org.util.hr.bl.dao.*;
import com.org.util.hr.bl.dto.*;
import java.util.*;

class DataModel extends AbstractTableModel
{
DesignationManagerInterface designationManager;
TreeSet<DesignationDTOInterface> treeSet;
java.util.List<DesignationDTOInterface> dList;
DataModel()
{
treeSet=null;
dList=null;
designationManager=new DesignationManager();
try
{
treeSet=designationManager.getDesignations();
dList=new ArrayList<>(treeSet);
}catch(BLException blexception)
{
if(blexception.hasGenericException())System.out.println(blexception.getGenericException());
if(blexception.hasPropertyException("code"))System.out.println(blexception.getPropertyException("code"));
if(blexception.hasPropertyException("title"))System.out.println(blexception.getPropertyException("title"));
}
}

public Object getValueAt(int rowIndex,int columnIndex)
{
DesignationDTOInterface designationDTO=dList.get(rowIndex);
if(columnIndex==0)return designationDTO.getCode();
if(columnIndex==1)return designationDTO.getTitle();
return "";
}

public int getColumnCount()
{
return 2;
}

public int getRowCount()
{
return treeSet.size();
}

public String getColumnName(int columnIndex)
{
if(columnIndex==0)return String.valueOf("Code");
if(columnIndex==1)return String.valueOf("Title");
return "";
}

public Class getColumnClass(int columnIndex)
{
Class c=null;
try
{
if(columnIndex==0)c=Class.forName("java.lang.Integer");
if(columnIndex==1)c=Class.forName("java.lang.String");
return c;
}catch(Exception e)
{
}
return c;
}

public boolean isCellEditable(int rowIndex,int columnIndex)
{
return false;
}
}



class swing4 extends JFrame
{
private JTable table;
private JScrollPane jsp;
Container container;
swing4()
{
table=new JTable(new DataModel());
table.setRowHeight(30);
table.setFont(new Font("Seriff",Font.PLAIN,24));
jsp=new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
container=getContentPane();
container.setLayout(new FlowLayout());
container.add(jsp);
int width,height;
width=600;
height=600;
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
int x=(d.width/2)-width/2;
int y=(d.height/2)-height/2;
this.setLocation(x,y);
this.setSize(width,height);
this.setVisible(true);
this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
}
}
class uitest
{
public static void main(String gg[])
{
swing4 s=new swing4();
}
}