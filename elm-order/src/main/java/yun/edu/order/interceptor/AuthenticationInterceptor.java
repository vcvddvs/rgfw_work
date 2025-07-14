package yun.edu.order.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import yun.edu.common.util.JwtUtil;

public class AuthenticationInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        logger.debug("AuthenticationInterceptor处理请求: {}", request.getRequestURI());
        
        // 如果是OPTIONS请求，放行
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            logger.debug("OPTIONS请求，放行");
            return true;
        }

        // 获取Authorization头
        String authHeader = request.getHeader("Authorization");
        logger.debug("Authorization头: {}", authHeader);
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.error("Authorization头不存在或格式不正确");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        // 验证token
        try {
            String token = authHeader.substring(7);
            logger.debug("提取的token: {}", token);
            
            if (!JwtUtil.validateToken(token)) {
                logger.error("Token验证失败");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
            
            // 获取用户ID并与请求中的用户ID比较
            String tokenUserId = JwtUtil.extractUserId(token);
            logger.debug("Token中的userId: {}", tokenUserId);
            
            // 对于POST请求，从请求体中获取userId
            String requestUserId = null;
            if ("POST".equalsIgnoreCase(request.getMethod())) {
                // POST请求中userId在请求体中，无法直接获取，这里不做验证
                logger.debug("POST请求，不验证请求体中的userId");
            } else {
                // 其他请求从URL参数中获取
                requestUserId = request.getParameter("userId");
                logger.debug("请求中的userId: {}", requestUserId);
                
                if (requestUserId != null && !tokenUserId.equals(requestUserId)) {
                    logger.error("Token中的userId与请求中的userId不匹配");
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    return false;
                }
            }
            
            logger.debug("Token验证通过");
            return true;
        } catch (Exception e) {
            logger.error("Token验证异常", e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        // 不需要实现
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 不需要实现
    }
} 