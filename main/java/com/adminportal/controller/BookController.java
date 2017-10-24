package com.adminportal.controller;

import com.adminportal.domain.Book;
import com.adminportal.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @RequestMapping(value="/add", method = RequestMethod.GET)
    public String addBook(Model model) {
        Book book = new Book();
        model.addAttribute("book", book);
        return "addBook";
    }

    @RequestMapping(value="/add", method = RequestMethod.POST)
    public String addBookPost(@ModelAttribute("book") Book book, HttpServletRequest request) {
        bookService.save(book); /* add book service for persistence (to save the book immediately) get to the id  generated by database for img*/

        MultipartFile bookImage = book.getBookImage();

        try {
            byte[] bytes = bookImage.getBytes(); /*convert image to bites*/
            String name = book.getId() + ".png"; /*define a book according to the id plus add .png*/
            BufferedOutputStream stream = new BufferedOutputStream( /*to write to the path*/
                    /*use amazon S3 bucket to store static files instead of the local system for production or add micro-services*/
                    new FileOutputStream(new File("src/main/resources/static/image/book/" +  name)));
            stream.write(bytes);
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:bookList";
    }

    @RequestMapping("/bookList")
    public String bookList(Model model) {
        List<Book> bookList = bookService.findAll();
        model.addAttribute("bookList", bookList); /*add attribute to our model to retrieve the list*/
        return "booklist";
    }
}
