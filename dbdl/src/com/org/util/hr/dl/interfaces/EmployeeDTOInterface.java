package com.org.util.hr.dl.interfaces;
import com.org.util.enums.*;
import java.math.*;
import java.util.*;
public interface EmployeeDTOInterface extends Comparable<EmployeeDTOInterface>,java.io.Serializable
{
public void setEmployeeID(java.lang.String employeeID);
public java.lang.String getEmployeeID();
public void setName(java.lang.String name);
public java.lang.String getName();
public void setGender(GENDER gender);
public char getGender();
public void setAadharCardNumber(java.lang.String aadharCardNumber);
public java.lang.String getAadharCardNumber();
public void setPANCardNumber(java.lang.String panCardNumber);
public java.lang.String getPANCardNumber();
public void setSalary(java.math.BigDecimal salary);
public java.math.BigDecimal getSalary();
public void setDateOfBirth(java.util.Date dateOfBirth);
public java.util.Date getDateOfBirth();
public void setIsIndian(boolean isIndian);
public boolean getIsIndian();
public void setDesignationCode(int designationCode);
public int getDesignationCode();
}
