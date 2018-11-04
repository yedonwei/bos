package cn.itcast.bos.domain.base;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "T_Arer")
public class Area {
    @Id
    @Column(name = "C_ID")
    private String id;

//    @Id
//    @GeneratedValue(generator="system_uuid")
//    @GenericGenerator(name="system_uuid",strategy= "UUID")
//    private String id;
    private String  province; //省
    private String  city;  //市
    private String  district;  //区
    private String  pinyinCity; //拼音市
    private String  pcd;  //省市区首字母
    private String  postalCode; //邮政编码

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPinyinCity() {
        return pinyinCity;
    }

    public void setPinyinCity(String pinyinCity) {
        this.pinyinCity = pinyinCity;
    }

    public String getPcd() {
        return pcd;
    }

    public void setPcd(String pcd) {
        this.pcd = pcd;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        return "Area{" +
                "id='" + id + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", pinyinCity='" + pinyinCity + '\'' +
                ", pcd='" + pcd + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}
