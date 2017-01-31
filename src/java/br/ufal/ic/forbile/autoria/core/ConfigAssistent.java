package br.ufal.ic.forbile.autoria.core;

import  br.ufal.ic.forbile.autoria.persistence.*;
import  br.ufal.ic.forbile.autoria.exceptions.*;
import  java.util.*;

public class ConfigAssistent {
    
    private int         idAssistent;
    private Ontology    ontology;
    private User        userCreator;
    private String      dateCreation;
    private String      dateLastModification;
    
    private ArrayList   assistentSteps;
    private int         stepsCount;
    
    //-------------------------------------------------------------------------
    
    public ConfigAssistent() {
        
    }
    
    public ConfigAssistent(Ontology ontology) {
    //public ConfigAssistent(int idConfigAssistent) {
        //this.setOntology(new Ontology(idOntology));
        getData(ontology.getIdOntology());
    }
    
    private void getData(int idOntology) {
        
        AssistentData    assData = new AssistentData();
        
        try {
            ArrayList   data    = assData.getAssistentByOntology(idOntology);
            
            if (!data.isEmpty()) {
                this.setIdAssistent(Integer.parseInt((String)data.get(0)));
                this.setUserCreator(new User(Integer.parseInt((String)data.get(1))));
                this.setDateCreation((String)data.get(2));
                this.assistentSteps = new ArrayList();
                for (int i = 0; i < ((ArrayList) data.get(3)).size(); i++) {
                    AssistentStep as = new AssistentStep(
                            Integer.parseInt(
                            (String) ((ArrayList) data.get(3)).get(i)));
                    as.setOntology(this.getOntology());
                    this.assistentSteps.add(i, as);
                }
                
                this.stepsCount = this.assistentSteps.size();
                
                if (data.size() == 5) {
                     this.setDateLastModification((String)data.get(4));
                } else {
                     this.setDateLastModification((String)data.get(2));
                }
               
            } else {
                this.setIdAssistent(0);
            }
            
        } catch (PersistenceException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public boolean isEmpty() {
        return (this.getIdAssistent() == 0? true : false);
    }
    //-------------------------------------------------------------------------
    
    public int getIdAssistent() {
        return this.idAssistent;
    }
    
    public void setIdAssistent(int idAssistent) {
        this.idAssistent = idAssistent;
    }
    
    //-------------------------------------------------------------------------
    
    public Ontology getOntology() {
        return this.ontology;
    }
    
    public void setOntology(Ontology ontology) {
        this.ontology = ontology;
    }

    //-------------------------------------------------------------------------
    
    public User getUserCreator() {
        return this.userCreator;
    }
    
    public void setUserCreator(User userCreator) {
        this.userCreator = userCreator;
    }
    
    //-------------------------------------------------------------------------
    
    public String getDateCreation() {
        
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
    
    public String getDateLastModification() {
        
        String date = this.dateLastModification.substring(8, 10) + "/" +
                      this.dateLastModification.substring(5, 7) + "/" +
                      this.dateLastModification.substring(0, 4) + " " +
                      this.dateLastModification.substring(11,16);
        return date;
    }
    
    public String getDateLastModificationTimeStamp() {
        return this.dateLastModification;
    }
    
    public void setDateLastModification(String dateLastModification) {
        this.dateLastModification = dateLastModification;
    }
    
    //-------------------------------------------------------------------------
    
    public ArrayList getAssistentSteps() {
        return this.assistentSteps;
    }
    
    public void setAssistentSteps(ArrayList assistentSteps) {
        this.assistentSteps = assistentSteps;
        this.stepsCount = assistentSteps.size();
    }
    
    public int getCountSteps() {
        return this.stepsCount;
    }
    
    //-------------------------------------------------------------------------
    
    public void save() {
        
        if (getOntology() != null && getUserCreator() != null) {
            AssistentData ad = new AssistentData();
            try {
                setIdAssistent(ad.insertNewAssistent(
                        String.valueOf(getOntology().getIdOntology()),
                        String.valueOf(getUserCreator().getIdUser())));
                getData(getOntology().getIdOntology());
            } catch (PersistenceException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    //-------------------------------------------------------------------------
}
