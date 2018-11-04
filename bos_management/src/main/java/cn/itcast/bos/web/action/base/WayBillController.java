package cn.itcast.bos.web.action.base;

import cn.itcast.bos.domain.base.WayBill;
import cn.itcast.bos.service.base.WayBillService;
import cn.itcast.bos.utils.FileUtils;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class WayBillController {

    @Autowired
    private WayBillService wayBillService;


    @RequestMapping("WayBillController_save")
    @ResponseBody
    public Map<String,Object> save(WayBill wayBill) {
        Map<String,Object> map = new HashMap<>();
        try {
            wayBillService.save(wayBill);
            map.put("success",true);
            return map;
        }catch (Exception e){
            e.printStackTrace();
            map.put("sucsess",false);
            return map;
        }
    }

//    @RequestMapping("waybill_pageQuery")
//    @ResponseBody
//    public Map<String, Object> pageQuery(Integer page, Integer rows) {
//        //从前端页面床过来的分页数据
//        System.out.println("page:"+page+"rows:"+rows);
//        Pageable pageable=new PageRequest(page-1,rows);
//        Page<WayBill> pageData = wayBillService.findPageData(pageable);
//        //返回数据,创建map集合
//        Map<String,Object> map= new HashMap<>();
//        map.put("total",pageData.getTotalElements());
//        map.put("rows",pageData.getContent());
//        return map;
//    }

    /**
     * 条件查询
     * @param wayBill
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("waybill_pageQuery")
    @ResponseBody
    public Map<String, Object> pageQuery(Integer page, Integer rows,WayBill wayBill) {
        //从前端页面床过来的分页数据
        System.out.println("page:"+page+"rows:"+rows);
        Pageable pageable=new PageRequest(page-1,rows);
        Page<WayBill> pageData = wayBillService.findPageData(pageable, wayBill);
        //返回数据,创建map集合
        Map<String,Object> map= new HashMap<>();
        map.put("total",pageData.getTotalElements());
        map.put("rows",pageData.getContent());
        return map;
    }

    /***
     * 根据订单编号回显
     * @param wayBill
     * @return
     */
    @RequestMapping("/waybill_findByWayBillNum")
    @ResponseBody
    public Map<String,Object> findBywayBillNum(WayBill wayBill) {
        try {
            WayBill BillNumData =  wayBillService.findByWayBillNum(wayBill.getWayBillNum());
            System.out.println(wayBill.getWayBillNum());
            Map<String,Object> map = new HashMap<>();
            map.put("BillNumData",BillNumData);
            map.put("success",true);
            return map;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("/report_exportXls")
    public void exportXls(WayBill wayBill, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //查询出满足条件的
        List<WayBill> wayBills = wayBillService.findWayBills(wayBill);
        //生成excel文件
        HSSFWorkbook hssfWorkbook =new HSSFWorkbook();
        //设置下载文件名称
        HSSFSheet sheet = hssfWorkbook.createSheet("运单报表");
        //设置头第一行
        HSSFRow headRow = sheet.createRow(0);
        headRow.createCell(0).setCellValue("运单号");
        headRow.createCell(1).setCellValue("寄件人");
        headRow.createCell(2).setCellValue("寄件人电话");
        headRow.createCell(3).setCellValue("寄件人地址");
        headRow.createCell(4).setCellValue("收件人");
        headRow.createCell(5).setCellValue("收件人电话");
        headRow.createCell(6).setCellValue("收件人地址");
        // 表格数据
        for (WayBill way : wayBills) {
            HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);    //在最后一行下面再创建一行
            dataRow.createCell(0).setCellValue(way.getWayBillNum());    //运单号
            dataRow.createCell(1).setCellValue(way.getSendName());        //寄件人
            dataRow.createCell(2).setCellValue(way.getSendMobile());    //寄件人电话
            dataRow.createCell(3).setCellValue(way.getSendAddress());    //寄件人地址
            dataRow.createCell(4).setCellValue(way.getRecName());        //收件人
            dataRow.createCell(5).setCellValue(way.getRecMobile());        //收件人电话
            dataRow.createCell(6).setCellValue(way.getRecAddress());    //收件人地址
        }
        //设置样式
        HSSFCellStyle cellStyle = hssfWorkbook.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); //设置水平样式
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//设置垂直样式
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);//设置底部单元格边框
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//设置左边单元格边框
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//设置右边单元格边框
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//设置右边单元格边框
        for(Row row : sheet){
            for(Cell cell:row ){
                    cell.setCellStyle(cellStyle);
            }
        }
        // -------------------下载导出-------------------
        // 设置头信息
        response.setContentType("application/vnd.ms-excel");
        String filename = "运单数据.xls";
        String agent = request.getHeader("user-agent");
        filename = FileUtils.encodeDownloadFilename(filename, agent);
        response.setHeader("Content-Disposition", "attachment;filename=" + filename);
        //获得输出流，输出文件
        ServletOutputStream outputStream = response.getOutputStream();
        hssfWorkbook.write(outputStream);
        // 关闭
        hssfWorkbook.close();
    }


    @RequestMapping("/report_exportJasperPdf")
    public void exportJasperPdf(WayBill wayBill, HttpServletRequest request, HttpServletResponse response) throws Exception{
        // -------------------查询出 满足当前条件 结果数据-------------------
        List<WayBill> wayBills = wayBillService.findWayBills(wayBill);
        // -------------------下载导出-------------------
        // 设置头信息
        response.setContentType("application/pdf");
        String filename = "运单数据.pdf";
        String agent = request.getHeader("user-agent");
        filename = FileUtils.encodeDownloadFilename(filename, agent);
        response.setHeader("Content-Disposition", "attachment;filename=" + filename);

        // -------------------根据 jasperReport模板 生成pdf-------------------
        // 根据 jasperReport模板 生成pdf
        // 获得模板文件
        String jrxml = request.getSession().getServletContext().getRealPath(
                "/WEB-INF/jasper/waybill.jrxml");
        // 根据模板文件得到报表对象(空内容)
        JasperReport report = JasperCompileManager.compileReport(jrxml);
        // 设置Parameter参数
        Map<String, Object> paramerters = new HashMap<>();
        paramerters.put("company", "联邦快递"); //这里只是举个例子,实际开发时可以显示时间,部门等各种信息
        paramerters.put("createDate", "生成日期: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        paramerters.put("sendCity", "发货地: " + wayBill.getSendAddress());
        Object recCity = paramerters.put("recCity", "收货地: " + wayBill.getRecAddress());
        // 得到内容输出对象,参数1表示把内容输出给哪个报表,参数2表示参数信息, 参数3表示数据内容
        JasperPrint jasperPrint = JasperFillManager.fillReport(report,
                paramerters, new JRBeanCollectionDataSource(wayBills)); //数据绑定
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
        exporter.exportReport();// 导出
        response.getOutputStream().close();
    }
}
