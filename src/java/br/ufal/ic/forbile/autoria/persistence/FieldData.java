package br.ufal.ic.forbile.autoria.persistence;

import  br.ufal.ic.forbile.autoria.exceptions.*;
import  br.ufal.ic.forbile.autoria.core.*;
import  br.ufal.ic.forbile.autoria.core.util.*;
import  java.sql.SQLException;
import  java.util.*;
import  javax.faces.model.SelectItem;

public class FieldData extends DatabaseConnection {

    public FieldData() {
        
    }
        
    public ArrayList getFieldsType() throws 
            PersistenceException {
        
        ArrayList list = new ArrayList();
        
        try{
            this.Connect();
            this.statement = getConnection().createStatement();
            this.resultSet = this.statement.executeQuery(
                    "SELECT idfieldType, name " +
                    "FROM   field_type " +
                    "ORDER BY name ASC");
            int i = 0;
            do {
                if (this.resultSet.next()) {
                    list.add(new SelectItem(this.resultSet.getString("idfieldType"),
                            this.resultSet.getString("name")));
                    i = 1;
                } else {
                    if (i == 1)
                        i = 0;
                    else
                        list.add(new SelectItem(Constant.ONTOLOGY_VIEW_DETAILS_MSG2,
                                Constant.FIELD_DATA_MSG1));
                }
            } while (i != 0);
            
            return list;
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
    
    public ArrayList getFieldById(int idField) throws 
            PersistenceException {
        
        ArrayList result = new ArrayList();
        
        try{
            this.Connect();
            this.statement = getConnection().createStatement();
            
            this.resultSet = this.statement.executeQuery(
                    "SELECT * " +
                    "FROM   field_type " +
                    "WHERE idfieldType = " + String.valueOf(idField));
            
            if (this.resultSet.next()) {
                result.add(0, this.resultSet.getString("idfieldType"));
                result.add(1, this.resultSet.getString("name"));
                result.add(2, this.resultSet.getString("defaultSize"));
                result.add(3, this.resultSet.getString("JSFComponentName"));
            }

            return result;
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
}
