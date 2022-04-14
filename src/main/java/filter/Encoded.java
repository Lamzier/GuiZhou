package main.java.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * 编码过滤器
 */
public class Encoded implements Filter {
    String encoding = null;//编码类型

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter("encoding");//获取编码参数
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (encoding == null){
            filterChain.doFilter(servletRequest , servletResponse);//传递
            return;
        }
        servletRequest.setCharacterEncoding(encoding);//设置接受编码
        servletResponse.setContentType("text/html; charset=" + encoding);//设置返回协议编码
        filterChain.doFilter(servletRequest,servletResponse);//传递
    }

    @Override
    public void destroy() {
        encoding = null;//清理
    }
}
