package br.ufal.ic.forbile.autoria.beans;

import  br.ufal.ic.forbile.autoria.core.*;
import  br.ufal.ic.forbile.autoria.persistence.*;
import  br.ufal.ic.forbile.autoria.exceptions.*;
import  br.ufal.ic.forbile.autoria.core.util.*;

import  java.util.*;

import  org.apache.myfaces.custom.fileupload.UploadedFile;
import  javax.faces.model.SelectItem;
import  java.io.File;
import  java.io.FileOutputStream;
import  java.io.IOException;
import  java.io.BufferedInputStream;

import  javax.faces.context.FacesContext;
import  javax.faces.application.FacesMessage;
import  javax.faces.component.UIComponent;
import  javax.faces.validator.ValidatorException;

//import  javax.servlet.http.HttpServletRequest;
//import  javax.servlet.http.HttpServletResponse;

//import  com.hp.hpl.jena.ontology.*;
//import  com.hp.hpl.jena.rdf.model.*;
//import  com.hp.hpl.jena.util.iterator.Filter;


public class AddOntology extends SessionBean {
    
    private UploadedFile    file;
    private String          ontName;
    private String          ontDescription;
    private String          relatedKnowArea;
    
    private ArrayList       allKnowAreas;
   
    //private String          idOntology;
    private int             count;
    
    //-------------------------------------------------------------------------
    
    public AddOntology() {
        
        if (getRequest("idOntology") != null) {
            String idOntology = (String) getRequest("idOntology");
            getSession().setAttribute("idOntology", idOntology);
        }
        
        try{
            Knowledge knowledge;
            
            knowledge            = new Knowledge();
            this.allKnowAreas    = knowledge.getAllKnowledgeAreas(); 
        } catch (PersistenceException e) {
            this.allKnowAreas   = new ArrayList();
            this.allKnowAreas.add(new SelectItem("nenhum",
                    Constant.ADD_ONTOLOGY_MSG1));
        }
        
    }
    
    //-------------------------------------------------------------------------
    
    public UploadedFile getFile() {  
        return this.file;  
    }  
   
    public void setFile(UploadedFile file) {
        this.file = file;  
    }  
    
    //-------------------------------------------------------------------------
    
    public String getOntName() {
        return this.ontName;
    }
    
    public void setOntName(String ontName) {
        this.ontName = ontName;
    }
    
    public void validateOntName(FacesContext context, UIComponent component, 
                                    Object value) {
        
        String name = value.toString();
        
        if ((name.length() <= 3)) {
            FacesMessage message = new FacesMessage(Constant.ADD_KNOWLEDGE_MSG1, 
                                        Constant.ADD_ONTOLOGY_MSG2);
            throw new ValidatorException(message);
        }
        
        OntologyData ontData = new OntologyData();
        
        try {
            if (ontData.checkOntologyName(name)) {
                FacesMessage message = new FacesMessage(Constant.ADD_KNOWLEDGE_MSG1, 
                                            Constant.ADD_ONTOLOGY_MSG3+ "\\" +
                                            name + Constant.ADD_KNOWLEDGE_MSG3);
                throw new ValidatorException(message);
            }
        } catch(PersistenceException e) {
            System.out.println(e.getMessage());
        }
    }
    
    //-------------------------------------------------------------------------
    
    public String getOntDescription() {
        return this.ontDescription;
    }
    
    public void setOntDescription(String ontDescription) {
        this.ontDescription = ontDescription;
    }
    
    //-------------------------------------------------------------------------
    
    public String getRelatedKnowArea() {
        return this.relatedKnowArea;
    }
    
    public void setRelatedKnowArea(String relatedKnowArea) {
        this.relatedKnowArea = relatedKnowArea;
    }
    
    //-------------------------------------------------------------------------
    
    public ArrayList getAllKnowAreas() {
        return this.allKnowAreas;
    }

    public void setAllKnowAreas(ArrayList allKnowAreas) {
        this.allKnowAreas = allKnowAreas;
    }
    
    //-------------------------------------------------------------------------
    
