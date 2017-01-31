package br.ufal.ic.forbile.autoria.persistence;

import  java.sql.Connection;
import  java.sql.Statement;
import  java.sql.DriverManager;
import  java.sql.ResultSet;
import  java.sql.ResultSetMetaData;
import  java.sql.SQLException;

import  br.ufal.ic.forbile.autoria.core.util.*;

public abstract class DatabaseConnection {
    
    public static Connection     connection;
    protected Statement           statement;
    protected Statement           statement2;
    protected ResultSet           resultSet;
    protected ResultSet           resultSet2;
    protected ResultSetMetaData   metaData;
    protected boolean             connectedToDatabase = false;
    
    
    public DatabaseConnection() {
    }
    
    public Connection getConnection() {
        return connection;
    }

        
    public void Connect() {
        
        if (!this.connectedToDatabase) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                this.connection = DriverManager.getConnection(
                        Constant.DATABASE_CONNECTION_DATABASECONNECTION, Constant.DATABASE_CONNECTION_USER, Constant.DATABASE_CONNECTION_PASSWORD);
                this.connectedToDatabase = true;

            } catch (ClassNotFoundException ex) {
                
            } catch (SQLException ex) {           

            }
        }
        
    }
    
    @Override
    public void finalize() {
        
        try{
            this.connection.close();
        } catch (SQLException ex) {

        }
    }
}
