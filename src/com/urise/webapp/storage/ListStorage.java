package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ListStorage extends ArrayStorage {
    private List<Resume> list = new ArrayList<>();

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public void update(Resume r) {
        int index = findIndex(r.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(r.getUuid());
        } else {
            list.set(index, r);
        }
    }

    @Override
    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            list.remove(index);
        }
    }

    @Override
    public void save(Resume r) {
        int index = findIndex(r.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(r.getUuid());
        } else if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        } else {
            list.add(r);
        }
    }

    @Override
    public Resume[] getAll() {
        return list.toArray(new Resume[list.size()]);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return list.get(index);
    }

    @Override
    protected int findIndex(String uuid) {
        for (Resume r : list) {
            if (Objects.equals(r.getUuid(), uuid)) {
                return list.indexOf(r);
            }
        }
        return -1;
    }
}