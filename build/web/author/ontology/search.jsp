<%@ include file="/default/top.jsp" %>  

<f:view>   
    <f:loadBundle basename="br.ufal.ic.forbile.autoria.core.util.messages" 
                  var="msgs"/>
    
    <%@ include file="/default/menu.jsp" %>
    
    <h:form id="search_ontology">
        <h:panelGrid columns="1" headerClass="boxTituloPagina" 
            styleClass="boxCorpoPagina">
            <f:facet name="header">
                <h:outputText id="pageTitle" value="#{msgs.genOntologiesMsg1}" 
                    styleClass="tituloPagina"/>
            </f:facet>
            
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
            
            <h:commandButton id="submit" value="#{msgs.configSearchMsg1}" 
                action="#{searchOntologyBean.search}"/>
        </h:panelGrid>
    </h:form>
    
    <h:panelGrid rendered="#{searchOntologyBean.ontologies.rowCount < 1}" id="search_empty" 
                 align="center"  styleClass="boxCorpoPagina">
        <h:outputText value="#{msgs.configNoOntologyWasLocalizedeMsg1} \"#{searchOntologyBean.ontName}\" #{msgs.authorOntologySearchMsg1}" 
                styleClass="tituloBox"/>
        
    </h:panelGrid>
    
    <h:panelGrid columns="1" headerClass="tituloBox" styleClass="boxCorpoPagina" rendered="#{searchOntologyBean.ontologies.rowCount > 0}">
        <f:facet name="header">
            <h:outputText id="box1Title" 
                          value="#{msgs.configSearchResultsMsg1} #{searchOntologyBean.ontologies.rowCount} #{msgs.configOntologiesMsg1}"/>
        </f:facet>
        <h:dataTable id="ontologies" columnClasses="list-column-four, list-column-one, 
                     list-column-two, list-column-three" 
                     headerClass="list-header" rowClasses="list-row" 
                     styleClass="list" value="#{searchOntologyBean.ontologies}" 
                     var="ont1">
            <h:column>
                <f:facet name="header">
                    <h:outputText  value=" "/>
                </f:facet>
                
                <h:outputLink value="#{facesContext.externalContext.requestContextPath}/author/ontology/details.jsf">
                    <h:graphicImage style="border: 0" width="16" height="16" id="editBt" alt="#{msgs.authorOntologySearchMsg2}" url="#{facesContext.externalContext.requestContextPath}/../imgs/search.gif"/>
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
                    <h:outputText value="#{msgs.authorOntologySearchMsg3}"/>
                </f:facet>
                <h:outputText value="#{ont1.knowledgeArea.name}"/>
            </h:column>
            <h:column>
                <f:facet name="header">
                    <h:outputText  value="#{msgs.authorOntologySearchMsg4}"/>
                </f:facet>
                <h:outputText value="#{ont1.dateLastModified}"/>
            </h:column>
        </h:dataTable>
    </h:panelGrid>
    
    <h:panelGrid rendered="#{searchOntologyBean.lastOntologies.rowCount < 1}" id="ontology_empty" 
                 align="center"  styleClass="boxCorpoPagina">
        <h:outputText value="#{msgs.authorOntologySearchMsg5}" 
                styleClass="tituloBox"/>
    </h:panelGrid>
    
    <h:panelGrid columns="1" headerClass="tituloBox" styleClass="boxCorpoPagina" rendered="#{searchOntologyBean.ontologies == null && searchOntologyBean.lastOntologies.rowCount > 0}">
        <f:facet name="header">
            <h:outputText id="box2Title" 
                          value="#{msgs.authorOntologySearchMsg6}"/>
        </f:facet>
        <h:dataTable id="ontologies2" columnClasses="list-column-four, list-column-one, 
                     list-column-two, list-column-three" 
                     headerClass="list-header" rowClasses="list-row" 
                     styleClass="list" value="#{searchOntologyBean.lastOntologies}" 
                     var="ont2">
            <h:column>
                <f:facet name="header">
                    <h:outputText  value=" "/>
                </f:facet>
                
                <h:outputLink value="#{facesContext.externalContext.requestContextPath}/author/ontology/details.jsf">
                    <h:graphicImage style="border: 0" width="16" height="16" id="editBt" alt="Exibir detalhes" url="#{facesContext.externalContext.requestContextPath}/../imgs/search.gif"/>
                    <f:param name="idOntology" value="#{ont2.idOntology}"/>                        
                </h:outputLink>            
            </h:column>
            <h:column>
                <f:facet name="header">
                    
                    <h:outputText value="#{msgs.configOntologyMsg1}"/>
                </f:facet>
                <h:outputText value="#{ont2.name}"/>
            </h:column>
            <h:column>
                <f:facet name="header">
                    <h:outputText value="#{msgs.authorOntologySearchMsg3}"/>
                </f:facet>
                <h:outputText value="#{ont2.knowledgeArea.name}"/>
            </h:column>
            <h:column>
                <f:facet name="header">
                    <h:outputText  value="#{msgs.authorOntologySearchMsg4}"/>
                </f:facet>
                <h:outputText value="#{ont2.dateLastModified}"/>
            </h:column>
        </h:dataTable>
    </h:panelGrid>
    
</f:view>

<%@ include file="/default/footer.jsp"  %>
