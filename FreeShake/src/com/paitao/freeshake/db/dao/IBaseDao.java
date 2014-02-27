package com.paitao.freeshake.db.dao;

import java.util.List;
import java.util.Map;

import android.database.sqlite.SQLiteOpenHelper;

public interface IBaseDao<T>
{
    public SQLiteOpenHelper getDbHelper();
    /**
     * 插入实体数据
     * @param entity
     * @return
     */
    public abstract long insert(T entity);
    /**
     * 插入实体数据
     * @param entity 实体数据
     * @param autoPrimaryKey true表示自动生成主键，false表示自行指定主键
     * @return
     */
    public abstract long insert(T entity,boolean autoPrimaryKey);
    /**
     * 根据ID删除数据
     * @param id
     */
    public abstract void delete(int id);
    public abstract void delete(Integer... ids);
    public abstract void update(T entity);
    public abstract T get(int id);
    public abstract List<T> rawQuery(String sql,String[] selectionArgs);
    public abstract List<T> find();
    public abstract List<T> find(String[] columns, String selection,
            String[] selectionArgs, String groupBy, String having,
            String orderBy, String limit);
    /**
     * 将查询的结果保存为名值对map.
     * 
     * @param sql
     *            查询sql
     * @param selectionArgs
     *            参数值
     * @return 返回的Map中的key全部是小写形式.
     */
    public List<Map<String, String>> query2MapList(String sql,
            String[] selectionArgs);
    public abstract boolean isExist(String sql, String[] selectionArgs);
    /**
     * 执行sql语句
     * @param sql
     * @param selectionArgs
     */
    public void execSql(String sql,Object[] selectionArgs);
}
