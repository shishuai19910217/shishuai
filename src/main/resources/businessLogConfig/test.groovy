package businessLogConfig
import com.ido85.frame.web.rest.utils.RestUserUtils;

class TestLog {
	
    def context
   
   	def CommonController_getchannels(){
   		[category:"查询类", log:"${getPreTemplate()}+ceshiku"]
   		
   	}
    def KeywordController_keywordList(){
    	"${getPreTemplate()}:${context._param0.projectId}:"
    }

    def getPreTemplate(){
    	"${RestUserUtils.getUserInfo().getUserId().toString()}-"
    }

}