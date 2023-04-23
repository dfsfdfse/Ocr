package realcool.ocr;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import java.io.File;
import java.util.List;

import realcool.ocr.config.OCREngineConfig;
import realcool.ocr.config.OCRInitConfig;
import realcool.ocr.exception.OCRException;

public class OCREngine {

    private OCRNative ocr;

    private final OCREngineConfig config;

    public OCREngine() {
        config = new OCREngineConfig();
    }

    public OCREngine(String modelPath) {
        config = new OCREngineConfig();
        config.setModelPath(modelPath);
    }

    public OCREngine(String modelPath, String detName, String clsName, String recName) {
        config = new OCREngineConfig();
        config.setModelPath(modelPath);
        config.setDetModelName(detName);
        config.setClsModelName(clsName);
        config.setRecModelName(recName);
    }

    public OCREngineConfig getConfig() {
        return config;
    }

    public void init(Context ctx) throws OCRException {
        release();
        String modelPath = config.getModelPath();
        if (modelPath.isEmpty()) {
            throw new OCRException("训练模板文件夹为空");
        }
        String realPath = modelPath;
        if (modelPath.charAt(0) != '/') {
            realPath = ctx.getCacheDir() + "/" + modelPath;
            Utils.copyDirectoryFromAssets(ctx, modelPath, realPath);
        }
        String sp = realPath + File.separator;
        OCRInitConfig cfg = new OCRInitConfig();
        cfg.setDetModelPath(sp + config.getDetModelName());
        cfg.setClsModelPath(sp + config.getClsModelName());
        cfg.setRecModelPath(sp + config.getRecModelName());
        cfg.setCpuPower(config.getCpuPowerMode());
        cfg.setCpuThreadNum(config.getCpuThreadNum());
        cfg.setUseOpencl(config.getUseOpencl() ? 1 : 0);
        ocr = new OCRNative(cfg);
    }

    public List<OCRResultModel> run(Bitmap input){
        if (input == null) {
            throw new OCRException("输入图片为空");
        }
        if (ocr == null) {
            throw new OCRException("OCREngine未初始化");
        }
        return ocr.exec(input, config.getDetLongSize(), config.getRunDet(), config.getRunCls(), config.getRunRec());
    }

    public void run(Bitmap input, Boolean drawPosBox, Listener listener) {
        if (input == null) {
            throw new OCRException("输入图片为空");
        }
        if (ocr == null) {
            throw new OCRException("OCREngine未初始化");
        }
        List<OCRResultModel> exec = ocr.exec(input, config.getDetLongSize(), config.getRunDet(), config.getRunCls(), config.getRunRec());
        Bitmap bitmap = null;
        if (drawPosBox) {
            bitmap = drawPosBox(input, exec);
        }
        listener.OnFinished(exec, bitmap);
    }

    public List<OCRResultModel> run(Bitmap input, Boolean runDet, Boolean runCls, Boolean runRec) {
        if (input == null) {
            throw new OCRException("输入图片为空");
        }
        if (ocr == null) {
            throw new OCRException("OCREngine未初始化");
        }
        return ocr.exec(input, config.getDetLongSize(), runDet, runCls, runRec);
    }

    private Bitmap drawPosBox(Bitmap bitmap, List<OCRResultModel> result) {
        Bitmap input = Bitmap.createBitmap(bitmap);
        Canvas canvas = new Canvas(input);
        Paint paintFillAlpha = new Paint();
        paintFillAlpha.setStyle(Paint.Style.FILL);
        paintFillAlpha.setColor(Color.parseColor("#3B85F5"));
        paintFillAlpha.setAlpha(50);

        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#3B85F5"));
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);

        for (OCRResultModel r : result) {
            Path path = new Path();
            List<Point> points = r.getPoints();
            if (points.size() == 0) {
                continue;
            }
            path.moveTo(points.get(0).x, points.get(0).y);
            for (int i = points.size() - 1; i >= 0; i--) {
                Point p = points.get(i);
                path.lineTo(p.x, p.y);
            }
            canvas.drawPath(path, paint);
            canvas.drawPath(path, paintFillAlpha);
        }
        return input;
    }

    public void release() {
        if (ocr != null) {
            ocr.destroy();
            ocr = null;
        }
    }

    public interface Listener {
        void OnFinished(List<OCRResultModel> result, Bitmap bitmap);
    }
}
