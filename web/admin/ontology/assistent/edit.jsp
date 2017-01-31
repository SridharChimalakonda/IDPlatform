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
    </h:panelGrid>
    
    <h:panelGrid columns="1" headerClass="boxTituloPagina2" 
                 styleClass="boxCorpoPagina2">
        <f:facet name="header">
            <h:outputText id="pageSubTitle2" 
                      value="#{msgs.editPageSubTitle2Msg1} #{sessionScope.stepNum}" 
                      styleClass="subSubTituloPagina"/>
        </f:facet>
        
        <h:panelGrid id="png_erro">
            <h:messages  id="msg_erro" globalOnly="true" layout="table" 
                         errorClass="erro" infoClass="dica"/>  
        </h:panelGrid>
        
        <h:panelGroup id="group2" style="align: right">
            
            <h:form id="stepForm" style="align: right"> 
                
                <h:message for="stepForm"           styleClass="erro_form"/>
                <h:message for="step_title"         styleClass="erro_form"/>
                <h:message for="step_description"   styleClass="erro_form"/>
                <h:message for="step"               styleClass="erro_form"/>
                
                <h:panelGrid columns="2" style="height: 60px">
                    <h:outputText value="#{msgs.editTitleOfStepMsg1}"
                                  styleClass="textoForm"/>
                    <h:inputText id="step_title" maxlength="100" size="85"
                                 value="#{createStepsBean.stepTitle}"/> 
                    
                    <h:outputText value="#{msgs.editDescriptionOfStepMsg1}" 
                                  styleClass="textoForm"/>
                    <h:inputTextarea id="step_description" rows="3" cols="63"
                                     value="#{createStepsBean.stepDescription}"/>
                </h:panelGrid>
                   
            <h:dataTable id="step"
                            headerClass="list-header" rowClasses="list-row" 
                            styleClass="list2"
                             value="#{createStepsBean.classes}" var="class">   
                    <h:column>
                        
                        <h:message for="objectName"         styleClass="erro_form"/>
                        <h:message for="selectOneOrder"     styleClass="erro_form"/>
                        <h:message for="step_properties"    styleClass="erro_form"/>
                        
                        <t:div style="margin-top: 15px">
                            <h:outputText value="#{msgs.editClassMsg1}"
                                styleClass="textoForm"/>
                                
                            <h:outputText value="#{class.className}"
                                styleClass="textoForm1"/>    
                        </t:div>
                        
                        <h:outputText value="#{msgs.editNameforTheObjectMsg1}"
                            styleClass="textoForm"/>
                        <h:inputText id="objectName" size="54" maxlength="60" 
                            value="#{class.objectName}" styleClass="selectItem2"/>
                        
                        <h:outputText value="#{msgs.editDisplayOrderMsg1}" 
                            styleClass="textoForm"/>
                        <h:selectOneMenu id="selectOneOrder" styleClass="selectItem2"
                            value="#{class.numOrderAppear}">
                            <f:selectItems value="#{createStepsBean.orderAppearList}"/>
                        </h:selectOneMenu> 

                        <h:dataTable id="step_properties" 
                            columnClasses="list3-column-one, list3-column-two,
                            list3-column-five, list3-column-four, list3-column-five" 
                            headerClass="list-header" rowClasses="list-row" 
                            styleClass="list2" value="#{class.properties}" 
                            rendered="#{class.hasProperties}"
                            var="property">   
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="#{msgs.editPropertyMsg1}"/>
                                </f:facet>
                                <h:outputText id="field" 
                                value="#{property.propertyName}"/>
                            </h:column>

                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="#{msgs.editFieldNameMsg1}"/>
                                </f:facet>
                                <h:inputText size="30" maxlength="100" id="fieldName" 
                                styleClass="selectItem" 
                                value="#{property.fieldName}"/>
                            </h:column>

                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="#{msgs.editFieldTypeMsg1}"/>
                                </f:facet>
                                <h:selectOneMenu id="fieldType" styleClass="selectItem"
                                     value="#{property.fieldType}">
                                    <f:selectItems 
                                        value="#{createStepsBean.allFieldsType}"/>
                                </h:selectOneMenu>
                            </h:column>
                            
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="#{msgs.editOrderMsg1}"/>
                                </f:facet>
                                <h:selectOneMenu id="selectOnePropOrder" 
                                    styleClass="selectItem"
                                    value="#{property.numOrderAppear}">
                                    <f:selectItems 
                                        value="#{class.orderAppearPropList}"/>
                                </h:selectOneMenu>
                            </h:column>       
                            
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="#{msgs.editIsNullMsg1}"/>
                                </f:facet>
                                <h:selectBooleanCheckbox value="#{property.isNull}"/>
                            </h:column>
                        </h:dataTable>
                        
                        <t:div style="align: center; margin-top: 5px">
                            <h:outputText rendered="#{!class.hasProperties}" 
                                styleClass="textoForm2"
                                value="#{msgs.editNoDeclaredPropertiesMsg1}"/>
                        </t:div>
                        
                        <h:commandButton rendered="#{createStepsBean.countClasses > 1}"
                            value="#{msgs.editRemoveClassFromThisStepMsg1}" 
                                 action="#{class.removeStepClass}"/>
                        
                    </h:column>
                </h:dataTable>
                
                <h:commandButton value="#{msgs.editBackAndCancelMsg1}" 
                                 action="#{createStepsBean.backAndCancel2}"/>
                <h:outputText value="  "/>
                <h:commandButton value="#{msgs.editSaveChangesMsg1}" 
                                 action="#{createStepsBean.updateStep}"/>
            </h:form>
        </h:panelGroup>
    </h:panelGrid>
</f:view>

<%@ include file="/default/footer.jsp"  %>
