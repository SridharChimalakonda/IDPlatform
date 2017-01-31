<%@ include file="/default/top.jsp"  %>

<f:view>
    <f:loadBundle basename="br.ufal.ic.forbile.autoria.core.util.messages" 
                  var="msgs"/>
    <center>
        <h:panelGrid id="png_erro">
            <h:messages  id="msg_erro" globalOnly="true" layout="table" 
                         errorClass="erro" infoClass="dica"/>   
        </h:panelGrid>
    </center>
    <h:form id="frm_autenticacao">
        <table align="center" cellspacing="15">	
            <tr>
                <td>
                    <h:panelGrid columns="2" id="png_login">
                        <h:outputText id="opt_login" 
                                      value="#{msgs.loginLabel}" 
                                      styleClass="textoForm1"/>
                        <h:inputText id="ipt_login" 
                                     value="#{login.strLogin}"
                                     required="true"/>
                        
                        <h:outputText id="opt_senha" 
                                       value="#{msgs.passwordLabel}" 
                                      styleClass="textoForm1"/>
                        <h:inputSecret id="ips_senha" 
                                       value="#{login.strPassword}"
                                       required="true"/>							  									
                    </h:panelGrid>
                    <center>
                        <h:panelGrid columns="1" id="bt_login">
                            <h:commandButton id="cmb_entrar" 
                                             value="#{msgs.logonButton}" 
                                             styleClass="botao" 
                                             action="#{login.doAuthentication}"/>
                        </h:panelGrid>
                    </center>
                </td>
            </tr>
        </table>
        <br><br>
    </h:form>
</f:view>

<%@ include file="/default/footer.jsp"  %>