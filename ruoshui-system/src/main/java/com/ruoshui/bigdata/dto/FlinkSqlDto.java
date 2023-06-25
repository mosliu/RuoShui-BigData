package com.ruoshui.bigdata.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 *
 * @author fei
 * @date 2022-01-27
 * 
 **/
@Data
public class FlinkSqlDto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "SQL 字符串必传！")
	private String sqlStr;

}
