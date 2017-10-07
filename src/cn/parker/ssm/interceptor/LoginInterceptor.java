package cn.parker.ssm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

//登录拦截器
public class LoginInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {

		//获取请求的url
		String url = request.getRequestURI();
		
		//判断url是否是公开 地址（实际使用时将公开 地址配置配置文件中）
		//这里公开地址是登陆提交的地址
		if(url.indexOf("login.action")>=0){
			//如果进行登录提交，放行
			return true;
		}
		
		//判断session
		HttpSession session = request.getSession();
		//从session中取出用户信息
		String username = (String) session.getAttribute("username");
		if (username!=null) {
			//身份存在，放行
			return true;
		}
		
		//执行这里表示用户身份需要认证(上面验证都不通过)，跳转登陆页面
		request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
		
		//return false;表示拦截成功
		return false;
	}
	
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {

		System.out.println("handlerInterceptor1...postHandle");
		
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

		System.out.println("handlerInterceptor1...afterCompletion");
		
	}


}
