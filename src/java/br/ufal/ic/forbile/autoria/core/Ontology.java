package br.ufal.ic.forbile.autoria.core;

import  br.ufal.ic.forbile.autoria.persistence.*;
import  br.ufal.ic.forbile.autoria.exceptions.*;
import  java.util.*;

//import  com.hp.hpl.jena.ontology.*;

public class Ontology {
    
    private int             idOntology;
    private String          name;
    private String          description;
    private String          dateCreation;
    private String          dateLastModified;
    private User            userCreator;
    private User            userLastModified;
    private KnowledgeArea   knowArea;
    private int             qtdClasses;
    private boolean         canBeAcessed;
    private ConfigAssistent configAssistent;

    private OWLData         owlFile;
        
    //-------------------------------------------------------------------------
    
    public Ontology() {
    
    }
    
    public Ontology(int idOntology) {
        
        OntologyData    ontData = new OntologyData();
        try {

            ArrayList   data    = ontData.getOntologyById(idOntology);
            
            this.setIdOntology(idOntology);
            this.setName((String)data.get(0));
            this.setDescription((String)data.get(1));
            this.setQtdClasses(Integer.parseInt((String)data.get(2)));
            this.setDateCreation((String)data.get(5));
            
            if (data.get(3).equals("0"))
                this.setAcessible(false);
            else
                this.setAcessible(true);
            
            this.setUserCreator(new User(Integer.parseInt(
                    (String)data.get(4))));
            this.setKnowledgeArea(new KnowledgeArea(Integer.parseInt(
                    (String)data.get(6))));
            
            this.setUserLastModified(new User(Integer.parseInt(
                    (String)data.get(8))));
            
            this.setDateLastModified((String)data.get(7));
            
            //this.setConfigAssistent(new ConfigAssistent(idOntology));
            this.setConfigAssistent(new ConfigAssistent(this));
            
            this.owlFile = new OWLData();
            
        } catch (PersistenceException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    //-------------------------------------------------------------------------
    
    public int getIdOntology() {
        return this.idOntology;
    }
    
    public void setIdOntology(int idOntology) {
        this.idOntology = idOntology;
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
    
    public int getQtdClasses() {
        return this.qtdClasses;
    }
    
    public void setQtdClasses(int qtdClasses) {
        this.qtdClasses = qtdClasses;
    }
    
    //-------------------------------------------------------------------------
    
    public User getUserCreator() {
        return this.userCreator;
    }
    
    public void setUserCreator(User userCreator) {
        this.userCreator = userCreator;
    }
    
    //-------------------------------------------------------------------------
    
    public boolean isAcessibleByAuthor() {
        return this.canBeAcessed;
    }
    
    public void setAcessible(boolean canBeAcessed) {
        this.canBeAcessed = canBeAcessed;
    }
    
    //-------------------------------------------------------------------------
    
    public KnowledgeArea getKnowledgeArea() {
        return this.knowArea;
    }
    
    public void setKnowledgeArea(KnowledgeArea knowArea) {
        this.knowArea = knowArea;
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
    
    private void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }
    
    //-------------------------------------------------------------------------
    
    public String getDateLastModified() {
        
        String date = this.dateLastModified.substring(8, 10) + "/" +
                      this.dateLastModified.substring(5, 7) + "/" +
                      this.dateLastModified.substring(0, 4) + " " +
                      this.dateLastModified.substring(11,16);
        return date;
    }
    
    public String getDateLastModifiedTimeStamp() {
        return this.dateLastModified;
    }
    
    public void setDateLastModified(String dateLastModified) {
        this.dateLastModified = dateLastModified;
    }
    
    //-------------------------------------------------------------------------
    
    public User getUserLastModified() {
        return this.userLastModified;
    }
    
    public void setUserLastModified(User userLastModified) {
        this.userLastModified = userLastModified;
    }
    
    //-------------------------------------------------------------------------
    
    public ConfigAssistent getConfigAssistent() {
        return this.configAssistent;
    }
    
    public void setConfigAssistent(ConfigAssistent configAssistent) {
        this.configAssistent = configAssistent;
    }
    
    //-------------------------------------------------------------------------
    
    public OWLData getOWLData() {
        if (!this.owlFile.hasFileLoaded())
            this.owlFile.setOntology(this.getIdOntology());
        return this.owlFile;
    }
    
    //-------------------------------------------------------------------------
    
    public void changeAccess() {
        
        OntologyData ontData = new OntologyData();
        try {
            ontData.updateOntAccess(this.getIdOntology(), 
                    (this.isAcessibleByAuthor()? "1" : "0"),
                    this.getUserLastModified().getIdUser());
        } catch (PersistenceException ex) {
            ex.printStackTrace();
        }
    }
    
    //-------------------------------------------------------------------------
}
