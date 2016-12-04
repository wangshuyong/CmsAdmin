package com.sea.util;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.apache.struts2.ServletActionContext;
import org.aspectj.lang.JoinPoint;
import com.sea.model.Log;
import com.sea.model.User;
import com.sea.service.LogService;

@Component("OperateLogger")
public class LogUtil {

	private LogService logService;// 日志记录Service

	public LogService getLogService() {
		return logService;
	}

	@Resource(name = "logService")
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	/**
	 * 用戶添加操作日志(后置通知)
	 * 
	 * @param joinPoint
	 * @param rtv
	 * @throws Throwable
	 */
	@SuppressWarnings("rawtypes")
	public void operationLog(JoinPoint joinPoint, Object rtv) throws Throwable {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("loginUser");
		if (null == u) {
			return;
		}
		String classname = joinPoint.getTarget().getClass().getName();// 获取当前的类名
		// 获取方法名
		String methodName = joinPoint.getSignature().getName();
		// 获取操作内容

		String moduleName = "";
		String action = "";
		Object[] arguments = joinPoint.getArgs();
		Class targetClass = Class.forName(classname);
		Method[] methods = targetClass.getMethods();
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length) {
					moduleName = method.getAnnotation(BussAnnotation.class)
							.moduleName();
					action = method.getAnnotation(BussAnnotation.class)
							.option();
					break;
				}
			}
		}
		// 创建日志对象
		if (rtv != "fail") {
			Log log = new Log();
			log.setModuleName(moduleName);
			log.setAction(action);
			log.setCreateTime(new Timestamp(System.currentTimeMillis()));// 操作时间
			log.setOperator_id(u.getId());// 操作 人
			log.setOperator_result("success");
			logService.addLog(log);// 添加日志
		}

	}
}
