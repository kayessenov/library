package task.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import task.library.entities.Booking;
import task.library.entities.Books;
import task.library.entities.Genre;
import task.library.entities.Users;
import task.library.repositories.BookRepository;
import task.library.repositories.BookingRepository;
import task.library.repositories.GenreRepository;
import task.library.repositories.UserRepository;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GenreRepository genreRepository;

    private Users getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            return (Users) authentication.getPrincipal();
        }
        return null;
    }

    @GetMapping("/")
    public String indexPage(Model model){
        model.addAttribute("currentUser", getUser());
        List<Books> booksList = bookRepository.findAll();
        model.addAttribute("books",booksList);
        List<Genre> genreList = genreRepository.findAll();
        model.addAttribute("genres", genreList);
        return "index";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/booking")
    public String bookingForm(@RequestParam(name = "amount")int amount,
                              @RequestParam(name = "book_id")Long id){
        Booking booking = new Booking();
        Books books = bookRepository.getById(id);
        Users users = getUser();
        booking.setUsers(users);
        booking.setBooks(books);
        if(amount>0) {
            booking.setAmount(amount);
            bookingRepository.save(booking);
        }
        return "redirect:?";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/bookin")
    public String bookingPage(Model model){
        model.addAttribute("currentUser", getUser());
        List<Books> booksList = bookRepository.findAll();
        model.addAttribute("books",booksList);
        List<Genre> genreList = genreRepository.findAll();
        model.addAttribute("genres", genreList);
        List<Booking> bookings = bookingRepository.findAll();
        model.addAttribute("booking",bookings);
        return "bookin";
    }

    @PostMapping("/bookingdel")
    public String bookingdelform(@RequestParam(name = "id")Long id){
        Booking booking = bookingRepository.getById(id);
        bookingRepository.delete(booking);
        return "redirect:bookin";
    }

}
