package br.ufal.ic.forbile.autoria.core;

import br.ufal.ic.forbile.autoria.persistence.*;


public class OntologyClass {

    private String idOntologyClass;
    private String URI;
    private String subClassOf;
    
    
    public OntologyClass(String idOntologyClass, String URI, String subClassOf) {
        
        setIdOntologyClass(idOntologyClass);
        setURI(URI);
        setSubClassOf(subClassOf);
    }
    
    public String getIdOntologyClass() {
        return this.idOntologyClass;
    }
    
    public void setIdOntologyClass(String idOntologyClass) {
        this.idOntologyClass = idOntologyClass;
    }
    
    public String getURI() {
        return this.URI;
    }
    
    public void setURI(String URI) {
        this.URI = URI;
    }
    
    public String getSubClassOf() {
        return this.subClassOf;
    }
    
    public void setSubClassOf(String subClassOf) {
        this.subClassOf = subClassOf;
    }
}
