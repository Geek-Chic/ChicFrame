package com.geekchic.base.db;

import java.lang.reflect.Field;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.database.sqlite.SQLiteDatabase;

import com.geekchic.base.db.annotation.Column;
import com.geekchic.base.db.annotation.Id;
import com.geekchic.base.db.annotation.Table;
import com.geekchic.common.log.Logger;
/**
 * @ClassName: TableUtils
 * @Descritpion:注释解析帮助类
 * @author evil
 * @date May 5, 2014
 */
public class TableUtils {
	/**
	 * TAG
	 */
	private static final String TAG = "TableUtils";
    /**
     * 建表
     * @param db
     * @param clazzs
     */
	public static <T> void createTablesByClasses(SQLiteDatabase db,
			Class<?>[] clazzs) {
		for (Class<?> clazz : clazzs)
			createTable(db, clazz);
	}
    /**
     * 删除表
     * @param db
     * @param clazzs
     */
	public static <T> void dropTablesByClasses(SQLiteDatabase db,
			Class<?>[] clazzs) {
		for (Class<?> clazz : clazzs)
			dropTable(db, clazz);
	}
     /**
      * 创建表
      * @param db
      * @param clazz
      */
	public static <T> void createTable(SQLiteDatabase db, Class<T> clazz) {
		String tableName = "";
		if (clazz.isAnnotationPresent(Table.class)) {
			Table table = (Table) clazz.getAnnotation(Table.class);
			tableName = table.name();
		}

		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE ").append(tableName).append(" (");
       //拼接属性字段
		List<Field> allFields = TableUtils
				.joinFields(clazz.getDeclaredFields(), clazz.getSuperclass()
						.getDeclaredFields());
		for (Field field : allFields) {
			if (!field.isAnnotationPresent(Column.class)) {
				continue;
			}

			Column column = (Column) field.getAnnotation(Column.class);

			String columnType = "";
			if (column.type().equals(""))
				columnType = getColumnType(field.getType());
			else {
				columnType = column.type();
			}

			sb.append(column.name() + " " + columnType);

			if (column.length() != 0) {
				sb.append("(" + column.length() + ")");
			}
            //int型主键自增
			if ((field.isAnnotationPresent(Id.class)) 
					&& ((field.getType() == Integer.TYPE) || (field.getType() == Integer.class)))
				sb.append(" primary key autoincrement");
			else if (field.isAnnotationPresent(Id.class)) {
				sb.append(" primary key");
			}

			sb.append(", ");
		}

		sb.delete(sb.length() - 2, sb.length() - 1);
		sb.append(")");

		String sql = sb.toString();

		Logger.d(TAG, "crate table [" + tableName + "]: " + sql);

		db.execSQL(sql);
	}

	public static <T> void dropTable(SQLiteDatabase db, Class<T> clazz) {
		String tableName = "";
		if (clazz.isAnnotationPresent(Table.class)) {
			Table table = (Table) clazz.getAnnotation(Table.class);
			tableName = table.name();
		}
		String sql = "DROP TABLE IF EXISTS " + tableName;
		Logger.d(TAG, "dropTable[" + tableName + "]:" + sql);
		db.execSQL(sql);
	}

	private static String getColumnType(Class<?> fieldType) {
		if (String.class == fieldType) {
			return "TEXT";
		}
		if ((Integer.TYPE == fieldType) || (Integer.class == fieldType)) {
			return "INTEGER";
		}
		if ((Long.TYPE == fieldType) || (Long.class == fieldType)) {
			return "BIGINT";
		}
		if ((Float.TYPE == fieldType) || (Float.class == fieldType)) {
			return "FLOAT";
		}
		if ((Short.TYPE == fieldType) || (Short.class == fieldType)) {
			return "INT";
		}
		if ((Double.TYPE == fieldType) || (Double.class == fieldType)) {
			return "DOUBLE";
		}
		if (Blob.class == fieldType) {
			return "BLOB";
		}

		return "TEXT";
	}

	/**
	 * 将父类子类的字段合并去重
	 * @param fields1
	 * @param fields2
	 * @return
	 */
	public static List<Field> joinFields(Field[] fields1, Field[] fields2) {
		Map<String, Field> map = new LinkedHashMap<String, Field>();
		for (Field field : fields1) {
			if (!field.isAnnotationPresent(Column.class)) {
				continue;
			}
			Column column = (Column) field.getAnnotation(Column.class);
			map.put(column.name(), field);
		}
		for (Field field : fields2) {
			if (!field.isAnnotationPresent(Column.class)) {
				continue;
			}
			Column column = (Column) field.getAnnotation(Column.class);
			if (!map.containsKey(column.name())) {
				map.put(column.name(), field);
			}
		}
		List<Field> list = new ArrayList<Field>();
		for (String key : map.keySet()) {
			Field tempField = map.get(key);
			//主键放头上
			if (tempField.isAnnotationPresent(Id.class)) {
				list.add(0, tempField);
			} else {
				list.add(tempField);
			}
		}
		return list;
	}
}