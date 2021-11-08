// Original Copyright 2002-2017 the original author or authors. Licensed under the Apache 2.0 License.
// Modifications Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved. 

package obsplat.ui.petclinic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

@SpringBootApplication
public class PetClinicUiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetClinicUiApplication.class, args);
    }

    @Value("classpath:/static/index.html")
    private Resource indexHtml;

    /**
     * workaround solution for forwarding to index.html
     * @see <a href="https://github.com/spring-projects/spring-boot/issues/9785">#9785</a>
     */
    @Bean
    RouterFunction<?> routerFunction() {
        RouterFunction<ServerResponse> router = RouterFunctions.resources("/**", new ClassPathResource("static/"))
                .andRoute(RequestPredicates.GET("/"),
                        request -> ServerResponse.ok().contentType(MediaType.TEXT_HTML).body(indexHtml));
        return router;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
