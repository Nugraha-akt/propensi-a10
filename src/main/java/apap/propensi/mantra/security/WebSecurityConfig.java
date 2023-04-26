package apap.propensi.mantra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/forgot-password**", "/reset-password**").permitAll()
                .antMatchers("/driver/viewall").hasAnyAuthority("ADMIN","MANAGER")
                .antMatchers("/driver/ringkasan").hasAnyAuthority("ADMIN","MANAGER")
                .antMatchers("/driver/detail/**").hasAnyAuthority("ADMIN","MANAGER")
                .antMatchers("/driver/update/**").hasAnyAuthority("ADMIN")
                .antMatchers("/driver/update-tugaskan/**").hasAnyAuthority("ADMIN")
                .antMatchers("/driver/update-sedang/**").hasAnyAuthority("ADMIN")
                .antMatchers("/driver/update-sudah/**").hasAnyAuthority("ADMIN")
                .antMatchers("/driver/nonaktifkan/**").hasAnyAuthority("ADMIN")
                .antMatchers("/driver/nonaktifkan-berhasil/**").hasAnyAuthority("ADMIN")
                .antMatchers("/driver/aktifkan/**").hasAnyAuthority("ADMIN")
                .antMatchers("/driver/aktifkan-berhasil/**").hasAnyAuthority("ADMIN")
                .antMatchers("/user/viewall").hasAnyAuthority("ADMIN","MANAGER")
                .antMatchers("/user/add").hasAnyAuthority("ADMIN")
                .antMatchers("/user/add/**").hasAnyAuthority("ADMIN")
                .antMatchers("/user/update/**").hasAnyAuthority("ADMIN")
                .antMatchers("/user/delete/**").hasAnyAuthority("ADMIN")
                .antMatchers("/user/summary").hasAnyAuthority("ADMIN","MANAGER")
                .antMatchers("/user/detail").hasAnyAuthority("ADMIN","MANAGER")
                .antMatchers("/unit/viewall").hasAnyAuthority("ADMIN","MANAGER")
                .antMatchers("/unit/view/**").hasAnyAuthority("ADMIN","MANAGER")
                .antMatchers("/unit/add").hasAnyAuthority("ADMIN")
                .antMatchers("/unit/update/**").hasAnyAuthority("ADMIN")
                .antMatchers("/unit/delete/**").hasAnyAuthority("ADMIN")
                .antMatchers("/surat/list").hasAnyAuthority("DRIVER")
                .antMatchers("/surat/upload-foto/**").hasAnyAuthority("DRIVER")
                .antMatchers("/surat/upload/**").hasAnyAuthority("DRIVER")
                .antMatchers("/surat/ringkasan").hasAnyAuthority("ADMIN","MANAGER")
                .antMatchers("/surat/dokumen/**").hasAnyAuthority("ADMIN","MANAGER")
                .antMatchers("/surat/verifikasi/**").hasAnyAuthority("ADMIN","MANAGER")
                .antMatchers("/surat/tolak/**").hasAnyAuthority("ADMIN","MANAGER")
                .antMatchers("/surat/pdf/**").hasAnyAuthority("ADMIN","DRIVER")
                .antMatchers("/surat/viewall").hasAnyAuthority("ADMIN")
                .antMatchers("/surat/view/**").hasAnyAuthority("ADMIN","DRIVER")
                .antMatchers("/request/viewall").hasAnyAuthority("ADMIN","MANAGER","DRIVER","CUSTOMER")
                .antMatchers("/request/detail").hasAnyAuthority("ADMIN","MANAGER","DRIVER","CUSTOMER")
                .antMatchers("/request/assign").hasAnyAuthority("ADMIN","MANAGER")
                .antMatchers("/request/confirm").hasAnyAuthority("DRIVER")
                .antMatchers("/request/update").hasAnyAuthority("DRIVER")
                .antMatchers("/request/finish").hasAnyAuthority("DRIVER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/",true)
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login").permitAll();
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }


}
