package com.org.util.hr.dl.interfaces;
import com.org.util.hr.dl.dto.*;
import com.org.util.hr.dl.exceptions.*;
import java.util.*;
public interface EmployeeDAOInterface
{
public void add(EmployeeDTOInterface employeeDTO) throws DAOException;
public void delete(String employeeID) throws DAOException;
public void update(EmployeeDTOInterface employeeDTO) throws DAOException;
public Set<EmployeeDTOInterface> getAll() throws DAOException;
public Set<EmployeeDTOInterface> getByDesignation(int code) throws DAOException;
public EmployeeDTOInterface getByEmployeeID(String employeeID) throws DAOException;
public EmployeeDTOInterface getByPANCardNumber(String panCardNumber) throws DAOException;
public EmployeeDTOInterface getByAadharCardNumber(String aadharCardNumber) throws DAOException;
public boolean employeeIDExists(String employeeID) throws DAOException;
public boolean panCardNumberExists(String panCardNumber) throws DAOException;
public boolean aadharCardNumberExists(String aadharCardNumber) throws DAOException;
public int getCount() throws DAOException;
public int getCountByDesignation(int code) throws DAOException;
public boolean designationAlloted(int code) throws DAOException;
}