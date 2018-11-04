package cn.itcast.bos.service.base.impl;

import cn.itcast.bos.dao.base.WayBillRepository;
import cn.itcast.bos.dao.search.WayBillSearchRepositry;
import cn.itcast.bos.domain.base.WayBill;
import cn.itcast.bos.service.base.WayBillService;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class WayBillServiceImpl implements WayBillService{

    @Autowired
    private WayBillRepository wayBillRepository;

    @Autowired
    private WayBillSearchRepositry wayBillSearchRepositry;

    @Override
    public void save(WayBill wayBill) {
        //判断是修改还是保存操作
        WayBill wayBillNum = wayBillRepository.findByWayBillNum(wayBill.getWayBillNum());
        if(wayBillNum == null){
            //运单不存在
            wayBill.setSignStatus(0);
            wayBillRepository.save(wayBill);
            wayBillSearchRepositry.save(wayBill);
        }else{
            //运单存在,更新数据库
            Integer id = wayBillNum.getId();
            BeanUtils.copyProperties(wayBill,wayBillNum);
            wayBillNum.setId(id);
            wayBillSearchRepositry.save(wayBillNum);
        }
    }

//    @Override
//    public Page<WayBill> findPageData(Pageable pageable) {
//        return wayBillRepository.findAll(pageable);
//    }

    @Override
    public WayBill findByWayBillNum(String wayBillNum) {
        return wayBillRepository.findByWayBillNum(wayBillNum);
    }

    @Override
    public Page<WayBill> findPageData(Pageable pageable, WayBill wayBill) {
        Page<WayBill> all =null;
        //判断wayBill查询条件是否存在
        if(StringUtils.isBlank(wayBill.getWayBillNum())
           && StringUtils.isBlank(wayBill.getSendAddress())
           && StringUtils.isBlank(wayBill.getRecAddress())
           && StringUtils.isBlank(wayBill.getSendProNum())
           && (wayBill.getSignStatus() == null || wayBill.getSignStatus() == 0)){
           //如果没有任何条件,就到数据库中查询
           all= wayBillRepository.findAll(pageable);
        }else {
            //如果有条件就用elasticseaarch搜索
            //1.创建多条件查询对象
            BoolQueryBuilder bool = new BoolQueryBuilder();
            //添加运单号查询
            if(StringUtils.isNotBlank(wayBill.getWayBillNum())){
                QueryBuilder queryBuilder = new TermQueryBuilder("wayBillNum",wayBill.getWayBillNum());
                bool.must(queryBuilder);
            }
            //发货地查询
            if(StringUtils.isNotBlank(wayBill.getSendAddress())){
                // 情况一： 输入"北" 是查询词条一部分， 使用模糊匹配词条查询
                QueryBuilder wildcardQuery = new WildcardQueryBuilder("sendAddress", "*" + wayBill.getSendAddress() + "*");
                // 情况二： 输入"北京市海淀区" 是多个词条组合，进行分词后 每个词条匹配查询
                QueryBuilder queryStringQueryBuilder = new QueryStringQueryBuilder(wayBill.getSendAddress())
                        .field("sendAddress")
                        .defaultOperator(QueryStringQueryBuilder.Operator.AND);
                // 两种情况取or关系
                BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
                boolQueryBuilder.should(wildcardQuery);
                boolQueryBuilder.should(queryStringQueryBuilder);
                bool.must(boolQueryBuilder);
            }
            // 收货地 模糊查询
            if (StringUtils.isNotBlank(wayBill.getRecAddress())) {
                QueryBuilder wildcardQuery = new WildcardQueryBuilder("recAddress", "*" + wayBill.getRecAddress() + "*");
                bool.must(wildcardQuery);
            }
            // 速运类型 等值查询
            if (StringUtils.isNotBlank(wayBill.getSendProNum())) {
                QueryBuilder termQuery = new TermQueryBuilder("sendProNum", wayBill.getSendProNum());
                bool.must(termQuery);
            }
            // 运单状态查询
            if (wayBill.getSignStatus() != null && wayBill.getSignStatus() != 0) {
                QueryBuilder termQuery = new TermQueryBuilder("signStatus", wayBill.getSignStatus());
                bool.must(termQuery);
            }
            SearchQuery searchQuery = new NativeSearchQuery(bool);
            searchQuery.setPageable(pageable); // 分页数据
            // 有条件查询 、查询索引库
            all= wayBillSearchRepositry.search(searchQuery);
        }
        return  all;

    }

    @Override
    public List<WayBill> findWayBills(WayBill wayBill) {

        //判断wayBill查询条件是否存在
        if(StringUtils.isBlank(wayBill.getWayBillNum())
                && StringUtils.isBlank(wayBill.getSendAddress())
                && StringUtils.isBlank(wayBill.getRecAddress())
                && StringUtils.isBlank(wayBill.getSendProNum())
                && (wayBill.getSignStatus() == null || wayBill.getSignStatus() == 0)){
            //如果没有任何条件,就到数据库中查询
             return  wayBillRepository.findAll();
        }else {
            //如果有条件就用elasticseaarch搜索
            //1.创建多条件查询对象
            BoolQueryBuilder bool = new BoolQueryBuilder();
            //添加运单号查询
            if(StringUtils.isNotBlank(wayBill.getWayBillNum())){
                QueryBuilder queryBuilder = new TermQueryBuilder("wayBillNum",wayBill.getWayBillNum());
                bool.must(queryBuilder);
            }
            //发货地查询
            if(StringUtils.isNotBlank(wayBill.getSendAddress())){
                // 情况一： 输入"北" 是查询词条一部分， 使用模糊匹配词条查询
                QueryBuilder wildcardQuery = new WildcardQueryBuilder("sendAddress", "*" + wayBill.getSendAddress() + "*");
                // 情况二： 输入"北京市海淀区" 是多个词条组合，进行分词后 每个词条匹配查询
                QueryBuilder queryStringQueryBuilder = new QueryStringQueryBuilder(wayBill.getSendAddress())
                        .field("sendAddress")
                        .defaultOperator(QueryStringQueryBuilder.Operator.AND);
                // 两种情况取or关系
                BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
                boolQueryBuilder.should(wildcardQuery);
                boolQueryBuilder.should(queryStringQueryBuilder);
                bool.must(boolQueryBuilder);
            }
            // 收货地 模糊查询
            if (StringUtils.isNotBlank(wayBill.getRecAddress())) {
                QueryBuilder wildcardQuery = new WildcardQueryBuilder("recAddress", "*" + wayBill.getRecAddress() + "*");
                bool.must(wildcardQuery);
            }
            // 速运类型 等值查询
            if (StringUtils.isNotBlank(wayBill.getSendProNum())) {
                QueryBuilder termQuery = new TermQueryBuilder("sendProNum", wayBill.getSendProNum());
                bool.must(termQuery);
            }
            // 运单状态查询
            if (wayBill.getSignStatus() != null && wayBill.getSignStatus() != 0) {
                QueryBuilder termQuery = new TermQueryBuilder("signStatus", wayBill.getSignStatus());
                bool.must(termQuery);
            }
            SearchQuery searchQuery = new NativeSearchQuery(bool);
            Pageable pageable = new PageRequest(0,10000);
            searchQuery.setPageable(pageable); // 分页数据
            // 有条件查询 、查询索引库
            return  wayBillSearchRepositry.search(searchQuery).getContent();
        }
    }

}
