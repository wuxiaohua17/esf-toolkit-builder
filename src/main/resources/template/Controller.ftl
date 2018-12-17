
//-----------------------------Controller-start---------------------------------//
package ${pkg}.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ${pkg}.entities.${className};
import ${pkg}.service.${className}Service;
import cn.com.ut.core.common.jdbc.PageBean;
import cn.com.ut.core.common.jdbc.PageBuilder;
import cn.com.ut.core.common.system.beans.User;
import cn.com.ut.core.restful.ResponseWrap;

/**
 * Controller
 * 
 * @author 
 * @since 
 */
@RestController
@RequestMapping(value = "/${className?uncap_first}")
public class ${className}Controller {

	@Autowired
	private ${className}Service ${className?uncap_first}Service;
	
}
//-----------------------------Controller-end---------------------------------//