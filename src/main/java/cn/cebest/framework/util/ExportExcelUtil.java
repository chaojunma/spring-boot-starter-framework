package cn.cebest.framework.util;

import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import cn.cebest.framework.annotation.IgnoreExportField;
import lombok.extern.slf4j.Slf4j;

/**
 *  导出excel工具类
  * @author maming  
  * @date 2018年8月29日
 */

@Slf4j
public class ExportExcelUtil<T> {


    public HSSFWorkbook exportExcel(String title, String[] headers, Collection<T> dataset,
                                    HttpServletResponse response) throws Exception {
        if (dataset == null || dataset.size() <= 0) {
            log.error(String.format("%s无法导出excel,因为查询数据为空", title));
            return null;
        }
        // 声明一个工作簿
        HSSFWorkbook wb = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = wb.createSheet(title);
        // 设置表格默认列宽度
        sheet.setDefaultColumnWidth(15);
        // 生成单元格样式
        HSSFCellStyle headerStyle = wb.createCellStyle();
        headerStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.AQUA.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.LEFT);
        // 生成字体
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) 12);
        headerStyle.setFont(font);
        HSSFCellStyle contentStyle = wb.createCellStyle();
        contentStyle.setFont(font);
        // 生成标题行
        HSSFRow rowHeader = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = rowHeader.createCell(i);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(headers[i]);
        }
        // 写入数据
        Iterator<T> it = dataset.iterator();
        int row = 1;
        while (it.hasNext()) {
            HSSFRow currRow = sheet.createRow(row++);
            T t = (T) it.next();
            // 利用反射,根据javabean属性的先后顺序,动态调用getXxx()方法得到属性值
            // 请注意不要随意移动javabean属性的先后顺序
            Field[] fields = t.getClass().getDeclaredFields();
            int column = 0;
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                // 加个注解 排除掉不需要导出的属性
                if (field.isAnnotationPresent(IgnoreExportField.class)) {
                    continue;
                }
                String fieldName = field.getName();
                String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                Object value = t.getClass().getMethod(getMethodName).invoke(t);
                if (value == null) {
                    continue;
                }
                HSSFCell cell = currRow.createCell(column);
                // TODO 字段类型定制
                cell.setCellValue(value.toString());
                cell.setCellStyle(contentStyle);
                column++;
            }
        }
        // 设置reponse header
        response.setHeader("Content-Disposition",
            "attachment;filename=" + URLEncoder.encode(title + ".xls", "UTF-8"));
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        wb.write(out);
        out.flush();
        out.close();
        return wb;
    }
}
