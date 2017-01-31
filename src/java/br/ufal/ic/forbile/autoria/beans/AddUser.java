package br.ufal.ic.forbile.autoria.beans;

import  br.ufal.ic.forbile.autoria.core.*;
import  br.ufal.ic.forbile.autoria.persistence.*;
import  br.ufal.ic.forbile.autoria.exceptions.*;
import  br.ufal.ic.forbile.autoria.core.util.*;
import  java.util.*;

import  javax.faces.model.SelectItem;
import  javax.faces.context.FacesContext;
import  javax.faces.application.FacesMessage;
import  javax.faces.component.UIComponent;
import  javax.faces.validator.ValidatorException;

import  javax.faces.model.DataModel;
import  javax.faces.model.ListDataModel;


public class AddUser extends SessionBean {

    private String idUser;
    
    private String name;
    private String email;
    private String type;
    private String login;
    private String password;
    private boolean isAcessible;
    
    private ArrayList allTypes;
    private DataModel knowledges;
    
    private ArrayList selectedKnowledges = new ArrayList();
    private ArrayList knowledgesList     = new ArrayList();
    
    private boolean canBeRemoved;
    private boolean hasKnowledgeList;
    
    //-------------------------------------------------------------------------
    
    public AddUser() {
        
        this.allTypes   = new ArrayList();
        this.allTypes.add(new SelectItem("2", Constant.ADD_USER_MSG1));
        this.allTypes.add(new SelectItem("1", Constant.ADD_USER_MSG2));
        
        if (getRequest("idUser") != null || getSession().getAttribute("idUser") != null) {
                        
            if (getRequest("idUser") != null) {
                getSession().removeAttribute("idUser");
                idUser = (String) getRequest("idUser");
                getSession().setAttribute("idUser", idUser);
                
                if (getRequest("idKnowledge") != null) {
                    
                    String idKnowledge = (String) getRequest("idKnowledge"); 
                    try{
                        Knowledge k = new Knowledge();
                        k.removeRelateUserKnowledgeArea(idUser, idKnowledge);
                    } catch(PersistenceException ex) {
                        ex.printStackTrace();
                    }
                }
                
            } else {
                idUser = (String) getSession().getAttribute("idUser");
            }

            getKnowledgeData();
        }
    }
    
    private void getKnowledgeData() {

        User usr = new User(Integer.parseInt(idUser));

        setName(usr.getName());
        setEmail(usr.getEmail());
        setType(String.valueOf(usr.getUserType()));
        setLogin(usr.getLogin());
        setIsAcessible(usr.isAcessible());

        getSession().setAttribute("preLogin", usr.getLogin());

        int idLoggedUser = ((User) getSession().getAttribute("authenticatedUser")).getIdUser();

        if (idLoggedUser == Integer.parseInt(idUser)) {
            setCanBeRemoved(false);
        } else {
            setCanBeRemoved(true);
        }

        setKnowledges(new ListDataModel(usr.getRelatedKnowledgeAreas()));

        try {
            Knowledge k = new Knowledge();
            this.knowledgesList = k.getAllKnowledgeAreas();
        } catch (PersistenceException ex) {
            ex.printStackTrace();
        }

        for (int i = 0; i < this.knowledges.getRowCount(); i++) {

            this.knowledges.setRowIndex(i);
            KnowledgeArea tmp = (KnowledgeArea) this.knowledges.getRowData();
            for (int j = 0; j < this.knowledgesList.size(); j++) {

                int idTmp = Integer.parseInt(
                        (String) ((SelectItem) this.knowledgesList.get(j)).getValue());
                if (tmp.getIdKnowledgeArea() == idTmp) {
                    this.knowledgesList.remove(j);
                    continue;
                }
            }
        }
    }

    //-------------------------------------------------------------------------
    
    public String getIdUser() {
        return this.idUser;
    }
    
    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
    
    //-------------------------------------------------------------------------
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    //-------------------------------------------------------------------------
    
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void validateEmail(FacesContext context, UIComponent component, 
                                    Object value) {
        
        Boolean validation = true;
        String emailTmp = value.toString();
        
        if ((emailTmp.length() <= 3))
            validation = false;
        
        String tmp[] = emailTmp.split("@");
        if (tmp.length != 2)
            validation = false;
 
        if (!validation) {    
            FacesMessage message = new FacesMessage(Constant.GEN_MSG1, 
                                        Constant.ADD_USER_MSG3);
            throw new ValidatorException(message);
        }
    }
    
    //-------------------------------------------------------------------------
    
    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    //-------------------------------------------------------------------------
    
    public String getLogin() {
        return this.login;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }
    
