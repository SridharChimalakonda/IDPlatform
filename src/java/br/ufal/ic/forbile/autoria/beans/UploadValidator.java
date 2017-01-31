package br.ufal.ic.forbile.autoria.beans;

import br.ufal.ic.forbile.autoria.core.util.Constant;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.apache.myfaces.custom.fileupload.UploadedFile;
//import java.lang.System;

public class UploadValidator implements Validator {
    
    public UploadValidator() {
                
    }
    
    private boolean validateFileType(UploadedFile _file) {
        
        String extension = _file.getName().substring(_file.getName().length()-3);
        String contentType  = _file.getContentType();    
        
        //System.out.println("Extensão: " + extension);
        //System.out.println("Tipo: " + contentType);
        //return ((extension.equals("owl") && 
        //        (contentType.equals("text/plain")))? true : false);
        return ((extension.equals("owl"))? true : false);
        
        //(contentType.equals("application/octet-stream") || 
    }
    
    public void validate(FacesContext facesContext, UIComponent uIComponent,
            Object object) throws ValidatorException {
        
        FacesMessage message;
        UploadedFile file = (UploadedFile) object;
                
        if (!validateFileType(file)) {
            //System.out.println("Deu erro!");
            message = new FacesMessage();
            message.setSummary(Constant.UPLOAD_VALIDATOR_MSG1);
            message.setDetail(Constant.UPLOAD_VALIDATOR_MSG2 + file.getName() + Constant.UPLOAD_VALIDATOR_MSG3);
            throw new ValidatorException(message);
        }                
    }
}
