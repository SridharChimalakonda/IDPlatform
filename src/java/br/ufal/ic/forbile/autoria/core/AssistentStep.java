package br.ufal.ic.forbile.autoria.core;

import  br.ufal.ic.forbile.autoria.persistence.*;
import  br.ufal.ic.forbile.autoria.exceptions.*;
import  java.util.*;


public class AssistentStep {

    private int             idAssistentStep;
    private Ontology        ontology;
    private int             stepNum = 0;
    private String          title;
    private String          description;
    private String          dateLastModification;
    private User            userWhoModified;          
    
    private ArrayList       assistentStepClasses;
    
    
    public AssistentStep() {
        
    }
    
    public AssistentStep(int idAssistent, int stepNum) {

        AssistentData   assData = new AssistentData();
        
        try {
            ArrayList data = assData.getAssistentStep(String.valueOf(idAssistent), 
                    String.valueOf(stepNum));

            this.setIdAssistentStep(Integer.parseInt((String) data.get(0)));
            //this.setOntology(new Ontology(Integer.parseInt((String) data.get(1))));
            //this.setConfigAssistent(new ConfigAssistent(this.getOntology()));
            this.setTitle((String) data.get(2));
            this.setDescription((String) data.get(3));
            this.setStepNum(stepNum);

            this.assistentStepClasses = new ArrayList();
            for (int i = 0; i < ((ArrayList) data.get(4)).size(); i++) {
                AssistentStepClass asc = new AssistentStepClass(
                        Integer.parseInt(
                        (String) ((ArrayList) data.get(4)).get(i)));
                asc.setAssistentStep(this);
                this.assistentStepClasses.add(i, asc);
            }

            this.setDateLastModification((String) data.get(5));
            this.setUserWhoModified(new User(Integer.parseInt(
                    (String) data.get(6))));
            
        } catch (PersistenceException ex) {
            System.out.println(ex.getMessage());
        }
        
    }
    
    public AssistentStep(int idAssistentStep) {
        
        this.setIdAssistentStep(idAssistentStep);
        AssistentData   assData = new AssistentData();
        
        try {
            ArrayList data = assData.getAssistentStep(
                    String.valueOf(idAssistentStep));

            this.setTitle((String) data.get(2));
            this.setDescription((String) data.get(3));
            this.setStepNum(Integer.parseInt((String) data.get(4)));

            this.assistentStepClasses = new ArrayList();
            for (int i = 0; i < ((ArrayList) data.get(5)).size(); i++) {
                AssistentStepClass asc = new AssistentStepClass(
                        Integer.parseInt(
                        (String) ((ArrayList) data.get(5)).get(i)));
                asc.setAssistentStep(this);
                this.assistentStepClasses.add(i, asc);
            }

            this.setDateLastModification((String) data.get(6));
            this.setUserWhoModified(new User(Integer.parseInt(
                    (String) data.get(7))));
            
        } catch (PersistenceException ex) {
            System.out.println(ex.getMessage());
        }
        
    }
    
    //-------------------------------------------------------------------------
    
    public int getIdAssistentStep() {
        return this.idAssistentStep;
    }
    
    public void setIdAssistentStep(int idAssistentStep) {
        this.idAssistentStep = idAssistentStep;
    }
    
    //-------------------------------------------------------------------------
    
    public Ontology getOntology() {
        return this.ontology;
    }
    
    public void setOntology(Ontology ontology) {
        this.ontology = ontology;
    }
    
    //-------------------------------------------------------------------------
    
    public int getStepNum() {
        return this.stepNum;
    }
    
    public void setStepNum(int stepNum) {
        this.stepNum = stepNum;
    }
    
    //-------------------------------------------------------------------------
    
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    //-------------------------------------------------------------------------
    
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    //-------------------------------------------------------------------------
    
    public ArrayList getAssistentStepClasses() {
        return this.assistentStepClasses;
    }
    
    public void setAssistentStepClasses(ArrayList stepClasses) {
        this.assistentStepClasses = stepClasses;
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
    
    public User getUserWhoModified() {
        return this.userWhoModified;
    }
    
    public void setUserWhoModified(User user) {
        this.userWhoModified = user;
    }
    
    //-------------------------------------------------------------------------
    
    public void goUp() {
        
        AssistentData ad = new AssistentData();
        try{
            ad.moveAssistentStep(getOntology().getConfigAssistent().getIdAssistent(),
                    getStepNum(), 1);
            setStepNum(getStepNum()-1);
        } catch (PersistenceException ex) {
            ex.printStackTrace();
        }
    }
    
    //-------------------------------------------------------------------------
    
    public void goDown() {
        
        AssistentData ad = new AssistentData();
        try{
            ad.moveAssistentStep(getOntology().getConfigAssistent().getIdAssistent(),
                    getStepNum(), 2);
            setStepNum(getStepNum()+1);
        } catch (PersistenceException ex) {
            ex.printStackTrace();
        }
    }
    
    //-------------------------------------------------------------------------
    
    public void save() {
        
        if (getOntology() != null && getTitle() != null && 
                getDescription() != null && getStepNum() != 0 
                && getUserWhoModified() != null) {
            
            AssistentData ad = new AssistentData();
            try {
                setIdAssistentStep(ad.insertNewAssistentStep(
                        String.valueOf(getOntology().getConfigAssistent().getIdAssistent()), 
                        String.valueOf(getStepNum()),
                        getTitle(),getDescription(),
                        String.valueOf(getUserWhoModified().getIdUser())));
            } catch (PersistenceException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    //-------------------------------------------------------------------------
    
    public void update() {
        
        if (getOntology() != null && getTitle() != null && 
                getDescription() != null && getStepNum() != 0 
                && getUserWhoModified() != null) {
            
            AssistentData ad = new AssistentData();
            try {
                ad.updateAssistentStep(
                        String.valueOf(getOntology().getConfigAssistent().getIdAssistent()), 
                        String.valueOf(getStepNum()),
                        getTitle(),getDescription(),
                        String.valueOf(getUserWhoModified().getIdUser()));
            } catch (PersistenceException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    //-------------------------------------------------------------------------
    
    public void remove() {
        
        if (getOntology() != null && getStepNum() != 0) {
            
            AssistentData ad = new AssistentData();
            try {
                ad.removerAssistentStep(
                        String.valueOf(getOntology().getConfigAssistent().getIdAssistent()), 
                        String.valueOf(getStepNum()));
            } catch (PersistenceException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    //-------------------------------------------------------------------------
}
