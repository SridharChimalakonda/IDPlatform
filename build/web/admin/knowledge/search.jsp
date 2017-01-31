<%@ include file="/default/top.jsp" %>  

<f:view>   
    <f:loadBundle basename="br.ufal.ic.forbile.autoria.core.util.messages" 
                  var="msgs"/>

    <%@ include file="/default/menu.jsp" %>
    
    <h:form id="search_knowledge">
        <h:panelGrid columns="1" headerClass="boxTituloPagina" 
            styleClass="boxCorpoPagina">
            <f:facet name="header">
                <h:outputText id="pageTitle" value="#{msgs.knwSrc_pageTitle}" 
                    styleClass="tituloPagina"/>
            </f:facet>
            
            <h:panelGrid columns="1" width="100%" align="center">          
                <h:message for="search_knowledge" styleClass="erro_form"/>
                <h:message id="nameError" for="Nome" styleClass="erro_form"/>
            </h:panelGrid>
            
            <h:outputText id="name_label" 
                value="#{msgs.knwSrc_srchLabel}" 
                styleClass="textoForm1"/>
            <h:inputText maxlength="100" size="68" id="Nome" 
                         value="#{searchKnowledgeBean.areaName}" required="true"/>	
            
            <h:commandButton id="submit" value="#{msgs.searchButton}" 
                action="#{searchKnowledgeBean.search}"/>
        </h:panelGrid>
    </h:form>
    
    <h:panelGrid rendered="#{searchKnowledgeBean.knowledges.rowCount < 1}" id="search_empty" 
                 align="center"  styleClass="boxCorpoPagina">
        <h:outputText value="#{msgs.knwSrc_srchNull} #{searchKnowledgeBean.areaName}" 
                styleClass="tituloBox"/>
    </h:panelGrid>
    
    <h:panelGrid columns="1" headerClass="tituloBox" styleClass="boxCorpoPagina" 
        rendered="#{searchKnowledgeBean.knowledges.rowCount > 0}">
        <f:facet name="header">
            <h:outputText id="box1Title" 
                          value="#{msgs.searchResult} #{searchKnowledgeBean.knowledges.rowCount} #{msgs.knwSrc_knowsRes}"/>
        </f:facet>
        <h:dataTable id="knows1" columnClasses="list-column-four, list-column-one, 
                     list-column-two, list-column-three" 
                     headerClass="list-header" rowClasses="list-row" 
                     styleClass="list" value="#{searchKnowledgeBean.knowledges}" 
                     var="knowledge">
            <h:column>
                <f:facet name="header">
                    <h:outputText  value=" "/>
                </f:facet>
                
                <h:outputLink 
                    value="#{facesContext.externalContext.requestContextPath}/admin/knowledge/edit.jsf">
                    <h:graphicImage style="border: 0" width="16" height="16" 
                        id="editBt" title="#{msgs.knwSrc_editBtLb}" 
                        url="#{facesContext.externalContext.requestContextPath}/../imgs/edit_knowledges.gif"/>
                    <f:param name="idKnowledge" value="#{knowledge.idKnowledgeArea}"/>                        
                </h:outputLink>            
            </h:column>
            <h:column>
                <f:facet name="header">
                    <h:outputText value="#{msgs.knwSrc_dttbCol1}"/>
                </f:facet>
                <h:outputText value="#{knowledge.name}"/>
            </h:column> 
            <h:column>
                <f:facet name="header">
                    <h:outputText value="#{msgs.knwSrc_dttbCol2}"/>
                </f:facet>
                <h:outputText value="#{knowledge.description}"/>
            </h:column>
            <h:column>
                <f:facet name="header">
                    <h:outputText  value="#{msgs.knwSrc_dttbCol3}"/>
                </f:facet>
                <h:outputText value="#{knowledge.dateCreation}"/>
            </h:column>
        </h:dataTable>
    </h:panelGrid>
    
    <h:panelGrid rendered="#{searchKnowledgeBean.lastKnowledges.rowCount < 1}" id="know_empty" 
                 align="center"  styleClass="boxCorpoPagina">
        <h:outputText value="#{msgs.knwSrc_srchEmpty}" 
                styleClass="tituloBox"/>
    </h:panelGrid>
    
    <h:panelGrid columns="1" headerClass="tituloBox" styleClass="boxCorpoPagina" 
        rendered="#{searchKnowledgeBean.knowledges == null && searchKnowledgeBean.lastKnowledges.rowCount > 0}">
        <f:facet name="header">
            <h:outputText id="box2Title" 
                          value="#{msgs.knwSrc_srchLast}"/>
        </f:facet>
        <h:dataTable id="knows2" columnClasses="list-column-four, list-column-one, 
                     list-column-two, list-column-three" 
                     headerClass="list-header" rowClasses="list-row" 
                     styleClass="list" value="#{searchKnowledgeBean.lastKnowledges}" 
                     var="knowledge2">
            <h:column>
                <f:facet name="header">
                    <h:outputText  value=" "/>
                </f:facet>
                
                <h:outputLink 
                    value="#{facesContext.externalContext.requestContextPath}/admin/knowledge/edit.jsf">
                    <h:graphicImage style="border: 0" width="16" height="16" 
                        id="editBt" title="#{msgs.knwSrc_editBtLb}" 
                        url="#{facesContext.externalContext.requestContextPath}/../imgs/edit_knowledges.gif"/>
                    <f:param name="idKnowledge" value="#{knowledge2.idKnowledgeArea}"/>                        
                </h:outputLink>            
            </h:column>
            <h:column>
                <f:facet name="header">
                    <h:outputText value="#{msgs.knwSrc_dttbCol1}"/>
                </f:facet>
                <h:outputText value="#{knowledge2.name}"/>
            </h:column>
            <h:column>
                <f:facet name="header">
                    <h:outputText value="#{msgs.knwSrc_dttbCol2}"/>
                </f:facet>
                <h:outputText value="#{knowledge2.description}"/>
            </h:column>
            <h:column>
                <f:facet name="header">
                    <h:outputText  value="#{msgs.knwSrc_dttbCol3}"/>
                </f:facet>
                <h:outputText value="#{knowledge2.dateCreation}"/>
            </h:column>
        </h:dataTable>
    </h:panelGrid>
    
</f:view>

<%@ include file="/default/footer.jsp"  %>
