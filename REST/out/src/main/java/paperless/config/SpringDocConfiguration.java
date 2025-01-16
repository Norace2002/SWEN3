package paperless.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class SpringDocConfiguration {

    @Bean(name = "paperless.config.SpringDocConfiguration.apiInfo")
    OpenAPI apiInfo() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Document Manager System Server")
                                .description("Lorem ipsum dolor sit amet")
                                .contact(
                                        new Contact()
                                                .email("if22b010@technikum-wien.at")
                                )
                                .version("1.0.11")
                )
        ;
    }
}