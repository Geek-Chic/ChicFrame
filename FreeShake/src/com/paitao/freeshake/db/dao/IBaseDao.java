package com.paitao.freeshake.db.dao;

import java.util.List;
import java.util.Map;

import android.database.sqlite.SQLiteOpenHelper;

public interface IBaseDao<T>
{
    public SQLiteOpenHelper getDbHelper();
    /**
     * ����ʵ������
     * @param entity
     * @return
     */
    public abstract long insert(T entity);
    /**
     * ����ʵ������
     * @param entity ʵ������
     * @param autoPrimaryKey true��ʾ�Զ�����������false��ʾ����ָ������
     * @return
     */
    public abstract long insert(T entity,boolean autoPrimaryKey);
    /**
     * ����IDɾ������
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
     * ����ѯ�Ľ������Ϊ��ֵ��map.
     * 
     * @param sql
     *            ��ѯsql
     * @param selectionArgs
     *            ����ֵ
     * @return ���ص�Map�е�keyȫ����Сд��ʽ.
     */
    public List<Map<String, String>> query2MapList(String sql,
            String[] selectionArgs);
    public abstract boolean isExist(String sql, String[] selectionArgs);
    /**
     * ִ��sql���
     * @param sql
     * @param selectionArgs
     */
    public void execSql(String sql,Object[] selectionArgs);
}
