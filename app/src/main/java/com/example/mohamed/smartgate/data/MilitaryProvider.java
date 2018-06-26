package com.example.mohamed.smartgate.data;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

@ContentProvider(authority = MilitaryProvider.AUTHORITY, database = MilitaryDatabase.class)
public final class MilitaryProvider {

    static final String AUTHORITY = "com.example.mohamed.smartgate";

    @TableEndpoint(table = MilitaryDatabase.TABLE_USERS)
    public static class Users {

        @ContentUri(
                path = MilitaryDatabase.TABLE_USERS,
                type = "vnd.android.cursor.dir/" + MilitaryDatabase.TABLE_USERS)

        public static final Uri USERS_URI = Uri.parse("content://" + AUTHORITY + "/" + MilitaryDatabase.TABLE_USERS);
    }
}
