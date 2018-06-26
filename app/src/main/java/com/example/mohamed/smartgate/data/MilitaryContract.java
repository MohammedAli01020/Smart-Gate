package com.example.mohamed.smartgate.data;


import android.provider.BaseColumns;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

public final class MilitaryContract {
    private MilitaryContract() {
    }

    public static final class UsersEntry implements BaseColumns {
        @DataType(DataType.Type.INTEGER)
        @PrimaryKey
        @AutoIncrement
        public static final String _ID = BaseColumns._ID;
        @DataType(DataType.Type.TEXT)
        @NotNull
        public static final String COLUMN_NAME = "name";
        @DataType(DataType.Type.TEXT)
        @NotNull
        public static final String COLUMN_USERNAME = "username";
        @DataType(DataType.Type.INTEGER)
        @NotNull
        public static final String COLUMN_PHOTO = "photo";
    }
}
