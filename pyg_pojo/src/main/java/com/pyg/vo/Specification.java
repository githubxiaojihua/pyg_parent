package com.pyg.vo;

import java.io.Serializable;
import java.util.List;

import com.pyg.pojo.TbSpecification;
import com.pyg.pojo.TbSpecificationOption;

/**
 * 用于新增规格以及规格属性的包装类
 * 同时包含规格，以及规格属性列表
 */
public class Specification implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8082099291111448436L;

	//包装规格对象
	private TbSpecification tbSpecification;
	
	//包装规格列表
	private List<TbSpecificationOption> specificationList;

	public TbSpecification getTbSpecification() {
		return tbSpecification;
	}

	public void setTbSpecification(TbSpecification tbSpecification) {
		this.tbSpecification = tbSpecification;
	}

	public List<TbSpecificationOption> getSpecificationList() {
		return specificationList;
	}

	public void setSpecificationList(List<TbSpecificationOption> specificationList) {
		this.specificationList = specificationList;
	}
	
	
	

}
