package task.library.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {


    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
        source.setDefaultEncoding("UTF-8");
        source.setBasename("classpath:messages");
        return source;
    }

    @Bean
    public CookieLocaleResolver localeResolver(){
        CookieLocaleResolver resolver = new CookieLocaleResolver();
        resolver.setDefaultLocale(new Locale("ru"));
        resolver.setCookieMaxAge(3600*24*30);
        resolver.setCookieName("language_of_site");
        return resolver;
    }

    @Bean
    public LocaleChangeInterceptor localeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        return interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(localeInterceptor());
    }

}