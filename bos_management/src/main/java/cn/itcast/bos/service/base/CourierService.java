package cn.itcast.bos.service.base;

import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.Standard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * @author ChuanJing
 * @date 2017年9月4日 下午4:11:30
 * @version 1.0

	快递员操作接口
 */
public interface CourierService {

	// 保存快递员
	public void save(Courier courier);

	// 分页查询
	public Page<Courier> findPageData(Specification<Courier> spec, Pageable pageable);
	public Page<Courier> findPageData(Pageable pageable);
	// 批量作废
	public void delBatch(String[] idArray);

	//还原
	public void restoreBatch(String[] idArray);


    List<Object[]> findBygroup();
}
