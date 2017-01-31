<%@ include file="/default/top.jsp" %>  

<f:view>   
    <f:loadBundle basename="br.ufal.ic.forbile.autoria.core.util.messages" 
                  var="msgs"/>
    
    <%@ include file="/default/menu.jsp" %>
    
    <h:panelGrid columns="1" headerClass="boxTituloPagina" 
                 styleClass="boxCorpoPagina">
        <f:facet name="header">
            <h:outputText id="pageTitle" value="#{msgs.editPageTitleMsg1}" 
                          styleClass="tituloPagina"/>
        </f:facet>
        
        <h:outputText id="pageSubTitle" value="#{ontologyViewBean.ontName}" 
                      styleClass="subTituloPagina"/>
        
        <h:panelGrid id="png_erro">
            <h:messages  id="msg_erro" globalOnly="true" layout="table" 
                         errorClass="erro" infoClass="dica"/>   
        </h:panelGrid>
    </h:panelGrid>    
    
    <h:panelGrid columns="2" styleClass="boxCorpoPagina3" 
                 columnClasses="list2-column-one, list2-column-two" >
        
        
        <h:outputText id="label1" value="#{msgs.genOntologiesMsg1}" 
                      styleClass="textoForm1"/>
        
        <h:outputText id="label7" value="#{ontologyViewBean.ontName}" 
                      styleClass="textoForm2"/>
        
        <h:outputText id="label2" value="#{msgs.genAddedOnMsg1}" 
                      styleClass="textoForm1"/>                  
        
        <h:outputText id="label8" value="#{ontologyViewBean.dateCreation}" 
                      styleClass="textoForm2"/>
        
        <h:outputText id="label13" value="#{msgs.genLastUpdatedOnMsg1}" 
                      styleClass="textoForm1"/>
        
        <h:outputText id="label14" value="#{ontologyViewBean.dateLastModified}" 
                      styleClass="textoForm2"/>
        
        <h:outputText id="label3" value="#{msgs.genResponsibleMsg1}" 
                      styleClass="textoForm1"/>
        
        <h:outputText id="label9" value="#{ontologyViewBean.userCreator}" 
                      styleClass="textoForm2"/>
        
        <h:outputText id="label4" value="#{msgs.genDescriptionMsg1}" 
                      styleClass="textoForm1"/>
        
        <h:outputText id="label10" value="#{ontologyViewBean.description}" 
                      styleClass="textoForm2"/>
        
        <h:outputText id="label5" value="#{msgs.genNumberOfClassesMsg1}" 
                      styleClass="textoForm1"/>
        
        <h:outputText id="label11" value="#{ontologyViewBean.qtdClasses}" 
                      styleClass="textoForm2"/>
        
        <h:outputText id="label6" value="#{msgs.genAvailableForInstantiationMsg1}" 
                      styleClass="textoForm1"/>
        
        <h:form id="access">
            <h:panelGrid columns="2" style="align: left">
                <h:selectOneRadio 
                    value="#{ontologyViewBean.isAcessible}" styleClass="textoForm2">
                    <f:selectItems
                        value="#{ontologyViewBean.accessOptions}" />
                </h:selectOneRadio>            
                <h:commandButton id="save" value="#{msgs.genChangeMsg1}" 
                                 action="#{ontologyViewBean.changeOntAccess}">
                </h:commandButton>
            </h:panelGrid>
        </h:form>
        
        <h:outputText id="label15" value="#{msgs.genConfigurationWizardMsg1}" 
                      styleClass="textoForm1"/>
        
        <h:outputLink value="#{facesContext.externalContext.requestContextPath}/admin/ontology/assistent/create.jsf" 
                      rendered="#{ontologyViewBean.hasConfigAssistent == false}">
            <h:outputText id="label16_link"
                          value="#{msgs.genCreateStepWizardMsg1}" 
                          styleClass="textoForm2"/> 
            <f:param name="stepNum" value="1"/>
        </h:outputLink>    
        
        <h:outputText id="label16"
                      rendered="#{ontologyViewBean.hasConfigAssistent == true}"
                      value="#{ontologyViewBean.countOfSteps} #{msgs.genStepsMsg1}" 
                      styleClass="textoForm2"/>
                      
        <h:outputText id="label18" 
                      rendered="#{ontologyViewBean.hasConfigAssistent == true}" 
                      value="" styleClass="textoForm1"/>

        <h:outputLink value="#{facesContext.externalContext.requestContextPath}/admin/ontology/assistent/create.jsf" 
                      rendered="#{ontologyViewBean.hasConfigAssistent == true}">
            <h:outputText id="label18_link"
                          value="#{msgs.genClickToCreateStepMsg1} #{ontologyViewBean.countOfSteps + 1}" 
                          styleClass="textoForm2"/> 
            <f:param name="stepNum" value="#{ontologyViewBean.countOfSteps + 1}"/>
        </h:outputLink>
                      
        <h:outputText id="label17" value="" 
                      styleClass="textoForm1"/>
                      
        <h:outputText id="label19" value="" 
                      styleClass="textoForm1"/>
                      
        <h:outputText id="label20" value="" 
                      styleClass="textoForm1"/>

        <h:dataTable id="stepLinks" value="#{ontologyViewBean.assistentSteps}" 
                     headerClass="list-header" rowClasses="list-row" 
                     styleClass="list" columnClasses="list5-column-one,
                     list5-column-one,list5-column-one,
                     list5-column-two, list5-column-three, list5-column-four"   
                     var="steps">
            <h:column>
                <f:facet name="header">
                    <h:outputText  value=" "/>
                </f:facet>
                
                <h:outputLink rendered="#{steps.stepNum > 1}"
                    value="#{facesContext.externalContext.requestContextPath}/admin/ontology/edit.jsf">
                    <h:graphicImage style="border: 0" width="16" height="16" id="upBt" 
                            title="#{msgs.genMoveUpMsg1}"
                            url="#{facesContext.externalContext.requestContextPath}/../imgs/up.gif"/>
                    <f:param name="action" value="up"/>        
                    <f:param name="stepNum" value="#{steps.stepNum}"/>        
                </h:outputLink>
            </h:column>
            
            <h:column>
                <f:facet name="header">
                    <h:outputText  value=" "/>
                </f:facet>
                
                <h:outputLink rendered="#{steps.stepNum < ontologyViewBean.countOfSteps}"
                    value="#{facesContext.externalContext.requestContextPath}/admin/ontology/edit.jsf">
                    <h:graphicImage style="border: 0" width="16" height="16" id="downBt" 
                            title="#{msgs.genMoveUpMsg1}"
                            url="#{facesContext.externalContext.requestContextPath}/../imgs/down.gif"/>
                    <f:param name="action" value="down"/>        
                    <f:param name="stepNum" value="#{steps.stepNum}"/>        
                </h:outputLink>
            </h:column>
            
            <h:column>
                <f:facet name="header">
                    <h:outputText  value=" "/>
                </f:facet>
                
                <h:outputLink value="#{facesContext.externalContext.requestContextPath}/admin/ontology/assistent/edit.jsf">
                    <h:graphicImage style="border: 0" width="16" height="16" id="editBt" 
                                    alt="#{msgs.genEditStepMsg1} #{steps.stepNum}" title="#{msgs.genEditStepMsg1} #{steps.stepNum}"
                            url="#{facesContext.externalContext.requestContextPath}/../imgs/edit.gif"/>
                    <f:param name="action" value="edit"/>        
                    <f:param name="stepNum" value="#{steps.stepNum}"/>        
                </h:outputLink>
            </h:column>
            
            <h:column>
                <f:facet name="header">
                    <h:outputText  value=" "/>
                </f:facet>
                
                <h:outputLink value="#{facesContext.externalContext.requestContextPath}/admin/ontology/assistent/delete.jsf">
                    <h:graphicImage style="border: 0" width="16" height="16" id="editBt" 
                            alt="#{msgs.genDeleteStepMsg1} #{steps.stepNum}" title="#{msgs.genDeleteStepMsg1} #{steps.stepNum}"
                            url="#{facesContext.externalContext.requestContextPath}/../imgs/delete.gif"/>
                    <f:param name="stepNum" value="#{steps.stepNum}"/>                        
                </h:outputLink>
            </h:column>
            
            <h:column>
                <f:facet name="header">
                    <h:outputText  value=" "/>
                </f:facet>
                
                <h:outputText value="#{msgs.genStepMsg1} #{steps.stepNum}"/>
            </h:column>    
            
            <h:column>
                <f:facet name="header">
                    <h:outputText  value=" "/>
                </f:facet>
                
                <h:outputText value="#{steps.title}"/>
            </h:column>
        </h:dataTable>
        
        <h:outputText id="empty_label" value="" 
                      styleClass="textoForm1"/>
        
        <h:panelGrid style="margin-top: 20px; align: center;">
            <h:outputLink value="#{facesContext.externalContext.requestContextPath}/admin/ontology/config.jsf">
                <!--h:graphicImage 
                    url="imgs/anterior.gif" 
                    style="align: center; border: 0;"/-->
            </h:outputLink>
        </h:panelGrid>
    </h:panelGrid>
</f:view>

<%@ include file="/default/footer.jsp"  %>