    public void validateLogin(FacesContext context, UIComponent component, 
                                    Object value) {
        
        String loginTmp = value.toString();
        String preLogin = (String) getSession().getAttribute("preLogin");
        
        if (preLogin != null)
            if (loginTmp.equals(preLogin))
                return;
        
        if ((loginTmp.length() <= 3)) {
            
            FacesMessage message = new FacesMessage(Constant.GEN_MSG1, 
                                        Constant.ADD_USER_MSG4);
            throw new ValidatorException(message);
        }
        
        UserData userData = new UserData();
        
        try {
            if (userData.checkLoginExists(loginTmp)) {
                FacesMessage message = new FacesMessage(Constant.GEN_MSG1, 
                                            Constant.ADD_USER_MSG5+ "\"" +
                                            loginTmp + "\\"+ Constant.ADD_KNOWLEDGE_MSG3);
                throw new ValidatorException(message);
            }
        } catch(PersistenceException e) {
            System.out.println(e.getMessage());
        }
    }
    
    //-------------------------------------------------------------------------
    
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void validatePassword(FacesContext context, UIComponent component, 
                                    Object value) {
        
        String pass = value.toString();
        
        if (!pass.equals(""))
            if ((pass.length() <= 3)) {

                FacesMessage message = new FacesMessage(Constant.GEN_MSG1, 
                                            Constant.ADD_USER_MSG6);
                throw new ValidatorException(message);
            }
 
    }
    
    //-------------------------------------------------------------------------
    
    public ArrayList getAllTypes() {
        return this.allTypes;
    }

    public void setAllTypes(ArrayList allTypes) {
        this.allTypes = allTypes;
    }
    
    //-------------------------------------------------------------------------
    
    public DataModel getKnowledges() {
        return this.knowledges;
    }

    public void setKnowledges(DataModel knowledges) {
        this.knowledges = knowledges;
    }
    
    //-------------------------------------------------------------------------
    
    public Object[] getSelectedKnowledges() {
        
        return this.selectedKnowledges.toArray();
    }

    public void setSelectedKnowledges(Object[] newKnowledges) {
        
        this.selectedKnowledges.clear();
        this.selectedKnowledges = new ArrayList(newKnowledges.length);
        for (int i = 0; i < newKnowledges.length; i++) {
            this.selectedKnowledges.add(newKnowledges[i]);
        }
    }
    
    //-------------------------------------------------------------------------
    
    public void setKnowledgesList(ArrayList knowledges) {
        this.knowledgesList = knowledges;
    }
    
    public ArrayList getKnowledgesList() {
        return this.knowledgesList;
    }
        
    //-------------------------------------------------------------------------
    
    public boolean getHasKnowledgeList() {
        this.hasKnowledgeList = !getKnowledgesList().isEmpty();
        return this.hasKnowledgeList;
    }
    
    //-------------------------------------------------------------------------
    
    public boolean getIsAcessible() {
        return this.isAcessible;
    }
    
    public void setIsAcessible(boolean isAcessible) {
        this.isAcessible = isAcessible;
    }
    
    //-------------------------------------------------------------------------
    
    public boolean getCanBeRemoved() {
        return this.canBeRemoved;
    }
    
    public void setCanBeRemoved(boolean canBeRemoved) {
        this.canBeRemoved = canBeRemoved;
    }
    
    //-------------------------------------------------------------------------
    
    public String saveNew() {
        
        User usr = new User();
        
        usr.setAcessible(true);
        usr.setName(getName());
        usr.setEmail(getEmail());
        usr.setLogin(getLogin());
        usr.setPassword(getPassword());
        usr.setUserType(Integer.parseInt(getType()));
        usr.save();
        return "searchUsers";
    }
    
    //-------------------------------------------------------------------------
    
    public String updateThis() {
        
        User usr = new User(Integer.parseInt(
                (String)getSession().getAttribute("idUser")));  
        
        usr.setAcessible(getIsAcessible());
        usr.setName(getName());
        usr.setEmail(getEmail());
        usr.setLogin(getLogin());
        if (getPassword() != null && !getPassword().equals(""))
            usr.setPassword(getPassword());
        usr.update();
        
        getSession().removeAttribute("idUser");
        getSession().removeAttribute("preLogin");
        return "searchUsers";
    }
    
    //-------------------------------------------------------------------------
    
    public void addKnowledge() {
            
        try {
            Knowledge k = new Knowledge();
            
            for (int i=0; i<this.selectedKnowledges.size(); i++)
                k.insertRelateUserKnowledgeArea(idUser,
                        (String)this.selectedKnowledges.get(i));
            this.selectedKnowledges.clear();
        } catch (PersistenceException ex) {
            ex.printStackTrace();
        }

        getKnowledgeData();
    }
    
    //-------------------------------------------------------------------------
    
    public String back() {
        
        getSession().removeAttribute("idUser");
        getSession().removeAttribute("preLogin");
        return "removeUser";
    }
    
    //-------------------------------------------------------------------------
    
    public String back2() {
        
        getSession().removeAttribute("idUser");
        getSession().removeAttribute("preLogin");
        return "editUser";
    }
    
    //-------------------------------------------------------------------------
}
