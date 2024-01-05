package com.urise.webapp.storage;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        ArrayStorageTest.class,
        SortedStorageTest.class,
        ListStorageTest.class,
        MapStorageTest.class
})
public class AllStorageTests {
}