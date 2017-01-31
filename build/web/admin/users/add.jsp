<%@ include file="/default/top.jsp" %>  

<f:view>
    <f:loadBundle basename="br.ufal.ic.forbile.autoria.core.util.messages" 
                  var="msgs"/>
    
    <%@ include file="/default/menu.jsp" %>
    
    <h:form id="add_user">
        <h:panelGrid columns="1" headerClass="boxTituloPagina" 
                     styleClass="boxCorpoPagina">
            <f:facet name="header">
                <h:outputText id="pageTitle" value="#{msgs.usrAdd_pageTitle}" 
                              styleClass="tituloPagina"/>
            </f:facet>
            
            <h:panelGrid id="png_erro">
                <h:messages  id="msg_erro" globalOnly="true" layout="table" 
                             errorClass="erro" infoClass="dica"/>   
            </h:panelGrid>
            
            <h:panelGrid columns="1" width="100%" align="center">          
                <h:message for="add_user" styleClass="erro_form"/>
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
            <h:selectOneMenu id="TipoUsuario" value="#{addUserBean.type}">
                <f:selectItems value="#{addUserBean.allTypes}"/>
            </h:selectOneMenu>
            
            <h:outputText value="#{msgs.loginLabel}" 
                          styleClass="textoForm1" />    	
            <h:inputText maxlength="20" size="25" id="Login" 
                         value="#{addUserBean.login}" required="true"
                         validator="#{addUserBean.validateLogin}"/>
            
            <h:outputText value="#{msgs.passwordLabel}" 
                          styleClass="textoForm1"/>
            <h:inputSecret maxlength="20" size="25" id="Senha" 
                         value="#{addUserBean.password}" required="true"
                         validator="#{addUserBean.validatePassword}"/>
            
            <h:commandButton id="cmb_entrar" value="#{msgs.saveButton}" 
                             styleClass="botao" 
                             action="#{addUserBean.saveNew}"/>
            
        </h:panelGrid>
    </h:form>
</f:view>

<%@ include file="/default/footer.jsp"  %>
