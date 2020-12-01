package com.cmcc.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Department {

	private String NAME;			//名称
	private String NAME_EN;			//英文名称
	private String BIANMA;			//编码
	private String PARENT_ID;		//上级ID
	private String HEADMAN;			//负责人
	private String TEL;				//电话
	private String FUNCTIONS;		//部门职能
	private String BZ;				//备注
	private	String ADDRESS;			//地址
	private String DEPARTMENT_ID;	//主键
	private String target;
	private Department department;
	private List<Department> subDepartment;
	private boolean hasDepartment = false;
	private String treeurl;
	private String icon;
	
}