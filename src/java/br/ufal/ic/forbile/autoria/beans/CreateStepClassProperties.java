package br.ufal.ic.forbile.autoria.beans;

import  br.ufal.ic.forbile.autoria.core.*;
import  br.ufal.ic.forbile.autoria.persistence.*;

import  java.util.*;
import  com.hp.hpl.jena.ontology.*;
import  com.hp.hpl.jena.rdf.model.*;


public class CreateStepClassProperties extends SessionBean {
       
    private AssistentStepClass stepClass;
    
    private String    propertyName;
    private String    propertyURI;
    private String    fieldName;
    private String    fieldType;
    private String    numOrderAppear;
    private boolean   isNull = false;
    
    private AssistentStepClassProperty scp;
    
    //-------------------------------------------------------------------------
    
    public CreateStepClassProperties() {
        
    }
    
    public CreateStepClassProperties(int idStepClassProperty) {
        
        AssistentStepClassProperty ascp = new AssistentStepClassProperty(
                idStepClassProperty);
        
        this.scp = ascp;
        String tmp[] = ascp.getNameProperty().split("#");
        setPropertyName(tmp[1]);
        setPropertyURI(ascp.getNameProperty());
        setFieldName(ascp.getNameField());
        setFieldType(String.valueOf(ascp.getFieldType().getIdFieldType()));
        setNumOrderAppear(String.valueOf(ascp.getNumOrderAppear()));
        setIsNull(ascp.getIsNull().equals("0")? true : false );
    }
    
    public CreateStepClassProperties(String ontProperty) {
        
        String property[] = ontProperty.split("#");
        this.propertyName = property[1];
        this.propertyURI  = ontProperty;
    }
    
    //-------------------------------------------------------------------------
    
    public AssistentStepClass getStepClass() {
        return this.stepClass;
    }
    
    public void setStepClass(AssistentStepClass asc) {
        this.stepClass = asc;
    }
    
    //-------------------------------------------------------------------------
    
    public String getPropertyName() {
        return this.propertyName;
    }
    
    public void setPropertyName(String name) {
        this.propertyName = name;
    }
    
    //-------------------------------------------------------------------------
    
    public String getPropertyURI() {
        return this.propertyURI;
    }
    
    public void setPropertyURI(String URI) {
        this.propertyURI = URI;
    }
    
    //-------------------------------------------------------------------------
    
    public String getFieldName() {
        return this.fieldName;
    }
    
    public void setFieldName(String name) {
        this.fieldName = name;
    }
    
    //-------------------------------------------------------------------------
    
    public String getFieldType() {
        return this.fieldType;
    }
    
    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }
    
    //-------------------------------------------------------------------------
    
    public String getNumOrderAppear() {
        return this.numOrderAppear;
    }
    
    public void setNumOrderAppear(String numOrderAppear) {
        this.numOrderAppear = numOrderAppear;
    }
    
    //-------------------------------------------------------------------------
    
    public boolean getIsNull() {
        return this.isNull;
    }
    
    public void setIsNull(boolean isNull) {
        this.isNull = isNull;
    }
    
    //-------------------------------------------------------------------------
    
    public void saveNew() {
        
        AssistentStepClassProperty ascp = new AssistentStepClassProperty();
        
        ascp.setNameProperty(this.getPropertyURI());
        ascp.setNameField(this.getFieldName());
        ascp.setFieldType(new FieldType(Integer.parseInt(this.getFieldType())));
        ascp.setNumOrderAppear(Integer.parseInt(this.getNumOrderAppear()));
        ascp.setStepClass(this.getStepClass());
        ascp.setIsNull(this.getIsNull());
        ascp.save();
        
    }
    
    //-------------------------------------------------------------------------
    
    public void updateThis() {
        
        this.scp.setNameProperty(this.getPropertyURI());
        this.scp.setNameField(this.getFieldName());
        this.scp.setFieldType(new FieldType(Integer.parseInt(this.getFieldType())));
        this.scp.setNumOrderAppear(Integer.parseInt(this.getNumOrderAppear()));
        this.scp.setStepClass(this.getStepClass());
        this.scp.setIsNull(this.getIsNull());
        this.scp.update();
    }
    
    //-------------------------------------------------------------------------
}
