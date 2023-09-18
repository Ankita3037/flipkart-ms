package com.example.flipkartms;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class books {
    @GetMapping("/books")
 public String getData(){
     return "50% discount on all types of books";
 }

}

