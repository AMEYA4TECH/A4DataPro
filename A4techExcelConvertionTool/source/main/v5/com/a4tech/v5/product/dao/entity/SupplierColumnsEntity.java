package com.a4tech.v5.product.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="supplier_file_column_count1")
public class SupplierColumnsEntity {

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(name="ASI_NO")
	private String  asiNumber;
	
	@Column(name="COLUMNS_COUNT")
	private String  columnCount;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAsiNumber() {
		return asiNumber;
	}

	public void setAsiNumber(String asiNumber) {
		this.asiNumber = asiNumber;
	}

	public String getColumnCount() {
		return columnCount;
	}

	public void setColumnCount(String columnCount) {
		this.columnCount = columnCount;
	}
	
}
