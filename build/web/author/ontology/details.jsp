<%@ include file="/default/top.jsp" %>  

<f:view>   
    <f:loadBundle basename="br.ufal.ic.forbile.autoria.core.util.messages" 
                  var="msgs"/>
    
    <%@ include file="/default/menu.jsp" %>
    
    <h:panelGrid columns="1" headerClass="boxTituloPagina" 
                 styleClass="boxCorpoPagina">
        <f:facet name="header">
            <h:outputText id="pageTitle" value="#{msgs.authorOntologyDetailsMsg1}" 
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
        
        
        <h:outputText id="label1" value="#{msgs.configOntologyMsg1}" 
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
        
        <h:outputText id="labe25" value="#{msgs.authorOntologyDetailsMsg2}" 
                      styleClass="textoForm1"/>
        
        <h:outputText id="label21" value="#{ontologyViewBean.countOfSteps} #{msgs.genStepsMsg1}" 
                      styleClass="textoForm2"/>  
                      
        <h:outputText id="label18" 
                      rendered="#{ontologyViewBean.hasConfigAssistent == true}" 
                      value="" styleClass="textoForm1"/>
                      
        <h:outputText id="label17" value="" 
                      styleClass="textoForm1"/>
                      
        <h:outputText id="label19" value="" 
                      styleClass="textoForm1"/>
                      
        <h:outputText id="label20" value="" 
                      styleClass="textoForm1"/>

   </h:panelGrid>
   
   <h:panelGrid columns="1" headerClass="boxTituloPagina" 
                 styleClass="boxCorpoPagina">  
   
        <h:dataTable id="stepLinks" value="#{ontologyViewBean.assistentSteps}" 
                     headerClass="list-header" rowClasses="list-row" 
                     styleClass="list" columnClasses="list8-column-one,
                     list8-column-two, list8-column-three"   
                     var="steps">
            
            <h:column>
                <f:facet name="header">
                    <h:outputText  value=" "/>
                </f:facet>
                
                <h:outputLink value="#{facesContext.externalContext.requestContextPath}/author/ontology/assistent.jsf">
                    <h:graphicImage style="border: 0" width="16" height="16" id="editBt" 
                                    alt="#{msgs.authorOntologyDetailsMsg3} #{steps.stepNum}" title="#{msgs.authorOntologyDetailsMsg4} #{steps.stepNum}"
                            url="#{facesContext.externalContext.requestContextPath}/../imgs/edit.gif"/>      
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
        
        <h:panelGrid align="center" style="margin-top: 20px; align: center;">
            <h:outputLink value="JavaScript:history.go(-1)">
                <!--h:graphicImage 
                    url="imgs/anterior.gif" 
                    style="align: center; border: 0;"/-->
            </h:outputLink>
        </h:panelGrid>
    </h:panelGrid>
</f:view>

<%@ include file="/default/footer.jsp"  %>
