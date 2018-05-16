package gkfire.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.Part;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ImportUtils {

    public static enum State {
        LOAD("Subiendo archivo ...", false),
        READING("Validando el contenido ...", false),
        PROCESS("Guardando registros...", false),
        ERROR("FINALIZADO", true),
        SUCCESS("FINALIZADO", true);

        private final String description;
        private final boolean terminate;

        private State(String description, boolean terminate) {
            this.description = description;
            this.terminate = terminate;
        }

        public String getDescription() {
            return this.description;
        }

        public boolean isTerminate() {
            return this.terminate;
        }
    }

    public static Object readFile(Part file) throws IOException, InvalidFormatException {
        String ct = file.getContentType();
        boolean isXML = true;
        Object o;
        if (isXML) {
            o = new XSSFWorkbook(file.getInputStream());
        } else {
            o = new HSSFWorkbook(file.getInputStream());
        }
        return o;
    }

    public static Integer countRows(Object file) {
        Workbook workbook = (Workbook) file;
        return Integer.valueOf(workbook.getSheetAt(0).getLastRowNum());
    }

    public static Object[] readRow(Object file, int rowNumber, int columns) throws Exception {
        Workbook workbook = (Workbook) file;

        Row row = workbook.getSheetAt(0).getRow(rowNumber);
        Iterator<Cell> iterator = row.iterator();
        List<Object> data = new ArrayList();
        for (int i = 0; i < columns; i++) {
            Cell cell = row.getCell(i);
            if (cell == null) {
                data.add("");
                data.set(data.size() - 1, null);
            } else {
                switch (cell.getCellType()) {
                    case 3:
                        data.add("");
                        data.set(data.size() - 1, null);
                        break;
                    case 4:
                        data.add(Boolean.valueOf(cell.getBooleanCellValue()));
                        break;
                    case 5:
                        throw new Exception("Se ha encontrado un error en el contenido");
                    case 2:
                        throw new Exception("Las formulas no estan soportadas en la importacion");
                    case 1:
                        data.add(cell.getStringCellValue());
                        break;
                    case 0:
                        if (DateUtil.isCellDateFormatted(cell)) {
                            data.add(cell.getDateCellValue());
                        } else {
                            data.add(Double.valueOf(cell.getNumericCellValue()));
                        }
                        break;
                    default:
                        throw new Exception("");
                }
            }
        }
        return data.toArray();
    }

    public static Object[] readRow(Object file, int rowNumber, Class[] classes) throws Exception {
        Workbook workbook = (Workbook) file;
        Row row = workbook.getSheetAt(0).getRow(rowNumber);
        Iterator<Cell> iterator = row.iterator();
        List<Object> data = new ArrayList();
        for (int i = 0; i < classes.length; i++) {
            Cell cell = row.getCell(i);
            if (cell == null) {
                data.add("");
                data.set(data.size() - 1, null);
            } else {
                Class localClass = classes[i];
            }
        }
        return data.toArray();
    }
}
