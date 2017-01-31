<%@ include file="/default/top.jsp" %>  

<f:view>   
    <f:loadBundle basename="br.ufal.ic.forbile.autoria.core.util.messages" 
                  var="msgs"/>
    
    <%@ include file="/default/menu.jsp" %>
    
    <h:form id="search_ontology">
        <h:panelGrid columns="1" headerClass="boxTituloPagina" 
            styleClass="boxCorpoPagina">
            <f:facet name="header">
                <h:outputText id="pageTitle" value="#{msgs.ontologyRemoveOntologiesMsg1}" 
                    styleClass="tituloPagina"/>
            </f:facet>
            
            <h:panelGrid id="png_erro">
                <h:messages  id="msg_erro" globalOnly="true" layout="table" 
                             errorClass="erro" infoClass="dica"/>   
            </h:panelGrid>
            
            <h:panelGrid columns="1" width="100%" align="center">          
                <h:message for="search_ontology" styleClass="erro_form"/>
                <h:message id="nameError" for="Nome" styleClass="erro_form"/>
            </h:panelGrid>
            
            <h:outputText id="name_label" 
                value="#{msgs.configOntologySearchMsg1}" 
                styleClass="textoForm1"/>
            <h:inputText maxlength="100" size="68" id="Nome" 
                         value="#{searchOntologyBean.ontName}" required="true" 
                         validator="#{searchOntologyBean.validateOntName}" />	
            
            <h:commandButton id="submit" value="Buscar" 
                action="#{searchOntologyBean.search}"/>
        </h:panelGrid>
    </h:form>
    
    <h:panelGrid rendered="#{searchOntologyBean.ontologies.rowCount < 1}" id="search_empty" 
                 align="center"  styleClass="boxCorpoPagina">
        <h:outputText value="#{msgs.configSearchMsg1} #{searchOntologyBean.ontName}" 
                styleClass="tituloBox"/>
    </h:panelGrid>
    
    <h:panelGrid columns="1" headerClass="tituloBox" styleClass="boxCorpoPagina" rendered="#{searchOntologyBean.ontologies.rowCount > 0}">
        <f:facet name="header">
            <h:outputText id="box1Title" 
                          value="#{msgs.configSearchResultsMsg1} #{searchOntologyBean.ontologies.rowCount} #{msgs.configOntologiesMsg1}"/>
        </f:facet>
        <h:form>
        <h:dataTable id="ontologies" columnClasses="list-column-four, list-column-one, 
                     list-column-two, list-column-three" 
                     headerClass="list-header" rowClasses="list-row" 
                     styleClass="list" value="#{searchOntologyBean.ontologies}" 
                     var="ont1">

            <h:column>
                <f:facet name="header">
                    <h:outputText  value=" "/>
                </f:facet>
                
                <h:outputLink value="#{facesContext.externalContext.requestContextPath}/admin/ontology/delete.jsf">
                    <h:graphicImage style="border: 0" width="16" height="16" id="editBt" alt="#{msgs.ontologyRemoveOntologiesMsg1}" url="#{facesContext.externalContext.requestContextPath}/../imgs/delete.gif"/>
                    <f:param name="idOntology" value="#{ont1.idOntology}"/> 
                </h:outputLink>           
            </h:column>
            <h:column>
                <f:facet name="header">
                    <h:outputText value="#{msgs.configOntologyMsg1}"/>
                </f:facet>
                <h:outputText value="#{ont1.name}"/>
            </h:column>
            <h:column>
                <f:facet name="header">
                    <h:outputText value="#{msgs.configCreatorMsg1}"/>
                </f:facet>
                <h:outputText value="#{ont1.userCreator.name}"/>
            </h:column>
            <h:column>
                <f:facet name="header">
                    <h:outputText  value="#{msgs.configLastModifiedMsg1}"/>
                </f:facet>
                <h:outputText value="#{ont1.dateLastModified}"/>
            </h:column>
        </h:dataTable>
        </h:form>
    </h:panelGrid>
</f:view>

<%@ include file="/default/footer.jsp"  %>
