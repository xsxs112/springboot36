package ksmart36.mybatis.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


@Component
public class CommonInterceptor	implements HandlerInterceptor {
	//@Component <- 클래스위에 위치하게되면 메서드호출시 객체화 되는게 아니라
	//클래스자체를 IOC컨테이너에 등록을 시켜줌(@Autowired 사용가능)

	
	private static final Logger log = LoggerFactory.getLogger(CommonInterceptor.class);

	
	 @Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		 //요청이 일어났을때 controller로 가기전
		 log.info("CommonInterceptor====================================================START");
		 log.info("ACCESS INFO =========================================================START");
		 log.info("Port			 :::::: {}", request.getLocalPort());
		 log.info("serverName	 :::::: {}", request.getServerName());
		 log.info("getMethod	 :::::: {}", request.getMethod());
		 log.info("getRequestURI :::::: {}", request.getRequestURI());
		 log.info("ACCESS INFO =========================================================START");
		 
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
	 @Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		 //컨트롤러에서 작업이 끝났을 때
		 // afterCompletion : 타임리프를 거치고 나서 실행( 뷰를 거치고 난 후 실행)
		 log.info("CommonInterceptor====================================================END");
		 
		 
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	 

	
	
}
