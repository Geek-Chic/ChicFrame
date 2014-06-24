package com.geekchic.base.db;

import java.util.List;
import java.util.Map;

import android.database.sqlite.SQLiteOpenHelper;
/**
 * 
 * @ClassName: BaseDao
 * @Descritpion: 数据库基本操作
 * @author evil
 * @date May 4, 2014
 * @param <T>
 */
public interface BaseDao<T> {

	public SQLiteOpenHelper getDbHelper();
	/**
	 * 插入数据
	 * @param entity
	 * @return
	 */
	public abstract long insert(T entity);
    /**
     * 插入,并设置主键是否自增
     * @param entity
     * @param flag
     * @return
     */
	public abstract long insert(T entity, boolean flag);
     /**
      * 根据id删除
      * @param id
      */
	public abstract void delete(int id);
    /**
     * 根据id数组删除多条记录
     * @param ids
     */
	public abstract void delete(Integer... ids);
     /**
      * 更新数据
      * @param entity
      */
	public abstract void update(T entity);
     /**
      * 根据id查询
      * @param id
      * @return
      */
	public abstract T get(int id);
     /**
      * 条件查询
      * @param sql
      * @param selectionArgs
      * @return
      */
	public abstract List<T> rawQuery(String sql, String[] selectionArgs);
    /**
     *  查找所有
     * @return
     */
	public abstract List<T> find();
     /**
      * 高级查询
      * @param columns
      * @param selection
      * @param selectionArgs
      * @param groupBy
      * @param having
      * @param orderBy
      * @param limit
      * @return
      */
	public abstract List<T> find(String[] columns, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy, String limit);
    /**
     * 记录是否存在
     * @param sql
     * @param selectionArgs
     * @return
     */
	public abstract boolean isExist(String sql, String[] selectionArgs);
     /**
      * 查询并转化为map
      * @param sql
      * @param selectionArgs
      * @return
      */
	public List<Map<String, String>> query2MapList(String sql,
			String[] selectionArgs);
    /**
     * 执行sql
     * @param sql
     * @param selectionArgs
     */
	public void execSql(String sql, Object[] selectionArgs);

}