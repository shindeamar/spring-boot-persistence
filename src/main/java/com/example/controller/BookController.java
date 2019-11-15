package com.example.controller;

import com.example.core.Book;
import com.example.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Api(value = "Books Controller", tags = "book")
@RestController
@Slf4j
public class BookController {
    @Autowired
    private BookService bookService;

    @ExceptionHandler({IllegalArgumentException.class})
    void handleBadRequest(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(DataAccessResourceFailureException.class)
    void handleDBError(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_GATEWAY.value(), "Database connection failed");
    }

    @ExceptionHandler(IllegalAccessException.class)
    void handleAuthError(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.UNAUTHORIZED.value(), "Authorization failed");
    }

    @RequestMapping(value = "/book",
            method = RequestMethod.GET)
    @ApiOperation(value = "Get all books",
            response = Book.class, responseContainer = "list",
            httpMethod = "GET",
            notes = "Get all the books")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Processed Successfully"),
            @ApiResponse(code = 400, message = "Bad Request")
    })
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<Book> getAllBooks() throws IllegalAccessException {
        log.info("Begin getAllBooks");
        try{
            return bookService.findAll();
        } catch (IllegalArgumentException ie){
            log.error("ERROR: " + ie.toString());
            throw ie;
        } catch (Exception e){
            log.error("ERROR: " + e.toString());
            throw new RuntimeException(e.toString());
        }
    }
}
