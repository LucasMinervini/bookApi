package com.example.bookApi;

import com.example.bookApi.model.Book;
import com.example.bookApi.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRepository bookRepository;

    private Book book1;
    private Book book2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        book1 = new Book(1L, "Title1", "Author1", "Category1", "ISBN1");
        book2 = new Book(2L, "Title2", "Author2", "Category2", "ISBN2");
    }

    @Test
    void getAllBooksTest() throws Exception {
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        mockMvc.perform(get("/api/books")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Title1"))
                .andExpect(jsonPath("$[1].title").value("Title2"));
    }

    @Test
    void getBookByIdTest() throws Exception {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book1));

        mockMvc.perform(get("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Title1"));
    }

    @Test
    void createBookTest() throws Exception {
        when(bookRepository.save(any(Book.class))).thenReturn(book1);

        String bookJson = "{\"title\": \"Title1\", \"author\": \"Author1\", \"category\": \"Category1\", \"isbn\": \"ISBN1\"}";

        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Title1"));
    }

    @Test
    void updateBookTest() throws Exception {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book1));
        when(bookRepository.save(any(Book.class))).thenReturn(book1);

        String bookJson = "{\"title\": \"UpdatedTitle\", \"author\": \"UpdatedAuthor\", \"category\": \"UpdatedCategory\", \"isbn\": \"UpdatedISBN\"}";

        mockMvc.perform(put("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("UpdatedTitle"));
    }

    @Test
    void deleteBookTest() throws Exception {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book1));

        mockMvc.perform(delete("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
