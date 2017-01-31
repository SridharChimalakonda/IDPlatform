package br.ufal.ic.forbile.autoria.core;

import  br.ufal.ic.forbile.autoria.persistence.*;
import  br.ufal.ic.forbile.autoria.exceptions.*;
import  java.util.*;

import  com.hp.hpl.jena.ontology.*;
import  com.hp.hpl.jena.rdf.model.*;
import  com.hp.hpl.jena.util.iterator.Filter;


public class AssistentStepClass {
    
    private int             idStepClass;
    private AssistentStep   assistentStep;
    private int             numOrderAppear;
    private String          className;
    private String          objectName;
    
    private ArrayList       stepClassProperties;

       
    public AssistentStepClass () {
        
    }
    
    public AssistentStepClass (int idStepClass) {
        
        this.setIdStepClass(idStepClass);
        AssistentData   assData = new AssistentData();
        
        try {
            ArrayList data = assData.getAssistentStepClass(
                    String.valueOf(idStepClass));

            this.setNumOrderAppear(Integer.parseInt((String) data.get(1)));
            this.setClassName((String) data.get(2));
            this.setObjectName((String) data.get(3));

            this.stepClassProperties = new ArrayList();
            for (int i = 0; i < ((ArrayList) data.get(4)).size(); i++) {
                AssistentStepClassProperty ascp = new AssistentStepClassProperty(
                        Integer.parseInt((String) ((ArrayList) data.get(4)).get(i)));
                ascp.setStepClass(this);
                this.stepClassProperties.add(i, ascp);
            }
            
        } catch (PersistenceException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    //-------------------------------------------------------------------------
    
    public int getIdStepClass() {
        return this.idStepClass;
    }
    
    public void setIdStepClass(int idStepClass) {
        this.idStepClass = idStepClass;
    }
    
    //-------------------------------------------------------------------------
    
    public AssistentStep getAssistentStep() {
        return this.assistentStep;
    }
    
    public void setAssistentStep(AssistentStep assistentStep) {
        this.assistentStep = assistentStep;
    }
    
    //-------------------------------------------------------------------------
    
    public int getNumOrderAppear() {
        return this.numOrderAppear;
    }
    
    public void setNumOrderAppear(int numOrderAppear) {
        this.numOrderAppear = numOrderAppear;
    }
    
    //-------------------------------------------------------------------------
    
    public String getClassName() {
        return this.className;
    }
    
    public void setClassName(String name) {
        this.className = name;
    }
    
    //-------------------------------------------------------------------------
    
    public String getObjectName() {
        return this.objectName;
    }
    
    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }
    
    //-------------------------------------------------------------------------
    
    public ArrayList getStepClasseProperties() {
        return this.stepClassProperties;
    }
    
    public void setStepClasseProperties(ArrayList stepClassProperties) {
        this.stepClassProperties = stepClassProperties;
    }
    
    //-------------------------------------------------------------------------
    
    public void save() {
        
        if (getAssistentStep() != null && getNumOrderAppear() != 0 && 
                getClassName() != null && getObjectName() != null) {
            
            AssistentData ad = new AssistentData();
            try {
                setIdStepClass(ad.insertNewAssistentStepClass(
                        String.valueOf(getAssistentStep().getIdAssistentStep()), 
                        String.valueOf(getNumOrderAppear()),
                        getClassName(),getObjectName()));
            } catch (PersistenceException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    //-------------------------------------------------------------------------
    
    public void update() {
        
        if (getIdStepClass() != 0 && getNumOrderAppear() != 0 && 
                getClassName() != null && getObjectName() != null) {
            
            AssistentData ad = new AssistentData();
            try {
                ad.updateAssistentStepClass(
                        String.valueOf(getIdStepClass()), 
                        String.valueOf(getNumOrderAppear()),
                        getObjectName());
            } catch (PersistenceException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    //-------------------------------------------------------------------------
    
    public void delete() {
        
        AssistentData ad = new AssistentData();
        try {
            ad.removeAssistentStepClass(
                    String.valueOf(getIdStepClass()), 
                    String.valueOf(getNumOrderAppear()));
        } catch (PersistenceException ex) {
            ex.printStackTrace();
        }
    }
    
    //-------------------------------------------------------------------------
}
