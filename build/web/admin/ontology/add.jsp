<%@ include file="/default/top.jsp" %>  

<f:view> 
    <f:loadBundle basename="br.ufal.ic.forbile.autoria.core.util.messages" 
                  var="msgs"/>
    
    <%@ include file="/default/menu.jsp" %>
    
    <h:form id="add_ontology" enctype="multipart/form-data">
        <h:panelGrid columns="1" headerClass="boxTituloPagina" 
                     styleClass="boxCorpoPagina">
            <f:facet name="header">
                <h:outputText id="pageTitle" value="#{msgs.ontAdd_pageTitle}" 
                              styleClass="tituloPagina"/>
            </f:facet>
            
            <h:panelGrid id="png_erro">
                <h:messages  id="msg_erro" globalOnly="true" layout="table" 
                             errorClass="erro" infoClass="dica"/>   
            </h:panelGrid>
            
            <h:panelGrid columns="1" width="100%" align="center">          
                <h:message for="add_ontology" styleClass="erro_form"/>
                <h:message id="uploadError" for="Arquivo" styleClass="erro_form"/>
                <h:message id="nameError" for="Nome" styleClass="erro_form"/>
                <h:message id="descError" for="Descrição" styleClass="erro_form"/>
            </h:panelGrid>
            
            <h:outputText id="ont_file_txt" value="#{msgs.ontAdd_fileLabel}" 
                          styleClass="textoForm1" />    	
            
            <t:inputFileUpload id="Arquivo" value="#{addOntologyBean.file}" 
                               size="67" storage="file" required="true">  
                <%-- accept="/owl/*" --%>
                <f:validator validatorId="uploadValidator"/>
            </t:inputFileUpload>
            
            <h:outputText id="name_label" value="#{msgs.ontAdd_nameLabel}" 
                          styleClass="textoForm1"/>
            <h:inputText maxlength="100" size="68" id="Nome" 
                         value="#{addOntologyBean.ontName}" required="true"
                         validator="#{addOntologyBean.validateOntName}"/>	
            
            <h:outputText id="know_area_txt" 
                          value="#{msgs.ontAdd_knowLabel}" 
                          styleClass="textoForm1"/>
            
            <h:selectOneMenu id="know_area_vlr" 
                             value="#{addOntologyBean.relatedKnowArea}">
                <f:selectItems value="#{addOntologyBean.allKnowAreas}"/>
            </h:selectOneMenu>
            
            <h:outputText id="descript_label" value="#{msgs.ontAdd_descLabel}" 
                          styleClass="textoForm1"/>
            <h:inputTextarea  id="Descrição" rows="4" cols="50" 
                              value="#{addOntologyBean.ontDescription}" 
                              required="true"/>
            
            <h:commandButton id="cmb_entrar" value="#{msgs.saveButton}" 
                             styleClass="botao" 
                             action="#{addOntologyBean.doSaveNew}"/>
            
        </h:panelGrid>
    </h:form>
</f:view>

<%@ include file="/default/footer.jsp"  %>