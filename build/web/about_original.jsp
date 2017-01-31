<%@ include file="/default/top.jsp" %>  

<f:view>   
    <f:loadBundle basename="br.ufal.ic.forbile.autoria.core.util.messages" 
                  var="msgs"/>
                  
    <h:form id="add_knowledge">
        <h:panelGrid columns="1" headerClass="boxTituloPagina" 
            styleClass="boxCorpoPagina">
            <f:facet name="header">
                <h:outputText id="pageTitle" 
                    value="#{msgs.about_pageTitle}" 
                    styleClass="tituloPagina"/>
            </f:facet>	
        </h:panelGrid>
    </h:form>
    
    
    
    
</f:view>

<%@ include file="/default/footer.jsp"  %>