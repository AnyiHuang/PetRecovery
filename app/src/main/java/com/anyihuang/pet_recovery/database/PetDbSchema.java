package com.anyihuang.pet_recovery.database;

public class PetDbSchema {
    public static final class PetTable{
        public static final String NAME = "pets";

        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String NAME = "name";
            public static final String LOCATION = "location";
            public static final String DETAIL = "detail";
            public static final String DATE = "date";
            public static final String FOUND = "found";
            public static final String PHOTO = "photo";
            public static final String MALE = "male";
        }
    }
}
