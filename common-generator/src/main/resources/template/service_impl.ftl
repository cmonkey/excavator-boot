package ${package_path}.service.impl;

import ${package_path}.service.${class_name}Service;
import ${package_path}.dao.${class_name}Dao;
import ${package_path}.entity.${class_name};
import org.excavator.boot.base.service.*;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * ServiceImpl:${class_name}ServiceImpl
 * @author ${author}
 * @version 3.0
 * @date ${sysDate?date}
 */
@Service("${class_name?uncap_first}Service") 
public class ${class_name}ServiceImpl  extends BaseServiceImpl<${class_name}Dao, ${class_name}> implements ${class_name}Service{
	
    //@Resource
    //private ${class_name}Dao ${class_name?uncap_first}Dao;

}