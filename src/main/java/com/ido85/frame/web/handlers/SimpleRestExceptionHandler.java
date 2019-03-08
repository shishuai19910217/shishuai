/**
 * 
 */
package com.ido85.frame.web.handlers;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.ido85.config.properties.BussinessMsgCodeProperties;
import com.ido85.frame.common.exceptions.BusinessException;
import com.ido85.frame.common.restful.ProcessStatus;
import com.ido85.frame.common.restful.Resource;

/**
 * 异常处理
 * @author rongxj
 *
 */
@ControllerAdvice(annotations = RestController.class)
public class SimpleRestExceptionHandler {

	@Inject
	private BussinessMsgCodeProperties msg;
	
	
	/**
	 * 业务异常
	 * @param req
	 * @param e
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public Resource<String> businessExceptionHandler(HttpServletRequest req, BusinessException e) throws Exception {
		e.printStackTrace();
		Resource<String> r = null;
		if(e.getStatus().getRetCode() == -3 || e.getStatus().getRetCode() == -5){
			String string = null;
			r = new Resource<String>(string);
		}else{
			r = new Resource<String>(e.getMessage(), e.getStatus());
		}
        return r;
    }
	
	/**
	 * 内部异常
	 * @param req
	 * @param e
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(value = Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Resource<String> exceptionHandler(HttpServletRequest req, Exception e) throws Exception {
		e.printStackTrace();
		Resource<String> r = new Resource<String>(e.getMessage(), ProcessStatus.COMMON_ERROR);
        return r;
    }
	
	/**
	 * 参数错误
	 * @param req
	 * @param e
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(value = {IllegalArgumentException.class, MissingServletRequestParameterException.class})
	@ResponseBody
    public Resource<String> assertHandler(HttpServletRequest req, Exception e) throws Exception {
		e.printStackTrace();
		Resource<String> r = new Resource<String>(e.getMessage(), msg.getProcessStatus("FORM_PARAM_ERROR"));
		
        return r;
    }
	
	/**
	 * 表单验证错误
	 * @param req
	 * @param e
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(value = {BindException.class, MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
	@ResponseBody
    public Resource<List<ValidResult>> validHandler(HttpServletRequest req, Exception e) throws Exception {
		e.printStackTrace();
		List<FieldError> errors = null;
		List<ValidResult> results = Lists.newArrayList();
		if(e instanceof BindException){
			errors = ((BindException)e).getFieldErrors();
		}else if(e instanceof HttpMessageNotReadableException){
			ValidResult result = new ValidResult("body", "body", "请求内容不能为空");
			results.add(result);
			return new Resource<List<ValidResult>>(results, msg.getProcessStatus("FORM_PARAM_ERROR"));
		}else{
			errors = ((MethodArgumentNotValidException)e).getBindingResult().getFieldErrors();
		}
		 
		errors.forEach(o->{
			results.add(new ValidResult(o));
		});
		Resource<List<ValidResult>> r = new Resource<List<ValidResult>>(results, msg.getProcessStatus("FORM_PARAM_ERROR"));
		
        return r;
    }
	
	
	/**
	 * 认证异常
	 * @param req
	 * @param e
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(value = UnauthenticatedException.class)
	@ResponseBody
    public Resource<String> unauthenticatedHandler(HttpServletRequest req, UnauthenticatedException e) throws Exception {
		Resource<String> r = new Resource<String>(e.getMessage(), msg.getProcessStatus("ILLEGAL_REQUEST"));
		
        return r;
    }
	
	/**
	 * 无授权
	 * @param req
	 * @param e
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(value = UnauthorizedException.class)
	@ResponseBody
    public Resource<String> unauthorizedHandler(HttpServletRequest req, UnauthorizedException e) throws Exception {
		Resource<String> r = new Resource<String>(e.getMessage(), msg.getProcessStatus("AUTHLOGIN_ERROR"));
		
        return r;
    }
	
	static class ValidResult{
		
		private String code;
		private String field;
		private String message;
		
		public ValidResult(FieldError error){
			this.code = error.getCode();
			this.field = error.getField();
			this.message = error.getDefaultMessage();
		}
		
		public ValidResult(String code, String field, String message){
			this.code = code;
			this.field = field;
			this.message = message;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getField() {
			return field;
		}

		public void setField(String field) {
			this.field = field;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
		
	}
}
