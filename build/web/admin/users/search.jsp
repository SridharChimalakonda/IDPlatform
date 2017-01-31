<%@ include file="/default/top.jsp" %>  

<f:view>   
    <f:loadBundle basename="br.ufal.ic.forbile.autoria.core.util.messages" 
                  var="msgs"/>

    <%@ include file="/default/menu.jsp" %>
    
    <h:form id="search_user">
        <h:panelGrid columns="1" headerClass="boxTituloPagina" 
            styleClass="boxCorpoPagina">
            <f:facet name="header">
                <h:outputText id="pageTitle" value="#{msgs.usrSrc_pageTitle}" 
                    styleClass="tituloPagina"/>
            </f:facet>
            
            <h:panelGrid columns="1" width="100%" align="center">          
                <h:message for="search_user" styleClass="erro_form"/>
                <h:message id="nameError" for="Nome" styleClass="erro_form"/>
            </h:panelGrid>
            
            <h:outputText id="name_label" 
                value="#{msgs.usrSrc_srchLabel}" 
                styleClass="textoForm1"/>
            <h:inputText maxlength="100" size="68" id="Nome" 
                         value="#{searchUserBean.usrName}" required="true"/>	
            
            <h:commandButton id="submit" value="#{msgs.searchButton}" 
                action="#{searchUserBean.search}"/>
        </h:panelGrid>
    </h:form>
    
    <h:panelGrid rendered="#{searchUserBean.users.rowCount < 1}" id="search_empty" 
                 align="center"  styleClass="boxCorpoPagina">
        <h:outputText value="#{msgs.usrSrc_srchNull} #{searchUserBean.usrName}" 
                styleClass="tituloBox"/>
    </h:panelGrid>
    
    <h:panelGrid columns="1" headerClass="tituloBox" styleClass="boxCorpoPagina" 
        rendered="#{searchUserBean.users.rowCount > 0}">
        <f:facet name="header">
            <h:outputText id="box1Title" 
                          value="#{msgs.searchResult} #{searchUserBean.users.rowCount} #{msgs.usrSrc_usersRes}"/>
        </f:facet>
        <h:dataTable id="users1" columnClasses="list-column-four, list6-column-three, 
                     list6-column-one, list6-column-two, list-column-three" 
                     headerClass="list-header" rowClasses="list-row" 
                     styleClass="list" value="#{searchUserBean.users}" 
                     var="usr">
            <h:column>
                <f:facet name="header">
                    <h:outputText  value=" "/>
                </f:facet>
                
                <h:outputLink 
                    value="#{facesContext.externalContext.requestContextPath}/admin/users/edit.jsf">
                    <h:graphicImage style="border: 0" width="16" height="16" 
                        id="editBt" alt="#{msgs.usrSrc_editBtLb}" 
                        url="#{facesContext.externalContext.requestContextPath}/../imgs/view_users.gif"/>
                    <f:param name="idUser" value="#{usr.idUser}"/>                        
                </h:outputLink>            
            </h:column>
            <h:column>
                <f:facet name="header">
                    <h:outputText value="#{msgs.usrSrc_dttbCol1}"/>
                </f:facet>
                <h:outputText value="#{usr.login}"/>
            </h:column> 
            <h:column>
                <f:facet name="header">
                    <h:outputText value="#{msgs.usrSrc_dttbCol2}"/>
                </f:facet>
                <h:outputText value="#{usr.name}"/>
            </h:column>
            <h:column>
                <f:facet name="header">
                    <h:outputText value="#{msgs.usrSrc_dttbCol3}"/>
                </f:facet>
                <h:outputText value="#{usr.userTypeName}"/>
            </h:column>
            <h:column>
                <f:facet name="header">
                    <h:outputText  value="#{msgs.usrSrc_dttbCol4}"/>
                </f:facet>
                <h:outputText value="#{usr.dateCreation}"/>
            </h:column>
        </h:dataTable>
    </h:panelGrid>
    
    <h:panelGrid rendered="#{searchUserBean.lastUsers.rowCount < 1}" id="usr_empty" 
                 align="center"  styleClass="boxCorpoPagina">
        <h:outputText value="#{msgs.usrSrc_srchEmpty}" 
                styleClass="tituloBox"/>
    </h:panelGrid>
    
    <h:panelGrid columns="1" headerClass="tituloBox" styleClass="boxCorpoPagina" 
        rendered="#{searchUserBean.users == null && searchUserBean.lastUsers.rowCount > 0}">
        <f:facet name="header">
            <h:outputText id="box2Title" 
                          value="#{msgs.usrSrc_srchLast}"/>
        </f:facet>
        <h:dataTable id="users2" columnClasses="list-column-four, list6-column-three, 
                     list6-column-one, list6-column-two, list-column-three" 
                     headerClass="list-header" rowClasses="list-row" 
                     styleClass="list" value="#{searchUserBean.lastUsers}" 
                     var="usr2">
            <h:column>
                <f:facet name="header">
                    <h:outputText  value=" "/>
                </f:facet>
                
                <h:outputLink 
                    value="#{facesContext.externalContext.requestContextPath}/admin/users/edit.jsf">
                    <h:graphicImage style="border: 0" width="16" height="16" 
                        id="editBt" alt="#{msgs.usrSrc_editBtLb}" 
                        url="#{facesContext.externalContext.requestContextPath}/../imgs/view_users.gif"/>
                    <f:param name="idUser" value="#{usr2.idUser}"/>                        
                </h:outputLink>            
            </h:column>
            <h:column>
                <f:facet name="header">
                    <h:outputText value="#{msgs.usrSrc_dttbCol1}"/>
                </f:facet>
                <h:outputText value="#{usr2.login}"/>
            </h:column>
            <h:column>
                <f:facet name="header">
                    <h:outputText value="#{msgs.usrSrc_dttbCol2}"/>
                </f:facet>
                <h:outputText value="#{usr2.name}"/>
            </h:column>
            <h:column>
                <f:facet name="header">
                    <h:outputText value="#{msgs.usrSrc_dttbCol3}"/>
                </f:facet>
                <h:outputText value="#{usr2.userTypeName}"/>
            </h:column>
            <h:column>
                <f:facet name="header">
                    <h:outputText  value="#{msgs.usrSrc_dttbCol4}"/>
                </f:facet>
                <h:outputText value="#{usr2.dateCreation}"/>
            </h:column>
        </h:dataTable>
    </h:panelGrid>
    
</f:view>

<%@ include file="/default/footer.jsp"  %>
