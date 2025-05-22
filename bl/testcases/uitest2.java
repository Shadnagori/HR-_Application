import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import com.org.util.hr.bl.exceptions.*;
import com.org.util.hr.bl.interfaces.*;
import com.org.util.hr.bl.dao.*;
import com.org.util.hr.bl.dto.*;
import java.util.*;

class DesignationModel extends AbstractTableModel
{
private java.util.List<DesignationDTOInterface> arrayList; 
String titles[];
Class columnClassNames[];
DesignationModel()
{
titles=new String[2];
titles[0]="S.No";
titles[1]="Designation";
columnClassNames=new Class[2];
try
{
columnClassNames[0]=Class.forName("java.util.Integer");
columnClassNames[1]=Class.forName("java.util.String");
}catch(ClassNotFoundException cnfe)
{
}
populateDataStructure();
}
private void populateDataStructure()
{
DesignationManagerInterface designationManager=new DesignationManager();
Set<DesignationDTOInterface> treeSet=null;
try
{
treeSet=designationManager.getDesignations();
arrayList=new ArrayList<>(treeSet);
}catch(BLException blexception)
{
if(blexception.hasGenericException())System.out.println(blexception.getGenericException());
if(blexception.hasPropertyException("code"))System.out.println(blexception.getPropertyException("code"));
if(blexception.hasPropertyException("title"))System.out.println(blexception.getPropertyException("title"));
}
}


public int getColumnCount()
{
return titles.length;
}

public int getRowCount()
{
return arrayList.size();
}

public String getColumnName(int columnIndex)
{
return titles[columnIndex];
}


public Class getColumnClass(int columnIndex)
{
return columnClassNames[columnIndex];
}

public Object getValueAt(int rowIndex,int columnIndex)
{
if(columnIndex==0)return rowIndex+1;
else
{
return arrayList.get(rowIndex);
}
}

public boolean isCellEditable(int rowIndex,int columnIndex)
{
return false;
}

//APPLICATION SPECIFIC METHODS

int getIndexOf(DesignationDTOInterface designationInterface)
{

}

DesignationDTOInterface getByTitle(String title)
{

}

}
class Main
{
public static void main(String gg[])
{
DesignationModel d=new DesignationModel();
}
}
