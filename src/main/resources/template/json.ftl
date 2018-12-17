
//-----------------------------json-start---------------------------------//
{
	<#assign index=0 />
	<#list columnTypes?values as columnType>
	<#if index==columnTypes?values?size-1>
	"${columnType.label}":"" //${columnType.comment}
	<#else>
	"${columnType.label}":"", //${columnType.comment}
	</#if>
	<#assign index+=1 />
	</#list>
}
//-----------------------------json-end---------------------------------//