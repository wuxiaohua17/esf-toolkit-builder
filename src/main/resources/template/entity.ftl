
//------------------------------entity-start--------------------------------//
package ${pkg}.entities;

import cn.com.ut.core.dal.jdbc.BaseEntity;

public class ${className} extends BaseEntity {


	<#list columnTypes?values as columnType>
	/**
     * ${columnType.comment}
     */
    public static final String ${columnType.label} = "${columnType.label}";
	</#list>
}
//------------------------------entity-end--------------------------------//
