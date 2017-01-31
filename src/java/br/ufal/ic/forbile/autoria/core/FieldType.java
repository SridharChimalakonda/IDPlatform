package br.ufal.ic.forbile.autoria.core;

import  br.ufal.ic.forbile.autoria.persistence.*;
import  br.ufal.ic.forbile.autoria.exceptions.*;
import  java.util.*;


public class FieldType {
    
    private int     idFieldType;
    private String  name;
    private int     defaultSize;
    private String  JSFComponentName;
    
    
    public FieldType() {
        
    }
    
    public FieldType(int idFieldType) {
        
        FieldData    fieldData = new FieldData();
        
        try {
            ArrayList   data    = fieldData.getFieldById(idFieldType);
            
            if (!data.isEmpty()) {
                this.setIdFieldType(Integer.parseInt((String)data.get(0)));
                this.setFieldName((String)data.get(1));
                this.setDefaultSize(Integer.parseInt((String)data.get(2)));
                this.setJSFComponent((String)data.get(3));
            } else {
                this.setIdFieldType(0);
            }
            
        } catch (PersistenceException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    //-------------------------------------------------------------------------
    
    public int getIdFieldType() {
        return this.idFieldType;
    }
    
    public void setIdFieldType(int idFieldType) {
        this.idFieldType = idFieldType;
    }
    
    //-------------------------------------------------------------------------
    
    public String getFieldName() {
        return this.name;
    }
    
    public void setFieldName(String name) {
        this.name = name;
    }
    
    //-------------------------------------------------------------------------
    
    public int getDefaultSize() {
        return this.defaultSize;
    }
    
    public void setDefaultSize(int defaultSize) {
        this.defaultSize = defaultSize;
    }
    
    //-------------------------------------------------------------------------
    
    public String getJSFComponent() {
        return this.JSFComponentName;
    }
    
    public void setJSFComponent(String JSFCompName) {
        this.JSFComponentName = JSFCompName;
    }
    
    //-------------------------------------------------------------------------
}
