package br.ufal.ic.forbile.autoria.core.util;

import  br.ufal.ic.forbile.autoria.persistence.*;
import  java.sql.SQLException;

public class CreateDatabaseTables extends DatabaseConnection {

    public CreateDatabaseTables() {
        
    }
    
    public void main(String[] args) throws Exception  {
        
        try{
            this.Connect();
            this.statement = getConnection().createStatement();
            
            this.statement.executeUpdate(
                  "DROP TABLE IF EXISTS `assistent`");
            
            /*
            this.statement.executeUpdate(
                  "CREATE TABLE `assistent` (" +
                  "`idAssistent` int(10) unsigned NOT NULL auto_increment," +
                  "`idOntology` int(10) unsigned NOT NULL," +
                  "`idUser` int(10) unsigned NOT NULL," +
                  "`dateCreation` datetime default NULL," +
                  "PRIMARY KEY  (`idAssistent`,`idOntology`)," +
                  "KEY `Assistent_FKIndex1` (`idOntology`)," +
                  "KEY `Assistent_FKIndex2` (`idUser`)" +
                  ") ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1");
            */
            
            System.out.println("---------------------------------------------");
            System.out.println(Constant.CREATE_DATABASE_TABLES_MSG1);
            System.out.println("---------------------------------------------");
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        }
    }
}
