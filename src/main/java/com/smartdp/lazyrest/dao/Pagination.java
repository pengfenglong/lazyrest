package com.smartdp.lazyrest.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 分页实体
 * 
 * @author pfl
 * @see Pagination
 * @since
 */
public class Pagination implements Serializable {
	/**
	 * 序列号<br>
	 */
	private static final long serialVersionUID = -2554565760258955645L;

	/**
	 * 每页显示的记录数
	 */
	private int pageSize;

	/**
	 * 记录总数 （命名必须为total 对应easyui分页）
	 */
	private int total;

	/**
	 * 总页数
	 */
	private int totalPages;

	/**
	 * 当前页码
	 */
	private int currentPage;

	/**
	 * 记录起始行数
	 */
	private int startIndex;

	/**
	 * 记录结束行数
	 */
	private int lastIndex;

	/**
	 * 结果集存放List
	 */
	private List<Map<String, Object>> result;

	/**
	 * 构造函数
	 * 
	 * @param sql
	 *            sql语句
	 * @param currentPage
	 *            当前页码
	 * @param numPerPage
	 *            每页显示记录数
	 * @param jdbcTemplate
	 *            JdbcTemplate实例
	 */
	public Pagination(String sql, int currentPage, int pageSize,
			JdbcTemplate jdbcTemplate) {
		if (jdbcTemplate == null) {
			throw new IllegalArgumentException(
					"jdbcTemplate is null , pls initialize ... ");
		} else if (StringUtils.isBlank(sql)) {
			throw new IllegalArgumentException(
					"sql is blank , pls initialize ... ");
		}
		// 设置每页显示记录数
		setPageSize(pageSize);

		// 设置当前页数
		setCurrentPage(currentPage);

		// 计算总记录数SQL
		StringBuffer totalSQL = new StringBuffer(" select count(1) from ( ");
		totalSQL.append(sql);
		totalSQL.append(" ) as total");

		// 总记录数
		setTotal(jdbcTemplate.queryForInt(totalSQL.toString()));

		// 计算总页数
		setTotalPages();

		// 计算起始行数
		setStartIndex();

		// 计算结束行数
		setLastIndex();

		String paginationSQL = getMySQLPageSQL(sql, startIndex, pageSize);

		// 装入结果集
		setResult(jdbcTemplate.queryForList(paginationSQL));
	}

	/**
	 * 构造MySQL数据分页SQL
	 * 
	 * @param queryString
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public String getMySQLPageSQL(String sql, Integer startIndex,
			Integer pageSize) {
		String result = "";
		if (null != startIndex && null != pageSize) {
			result = sql + " limit " + startIndex + "," + pageSize;
		} else if (null != startIndex && null == pageSize) {
			result = sql + " limit " + startIndex;
		} else {
			result = sql;
		}
		return result;
	}

	/**
	 * 构造oracle数据分页SQL
	 * 
	 * @param sql
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public String getOraclePageSQL(String sql, Integer startIndex,
			Integer lastIndex) {
		// 拼装oracle的分页语句 （其他DB修改此处的分页关键词即可）
		StringBuffer paginationSQL = new StringBuffer(" select * from ( ");
		paginationSQL.append(" select row_limit.*,rownum rownum_ from ( ");
		paginationSQL.append(sql);
		paginationSQL.append("　) row_limit where rownum <= " + lastIndex);
		paginationSQL.append(" ) where　rownum_ > " + startIndex);
		return paginationSQL.toString();
	}

	/**
	 * 根据总记录数和每页显示记录数 计算总页数
	 * 
	 * @see
	 */
	private void setTotalPages() {
		if (total % pageSize == 0) {
			this.totalPages = total / pageSize;
		} else {
			this.totalPages = (total / pageSize) + 1;
		}
	}

	/**
	 * 根据当前页和每页显示记录条数 计算记录开始行数
	 * 
	 * @see
	 */
	private void setStartIndex() {
		this.startIndex = (currentPage - 1) * pageSize;
	}

	/**
	 * 计算记录结束行数
	 * 
	 * @see
	 */
	private void setLastIndex() {
		if (total < pageSize) {
			this.lastIndex = total;
		} else if ((total % pageSize == 0)
				|| (total % pageSize != 0 && currentPage < totalPages)) {
			this.lastIndex = currentPage * pageSize;
		} else if (total % pageSize != 0 && currentPage == totalPages) {
			this.lastIndex = total;
		}
	}

	// setter and getter
	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<Map<String, Object>> getResult() {
		return result;
	}

	public void setResult(List<Map<String, Object>> result) {
		this.result = result;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public int getLastIndex() {
		return lastIndex;
	}

}

class ConvertMapkey {

	/**
	 * 把map对象的key全部转为小写形式
	 * 
	 * @param map
	 * @return
	 */
	public static Map<String, Object> keyToLower(Map<String, Object> map) {
		Map<String, Object> r = new HashMap<String, Object>();
		if (map == null || map.size() == 0)
			return r;
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			r.put(entry.getKey().toLowerCase(), entry.getValue());
		}
		return r;
	}

	/**
	 * 把list map中map对象的key全部转为小写形式
	 * 
	 * @param listmap
	 * @return
	 */
	public static List<Map<String, Object>> listKeyToLower(
			List<Map<String, Object>> listmap) {
		List<Map<String, Object>> r = new ArrayList<Map<String, Object>>();
		if (listmap == null || listmap.size() == 0)
			return r;
		for (Map<String, Object> map : listmap) {
			r.add(keyToLower(map));
		}
		return r;
	}
}
