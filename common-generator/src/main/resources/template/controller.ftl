package ${package_path}.controller;

import ${package_path}.service.${class_name}Service;
import ${package_path}.dao.${class_name}Dao;
import ${package_path}.entity.${class_name};
import org.excavator.boot.base.service.*;
import org.excavator.boot.base.domain.*;
import org.springframework.http.ResponseEntity;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import java.util.List;
import org.excavator.boot.idworker.generator.IdGenerator;
/**
 * ServiceImpl:${class_name}Controller
 * @author ${author}
 * @version 3.0
 * @date ${sysDate?date}
 */
@RestController
@RequestMapping("/v1")
public class ${class_name}Controller{
    @Resource
    private IdGenerator idGenerator;
	
    @Resource
    private ${class_name}Service ${class_name?uncap_first}Service;

    @GetMapping("/${class_name?uncap_first}s")
    public ResponseEntity<List<${class_name}>> findAll(){
       List<${class_name}> list = ${class_name?uncap_first}Service.findAll();

       return ResponseEntity.ok().body(list);
    }

    @GetMapping("/${class_name?uncap_first}s/{id}")
    public ResponseEntity<${class_name}> find(@PathVariable("id") Long id){
       ${class_name} ${class_name?uncap_first} = ${class_name?uncap_first}Service.get(id);

       return ResponseEntity.ok().body(${class_name?uncap_first});
    }

    @PostMapping("/${class_name?uncap_first}s")
    public ResponseEntity<${class_name}> save(@Validated @RequestBody ${class_name} ${class_name?uncap_first}){
       ${class_name?uncap_first}.setId(idGenerator.nextId());
       ${class_name?uncap_first}Service.insert(${class_name?uncap_first});

       return ResponseEntity.ok().body(${class_name?uncap_first});
    }

    @PutMapping("/${class_name?uncap_first}s/{id}")
    public ResponseEntity<Boolean> putEntity(@PathVariable("id") Long id){
      //TODO ${class_name?uncap_first}Controller putEntity
      ${class_name} ${class_name?uncap_first} = ${class_name?uncap_first}Service.get(id);
       if(null != ${class_name?uncap_first} ){
           ${class_name?uncap_first}Service.update(${class_name?uncap_first});
       }

      return ResponseEntity.ok().body(true);

    }

    @DeleteMapping("/${class_name?uncap_first}s/{id}")
    public ResponseEntity<Boolean> deleteEntity(@PathVariable("id") Long id){
        ${class_name?uncap_first}Service.delete(id);

        return ResponseEntity.ok().body(true);
    }

}