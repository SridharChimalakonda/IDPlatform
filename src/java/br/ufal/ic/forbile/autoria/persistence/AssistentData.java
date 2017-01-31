package br.ufal.ic.forbile.autoria.persistence;

import  br.ufal.ic.forbile.autoria.exceptions.*;
import  br.ufal.ic.forbile.autoria.core.*;
import  br.ufal.ic.forbile.autoria.core.util.*;
import  java.sql.SQLException;
import  java.util.*;


public class AssistentData extends DatabaseConnection {

    public AssistentData() {
        
    }
    
    public int insertNewAssistent(String idOntology, String idUser) throws 
            PersistenceException {
        
        try{
            this.Connect();
            this.statement = getConnection().createStatement();
            
            this.statement.executeUpdate(
                    "INSERT INTO assistent VALUES (NULL, " +
                    idOntology + ", " + idUser + ", NOW())");


            this.resultSet = this.statement.executeQuery(
                    "SELECT idAssistent " +
                    "FROM   assistent " +
                    "WHERE idOntology = " + idOntology);

            this.resultSet.next();
            return this.resultSet.getInt("idAssistent");
            
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
    
    public int insertNewAssistentStep(String idAssistent, String numStep, 
            String title, String description, String idUser) throws 
            PersistenceException {
        
        try{
            this.Connect();
            this.statement = getConnection().createStatement();
            
            this.resultSet = this.statement.executeQuery(
                    "SELECT idOntology " +
                    "FROM   assistent " +
                    "WHERE  idAssistent = " + idAssistent);
            this.resultSet.next();
            String idOntology = this.resultSet.getString("idOntology");
            
            this.statement.executeUpdate(
                    "INSERT INTO assistent_step VALUES (NULL, " +
                    idOntology + ", " + idAssistent + ", " + numStep + ", '" + 
                    title + "', '" + description + "')");
            
            this.resultSet = this.statement.executeQuery(
                    "SELECT idAssistentStep " +
                    "FROM   assistent_step " +
                    "WHERE  idAssistent = " + idAssistent +
                    " AND    numStep = " + numStep);
            this.resultSet.next();
            int idAssistentStep = this.resultSet.getInt("idAssistentStep");

            this.statement.executeUpdate(
                    "INSERT user_modified_assistent_step VALUES (" +
                    idAssistentStep + ", " + idUser + ", " + "NOW())");
            
            return idAssistentStep;
            
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
    
    public int insertNewAssistentStepClass(String idAssistentStep, 
            String numOrderAppear, String className, String objectName) throws 
            PersistenceException {
        
        try{
            this.Connect();
            this.statement = getConnection().createStatement();
            
            this.statement.executeUpdate(
                    "INSERT INTO step_class VALUES (NULL, " +
                    idAssistentStep + ", " + numOrderAppear + ", '" + 
                    className + "', '" + objectName + "')");
            
            this.resultSet = this.statement.executeQuery(
                    "SELECT idStepClass " +
                    "FROM   step_class " +
                    "WHERE  idAssistentStep = " + idAssistentStep +
                    " AND    numOrderAppear = " + numOrderAppear);
            this.resultSet.next();
            
            return this.resultSet.getInt("idStepClass");
            
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
    
    public int insertNewAssistentStepClassAtribute(String idStepClass, 
            String numOrderAppear, String nameAttribute, String nameField, 
            String idFieldType, String sizeField, String isNotNull) throws 
            PersistenceException {
        
        try{
            this.Connect();
            this.statement = getConnection().createStatement();
            
            this.statement.executeUpdate(
                    "INSERT INTO class_atribute VALUES (NULL, " +
                    idStepClass + ", " + numOrderAppear + ", '" + 
                    nameAttribute + "', '" + nameField + "', " + 
                    idFieldType + ", " + sizeField + ", '" + isNotNull + "')");
            
            this.resultSet = this.statement.executeQuery(
                    "SELECT idClassAtribute " +
                    "FROM   class_atribute " +
                    "WHERE  idStepClass = " + idStepClass +
                    " AND    numOrderAppear = " + numOrderAppear);
            this.resultSet.next();
            
            return this.resultSet.getInt("idClassAtribute");
            
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
    
    public void updateAssistentStep(String idAssistent, String numStep, 
            String title, String description, String idUser) throws 
            PersistenceException {
        
        try{
            this.Connect();
            this.statement = getConnection().createStatement();
            
            this.statement.executeUpdate(
                    "UPDATE assistent_step SET title = '" + title + 
                    "', description = '" + description + 
                    "' WHERE idAssistent = " + idAssistent + " AND numStep = " + 
                    numStep);
            
            this.resultSet = this.statement.executeQuery(
                    "SELECT idAssistentStep " +
                    "FROM   assistent_step " +
                    "WHERE  idAssistent = " + idAssistent +
                    " AND   numStep = " + numStep);
            this.resultSet.next();
            int idAssistentStep = this.resultSet.getInt("idAssistentStep");

            this.statement.executeUpdate(
                    "INSERT user_modified_assistent_step VALUES (" +
                    idAssistentStep + ", " + idUser + ", " + "NOW())");
            
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
    
    public void updateAssistentStepClass(String idAssistentStepClass, 
            String numOrderAppear, String objectName) throws 
            PersistenceException {
        
        try{
            this.Connect();
            this.statement = getConnection().createStatement();
            
            this.statement.executeUpdate(
                    "UPDATE step_class SET numOrderAppear = " +
                    numOrderAppear + ", objectName = '" + 
                    objectName + "' WHERE idStepClass = " + idAssistentStepClass);
            
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
    
    public void updateStepClassAtribute(String idClassAtribute, 
            String numOrderAppear, String nameField, 
            String idFieldType, String sizeField, String isNotNull) throws 
            PersistenceException {
        
        try{
            this.Connect();
            this.statement = getConnection().createStatement();
            
            this.statement.executeUpdate(
                    "UPDATE class_atribute SET numOrderAppear = " +
                    numOrderAppear + ", nameField = '" + nameField + "', idFieldType = " + 
                    idFieldType + ", sizeField = " + sizeField + ", isNotNull = '" + 
                    isNotNull + "' WHERE idClassAtribute = " + idClassAtribute);
            
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
    
    public void moveAssistentStep(int idAssistent, int numStep, 
            int typeAction) throws 
            PersistenceException {
        
        try{
            this.Connect();
            this.statement = getConnection().createStatement();
            
            if (typeAction == 1) {
                this.resultSet = this.statement.executeQuery(
                    "SELECT idAssistentStep " +
                    "FROM   assistent_step " +
                    "WHERE  idAssistent = " + idAssistent +
                    " AND   numStep = " + (numStep-1));
            } else {
                this.resultSet = this.statement.executeQuery(
                    "SELECT idAssistentStep " +
                    "FROM   assistent_step " +
                    "WHERE  idAssistent = " + idAssistent +
                    " AND   numStep = " + (numStep+1));
            }
            this.resultSet.next();
            int idAssistentStep = this.resultSet.getInt("idAssistentStep");
            
            if (typeAction == 1) {
                this.statement.executeUpdate(
                        "UPDATE assistent_step SET numStep = numStep-1 " + 
                        "WHERE idAssistent = " + idAssistent + " AND numStep = " + numStep);
                
                this.statement.executeUpdate(
                        "UPDATE assistent_step SET numStep = numStep+1 " + 
                        "WHERE idAssistentStep = " + idAssistentStep);
            } else {
                this.statement.executeUpdate(
                        "UPDATE assistent_step SET numStep = numStep+1 " + 
                        "WHERE idAssistent = " + idAssistent + " AND numStep = " + numStep);
                
                this.statement.executeUpdate(
                        "UPDATE assistent_step SET numStep = numStep-1 " + 
                        "WHERE idAssistentStep = " + idAssistentStep);
            }
            
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
        
    }
    
    public ArrayList getAssistentStep(String idAssistent, String stepNum) throws 
            PersistenceException {
    
        ArrayList result = new ArrayList();
        
        try{
            this.Connect();
            this.statement = getConnection().createStatement();
            
            this.resultSet = this.statement.executeQuery(
                    "SELECT * " +
                    "FROM   assistent_step " +
                    "WHERE idAssistent = " + idAssistent +
                    " AND numStep = " + stepNum);
            
            if (this.resultSet.next()) {

                String idAssistentStep = this.resultSet.
                        getString("idAssistentStep");
                
                result.add(0, idAssistentStep);
                result.add(1, this.resultSet.getString("idOntology"));
                result.add(2, this.resultSet.getString("title"));
                result.add(3, this.resultSet.getString("description"));
                
                ArrayList subResult = new ArrayList();

                this.resultSet = this.statement.executeQuery(
                        "SELECT idStepClass " +
                        "FROM   step_class " +
                        "WHERE idAssistentStep = " + idAssistentStep +
                        " ORDER BY numOrderAppear ASC");

                for (int i = 0; this.resultSet.next(); i++) {
                    subResult.add(i, this.resultSet.getString("idStepClass"));
                }
                result.add(4, subResult);

                this.resultSet = this.statement.executeQuery(
                        "SELECT idUser, date " +
                        "FROM   user_modified_assistent_step " +
                        "WHERE  idAssistentStep = " + idAssistentStep +
                        " ORDER BY date DESC LIMIT 0,1");

                this.resultSet.next();
                result.add(5, this.resultSet.getString("date"));
                result.add(6, this.resultSet.getString("idUser"));
            }

            return result;
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
    
    public ArrayList getAssistentStep(String idAssistentStep) throws 
            PersistenceException {
    
        ArrayList result = new ArrayList();
        
        try{
            this.Connect();
            this.statement = getConnection().createStatement();
            
            this.resultSet = this.statement.executeQuery(
                    "SELECT * " +
                    "FROM   assistent_step " +
                    "WHERE idAssistentStep = " + idAssistentStep);
            
            if (this.resultSet.next()) {
                
                result.add(0, this.resultSet.getString("idAssistent"));
                result.add(1, this.resultSet.getString("idOntology"));
                result.add(2, this.resultSet.getString("title"));
                result.add(3, this.resultSet.getString("description"));
                result.add(4, this.resultSet.getString("numStep"));
                
                ArrayList subResult = new ArrayList();

                this.resultSet = this.statement.executeQuery(
                        "SELECT idStepClass " +
                        "FROM   step_class " +
                        "WHERE idAssistentStep = " + idAssistentStep +
                        " ORDER BY numOrderAppear ASC");

                for (int i = 0; this.resultSet.next(); i++) {
                    subResult.add(i, this.resultSet.getString("idStepClass"));
                }
                result.add(5, subResult);

                this.resultSet = this.statement.executeQuery(
                        "SELECT idUser, date " +
                        "FROM   user_modified_assistent_step " +
                        "WHERE  idAssistentStep = " + idAssistentStep +
                        " ORDER BY date DESC LIMIT 0,1");

                this.resultSet.next();
                result.add(6, this.resultSet.getString("date"));
                result.add(7, this.resultSet.getString("idUser"));
            }

            return result;
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
    
    public ArrayList getAssistentStepClass(String idAssistentStepClass) throws 
            PersistenceException {
    
        ArrayList result = new ArrayList();
        
        try{
            this.Connect();
            this.statement = getConnection().createStatement();
            
            this.resultSet = this.statement.executeQuery(
                    "SELECT * " +
                    "FROM   step_class " +
                    "WHERE idStepClass = " + idAssistentStepClass);
            
            if (this.resultSet.next()) {
                
                result.add(0, this.resultSet.getString("idAssistentStep"));
                result.add(1, this.resultSet.getString("numOrderAppear"));
                result.add(2, this.resultSet.getString("className"));
                result.add(3, this.resultSet.getString("objectName"));
                
                ArrayList subResult = new ArrayList();
                this.resultSet = this.statement.executeQuery(
                        "SELECT idClassAtribute " +
                        "FROM   class_atribute " +
                        "WHERE idStepClass = " + idAssistentStepClass +
                        " ORDER BY numOrderAppear ASC");

                for (int i = 0; this.resultSet.next(); i++) {
                    subResult.add(i, this.resultSet.getString("idClassAtribute"));
                }
                result.add(4, subResult);
            }

            return result;
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
    
    public ArrayList getAssistentStepClassAtribute(String idClassAtribute) throws 
            PersistenceException {
    
        ArrayList result = new ArrayList();
        
        try{
            this.Connect();
            this.statement = getConnection().createStatement();
            
            this.resultSet = this.statement.executeQuery(
                    "SELECT * " +
                    "FROM   class_atribute " +
                    "WHERE idClassAtribute = " + idClassAtribute);
            
            if (this.resultSet.next()) {
                
                result.add(0, this.resultSet.getString("idStepClass"));
                result.add(1, this.resultSet.getString("numOrderAppear"));
                result.add(2, this.resultSet.getString("nameAttribute"));
                result.add(3, this.resultSet.getString("nameField"));
                result.add(4, this.resultSet.getString("idFieldType"));
                result.add(5, this.resultSet.getString("sizeField"));
                result.add(6, this.resultSet.getString("isNotNull"));
            }

            return result;
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
    
    public ArrayList getAssistentByOntology(int idOntology) throws 
            PersistenceException {
        
        ArrayList result = new ArrayList();
        
        try{
            this.Connect();
            this.statement = getConnection().createStatement();
            
            this.resultSet = this.statement.executeQuery(
                    "SELECT * " +
                    "FROM   assistent " +
                    "WHERE idOntology = " + String.valueOf(idOntology));
            
            if (this.resultSet.next()) {

                result.add(0, this.resultSet.getString("idAssistent"));
                result.add(1, this.resultSet.getString("idUser"));
                result.add(2, this.resultSet.getString("dateCreation"));

                String idAssistent = this.resultSet.getString("idAssistent");

                ArrayList subResult = new ArrayList();

                this.resultSet = this.statement.executeQuery(
                        "SELECT idAssistentStep " +
                        "FROM   assistent_step " +
                        "WHERE idAssistent = " +
                        idAssistent +
                        " ORDER BY numStep ASC");

                for (int i = 0; this.resultSet.next(); i++) {
                    subResult.add(i, this.resultSet.getString("idAssistentStep"));
                }
                result.add(3, subResult);

                this.resultSet = this.statement.executeQuery(
                        "SELECT us.date AS date " +
                        "FROM   user_modified_assistent_step us " +
                        "INNER JOIN assistent_step ass ON " +
                        "us.idAssistentStep = ass.idAssistentStep " +
                        "INNER JOIN assistent a ON " +
                        "ass.idAssistent = a.idAssistent " +
                        "WHERE a.idAssistent = " +
                        idAssistent +
                        " ORDER BY date DESC LIMIT 0,1");

                if (this.resultSet.next()) 
                    result.add(4, this.resultSet.getString("date"));
            }

            return result;
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
    
    public void removerAssistentStep(String idAssistent, String stepNum) throws 
            PersistenceException {
        
        try{
            this.Connect();
            this.statement  = getConnection().createStatement();
            this.statement2 = getConnection().createStatement();
            
            this.resultSet = this.statement.executeQuery(
                    "SELECT idAssistentStep " +
                    "FROM   assistent_step " +
                    "WHERE  idAssistent = " + idAssistent +
                    " AND   numStep = " + stepNum);
            this.resultSet.next();
            
            String idAssistentStep = this.resultSet.getString("idAssistentStep");
            
            this.resultSet = this.statement.executeQuery(
                    "SELECT idStepClass " +
                    "FROM   step_class " +
                    "WHERE  idAssistentStep = " + idAssistentStep +
                    " ORDER BY numOrderAppear ASC");
            
            while (this.resultSet.next()) {
            
                String idStepClass = this.resultSet.getString("idStepClass");

                this.statement2.executeUpdate(
                        "DELETE FROM class_atribute WHERE idStepClass = " +
                        idStepClass);
            }
            
            this.statement.executeUpdate(
                        "DELETE FROM step_class WHERE idAssistentStep = '" +
                        idAssistentStep + "'");
            
            this.statement.executeUpdate(
                        "DELETE FROM user_modified_assistent_step " +
                        "WHERE idAssistentStep = '" +
                        idAssistentStep + "'");
            
            this.statement.executeUpdate(
                    "DELETE FROM assistent_step WHERE idAssistent = '" +
                    idAssistent + "' AND numStep = " + stepNum);
            
            this.resultSet = this.statement.executeQuery(
                    "SELECT idAssistentStep, numStep " +
                    "FROM   assistent_step " +
                    "WHERE  idAssistent = " + idAssistent +
                    " AND   numStep > " + stepNum);
            
            while (this.resultSet.next()) {
                this.statement2.executeUpdate(
                    "UPDATE assistent_step SET numStep = numStep - 1 WHERE idAssistentStep = '" +
                    this.resultSet.getString("idAssistentStep") + "'");
            }
            
            this.resultSet = this.statement.executeQuery(
                    "SELECT COUNT(*)" +
                    "FROM   assistent_step " +
                    "WHERE  idAssistent = '" + idAssistent + "'");
            this.resultSet.next();
            
            if (this.resultSet.getInt(1) == 0) {
                
                this.statement2.executeUpdate(
                    "DELETE FROM assistent WHERE idAssistent = '" +
                    idAssistent + "'");
            }
                        
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
    
    public void removeAssistentStepClass(String idStepClass, 
            String numOrderAppear) throws 
            PersistenceException {
        
        try{
            this.Connect();
            this.statement  = getConnection().createStatement();
            this.statement2 = getConnection().createStatement();
            
            this.resultSet = this.statement.executeQuery(
                    "SELECT idAssistentStep " +
                    "FROM   step_class " +
                    "WHERE  idStepClass = " + idStepClass);
            this.resultSet.next();
            
            String idAssistentStep = this.resultSet.getString("idAssistentStep");
            
            this.statement.executeUpdate(
                    "DELETE FROM class_atribute WHERE idStepClass = " +
                    idStepClass);
            
            this.statement.executeUpdate(
                    "DELETE FROM step_class WHERE idAssistentStep = '" +
                    idAssistentStep + "' AND numOrderAppear = '" + 
                    numOrderAppear + "'");
            
            this.resultSet = this.statement.executeQuery(
                    "SELECT idStepClass, numOrderAppear " +
                    "FROM   step_class " +
                    "WHERE  idAssistentStep = " + idAssistentStep +
                    " AND   numOrderAppear > " + numOrderAppear);
            
            while (this.resultSet.next()) {
                this.statement2.executeUpdate(
                    "UPDATE step_class SET numOrderAppear = numOrderAppear - 1 WHERE idStepClass = '" +
                    this.resultSet.getString("idStepClass") + "'");
            }
                        
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
    
    }
}
    
