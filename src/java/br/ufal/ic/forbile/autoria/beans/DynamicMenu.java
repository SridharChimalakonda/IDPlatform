package br.ufal.ic.forbile.autoria.beans;

import br.ufal.ic.forbile.autoria.core.util.Constant;
import java.util.ArrayList;
import org.apache.myfaces.custom.navmenu.NavigationMenuItem;

public class DynamicMenu {
    
    private NavigationMenuItem[] navAdminItems;
    private NavigationMenuItem[] navAuthorItems;
    
    /** Creates a new instance of DynamicMenu */
    public DynamicMenu() {
    
        // Creating root menus for Administrator
        this.createAdminMenu();
        
        // Creating root menus for Author
        this.createAuthorMenu();
    }
    
    
    private void createAdminMenu() {
        
        NavigationMenuItem firstMenu = new NavigationMenuItem(
                Constant.GEN_ONTOLOGIES_MSG1,null,"/imgs/ontology.gif",false);
        NavigationMenuItem secondMenu = new NavigationMenuItem(
                Constant.GEN_AREAS_OF_EXPERTISE_MSG1,null,"/imgs/knowledges.gif",false);
        NavigationMenuItem thirdMenu = new NavigationMenuItem(
                Constant.GEN_USERS_MSG1,null,"/imgs/users.gif",false);
        NavigationMenuItem fourthMenu = new NavigationMenuItem(
                Constant.GEN_ABOUT_MSG1,"aboutPage","/imgs/about.gif",false);
        NavigationMenuItem fifthMenu = new NavigationMenuItem(
                Constant.GEN_EXIT_MSG1,"#{login.doDisauthentication}","/imgs/exit.gif",false);
        
        // Adding root menus in array returned to JSP
        this.navAdminItems = new NavigationMenuItem[5];
        this.navAdminItems[0] = firstMenu;
        this.navAdminItems[1] = secondMenu;
        this.navAdminItems[2] = thirdMenu;
        this.navAdminItems[3] = fourthMenu;
        this.navAdminItems[4] = fifthMenu;
        
        // Adding some items in a firstMenu
        //ArrayList subItemsFirstMenu = new ArrayList();
        //NavigationMenuItem firstSubItem = new NavigationMenuItem(
        //        "#{msgs.genOntologiesMsg1}",null,"/imgs/ontology.gif",true);
        //subItemsFirstMenu.add(firstSubItem);
        //firstMenu.setNavigationMenuItems(subItemsFirstMenu);
        
        ArrayList subItemsFirstSubItem = new ArrayList();
        subItemsFirstSubItem.add(new NavigationMenuItem(
                Constant.GEN_ADD_MSG1, "addOntology", "/imgs/new_ontology.gif", false));
        subItemsFirstSubItem.add(new NavigationMenuItem(
                Constant.GEN_CONFIGURE_MSG1, "configOntology", "/imgs/configure_ontology.gif", false));
        subItemsFirstSubItem.add(new NavigationMenuItem(
                Constant.GEN_REMOVE_MSG1, "removeOntology", "/imgs/delete_ontology.gif", false));
        firstMenu.setNavigationMenuItems(subItemsFirstSubItem);


        ArrayList subItemsSecMenu = new ArrayList();
        subItemsSecMenu.add(new NavigationMenuItem(
                Constant.GEN_ADD_MSG1, "addKnowledgeArea", "/imgs/add_knowledges.gif", false));
        subItemsSecMenu.add(new NavigationMenuItem(
                Constant.GEN_EDIT_MSG1, "editKnowledgeArea", "/imgs/edit_knowledges.gif", false));
        subItemsSecMenu.add(new NavigationMenuItem(
                Constant.GEN_RELATE_KNOWLEDGE_AREA_MSG1, "relateKnowledgeArea", "/imgs/relate_know_ontology.gif", false));
        subItemsSecMenu.add(new NavigationMenuItem(
                Constant.GEN_REMOVE_MSG1, "removeKnowledgeArea", "/imgs/delete_knowledges.gif", false));
        secondMenu.setNavigationMenuItems(subItemsSecMenu);
        
        ArrayList subItemsThirdMenu = new ArrayList();
        subItemsThirdMenu.add(new NavigationMenuItem(
                Constant.GEN_ADD_MSG1,"addUser","/imgs/add_user.gif",false));
        subItemsThirdMenu.add(new NavigationMenuItem(
                Constant.GEN_EDIT_MSG1,"editUser","/imgs/view_users.gif",false));
        subItemsThirdMenu.add(new NavigationMenuItem(
                Constant.GEN_REMOVE_MSG1,"removeUser","/imgs/remove_user.gif",false));
        thirdMenu.setNavigationMenuItems(subItemsThirdMenu);  
    }
    
    private void createAuthorMenu() {
        
        NavigationMenuItem firstMenu = new NavigationMenuItem(
                Constant.GEN_ONTOLOGIES_MSG1,"searchOntology","/imgs/ontology.gif",false);
        NavigationMenuItem secMenu = new NavigationMenuItem(
                Constant.GEN_ABOUT_MSG1,"aboutPage","/imgs/about.gif",false);
        NavigationMenuItem thirdMenu = new NavigationMenuItem(
                Constant.GEN_EXIT_MSG1,"#{login.doDisauthentication}","/imgs/exit.gif",false);

        // Adding root menus in array returned to JSP
        this.navAuthorItems = new NavigationMenuItem[3];
        this.navAuthorItems[0] = firstMenu;
        this.navAuthorItems[1] = secMenu;
        this.navAuthorItems[2] = thirdMenu;
    }
    
    public NavigationMenuItem[] getNavAdminItems() {
        return navAdminItems;
    }
    
    public void setNavAdminItems(NavigationMenuItem[] navItems) {
        this.navAdminItems = navItems;
    }
    
    public NavigationMenuItem[] getNavAuthorItems() {
        return navAuthorItems;
    }
    
    public void setNavAuthorItems(NavigationMenuItem[] navItems) {
        this.navAuthorItems = navItems;
    }
}
