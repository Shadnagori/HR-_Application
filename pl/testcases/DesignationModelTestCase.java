import com.org.util.hr.pl.model.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class DesignationModelTestCase extends JFrame
{
private JTable table;
private JScrollPane jsp;
private Container container;
DesignationModelTestCase()
{
table=new JTable(new DesignationModel());
table.setRowHeight(20);
table.setGridColor(Color.gray);
jsp=new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
container=getContentPane();
container.setLayout(new BorderLayout());
container.add(jsp);
this.setLocation(20,20);
this.setSize(600,600);
this.setVisible(true);
this.setDefaultCloseOperation(EXIT_ON_CLOSE);
}
}
class Main
{
public static void main(String gg[])
{
DesignationModelTestCase d=new DesignationModelTestCase();
}
}