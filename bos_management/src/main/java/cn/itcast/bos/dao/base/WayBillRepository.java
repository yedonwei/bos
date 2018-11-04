package cn.itcast.bos.dao.base;

import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.domain.base.WayBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WayBillRepository extends JpaRepository<WayBill, Integer>, JpaSpecificationExecutor<WayBill> {


    WayBill findByWayBillNum(String wayBillNum);
}
