package com.book;

import org.springframework.stereotype.Service;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.kotlin.CoroutineCrudRepository;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.queryParamOrNull
import org.springframework.data.r2dbc.core.flow;
import org.springframework.web.reactive.function.server.bodyAndAwait
import org.springframework.http.HttpStatus
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import kotlinx.coroutines.flow.Flow;

@Table("books")
data class Book(
   @Id  
   val id: Long,
   val name: String,
   val author: String,
)

@Repository
class BookRepository(private val template: R2dbcEntityTemplate) {
    suspend fun findAll(): Flow<Book> = 
       template.select(Book::class.java).flow()
}


@Service
class BookService(
  private val repository: BookRepository
) { 
   suspend fun getBooks(
      request: ServerRequest
   ): ServerResponse{
      println("-----------Getting books------------"); 
      return ServerResponse
        .status(HttpStatus.OK).bodyAndAwait(repository.findAll())
   } 
}







