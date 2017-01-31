package br.ufal.ic.forbile.autoria.core;

import br.ufal.ic.forbile.autoria.core.util.Constant;
import  br.ufal.ic.forbile.autoria.persistence.*;
import  br.ufal.ic.forbile.autoria.exceptions.*;
import  java.util.*;
import  java.security.*;


public class User {
    
    private int     idUser;
    private int     userType;
    private String  name;
    private String  email;
    private String  login;
    private String  pass;
    private boolean isAcessible;
    private String  dateCreation;

    
    public User() {
        
    }
    
    public User(int idUser) {
        
        UserData    userData = new UserData();
        try {
            ArrayList   data    = userData.getUserById(idUser);
            
            this.setIdUser(idUser);
            this.setName((String)data.get(0));
            this.setEmail((String)data.get(1));
            this.setLogin((String)data.get(2));
            this.setPassword((String)data.get(3));
            this.setUserType(Integer.parseInt((String)data.get(4)));
                        
            if (data.get(5).equals("0"))
                this.setAcessible(false);
            else
                this.setAcessible(true);
            
            this.setDateCreation((String)data.get(6));
            
        } catch (PersistenceException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //-------------------------------------------------------------------------
   
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
    
    public int getIdUser() {
        return this.idUser;
    }
    
    //-------------------------------------------------------------------------
    
    public void setUserType(int userType) {
        this.userType = userType;
    }
    
    public int getUserType() {
        return this.userType;
    }
    
    public String getUserTypeName() {
        return ((this.userType == 1)? Constant.USER_MSG1 : Constant.USER_MSG2);
    }
    
    //-------------------------------------------------------------------------
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    //-------------------------------------------------------------------------
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    //-------------------------------------------------------------------------
    
    public void setLogin(String login) {
        this.login = login;
    }
    
    public String getLogin() {
        return this.login;
    }
    
    //-------------------------------------------------------------------------
    
    public void setPassword(String password) {
        this.pass = password;
    }
    
    public String getPassword() {
        return this.pass;
    }
    
    //-------------------------------------------------------------------------
    
    public void setAcessible(boolean isAcessible) {
        this.isAcessible = isAcessible;
    }
    
    public boolean isAcessible() {
        return this.isAcessible;
    }
    
    public String getAcessible() {
        return (isAcessible() ? "1" : "0");
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
    
    public ArrayList getRelatedKnowledgeAreas() {
        
        ArrayList tmp = new ArrayList();
        
        if (getUserType() == 2) {
            
            try{
                Knowledge k = new Knowledge();
                tmp = k.getKnowledgeAreaOfUser(getIdUser());
            } catch (PersistenceException ex) {
                ex.printStackTrace();
            } 
        }
        
        return tmp;
    }
    
    //-------------------------------------------------------------------------
    
    public void save() {
        
        UserData    userData = new UserData();
        
        try {
            setIdUser(userData.insertNewUser(String.valueOf(getUserType()), 
                    getName(), getEmail(), getLogin(), toPassword(getPassword()), 
                    getAcessible()));
        } catch (PersistenceException ex) {
            System.out.println(ex.getMessage());
        } catch (NoSuchAlgorithmException ex2) {
            System.out.println(ex2.getMessage());
        }
    }
    
    //-------------------------------------------------------------------------
    
    public void update() {
        
        UserData    userData = new UserData();      
        String      tmpPass  = getPassword();
        
        try {   
            if (getPassword().length() != 32)
                tmpPass = toPassword(getPassword());
                    
            userData.updateUser(String.valueOf(getIdUser()),
                    String.valueOf(getUserType()), 
                    getName(), getEmail(), getLogin(), tmpPass, 
                    getAcessible());
        } catch (PersistenceException ex) {
            System.out.println(ex.getMessage());
        } catch (NoSuchAlgorithmException ex2) {
            System.out.println(ex2.getMessage());
        }
    }
    
    //-------------------------------------------------------------------------
    
    public void delete() {
        
        UserData    userData = new UserData();
        
        try{
            userData.removeUser(String.valueOf(getIdUser()));
        } catch (PersistenceException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    //-------------------------------------------------------------------------
    
    private static String bytesToHex(byte[] b) {
        
        StringBuffer sb = new StringBuffer();
        for (int i=0; i < b.length; ++i) {
            sb.append((Integer.toHexString((b[i] & 0xFF) | 0x100)).substring(1,3));
        }
        return sb.toString();
    }
    
    //-------------------------------------------------------------------------
   
    private static String toPassword(String data) 
            throws NoSuchAlgorithmException {
        
        byte[] mybytes = data.getBytes();
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] md5digest = md5.digest(mybytes);
        return bytesToHex(md5digest);
    }
}
