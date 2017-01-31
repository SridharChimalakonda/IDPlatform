package br.ufal.ic.forbile.autoria.persistence;

import  br.ufal.ic.forbile.autoria.exceptions.*;
import  br.ufal.ic.forbile.autoria.core.*;
import  br.ufal.ic.forbile.autoria.core.util.*;
import  java.sql.SQLException;

public class Authentication extends DatabaseConnection {
    
    private int userId;
    
    public Authentication() {
    }
    
    public void authenticate(String strUser, String strPassword) throws 
            AuthenticationException, PersistenceException {
        
        try{
            this.Connect();
            this.statement = getConnection().createStatement();
            this.resultSet = this.statement.executeQuery(
                    "SELECT idUser, isAcessible " +
                    "FROM   User " +
                    "WHERE  login = '" + strUser + "' " +
                    "AND    pass = '" + strPassword + "' " +
                    "AND    isRemoved = 0");

            if (this.resultSet.next()) {
                if (this.resultSet.getString("isAcessible").equals("0"))
                    throw new AuthenticationException(Constant.NOT_ACESSIBLE);
                this.userId = this.resultSet.getInt("idUser");
            } else {
                throw new AuthenticationException(Constant.LOGIN_INCORRECT);
            }
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
        
    public User getUser() throws PersistenceException {
               
        try {
            User user;
            
            this.Connect();
            this.statement = getConnection().createStatement();
            this.resultSet = this.statement.executeQuery(
                    "SELECT * " +
                    "FROM   User " +
                    "WHERE  idUser = " + this.userId );
            this.resultSet.next();
            
            user = new User();
            user.setIdUser(this.userId);
            user.setName(this.resultSet.getString("name"));
            user.setEmail(this.resultSet.getString("email"));
            user.setLogin(this.resultSet.getString("login"));
            user.setUserType(this.resultSet.getInt("usertype"));          
            
            return user;
        } catch (SQLException ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
    
}
