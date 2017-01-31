package br.ufal.ic.forbile.autoria.core.util;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Message {

    /**
     * Add a error message in a context
     * 
     * @param errorMessage
     */
    public static void addError(String errorMessage, FacesContext context) {
        context.addMessage(null, 
                           new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                                            errorMessage, 
                                            errorMessage));
    }

    /**
     * Add a List of error messages in a context
     * 
     * @param errorsPageList
     */
    public static void addError(List<String> errorsPageList,
            FacesContext context) {

        for (String error : errorsPageList) {
            context.addMessage(null, 
                               new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                                                error, 
                                                error));
        }
    }

    /**
     * Add a message in a context
     * 
     * @param message
     */
    public static void addMessage(String message, FacesContext context) {
        context.addMessage(null, 
                            new FacesMessage(FacesMessage.SEVERITY_INFO, 
                                             message, 
                                             message));
    }
}
