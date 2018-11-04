package cn.itcast.bos.dao.search;

import cn.itcast.bos.domain.base.WayBill;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface WayBillSearchRepositry extends ElasticsearchRepository<WayBill, Integer> {

}
