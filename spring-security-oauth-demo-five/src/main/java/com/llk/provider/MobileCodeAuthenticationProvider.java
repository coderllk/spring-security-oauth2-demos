package com.llk.provider;

import com.llk.controller.SmsController;
import com.llk.filter.MobileCodeAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Component
public class MobileCodeAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 进行身份认证的逻辑
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        //该 authenticationToken 就是我们在 MobileCodeAuthenticationFilter 中创建的那个
        MobileCodeAuthenticationToken authenticationToken = (MobileCodeAuthenticationToken)authentication;

        //获取用户输入的手机号码和验证码
        String mobile = (String) authenticationToken.getPrincipal();
        String code = (String) authenticationToken.getCredentials();

        //根据手机号码拿到用户信息
        //先根据手机号码查询该用户是否注册
        UserDetails user = userDetailsService.loadUserByUsername(mobile);
        if(user == null){
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }

        //验证手机号和验证码
        checkSmsCode(mobile,code);

        //注意这里调用的构造方法
        //代码执行到这里就说明用户输入的手机号码和验证码是正确的，
        // 就需要将 authenticated 设置为 true,否则后面的流程认为该用户还没有认证通过
        MobileCodeAuthenticationToken authenticationResult
                = new MobileCodeAuthenticationToken(user,null,user.getAuthorities());

        authenticationResult.setDetails(authenticationToken.getDetails());
        return authenticationResult;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        //该 provider 支持的认证方式
        return authentication.equals(MobileCodeAuthenticationToken.class);
    }

    //验证输入的手机号码与验证是否与 session 中存储的一致
    private void checkSmsCode(String mobile, String codeParameter) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        //从 session 中获取 SmsController 中保存的验证码信息
        Map<String, Object> code = (Map<String, Object>) request.getSession().getAttribute(SmsController.SMS_CODE);
        if(code == null) {
            throw new BadCredentialsException("未申请验证码");
        }

        String savedMobile = (String) code.get("mobile");
        int codeInt = (int) code.get("code");

        if(!savedMobile.equals(mobile)) {
            throw new BadCredentialsException("手机号码不一致");
        }
        if(codeInt != Integer.parseInt(codeParameter)) {
            throw new BadCredentialsException("验证码错误");
        }
    }
}

