package com.org.util.hr.dl.interfaces;
import com.org.util.hr.dl.exceptions.*;
import java.util.*;
public interface DesignationDAOInterface
{
public void add(DesignationDTOInterface designationDTO) throws DAOException;
public void delete(int code) throws DAOException;
public void update(DesignationDTOInterface designationDTO) throws DAOException;
public TreeSet<DesignationDTOInterface> getAll() throws DAOException;
public DesignationDTOInterface getByCode(int code) throws DAOException;
public DesignationDTOInterface getByTitle(String title) throws DAOException;
public boolean codeExists(int code) throws DAOException;
public boolean titleExists(String title) throws DAOException;
public int getCount() throws DAOException;
}
