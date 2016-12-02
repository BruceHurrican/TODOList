/*
 * BruceHurrican
 * Copyright (c) 2016.
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 *    This document is Bruce's individual learning the android demo, wherein the use of the code from the Internet, only to use as a learning exchanges.
 *    And where any person can download and use, but not for commercial purposes.
 *    Author does not assume the resulting corresponding disputes.
 *    If you have good suggestions for the code, you can contact BurrceHurrican@foxmail.com
 *    本文件为Bruce's个人学习android的作品, 其中所用到的代码来源于互联网，仅作为学习交流使用。
 *    任和何人可以下载并使用, 但是不能用于商业用途。
 *    作者不承担由此带来的相应纠纷。
 *    如果对本代码有好的建议，可以联系BurrceHurrican@foxmail.com
 */

package bruce.kk.brucetodos.database.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

import bruce.kk.brucetodos.database.bean.UnFinishItem;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table "t_todo".
 */
public class UnFinishItemDao extends AbstractDao<UnFinishItem, Long> {

    public static final String TABLENAME = "t_todo";

    public UnFinishItemDao(DaoConfig config) {
        super(config);
    }


    public UnFinishItemDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /**
     * Creates the underlying database table.
     */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "\"t_todo\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE ," + // 0: id
                "\"createDay\" Date NOT NULL ," + // 1: createDay
                "\"content\" String NOT NULL ," + // 2: content
                "\"modifyDay\" Date NOT NULL ," + // 3: modifyDay
                "\"finishDay\" Date);"); // 4: finishDay
    }

    /**
     * Drops the underlying database table.
     */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"t_todo\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, UnFinishItem entity) {
        stmt.clearBindings();

        Long id = entity.id;
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.createDay.getTime());
        stmt.bindString(3, entity.content);
        stmt.bindLong(4, entity.modifyDay.getTime());

        java.util.Date finishDay = entity.finishDay;
        if (finishDay != null) {
            stmt.bindLong(5, finishDay.getTime());
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, UnFinishItem entity) {
        stmt.clearBindings();

        Long id = entity.id;
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.createDay.getTime());
        stmt.bindString(3, entity.content);
        stmt.bindLong(4, entity.modifyDay.getTime());

        java.util.Date finishDay = entity.finishDay;
        if (finishDay != null) {
            stmt.bindLong(5, finishDay.getTime());
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    @Override
    public UnFinishItem readEntity(Cursor cursor, int offset) {
        UnFinishItem entity = new UnFinishItem( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
                new java.util.Date(cursor.getLong(offset + 1)), // createDay
                cursor.getString(offset + 2), // content
                new java.util.Date(cursor.getLong(offset + 3)), // modifyDay
                cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)) // finishDay
        );
        return entity;
    }

    @Override
    public void readEntity(Cursor cursor, UnFinishItem entity, int offset) {
        entity.id = (cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.createDay = new java.util.Date(cursor.getLong(offset + 1));
        entity.content = cursor.getString(offset + 2);
        entity.modifyDay = new java.util.Date(cursor.getLong(offset + 3));
        entity.finishDay = cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4));
    }

    @Override
    protected final Long updateKeyAfterInsert(UnFinishItem entity, long rowId) {
        entity.id = rowId;
        return rowId;
    }

    @Override
    public Long getKey(UnFinishItem entity) {
        if (entity != null) {
            return entity.id;
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(UnFinishItem entity) {
        return entity.id != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }

    /**
     * Properties of entity UnFinishItem.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property CreateDay = new Property(1, java.util.Date.class, "createDay", false, "createDay");
        public final static Property Content = new Property(2, String.class, "content", false, "content");
        public final static Property ModifyDay = new Property(3, java.util.Date.class, "modifyDay", false, "modifyDay");
        public final static Property FinishDay = new Property(4, java.util.Date.class, "finishDay", false, "finishDay");
    }

}
