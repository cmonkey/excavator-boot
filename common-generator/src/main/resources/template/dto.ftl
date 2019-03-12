package ${package_path};

<#if (hasDateColumn)>
import java.util.Date;
</#if>


/**
 * DTO:${class_name}DTO
 * @author ${author}
 * @version 3.0
 * @date ${sysDate?date}
 */
public class ${class_name}DTO implements Serializable {
	
	<#list table_column as c>
	<#if (c.name!="id")>private ${c.type}	${c.nameJ};		<#if (c.remark?? && c.remark!="")> /* ${c.remark} */ </#if></#if>
	</#list>

	// Constructor
	public ${class_name}DTO() {
	}

	/**
	 * full Constructor
	 */
	public ${class_name}DTO(<#list table_column as c>${c.type} ${c.nameJ}<#if c_has_next>, </#if></#list>) {
		<#list table_column as c>
		<#if (c.name!="id")>this.${c.nameJ} = ${c.nameJ};<#else>setId(id);</#if>
		</#list>
	}

	<#list table_column as c><#if (c.name!="id")>
	<#if (c.type=="Date")>@JsonSerialize(using = ShortDateSerializer.class)</#if>
	public ${c.type} get${c.nameJ?cap_first}() {
		return ${c.nameJ};
	}

	public void set${c.nameJ?cap_first}(${c.type} ${c.nameJ}) {
		this.${c.nameJ} = ${c.nameJ};
	}

	</#if></#list>
	@Override
	public String toString() {
		return "${class_name}DTO [" + <#list table_column as c>"<#if (c_index>=1)>, </#if>${c.nameJ}=" + <#if (c.name!="id")>${c.nameJ}<#else>getId()</#if> + </#list> "]";
	}
}
