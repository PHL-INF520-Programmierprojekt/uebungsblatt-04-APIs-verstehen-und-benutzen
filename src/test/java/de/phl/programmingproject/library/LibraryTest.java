package de.phl.programmingproject.library;

import de.phl.programmingproject.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for the {@link LibraryDay} exercise.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({LibraryDay.class, LibraryTest.class})
public class LibraryTest {

    List<Book> books = Arrays.asList(new Book("The Lord of the Rings", "J. R. R. Tolkien"),
            new Book("The Hobbit", "J. R. R. Tolkien"),
            new Book("Harry Potter and the Philosopher's Stone", "J. K. Rowling"));
    Library librarySpy;

    @Before
    public void initMocks() {
        librarySpy = Mockito.spy(new Library(books));
    }

    @org.junit.Test
    public void task_1_library_with_three_books_and_two_visitors_was_created() throws Exception {

        PowerMockito.whenNew(Library.class).withAnyArguments().thenReturn(librarySpy);
        Mockito.doNothing().when(librarySpy).lendBook(anyString(), anyInt());
        Mockito.doNothing().when(librarySpy).returnBook(anyString(), anyInt());

        LibraryDay.main(null);
        try {
            PowerMockito.verifyNew(Library.class).withArguments(Mockito.any(Collection.class));
        } catch (AssertionError e) {
            fail("The 'Library' object is not correctly created in the 'main' method of the 'Library' file.");
        }

        assertEquals(3, librarySpy.getBookCount(), "The 'Library' object does not contain three books.");
    }

    @Test
    public void task_1_visitors_paula_and_simon_registered() throws Exception {
        PowerMockito.whenNew(Library.class).withAnyArguments().thenReturn(librarySpy);
        Mockito.doNothing().when(librarySpy).lendBook(anyString(), anyInt());
        Mockito.doNothing().when(librarySpy).returnBook(anyString(), anyInt());
        LibraryDay.main(null);
        try {
            verify(librarySpy).registerVisitor("Paula");
            verify(librarySpy).registerVisitor("Simon");
        } catch (AssertionError e) {
            fail("The visitors 'Paula' and/or 'Simon' where not correctly registered.");
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
