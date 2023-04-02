package com.urise.webapp.storage;

import com.urise.webapp.exception.*;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.*;

class AbstractArrayStorageTest {
    private final Storage STORAGE;

    public AbstractArrayStorageTest() {
        this.STORAGE = new ArrayStorage();
    }

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String UUID_NOT_EXIST = "dummy";

    private static final Resume RESUME_1 = new Resume(UUID_1);
    private static final Resume RESUME_2 = new Resume(UUID_2);
    private static final Resume RESUME_3 = new Resume(UUID_3);
    private static final Resume RESUME_4 = new Resume(UUID_4);

    @BeforeEach
    public void setUp() {
        STORAGE.clear();
        STORAGE.save(RESUME_1);
        STORAGE.save(RESUME_2);
        STORAGE.save(RESUME_3);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    @Test
    void clear() {
        STORAGE.clear();
        assertSize(0);
        Assertions.assertArrayEquals(new Resume[0], STORAGE.getAll());
    }

    @Test
    void update() {
        STORAGE.update(RESUME_1);
        assertSize(3);
        Assertions.assertSame(RESUME_1, STORAGE.get(UUID_1));
    }

    @Test
    void updateNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            STORAGE.update(new Resume());
        });
    }

    @Test
    void delete() {
        STORAGE.delete(UUID_1);
        assertSize(2);
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            STORAGE.get(UUID_1);
        });
    }

    @Test
    void deleteNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            STORAGE.delete(UUID_NOT_EXIST);
        });
    }

    @Test
    void save() {
        STORAGE.save(RESUME_4);
        assertSize(4);
        assertGet(RESUME_4);
    }

    @Test
    void saveExist() {
        Assertions.assertThrows(ExistStorageException.class, () -> {
            STORAGE.save(RESUME_1);
        });
    }

    @Test
    void saveOverflow() {
        STORAGE.clear();
        try {
            for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                STORAGE.save(new Resume());
            }
        } catch (StorageException e) {
            System.out.println(e.getMessage());
        }

        Assertions.assertThrows(StorageException.class, () -> {
            STORAGE.save(new Resume());
        });
    }

    @Test
    void getAll() {
        Resume[] actual = new Resume[]{RESUME_1, RESUME_2, RESUME_3};
        Assertions.assertArrayEquals(actual, STORAGE.getAll());
    }

    @Test
    void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test
    public void getNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            STORAGE.get(UUID_NOT_EXIST);
        });
    }

    private void assertSize(int size) {
        Assertions.assertEquals(size, STORAGE.size());
    }

    private void assertGet(Resume resume) {
        Assertions.assertEquals(resume, STORAGE.get(resume.getUuid()));
    }
}