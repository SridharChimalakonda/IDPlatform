package br.ufal.ic.forbile.autoria.beans;

import  br.ufal.ic.forbile.autoria.persistence.*;
import  br.ufal.ic.forbile.autoria.exceptions.*;

import  javax.faces.model.DataModel;
import  javax.faces.model.ListDataModel;
import  java.util.*;


public class SearchUser {
    
    private DataModel   users;
    private DataModel   lastUsers;
    private String      usrName;
    
    //-------------------------------------------------------------------------
    
    public SearchUser() {
        
        if (this.usrName == null)
            this.searchLastModified();
    }
    
    //-------------------------------------------------------------------------
    
    public String getUsrName() {
        return this.usrName;
    }
    
    public void setUsrName(String usrName) {
        this.usrName = usrName;
    }
    
    //-------------------------------------------------------------------------
    
    public DataModel getUsers() {
        return this.users;
    }
    
    public void setUsers(final DataModel users) {
        this.users = users;
    }
    
    //-------------------------------------------------------------------------
    
    public DataModel getLastUsers() {
        return this.lastUsers;
    }
    
    public void setLastUsers(final DataModel users) {
        this.lastUsers = users;
    }
    
    //-------------------------------------------------------------------------
    
    public void search() {
        
        List usrList = new ArrayList();
        UserData userData = new UserData();
        
        try {
            usrList = userData.searchUsers(getUsrName());
            this.users = new ListDataModel(usrList);
        } catch (PersistenceException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    //-------------------------------------------------------------------------
    
    public void searchLastModified() {
        
        List usrList = new ArrayList();
        UserData userData = new UserData();
        
        try {
            usrList = userData.searchLastModifiedUsers();
            this.lastUsers = new ListDataModel(usrList);
        } catch (PersistenceException ex) {
            System.out.println(ex.getMessage());
        } 
    }
}
