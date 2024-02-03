package com.urise.webapp.storage;

class ObjectStreamStorageTest extends AbstractStorageTest {
    protected ObjectStreamStorageTest() {
        super(new ObjectStreamStorage(STORAGE_DIR));
    }
}