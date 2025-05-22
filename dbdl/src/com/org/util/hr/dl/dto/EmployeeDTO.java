package com.org.util.hr.dl.dto;
import com.org.util.hr.dl.interfaces.EmployeeDTOInterface;
import java.util.*;
import java.math.*;
import com.org.util.enums.*;
public class EmployeeDTO implements EmployeeDTOInterface
{
private String employeeID;
private String name;
private char gender;
private String aadharCardNumber;
private String panCardNumber;
private BigDecimal salary;
private Date dateOfBirth;
private boolean isIndian;
private int designationCode;

public EmployeeDTO()
{
this.employeeID="";
this.name="";
this.gender=' ';
this.aadharCardNumber="";
this.panCardNumber="";
this.salary=null;
this.dateOfBirth=null;
this.isIndian=false;
this.designationCode=0;
}
public void setEmployeeID(java.lang.String employeeID)
{
this.employeeID=employeeID;
}
public java.lang.String getEmployeeID()
{
return this.employeeID;
}
public void setName(java.lang.String name)
{
this.name=name;
}
public java.lang.String getName()
{
return this.name;
}
public void setGender(GENDER gender)
{
if(gender==GENDER.Male)this.gender='M';
else this.gender='F';
}
public char getGender()
{
return this.gender;
}
public void setAadharCardNumber(java.lang.String aadharCardNumber)
{
this.aadharCardNumber=aadharCardNumber;
}
public java.lang.String getAadharCardNumber()
{
return this.aadharCardNumber;
}
public void setPANCardNumber(java.lang.String panCardNumber)
{
this.panCardNumber=panCardNumber;
}
public java.lang.String getPANCardNumber()
{
return this.panCardNumber;
}
public void setSalary(java.math.BigDecimal salary)
{
this.salary=salary;
}
public java.math.BigDecimal getSalary()
{
return this.salary;
}
public void setDateOfBirth(java.util.Date dateOfBirth)
{
this.dateOfBirth=dateOfBirth;
}
public java.util.Date getDateOfBirth()
{
if(this.dateOfBirth!=null)return new Date(this.dateOfBirth.getTime());
return null;
}
public void setIsIndian(boolean isIndian)
{
this.isIndian=isIndian;
}
public boolean getIsIndian()
{
return this.isIndian;
}
public void setDesignationCode(int designationCode)
{
this.designationCode=designationCode;
}
public int getDesignationCode()
{
return this.designationCode;
}

public int hashCode()
{
return this.employeeID.toUpperCase().hashCode();
}


public boolean equals(Object other)
{
if(!(other instanceof EmployeeDTOInterface))return false;
EmployeeDTOInterface employeeDTO=(EmployeeDTOInterface)other;
if(this.employeeID.equalsIgnoreCase(employeeDTO.getEmployeeID()))return true;
return false;
}


public int compareTo(EmployeeDTOInterface other)
{
return this.employeeID.compareTo(other.getEmployeeID());
}

}