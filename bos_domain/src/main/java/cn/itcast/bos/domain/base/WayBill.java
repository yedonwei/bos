package cn.itcast.bos.domain.base;

import cn.itcast.bos.domain.base.Area;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.*;

/**
 * @description:运单实体类
 */
@Entity
@Table(name = "T_WAY_BILL")
@Document(indexName = "bos", type = "waybill")
public class WayBill {

	/*
	 * 注解作用说明： 
		@Field(index = FieldIndex.not_analyzed, store = true, type = FieldType.Long) 
			index： 
				FieldIndex.not_analyzed：不会分词，只能根据原词索引Field; 
				FieldIndex.analyzed：根据分词器分词，可以根据原词和分词后的词条索引Field; 
				FieldIndex.no：该字段不会被索引，查不到;
			store：es是否会存储这个Field的内容;
			type：es按照什么数据类型解析这个Field;
	 */
	@Id
	@GeneratedValue
	@Column(name = "C_ID")
	@org.springframework.data.annotation.Id
	@Field(index = FieldIndex.not_analyzed, store = true, type = FieldType.Integer)
	private Integer id;
	
	@Column(name = "C_WAY_BILL_NUM", unique = true)
	@Field(index = FieldIndex.not_analyzed, store = true, type = FieldType.String)
	private String wayBillNum; // 运单编号

	@Column(name = "C_SEND_NAME")
	@Field(index = FieldIndex.analyzed, analyzer = "ik", searchAnalyzer = "ik", store = true, type = FieldType.String)
	private String sendName; // 寄件人姓名
	
	@Column(name = "C_SEND_MOBILE")
	@Field(index = FieldIndex.analyzed, analyzer = "ik", searchAnalyzer = "ik", store = true, type = FieldType.String)
	private String sendMobile;// 寄件人电话
	
	@Column(name = "C_SEND_COMPANY")
	@Field(index = FieldIndex.analyzed, analyzer = "ik", searchAnalyzer = "ik", store = true, type = FieldType.String)
	private String sendCompany;// 寄件人公司
	
	@OneToOne
	@JoinColumn(name = "C_SEND_AREA_ID")
	private Area sendArea; // 寄件人省市区信息
	
	@Column(name = "C_SEND_ADDRESS")
	@Field(index = FieldIndex.analyzed, analyzer = "ik", searchAnalyzer = "ik", store = true, type = FieldType.String)
	private String sendAddress;// 寄件人详细地址信息

	@Column(name = "C_REC_NAME")
	@Field(index = FieldIndex.analyzed, analyzer = "ik", searchAnalyzer = "ik", store = true, type = FieldType.String)
	private String recName;// 收件人姓名
	
	@Column(name = "C_REC_MOBILE")
	@Field(index = FieldIndex.analyzed, analyzer = "ik", searchAnalyzer = "ik", store = true, type = FieldType.String)
	private String recMobile;// 收件人电话
	
	@Column(name = "C_REC_COMPANY")
	@Field(index = FieldIndex.analyzed, analyzer = "ik", searchAnalyzer = "ik", store = true, type = FieldType.String)
	private String recCompany;// 收件人公司
	
	@OneToOne
	@JoinColumn(name = "C_REC_AREA_ID")
	private Area recArea; // 收件人省市区信息
	
	@Column(name = "C_REC_ADDRESS")
	@Field(index = FieldIndex.analyzed, analyzer = "ik", searchAnalyzer = "ik", store = true, type = FieldType.String)
	private String recAddress;// 收件人详细地址信息

	@Column(name = "C_SEND_PRO_NUM")
//	@Field(index = FieldIndex.analyzed, analyzer = "ik", searchAnalyzer = "ik", store = true, type = FieldType.String)
	@Field(index = FieldIndex.not_analyzed, store = true, type = FieldType.String)
	private String sendProNum; // 快递产品类型编号：速运当日、速运次日、速运隔日
	
	@Column(name = "C_GOODS_TYPE")
	@Field(index = FieldIndex.analyzed, analyzer = "ik", searchAnalyzer = "ik", store = true, type = FieldType.String)
	private String goodsType;// 托寄物类型：文件、衣服 、食品、电子商品
	
	@Column(name = "C_PAY_TYPE_NUM")
	@Field(index = FieldIndex.analyzed, analyzer = "ik", searchAnalyzer = "ik", store = true, type = FieldType.String)
	private String payTypeNum;// 支付类型编号：寄付日结、寄付月结、到付
	
	@Column(name = "C_WEIGHT")
	@Field(index = FieldIndex.no, store = true, type = FieldType.String)
	private Double weight;// 托寄物重量
	
