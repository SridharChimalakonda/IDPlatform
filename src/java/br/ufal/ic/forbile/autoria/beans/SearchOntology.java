package br.ufal.ic.forbile.autoria.beans;

import br.ufal.ic.forbile.autoria.core.util.Constant;
import  br.ufal.ic.forbile.autoria.persistence.*;
import  br.ufal.ic.forbile.autoria.exceptions.*;

import  javax.faces.model.DataModel;
import  javax.faces.model.ListDataModel;
import  javax.faces.context.FacesContext;
import  javax.faces.application.FacesMessage;
import  javax.faces.component.UIComponent;
import  javax.faces.validator.ValidatorException;
import  java.util.*;


public class SearchOntology extends SessionBean {
    
    private DataModel   ontologies;
    private DataModel   lastOntologies;
    private String      ontName;
    
    //-------------------------------------------------------------------------
    
    public SearchOntology() {
        
        if (this.ontName == null)
            this.searchLastModified();
    }
    
    //-------------------------------------------------------------------------
    
    public String getOntName() {
        return this.ontName;
    }
    
    public void setOntName(String ontName) {
        this.ontName = ontName;
    }
    
    public void validateOntName(FacesContext context, UIComponent component, 
                                    Object value) {
        
        String name = value.toString();
        
        if ((name.length() <= 3)) {
            FacesMessage message = new FacesMessage(Constant.GEN_MSG1, 
                                        Constant.SEARCH_ONTOLOGY_MSG1);
            throw new ValidatorException(message);
        }
        
    }
    
    //-------------------------------------------------------------------------
    
    public DataModel getOntologies() {
        return this.ontologies;
    }
    
    public void setOntologies(final DataModel ontologies) {
        this.ontologies = ontologies;
    }
    
    //-------------------------------------------------------------------------
    
    public DataModel getLastOntologies() {
        return this.lastOntologies;
    }
    
    public void setLastOntologies(final DataModel ontologies) {
        this.lastOntologies = ontologies;
    }
    
    //-------------------------------------------------------------------------
    
    public void search() {
        
        List ontList = new ArrayList();
        OntologyData ontData = new OntologyData();
        
        try {
            if (getAuthUser().getUserType() == 1)
                ontList = ontData.searchOntologies(this.getOntName());
            else
                ontList = ontData.searchOntologies(this.getOntName(),
                        getAuthUser().getIdUser());
            this.ontologies = new ListDataModel(ontList);
        } catch (PersistenceException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    //-------------------------------------------------------------------------
    
    public void searchLastModified() {
        
        List ontList = new ArrayList();
        OntologyData ontData = new OntologyData();
        
        try {
            if (getAuthUser().getUserType() == 1)
                ontList = ontData.searchLastModOntologies();
            else
                ontList = ontData.searchLastModOntologies(
                        getAuthUser().getIdUser());
            this.lastOntologies = new ListDataModel(ontList);
        } catch (PersistenceException ex) {
            System.out.println(ex.getMessage());
        } 
    }
}
