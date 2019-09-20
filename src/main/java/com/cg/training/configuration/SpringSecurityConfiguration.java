//
//  package com.cg.training.configuration;
//  
//  import org.springframework.beans.factory.annotation.Autowired; import
//  org.springframework.context.annotation.Bean; import
//  org.springframework.context.annotation.Configuration; import
//  org.springframework.security.config.annotation.authentication.builders.
//  AuthenticationManagerBuilder; import
//  org.springframework.security.config.annotation.web.builders.HttpSecurity;
//  import org.springframework.security.config.annotation.web.configuration.
//  EnableWebSecurity; import
//  org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; import
//  org.springframework.web.servlet.config.annotation.CorsRegistry; import
//  org.springframework.web.servlet.config.annotation.WebMvcConfigurer; import
//  org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//  
//  import com.cg.training.services.AuthenticationEntryPoint;
//  
//  @Configuration
//  
//  @EnableWebSecurity public class SpringSecurityConfiguration {
//  
//  @Autowired private AuthenticationEntryPoint authEntryPoint;
//  
//  protected void configure(HttpSecurity http) throws Exception {
//  http.csrf().disable().authorizeRequests().anyRequest().authenticated().and().
//  httpBasic() .authenticationEntryPoint(authEntryPoint); }
//  
//  @Autowired public void configureGlobal(AuthenticationManagerBuilder auth)
//  throws Exception { auth.inMemoryAuthentication().withUser("saiyam").password(
//  "$2a$04$fHC52tYUD/3759MtCuc9aOuBY4//qgzH3VYXqjOCDXdG7SPIauqGS").roles("ADMIN"
//  ); }
//  
//  @Bean public BCryptPasswordEncoder encoder() { return new
//  BCryptPasswordEncoder(); }
//  
//  @Bean public WebMvcConfigurer corsConfigurer() { return new
//  WebMvcConfigurerAdapter() {
//  
//  @Override public void addCorsMappings(CorsRegistry registry) {
//  registry.addMapping("/**").allowedMethods("GET", "POST", "PUT",
//  "DELETE").allowedOrigins("*") .allowedHeaders("*"); } }; } }
// 