	@Column(name = "C_REMARK")
	@Field(index = FieldIndex.analyzed, analyzer = "ik", searchAnalyzer = "ik", store = true, type = FieldType.String)
	private String remark; // 备注
	
	@Column(name = "C_NUM")
	@Field(index = FieldIndex.no, store = true, type = FieldType.String)
	private Integer num; // 原件数

	@Column(name = "C_ARRIVE_CITY")
	@Field(index = FieldIndex.analyzed, analyzer = "ik", searchAnalyzer = "ik", store = true, type = FieldType.String)
	private String arriveCity; // 到达地

	@Column(name = "C_FEEITEMNUM")
	@Field(index = FieldIndex.no, store = true, type = FieldType.String)
	private Integer feeitemnum; // 实际件数
	
	@Column(name = "C_ACTLWEIT")
	@Field(index = FieldIndex.no, store = true, type = FieldType.String)
	private Double actlweit; // 实际重量
	
	@Column(name = "C_VOL")
	@Field(index = FieldIndex.no, store = true, type = FieldType.String)
	private String vol; // 体积 输入格式为1*1*1*1，表示长*宽*高*数量
	
	@Column(name = "C_FLOADREQR")
	@Field(index = FieldIndex.no, store = true, type = FieldType.String)
	private String floadreqr; // 配载要求 (比如录入1=无，2=禁航，4=禁航空铁路)

	/*
	 * 运单状态： 1 待发货、 2 派送中、3 已签收、4 异常
	 */
	@Column(name = "C_SIGN_STATUS")
	@Field(index = FieldIndex.not_analyzed, store = true, type = FieldType.String)
	private Integer signStatus; // 签收状态

	public String getArriveCity() {
		return arriveCity;
	}

	public void setArriveCity(String arriveCity) {
		this.arriveCity = arriveCity;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getWayBillNum() {
		return wayBillNum;
	}

	public void setWayBillNum(String wayBillNum) {
		this.wayBillNum = wayBillNum;
	}

	public String getSendName() {
		return sendName;
	}

	public void setSendName(String sendName) {
		this.sendName = sendName;
	}

	public String getSendMobile() {
		return sendMobile;
	}

	public void setSendMobile(String sendMobile) {
		this.sendMobile = sendMobile;
	}

	public String getSendCompany() {
		return sendCompany;
	}

	public void setSendCompany(String sendCompany) {
		this.sendCompany = sendCompany;
	}

	@JsonIgnore//SpringMVC中在转换成json时，忽略这个字段
	public Area getSendArea() {
		return sendArea;
	}

	public void setSendArea(Area sendArea) {
		this.sendArea = sendArea;
	}

	public String getSendAddress() {
		return sendAddress;
	}

	public void setSendAddress(String sendAddress) {
		this.sendAddress = sendAddress;
	}

	public String getRecName() {
		return recName;
	}

	public void setRecName(String recName) {
		this.recName = recName;
	}

	public String getRecMobile() {
		return recMobile;
	}

	public void setRecMobile(String recMobile) {
		this.recMobile = recMobile;
	}

	public String getRecCompany() {
		return recCompany;
	}

	public void setRecCompany(String recCompany) {
		this.recCompany = recCompany;
	}

	@JsonIgnore//SpringMVC中在转换成json时，忽略这个字段
	public Area getRecArea() {
		return recArea;
	}

	public void setRecArea(Area recArea) {
		this.recArea = recArea;
	}

	public String getRecAddress() {
		return recAddress;
	}

	public void setRecAddress(String recAddress) {
		this.recAddress = recAddress;
	}

	public String getSendProNum() {
		return sendProNum;
	}

	public void setSendProNum(String sendProNum) {
		this.sendProNum = sendProNum;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public String getPayTypeNum() {
		return payTypeNum;
	}

	public void setPayTypeNum(String payTypeNum) {
		this.payTypeNum = payTypeNum;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getFeeitemnum() {
		return feeitemnum;
	}

	public void setFeeitemnum(Integer feeitemnum) {
		this.feeitemnum = feeitemnum;
	}

	public Double getActlweit() {
		return actlweit;
	}

	public void setActlweit(Double actlweit) {
		this.actlweit = actlweit;
	}

	public String getVol() {
		return vol;
	}

	public void setVol(String vol) {
		this.vol = vol;
	}

	public String getFloadreqr() {
		return floadreqr;
	}

	public void setFloadreqr(String floadreqr) {
		this.floadreqr = floadreqr;
	}

	public Integer getSignStatus() {
		return signStatus;
	}

	public void setSignStatus(Integer signStatus) {
		this.signStatus = signStatus;
	}

}