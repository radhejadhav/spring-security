package com.security;

import com.security.events.EmailUpdateEvent;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.event.EventListener;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@SpringBootApplication
//@EnableConfigurationProperties
//@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true)
//@PropertySources({
//		@PropertySource(value = "classpath:application.properties"),
//		@PropertySource(value = "classpath:application-dev.properties")
//})
@Slf4j
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SecurityApplication {

	@Autowired
	private WebClient webClient;

	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}


	@EventListener(EmailUpdateEvent.class)
	public void emailEventListener(EmailUpdateEvent event){

		log.info("Email update event: {}",event.getEmail());
	}
//	@EventListener(ApplicationStartedEvent.class)
//	public void runAsync(){
//		CompletableFuture<List<ResponseData>> res = this.webClient.get().uri("https://dummyjson.com/products/categories")
//				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
//				.accept(MediaType.APPLICATION_JSON)
//				.acceptCharset(StandardCharsets.UTF_8)
//				.retrieve()
//				.bodyToMono(new ParameterizedTypeReference<List<ResponseData>>(){})
//				.toFuture();
//
//		res.thenAccept(r->r.forEach(d->log.info(d.toString())));
//	}

}

@Data
@ToString
class ResponseData {
	private String slug;
	private String name;
	private String url;
}
