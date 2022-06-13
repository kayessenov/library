package task.library.controllers;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import task.library.entities.Books;
import task.library.entities.Genre;
import task.library.entities.Users;
import task.library.repositories.BookRepository;
import task.library.repositories.GenreRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Controller
@PreAuthorize("hasAnyRole('ROLE_MODERATOR')")
@RequestMapping("/admin")
public class RoleController {

    @Value("${basebookIMG}")
    private String basebookIMG;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private GenreRepository genreRepository;

    private Users getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            return (Users) authentication.getPrincipal();
        }
        return null;
    }

    @GetMapping()
    public String AdminPage(Model model){
        model.addAttribute("currentUser", getUser());
        return "/admin/admin";
    }

    @GetMapping("/addgenre")
    public String genrePage(Model model){
        model.addAttribute("currentUser", getUser());
        return "/admin/genre";
    }

    @GetMapping("/editbook")
    public String editBook(Model model){
        model.addAttribute("currentUser", getUser());
        return "/admin/editbook";
    }

    @PostMapping("/addgenreform")
    public String addGenreForm(@RequestParam(name = "name")String name,
                               @RequestParam(name = "code_name")String code){
        Genre genre = new Genre();
        genre.setName(name);
        genre.setCode(code);
        genreRepository.save(genre);
        return "redirect:/admin/addgenre";
    }

    @GetMapping("/viewgenre")
    public String viewGerePage(Model model){
        List<Genre> genreList = genreRepository.findAll();
        model.addAttribute("genre", genreList);
        model.addAttribute("currentUser", getUser());
        return "/admin/viewgenre";
    }

    @PostMapping("/genredelete")
    public String genredeleteForm(@RequestParam(name = "id")Long id){
        Genre onegenre = genreRepository.getById(id);
        genreRepository.delete(onegenre);
        return "redirect:/admin/viewgenre";
    }

    @GetMapping("/addbook")
    public String addBookPage(Model model){
        model.addAttribute("currentUser", getUser());
        List<Genre> genreList = genreRepository.findAll();
        model.addAttribute("code",genreList);
        return "/admin/addbook";
    }

    @PostMapping("/addbookform")
    public String addbookform(@RequestParam(name = "name")String name,
                              @RequestParam(name = "description")String description,
                              @RequestParam(name = "price")int price,
                              @RequestParam(name = "img")MultipartFile file,
                              @RequestParam(name = "code_id")Long id){
        Genre genre = genreRepository.getById(id);
        Books book = new Books();
        book.setName(name);
        book.setDescription(description);
        book.setPrice(price);
        book.setGenre(genre);
        if(Objects.equals(file.getContentType(), "image/jpeg") || Objects.equals(file.getContentType(), "image/png")){
            try{

                String fileName = DigestUtils.sha1Hex(name+"_ava");

                byte[] bytes = file.getBytes();
                Path path = Paths.get(basebookIMG+fileName+".jpg");

                Files.write(path, bytes);

                book.setImage(fileName);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        bookRepository.save(book);
        return "redirect:/admin/addbook";
    }

    @GetMapping("/viewbook")
    public String viewBookPage(Model model){
        model.addAttribute("currentUser", getUser());
        List<Books> booksList = bookRepository.findAll();
        model.addAttribute("bookslist",booksList);
        List<Genre> genreList = genreRepository.findAll();
        model.addAttribute("code",genreList);
        return "/admin/viewbook";
    }

    @PostMapping("/deletebook")
    public String deleteBookForm(@RequestParam(name = "id")Long id){
        Books books = bookRepository.getById(id);
        bookRepository.delete(books);
        return "redirect:/admin/viewbook";
    }

    @GetMapping("/viewbook/editbook/{bookId}")
    public String depDetailsPage(Model model,
                                 @PathVariable(name = "bookId") Long id) {
        model.addAttribute("currentUser", getUser());
        Books books = bookRepository.getById(id);
        model.addAttribute("book", books);
        return "/admin/editbook";
    }

    @PostMapping("/savebook")
    public String saveBookForm(@RequestParam(name = "name")String name,
                               @RequestParam(name = "price")int price,
                               @RequestParam(name = "description")String description,
                               @RequestParam(name = "book_img")MultipartFile file,
                               @RequestParam(name = "id")Long id){
        Books books = bookRepository.getById(id);
        books.setPrice(price);
        books.setDescription(description);
        books.setName(name);
        if(Objects.equals(file.getContentType(), "image/jpeg") || Objects.equals(file.getContentType(), "image/png")){
            try{

                String fileName = DigestUtils.sha1Hex(name+"_ava");

                byte[] bytes = file.getBytes();
                Path path = Paths.get(basebookIMG+fileName+".jpg");

                Files.write(path, bytes);

                books.setImage(fileName);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        bookRepository.save(books);
        return "redirect:/admin/viewbook/editbook/"+id;
    }

}
