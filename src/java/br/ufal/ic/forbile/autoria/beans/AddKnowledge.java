package br.ufal.ic.forbile.autoria.beans;

import  br.ufal.ic.forbile.autoria.core.*;
import  br.ufal.ic.forbile.autoria.persistence.*;
import  br.ufal.ic.forbile.autoria.exceptions.*;
import  br.ufal.ic.forbile.autoria.core.util.*;
import  java.util.*;


import  javax.faces.context.FacesContext;
import  javax.faces.application.FacesMessage;
import  javax.faces.component.UIComponent;
import  javax.faces.validator.ValidatorException;


public class AddKnowledge extends SessionBean {

    private String name;
    private String description;
    
    
    //-------------------------------------------------------------------------
    
    public AddKnowledge() {
         
        if (getRequest("idKnowledge") != null) {
            
            getSession().removeAttribute("idKnowledge");
            
            String idKnowledge = (String) getRequest("idKnowledge");
            getSession().setAttribute("idKnowledge", idKnowledge);
            
            KnowledgeArea ka = new KnowledgeArea(Integer.parseInt(idKnowledge));
            setName(ka.getName());
            setDescription(ka.getDescription());
            
            getSession().setAttribute("preName", ka.getName());
            
        }
    }
    
    //-------------------------------------------------------------------------
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void validateName(FacesContext context, UIComponent component, 
                                    Object value) {

        String nameField = value.toString();
        String preName = (String) getSession().getAttribute("preName");
        
        if (preName != null)
            if (nameField.equals(preName))
                return;
        
        if ((nameField.length() <= 2)) {    
            FacesMessage message = new FacesMessage(Constant.GEN_MSG1, 
                                        Constant.ADD_KNOWLEDGE_MSG1);
            throw new ValidatorException(message);
        }
        
        
        Knowledge knowData = new Knowledge();
        
        try {
            if (knowData.checkKnowledgeAreaNameExists(nameField)) {
                FacesMessage message = new FacesMessage(Constant.GEN_MSG1, 
                                            Constant.ADD_KNOWLEDGE_MSG2 +" \"" +
                                            nameField + "\\" + Constant.ADD_KNOWLEDGE_MSG3);
                throw new ValidatorException(message);
            }
        } catch(PersistenceException e) {
            System.out.println(e.getMessage());
        }
    }
    
    //-------------------------------------------------------------------------
    
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    //-------------------------------------------------------------------------
    
    public String saveNew() {
        
        KnowledgeArea ka = new KnowledgeArea();
        
        ka.setName(getName());
        ka.setDescription(getDescription());
        ka.setUserCreator((User)getSession().getAttribute("authenticatedUser"));
        ka.save();
        
        return "editKnowledgeArea";
    }
    
    //-------------------------------------------------------------------------
    
    public String updateThis() {
        
        KnowledgeArea ka = new KnowledgeArea(Integer.parseInt(
                (String)getSession().getAttribute("idKnowledge")));  
        
        ka.setName(getName());
        ka.setDescription(getDescription());
        ka.update();
        
        getSession().removeAttribute("idKnowledge");
        getSession().removeAttribute("preName");
        return "editKnowledgeArea";
    }
    
    //-------------------------------------------------------------------------
    
    public String removeThis() {
        
        System.out.println(Constant.ADD_KNOWLEDGE_MSG4);
        getSession().removeAttribute("idKnowledge");
        return "editKnowledgeArea";
    }
    
    //-------------------------------------------------------------------------
    
    public String back() {
        
        getSession().removeAttribute("idKnowledge");
        return "removeUser";
    }
    
    //-------------------------------------------------------------------------
    
    public String back2() {
        
        getSession().removeAttribute("idKnowledge");
        return "editKnowledgeArea";
    }
    
    //-------------------------------------------------------------------------
}
