package com.org.util.hr.dl.interfaces;
import java.io.Serializable;
import java.util.*;
public interface DesignationDTOInterface extends Comparable<DesignationDTOInterface>,Serializable
{
public void setCode(int code);
public void setTitle(String title);
public int getCode();
public String getTitle();
}