
//-----------------------------DAOImpl-start---------------------------------//
package ${pkg}.dao.impl;

<#list columnTypes?values as columnType>
import static ${pkg}.entities.${className}.${columnType.label};
</#list>
import static cn.com.ut.core.dal.jdbc.BaseEntity.idx;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import ${pkg}.dao.${className}DAO;
import ${pkg}.entities.${className};
import cn.com.ut.core.common.jdbc.PageBean;
import cn.com.ut.core.common.util.ArrayUtil;
import cn.com.ut.core.common.util.CollectionUtil;
import cn.com.ut.core.common.util.CommonUtil;
import cn.com.ut.core.common.util.converter.DateTimeUtil;
import cn.com.ut.core.dal.jdbc.JdbcOperationsImpl;
import cn.com.ut.core.dal.jdbc.ParameterBuilder;
import cn.com.ut.core.dal.sql.SQLHelper;

@Repository
public class ${className}DAOImpl extends JdbcOperationsImpl<${className}> implements ${className}DAO {
	
	<#assign fields="" />
	<#assign index=0 />
	<#list columnTypes?values as columnType>
	<#if index==0>
	<#assign fields+="${columnType.label}" />
	<#else>
	<#assign fields+=", ${columnType.label}" />
	</#if>
	<#assign index+=1 />
	</#list>
	
	public String add(Map<String, Object> vo) {

		String id = CommonUtil.getUUID();
		add(null, new String[]{${fields}}, NAMES_ID_CT_CID, ParameterBuilder.builder().append(vo, new String[]{${fields}})
				.append(id, DateTimeUtil.currentDateTime(), vo.get(${className}.create_id)).toArray());
		return id;
	}

	public int update(Map<String, Object> vo) {

		return updateById(null, new String[]{${fields}}, NAMES_UT_UID, null,
				ParameterBuilder.builder().append(vo, new String[]{${fields}})
						.append(DateTimeUtil.currentDateTime(), vo.get(${className}.update_id)).toArray(),
				(String) vo.get(${className}.idx), null);
	}
}
//-----------------------------DAOImpl-end---------------------------------//