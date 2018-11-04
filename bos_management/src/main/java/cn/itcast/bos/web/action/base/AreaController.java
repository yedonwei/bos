package cn.itcast.bos.web.action.base;

import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.service.base.AreaService;
import cn.itcast.bos.utils.PinYin4jUtils;
import com.lowagie.text.Row;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.lowagie.text.Element.ROW;

@Controller
public class AreaController {

    @Autowired
    private AreaService areaService;

    @RequestMapping("area_save")
    public String save(Area area,String areaInfo){
        String id = area.getId();
        //判断id是否为空
        if(org.apache.commons.lang.StringUtils.isEmpty(id)){
            area.setId(UUID.randomUUID().toString());
        }
        //辽宁省/丹东市/元宝区
        String[] split = areaInfo.split("/");
        area.setProvince(split[0]);
        area.setCity(split[1]);
        area.setDistrict(split[2]);
        areaService.save(area);
        return "redirect:./pages/base/area.html";
    }

    @RequestMapping("area_pageQuery")
    @ResponseBody
    public Map<String, Object> pageQuery(Integer page, Integer rows) {
        //从前端页面床过来的分页数据
        System.out.println("page:"+page+"rows:"+rows);
        Pageable pageable=new PageRequest(page-1,rows);
        Page<Area> pageData = areaService.findPageData(pageable);
        //返回数据,创建map集合
        Map<String,Object> map= new HashMap<>();
        map.put("total",pageData.getTotalElements());
        map.put("rows",pageData.getContent());
        return map;
    }

    @RequestMapping("area_upload")
    @ResponseBody
    public String upload(MultipartFile file){
        ArrayList<Area> list = new ArrayList<>();
        try {
            /*Workbook hssfWorkbook = null;
            String name = file.getOriginalFilename();
            String[] split = name.split("\\.");
            if(split[0].equals("xls")){
                hssfWorkbook = new HSSFWorkbook(file.getInputStream());
            }else{
                hssfWorkbook = new XSSFWorkbook(file.getInputStream());
            }*/
            //1.加载Exclel文件对象
            HSSFWorkbook hssfWorkbook= new HSSFWorkbook(file.getInputStream());
            //2.读取一个sheet
            HSSFSheet sheetAt = hssfWorkbook.getSheetAt(0);
            //3.读取sheet中的每一行数据
            for(org.apache.poi.ss.usermodel.Row row: sheetAt){
                //第一行跳过
                if(row.getRowNum() == 0){
                    continue;
                }
                //跳过空行
                if(row.getCell(0) == null || StringUtils.isBlank(row.getCell(0).getStringCellValue())){
                    continue;
                }
                Area area = new Area();
                area.setId(row.getCell(0).getStringCellValue());
                area.setProvince(row.getCell(1).getStringCellValue());
                area.setCity(row.getCell(2).getStringCellValue());
                area.setDistrict(row.getCell(3).getStringCellValue());
                area.setPostalCode(row.getCell(4).getStringCellValue());

                String province = area.getProvince(); //获得省
                String city = area.getCity();  //获得市
                String district = area.getDistrict();  //获得区
                province = province.substring(0, province.length() - 1);//把省去掉
                city = city.substring(0, city.length() - 1);//把市去掉
                district= district.substring(0,district.length()-1);//把区去掉
                String[] headByString = PinYin4jUtils.getHeadByString(province + city + district);
                StringBuffer stringBuffer= new StringBuffer();
                for (String s: headByString){
                    stringBuffer.append(s);
                }
                area.setPcd(stringBuffer.toString());
                //城市拼音
                String s = PinYin4jUtils.hanziToPinyin(city,"");
                area.setPinyinCity(s);
                list.add(area);
            }
            //调用业务层
            areaService.save(list);
            return "0";
        } catch (Exception e) {
            e.printStackTrace();
            return "1";
        }

    }
}
