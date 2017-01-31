<%          
    if (session.getAttribute("authenticatedUser") != null) {      
%>
    <h:form id="menuForm">
        <h:panelGrid columns="1" styleClass="boxMenu">
            <t:jscookMenu layout="hbr" theme="ThemeGray" 
                        styleLocation="default/menu"> 
<% 
        User userData = (User)session.getAttribute("authenticatedUser");

        switch(userData.getUserType()){
            case 1:
%>
                <t:navigationMenuItems id="navitems" 
                        value="#{dynamicMenu.navAdminItems}"/>
<%
                break;
            case 2:
%>
                <t:navigationMenuItems id="navitems" 
                        value="#{dynamicMenu.navAuthorItems}"/>
<%
                break;
        }
%>
            </t:jscookMenu>
        </h:panelGrid>
    </h:form>
<%
    }
%>