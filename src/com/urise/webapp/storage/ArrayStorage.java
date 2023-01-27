package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private static final int STORAGE_LIMIT = 10_000;
    Resume[] storage = new Resume[STORAGE_LIMIT];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        int index = findIndex(resume.uuid);
        if(index != -1) {
            storage[index] = resume;
            System.out.println(storage[index]);
        } else {
            System.out.println("Ошибка");
        }
    }

    public void save(Resume r) {
        if (size >= STORAGE_LIMIT) {
            System.out.println("Хранилище переполнено");
        } else if (findIndex(r.uuid) != -1) {
            System.out.println("Резюме уже есть в хранилище");
        } else {
            storage[size] = r;
            size++;
        }
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if(index != -1) {
            return storage[index];
        } else {
            System.out.println("Запись не найдена");
            return null;
        }
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index != -1) {
            storage[index] = storage[size - 1];
            size--;
            return;
        }
        System.out.println("Не удалено");
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    private int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}