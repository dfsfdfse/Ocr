package realcool.ocr.config;

public class OCRExecConfig {
    private Integer detLongSize = 960;
    private Integer execDet = 1;
    private Integer execCls = 1;
    private Integer execRec = 1;

    public Integer getDetLongSize() {
        return detLongSize;
    }

    public void setDetLongSize(int detLongSize) {
        this.detLongSize = detLongSize;
    }

    public Integer getExecDet() {
        return execDet;
    }

    public void setExecDet(int execDet) {
        this.execDet = execDet;
    }

    public Integer getExecCls() {
        return execCls;
    }

    public void setExecCls(int execCls) {
        this.execCls = execCls;
    }

    public Integer getExecRec() {
        return execRec;
    }

    public void setExecRec(int execRec) {
        this.execRec = execRec;
    }

    public void copyConfig(OCRExecConfig config){
        if (config.getExecCls() != null){
            this.execCls = config.getExecCls();
        }
        if (config.getDetLongSize() != null){
            this.detLongSize = config.getDetLongSize();
        }
        if (config.getExecRec() != null){
            this.execRec = config.getExecRec();
        }
        if (config.getExecDet() != null){
            this.execDet = config.getExecDet();
        }
    }
}
