package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    private Resume[] sortArray() {
        Arrays.parallelSort(storage, 0, size);
        return storage;
    }

    @Override
    protected int findIndex(String uuid) {
        sortArray();
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}