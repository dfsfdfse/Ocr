package realcool.ocr;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import realcool.ocr.config.OCRExecConfig;
import realcool.ocr.config.OCRInitConfig;

public class OCRNative {
    private static final AtomicBoolean isSOLoaded = new AtomicBoolean();

    public static void loadLibrary() throws RuntimeException {
        if (!isSOLoaded.get() && isSOLoaded.compareAndSet(false, true)) {
            try {
                System.loadLibrary("Native");
            } catch (Throwable e) {
                RuntimeException exception = new RuntimeException(
                        "Load libNative.so failed, please check it exists in apk file.", e);
                throw exception;
            }
        }
    }

    private long pointer = 0;

    private final OCRExecConfig execConfig = new OCRExecConfig();

    public OCRNative(OCRInitConfig initConfig) {
        loadLibrary();
        pointer = init(initConfig.getDetModelPath(), initConfig.getRecModelPath(), initConfig.getClsModelPath(), initConfig.getUseOpencl(), initConfig.getCpuThreadNum(), initConfig.getCpuPower());
    }

    public List<OCRResultModel> exec(Bitmap img, int detLongSize, boolean runDet, boolean runCls, boolean runRec) {
        List<OCRResultModel> results = new ArrayList<>();
        int begin = 0;
        float[] raw = forward(pointer, img, detLongSize, runDet ? 1 : 0, runCls ? 1 : 0, runRec ? 1 : 0);
        while (begin < raw.length) {
            int point_num = Math.round(raw[begin]);
            int word_num = Math.round(raw[begin + 1]);
            OCRResultModel res = parse(raw, begin + 2, point_num, word_num);
            begin += 5 + point_num * 2 + word_num;
            results.add(res);
        }
        return results;
    }

    private OCRResultModel parse(float[] raw, int begin, int pointNum, int wordNum) {
        int current = begin;
        OCRResultModel res = new OCRResultModel();
        res.setConfidence(raw[current]);
        current++;
        for (int i = 0; i < pointNum; i++) {
            res.addPoints(Math.round(raw[current + i * 2]), Math.round(raw[current + i * 2 + 1]));
        }
        current += (pointNum * 2);
        for (int i = 0; i < wordNum; i++) {
            int index = Math.round(raw[current + i]);
            res.addWordIndex(index);
        }
        current += wordNum;
        res.setClsIdx(raw[current]);
        res.setClsConfidence(raw[current + 1]);
        return res;
    }

    public void destroy() {
        if (pointer != 0) {
            release(pointer);
            pointer = 0;
        }
    }

    protected native long init(String delModelPath, String recModelPath, String clsModelPath, int useOpencl, int threadNum, String cpuMode);

    protected native float[] forward(long pointer, Bitmap originalImage, int max_size_len, int run_det, int run_cls, int run_rec);

    protected native void release(long pointer);
}
