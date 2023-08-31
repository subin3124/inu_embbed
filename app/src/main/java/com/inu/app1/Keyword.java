package com.inu.app1;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Keyword {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo()
    public String keyword;
    @ColumnInfo()
    public int important;
}
