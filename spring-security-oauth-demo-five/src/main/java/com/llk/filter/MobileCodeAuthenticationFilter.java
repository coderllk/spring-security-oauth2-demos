package com.llk.filter;

import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MobileCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    //request parameter 参数名称
    public static final String SPRING_SECURITY_FORM_MOBILE_KEY = "mobile";
    public static final String SPRING_SECURITY_FORM_CODE_KEY = "code";

    private String mobileParameter = SPRING_SECURITY_FORM_MOBILE_KEY;
    private String codeParameter = SPRING_SECURITY_FORM_CODE_KEY;

    //我们的手机验证码登录 默认也只支持 POST 请求
    private boolean postOnly = true;

    public MobileCodeAuthenticationFilter() {
        //默认的登录请求处理地址
        super(new AntPathRequestMatcher("/mobile/login", "POST"));
    }

    //完成验证功能的方法
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        String mobile = obtainMobile(request);
        String code = obtainCode(request);

        if (mobile == null) {
            mobile = "";
        }

        if (code == null) {
            code = "";
        }

        mobile = mobile.trim();

        MobileCodeAuthenticationToken authRequest = new MobileCodeAuthenticationToken(
                mobile, code);

        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }


    //获取用户输入的验证码
    @Nullable
    protected String obtainCode(HttpServletRequest request) {
        return request.getParameter(codeParameter);
    }

    //获取用户输入的手机号码
    @Nullable
    protected String obtainMobile(HttpServletRequest request) {
        return request.getParameter(mobileParameter);
    }

    //设置 IP地址、sessionID 等信息到 MobileCodeAuthenticationToken 对象中
    protected void setDetails(HttpServletRequest request,
                              MobileCodeAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    //这几个方法基本用不上
    /*public void setMobileParameter(String mobileParameter) {
        this.mobileParameter = mobileParameter;
    }


    public void setCodeParameter(String codeParameter) {
        this.codeParameter = codeParameter;
    }


    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getMobileParameter() {
        return mobileParameter;
    }

    public final String getCodeParameter() {
        return codeParameter;
    }*/
}
