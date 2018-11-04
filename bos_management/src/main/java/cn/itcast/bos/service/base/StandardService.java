package cn.itcast.bos.service.base;

import cn.itcast.bos.domain.base.Standard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StandardService {

	public void save(Standard standard);

	public Page<Standard> findPageData(Pageable pageable);

	public List<Standard> findAll();
}
