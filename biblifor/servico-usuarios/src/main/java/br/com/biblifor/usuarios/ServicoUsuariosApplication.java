package br.com.biblifor.usuarios;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ServicoUsuariosApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServicoUsuariosApplication.class, args);
    }

    // Apache HttpClient suporta PATCH, ao contrario do HttpURLConnection padrao do Java
    @Bean
    public RestTemplate restTemplate() {
        HttpClient httpClient = HttpClients.createDefault();
        HttpComponentsClientHttpRequestFactory factory =
                new HttpComponentsClientHttpRequestFactory(httpClient);
        return new RestTemplate(factory);
    }
}
