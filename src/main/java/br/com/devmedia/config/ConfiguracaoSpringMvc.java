package br.com.devmedia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;

@Configuration // indica ao Spring que essa classe é de configuração
public class ConfiguracaoSpringMvc extends WebMvcConfigurerAdapter {

	// Qual template engine é usado no projeto
    @Bean // geralmente usado em metodos de classe que recebeu anotação @configuration, que Spring deve gerenciar esse metodo
    // em resumo o Spring sabe que vai lidar com Thymeleaf nas paginas web
    public SpringTemplateEngine templateEngine(SpringResourceTemplateResolver resolver) {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(resolver);
        return templateEngine;
    }

    // registrando um controller automatico definido pelo proprio Spring para atender requisições, direcionadas as URL "/" e "/Home" sempre queu for chamadas essas requições a pagina home será exibida
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/home").setViewName("home");
    }
	
	//basta colocar localhost:8080 no navegador esse é um dos principais diferenciais do Spring Boot não facilitando somente o desenvolvimento da aplicação assim como execuçao da mesma
  
}
