package de.phl.programmingproject;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DiscussionTest {
    @Test
    public void task_1_discussion_markdown_file_exists_in_root_directory() {
        assertTrue(TestUtils.fileExistsInRootOrSrcDirectory("discussion.md"), "The file 'discussion.md' does not exist in the root (or './src') directory of the project.");
    }
}
