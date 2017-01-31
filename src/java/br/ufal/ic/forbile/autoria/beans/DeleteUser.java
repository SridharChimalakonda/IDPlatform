package br.ufal.ic.forbile.autoria.beans;

import  br.ufal.ic.forbile.autoria.core.*;
import  br.ufal.ic.forbile.autoria.persistence.*;
import  br.ufal.ic.forbile.autoria.exceptions.*;
import  br.ufal.ic.forbile.autoria.core.util.*;
import  java.util.*;


public class DeleteUser extends SessionBean {

    private String idUser;
    private String name;
    
    private boolean canBeRemoved;
    
    //-------------------------------------------------------------------------
    
    public DeleteUser() {
        
        if (getRequest("idUser") != null) {
            getSession().removeAttribute("idUser");
            this.idUser = (String) getRequest("idUser");
            getSession().setAttribute("idUser", this.idUser);
        } else {
            this.idUser = (String) getSession().getAttribute("idUser");
        }

        User usr = new User(Integer.parseInt(this.idUser));
        setName(usr.getName());

        int idLoggedUser = ((User) getSession().getAttribute("authenticatedUser")).
                getIdUser();

        if (idLoggedUser == Integer.parseInt(this.idUser)) {
            setCanBeRemoved(false);
        } else {
            setCanBeRemoved(true);
        }
        
    }
    
    //-------------------------------------------------------------------------
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    //-------------------------------------------------------------------------
    
    public boolean getCanBeRemoved() {
        return this.canBeRemoved;
    }
    
    public void setCanBeRemoved(boolean canBeRemoved) {
        this.canBeRemoved = canBeRemoved;
    }

    //-------------------------------------------------------------------------
    
    public String removeThis() {
        
        User usr = new User(Integer.parseInt(this.idUser));
        usr.delete();
        getSession().removeAttribute("idUser");
        return "removeUser";
    }
    
    //-------------------------------------------------------------------------
    
    public String back() {
        
        getSession().removeAttribute("idUser");
        return "removeUser";
    }
    
    //-------------------------------------------------------------------------
}
