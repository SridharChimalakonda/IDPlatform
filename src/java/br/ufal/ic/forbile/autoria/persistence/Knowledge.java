package br.ufal.ic.forbile.autoria.persistence;

import  br.ufal.ic.forbile.autoria.exceptions.*;
import  br.ufal.ic.forbile.autoria.core.*;
import  br.ufal.ic.forbile.autoria.core.util.*;
import  java.sql.SQLException;
import  java.util.*;

import javax.faces.model.SelectItem;

public class Knowledge extends DatabaseConnection {
          
    public Knowledge() {
    }
    
    public int insertNewKnowledgeArea(String name, String description, 
            String idUserCreator) throws 
            PersistenceException {
        
        try{
            this.Connect();
            this.statement = getConnection().createStatement();
            
            this.statement.executeUpdate(
                    "INSERT INTO knowledge_area VALUES (NULL, '" +
                    name + "', '" + description + "', NOW(), " +
                    idUserCreator + ")");

            this.resultSet = this.statement.executeQuery(
                    "SELECT idKnowledgeArea " +
                    "FROM   knowledge_area " +
                    "WHERE  name = '" + name +
                    "' AND description = '" + description +
                    "' AND idUserCreator = " + idUserCreator);
            this.resultSet.next();
            
            return this.resultSet.getInt("idKnowledgeArea");
            
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
    
    public void insertRelateUserKnowledgeArea(String idUser, 
            String idKnowledgeArea) throws PersistenceException {
        
        try{
            this.Connect();
            this.statement = getConnection().createStatement();
            
            this.statement.executeUpdate(
                    "INSERT INTO user_has_knowledge_area VALUES (" +
                    idUser + ", " + idKnowledgeArea + ")");
            
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
    
    public void updateKnowledgeArea(int idKnowledgeArea, String name, 
            String description) throws 
            PersistenceException {
        
        try{
            this.Connect();
            this.statement = getConnection().createStatement();
            
            this.statement.executeUpdate(
                    "UPDATE knowledge_area SET name = '" +
                    name + "', description = '" + description + 
                    "' WHERE idKnowledgeArea = " + idKnowledgeArea);
            
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
    
    public void removeKnowledgeArea(int idKnowledgeArea) throws 
            PersistenceException {
        
        try{
            this.Connect();
            this.statement = getConnection().createStatement();
            
            if (!checkKnowledgeAreaIsBeingUsed(idKnowledgeArea))
                this.statement.executeUpdate(
                        "DELETE FROM knowledge_area WHERE idKnowledgeArea = " +
                        idKnowledgeArea);
            
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
    
    public void removeRelateUserKnowledgeArea(String idUser, 
            String idKnowledgeArea) throws PersistenceException {
        
        try{
            this.Connect();
            this.statement = getConnection().createStatement();
            
            this.statement.executeUpdate(
                    "DELETE FROM user_has_knowledge_area WHERE idUser = " +
                    idUser + " AND idKnowledgeArea = " + idKnowledgeArea);
            
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
    
    public ArrayList searchKnowledgeArea(String searchKey) throws 
            PersistenceException {
        
        ArrayList results = new ArrayList();
        
        try{
            this.Connect();
            this.statement = getConnection().createStatement();
            
            this.resultSet = this.statement.executeQuery(
                    "SELECT idKnowledgeArea " +
                    "FROM   knowledge_area " +
                    "WHERE name LIKE '%" + searchKey + "%' " +
                    "ORDER BY name ASC");
            
                while (this.resultSet.next()) {
                    results.add(new KnowledgeArea(
                            this.resultSet.getInt("idKnowledgeArea")));
                }
            
            return results;
            
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
    
    public ArrayList searchLastModifiedKnowledges() throws 
            PersistenceException {
        
        ArrayList results = new ArrayList();
        
        try{
            this.Connect();
            this.statement = getConnection().createStatement();
            
            this.resultSet = this.statement.executeQuery(
                    "SELECT idKnowledgeArea " +
                    "FROM   knowledge_area " +
                    "ORDER BY dateCreation DESC " +
                    "LIMIT 0,10");

            while (this.resultSet.next()) {
                results.add(new KnowledgeArea(
                        this.resultSet.getInt("idKnowledgeArea")));

            }
            
            return results;
            
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
    
    public boolean checkKnowledgeAreaIsBeingUsed(int idKnowledgeArea) throws
            PersistenceException {
        
        try{
            this.Connect();
            this.statement = getConnection().createStatement();
            
            this.resultSet = this.statement.executeQuery(
                    "SELECT idOntology " +
                    "FROM   ontology " +
                    "WHERE  idKnowledgeArea = '" + idKnowledgeArea + "'");
            
            if (this.resultSet.next()) 
                return true;
            else
                return false;
            
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
    
    public boolean checkKnowledgeAreaNameExists(String name) throws 
            PersistenceException {
        
        try{
            this.Connect();
            this.statement = getConnection().createStatement();
            
            this.resultSet = this.statement.executeQuery(
                    "SELECT idKnowledgeArea " +
                    "FROM   knowledge_area " +
                    "WHERE  name = '" + name + "'");
            
            if (this.resultSet.next()) 
                return true;
            else
                return false;
            
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
    
    public ArrayList getKnowledgeAreaById(int idKnowledgeArea) throws 
            PersistenceException {
        
        ArrayList result = new ArrayList();
        
        try{
            this.Connect();
            this.statement = getConnection().createStatement();
            
            this.resultSet = this.statement.executeQuery(
                    "SELECT * " +
                    "FROM   knowledge_area " +
                    "WHERE  idKnowledgeArea = " + String.valueOf(idKnowledgeArea));
            this.resultSet.next();
               
            result.add(0, this.resultSet.getString("name"));
            result.add(1, this.resultSet.getString("description"));
            result.add(2, this.resultSet.getString("dateCreation"));
            result.add(3, this.resultSet.getString("idUserCreator"));
            
            ArrayList users = new ArrayList();
            
            this.resultSet = this.statement.executeQuery(
                    "SELECT idUser " +
                    "FROM   user_has_knowledge_area " +
                    "WHERE  idKnowledgeArea = " + String.valueOf(idKnowledgeArea));
            
            while (this.resultSet.next()) {
                users.add(new User(this.resultSet.getInt("idUser")));
            }
            
            result.add(4, users);
            return result;
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
    
    public ArrayList getKnowledgeAreaOfUser(int idUser) throws 
            PersistenceException {
        
        ArrayList result = new ArrayList();
        
        try{
            this.Connect();
            this.statement = getConnection().createStatement();
            
            this.resultSet = this.statement.executeQuery(
                    "SELECT uka.idKnowledgeArea " +
                    "FROM   user_has_knowledge_area uka " +
                    "INNER JOIN knowledge_area ka ON " +
                    "   ka.idKnowledgeArea = uka.idKnowledgeArea " +
                    "WHERE  uka.idUser = " + idUser +
                    " ORDER BY ka.name ASC");
            
            while (this.resultSet.next()) {
                result.add(new KnowledgeArea(
                        this.resultSet.getInt("idKnowledgeArea")));
            }

            return result;
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
    
    public ArrayList getAllKnowledgeAreas() throws 
            PersistenceException {
        
        ArrayList list = new ArrayList();
        
        try{
            this.Connect();
            this.statement = getConnection().createStatement();
            this.resultSet = this.statement.executeQuery(
                    "SELECT idKnowledgeArea, name " +
                    "FROM   knowledge_area " +
                    "ORDER BY name ASC");
            int i = 0;
            do {
                if (this.resultSet.next()) {
                    list.add(new SelectItem(this.resultSet.getString("idKnowledgeArea"),
                            this.resultSet.getString("name")));
                    i = 1;
                } else {
                    if (i == 1)
                        i = 0;
                    else
                        list.add(new SelectItem(Constant.ONTOLOGY_VIEW_DETAILS_MSG2,
                                Constant.KNOWLEDGE_MSG1));
                }
            } while (i != 0);
            
            return list;
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
    
    public ArrayList getUsersHasKnowledgeArea(int idKnowledgeArea) throws
            PersistenceException {
        
        ArrayList result = new ArrayList();
        
        try{
            this.Connect();
            this.statement = getConnection().createStatement();
            
            this.resultSet = this.statement.executeQuery(
                    "SELECT uka.idUser " +
                    "FROM   user_has_knowledge_area uka " +
                    "INNER JOIN user u ON " +
                    "  u.idUser = uka.idUser " +
                    "WHERE  uka.idKnowledgeArea = " + String.valueOf(idKnowledgeArea) +
                    " ORDER BY u.name ASC");
            
            while (this.resultSet.next()) {
                result.add(new User(this.resultSet.getInt("idUser")));
            }
            
            return result;
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
}
