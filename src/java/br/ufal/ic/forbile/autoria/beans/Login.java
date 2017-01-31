package br.ufal.ic.forbile.autoria.beans;

import  javax.faces.context.ExternalContext;
import  javax.faces.context.FacesContext;
import  javax.servlet.http.HttpServletRequest;
import  javax.servlet.http.HttpSession;
import  javax.faces.validator.ValidatorException;
import  javax.faces.application.FacesMessage;

import  br.ufal.ic.forbile.autoria.core.*;
import  br.ufal.ic.forbile.autoria.core.util.*;
import  br.ufal.ic.forbile.autoria.persistence.*;
import  br.ufal.ic.forbile.autoria.exceptions.*;

import  java.security.*;


public class Login {
    
    private String strLogin;
    private String strPassword;
    
    //-------------------------------------------------------------------------
    
    public Login() {
    }
    
    //-------------------------------------------------------------------------
    
    public String getStrLogin() {
        return strLogin;
    }
    
    public void setStrLogin(String strLogin) {
        this.strLogin = strLogin;
    }
    
    //-------------------------------------------------------------------------
    
    public String getStrPassword() {
        return strPassword;
    }
    
    public void setStrPassword(String strPassword) {
        this.strPassword = strPassword;
    }
    
    //-------------------------------------------------------------------------

    public String doAuthentication() {
        
        User objectUser = new User();

        try {
            Authentication authentication = new Authentication();

            ExternalContext context     = FacesContext.getCurrentInstance().
                    getExternalContext();
            HttpServletRequest request  = (HttpServletRequest)context.
                    getRequest();
            HttpSession session         = request.getSession(false);

            /* Invalidate a previous session, if exists... */
            if (session != null) {
                session.invalidate();
            }
            
            session = request.getSession(true);
            
            authentication.authenticate(this.strLogin,
                    toPassword(this.strPassword));
            
            objectUser = authentication.getUser();
            session.setAttribute(Constant.AUT_USER, objectUser);
                       
            this.strLogin = null;
            this.strPassword = null;
              
            return Constant.SUCCESS;       
        } catch (AuthenticationException e) {
            Message.addError(e.getMessage(),
                    FacesContext.getCurrentInstance());
            this.strLogin = null;
            this.strPassword = null;
            return Constant.FAILURE;
        } catch (PersistenceException e) {
            Message.addError(e.getMessage(),
                    FacesContext.getCurrentInstance());
            this.strLogin = null;
            this.strPassword = null;
            return Constant.FAILURE;
        } catch (NoSuchAlgorithmException e) {
            Message.addError(e.getMessage(),
                    FacesContext.getCurrentInstance());
            this.strLogin = null;
            this.strPassword = null;
            return Constant.FAILURE;
        }
    }
    
    //-------------------------------------------------------------------------
    
    public String doDisauthentication() {
                
        ExternalContext context     = FacesContext.getCurrentInstance().
                getExternalContext();
        HttpServletRequest request  = (HttpServletRequest)context.
                getRequest();
        HttpSession session         = request.getSession(false);
        
        session.invalidate();
        return Constant.LOGIN_PAGE;
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
