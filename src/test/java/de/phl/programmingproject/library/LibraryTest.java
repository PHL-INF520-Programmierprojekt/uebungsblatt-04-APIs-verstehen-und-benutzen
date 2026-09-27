package de.phl.programmingproject.library;

import de.phl.programmingproject.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockedConstruction;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for the {@link LibraryDay} exercise.
 */
public class LibraryTest {

    List<Book> books = Arrays.asList(new Book("The Lord of the Rings", "J. R. R. Tolkien"),
            new Book("The Hobbit", "J. R. R. Tolkien"),
            new Book("Harry Potter and the Philosopher's Stone", "J. K. Rowling"));
    Library librarySpy;

    @BeforeEach
    public void initMocks() {
        librarySpy = Mockito.spy(new Library(books));
    }

    @Test
    public void task_1_library_with_three_books_and_two_visitors_was_created() {
        List<List<?>> calls = new ArrayList<>();
        try (MockedConstruction<Library> construction = TestUtils.observeConstruction(Library.class,
                (mock, context) -> calls.add(new ArrayList<>(context.arguments())))) {
            LibraryDay.main(new String[0]);
            assertEquals(1, calls.size(), "Erstellen Sie genau eine Bibliothek.");
            Collection<?> suppliedBooks = assertInstanceOf(Collection.class, calls.getFirst().getFirst());
            assertEquals(3, suppliedBooks.size(), "Übergeben Sie drei Bücher an den Konstruktor.");
            assertTrue(suppliedBooks.stream().allMatch(Book.class::isInstance), "Die Sammlung darf nur Bücher enthalten.");
        }
    }

    @Test
    public void task_1_visitors_paula_and_simon_registered() {
        try (MockedConstruction<Library> construction = TestUtils.observeConstruction(Library.class)) {
            LibraryDay.main(new String[0]);
            assertEquals(1, construction.constructed().size(), "Erstellen Sie genau eine Bibliothek.");
            verify(construction.constructed().getFirst()).registerVisitor("Paula");
            verify(construction.constructed().getFirst()).registerVisitor("Simon");
        }
    }

    @Test
    public void task_2_returnBook_with_empty_book_throws_IllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> librarySpy.returnBook("", 42),
                "The 'returnBook' method does not throw an 'IllegalArgumentException' if the title is empty.");

        assertThrows(IllegalArgumentException.class, () -> librarySpy.returnBook(null, 42),
                "The 'returnBook' method does not throw an 'IllegalArgumentException' if the title is null.");

    }

    @Test
    public void task_2_returnBook_with_book_not_borrowed_throws_IllegalArgumentException() {
        int visitorID = librarySpy.registerVisitor("Smeagol");

        assertThrows(IllegalArgumentException.class, () -> librarySpy.returnBook("Dragon Ball Z: Battle of the Gods", visitorID),
                "The 'returnBook' method does not throw an 'IllegalArgumentException' if the book was not borrowed or when the book is not part of the library.");
    }

    @Test
    public void task_2_returnBook_with_borrowed_book_succeeds() {
        int visitorID = librarySpy.registerVisitor("Smeagol");
        librarySpy.lendBook(books.get(0).getTitle(), visitorID);
        assertDoesNotThrow(() -> librarySpy.returnBook(books.get(0).getTitle(), visitorID),
                "The 'returnBook' method is not yet completely implemented.");
        assertEquals(0, librarySpy.getVisitor(visitorID).getLentBooks().size(), "The 'returnBook' method does not remove the book from the visitor's list of lent books.");
    }

    @Test
    public void task_3_searchAvailableBooks_with_empty_or_null_author_throws_IllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> librarySpy.searchAvailableBooks(""),
                "The 'searchBooks' method does not throw an 'IllegalArgumentException' if the searchTerm is empty.");

        assertThrows(IllegalArgumentException.class, () -> librarySpy.searchAvailableBooks(null),
                "The 'searchBooks' method does not throw an 'IllegalArgumentException' if the searchTerm is null.");
    }

    @Test
    public void task_3_searchAvailableBooks_with_existing_author_returns_book() {
        Set<Book> matches = librarySpy.searchAvailableBooks("Tolkien");
        assertTrue(null != matches && matches.size() == 2 && matches.contains(books.get(0)),
                "The 'searchAvailableBooks' method does not return the book with the search term for the given author.");
    }

    @Test
    public void task_3_searchAvailableBooks_with_existing_title_returns_book() {
        Set<Book> matches = librarySpy.searchAvailableBooks("the Rings");
        assertTrue(null != matches && matches.size() > 0 && matches.contains(books.get(0)),
                "The 'searchAvailableBooks' method does not return the book with the search term for the title.");
    }

    @Test
    public void task_4_library_markdown_file_exists_in_root_directory() {
        assertTrue(TestUtils.fileExistsInRootOrSrcDirectory("library.md"), "The file 'library.md' does not exist in the root (or './src') directory of the project.");
    }
}
