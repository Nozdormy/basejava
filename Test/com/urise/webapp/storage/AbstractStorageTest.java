package com.urise.webapp.storage;

import com.urise.webapp.Config;
import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = Config.get().getStorageDir();

    protected final Storage STORAGE;

    protected AbstractStorageTest(Storage storage) {
        this.STORAGE = storage;
    }

    private static final String UUID_1 = UUID.randomUUID().toString();
    private static final String UUID_2 = UUID.randomUUID().toString();
    private static final String UUID_3 = UUID.randomUUID().toString();
    private static final String UUID_4 = UUID.randomUUID().toString();

    private static final Resume RESUME_1 = new Resume(UUID_1, "Name1");
    private static final Resume RESUME_2 = new Resume(UUID_2, "Name2");
    private static final Resume RESUME_3 = new Resume(UUID_3, "Name3");
    private static final Resume RESUME_4 = new Resume(UUID_4, "Name4");

    @BeforeEach
    void setUp() {
        STORAGE.clear();
        STORAGE.save(RESUME_1);
        STORAGE.save(RESUME_2);
        STORAGE.save(RESUME_3);
    }

    @Test
    void clear() {
        STORAGE.clear();
        assertSize(0);
    }

    @Test
    void save() {
        STORAGE.save(RESUME_4);
        assertSize(4);
        Assertions.assertEquals(RESUME_4, STORAGE.get(UUID_4));
    }

    @Test
    void saveExist() {
        assertThrows(ExistStorageException.class, () -> STORAGE.save(RESUME_1));
    }

    @Test
    void get() {
        STORAGE.save(RESUME_4);
        Assertions.assertEquals(RESUME_4, STORAGE.get(UUID_4));
        Assertions.assertEquals(RESUME_1, STORAGE.get(RESUME_1.getUuid()));
    }

    @Test
    public void getNotExist() {
        assertThrows(NotExistStorageException.class, () -> STORAGE.get("dummy"));
    }

    @Test
    void update() {
        STORAGE.update(RESUME_1);
        assertSize(3);
        assertTrue(RESUME_1.equals(STORAGE.get(UUID_1)));
    }

    @Test
    void updateNotExist() {
        assertThrows(NotExistStorageException.class, () -> STORAGE.update(RESUME_4));
    }

    @Test
    void delete() {
        STORAGE.delete(UUID_1);
        assertSize(2);
        assertThrows(NotExistStorageException.class, () -> STORAGE.get(UUID_1));
    }

    @Test
    void deleteNotExist() {
        assertThrows(NotExistStorageException.class, () -> STORAGE.delete(UUID_4));
    }

    @Test
    void getAllSorted() {
        List<Resume> list = STORAGE.getAllSorted();
        assertEquals(3, list.size());
        List<Resume> sortedResumes = Arrays.asList(RESUME_1, RESUME_2, RESUME_3);
        Collections.sort(sortedResumes);
        assertEquals(sortedResumes, list);
    }

    @Test
    void size() {
        assertSize(3);
    }

    private void assertSize(int size) {
        Assertions.assertEquals(size, STORAGE.size());
    }
}