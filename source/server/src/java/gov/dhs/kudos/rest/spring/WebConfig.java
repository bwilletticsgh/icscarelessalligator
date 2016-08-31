package gov.dhs.kudos.rest.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import gov.dhs.kudos.rest.v1.notification.EmailNotification;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * General purpose Spring MVC configuration
 * @author bsuneson
 */
@Configuration
@PropertySource("classpath:/gov/dhs/kudos/rest/spring/kudos.properties")
public class WebConfig extends WebMvcConfigurerAdapter
{
    @Autowired
    private Environment env;
    
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) 
    {
        converters.add(jackson2Converter());
    }

    @Bean
    public MappingJackson2HttpMessageConverter jackson2Converter() 
    {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper());
        return converter;
    }

    @Bean
    public ObjectMapper objectMapper() 
    {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        return objectMapper;
    }
    
    @Bean
    public EmailNotification emailNotification()
    {
        return new EmailNotification(env.getProperty("kudos.admin.email"), 
                                     env.getProperty("smtp.host"), 
                                     env.getProperty("smtp.port"), 
                                     env.getProperty("smtp.user"), 
                                     env.getProperty("smtp.auth"));
    }
    
     @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) 
    {
            registry.addResourceHandler("swagger-ui.html")
                    .addResourceLocations("classpath:/META-INF/resources/");

            registry.addResourceHandler("/webjars/**")
                    .addResourceLocations("classpath:/META-INF/resources/webjars/");

    }
}
