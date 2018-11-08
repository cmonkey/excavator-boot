package ${package_path}.dao;

import ${package_path}.entity.${class_name};
import org.excavator.boot.base.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
/**
 * Dao Interface:${class_name}Mapper
 * @author ${author}
 * @version 3.0
 * @date ${sysDate?date}
 */
@Mapper
public interface ${class_name}Dao extends BaseMapper<${class_name}> {

}