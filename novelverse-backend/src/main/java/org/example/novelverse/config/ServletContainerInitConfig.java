package org.example.novelverse.config;

import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletRegistration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ServletContainerInitConfig extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{SpringConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringMVCConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected void customizeRegistration(
            ServletRegistration.Dynamic registration) {
        MultipartConfigElement multipartConfig =
                new MultipartConfigElement(
                        null,
                        50 * 1024 * 1024,
                        50 * 1024 * 1024,
                        0
                );

        registration.setMultipartConfig(multipartConfig);
    }
}
