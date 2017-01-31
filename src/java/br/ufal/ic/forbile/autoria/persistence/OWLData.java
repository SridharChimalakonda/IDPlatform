package br.ufal.ic.forbile.autoria.persistence;

import br.ufal.ic.forbile.autoria.core.util.Constant;
import  br.ufal.ic.forbile.autoria.exceptions.*;

import  javax.faces.context.ExternalContext;
import  javax.faces.context.FacesContext;
import  javax.servlet.http.HttpServletRequest;
import  javax.servlet.http.HttpSession;
import  javax.servlet.ServletContext;

import  java.util.*;

import  com.hp.hpl.jena.ontology.*;
import  com.hp.hpl.jena.rdf.model.*;
import  com.hp.hpl.jena.util.iterator.Filter;
import java.io.File;

public class OWLData {

    private int             idOntology;
    private OntModel        owlFile;
    private int             classesCount;
    
    public OWLData() {
        
        this.classesCount = 0;
    }
    
    public OWLData(int idOntology) {
        
        this.classesCount = 0;
        this.setOntology(idOntology);
    }
 
    public boolean hasFileLoaded() {
        return (this.owlFile == null? false : true);
    }
    
    public void setOntology(String idOntology) {
        this.setOntology(Integer.parseInt(idOntology));
    }
    
    public void setOntology(int idOntology) {
        this.idOntology = idOntology;
        this.loadOWLfile(idOntology);
    }

    private void loadOWLfile(int idOntology) {

        // Get the session data and path to save the file
        ExternalContext exContext = FacesContext.getCurrentInstance().
                getExternalContext();
        HttpServletRequest request =
                (HttpServletRequest) exContext.getRequest();
        HttpSession session = request.getSession(false);

        /*String ontFile = request.getScheme() + "://" +
                request.getServerName() + ":" +
                request.getServerPort() + ((ServletContext) FacesContext.
                getCurrentInstance().getExternalContext().getContext()).
                getContextPath() + "/owl/ontology" +
                String.valueOf(idOntology) + ".owl";*/
        
        String ontFile = new File ("C:" + "/owl/ontology" + String.valueOf(idOntology) + ".owl").toURI().toString();

        this.owlFile = ModelFactory.createOntologyModel(
                OntModelSpec.OWL_MEM, null);
        this.owlFile.read(ontFile);
    }
    
    private void getClassesToDatabase() {
        
        List orderClasses = new LinkedList();
        Iterator i = this.owlFile.listHierarchyRootClasses().filterDrop(
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
            OntClass tmp = this.owlFile.getOntClass((String) orderClasses.get(x));
            getHierarchyClassNames(tmp, new ArrayList(), 0);
        }
    }
    
    private void getHierarchyClassNames(OntClass cls, List occurs, int depth) {
        
        OntologyData ontData = new OntologyData();
        int newDepth = 0;

        try{
            newDepth = ontData.insertNewOntologyClass(
                    String.valueOf(this.idOntology), cls.getURI(), 
                    String.valueOf(depth));
            this.classesCount++;
        } catch (PersistenceException e) {
            System.out.println(e.getMessage());
        }
        
        // recurse to the next level down
        if (cls.canAs(OntClass.class) && !occurs.contains(cls)) {
            
            List orderSubClasses = new LinkedList();
            Iterator i = cls.listSubClasses(true);
            while (i.hasNext()) {
                orderSubClasses.add(((OntClass) i.next()).getURI());
            }
            Collections.sort(orderSubClasses);
            
            for (int x=0; x<orderSubClasses.size(); x++) {
                OntClass tmp = this.owlFile.getOntClass((String) orderSubClasses.get(x));
                
                occurs.add(cls);
                getHierarchyClassNames(tmp, occurs, newDepth);
                occurs.remove(cls);
            }
        }
    }
    
    public List getClassProperties(String classURI) {
        
        OntClass ontClass = this.owlFile.getOntClass(classURI);
        
        List allProps = new LinkedList();
        
        for (Iterator i2 = ontClass.listSuperClasses(true); i2.hasNext();) {
            OntClass c = (OntClass) i2.next();
            if (c.isRestriction()) {
                Resource rc = (Resource) c.asRestriction().getOnProperty();
                if (!allProps.contains(rc.getURI())) {
                    allProps.add(rc.getURI());
                }
            }
        }

        for (Iterator i2 = ontClass.listSubClasses(true); i2.hasNext();) {
            OntClass c = (OntClass) i2.next();
            if (c.isRestriction()) {
                Resource rc = (Resource) c.asRestriction().getOnProperty();
                if (!allProps.contains(rc.getURI())) {
                    allProps.add(rc.getURI());
                }
            }
        }

        Collections.sort(allProps);
        
        return allProps;
    }
    
    public List getIndividuals(String URI) {
        
        OntClass ontClass = this.owlFile.getOntClass(URI);
        
        List sortedIndividuals = new LinkedList();
        
        for (Iterator i = ontClass.listInstances(true); i.hasNext(); ) {
            Individual idv = (Individual) i.next();
            sortedIndividuals.add(idv.getURI());
        }
        Collections.sort(sortedIndividuals);
        
        return sortedIndividuals;
    } 
    
    public List getSomeValuesFromRestriction(String URI) {
        
        System.out.println(Constant.OWL_DATA_MSG1 + URI);
        
        //OntProperty p = this.owlFile.getOntProperty(URI);
        
        //p.listDomain()

        //SomeValuesFromRestriction values = this.owlFile.getSomeValuesFromRestriction(URI);
        
        List sortedIndividuals = new LinkedList();
        
        /*if (p != null) {
            for (Iterator i = p.listDomain(); i.hasNext(); ) {
                Individual idv = (Individual) i.next();
                sortedIndividuals.add(idv.getURI());
            }
            Collections.sort(sortedIndividuals);
        } */
        
        System.out.println(Constant.OWL_DATA_MSG2);
        return sortedIndividuals;
    }
    
    public int getClassCount() {
        
        if (this.classesCount == 0)
            this.getClassesToDatabase();
        return this.classesCount;
    }
    
    public String getClassShortName(String URI) {
        
        return this.owlFile.getOntClass(URI).getModel().shortForm(URI).
                replace(":", "");
    }
    
}
