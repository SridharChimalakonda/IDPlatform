package br.ufal.ic.forbile.autoria.beans;

import  br.ufal.ic.forbile.autoria.core.*;
import  br.ufal.ic.forbile.autoria.persistence.*;
import  br.ufal.ic.forbile.autoria.exceptions.*;
import  br.ufal.ic.forbile.autoria.core.util.*;
import  java.util.*;
import  java.io.IOException;
import  javax.faces.model.SelectItem;
import  javax.faces.model.DataModel;
import  javax.faces.model.ListDataModel;
//import  com.hp.hpl.jena.ontology.*;
//import  com.hp.hpl.jena.rdf.model.*;
//import  com.hp.hpl.jena.util.iterator.Filter;


public class EditSteps extends SessionBean {
    
    private ArrayList selectedClasses    = new ArrayList();
    private ArrayList classesList        = new ArrayList();
    private ArrayList allFieldsType;
    private ArrayList orderAppearList    = new ArrayList();
    
    private String    stepTitle;
    private String    stepDescription;
    
    private DataModel classes;
    private boolean   hasClasses;
    

    //-------------------------------------------------------------------------
    
    public EditSteps() {
        
        String idOntology = (String) getRequest("idOntology");
        String stepNum    = (String) getRequest("stepNum");
            
        // Controls the session and request/response calls
        if (idOntology == null || idOntology.equals("0") || idOntology.equals("") ||
                stepNum == null || stepNum.equals("0") || stepNum.equals("")) {

            if (idOntology == null || idOntology.equals("0") || idOntology.equals(""))
                idOntology = (String) getSession().getAttribute("idOntology");
            
            if (stepNum == null || stepNum.equals("0") || stepNum.equals(""))
                stepNum = (String) getSession().getAttribute("stepNum");
            
            if (idOntology == null || stepNum == null) {
                  
                try {
                    getResponse().sendRedirect("../edit.jsf");
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                    return;
                }
            }
        } else {            
            getSession().setAttribute("idOntology", idOntology);
            getSession().setAttribute("stepNum", stepNum);
        }
        
        // Get fieldsType from DB
        try{
            FieldData fieldTypes;
            fieldTypes           = new FieldData();
            this.allFieldsType   = fieldTypes.getFieldsType();
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        
        // Get classes from ontology
        this.getOntClasses();
        
        
        String tmpHasClasses = (String)getSession().getAttribute("hasClasses"); 
        if (tmpHasClasses != null)
            this.setHasClasses(Boolean.parseBoolean(tmpHasClasses));
        else
            this.setHasClasses(false);
    }
    
    //-------------------------------------------------------------------------
    
    public void setClassesList(ArrayList classes) {
        this.classesList = classes;
    }
    
    public ArrayList getClassesList() {
        return this.classesList;
    }

    //-------------------------------------------------------------------------
    
    public Object[] getSelectedClasses() {
        
        return this.selectedClasses.toArray();
    }

    public void setSelectedClasses(Object[] newClasses) {
        
        this.selectedClasses.clear();
        this.selectedClasses = new ArrayList(newClasses.length);
        for (int i = 0; i < newClasses.length; i++) {
            this.selectedClasses.add(newClasses[i]);
        }
    }

    //-------------------------------------------------------------------------
    
    public ArrayList getAllFieldsType() {
        return this.allFieldsType;
    }

    public void setAllFieldsType(ArrayList allFieldsType) {
        this.allFieldsType = allFieldsType;
    }
    
    //-------------------------------------------------------------------------
    
    public ArrayList getOrderAppearList() {
        return this.orderAppearList;
    }

    public void setOrderAppearList(ArrayList orderAppearList) {
        this.orderAppearList = orderAppearList;
    }
    
    //-------------------------------------------------------------------------
    
    public String getStepTitle() {
        return this.stepTitle;
    }
    
    public void setStepTitle(String stepTitle) {
        this.stepTitle = stepTitle;
    }
    
    //-------------------------------------------------------------------------
    
    public String getStepDescription() {
        return this.stepDescription;
    }
    
    public void setStepDescription(String stepDescription) {
        this.stepDescription = stepDescription;
    }
    
    //-------------------------------------------------------------------------
    
    public DataModel getClasses() {
        return this.classes;
    }
    
    public void setClasses(final DataModel classes) {
        this.classes = classes;
    }
    
    //-------------------------------------------------------------------------
    
    public boolean getHasClasses() {
        
        return this.hasClasses;
        //return !this.selectedClasses.isEmpty();
    }
    
    public void setHasClasses(boolean hasClasses) {
        this.hasClasses = hasClasses;
    }
    
    //-------------------------------------------------------------------------
    
    public void mountStep() {
        
        String idOntology = (String) getSession().getAttribute("idOntology");
            
        br.ufal.ic.forbile.autoria.core.Ontology ontology =
                    new br.ufal.ic.forbile.autoria.core.Ontology(
                    Integer.parseInt(idOntology));
        
        if (this.getSelectedClasses().length == 0)
            setErrorMsg(Constant.EDIT_STEPS_MSG1);
        else {
            getSession().setAttribute("hasClasses", "true");
            this.setHasClasses(true);
        }
        
        List classList = new ArrayList();
        for(int i=0; i<this.selectedClasses.size(); i++) {
            try{ 
                CreateStepClasses csc = new CreateStepClasses(
                    (String)this.selectedClasses.get(i),ontology.getOWLData());
                classList.add(csc);
            } catch (NullPointerException ex) {
                System.out.println(this.selectedClasses.get(i) +
                        Constant.EDIT_STEPS_MSG2);
                System.out.println(ex.getMessage());
            }
        }
        this.classes = new ListDataModel(classList);
        
        for(int i=0; i<this.selectedClasses.size(); i++) {
            this.orderAppearList.add(new SelectItem(
                    String.valueOf(i+1),String.valueOf(i+1)));
        }
        
    }
    
    //-------------------------------------------------------------------------
    
    private void saveNewAssistent() {
        
        ConfigAssistent ca = new ConfigAssistent();
        
        ca.setOntology(new br.ufal.ic.forbile.autoria.core.Ontology(
                Integer.parseInt(
                    ((String)getSession().getAttribute("idOntology")))));
        ca.setUserCreator((User)getSession().getAttribute("authenticatedUser"));
        ca.save();
    }
    
    //-------------------------------------------------------------------------
    
    private AssistentStep saveNewStep() {
            
        if (((String)getSession().getAttribute("stepNum")).equals("1"))
            saveNewAssistent();
        
        br.ufal.ic.forbile.autoria.core.Ontology ontology = 
                    new br.ufal.ic.forbile.autoria.core.Ontology(Integer.parseInt(
                        ((String)getSession().getAttribute("idOntology"))));
        
        User usr = (User)getSession().getAttribute("authenticatedUser");
        
        AssistentStep as = new AssistentStep();
        
        as.setOntology(ontology);
        as.setTitle(getStepTitle());
        as.setDescription(getStepDescription());
        as.setStepNum(Integer.parseInt((String)getSession().
                getAttribute("stepNum")));
        as.setUserWhoModified(usr);
        as.save();
        return as;
    }
    
    //-------------------------------------------------------------------------
    
    public String backAndCancel() {
        
        getSession().removeAttribute("hasClasses");
        getSession().removeAttribute("createStepsBean");
        getSession().removeAttribute("createStepClassesBean");
        getSession().removeAttribute("createStepClassPropertiesBean");
        return "back";
    }
    
    public String backAndCancel2() {
        
        getSession().removeAttribute("hasClasses");
        getSession().removeAttribute("createStepsBean");
        getSession().removeAttribute("createStepClassesBean");
        getSession().removeAttribute("createStepClassPropertiesBean");
        return "back2";
    }
    
    //-------------------------------------------------------------------------
    
    public String saveAndFinish() {
        
        if (!validate())
            return "";
        
        AssistentStep as = saveNewStep();
        for (int i=0; i<this.getClasses().getRowCount(); i++) {
            this.getClasses().setRowIndex(i);
            CreateStepClasses csc = (CreateStepClasses) this.getClasses().
                    getRowData();
            csc.setAssistentStep(as);
            csc.saveNew();
        }
        
        getSession().removeAttribute("hasClasses");
        getSession().removeAttribute("createStepsBean");
        getSession().removeAttribute("createStepClassesBean");
        getSession().removeAttribute("createStepClassPropertiesBean");
        return "finishedCreation";
    }
    
    //-------------------------------------------------------------------------
    
    public String saveAndContinue() {

        if (!validate())
            return "";
        
        AssistentStep as = saveNewStep();
        for (int i=0; i<this.getClasses().getRowCount(); i++) {
            this.getClasses().setRowIndex(i);
            CreateStepClasses csc = (CreateStepClasses) this.getClasses().
                    getRowData();
            csc.setAssistentStep(as);
            csc.saveNew();
        }
         
        getSession().removeAttribute("hasClasses");
        getSession().removeAttribute("createStepsBean");
        getSession().removeAttribute("createStepClassesBean");
        getSession().removeAttribute("createStepClassPropertiesBean");
        
        int stepNum = Integer.parseInt((String)getSession().
                getAttribute("stepNum"));
        getSession().setAttribute("stepNum", String.valueOf(stepNum+1));
        return "back";
    }
    
    //-------------------------------------------------------------------------
    
    public String updateStep() {
        
        
        
        return "finishedCreation";
    }
    
    //-------------------------------------------------------------------------
    
    public String removeStep() {
        
        br.ufal.ic.forbile.autoria.core.Ontology ontology = 
                    new br.ufal.ic.forbile.autoria.core.Ontology(Integer.parseInt(
                        ((String)getSession().getAttribute("idOntology"))));
        
        AssistentStep as = new AssistentStep(
                ontology.getConfigAssistent().getIdAssistent(), 
                Integer.parseInt((String)getSession().getAttribute("stepNum")));
        
        as.setOntology(ontology);
        as.remove();
        
        getSession().removeAttribute("hasClasses");
        getSession().removeAttribute("createStepsBean");
        getSession().removeAttribute("createStepClassesBean");
        getSession().removeAttribute("createStepClassPropertiesBean");
        return "finishedCreation";
    }
    
    //-------------------------------------------------------------------------
    
    private boolean validate() {
        
        List orderClasses = new ArrayList();
        for (int i=0; i<this.getClasses().getRowCount(); i++) {
            this.getClasses().setRowIndex(i);
            CreateStepClasses csc = (CreateStepClasses)this.getClasses().getRowData();
            // Checks if the object name of class is empty            
            if (csc.getObjectName().equals("") || csc.getObjectName() == null) {
                        setErrorMsg(Constant.CREATE_STEPS_MSG3 + csc.getClassName() + 
                                "\\"+ Constant.CREATE_STEPS_MSG4);
                        return false;
                    }
            // Checks the appear sort order of classes in a step
            if (orderClasses.contains(csc.getNumOrderAppear())) {
                setErrorMsg(Constant.CREATE_STEPS_MSG5 +
                        csc.getNumOrderAppear() + Constant.CREATE_STEPS_MSG6 +
                        Constant.CREATE_STEPS_MSG3+ csc.getClassName() + "\".");
                return false;
            } else {
                orderClasses.add(csc.getNumOrderAppear());
                // Now checks if the property field name is empty
                List properties = new ArrayList();
                for (int j=0; j<csc.getProperties().getRowCount(); j++) {
                    csc.getProperties().setRowIndex(j);
                    CreateStepClassProperties cscp = 
                            (CreateStepClassProperties) csc.getProperties().getRowData();
                    if (cscp.getFieldName().equals("") || cscp.getFieldName() == null) {
                        setErrorMsg(Constant.CREATE_STEPS_MSG3 + csc.getClassName() + 
                                Constant.CREATE_STEPS_MSG7 + 
                                cscp.getPropertyName() + "\".");
                        return false;
                    }
                    // And finally, checks the appear sort order of properties
                    if (properties.contains(cscp.getNumOrderAppear())) {
                        setErrorMsg(Constant.ADD_KNOWLEDGE_MSG3 + csc.getClassName() + 
                                Constant.CREATE_STEPS_MSG8 +
                        cscp.getNumOrderAppear() + Constant.CREATE_STEPS_MSG6 +
                        Constant.CREATE_STEPS_MSG9 + cscp.getPropertyName() + "\".");
                        return false;
                    } else {
                        properties.add(cscp.getNumOrderAppear());
                    }
                }
            }
        }
        return true;
    }
    //-------------------------------------------------------------------------
    
    private void getOntClasses() {
        
        String idOntology = (String) getSession().getAttribute("idOntology");
        ArrayList list = new ArrayList();
        
        try {
            OntologyData ontData = new OntologyData();
            list = ontData.getOntologyHierarchyClasses(idOntology);
        } catch(PersistenceException ex) {
            ex.printStackTrace();
        }
        
        getRecursiveSubClasses(list, "0", "");
        
        /* for (int x = 0; x < list.size(); x++) {
            if (((OntologyClass)list.get(x)).getSubClassOf().equals("0")) {
                
                String ontClassNameValue  = ((OntologyClass)list.get(x)).
                        getURI();
                String ontClassNameTabbed[] = ((OntologyClass)list.get(x)).
                        getURI().split("#");
                this.classesList.add(new SelectItem(ontClassNameValue, 
                        ontClassNameTabbed[1]));
                
            }
        } */
    }
    
    private void getRecursiveSubClasses(ArrayList list, String depth, String space) {
        
        for (int x = 0; x < list.size(); x++) {
            if (((OntologyClass)list.get(x)).getSubClassOf().equals(depth)) {
                
                String ontClassNameValue  = ((OntologyClass)list.get(x)).
                        getURI();
                String ontClassNameTabbed[] = ((OntologyClass)list.get(x)).
                        getURI().split("#");
                this.classesList.add(new SelectItem(ontClassNameValue, 
                        space + " " + ontClassNameTabbed[1]));
                getRecursiveSubClasses(list, ((OntologyClass)list.get(x)).
                        getIdOntologyClass(), space+"---");
            }
        }
    }
    /*
    private void getOntClasses() {

        String idOntology = (String) getSession().getAttribute("idOntology");

        br.ufal.ic.forbile.autoria.core.Ontology newOntology =
                new br.ufal.ic.forbile.autoria.core.Ontology(
                Integer.parseInt(idOntology));

        List orderClasses = new LinkedList();
        Iterator i = newOntology.getOWLfile().listHierarchyRootClasses().filterDrop(
                new Filter() {

                    public boolean accept(Object o) {
                        return ((Resource) o).isAnon();
                    }
                });

        while (i.hasNext()) {
            orderClasses.add(((OntClass) i.next()).getURI());
        //getHierarchyClassNames( (OntClass) i.next(), new ArrayList(), 0 );
        }
        Collections.sort(orderClasses);

        for (int x = 0; x < orderClasses.size(); x++) {
            OntClass tmp = newOntology.getOWLfile().
                    getOntClass((String) orderClasses.get(x));
            getHierarchyClassNames(tmp, new ArrayList(), 0);
        }
    }
    */
    //-------------------------------------------------------------------------
    /*
    private void getHierarchyClassNames(OntClass cls, List occurs, int depth) {
        
        String ontClassNameTabbed = "";
        String ontClassNameValue;
        
        for (int i = 0;  i < depth; i++)
            ontClassNameTabbed += "----";
        ontClassNameTabbed += cls.getModel().shortForm(cls.getURI()).replace(":", " ");
        ontClassNameValue   = cls.getURI();
        //ontClassNameValue   = cls.getURI();
        this.classesList.add(new SelectItem(ontClassNameValue, ontClassNameTabbed));

        // recurse to the next level down
        if (cls.canAs(OntClass.class) && !occurs.contains(cls)) {
            
            String idOntology = (String) getSession().getAttribute("idOntology");

            br.ufal.ic.forbile.autoria.core.Ontology newOntology =
                    new br.ufal.ic.forbile.autoria.core.Ontology(
                    Integer.parseInt(idOntology));
            
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
                getHierarchyClassNames(tmp, occurs, depth+1);
                occurs.remove(cls);
            }
            /*
            for (Iterator i = cls.listSubClasses(true); i.hasNext();) {
                
                OntClass sub = (OntClass) i.next();
                // we push this expression on the occurs list before we recurse
                occurs.add(cls);
                getHierarchyClassNames(sub, occurs, depth+1);
                occurs.remove(cls);
            }
        }
    }
    */
    //-------------------------------------------------------------------------
}
