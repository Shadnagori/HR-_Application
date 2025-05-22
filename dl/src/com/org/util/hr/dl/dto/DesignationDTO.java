package com.org.util.hr.dl.dto;
import com.org.util.hr.dl.interfaces.DesignationDTOInterface;
public class DesignationDTO implements DesignationDTOInterface
{
private int code;
private String title;
public void setCode(int code)
{
this.code=code;
}
public void setTitle(String title)
{
this.title=title;
}
public int getCode()
{
return this.code;
}
public String getTitle()
{
return this.title;
}
public int compareTo(DesignationDTOInterface designationDTO)
{
return this.code-designationDTO.getCode();
}
public boolean equals(Object other)
{
if(!(other instanceof DesignationDTOInterface))return false;
DesignationDTOInterface designationDTO;
designationDTO=(DesignationDTOInterface)other;
return this.code==designationDTO.getCode();
}
public int hashCode()
{
return this.code;
}
}
