package gkfire.report.util;

import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

public enum ReportContentType {
    DOCX("application/vnd.openxmlformats-officedocument.wordprocessingml.document", new JRDocxExporter()),
    XLSX("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", new JRXlsxExporter()),
    PDF("application/pdf", new JRPdfExporter()),
    TXT("text/plain", new JRTextExporter()),
    SYBASE("", null),
    HTML("text/html", new JRHtmlExporter());

    private final String mimeType;
    private final Object exporter;

    private ReportContentType(String mimeType, Object exporter) {
        this.mimeType = mimeType;
        this.exporter = exporter;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public Object getExporter() {
        return this.exporter;
    }
}
