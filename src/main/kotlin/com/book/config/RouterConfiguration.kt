package com.book.config

import com.book.BookService;
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions.resources
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class RouterConfiguration {
   
    @Bean
    fun routes(
      bookService: BookService, 
    ): RouterFunction<ServerResponse> = coRouter {
        
       accept(MediaType.APPLICATION_JSON).nest{ 
          "/books".nest{
	      GET("",  bookService::getBooks)
           } 
       }
    }   
}
