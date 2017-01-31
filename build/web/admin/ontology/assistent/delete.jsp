<%@ include file="/default/top.jsp" %>  

<f:view>   
    <f:loadBundle basename="br.ufal.ic.forbile.autoria.core.util.messages" 
                  var="msgs"/>
    
    <%@ include file="/default/menu.jsp" %>
    
    <h:panelGrid columns="1" headerClass="boxTituloPagina" 
                 styleClass="boxCorpoPagina">
        <f:facet name="header">
            <h:outputText id="pageTitle" value="#{msgs.deletePageTitleMsg1}" 
                          styleClass="tituloPagina"/>
        </f:facet>
        
        <h:outputText id="pageSubTitle" value="#{ontologyViewBean.ontName}" 
                      styleClass="subTituloPagina"/>
        
        <h:panelGrid id="png_erro">
            <h:messages  id="msg_erro" globalOnly="true" layout="table" 
                         errorClass="erro" infoClass="dica"/>   
        </h:panelGrid>
    </h:panelGrid>    
    
    <h:panelGrid columns="1" headerClass="boxTituloPagina" 
                 styleClass="boxCorpoPagina">
        <h:form>
            <h:outputText styleClass="textoForm1" value="#{msgs.deleteTextoForm1Msg1} #{sessionScope.stepNum}."/>
            <h:outputText styleClass="textoForm1" value="#{msgs.deleteTextoForm1Msg2}"/>
            <h:outputText styleClass="textoForm1" value="#{msgs.deleteTextoForm1Msg3}"/>

            <h:commandButton value="#{msgs.deleteYesMsg1}" action="#{createStepsBean.removeStep}"/>
            <h:commandButton value="#{msgs.deleteNoMsg1}" action="#{createStepsBean.backAndCancel2}"/>
        </h:form>
    </h:panelGrid>
</f:view>

<%@ include file="/default/footer.jsp"  %>