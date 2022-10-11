package com.solucioneshr.soft.retrofit_room.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.String;

@Entity(tableName = "data")
public class DataModel implements Serializable {
  @PrimaryKey
  @NonNull
  @ColumnInfo(name = "id")
  private Integer id;

  @ColumnInfo(name = "title")
  private String title;

  @ColumnInfo(name = "body")
  private String body;

  @ColumnInfo(name = "userId")
  private Integer userId;

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getBody() {
    return this.body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public Integer getUserId() {
    return this.userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }
}
