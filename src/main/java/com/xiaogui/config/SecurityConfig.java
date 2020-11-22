package com.xiaogui.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//配置类，spring boot一般把重要的类或特殊类的才写到配置文件中，其他都是以配置类实现
@Configuration //配置类的入口
@EnableWebSecurity //被SpringSecurity识别
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //认证用户的来源【内存或数据库】
   public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password("{noop}123456")
                .roles("USER"); //配置类中没有ROLE，不然报错
    }

    //配置springSecurity相关的信息
    public void configure(HttpSecurity http) throws Exception {
        //释放静态资源，指定资源拦截规则，指定自定义认证页面，指定退出认证配置，csrf配置
        http.authorizeRequests()//作为开始，都必须以这个为开始
                //释放这些资源，拦截器不要拦截他们
            .antMatchers("/login.jsp","/failer.jsp","/css/**","/img/**","/plugins/**").permitAll()
            .antMatchers("/**").hasAnyRole("USER","ADMIN")//所有页面需要用USER/ADMIN角色认证
            .anyRequest()
            .authenticated()//必须认证通过才能访问资源，否则不能访问
            .and()//新的配置开始
            //自定义认证配置
            .formLogin()
            .loginPage("/login.jsp")//自己设置的认证页面
            .loginProcessingUrl("/login")//认证地址，SpringSecurity自己设置的，直接拿过来用就好
            .successForwardUrl("index.jsp")//认证成功后，跳转的页面
            .failureForwardUrl("/failer.jsp")//认证失败后，跳转的页面
            .permitAll()//释放前面的资源，不然一直卡在原处
            .and()//退出配置
            .logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login.jsp")//退出成功后，跳到login.jsp页面
            .invalidateHttpSession(true)//清空会话
            .permitAll()
            .and()
            //配置csrf，系统随机生成Token,为了测试方便，把csrf关掉，不让csrf生效
            .csrf()
            .disable();

    }
}
