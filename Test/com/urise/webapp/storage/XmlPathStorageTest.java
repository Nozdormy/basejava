package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.XmlStreamSerializer;

public class XmlPathStorageTest extends AbstractStorageTest{
    protected XmlPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new XmlStreamSerializer()));
    }
}