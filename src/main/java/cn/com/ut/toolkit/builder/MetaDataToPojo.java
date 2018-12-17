package cn.com.ut.toolkit.builder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.com.ut.core.dal.jdbc.BaseEntity;
import cn.com.ut.toolkit.builder.pojo.ColumnType;
import cn.com.ut.toolkit.builder.pojo.DataBase;
import cn.com.ut.toolkit.builder.pojo.DataBaseHolder;
import cn.com.ut.toolkit.builder.util.JdbcUtil;

/**
 * 按照表字段名称和类型生成POJO
 * 
 * @author wuxiaohua
 * @since 2018-5-11
 */
@Component
public class MetaDataToPojo {

	/* DATE 91 */
	/* TIME 92 */
	/* TIMESTAMP 93 */
	/* CHAR 1 */
	/* VARCHAR 12 */
	/* DECIMAL 3 */
	/* BIGINT -5 */
	public static List<Integer> ints = Arrays.asList(new Integer[] { 4, 5 });

	public static List<Integer> strings = Arrays.asList(new Integer[] { 1, 12, -1 });

	public static List<Integer> reals = Arrays.asList(new Integer[] { 6, 7, 8, 2, 3 });

	public static final List<String> EXCLUDE_FIELDS = Arrays
			.asList(new String[] { BaseEntity.idx, BaseEntity.create_id, BaseEntity.create_time,
					BaseEntity.update_id, BaseEntity.update_time, BaseEntity.is_del });

	@Autowired
	private DataBaseHolder dataBaseHolder;

	/**
	 * 将表字段名称转换为对象属性名称，注意"_"下划线的处理，采用驼峰命名法则
	 * 
	 * @param columnLabel
	 *            表字段名称
	 * @return
	 */
	public String convertColumnLabel(String columnLabel) {

		StringBuilder stringBuilder = new StringBuilder();
		columnLabel = columnLabel.toLowerCase();
		String[] array = columnLabel.split("_");
		for (String e : array) {
			if ("".equals(e) || "_".equals(e))
				continue;
			if (stringBuilder.length() == 0) {
				stringBuilder.append(e);
			} else {
				char[] data = e.toCharArray();
				data[0] = Character.toUpperCase(data[0]);
				stringBuilder.append(String.valueOf(data));
			}
		}

		return stringBuilder.toString();
	}

	public Map<String, ColumnType> buildStaticFieldsByJdbc(String packageName, String className,
			String table, String db) {

		Map<String, ColumnType> columnTypeMap = new HashMap<>();
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		DataBase dataBase = dataBaseHolder.getDbHolder().get(db);

		try {
			conn = JdbcUtil.getConnection(dataBase);
			pstmt = conn.prepareStatement("select * from " + table);
			ResultSetMetaData rsmd = pstmt.getMetaData();
			int count = rsmd.getColumnCount();
			for (int i = 0; i < count; i++) {
				String columnName = rsmd.getColumnName(i + 1).toLowerCase();
				int type = rsmd.getColumnType(i + 1);
				if (!EXCLUDE_FIELDS.contains(columnName)) {
					ColumnType columnType = new ColumnType();
					columnType.setLabel(columnName);
					columnType.setType(type);
					columnTypeMap.put(columnName, columnType);
				}
			}

			stmt = conn.createStatement();
			rs = stmt.executeQuery("show full columns from " + table);
			while (rs.next()) {
				String field = rs.getString("Field");
				ColumnType columnType = columnTypeMap.get(field);
				if (columnType != null) {
					columnType.setComment(rs.getString("Comment"));
				}
			}

		} catch (SQLException e) {

		} finally {
			JdbcUtil.closeResultSet(rs);
			JdbcUtil.closeStatement(pstmt);
			JdbcUtil.closeStatement(stmt);
			JdbcUtil.closeConnection(conn);
		}

		return columnTypeMap;

	}

	public static void main(String[] args) {

		MetaDataToPojo obj = new MetaDataToPojo();
		String value = "__sd__fE_T_es";
		System.out.println(obj.convertColumnLabel(value));

	}

}
