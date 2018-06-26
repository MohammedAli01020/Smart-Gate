package com.example.mohamed.smartgate.data;


import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

@Database(version = MilitaryDatabase.VERSION)
final class MilitaryDatabase {

    static final int VERSION = 1;

    @Table(MilitaryContract.UsersEntry.class)
    static final String TABLE_USERS = "users";
}
