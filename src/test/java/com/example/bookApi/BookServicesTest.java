package com.example.bookApi;
import com.example.bookApi.model.Book;
import com.example.bookApi.repository.BookRepository;
import com.example.bookApi.services.BookService;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    public BookServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Book book1 = new Book(1L, "Title1", "Author1", "Category1", "ISBN1");
        Book book2 = new Book(2L, "Title2", "Author2", "Category2", "ISBN2");

        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        List<Book> books = bookService.findAll();
        assertEquals(2, books.size());
        assertEquals("Title1", books.get(0).getTitle());
        assertEquals("Title2", books.get(1).getTitle());
    }

    @Test
    void testSave() {
        Book book = new Book(1L, "Title", "Author", "Category", "ISBN");

        when(bookRepository.save(book)).thenReturn(book);

        Book savedBook = bookService.save(book);
        assertEquals("Title", savedBook.getTitle());
    }
}