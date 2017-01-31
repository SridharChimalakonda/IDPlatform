package br.ufal.ic.forbile.autoria.core.util;

public class Constant {
    
    public static String LOGIN_PAGE = "loginPage";
    
    public static String AUT_USER   = "authenticatedUser";
    public static String USER_PERM  = "userPermissions";
    
    public static String ERROR_MSG  = "errorMessage";
    public static String HAS_ERROR  = "hasError";
    
    public static String SUCCESS  = "successOperation";
    public static String FAILURE  = "failureOperation";
    
    public static String NOT_ACESSIBLE   = "User access denied";
    public static String LOGIN_INCORRECT = "User and / or password is incorrect"; 
    
    public static int ADMIN_USER  = 1;
    public static int AUTHOR_USER = 2;
    
    //Constants added by dida
    
    // Started from DynamicMenu.java
    
    public static final String GEN_ONTOLOGIES_MSG1 = "Ontologies";
    public static final String GEN_AREAS_OF_EXPERTISE_MSG1 = "Areas of Knowledge";
    public static final String GEN_USERS_MSG1 = "Users";
    public static final String GEN_ABOUT_MSG1 = "Research Publications";
    public static final String GEN_EXIT_MSG1 = "Exit";
    public static final String GEN_ADD_MSG1 = "Add";
    public static final String GEN_CONFIGURE_MSG1 = "Configure";
    public static final String GEN_REMOVE_MSG1 = "Remove";
    public static final String GEN_EDIT_MSG1 = "Edit";
    public static final String GEN_RELATE_KNOWLEDGE_AREA_MSG1 = "Relate to Authors";

    
    //Constants for java files
    //AddKnowledge.java
    
    public static final String GEN_MSG1 = "Invalid Key";
    public static final String ADD_KNOWLEDGE_MSG1 = "The name of the area of knowledge should be more than two characters.";
    public static final String ADD_KNOWLEDGE_MSG2 = "There is already an area of knowledge with the name";
    public static final String ADD_KNOWLEDGE_MSG3 = "Please choose another name!";
    public static final String ADD_KNOWLEDGE_MSG4 = "Removed this!";
    
    
    public static final String ADD_ONTOLOGY_MSG1 = "Unable to connect to the database";
    public static final String ADD_ONTOLOGY_MSG2 = "The name of the ontology should be more than three characters.";
    public static final String ADD_ONTOLOGY_MSG3 = "There is already a registered ontology with this name";
    public static final String ADD_ONTOLOGY_MSG4 = "Unable to save the ontology";
    public static final String ADD_ONTOLOGY_MSG5 = "Error Details:";
    public static final String ADD_ONTOLOGY_MSG6 = "Unable to read the file and save the ontology";
    public static final String ADD_ONTOLOGY_MSG7 = "Unable to remove the ontology";
    
    public static final String ADD_USER_MSG1 = "Author";
    public static final String ADD_USER_MSG2 = "Knowledge Analyst";
    public static final String ADD_USER_MSG3 = "Invalid email address. Please check the address you entered!";
    public static final String ADD_USER_MSG4 = "The username must be more than three characters.";
    public static final String ADD_USER_MSG5 = "There is already a registered user with the entered login name";
    public static final String ADD_USER_MSG6 = "The password must have more than three characters.";
    
    public static final String ASSITENT_CLASS_MSG1 = "There are no instances for this class";
    
    public static final String ASSITENT_CLASS_PROPERTY_MSG1 = "There are no instances for this property";
    
    public static final String CREATE_STEPS_MSG1 = "Select at least one class for assembling this step.";
    public static final String CREATE_STEPS_MSG2 = "has no attributes!";
    public static final String CREATE_STEPS_MSG3 = "In class\\";
    public static final String CREATE_STEPS_MSG4 = "Name of the object is not defined";
    public static final String CREATE_STEPS_MSG5 = "There is already a class in this step with the order No.";
    public static final String CREATE_STEPS_MSG6 = "Please modify the order";
    public static final String CREATE_STEPS_MSG7 = "Please define the field name for the property";
    public static final String CREATE_STEPS_MSG8 = "Already exists with the property order no";
    public static final String CREATE_STEPS_MSG9 = "Property";
    
    public static final String EDIT_STEPS_MSG1 = "Select at least one class for assembling this step.";
    public static final String EDIT_STEPS_MSG2 = "Has no attributes!";
    
    public static final String ONTOLOGY_VIEW_DETAILS_MSG1 = "Yes";
    public static final String ONTOLOGY_VIEW_DETAILS_MSG2 = "No";
    public static final String ONTOLOGY_VIEW_DETAILS_MSG3 = "You can only modify if you have access to configuration wizard";
    
    public static final String SEARCH_ONTOLOGY_MSG1 = "The search key must have more than three characters";
    
    public static final String UPLOAD_VALIDATOR_MSG1 = "Invalid File!";
    public static final String UPLOAD_VALIDATOR_MSG2 = "The file";
    public static final String UPLOAD_VALIDATOR_MSG3 = "is not an ontology";
    
    public static final String USER_MSG1 = "Knowledge Analyst";
    public static final String USER_MSG2 = "Author";
    
    public static final String CREATE_DATABASE_TABLES_MSG1 = "Creation of tables successfully accomplished!";
    
    public static final String DATABASE_CONNECTION_DATABASECONNECTION = "jdbc:mysql://localhost/forbile_authorship";
    public static final String DATABASE_CONNECTION_USER = "root";
    public static final String DATABASE_CONNECTION_PASSWORD = "root";
    
    public static final String FIELD_DATA_MSG1 = "There are no registered types";
    
    public static final String KNOWLEDGE_MSG1 = "There are no registered knowledge areas";
    
    public static final String OWL_DATA_MSG1 = "Entered OWL data";
    public static final String OWL_DATA_MSG2 = "Left OWL data";
    
    
    
    
    
    
    
    public Constant() {
    }
    
}
