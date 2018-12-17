
//-----------------------------ServiceImpl-start---------------------------------//
package ${pkg}.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ${pkg}.dao.${className}DAO;
import ${pkg}.entities.${className};
import ${pkg}.service.${className}Service;
import cn.com.ut.core.common.jdbc.PageBean;
import cn.com.ut.core.common.system.beans.User;
import cn.com.ut.core.common.util.CollectionUtil;
import cn.com.ut.core.common.util.CommonUtil;
import cn.com.ut.core.common.util.ExceptionUtil;
import cn.com.ut.core.common.util.validator.ValidatorUtil;

/**
 * ServiceImpl
 * 
 * @author 
 * @since 
 */
@Service
public class ${className}ServiceImpl implements ${className}Service {

	@Autowired
	private ${className}DAO ${className?uncap_first}DAO;
	
}
//-----------------------------ServiceImpl-end---------------------------------//