    public String doSaveNew() {

        OntologyData    ontData    = new OntologyData();
        int             idCreatedOntology = 0;
        String          idUserCreator;
        String          fileName;

        idUserCreator = String.valueOf(((User)getSession().
                getAttribute("authenticatedUser")).getIdUser());
                
        try {
            
            // Inserts a new row in MySQL database and get the id
            idCreatedOntology = ontData.insertNewOntology(this.ontName,
                    this.ontDescription, idUserCreator, this.relatedKnowArea);
            //this.idOntology = String.valueOf(idCreatedOntology);
            getSession().setAttribute("idOntology",  String.valueOf(idCreatedOntology));
            
            // With Ontology id, saves the file
            fileName = "/owl/ontology" + String.valueOf(idCreatedOntology) + 
                    ".owl";
            
            //File newFile = new File(getRealPath() + fileName);
            
             File newFile = new File("C:\\" + fileName);
            
            BufferedInputStream bufferedInputStream = 
                    new BufferedInputStream(this.file.getInputStream());
            FileOutputStream fileOutputStream = new FileOutputStream(newFile);
            
            try {
                byte[] buffer = new byte[1024];
                int countx;
                while ((countx = bufferedInputStream.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, countx);
                }
            } finally {
                bufferedInputStream.close();
                fileOutputStream.close();
            }

        } catch (PersistenceException e) {
            setErrorMsg(Constant.ADD_ONTOLOGY_MSG4+ "\\" + 
                    this.ontName + "\\" + Constant.ADD_ONTOLOGY_MSG5 + "<br><br>" +
                    e.getMessage());
            return ""; 
        } catch (IOException e) {
            try{
                ontData.removeOntologyOnSaveMethod(String.valueOf(idCreatedOntology));
            } catch (PersistenceException ex) {
                System.out.println(ex.getMessage());
            }
            
            setErrorMsg(Constant.ADD_ONTOLOGY_MSG6+ 
                    this.ontName  + "\""+ Constant.ADD_ONTOLOGY_MSG5 +"<br><br>" +
                    e.getMessage());
            
            return ""; 
        }
              
        // With file saved, get the count of all classes and save in MySQL Database
        //OntModel m = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, 
        //        null);

        //m.read(getFullURL() + fileName);
        OWLData owl = new OWLData(idCreatedOntology);
        this.count = owl.getClassCount();
        
        // Finally, save the count value
        try{
            ontData.updateOntologyWithClassesCount(idCreatedOntology, this.count);
        } catch (PersistenceException e) {                        
            setErrorMsg(Constant.ADD_ONTOLOGY_MSG4 + 
                    this.ontName  + "\\"+ Constant.ADD_ONTOLOGY_MSG5 + "<br><br>" +
                    e.getMessage());
            return "";
        }

        getSession().removeAttribute("idOntology");
        getSession().removeAttribute("addOntologyBean");
        return "configOntology";
    }
    
    //-------------------------------------------------------------------------
    
    public String removeOntology() {

        OntologyData ontData = new OntologyData();
        
        try{
            ontData.removeOntology((String)getSession().getAttribute("idOntology"));
        } catch (PersistenceException e) {
            br.ufal.ic.forbile.autoria.core.Ontology temp = 
                    new br.ufal.ic.forbile.autoria.core.Ontology(
                    Integer.parseInt((String)getSession().getAttribute("idOntology")));
            
            setErrorMsg(Constant.ADD_ONTOLOGY_MSG7 +"\\" + 
                    temp.getName()  + "\\" + Constant.ADD_ONTOLOGY_MSG5+ "<br><br>" +
                    e.getMessage());
            return "";
        }
        
        getSession().removeAttribute("idOntology");
        getSession().removeAttribute("addOntologyBean");
        return "configOntology";
    }
    
    //-------------------------------------------------------------------------
    
    public String back() {
        
        getSession().removeAttribute("idOntology");
        getSession().removeAttribute("addOntologyBean");
        return "configOntology";
    }
    
    //-------------------------------------------------------------------------
    /*
    private void getOntClasses() {

        br.ufal.ic.forbile.autoria.core.Ontology newOntology =
                new br.ufal.ic.forbile.autoria.core.Ontology(
                Integer.parseInt((String)getSession().getAttribute("idOntology")));

        List orderClasses = new LinkedList();
        Iterator i = newOntology.getOWLfile().listHierarchyRootClasses().filterDrop(
                new Filter() {
                    public boolean accept(Object o) {
                        return ((Resource) o).isAnon();
                    }
                });

        while (i.hasNext()) {
            orderClasses.add(((OntClass) i.next()).getURI());
        }
        Collections.sort(orderClasses);

        for (int x = 0; x < orderClasses.size(); x++) {
            OntClass tmp = newOntology.getOWLfile().
                    getOntClass((String) orderClasses.get(x));
            getHierarchyClassNames(tmp, new ArrayList(), 0);
        }
    }
    
    //-------------------------------------------------------------------------
    
    private void getHierarchyClassNames(OntClass cls, List occurs, int depth) {
        
        OntologyData ontData = new OntologyData();
        int newDepth = 0;

        try{
            newDepth = ontData.insertNewOntologyClass(
                    (String)getSession().getAttribute("idOntology"), cls.getURI(), 
                    String.valueOf(depth));
            this.count++;
        } catch (PersistenceException e) {
            System.out.println(e.getMessage());
        }
        
        // recurse to the next level down
        if (cls.canAs(OntClass.class) && !occurs.contains(cls)) {

            br.ufal.ic.forbile.autoria.core.Ontology newOntology =
                    new br.ufal.ic.forbile.autoria.core.Ontology(
                    Integer.parseInt((String)getSession().getAttribute("idOntology")));
            
            List orderSubClasses = new LinkedList();
            Iterator i = cls.listSubClasses(true);
            while (i.hasNext()) {
                orderSubClasses.add(((OntClass) i.next()).getURI());
            }
            Collections.sort(orderSubClasses);
            
            for (int x=0; x<orderSubClasses.size(); x++) {
                OntClass tmp = newOntology.getOWLfile().
                    getOntClass((String) orderSubClasses.get(x));
                
                occurs.add(cls);
                getHierarchyClassNames(tmp, occurs, newDepth);
                occurs.remove(cls);
            }
        }
    } */
    //-------------------------------------------------------------------------
}
