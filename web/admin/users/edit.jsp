<%@ include file="/default/top.jsp" %>  

<f:view>
    <f:loadBundle basename="br.ufal.ic.forbile.autoria.core.util.messages" 
                  var="msgs"/>
    
    <%@ include file="/default/menu.jsp" %>
    
    <h:form id="edit_user">
        <h:panelGrid columns="1" headerClass="boxTituloPagina" 
                     styleClass="boxCorpoPagina">
            <f:facet name="header">
                <h:outputText id="pageTitle" value="#{msgs.usrEdt_pageTitle}" 
                              styleClass="tituloPagina"/>
            </f:facet>
            
            <h:panelGrid id="png_erro">
                <h:messages  id="msg_erro" globalOnly="true" layout="table" 
                             errorClass="erro" infoClass="dica"/>   
            </h:panelGrid>
            
            <h:panelGrid columns="1" width="100%" align="center">          
                <h:message for="edit_user" styleClass="erro_form"/>
                <h:message for="Nome" styleClass="erro_form"/>
                <h:message for="Email" styleClass="erro_form"/>
                <h:message for="TipoUsuario" styleClass="erro_form"/>
                <h:message for="Login" styleClass="erro_form"/>
                <h:message for="Senha" styleClass="erro_form"/>
            </h:panelGrid>
            
            <h:outputText value="#{msgs.usrAdd_nameLabel}" 
                          styleClass="textoForm1" />    	
            <h:inputText maxlength="100" size="68" id="Nome" 
                         value="#{addUserBean.name}" required="true"/>
            
            <h:outputText value="#{msgs.usrAdd_mailLabel}" 
                          styleClass="textoForm1"/>
            <h:inputText maxlength="100" size="68" id="Email" 
                         value="#{addUserBean.email}" required="true"
                         validator="#{addUserBean.validateEmail}"/>	
            
            <h:outputText value="#{msgs.usrAdd_typeLabel}" 
                          styleClass="textoForm1"/>
            <h:selectOneMenu id="TipoUsuario" readonly="true" 
                value="#{addUserBean.type}">
                <f:selectItems value="#{addUserBean.allTypes}"/>
            </h:selectOneMenu>
            
            <h:outputText value="#{msgs.loginLabel}" 
                          styleClass="textoForm1" />    	
            <h:inputText maxlength="20" size="25" id="Login" 
                         value="#{addUserBean.login}" required="true"
                         validator="#{addUserBean.validateLogin}"/>
            
            <h:outputText value="#{msgs.usrEdt_passLabel}" 
                          styleClass="textoForm1"/>
            <h:inputSecret maxlength="20" size="25" id="Senha" 
                         value="#{addUserBean.password}"
                         validator="#{addUserBean.validatePassword}"/>
                         
            <h:outputText value="#{msgs.usrEdt_accsLabel}" 
                          styleClass="textoForm1"/>
            <h:selectBooleanCheckbox value="#{addUserBean.isAcessible}"/>
            
            <h:commandButton value="#{msgs.backButton}" action="#{addUserBean.back2}"/>
            
            <h:commandButton id="cmb_entrar" value="#{msgs.saveAltButton}" 
                             styleClass="botao" 
                             action="#{addUserBean.updateThis}"/>
            
        </h:panelGrid>
    </h:form>
    
    <h:panelGrid columns="1" headerClass="tituloBox" styleClass="boxCorpoPagina" 
        rendered="#{addUserBean.type == 2 && addUserBean.knowledges.rowCount > 0}">
        <f:facet name="header">
            <h:outputText id="box1Title" 
                          value="#{msgs.usrEdt_knowLabel} #{addUserBean.knowledges.rowCount}"/>
        </f:facet>
        <h:dataTable id="knowledges" columnClasses="list-column-four, list7-column-one,
                     list7-column-two" 
                     headerClass="list-header" rowClasses="list-row" 
                     styleClass="list" value="#{addUserBean.knowledges}" 
                     var="know">
            <h:column>
                <f:facet name="header">
                    <h:outputText  value=" "/>
                </f:facet>
                
                <h:outputLink 
                    value="#{facesContext.externalContext.requestContextPath}/admin/users/edit.jsf">
                    <h:graphicImage style="border: 0" width="16" height="16" 
                        id="editBt" alt="#{msgs.usrSrc_editBtLb}" 
                        url="#{facesContext.externalContext.requestContextPath}/../imgs/delete.gif"/>
                    <f:param name="idKnowledge" value="#{know.idKnowledgeArea}"/>                
                    <f:param name="idUser" value="#{sessionScope.idUser}"/>
                </h:outputLink>            
            </h:column>
            <h:column>
                <f:facet name="header">
                    <h:outputText value="#{msgs.usrEdt_dttbCol1}"/>
                </f:facet>
                <h:outputText value="#{know.name}"/>
            </h:column> 
            <h:column>
                <f:facet name="header">
                    <h:outputText value="#{msgs.usrEdt_dttbCol2}"/>
                </f:facet>
                <h:outputText value="#{know.description}"/>
            </h:column>
        </h:dataTable>
    </h:panelGrid>
    
    <h:panelGrid columns="1" headerClass="tituloBox" styleClass="boxCorpoPagina" 
        rendered="#{addUserBean.type == 2 && addUserBean.knowledges.rowCount == 0}">
        <f:facet name="header">
            <h:outputText id="box1Title2" 
                          value="#{msgs.usrEdt_knnlLabel}"/>
        </f:facet>
    </h:panelGrid>
    
    <h:form id="addKnowledge">
        <h:panelGrid rendered="#{addUserBean.type == 2 && addUserBean.hasKnowledgeList}"
        columns="1" headerClass="tituloBox" styleClass="boxCorpoPagina">
            
            <h:outputText value="#{msgs.usrEdt_addKnow}" 
                              styleClass="textoForm1" />    	
            <h:selectManyListbox value="#{addUserBean.selectedKnowledges}" size="4">
                   <f:selectItems value="#{addUserBean.knowledgesList}"/>
            </h:selectManyListbox>

            <h:commandButton id="cmb_entrar" value="#{msgs.includeButton}" 
                                 styleClass="botao" 
                                 action="#{addUserBean.addKnowledge}"/>                                 
        </h:panelGrid>
    </h:form>
</f:view>

<%@ include file="/default/footer.jsp"  %>

