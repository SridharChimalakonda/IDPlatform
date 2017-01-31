package br.ufal.ic.forbile.autoria.beans;

import  br.ufal.ic.forbile.autoria.core.*;
import br.ufal.ic.forbile.autoria.core.util.Constant;
import  br.ufal.ic.forbile.autoria.persistence.*;

import  java.util.*;
import  javax.faces.model.SelectItem;
import  javax.faces.model.DataModel;
import  javax.faces.model.ListDataModel;
//import  com.hp.hpl.jena.ontology.*;
//import  com.hp.hpl.jena.rdf.model.*;


public class AssistentClass extends SessionBean implements java.io.Serializable {
    
    private AssistentStep   assistentStep;
    private String          className;
    private String          classURI;
    private String          numOrderAppear;
    private String          objectName;
    
    private ArrayList       individuals            = new ArrayList();
    private boolean         hasIndividuals;
    private String          selectedIndividual;
    
    private DataModel       properties;
    private ArrayList       orderAppearPropList    = new ArrayList();
    
    private AssistentStepClass stepClass;
    
    //-------------------------------------------------------------------------
    
    public AssistentClass() {
        
    }
    
    public AssistentClass(int idStepClass) {
        
        AssistentStepClass asc = new AssistentStepClass(idStepClass);
        
        this.stepClass = asc;
        setAssistentStep(asc.getAssistentStep());
        String tmp[] = asc.getClassName().split("#");
        setClassName(tmp[1]);
        setClassURI(asc.getClassName());
        setNumOrderAppear(String.valueOf(asc.getNumOrderAppear()));
        setObjectName(asc.getObjectName());
        
        this.getIndividualsFromOWL();
        
        List propertiesList = new ArrayList();
        int x = 0;
        for ( ; x < asc.getStepClasseProperties().size(); x++) {
            propertiesList.add(new AssistentClassProperty(
                    ((AssistentStepClassProperty)asc.getStepClasseProperties().get(x)).
                    getIdStepClassProperty()));
        }

        this.properties = new ListDataModel(propertiesList);
        
        for(int j=1; j<=x; j++) {
            this.orderAppearPropList.add(new SelectItem(
                    String.valueOf(j),String.valueOf(j)));
        }
    }
    
    public AssistentClass(String ontClassURI, OWLData ontology) {
        
        List allProps = ontology.getClassProperties(ontClassURI);
        
        this.getIndividualsFromOWL();
        
        List propertiesList = new ArrayList();
        int x = 0;
        for ( ; x < allProps.size(); x++) {
            propertiesList.add(new AssistentClassProperty(
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
    
    private void getIndividualsFromOWL() {
        
        String idOntology = (String) getSession().getAttribute("idOntology");
        OWLData ontology = new OWLData(Integer.parseInt(idOntology));
        
        List orderIndividuals = ontology.getIndividuals(this.classURI);
        
        if (orderIndividuals.isEmpty()) {
            this.hasIndividuals = false;
            this.individuals.add(new SelectItem("",
                    Constant.ASSITENT_CLASS_MSG1));
        } else {
            this.hasIndividuals = true;
            for(int j=0; j<orderIndividuals.size(); j++) {

                String uri  = (String) orderIndividuals.get(j);
                String name[] = uri.split("#");
                this.individuals.add(new SelectItem(uri,name[1]));
            }
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

    public ArrayList getIndividuals() {
        //System.out.println("I am in get invidiuals"+ this.individuals.toString());
        return this.individuals;
    }

    public void setIndividuals(ArrayList individuals) {
        this.individuals = individuals;
        System.out.println("I am in set invidiuals"+ this. individuals.toString());
    }
    
    //-------------------------------------------------------------------------
    
    public boolean getHasIndividuals() {
        return this.hasIndividuals;
    }
    
    //-------------------------------------------------------------------------
    
    public String getSelectedIndividual() {
        return this.selectedIndividual;
    }
    
    public void setSelectedIndividual(String selectedIndividual) {
        this.selectedIndividual = selectedIndividual;
    }
    
    //-------------------------------------------------------------------------
    
    public DataModel getProperties() {
        //System.out.println("I am in get properties"+ this.properties.toString());
        return this.properties;        
    }
    
    public void setProperties(final DataModel properties) {
        this.properties = properties;
        System.out.println("I am in set properties"+ this.properties.toString());
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
}
