package businessLogConfig
import com.ido85.frame.web.rest.utils.RestUserUtils;

class DefaultLog {

    def context

    def OrganizationApplicationImpl_createAsTopOrganization() {
        "${getPreTemplate()}:创建顶层机构:${context._param0.name}"
    }

    def OrganizationApplicationImpl_createCompany() {
        "${getPreTemplate()}:为${context._param0.name},创建分公司:${context._param1.name}"
    }

    def OrganizationApplicationImpl_assignChildOrganization() {
        "${getPreTemplate()}:向${context._param0.name},分配子机构:${context._param1.name},期限为${context._param2}"
    }

    def OrganizationApplicationImpl_createDepartment() {
        "${getPreTemplate()}:在${context._param0.name}下创建部门:${context._param1.name}"
    }

    def OrganizationApplicationImpl_terminateEmployeeOrganizationRelation() {
        "${getPreTemplate()}:终止机构${context._param0.name}"
    }
    
    def WsSecurityAccessApplication_getUserByUserAcount(){
    	"用户（${context._param0}）尝试登录"
    }
    
    def getPreTemplate(){
    	"${RestUserUtils.getUserInfo().getUserId()}-"
    }
    
    def SentimentController_sentimentUpdate(){
   		[category:"前台正负面修改", log:"用户Id:${getPreTemplate()},sentimentState:修改前:${context._param0.preSentimentState},修改后:${context._param0.sentimentState},relationId:${context._param0.relationId}"]	
    }

}