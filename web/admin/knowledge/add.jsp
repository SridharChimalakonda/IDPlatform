<%@ include file="/default/top.jsp" %>  

<f:view>
    <f:loadBundle basename="br.ufal.ic.forbile.autoria.core.util.messages" 
                  var="msgs"/>
    
    <%@ include file="/default/menu.jsp" %>
    
    <h:form id="add_knowledge">
        <h:panelGrid columns="1" headerClass="boxTituloPagina" 
                     styleClass="boxCorpoPagina">
            <f:facet name="header">
                <h:outputText id="pageTitle" value="#{msgs.knwAdd_pageTitle}" 
                              styleClass="tituloPagina"/>
            </f:facet>
            
            <h:panelGrid id="png_erro">
                <h:messages  id="msg_erro" globalOnly="true" layout="table" 
                             errorClass="erro" infoClass="dica"/>   
            </h:panelGrid>
            
            <h:panelGrid columns="1" width="100%" align="center">          
                <h:message for="add_knowledge" styleClass="erro_form"/>
                <h:message for="Nome" styleClass="erro_form"/>
                <h:message for="Descricao" styleClass="erro_form"/>
            </h:panelGrid>
            
            <h:outputText value="#{msgs.knwAdd_nameLabel}" 
                          styleClass="textoForm1" />    	
            <h:inputText maxlength="100" size="68" id="Nome" 
                         value="#{addKnowledgeBean.name}" required="true"
                         validator="#{addKnowledgeBean.validateName}"/>
            
            <h:outputText value="#{msgs.knwAdd_descLabel}" 
                          styleClass="textoForm1"/>
            <h:inputTextarea rows="5" cols="50" id="Descricao" 
                         value="#{addKnowledgeBean.description}"/>	
            
            <h:commandButton id="cmb_entrar" value="#{msgs.saveButton}" 
                             styleClass="botao" 
                             action="#{addKnowledgeBean.saveNew}"/>
            
        </h:panelGrid>
    </h:form>
</f:view>

<%@ include file="/default/footer.jsp"  %>
