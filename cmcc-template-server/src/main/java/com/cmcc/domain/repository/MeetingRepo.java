package com.cmcc.domain.repository;

import com.cmcc.domain.model.Department;

import java.util.List;

public interface MeetingRepo {


	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @return
	 * @throws Exception
	 */
	public List<Department> listAllDepartment(String parentId) throws Exception;
	

	
}

