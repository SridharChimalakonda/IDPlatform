package br.ufal.ic.forbile.autoria.beans;

import  br.ufal.ic.forbile.autoria.persistence.*;
import  br.ufal.ic.forbile.autoria.exceptions.*;

import  javax.faces.model.DataModel;
import  javax.faces.model.ListDataModel;
import  java.util.*;


public class SearchKnowledge {
    
    private DataModel   knowledges;
    private DataModel   lastKnowledges;
    private String      areaName;
    
    //-------------------------------------------------------------------------
    
    public SearchKnowledge() {
        
        if (this.areaName == null)
            this.searchLastModified();
    }
    
    //-------------------------------------------------------------------------
    
    public String getAreaName() {
        return this.areaName;
    }
    
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
    
    //-------------------------------------------------------------------------
    
    public DataModel getKnowledges() {
        return this.knowledges;
    }
    
    public void setKnowledges(final DataModel knowledges) {
        this.knowledges = knowledges;
    }
    
    //-------------------------------------------------------------------------
    
    public DataModel getLastKnowledges() {
        return this.lastKnowledges;
    }
    
    public void setLastKnowledges(final DataModel knowledges) {
        this.lastKnowledges = knowledges;
    }
    
    //-------------------------------------------------------------------------
    
    public void search() {
        
        List knwList = new ArrayList();
        Knowledge knwData = new Knowledge();
        
        try {
            knwList = knwData.searchKnowledgeArea(getAreaName());
            this.knowledges = new ListDataModel(knwList);
        } catch (PersistenceException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    //-------------------------------------------------------------------------
    
    public void searchLastModified() {
        
        List knwList = new ArrayList();
        Knowledge knwData = new Knowledge();
        
        try {
            knwList = knwData.searchLastModifiedKnowledges();
            this.lastKnowledges = new ListDataModel(knwList);
        } catch (PersistenceException ex) {
            System.out.println(ex.getMessage());
        } 
    }
}
