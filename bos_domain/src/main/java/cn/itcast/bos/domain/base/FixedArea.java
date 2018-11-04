package cn.itcast.bos.domain.base;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="T_FixedArea")
public class FixedArea {
    @Id
    @GeneratedValue
    private Integer id ;
    private String fixedAreaNum; //定区编号
    private String fixedAreaName; //定区名称
    private String fixedAreaLeader; //负责人
    private String telephone;  //联系电话
    private String company; //所属公司

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFixedAreaNum() {
        return fixedAreaNum;
    }

    public void setFixedAreaNum(String fixedAreaNum) {
        this.fixedAreaNum = fixedAreaNum;
    }

    public String getFixedAreaName() {
        return fixedAreaName;
    }

    public void setFixedAreaName(String fixedAreaName) {
        this.fixedAreaName = fixedAreaName;
    }

    public String getFixedAreaLeader() {
        return fixedAreaLeader;
    }

    public void setFixedAreaLeader(String fixedAreaLeader) {
        this.fixedAreaLeader = fixedAreaLeader;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "FixedArea{" +
                "id=" + id +
                ", fixedAreaNum='" + fixedAreaNum + '\'' +
                ", fixedAreaName='" + fixedAreaName + '\'' +
                ", fixedAreaLeader='" + fixedAreaLeader + '\'' +
                ", telephone='" + telephone + '\'' +
                ", company='" + company + '\'' +
                '}';
    }
}
