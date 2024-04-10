package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@SpringBootApplication
public class DemoApplication {

    @Autowired
    private ThymeleafProperties properties;

    @Value("${spring.thymeleaf.templates_root:}")
    private String templatesRoot;

    @Value("classpath:templates/*.html")
    private Resource[] siteResources;

    @Value("classpath:templates/fragments/*")
    private Resource[] fragmentResources;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            for (final Resource resourceFile : siteResources) {
                Files.createDirectories(Paths.get("./html"));
                Files.copy(resourceFile.getInputStream(), Paths.get("./html/" + resourceFile.getFilename()), StandardCopyOption.REPLACE_EXISTING);
            }
            for (final Resource resourceFile : fragmentResources) {
                Files.createDirectories(Paths.get("./html/fragments"));
                Files.copy(resourceFile.getInputStream(), Paths.get("./html/fragments/" + resourceFile.getFilename()), StandardCopyOption.REPLACE_EXISTING);
            }
        };
    }

    @Bean
    public ITemplateResolver defaultTemplateResolver() {
        FileTemplateResolver resolver = new FileTemplateResolver();
        resolver.setSuffix(properties.getSuffix());
        resolver.setPrefix(templatesRoot);
        resolver.setTemplateMode(properties.getMode());
        resolver.setCacheable(properties.isCache());
        return resolver;
    }
}
