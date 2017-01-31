package br.ufal.ic.forbile.autoria.beans;

import  br.ufal.ic.forbile.autoria.core.*;
import  br.ufal.ic.forbile.autoria.persistence.*;

import  java.util.*;
import  javax.faces.model.SelectItem;
import  javax.faces.model.DataModel;
import  javax.faces.model.ListDataModel;
//import  com.hp.hpl.jena.ontology.*;
//import  com.hp.hpl.jena.rdf.model.*;


public class CreateStepClasses extends SessionBean {
    
    private AssistentStep   assistentStep;
    private String          className;
    private String          classURI;
    private String          numOrderAppear;
    private String          objectName;
    
    private DataModel       properties;
    private ArrayList       orderAppearPropList    = new ArrayList();
    
    private AssistentStepClass stepClass;
    
    //-------------------------------------------------------------------------
    
    public CreateStepClasses() {
        
    }
    
    public CreateStepClasses(int idStepClass) {
        
        AssistentStepClass asc = new AssistentStepClass(idStepClass);
        
        this.stepClass = asc;
        setAssistentStep(asc.getAssistentStep());
        String tmp[] = asc.getClassName().split("#");
        setClassName(tmp[1]);
        setClassURI(asc.getClassName());
        setNumOrderAppear(String.valueOf(asc.getNumOrderAppear()));
        setObjectName(asc.getObjectName());
        
        List propertiesList = new ArrayList();
        int x = 0;
        for ( ; x < asc.getStepClasseProperties().size(); x++) {
            propertiesList.add(new CreateStepClassProperties(
                    ((AssistentStepClassProperty)asc.getStepClasseProperties().get(x)).
                    getIdStepClassProperty()));
        }

        this.properties = new ListDataModel(propertiesList);
        
        for(int j=1; j<=x; j++) {
            this.orderAppearPropList.add(new SelectItem(
                    String.valueOf(j),String.valueOf(j)));
        }
    }
    
    public CreateStepClasses(String ontClassURI, OWLData ontology) {
        
        List allProps = ontology.getClassProperties(ontClassURI);

        List propertiesList = new ArrayList();
        int x = 0;
        for ( ; x < allProps.size(); x++) {
            propertiesList.add(new CreateStepClassProperties(
                    (String)allProps.get(x)));
        }

        this.className  = ontology.getClassShortName(ontClassURI);
        this.classURI   = ontClassURI;
        
        this.properties = new ListDataModel(propertiesList);
        
        for(int j=1; j<=x; j++) {
            this.orderAppearPropList.add(new SelectItem(
                    String.valueOf(j),String.valueOf(j)));
        }
    }
    
    //-------------------------------------------------------------------------
    
    public AssistentStep getAssistentStep() {
        return this.assistentStep;
    }
    
    public void setAssistentStep(AssistentStep as) {
        this.assistentStep = as;
    }
    
    //-------------------------------------------------------------------------
    
    public String getClassName() {
        return this.className;
    }
    
    public void setClassName(String name) {
        this.className = name;
    }
    
    //-------------------------------------------------------------------------
    
    public String getClassURI() {
        return this.classURI;
    }
    
    public void setClassURI(String URI) {
        this.classURI = URI;
    }
    
    //-------------------------------------------------------------------------
    
    public String getNumOrderAppear() {
        return this.numOrderAppear;
    }
    
    public void setNumOrderAppear(String numOrderAppear) {
        this.numOrderAppear = numOrderAppear;
    }
    
    //-------------------------------------------------------------------------
    
    public String getObjectName() {
        return this.objectName;
    }
    
    public void setObjectName(String name) {
        this.objectName = name;
    }
    
    //-------------------------------------------------------------------------
    
    public DataModel getProperties() {
        return this.properties;
    }
    
    public void setProperties(final DataModel properties) {
        this.properties = properties;
    }
    
    //-------------------------------------------------------------------------

    public ArrayList getOrderAppearPropList() {
        return this.orderAppearPropList;
    }

    public void setOrderAppearPropList(ArrayList orderAppearPropList) {
        this.orderAppearPropList = orderAppearPropList;
    }
    
    //-------------------------------------------------------------------------
    
    public boolean getHasProperties() {
        return (this.properties.getRowCount() > 0);
    }
    
    //-------------------------------------------------------------------------
    
    public void saveNew() {

        AssistentStepClass asc = new AssistentStepClass();
        asc.setClassName(this.getClassURI());
        asc.setNumOrderAppear(Integer.parseInt(this.getNumOrderAppear()));
        asc.setObjectName(this.getObjectName());
        asc.setAssistentStep(this.getAssistentStep());
        asc.save();
        
        for (int i=0; i<this.getProperties().getRowCount(); i++) {
            this.getProperties().setRowIndex(i);
            CreateStepClassProperties cscp = 
                    (CreateStepClassProperties) this.getProperties().
                    getRowData();
            cscp.setStepClass(asc);
            cscp.saveNew();
        }
    }
 
    //-------------------------------------------------------------------------
    
    public void updateStepClass() {
        
        this.stepClass.setClassName(this.getClassURI());
        this.stepClass.setNumOrderAppear(Integer.parseInt(this.getNumOrderAppear()));
        this.stepClass.setObjectName(this.getObjectName());
        this.stepClass.setAssistentStep(this.getAssistentStep());
        this.stepClass.update();
        
        for (int i=0; i<this.getProperties().getRowCount(); i++) {
            this.getProperties().setRowIndex(i);
            CreateStepClassProperties cscp = 
                    (CreateStepClassProperties) this.getProperties().
                    getRowData();
            cscp.setStepClass(this.stepClass);
            cscp.updateThis();
        }
    }
    
    //-------------------------------------------------------------------------
    
    public String removeStepClass() {
        
        this.stepClass.delete();
        
        getSession().removeAttribute("hasClasses");
        getSession().removeAttribute("createStepsBean");
        getSession().removeAttribute("createStepClassesBean");
        getSession().removeAttribute("createStepClassPropertiesBean");
        return "";
    }
    
    //-------------------------------------------------------------------------
}
