package cn.itcast.bos.service.base;

import cn.itcast.bos.domain.base.WayBill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WayBillService {

    void save(WayBill wayBill);

//    Page<WayBill> findPageData(Pageable pageable);

    WayBill findByWayBillNum(String wayBillNum);

    Page<WayBill> findPageData(Pageable pageable, WayBill wayBill);

    List<WayBill> findWayBills(WayBill wayBill);
}
