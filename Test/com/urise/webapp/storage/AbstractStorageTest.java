package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AbstractStorageTest {

    protected final Storage STORAGE;

    protected AbstractStorageTest(Storage storage) {
        this.STORAGE = storage;
    }

    private static final String UUID_1 = "uuid_1";
    private static final String UUID_2 = "uuid_2";
    private static final String UUID_3 = "uuid_3";
    private static final String UUID_4 = "uuid_4";

    private static final Resume RESUME_1 = new Resume(UUID_1, "Ivan");
    private static final Resume RESUME_2 = new Resume(UUID_2, "Vov");
    private static final Resume RESUME_3 = new Resume(UUID_3, "Sew");
    private static final Resume RESUME_4 = new Resume(UUID_4, "rew");

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
        assertIterableEquals(new ArrayList<>(), STORAGE.getAllSorted());
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
        assertSame(RESUME_1, STORAGE.get(UUID_1));
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
    void getAll() {
        ArrayList<Resume> expectedList = new ArrayList<>();
        expectedList.add(RESUME_1);
        expectedList.add(RESUME_2);
        expectedList.add(RESUME_3);
        assertIterableEquals(expectedList, STORAGE.getAllSorted());
        assertSize(3);
    }

    @Test
    void size() {
        assertSize(3);
    }

    private void assertSize(int size) {
        Assertions.assertEquals(size, STORAGE.size());
    }
}