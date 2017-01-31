package br.ufal.ic.forbile.autoria.beans;

import  br.ufal.ic.forbile.autoria.core.*;
import br.ufal.ic.forbile.autoria.core.util.Constant;
import  br.ufal.ic.forbile.autoria.persistence.*;

import  java.util.*;
import  javax.faces.model.SelectItem;
//import  com.hp.hpl.jena.ontology.*;
//import  com.hp.hpl.jena.rdf.model.*;


public class AssistentClassProperty extends SessionBean {
       
    private AssistentStepClass stepClass;
    
    private String    propertyName;
    private String    propertyURI;
    
    private String    fieldName;
    private int       fieldType;
    private int       fieldMaxSize;
    
    private String    value;
    private ArrayList values = new ArrayList();
    private boolean   hasValues;
    private ArrayList selectedValues = new ArrayList();
    
    private String    numOrderAppear;
    private boolean   isNull = false;
    
    private AssistentStepClassProperty scp;
    
    //-------------------------------------------------------------------------
    
    public AssistentClassProperty() {
        
    }
    
    public AssistentClassProperty(int idStepClassProperty) {
        
        AssistentStepClassProperty ascp = new AssistentStepClassProperty(
                idStepClassProperty);
        
        this.scp = ascp;
        String tmp[] = ascp.getNameProperty().split("#");
        setPropertyName(tmp[1]);
        setPropertyURI(ascp.getNameProperty());
        setFieldName(ascp.getNameField());
        setFieldType(ascp.getFieldType().getIdFieldType());
        setFieldMaxSize(ascp.getFieldType().getDefaultSize());
        setNumOrderAppear(String.valueOf(ascp.getNumOrderAppear()));
        setIsNull(ascp.getIsNull().equals("0")? true : false );
        
        this.getPropInstances();
    }
    
    public AssistentClassProperty(String ontProperty) {
        
        String property[] = ontProperty.split("#");
        this.propertyName = property[1];
        this.propertyURI  = ontProperty;
        
        //this.getInstances();
    }
    
    private void getPropInstances() {
                
        OWLData owl = new OWLData();
        owl.setOntology(Integer.parseInt((String)getSession().getAttribute("idOntology")));
        
        List orderIndividuals = owl.getSomeValuesFromRestriction(this.propertyURI);

        
        if (orderIndividuals.isEmpty()) {
            this.hasValues = false;
            this.values.add(new SelectItem("",
                    Constant.ASSITENT_CLASS_PROPERTY_MSG1));
        } else {
            this.hasValues = true;
            for(int j=0; j<orderIndividuals.size(); j++) {

                String uri  = (String) orderIndividuals.get(j);
                String name[] = uri.split("#");
                this.values.add(new SelectItem(uri,name[1]));
            }
        } 
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
    
    public int getFieldType() {
        return this.fieldType;
    }
    
    public void setFieldType(int fieldType) {
        this.fieldType = fieldType;
    }
    
    //-------------------------------------------------------------------------
    
    public int getFieldMaxSize() {
        return this.fieldMaxSize;
    }
    
    public void setFieldMaxSize(int fieldMaxSize) {
        this.fieldMaxSize = fieldMaxSize;
    }
    
    //-------------------------------------------------------------------------
    
    public String getValue() {
        return this.value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
    //-------------------------------------------------------------------------
    
    public ArrayList getValues() {
        return this.values;
    }
    
    public void setValues(ArrayList values) {
        this.values = values;
    }
    
    //-------------------------------------------------------------------------
    
    public ArrayList getSelectedValues() {
        return this.selectedValues;
    }
    
    public void setSelectedValues(ArrayList selectedValues) {
        this.selectedValues = selectedValues;
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
}
