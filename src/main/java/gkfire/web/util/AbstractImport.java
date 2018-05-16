package gkfire.web.util;

import gkfire.report.util.ReportContentType;
import gkfire.util.ImportUtils;
import gkfire.util.ImportUtils.State;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.servlet.http.Part;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

public abstract class AbstractImport<T>
        implements Serializable {

    protected ExecutorService executor;
    protected List<Object[]> importObjects;
    private Integer currentPlots;
    private Integer totalPlots;
    protected Part file;
    protected Object fileObject;
    protected Integer percentLoad;
    protected Integer totalRecords;
    protected ImportUtils.State state;
    protected boolean seeDetail;
    protected Map<Integer, String> log;
    protected Map<Integer, String> logError;

    public void refresh() {
        this.file = null;
        this.totalRecords = 0;
        this.percentLoad = 0;
        this.currentPlots = 0;
        this.totalPlots = Integer.MAX_VALUE;
        this.log = new HashMap() {
            @Override
            public Object put(Object key, Object value) {
                AbstractImport.this.percentLoad = (keySet().size() + 1 + AbstractImport.this.logError.keySet().size()) * 100 / AbstractImport.this.totalRecords;
                return super.put(key, value);
            }

        };
        this.logError = new HashMap() {
            @Override
            public Object put(Object key, Object value) {
                AbstractImport.this.percentLoad = (keySet().size() + 1 + AbstractImport.this.log.keySet().size()) * 100 / AbstractImport.this.totalRecords;
                return super.put(key, value);
            }

        };
        this.importObjects = new ArrayList();
        this.state = ImportUtils.State.LOAD;
        this.seeDetail = false;
    }

    public void begin() {
        try {
            this.fileObject = ImportUtils.readFile(getFile());
        } catch (IOException | InvalidFormatException e) {
            this.state = ImportUtils.State.ERROR;
            return;
        }
        this.state = ImportUtils.State.READING;
        this.currentPlots = 0;
        this.totalRecords = ImportUtils.countRows(this.fileObject);
        this.totalPlots = this.totalRecords / 200;
        if (this.totalRecords % 200 != 0) {
            this.totalPlots = this.totalPlots + 1;
        }
        for (int i = 0; i < this.totalPlots; i++) {
            final int j = i;
            this.executor = Executors.newSingleThreadExecutor();
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    AbstractImport.this.beginThread(200 * j + 1, 200);
                    AbstractImport.this.setCurrentPlots(AbstractImport.this.getCurrentPlots() + 1);
                }
            };
            this.executor.submit(task);
        }
    }

    public StreamedContent downloadLog(boolean isError) throws Exception {
        Path file = Files.createTempFile("", "txt", new FileAttribute[0]);
        Writer output = new BufferedWriter(new FileWriter(file.toFile()));
        Map<Integer, String> logDisplay = isError ? this.logError : this.log;
        if (isError) {
            List<Integer> keys = new ArrayList(logDisplay.keySet());
            Collections.sort(keys);

            for (Integer key : keys) {
                String sRow = "Fila " + (key + 1) + " : " + (String) logDisplay.get(key);
                sRow = sRow + "\r\n";
                output.write(sRow);
                output.flush();
            }
        }
        output.close();
        return new DefaultStreamedContent(new FileInputStream(file.toFile()), ReportContentType.TXT.getMimeType(), "Log - " + (!isError ? "Exito" : "Error") + " - " + System.currentTimeMillis() + "." + ReportContentType.TXT.name().toLowerCase());
    }

    public abstract void beginThread(int paramInt1, int paramInt2);

    public Part getFile() {
        return this.file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public Integer getPercentLoad() {
        return this.percentLoad;
    }

    public void setPercentLoad(Integer percentLoad) {
        this.percentLoad = percentLoad;
    }

    public ImportUtils.State getState() {
        return this.state;
    }

    public void setState(ImportUtils.State state) {
        this.state = state;
    }

    public Integer getTotalRecords() {
        return this.totalRecords;
    }

    public void setTotalRecords(Integer totalRecords) {
        this.totalRecords = totalRecords;
    }

    public List<Object[]> getImportObjects() {
        return this.importObjects;
    }

    public void setImportObjects(List<Object[]> importObjects) {
        this.importObjects = importObjects;
    }

    public boolean isSeeDetail() {
        return this.seeDetail;
    }

    public void setSeeDetail(boolean seeDetail) {
        this.seeDetail = seeDetail;
    }

    public Object getFileObject() {
        return this.fileObject;
    }

    public void setFileObject(Object fileObject) {
        this.fileObject = fileObject;
    }

    public Map<Integer, String> getLog() {
        return this.log;
    }

    public void setLog(Map<Integer, String> log) {
        this.log = log;
    }

    public Map<Integer, String> getLogError() {
        return this.logError;
    }

    public void setLogError(Map<Integer, String> logError) {
        this.logError = logError;
    }

    public Integer getCurrentPlots() {
        return this.currentPlots;
    }

    public void setCurrentPlots(Integer currentPlots) {
        this.currentPlots = currentPlots;
    }

    public Integer getTotalPlots() {
        return this.totalPlots;
    }

    public void setTotalPlots(Integer totalPlots) {
        this.totalPlots = totalPlots;
    }
}
