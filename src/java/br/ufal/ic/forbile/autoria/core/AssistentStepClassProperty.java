package br.ufal.ic.forbile.autoria.core;

import  br.ufal.ic.forbile.autoria.persistence.*;
import  br.ufal.ic.forbile.autoria.exceptions.*;
import  java.util.*;

public class AssistentStepClassProperty {

    private int                 idStepClassProperty;
    private AssistentStepClass  stepClass;
    private int                 numOrderAppear;
    private String              name;
    private String              nameField;
    private FieldType           fieldType;
    private int                 maxSizeField;
    private boolean             isNull;
    
    
    public AssistentStepClassProperty () {
        
    }
    
    public AssistentStepClassProperty(int idStepClassProperty) {
        
        this.setIdStepClassProperty(idStepClassProperty);
        AssistentData   assData = new AssistentData();
        
        try {
            ArrayList data = assData.getAssistentStepClassAtribute(
                    String.valueOf(idStepClassProperty));

            this.setNumOrderAppear(Integer.parseInt((String) data.get(1)));
            this.setNameProperty((String) data.get(2));
            this.setNameField((String) data.get(3));
            this.setFieldType(new FieldType(Integer.parseInt(
                    (String) data.get(4))));
            this.setMaxSizeField(Integer.parseInt((String) data.get(5)));
            this.setIsNull(((String)data.get(6)).equals("0")? true : false);
        } catch (PersistenceException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //-------------------------------------------------------------------------
    
    public int getIdStepClassProperty() {
        return this.idStepClassProperty;
    }
    
    public void setIdStepClassProperty(int idStepClassProperty) {
        this.idStepClassProperty = idStepClassProperty;
    }
    
    //-------------------------------------------------------------------------
    
    public AssistentStepClass getStepClass() {
        return this.stepClass;
    }
    
    public void setStepClass(AssistentStepClass stepClass) {
        this.stepClass = stepClass;
    }
    
    //-------------------------------------------------------------------------
    
    public int getNumOrderAppear() {
        return this.numOrderAppear;
    }
    
    public void setNumOrderAppear(int numOrderAppear) {
        this.numOrderAppear = numOrderAppear;
    }
    
    //-------------------------------------------------------------------------
    
    public String getNameProperty() {
        return this.name;
    }
    
    public void setNameProperty(String name) {
        this.name = name;
    }
    
    //-------------------------------------------------------------------------
    
    public String getNameField() {
        return this.nameField;
    }
    
    public void setNameField(String nameField) {
        this.nameField = nameField;
    }
    
    //-------------------------------------------------------------------------
    
    public FieldType getFieldType() {
        return this.fieldType;
    }
    
    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }
    
    //-------------------------------------------------------------------------
    
    public int getMaxSizeField() {
        return this.maxSizeField;
    }
    
    public void setMaxSizeField(int maxSize) {
        this.maxSizeField = maxSize;
    }
    
    //-------------------------------------------------------------------------
    
    public boolean isNull() {
        return this.isNull;
    }
    
    public String getIsNull() {
        return (this.isNull())? "0": "1";
    }
    
    public void setIsNull(boolean isNull) {
        this.isNull = isNull;
    }
    
    //-------------------------------------------------------------------------
    
    public void save() {
        
        if (getStepClass() != null && getNumOrderAppear() != 0 && 
                getNameProperty() != null && getNameField() != null &&
                getFieldType() != null && getIsNull() != null) {
            
            AssistentData ad = new AssistentData();
            try {
                setIdStepClassProperty(ad.insertNewAssistentStepClassAtribute(
                        String.valueOf(getStepClass().getIdStepClass()), 
                        String.valueOf(getNumOrderAppear()),
                        getNameProperty(),getNameField(),
                        String.valueOf(getFieldType().getIdFieldType()),
                        String.valueOf(getFieldType().getDefaultSize()),
                        getIsNull()));
            } catch (PersistenceException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    //-------------------------------------------------------------------------
    
    public void update() {
        
        if (getIdStepClassProperty() != 0 && getNumOrderAppear() != 0 && 
                getNameProperty() != null && getNameField() != null &&
                getFieldType() != null && getIsNull() != null) {
            
            AssistentData ad = new AssistentData();
            try {
                ad.updateStepClassAtribute(
                        String.valueOf(getIdStepClassProperty()), 
                        String.valueOf(getNumOrderAppear()),
                        getNameField(),
                        String.valueOf(getFieldType().getIdFieldType()),
                        String.valueOf(getFieldType().getDefaultSize()),
                        getIsNull());
            } catch (PersistenceException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    //-------------------------------------------------------------------------
}