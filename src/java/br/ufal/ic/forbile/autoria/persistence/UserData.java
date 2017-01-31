package br.ufal.ic.forbile.autoria.persistence;

import  br.ufal.ic.forbile.autoria.exceptions.*;
import  br.ufal.ic.forbile.autoria.core.*;
import  br.ufal.ic.forbile.autoria.core.util.*;
import  java.sql.SQLException;
import  java.util.*;

public class UserData extends DatabaseConnection {
          
    public UserData() {
    }
    
    public int insertNewUser(String userType, String name, String email, 
            String login, String password, String isAcessible) throws 
            PersistenceException {
        
        int resultInsert;
        
        try{
            this.Connect();
            this.statement = getConnection().createStatement();
            
            resultInsert = this.statement.executeUpdate(
                    "INSERT INTO user VALUES (NULL, " +
                    userType + ", '" + name + "', '" + email + "', '" + 
                    login + "', '" + password + "', '" + 
                    isAcessible + "', NOW(), '0')");
            
            if (resultInsert < 1) 
                return -1;
            else {
                this.resultSet = this.statement.executeQuery(
                        "SELECT idUser " +
                        "FROM   User " +
                        "WHERE login = '" + login +
                        "' AND password = '" + password + "'");
                
                this.resultSet.next();
                return this.resultSet.getInt("idUser");
            }
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
    
    public void updateUser(String idUser, String userType, String name, 
            String email, String login, String password, 
            String isAcessible) throws PersistenceException {
        
        try{
            this.Connect();
            this.statement = getConnection().createStatement();
            
            this.statement.executeUpdate(
                    "UPDATE user SET userType = " + userType + ", name = '" +
                    name + "', email = '" + email + "', login = '" + login + 
                    "', pass = '" + password + "', isAcessible = '" + 
                    isAcessible + "' WHERE idUser = " + idUser);
            
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
    } 
    
    public void removeUser(String idUser) throws 
            PersistenceException {
       
        try{
            this.Connect();
            this.statement = getConnection().createStatement();
            
            this.statement.executeUpdate(
                    "DELETE FROM user_has_knowledge_area WHERE idUser = " + idUser);            
            
            this.statement.executeUpdate(
                    "UPDATE user SET isRemoved = 1 WHERE idUser = " + idUser);
            
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
    
    public ArrayList searchUsers(String searchKey) throws 
            PersistenceException {
        
        ArrayList results = new ArrayList();
        
        try{
            this.Connect();
            this.statement = getConnection().createStatement();
            
            this.resultSet = this.statement.executeQuery(
                    "SELECT idUser " +
                    "FROM   user " +
                    "WHERE ( name LIKE '%" + searchKey + "%' OR " +
                    "login LIKE '%" + searchKey + "%' ) AND " +
                    "isRemoved = 0 " +
                    "ORDER BY name ASC");
            int i = 0;
            do {
                if (this.resultSet.next()) {
                    results.add(new User(
                            this.resultSet.getInt("idUser")));
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
    
    public ArrayList searchLastModifiedUsers() throws 
            PersistenceException {
        
        ArrayList results = new ArrayList();
        
        try{
            this.Connect();
            this.statement = getConnection().createStatement();
            
            this.resultSet = this.statement.executeQuery(
                    "SELECT idUser " +
                    "FROM   user " +
                    "WHERE isRemoved = 0 " +
                    "ORDER BY dateCreation DESC " +
                    "LIMIT 0,10");

            while (this.resultSet.next()) {
                results.add(new User(
                        this.resultSet.getInt("idUser")));

            }
            
            return results;
            
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
    
    public ArrayList getUserById(int idUser) throws 
            PersistenceException {
        
        ArrayList result = new ArrayList();
        
        try{
            this.Connect();
            this.statement = getConnection().createStatement();
            
            this.resultSet = this.statement.executeQuery(
                    "SELECT * " +
                    "FROM   user " +
                    "WHERE idUser = " + String.valueOf(idUser));
            this.resultSet.next();
               
            result.add(0, this.resultSet.getString("name"));
            result.add(1, this.resultSet.getString("email"));
            result.add(2, this.resultSet.getString("login"));
            result.add(3, this.resultSet.getString("pass"));
            result.add(4, this.resultSet.getString("userType"));
            result.add(5, this.resultSet.getString("isAcessible"));
            result.add(6, this.resultSet.getString("dateCreation"));
            
            return result;
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
    
    public boolean checkLoginExists(String login) throws 
            PersistenceException {
        
        try{
            this.Connect();
            this.statement = getConnection().createStatement();
            
            this.resultSet = this.statement.executeQuery(
                    "SELECT idUser " +
                    "FROM   user " +
                    "WHERE  login = '" + login +
                    "' AND   isRemoved = 0");
            
            if (this.resultSet.next())
                return true;
            else
                return false;
            
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
}
