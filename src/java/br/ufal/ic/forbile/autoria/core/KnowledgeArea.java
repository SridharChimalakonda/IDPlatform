package br.ufal.ic.forbile.autoria.core;

import  br.ufal.ic.forbile.autoria.persistence.*;
import  br.ufal.ic.forbile.autoria.exceptions.*;

import  java.util.*;


public class KnowledgeArea {

    private int     idKnowledgeArea;
    private String  name;
    private String  description;
    private String  dateCreation;
    private User    userCreator;
    
    private ArrayList authors;
    
    //-------------------------------------------------------------------------
    
    public KnowledgeArea() { 
        
        setAuthorsHasThisArea(new ArrayList());
    }
    
    public KnowledgeArea(int idKnowledgeArea) {
    
        Knowledge ka = new Knowledge();
        
        try{
            ArrayList data = ka.getKnowledgeAreaById(idKnowledgeArea);
            
            setIdKnowledgeArea(idKnowledgeArea);
            setName((String)data.get(0));
            setDescription((String)data.get(1));
            setDateCreation((String)data.get(2));
            setUserCreator(new User(Integer.parseInt((String)data.get(3))));
            setAuthorsHasThisArea((ArrayList)data.get(4));
            
        } catch (PersistenceException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    //-------------------------------------------------------------------------
    
    public int getIdKnowledgeArea() {
        return this.idKnowledgeArea;
    }
    
    public void setIdKnowledgeArea(int idKnowledgeArea) {
        this.idKnowledgeArea = idKnowledgeArea;
    }
    
    //-------------------------------------------------------------------------
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    //-------------------------------------------------------------------------
    
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    //-------------------------------------------------------------------------
    
    public String getDateCreation() {
        
        if (this.dateCreation == null)
            return null;
        
        String date = this.dateCreation.substring(8, 10) + "/" +
                      this.dateCreation.substring(5, 7) + "/" +
                      this.dateCreation.substring(0, 4) + " " +
                      this.dateCreation.substring(11,16);
        return date;
    }
    
    public String getDateCreationTimeStamp() {
        return this.dateCreation;
    }
    
    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }
    
    //-------------------------------------------------------------------------
    
    public User getUserCreator() {
        return this.userCreator;
    }
    
    public void setUserCreator(User userCreator) {
        this.userCreator = userCreator;
    }
    
    //-------------------------------------------------------------------------
    
    public ArrayList getAuthorsHasThisArea() {
        return this.authors;
    }
    
    public void setAuthorsHasThisArea(ArrayList authors) {
        this.authors = authors;
    }
    
    public boolean hasAuthors() {
        return (this.authors.size()>0? true : false);
    }
    
    public int countOfAuthors() {
        return this.authors.size();
    }
    
    //-------------------------------------------------------------------------
    
    public void save() {
        
        Knowledge k = new Knowledge();
        
        try {
            setIdKnowledgeArea(k.insertNewKnowledgeArea(getName(), 
                    getDescription(), String.valueOf(getUserCreator().getIdUser())));
        } catch (PersistenceException ex) {
            ex.printStackTrace();
        }
    }
    
    //-------------------------------------------------------------------------
    
    public void update() {
        
        Knowledge k = new Knowledge();
        
        try {
            k.updateKnowledgeArea(getIdKnowledgeArea(), getName(), 
                    getDescription());
            
        } catch (PersistenceException ex) {
            ex.printStackTrace();
        }
    }
    
    //-------------------------------------------------------------------------
    
    public void delete() {
        
    }
    
}
