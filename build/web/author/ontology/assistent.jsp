<%@ include file="/default/top.jsp" %>  

<f:view>   
    <f:loadBundle basename="br.ufal.ic.forbile.autoria.core.util.messages" 
                  var="msgs"/>
    
    <%@ include file="/default/menu.jsp" %>
    
    <h:panelGrid columns="1" headerClass="boxTituloPagina" 
                 styleClass="boxCorpoPagina">
        <f:facet name="header">
            <h:outputText id="pageTitle" value="#{msgs.authorOntologyAssistentMsg1}" 
                          styleClass="tituloPagina"/>
        </f:facet>
        
        <h:outputText id="pageSubTitle" value="#{ontologyViewBean.ontName}" 
                      styleClass="subTituloPagina"/>
    </h:panelGrid>
    
    <h:outputText id="pageSubTitle4" 
                      value=" " 
                      styleClass="subSubTituloPagina"/>
    
    <h:panelGrid columns="1" headerClass="boxTituloPagina2" 
                 styleClass="boxCorpoPagina2">
        <f:facet name="header">
            <h:outputText id="pageSubTitle2" 
                      value="#{msgs.genStepMsg1} #{sessionScope.stepNum} - #{assistentBean.stepTitle}" 
                      styleClass="subSubTituloPagina"/>
        </f:facet>
        
        <h:outputText id="pageSubTitle3" 
                      value="#{assistentBean.stepDescription}" 
                      styleClass="textoForm2"/>
        
        <h:panelGrid id="png_erro">
            <h:messages  id="msg_erro" globalOnly="true" layout="table" 
                         errorClass="erro" infoClass="dica"/>  
        </h:panelGrid>
        
        <h:panelGroup id="group2" style="align: right">
            
            <h:form id="stepForm" style="align: right"> 
                
                <h:message for="stepForm"           styleClass="erro_form"/>
                <h:message for="step"               styleClass="erro_form"/>
                   
                <h:dataTable id="step"
                            headerClass="list-header" rowClasses="list-row" 
                            styleClass="list2"
                             value="#{assistentBean.classes}" var="class">   
                    <h:column>
                        
                        <h:message for="selectOneOrder"     styleClass="erro_form"/>
                        <h:message for="step_properties"    styleClass="erro_form"/>
                        
                        <t:div style="margin-top: 15px">
                            <h:outputText value="#{class.objectName}"
                                styleClass="textoForm"/> 
                        </t:div>

                        <t:div style="margin-top: 5px; margin-left: 50px">
                            <h:outputText value="#{msgs.authorOntologyAssistentMsg2}" 
                                          styleClass="textoForm1"/>
                            <h:selectOneMenu id="selectOneOrder" styleClass="selectItem2"
                                             value="#{class.selectedIndividual}">
                                <f:selectItems value="#{class.individuals}"/>
                            </h:selectOneMenu> 
                            
                            <h:outputText value="  "/>
                            <h:commandButton value="#{msgs.genEditMsg1}" 
                                 rendered="#{class.hasIndividuals}"
                                 action="#{createStepsBean.backAndCancel2}"/>
                            <h:outputText value="  "/>     
                            <h:commandButton value="#{msgs.genRemoveMsg1}"
                                 rendered="#{class.hasIndividuals}"
                                 action="#{createStepsBean.backAndCancel2}"/>
                        </t:div>
                        
                        <t:div style="margin-top: 15px; margin-left: 80px">
                            <h:form>
                            <h:dataTable id="step_properties" 
                                columnClasses="list2-column-one,
                                list2-column-two" 
                                headerClass="list-header2" rowClasses="list-row" 
                                styleClass="list2" value="#{class.properties}" 
                                rendered="#{class.hasProperties}"
                                var="property">   
                        
                                <h:column>      
                                    <f:facet name="header">
                                    <h:outputText value="#{msgs.authorOntologyAssistentMsg3}"/>
                                    </f:facet>
                                    <h:outputText value="#{property.fieldName}:" 
                                              styleClass="textoForm1"/>
                                </h:column>

                                <h:column>
                                    <f:facet name="header">
                                    <h:inputText id="instance_name" maxlength="30" size="30"
                                         value="#{createStepsBean.stepTitle}"/>
                                    </f:facet>                    
                                    
                                    <h:inputText maxlength="#{property.fieldMaxSize}" size="30"
                                         required="#{!property.isNull}" value="#{property.value}"
                                         rendered="#{property.fieldType == 1}"/>
                                         
                                    <h:inputTextarea rows="4" cols="30" value="#{property.value}"
                                                     required="#{!property.isNull}"
                                                     rendered="#{property.fieldType == 2}"/>

                                    <h:selectOneMenu styleClass="selectItem2" 
                                                     required="#{!property.isNull}"
                                                     rendered="#{property.fieldType == 3}"
                                                     value="#{property.value}">
                                        <f:selectItems 
                                            value="#{property.values}"/>
                                    </h:selectOneMenu>
                                    
                                    <h:selectManyListbox styleClass="selectItem2" 
                                                     size="#{property.fieldMaxSize}"
                                                     required="#{!property.isNull}"
                                                     rendered="#{property.fieldType == 4}"
                                                     value="#{property.selectedValues}">
                                         <f:selectItems 
                                            value="#{property.values}"/>
                                    </h:selectManyListbox>
                                </h:column>
                            </h:dataTable>
                            </h:form>
                        </t:div>
                        
                        <t:div style="margin-top: 10px; margin-left: 80px">
                            <%--Commented by Dida--%>
                            <h:commandButton rendered="#{class.hasProperties}" 
                                             value="#{msgs.authorOntologyAssistentMsg4}" 
                                             action="#{createStepsBean.backAndCancel2}"/>
                            <%--
                            <h:commandButton value="Salvar alterações" 
                                             action="#{createStepsBean.backAndCancel2}"/>
                            <h:outputText value="  "/>
                            <h:commandButton value="Criar nova instância" 
                                             action="#{createStepsBean.updateStep}"/>
                            --%>
                            <%--Written by Dida just to see if another button works--%>
                            <%--h:commandButton rendered="true"
                                     style="align: right" value="My button" 
                                     action="#{createStepsBean.myButton}"/>
                     
                            <h:commandButton rendered="true"                    
                                     style="align: right" value="Lets see what happens" 
                                     action="#{assistentBean.myButton}"/--%>
     
                        </t:div>
                    </h:column>
                </h:dataTable>
                
            </h:form>
            <h:form id="stepForm2" style="align: right"> 
                <t:div style="align: right; margin-top: 20px">
                    <h:commandButton style="align: right" value="#{msgs.authorOntologyAssistentMsg5}" 
                                     action="#{assistentBean.backToAll}"/>
                    <h:outputText value="  "/>
                    <h:commandButton rendered="#{sessionScope.stepNum > 1}"                    
                                     style="align: right" value="#{msgs.authorOntologyAssistentMsg6}" 
                                     action="#{assistentBean.previous}"/>
                    <h:outputText value="  "/>
                    <h:commandButton rendered="#{sessionScope.stepNum <= sessionScope.stepsCount}" 
                                     style="align: right" value="#{msgs.authorOntologyAssistentMsg7}" 
                                     action="#{assistentBean.next}"/>
                </t:div>
            </h:form>
            
        </h:panelGroup>
    </h:panelGrid>
</f:view>

<%@ include file="/default/footer.jsp"  %>
