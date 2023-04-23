package realcool.ocr.config;

public class OCREngineConfig {
    private String modelPath = "";

    private Boolean useOpencl = false;

    private Integer detLongSize = 960;

    private Integer cpuThreadNum = 1;

    private Boolean runDet = true;
    private Boolean runCls = true;
    private Boolean runRec = true;
    /**
     * @params LITE_POWER_FULL|LITE_POWER_HIGH
     */
    private String cpuPowerMode = "LITE_POWER_HIGH";

    private String detModelName = "det_db.nb";

    private String recModelName = "rec_crnn.nb";

    private String clsModelName = "cls.nb";

    public Boolean getRunDet() {
        return runDet;
    }

    public void setRunDet(Boolean runDet) {
        this.runDet = runDet;
    }

    public Boolean getRunCls() {
        return runCls;
    }

    public void setRunCls(Boolean runCls) {
        this.runCls = runCls;
    }

    public Boolean getRunRec() {
        return runRec;
    }

    public void setRunRec(Boolean runRec) {
        this.runRec = runRec;
    }

    public Integer getDetLongSize() {
        return detLongSize;
    }

    public void setDetLongSize(Integer detLongSize) {
        this.detLongSize = detLongSize;
    }

    public String getModelPath() {
        return modelPath;
    }

    public void setModelPath(String modelPath) {
        this.modelPath = modelPath;
    }

    public Boolean getUseOpencl() {
        return useOpencl;
    }

    public void setUseOpencl(Boolean useOpencl) {
        this.useOpencl = useOpencl;
    }

    public Integer getCpuThreadNum() {
        return cpuThreadNum;
    }

    public void setCpuThreadNum(Integer cpuThreadNum) {
        this.cpuThreadNum = cpuThreadNum;
    }

    public String getCpuPowerMode() {
        return cpuPowerMode;
    }

    public void setCpuPowerMode(String cpuPowerMode) {
        this.cpuPowerMode = cpuPowerMode;
    }

    public String getDetModelName() {
        return detModelName;
    }

    public void setDetModelName(String detModelName) {
        this.detModelName = detModelName;
    }

    public String getRecModelName() {
        return recModelName;
    }

    public void setRecModelName(String recModelName) {
        this.recModelName = recModelName;
    }

    public String getClsModelName() {
        return clsModelName;
    }

    public void setClsModelName(String clsModelName) {
        this.clsModelName = clsModelName;
    }
}
