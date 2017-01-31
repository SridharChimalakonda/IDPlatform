package br.ufal.ic.forbile.autoria.beans;

import  br.ufal.ic.forbile.autoria.core.*;
import  br.ufal.ic.forbile.autoria.persistence.*;
import  br.ufal.ic.forbile.autoria.exceptions.*;
import  br.ufal.ic.forbile.autoria.core.util.*;
import  java.util.*;
import  java.io.IOException;
import  javax.faces.model.SelectItem;
import  javax.faces.model.DataModel;
import  javax.faces.model.ListDataModel;
//import  org.apache.myfaces.custom.tree2.*;
//import  org.apache.myfaces.custom.tree.model.TreeModel;
//import  org.apache.myfaces.custom.tree.model.DefaultTreeModel;
//import  org.apache.myfaces.custom.tree.DefaultMutableTreeNode;



public class OntologyViewDetails extends SessionBean {
    
    private String ontName;
    private String dateLastModified;
    private String dateCreation;
    private String userCreator;
    private String userLastModified;
    private String description;
    private String qtdClasses;
    private String isAcessible;
    
    private ArrayList accessOptions;
    private boolean   hasConfigAssistent;
    private String    countOfSteps;
    //private ArrayList assistentSteps;
    private DataModel assistentSteps;
    
    
    public OntologyViewDetails() {

        this.accessOptions = new ArrayList();
        this.accessOptions.add(new SelectItem("1", Constant.ONTOLOGY_VIEW_DETAILS_MSG1));
        this.accessOptions.add(new SelectItem("0", Constant.ONTOLOGY_VIEW_DETAILS_MSG2));

        String idOntology = (String) getRequest("idOntology");
        
        if ( ((String)getRequest("stepNum")) != null && 
             !((String)getRequest("stepNum")).equals("0") &&
             !((String)getRequest("stepNum")).equals(""))
            getSession().setAttribute("stepNum", (String)getRequest("stepNum"));

        if (idOntology == null || idOntology.equals("0") || idOntology.equals("")) {

            idOntology = (String) getSession().getAttribute("idOntology");
            
            if (idOntology == null) {
                  
                try {
                    getResponse().sendRedirect("config.jsf");
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                    return;
                }
            }
        } else {

            getSession().setAttribute("idOntology", idOntology);
        }
        
        br.ufal.ic.forbile.autoria.core.Ontology newOntology =
                new br.ufal.ic.forbile.autoria.core.Ontology(
                Integer.parseInt(idOntology));

        this.ontName = newOntology.getName();
        this.dateCreation = newOntology.getDateCreation();
        this.userCreator = newOntology.getUserCreator().getName();
        this.description = newOntology.getDescription();
        this.qtdClasses = String.valueOf(newOntology.getQtdClasses());
        this.isAcessible = (newOntology.isAcessibleByAuthor() ? "1" : "0");
        this.dateLastModified = newOntology.getDateLastModified();
        this.userLastModified = newOntology.getUserLastModified().getName();

        this.hasConfigAssistent = !newOntology.getConfigAssistent().isEmpty();

        if (!newOntology.getConfigAssistent().isEmpty()) {
            
            this.countOfSteps = String.valueOf(
                    newOntology.getConfigAssistent().getCountSteps());
            
            getSession().setAttribute("stepsCount", this.countOfSteps);
            
            ArrayList listSteps = new ArrayList();
            for (int i=0; i< Integer.parseInt(this.countOfSteps); i++)
                listSteps.add(new AssistentStep(newOntology.
                        getConfigAssistent().getIdAssistent(), i+1));
                                    
            if (getRequest("action") != null && !getRequest("action").equals("edit")) {
                
                AssistentStep as = (AssistentStep)listSteps.get((
                            Integer.parseInt((String)getRequest("stepNum"))-1));
                
                if (getRequest("action").equals("up")) {    
                    as.setOntology(newOntology);
                    as.goUp();
                } else {
                    as.setOntology(newOntology);
                    as.goDown();
                }
                
                listSteps.clear();
                for (int i=0; i< Integer.parseInt(this.countOfSteps); i++)
                listSteps.add(new AssistentStep(newOntology.
                        getConfigAssistent().getIdAssistent(), i+1));
            }
            
            this.assistentSteps = new ListDataModel(listSteps);
        }
    }
    
    //-------------------------------------------------------------------------
    
    public String getOntName() {
        return this.ontName;
    }
    
    public void setOntName(String ontName) {
        this.ontName = ontName;
    }
    
    //-------------------------------------------------------------------------
    
    public String getDateCreation() {
        return this.dateCreation;
    }
    
    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }
    
    //-------------------------------------------------------------------------
    
    public String getDateLastModified() {
        return this.dateLastModified;
    }
    
    public void setDateLastModified(String dateLastModified) {
        this.dateLastModified = dateLastModified;
    }
    
    //-------------------------------------------------------------------------
    
    public String getUserCreator() {
        return this.userCreator;
    }
    
    public void setUserCreator(String userCreator) {
        this.userCreator = userCreator;
    }
    
    //-------------------------------------------------------------------------
    
    public String getUserLastModified() {
        return this.userLastModified;
    }
    
    public void setUserLastModified(String userLastModified) {
        this.userLastModified = userLastModified;
    }
    
    //-------------------------------------------------------------------------
    
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    //-------------------------------------------------------------------------
    
    public String getQtdClasses() {
        return this.qtdClasses;
    }
    
    public void setQtdClasses(String qtdClasses) {
        this.qtdClasses = qtdClasses;
    }
    
    //-------------------------------------------------------------------------
    
    public String getIsAcessible() {
        return this.isAcessible;
    }
    
    public void setIsAcessible(String isAcessible) {
        this.isAcessible = isAcessible;
    }
    
    //-------------------------------------------------------------------------
    
    public ArrayList getAccessOptions() {
        return this.accessOptions;
    }

    public void setAccessOptions(ArrayList accessOptions) {
        this.accessOptions = accessOptions;
    }
    
    //-------------------------------------------------------------------------
    
    public boolean getHasConfigAssistent() {
        return this.hasConfigAssistent;
    }
    
    public void setHasConfigAssistent(boolean hasConfigAssistent) {
        this.hasConfigAssistent = hasConfigAssistent;
    }
    
    //-------------------------------------------------------------------------
    
    public String getCountOfSteps() {
        return this.countOfSteps;
    }
    
    public void setCountOfSteps(String countOfSteps) {
        this.countOfSteps = countOfSteps;
    }
    
    //-------------------------------------------------------------------------
    
    public DataModel getAssistentSteps() {
        return this.assistentSteps;
    }
    
    public void setAssistentSteps(DataModel assistentSteps) {
        this.assistentSteps = assistentSteps;
    }
    
    //-------------------------------------------------------------------------
             
    public void changeOntAccess() {
        
        String idOntology = (String) getSession().
                getAttribute("idOntology");

        br.ufal.ic.forbile.autoria.core.Ontology ontology =
                new br.ufal.ic.forbile.autoria.core.Ontology(
                Integer.parseInt(idOntology));
        
        User user = (User)getSession().getAttribute("authenticatedUser");

        if (this.getHasConfigAssistent()) {
            ontology.setAcessible((this.getIsAcessible().equals("0") ? false : true));
            ontology.setUserLastModified(user);
            ontology.changeAccess();
        } else {
            this.setIsAcessible((ontology.isAcessibleByAuthor()? "1" : "0"));
            setErrorMsg(Constant.ONTOLOGY_VIEW_DETAILS_MSG3);
        }
    }
    
    //-------------------------------------------------------------------------
}
