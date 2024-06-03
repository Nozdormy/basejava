package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ListStorageTest {
    private Storage list;

    public ListStorageTest() {
        this.list = new ListStorage();
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
        list.clear();
        list.save(RESUME_1);
        list.save(RESUME_2);
        list.save(RESUME_3);
    }


    @Test
    void clear() {
        list.clear();
        assertSize(0);
    }

    @Test
    void update() {
        list.update(RESUME_1);
        assertSize(3);
        Assertions.assertSame(RESUME_1, list.get(UUID_1));
    }

    @Test
    void updateNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            list.update(new Resume());
        });
    }

    @Test
    void delete() {
        list.delete(UUID_1);
        assertSize(2);
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            list.get(UUID_1);
        });
    }

    @Test
    void deleteNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            list.delete(UUID_NOT_EXIST);
        });
    }

    @Test
    void save() {
        list.save(RESUME_4);
        assertSize(4);
        assertGet(RESUME_4);
    }

    @Test
    void saveExist() {
        Assertions.assertThrows(ExistStorageException.class, () -> {
            list.save(RESUME_1);
        });
    }

    @Test
    void getAll() {
        Resume[] expected = new Resume[]{RESUME_1, RESUME_2, RESUME_3};
        Assertions.assertArrayEquals(expected, list.getAll());
    }

    @Test
    void size() {
        assertSize(3);
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
            list.get(UUID_NOT_EXIST);
        });
    }

    private void assertSize(int size) {
        Assertions.assertEquals(size, list.size());
    }

    private void assertGet(Resume resume) {
        Assertions.assertEquals(resume, list.get(resume.getUuid()));
    }
}