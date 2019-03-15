package com.task.tracking.service.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;


@Configuration
@EnableWebMvc
@ComponentScan("com.task.tracking.service") // com.javasampleapproach.springsecurity.jdbcauthentication
public abstract class WebConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * The ViewResolver interface maps the view names returned by a controller
     * to actual view objects.
     * ThymeleafViewResolver implements the ViewResolver interface
     * and is used to determine which Thymeleaf views to render, given a view name.
     *
     * @return viewResolver
     */
    @Bean
    public ViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();

        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setCharacterEncoding("Windows-1251"); // UTF-8
        viewResolver.setOrder(1);
        return viewResolver;
    }

    /**
     * The SpringTemplateEngine class performs all of the configuration steps.
     * This class can be configured as a bean in the configuration file
     */
    @Bean
    @Description("Thymeleaf Template Engine")
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addDialect(new Java8TimeDialect());

        templateEngine.setTemplateResolver(templateResolver());

        return templateEngine;
    }

    /**
     * The templateResolver bean prefix and suffix indicate the location of:
     * the view pages within the webapp directory - "/WEB-INF/views/"
     * and their filename extension - ".html"
     *
     * @return templateResolver
     */
    @Bean
    @Description("Thymeleaf Template Resolver")
    public ClassLoaderTemplateResolver templateResolver() {

        ClassLoaderTemplateResolver templateResolver =
                new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setCacheable(false);
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setCharacterEncoding("UTF-8");

        return templateResolver;
    }

}
