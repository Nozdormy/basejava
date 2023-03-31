package com.urise.webapp.storage;

import com.urise.webapp.exception.*;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.*;

class AbstractArrayStorageTest {
    private Storage storage;

    public AbstractArrayStorageTest() {
        this.storage = new ArrayStorage();
    }

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void size() {
        Assertions.assertEquals(3, storage.size());
    }

    @Test
    void clear() {
        storage.clear();
        Assertions.assertEquals(0, storage.size());
    }

    @Test
    void update() {
        Resume resume = new Resume("uuid0");
        storage.save(resume);
        storage.update(resume);
        Assertions.assertEquals(resume, storage.get("uuid0"));
        Assertions.assertEquals(4, storage.size());
    }

    @Test
    void updateNotExistStorageException() {
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.update(new Resume());
        });
    }

    @Test
    void delete() {
        storage.delete("uuid1");
        Assertions.assertEquals(2, storage.size());
    }

    @Test
    void deleteNotExistStorageException() {
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.delete("uuid10");
            Assertions.assertEquals(2, storage.size());
        });
    }

    @Test
    void save() {
        String UUID = "uuid0";
        storage.save(new Resume(UUID));
        Assertions.assertEquals(4, storage.size());
    }

    @Test
    void saveExistStorageException() {
        String UUID = "uuid2";
        Assertions.assertThrows(ExistStorageException.class, () -> {
            storage.save(new Resume(UUID));
            Assertions.assertEquals(4, storage.size());
        });
    }

    @Test
    void saveStorageException() {
        for (int i = 3; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
            storage.save(new Resume());
        }
        Assertions.assertThrows(StorageException.class, () -> {
            storage.save(new Resume());
            Assertions.assertEquals(AbstractArrayStorage.STORAGE_LIMIT, storage.size());
        });
    }

    @Test
    void getAll() {
        Assertions.assertEquals(storage.size(), storage.getAll().length);
    }

    @Test
    void get() {
        Assertions.assertEquals(UUID_1, storage.get(UUID_1).toString());
    }

    @Test
    public void getNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.get("dummy");
        });
    }
}