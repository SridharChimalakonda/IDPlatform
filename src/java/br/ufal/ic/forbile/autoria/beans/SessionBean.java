package br.ufal.ic.forbile.autoria.beans;

import  br.ufal.ic.forbile.autoria.core.util.*;
import  br.ufal.ic.forbile.autoria.core.*;
import  javax.faces.context.ExternalContext;
import  javax.servlet.http.HttpServletRequest;
import  javax.servlet.http.HttpSession;
import  javax.faces.context.FacesContext;
import  javax.servlet.http.HttpServletResponse;
import  javax.servlet.http.HttpServlet;  
import  javax.servlet.ServletContext;

public abstract class SessionBean extends HttpServlet {
    
    protected ServletContext        servlet;
    protected FacesContext          context;
    protected ExternalContext       extContext;
    protected HttpServletResponse   response;
    protected HttpServletRequest    request;
    protected HttpSession           session;
    
    public SessionBean() {
        getPropertyParams();
    }
    
    
    private void getPropertyParams() {
        
        this.context = FacesContext.getCurrentInstance();
        this.extContext = this.context.getExternalContext();
        this.servlet = (ServletContext) this.extContext.getContext();
        this.request = (HttpServletRequest) this.extContext.getRequest();
        this.response = (HttpServletResponse) this.extContext.getResponse();
        
        this.session = ((HttpServletRequest) this.extContext.getRequest()).
                getSession(false);
    }
    
    public HttpSession getSession() {
        getPropertyParams();
        return this.session;
    }
    
    public HttpServletResponse getResponse() {
        getPropertyParams();
        return this.response;
    }
    
    public Object getRequest(String param) {
        getPropertyParams();
        return this.extContext.getRequestParameterMap().get(param);
    }
    
    public String getRealPath() {
        getPropertyParams();
        return this.servlet.getRealPath("/");
    }
    
    public String getFullURL() {
        getPropertyParams();
        return this.request.getScheme() + "://" + this.request.getServerName() +
                ":" + this.request.getServerPort() + this.servlet.getContextPath(); 
    }
    
    public void setErrorMsg(String msg) {
        Message.addError(msg,FacesContext.getCurrentInstance());
    }
    
    public User getAuthUser() {
        return (User) getSession().getAttribute("authenticatedUser");
    }
}
