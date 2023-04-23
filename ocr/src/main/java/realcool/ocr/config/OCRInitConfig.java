package realcool.ocr.config;

public class OCRInitConfig {
    private int useOpencl = 0;
    private int cpuThreadNum = 1;
    private String cpuPower = "LITE_POWER_HIGH";//LITE_POWER_FULL
    private String detModelPath;
    private String recModelPath;
    private String clsModelPath;

    public int getUseOpencl() {
        return useOpencl;
    }

    public void setUseOpencl(int useOpencl) {
        this.useOpencl = useOpencl;
    }

    public int getCpuThreadNum() {
        return cpuThreadNum;
    }

    public void setCpuThreadNum(int cpuThreadNum) {
        this.cpuThreadNum = cpuThreadNum;
    }

    public String getCpuPower() {
        return cpuPower;
    }

    public void setCpuPower(String cpuPower) {
        this.cpuPower = cpuPower;
    }

    public String getDetModelPath() {
        return detModelPath;
    }

    public void setDetModelPath(String detModelPath) {
        this.detModelPath = detModelPath;
    }

    public String getRecModelPath() {
        return recModelPath;
    }

    public void setRecModelPath(String recModelPath) {
        this.recModelPath = recModelPath;
    }

    public String getClsModelPath() {
        return clsModelPath;
    }

    public void setClsModelPath(String clsModelPath) {
        this.clsModelPath = clsModelPath;
    }
}
