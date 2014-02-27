package com.paitao.freeshake.db.dao;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import com.paitao.freeshake.db.annotation.Column;
import com.paitao.freeshake.db.annotation.Id;
import com.paitao.freeshake.db.annotation.Table;
import com.paitao.freeshake.db.util.TableHelper;

import android.database.sqlite.SQLiteOpenHelper;


public class BaseDaoImpl<T> implements IBaseDao<T>{
  private SQLiteOpenHelper mDbHelper;
  private String tableName;
  private String idColumn;
  private Class<T> clazz;
  private List<Field> allFields;
  public BaseDaoImpl(SQLiteOpenHelper dbHelper,Class<T> clazz)
{
    // TODO Auto-generated constructor stub
      this.mDbHelper=dbHelper;
      if(clazz==null){
          this.clazz=(Class<T>) ((java.lang.reflect.ParameterizedType)(super.getClass().getGenericSuperclass())).getActualTypeArguments()[0];
      }else{
          this.clazz=clazz;
      }
      if(this.clazz.isAnnotationPresent(Table.class)){
          Table table=(Table)this.clazz.getAnnotation(Table.class);
          this.tableName=table.name();
      }
      this.allFields=TableHelper.joinFields(this.clazz.getDeclaredFields(), this.clazz.getSuperclass().getDeclaredFields());
      for(Field field:this.allFields){
          if(field.isAnnotationPresent(Id.class)){
              Column column=field.getAnnotation(Column.class);
              this.idColumn=column.name();
              break;
          }
      }
}
  public BaseDaoImpl(SQLiteOpenHelper dbHelper){
      this(dbHelper, null);
  }
    @Override
    public SQLiteOpenHelper getDbHelper()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long insert(T entity)
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public long insert(T entity, boolean autoPrimaryKey)
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void delete(int id)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void delete(Integer... ids)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void update(T entity)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public T get(int id)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<T> rawQuery(String sql, String[] selectionArgs)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<T> find()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<T> find(String[] columns, String selection,
            String[] selectionArgs, String groupBy, String having,
            String orderBy, String limit)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, String>> query2MapList(String sql,
            String[] selectionArgs)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isExist(String sql, String[] selectionArgs)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void execSql(String sql, Object[] selectionArgs)
    {
        // TODO Auto-generated method stub
        
    }
    
}

