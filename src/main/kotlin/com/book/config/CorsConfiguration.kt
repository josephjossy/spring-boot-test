package com.book.config;

import org.springframework.web.reactive.config.*;
import org.springframework.context.annotation.Configuration


@Configuration
@EnableWebFlux
open class CorsConfiguration: WebFluxConfigurer {

   override fun addCorsMappings(corsRegistry: CorsRegistry) {
        corsRegistry.addMapping("/**")
          .allowedOrigins(
	    "*", 
          ) 
	  .allowedMethods("POST", "GET", "PUT")
          .maxAge(3600);
    }
}

