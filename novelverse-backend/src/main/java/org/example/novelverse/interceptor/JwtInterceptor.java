package org.example.novelverse.interceptor;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.novelverse.utils.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String method = request.getMethod();
        String uri = request.getRequestURI();

        // 1. 放行 OPTIONS（否则前端直接挂）
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        // ===== 1. 放行 GET /books /chapters =====
        if ("GET".equalsIgnoreCase(method)) {
            System.out.println(uri);
            // 放行除了 /users /shelf 的 GET
            if (!uri.matches(".*(/users|/shelf)(/.*)?")) {
                return true; // 放行
            }
        }

        // 2. 读取 Authorization
        String auth = request.getHeader("Authorization");

        if (auth == null || !auth.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"msg\":\"未登录\"}");
            return false;
        }
        String token = auth.substring(7);
        try {
            Claims claims = JwtUtil.parseToken(token);

            // 3. 放入 request，供 Controller 使用
            request.setAttribute("userId", claims.get("userId"));
            request.setAttribute("username", claims.getSubject());

            return true;
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"msg\":\"token 无效或过期\"}");
            return false;
        }
    }
}

