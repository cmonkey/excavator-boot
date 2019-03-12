package ${package_path};

<#if (hasDateColumn)>
import java.util.Date;
</#if>
import org.excavator.boot.base.domain.BaseEntity;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
/**
 * entity:${class_name}
 * 
 * @author ${author}
 * @version 3.0
 * @date ${sysDate?date}
 */
public class ${class_name} extends BaseEntity<${class_name}> {
	
	private static final long serialVersionUID = 1L;
	<#list table_column as c>
	<#if (c.name!="uuid")>private ${c.type}	${c.nameJ};		<#if (c.remark?? && c.remark!="")> /* ${c.remark} */ </#if></#if>
	</#list>

	//其他自定义属性
	

	// Constructor
	public ${class_name}() {
	}

	/**
	 * full Constructor
	 */
	public ${class_name}(<#list table_column as c>${c.type} ${c.nameJ}<#if c_has_next>, </#if></#list>) {
		<#list table_column as c>
		<#if (c.name!="uuid")>this.${c.nameJ} = ${c.nameJ};<#else>setUuid(uuid);</#if>
		</#list>
	}

	<#list table_column as c><#if (c.name!="uuid")>
	<#-- <#if (c.type=="Date")>@JsonSerialize(using = ShortDateSerializer.class)</#if>	-->
	public ${c.type} get${c.nameJ?cap_first}() {
		return ${c.nameJ};
	}

	public void set${c.nameJ?cap_first}(${c.type} ${c.nameJ}) {
		this.${c.nameJ} = ${c.nameJ};
	}

	</#if></#list>
	@Override
	public String toString() {
        return ReflectionToStringBuilder.toString(this);
	}
}
