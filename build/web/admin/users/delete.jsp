<%@ include file="/default/top.jsp" %>  

<f:view>   
    <f:loadBundle basename="br.ufal.ic.forbile.autoria.core.util.messages" 
                  var="msgs"/>

    <%@ include file="/default/menu.jsp" %>
    
    <h:panelGrid columns="1" headerClass="boxTituloPagina" 
                 styleClass="boxCorpoPagina">
        <f:facet name="header">
            <h:outputText id="pageTitle" value="#{msgs.usrRem_pageTitle}" 
                          styleClass="tituloPagina"/>
        </f:facet>
        
        <h:panelGrid id="png_erro">
            <h:messages  id="msg_erro" globalOnly="true" layout="table" 
                         errorClass="erro" infoClass="dica"/>   
        </h:panelGrid>
    </h:panelGrid>    
    
    <h:panelGrid columns="1" headerClass="boxTituloPagina" 
                 rendered="#{deleteUserBean.canBeRemoved}"
                 styleClass="boxCorpoPagina">
        <h:form id="form1">
            <h:message for="form1" styleClass="erro_form"/>

            <h:outputText styleClass="textoForm1" 
                value="#{msgs.userDeleteUserMsg1} #{deleteUserBean.name}."/>
            <h:outputText styleClass="textoForm1" 
                value="#{msgs.userDeleteUserMsg2}"/>
            <h:outputText styleClass="textoForm1" 
                value="#{msgs.userDeleteUserMsg3}"/>

            
            <h:commandButton value="#{msgs.deleteYesMsg1}" action="#{deleteUserBean.removeThis}"/>
            <h:commandButton value="#{msgs.deleteNoMsg1}" action="#{deleteUserBean.back}"/>
        </h:form>
    </h:panelGrid>
    
    <h:panelGrid columns="1" headerClass="boxTituloPagina" 
                 rendered="#{!deleteUserBean.canBeRemoved}"
                 styleClass="boxCorpoPagina">
        <h:form id="form2">
            <h:outputText styleClass="textoForm1" 
                value="#{msgs.userDeleteUserMsg4}"/>

            <h:commandButton value="#{msgs.userDeleteUserMsg5}" action="#{deleteUserBean.back}"/>
        </h:form>
    </h:panelGrid>
</f:view>

<%@ include file="/default/footer.jsp"  %>