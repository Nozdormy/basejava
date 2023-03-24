package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume r) {
        int index = findIndex(r.getUuid());
        if (index != -1) {
            storage[index] = r;
            System.out.println(storage[index]);
        }
        System.out.println("Ошибка");
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index > 0) {
            fillDeletedElement(index);
            storage[size - 1] = null;
            size--;
            return;
        }
        System.out.println("Не удалено");
    }

    public void save(Resume r) {
        int index = findIndex(r.getUuid());
        if (size >= STORAGE_LIMIT) {
            System.out.println("Хранилище переполнено");
        } else if (index > 0) {
            System.out.println("Резюме " + r.getUuid() + " уже есть в хранилище");
        } else {
            insertElement(r, index);
            size++;
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            System.out.println("Резюме " + uuid + " не найдено");
            return null;
        }
        return storage[index];
    }

    protected abstract int findIndex(String uuid);

    protected abstract void insertElement(Resume r, int index);

    protected abstract void fillDeletedElement(int index);
}