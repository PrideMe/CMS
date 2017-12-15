package com.util.test;

import chrriis.dj.nativeswing.swtimpl.NativeComponent;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserAdapter;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserEvent;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Test extends JPanel {
    /**
     *  DJNativeSwing-SWT.jar
     */
    private static final long serialVersionUID = 1L;
    // 行分隔符
    final static public String LS = System.getProperty("line.separator", "/n");
    // 文件分割符
    final static public String FS = System.getProperty("file.separator", "//");
    //以javascript脚本获得网页全屏后大小
    final static StringBuffer jsDimension;

    static {
        jsDimension = new StringBuffer();
        jsDimension.append("var width = 0;").append(LS);
        jsDimension.append("var height = 0;").append(LS);
        jsDimension.append("if(document.documentElement) {").append(LS);
        jsDimension.append(
                "  width = Math.max(width, document.documentElement.scrollWidth);")
                .append(LS);
        jsDimension.append(
                "  height = Math.max(height, document.documentElement.scrollHeight);")
                .append(LS);
        jsDimension.append("}").append(LS);
        jsDimension.append("if(self.innerWidth) {").append(LS);
        jsDimension.append("  width = Math.max(width, self.innerWidth);")
                .append(LS);
        jsDimension.append("  height = Math.max(height, self.innerHeight);")
                .append(LS);
        jsDimension.append("}").append(LS);
        jsDimension.append("if(document.body.scrollWidth) {").append(LS);
        jsDimension.append(
                "  width = Math.max(width, document.body.scrollWidth);")
                .append(LS);
        jsDimension.append(
                "  height = Math.max(height, document.body.scrollHeight);")
                .append(LS);
        jsDimension.append("}").append(LS);
        jsDimension.append("return width + ':' + height;");
    }
    //DJNativeSwing组件请于http://djproject.sourceforge<a href="http://lib.csdn.net/base/dotnet" class='replace_word' title=".NET知识库" target='_blank' style='color:#df3434; font-weight:bold;'>.NET</a>/main/index.html下载
    public Test(final String url, final int maxWidth, final int maxHeight) {
        super(new BorderLayout());
        JPanel webBrowserPanel = new JPanel(new BorderLayout());
        final String fileName = System.currentTimeMillis() + ".jpg";
        final JWebBrowser webBrowser = new JWebBrowser(null);
        webBrowser.setBarsVisible(false);
        webBrowser.navigate(url);
        webBrowserPanel.add(webBrowser, BorderLayout.CENTER);
        add(webBrowserPanel, BorderLayout.CENTER);
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 4));
        webBrowser.addWebBrowserListener(new WebBrowserAdapter() {
                                             // 监听加载进度
                                             public void loadingProgressChanged(WebBrowserEvent e) {
                                                 // 当加载完毕时
                                                 if (e.getWebBrowser().getLoadingProgress() == 100) {
                                                     String result = (String) webBrowser
                                                             .executeJavascriptWithResult(jsDimension.toString());
                                                     int index = result == null ? -1 : result.indexOf(":");
                                                     NativeComponent nativeComponent = webBrowser
                                                             .getNativeComponent();
                                                     Dimension originalSize = nativeComponent.getSize();
                                                     Dimension imageSize = new Dimension(Integer.parseInt(result
                                                             .substring(0, index)), Integer.parseInt(result
                                                             .substring(index + 1)));
                                                     imageSize.width = Math.max(originalSize.width,
                                                             imageSize.width + 50);
                                                     imageSize.height = Math.max(originalSize.height,
                                                             imageSize.height + 50);
                                                     nativeComponent.setSize(imageSize);
                                                     BufferedImage image = new BufferedImage(imageSize.width,
                                                             imageSize.height, BufferedImage.TYPE_INT_RGB);
                                                     nativeComponent.paintComponent(image);
                                                     nativeComponent.setSize(originalSize);
                                                     // 当网页超出目标大小时
                                                     if (imageSize.width > maxWidth
                                                             || imageSize.height > maxHeight) {
                                                         //截图部分图形
                                                         image = image.getSubimage(0, 0, maxWidth, maxHeight);
                        /*此部分为使用缩略图
                        int width = image.getWidth(), height = image
                            .getHeight();
                         AffineTransform tx = new AffineTransform();
                        tx.scale((double) maxWidth / width, (double) maxHeight
                                / height);
                        AffineTransformOp op = new AffineTransformOp(tx,
                                AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                        //缩小
                        image = op.filter(image, null);*/
                                                     }
                                                     try {
                                                         // 输出图像
                                                         ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                                         ImageIO.write(image, "jpg", byteArrayOutputStream);
                                                         System.out.println("data:image/jpeg;base64,"+ImageToBase64(byteArrayOutputStream));
                                                     } catch (IOException ex) {
                                                         ex.printStackTrace();
                                                     }
                                                     // 退出操作
                                                     System.exit(0);
                                                 }
                                             }
                                         }
        );
        add(panel, BorderLayout.SOUTH);
    }
    public static void main(String[] args) {
        NativeInterface.open();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // SWT组件转Swing组件，不初始化父窗体将无法启动webBrowser
                JFrame frame = new JFrame("以DJ组件保存指定网页截图");
                // 加载指定页面，最大保存为640x480的截图
                frame.getContentPane().add(new Test("http://echarts.baidu.com/tutorial.html#%E8%87%AA%E5%AE%9A%E4%B9%89%E6%9E%84%E5%BB%BA%20ECharts", 1024, 768), BorderLayout.CENTER);
                frame.setSize(1024, 768);
                // 仅初始化，但不显示
                frame.invalidate();
                frame.pack();
                frame.setVisible(false);
            }
        });
        NativeInterface.runEventPump();
    }
    //图片转base64
    public static String ImageToBase64(ByteArrayOutputStream byteArrayOutputStream) {
        byte[] data = byteArrayOutputStream.toByteArray();
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }
}
