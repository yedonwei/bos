package cn.itcast.bos.domain.base;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @description:收派标准实体类
 */
@Entity
@Table(name = "T_STANDARD")
public class Standard implements Serializable {
	@Id
	@GeneratedValue
	@Column(name = "C_ID")
	private Integer id; // 主键
	
	@Column(name = "C_NAME")
	private String name; // 标准名称
	
	@Column(name = "C_MIN_WEIGHT")
	private Integer minWeight; // 最小重量
	
	@Column(name = "C_MAX_WEIGHT")
	private Integer maxWeight; // 最大重量
	
	@Column(name = "C_MIN_LENGTH")
	private Integer minLength; // 最小长度
	
	@Column(name = "C_MAX_LENGTH")
	private Integer maxLength; // 最大重量

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getMinWeight() {
		return minWeight;
	}

	public void setMinWeight(Integer minWeight) {
		this.minWeight = minWeight;
	}

	public Integer getMaxWeight() {
		return maxWeight;
	}

	public void setMaxWeight(Integer maxWeight) {
		this.maxWeight = maxWeight;
	}

	public Integer getMinLength() {
		return minLength;
	}

	public void setMinLength(Integer minLength) {
		this.minLength = minLength;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}


}
