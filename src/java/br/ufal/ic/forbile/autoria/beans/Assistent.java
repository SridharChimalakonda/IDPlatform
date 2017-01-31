package br.ufal.ic.forbile.autoria.beans;

import  br.ufal.ic.forbile.autoria.core.*;
import  br.ufal.ic.forbile.autoria.persistence.*;
import  br.ufal.ic.forbile.autoria.exceptions.*;
import  br.ufal.ic.forbile.autoria.core.util.*;
import  java.util.*;
import  java.io.IOException;
import  javax.faces.model.DataModel;
import  javax.faces.model.ListDataModel;

public class Assistent extends SessionBean implements java.io.Serializable {

    private String stepTitle;
    private String stepDescription;
    
    private DataModel classes;
    
    public Assistent() {
        
        String idOntology = (String) getRequest("idOntology");
        String stepNum    = (String) getRequest("stepNum");
        
        // Controls the session and request/response calls
        if (idOntology == null || idOntology.equals("0") || idOntology.equals("") ||
                stepNum == null || stepNum.equals("0") || stepNum.equals("")) {

            if (idOntology == null || idOntology.equals("0") || idOntology.equals(""))
                idOntology = (String) getSession().getAttribute("idOntology");
            
            if (stepNum == null || stepNum.equals("0") || stepNum.equals(""))
                stepNum = (String) getSession().getAttribute("stepNum");
            
            if (idOntology == null || stepNum == null) {
                  
                try {
                    getResponse().sendRedirect("search.jsf");
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                    return;
                }
            }
        } else {            
            getSession().setAttribute("idOntology", idOntology);
            getSession().setAttribute("stepNum", stepNum);
        }
        
        Ontology ontology = new Ontology(Integer.parseInt(idOntology));

        AssistentStep as = new AssistentStep(
                ontology.getConfigAssistent().getIdAssistent(),
                Integer.parseInt(stepNum));

        as.setOntology(ontology);
        this.setStepTitle(as.getTitle());
        this.setStepDescription(as.getDescription());
        
        List classList = new ArrayList();
        for (int i = 0; i < as.getAssistentStepClasses().size(); i++) {
            try {
                AssistentClass ac = new AssistentClass(
                        ((AssistentStepClass) as.getAssistentStepClasses().
                        get(i)).getIdStepClass());
                classList.add(ac);
            } catch (NullPointerException ex) {
                System.out.println(ex.getMessage());
            }
        }
        this.classes = new ListDataModel(classList);
    }
    
    //-------------------------------------------------------------------------
    
    public String getStepTitle() {
        return this.stepTitle;
    }
    
    public void setStepTitle(String stepTitle) {
        this.stepTitle = stepTitle;
    }
    
    //-------------------------------------------------------------------------
    
    public String getStepDescription() {
        return this.stepDescription;
    }
    
    public void setStepDescription(String stepDescription) {
        this.stepDescription = stepDescription;
    }
    
    //-------------------------------------------------------------------------
    
    public DataModel getClasses() {
        return this.classes;
    }
    
    public void setClasses(final DataModel classes) {
        this.classes = classes;
    }
    
      //Written by Dida just for custom button
    public void myButton() {
        System.out.println("I am in assistent bean my button");
        
         //return "back";
    }
    
    
    //-------------------------------------------------------------------------
    
    public String backToAll() {
        
        System.out.println("I am in back to all");
        getSession().removeAttribute("assistentBean");
        return "ontologyInfo";
    }
    //-------------------------------------------------------------------------
    
    public String previous() {
        
       int prevStep = Integer.parseInt((String)getSession().
       getAttribute("stepNum")) - 1;
        
        getSession().removeAttribute("assistentBean");
        if (prevStep > 0) {
            getSession().setAttribute("stepNum", String.valueOf(prevStep));
            return "";
        } else {
            return "ontologyInfo";
        }
    }
    //-------------------------------------------------------------------------
    
    public String next() {
        
        int nextStep = Integer.parseInt((String)getSession().
                getAttribute("stepNum")) + 1;
        int stepsCount = Integer.parseInt((String)getSession().
                getAttribute("stepsCount"));
        
        getSession().removeAttribute("assistentBean");        
        if (nextStep <= stepsCount) {
            getSession().setAttribute("stepNum", String.valueOf(nextStep));
            return "";
        } else {
            return "ontologyInfo";
        }
    }
    //-------------------------------------------------------------------------
}
