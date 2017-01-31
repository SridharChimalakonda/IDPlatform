package br.ufal.ic.forbile.autoria.persistence;

import  br.ufal.ic.forbile.autoria.exceptions.*;
import  br.ufal.ic.forbile.autoria.core.*;
import  br.ufal.ic.forbile.autoria.core.util.*;
import  java.sql.SQLException;
import  java.util.*;

public class OntologyData extends DatabaseConnection {
          
    public OntologyData() {
    }
    
    public int insertNewOntology(String name, String description, 
            String idUserCreator, String idKnowledgeArea) throws 
            PersistenceException {
        
        try{
            this.Connect();
            this.statement = getConnection().createStatement();
            
            this.statement.executeUpdate(
                    "INSERT INTO ontology VALUES (NULL, " +
                    idKnowledgeArea + ", '" +
                    name + "', '" +
                    description + "', 0, '0', " +
                    idUserCreator + ", NOW(), 0)");

            this.resultSet = this.statement.executeQuery(
                    "SELECT idOntology " +
                    "FROM   ontology " +
                    "WHERE name = '" + name +
                    "' AND description = '" + description +
                    "' AND idUserCreator = " + idUserCreator);

            this.resultSet.next();
            
            int idOntology = this.resultSet.getInt("idOntology");
            
            this.statement.executeUpdate(
                    "INSERT INTO user_modified_ontology VALUES (" +
                    idUserCreator + ", " +
                    idOntology + ", " +
                    "NOW())");
            
            return idOntology;
            
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
    
    public int insertNewOntologyClass(String idOntology, String URI, 
            String subClass) throws PersistenceException {
                
        try{
            this.Connect();
            this.statement = getConnection().createStatement();
            
            this.resultSet = this.statement.executeQuery(
                    "SELECT idOntologyClass " +
                    "FROM   ontology_class " +
                    "WHERE idOntology = " + idOntology +
                    " AND URI = '" + URI + "'");
            
            if (this.resultSet.next()) {
                return this.resultSet.getInt("idOntologyClass");
            } else {
                
                this.statement.executeUpdate(
                        "INSERT INTO ontology_class VALUES (NULL, " +
                        idOntology + ", '" + URI + "', " + subClass + ")");

                this.resultSet = this.statement.executeQuery(
                        "SELECT idOntologyClass " +
                        "FROM   ontology_class " +
                        "WHERE idOntology = " + idOntology +
                        " AND URI = '" + URI + "'");

                this.resultSet.next();

                return this.resultSet.getInt("idOntologyClass");
            }
            
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
    
    public void removeOntology(String idOntology) throws 
            PersistenceException {
       
        try{
            this.Connect();
            this.statement = getConnection().createStatement();
            
            this.statement.executeUpdate(
                    "UPDATE ontology SET isRemoved = '1' WHERE idOntology = " + 
                    idOntology);
 
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
    
    public void updateOntologyWithClassesCount(int idOntology, 
            int count) throws PersistenceException {
       
        try{
            this.Connect();
            this.statement = getConnection().createStatement();
            
            this.statement.executeUpdate(
                    "UPDATE ontology SET qtdClasses = '" + 
                    String.valueOf(count) + "' WHERE idOntology = " 
                    + String.valueOf(idOntology));
 
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
    
    public void removeOntologyOnSaveMethod(String idOntology) throws 
            PersistenceException {
       
        try{
            this.Connect();
            this.statement = getConnection().createStatement();
            
            this.statement.executeUpdate(
                    "DELETE FROM ontology WHERE idOntology = " + idOntology);
            
            this.statement.executeUpdate(
                    "DELETE FROM user_modified_ontology WHERE idOntology = " +
                    idOntology);            
 
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
    
    public ArrayList searchOntologies(String searchKey) throws 
            PersistenceException {
        
        return searchOntologies(searchKey, 0);
    }
    
    public ArrayList searchOntologies(String searchKey, 
             int userId) throws PersistenceException {
       
        ArrayList results = new ArrayList();
        
        try{
            this.Connect();
            this.statement = getConnection().createStatement();
            
            String query = 
                    "SELECT idOntology " +
                    "FROM   ontology " +
                    "WHERE name LIKE '%" + searchKey + "%' ";
            
            if (userId != 0) {
                query += "AND idKnowledgeArea IN (SELECT idKnowledgeArea FROM ";
                query += "user_has_knowledge_area WHERE idUser = " + userId + ") ";
                query += "AND canBeAcessed = 1 ";
            }
            
            query+= "AND isRemoved = 0 " +
                    "ORDER BY dateCreation DESC";
            
            this.resultSet = this.statement.executeQuery(query);
            int i = 0;
            do {
                if (this.resultSet.next()) {
                    results.add(new Ontology(
                            this.resultSet.getInt("idOntology")));
                    i = 1;
                } else {
                    if (i == 1)
                        i = 0;
                }
            } while (i != 0);
            
            return results;
            
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
    
    public boolean checkOntologyName(String name) throws 
            PersistenceException {
        
        try{
            this.Connect();
            this.statement = getConnection().createStatement();
            
            this.resultSet = this.statement.executeQuery(
                    "SELECT idOntology " +
                    "FROM   ontology " +
                    "WHERE  name = '" + name + "' " +
                    "AND isRemoved = 0");
            
            if (this.resultSet.next()) 
                return true;
            else
                return false;
            
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
    
    public ArrayList searchLastModOntologies() throws 
            PersistenceException {
        
        return searchLastModOntologies(0);
    }
    
    public ArrayList searchLastModOntologies(int userId) throws 
            PersistenceException {
        
        ArrayList results = new ArrayList();
        
        try{
            this.Connect();
            this.statement = getConnection().createStatement();
            
            String query = 
                    "SELECT distinct umo.idOntology " +
                    "FROM   user_modified_ontology umo " +
                    "INNER JOIN ontology o ON " +
                    "    o.idontology = umo.idOntology ";
            
            if (userId != 0) {
                query += "AND o.idKnowledgeArea IN (SELECT idKnowledgeArea FROM ";
                query += "user_has_knowledge_area WHERE idUser = " + userId + ") ";
                query += "AND o.canBeAcessed = 1 ";
            }
            
            query +="AND o.isremoved  = 0 " +
                    "ORDER BY umo.date DESC " +
                    "LIMIT 0,10";
            
            this.resultSet = this.statement.executeQuery(query);
            int i = 0;
            do {
                if (this.resultSet.next()) {
                    results.add(new Ontology(
                            this.resultSet.getInt("idOntology")));
                    i = 1;
                } else {
                    if (i == 1)
                        i = 0;
                }
            } while (i != 0);
            
            return results;
            
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
    
    public ArrayList getOntologyById(int idOntology) throws 
            PersistenceException {
        
        ArrayList result = new ArrayList();
        
        try{
            this.Connect();
            this.statement = getConnection().createStatement();
            
            this.resultSet = this.statement.executeQuery(
                    "SELECT * " +
                    "FROM   ontology " +
                    "WHERE idOntology = " + String.valueOf(idOntology));
            this.resultSet.next();
               
            result.add(0, this.resultSet.getString("name"));
            result.add(1, this.resultSet.getString("description"));
            result.add(2, this.resultSet.getString("qtdClasses"));
            result.add(3, this.resultSet.getString("canBeAcessed"));
            result.add(4, this.resultSet.getString("idUserCreator"));
            result.add(5, this.resultSet.getString("dateCreation"));
            result.add(6, this.resultSet.getString("idKnowledgeArea"));
            
            this.resultSet = this.statement.executeQuery(
                    "SELECT * " +
                    "FROM   user_modified_ontology " +
                    "WHERE idOntology = " + String.valueOf(idOntology) +
                    " ORDER BY date DESC " +
                    " LIMIT 0, 1");
            this.resultSet.next();
            
            result.add(7, this.resultSet.getString("date"));
            result.add(8, this.resultSet.getString("idUser"));
            
            return result;
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
    
    public ArrayList getOntologyHierarchyClasses(String idOntology) throws 
            PersistenceException {
        
        ArrayList result = new ArrayList();
        
        try{
            this.Connect();
            this.statement = getConnection().createStatement();
            
            this.resultSet = this.statement.executeQuery(
                    "SELECT * " +
                    "FROM   ontology_class " +
                    "WHERE idOntology = " + idOntology +
                    " ORDER BY URI ASC");
            
            while (this.resultSet.next()) {
                result.add(new OntologyClass(
                        this.resultSet.getString("idOntologyClass"), 
                        this.resultSet.getString("URI"), 
                        this.resultSet.getString("subClassOf")));
            }
            
            return result;
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
    
    public void updateOntAccess(int idOntology, String canBeAcessed, int idUser) 
            throws PersistenceException {
       
        try{
            this.Connect();
            this.statement = getConnection().createStatement();
            
            this.statement.executeUpdate(
                    "UPDATE ontology SET canBeAcessed = '" + 
                    canBeAcessed + "' WHERE idOntology = " 
                    + String.valueOf(idOntology));
 
            this.statement.executeUpdate(
                    "INSERT INTO user_modified_ontology VALUES (" + 
                    idUser + ", " + idOntology + ", NOW())");
            
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
        
    }
    
}
