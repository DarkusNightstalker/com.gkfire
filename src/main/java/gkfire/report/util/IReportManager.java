package gkfire.report.util;

import gkfire.web.util.BeanUtil;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.sql.Connection;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;

@ManagedBean
@SessionScoped
public class IReportManager
        implements Serializable {

    private JasperPrint jasperPrint;

    public IReportManager(JasperPrint jasperPrint) {
        this.jasperPrint = jasperPrint;
    }

    public JasperPrint getJasperPrint() {
        return this.jasperPrint;
    }

    public void setJasperPrint(JasperPrint jasperPrint) {
        this.jasperPrint = jasperPrint;
    }

    public void setJasperPrint(String filePathWeb, Map<String, Object> param, JRBeanCollectionDataSource collection)
            throws JRException {
        File jasper = new File(BeanUtil.getRealPath(filePathWeb));
        this.jasperPrint = JasperFillManager.fillReport(jasper.getPath(), param, collection);
    }

    public void setJasperPrint(String filePathWeb, Map<String, Object> param, Connection connection)
            throws JRException {
        File jasper = new File(BeanUtil.getRealPath(filePathWeb));
        this.jasperPrint = JasperFillManager.fillReport(jasper.getPath(), param, connection);
    }

    public void exportarPDF(String outputName)
            throws JRException, IOException {
        if (!outputName.endsWith(".pdf")) {
            outputName = outputName + ".pdf";
        }

        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        JRExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, this.jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, path + "view\\pages\\report\\file\\" + outputName);
        exporter.exportReport();
        File ficheroDoc = new File(path + "view\\pages\\report\\file\\" + outputName);
        FacesContext ctx = FacesContext.getCurrentInstance();
        FileInputStream fis = new FileInputStream(ficheroDoc);
        byte[] bytes = new byte['Ϩ'];
        int read = 0;

        if (!ctx.getResponseComplete()) {
            String fileName = outputName;
            String contentType = "application/pdf";

            HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();

            response.setContentType(contentType);

            response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");

            ServletOutputStream out = response.getOutputStream();

            while ((read = fis.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            out.flush();
            out.close();
            System.out.println("\nDescargado\n");
            ctx.responseComplete();
            out = null;
            System.gc();
            clearFile(path + "view\\pages\\report\\file\\");
        }
    }

    private void clearFile(String strFolderSource) {
        String sourcePath = strFolderSource;
        File prueba = new File(sourcePath);
        File[] ficheros = prueba.listFiles();
        File f = null;
        if (prueba.exists()) {
            for (int x = 0; x < ficheros.length; x++) {
                f = new File(ficheros[x].toString());
                System.out.println("Archivo:" + ficheros[x].getName());
                f.delete();
            }
        } else {
            System.out.println("No existe el directorio");
        }
    }

    public void verPDF(ActionEvent actionEvent)
            throws Exception {
    }

    public void exportarExcel(String outputName)
            throws JRException, IOException {
        HttpServletResponse response = BeanUtil.getResponse();
        if (!outputName.endsWith(".xls")) {
            outputName = outputName + ".xls";
        }
        response.addHeader("Content-disposition", "attachment; filename=" + outputName);
        ServletOutputStream outStream = response.getOutputStream();

        JRXlsExporter exporter = new JRXlsExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, this.jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outStream);
        exporter.exportReport();

        outStream.flush();
        outStream.close();
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void exportarDOC(String outputName) throws JRException, IOException {
        HttpServletResponse response = BeanUtil.getResponse();
        if (!outputName.endsWith(".doc")) {
            outputName = outputName + ".doc";
        }
        response.addHeader("Content-disposition", "attachment; filename=" + outputName);
        ServletOutputStream outStream = response.getOutputStream();

        JRDocxExporter exporter = new JRDocxExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, this.jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outStream);
        exporter.exportReport();

        outStream.flush();
        outStream.close();
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void exportarPPT(String outputName) throws JRException, IOException {
        HttpServletResponse response = BeanUtil.getResponse();
        if (!outputName.endsWith(".ppt")) {
            outputName = outputName + ".ppt";
        }
        response.addHeader("Content-disposition", "attachment; filename=" + outputName);
        ServletOutputStream outStream = response.getOutputStream();

        JRPptxExporter exporter = new JRPptxExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, this.jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outStream);
        exporter.exportReport();

        outStream.flush();
        outStream.close();
        FacesContext.getCurrentInstance().responseComplete();
    }
}
