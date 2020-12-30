package com.aitao.myblog.utils;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

/***
 * Data : 2020/05/22
 * Time : 11:54
 * Author : AiTao
 * Information :  验证码工具类
 */
@Component
public class ValidUtils extends JComponent implements MouseListener {
    private final static int NUMBER = 5;// 生成的验证码个数
    private String code;//验证码
    private int width, height;//宽，高
    private OutputStream os;//流对象
    private Random random;//随机数对象

    public ValidUtils() {
        width = NUMBER * 16 + (NUMBER - 1) * 10;
        height = 50;
        random = new Random();
        setPreferredSize(new Dimension(0, height));
        setSize(width, height);
        this.addMouseListener(this);
    }

    public String getCode() {
        return code;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public OutputStream getOs() {
        if (os != null) {
            return os;
        }
        return null;
    }

    /**
     * 设置流
     *
     * @param os 流对象
     */
    public void setOs(OutputStream os) {
        this.os = os;
    }

    /**
     * UUID生成随机生成的验证码
     *
     * @return 返回生成的验证码字符串
     */
    private String getVericationCode() {
        this.code = UUID.randomUUID().toString().replace("-", "").substring(0, NUMBER);
        System.out.println("yzm:" + this.code);
        return code;
    }

    /**
     * 切换验证码
     */
    private void toggle() {
        getVericationCode();
        repaint();
    }

    /**
     * 随机生成不同颜色的字母
     *
     * @param min 颜色范围最小值
     * @param max 颜色范围最大值
     * @return 返回一个具体的rgb颜色
     */
    private Color getRandColor(int min, int max) {
        if (min > 255)
            min = 255;
        if (max > 255)
            max = 255;
        int r = min + random.nextInt(max - min);
        int g = min + random.nextInt(max - min);
        int b = min + random.nextInt(max - min);
        return new Color(r, g, b);
    }

    /**
     * 绘制面板
     *
     * @param g2 画笔对象
     */
    @Override
    public void paintComponent(Graphics g2) {
        width = NUMBER * 16 + (NUMBER - 1) * 10;
        super.paintComponent(g2);
        Graphics2D g = (Graphics2D) g2;
        if (this.code == null || this.code.length() != NUMBER) {
            this.code = getVericationCode();
        }
        super.setSize(width, height);
        super.setPreferredSize(new Dimension(width, height));
        // 绘制验证码的背景线
        for (int i = 0; i < 70; i++) {
            int x = random.nextInt(getWidth() - 1);
            int y = random.nextInt(getWidth() - 1);
            int xl = random.nextInt(6) + 1;
            int yl = random.nextInt(12) + 1;
            g.setColor(getRandColor(100, 200));// 设置线条的颜色
            g.drawLine(x, y, x + xl + 40, y + yl + 20);
        }

        // 绘制验证码的数字与字母
        g.setColor(getRandColor(100, 160));
        g.setFont(new Font("Trebuchet MS", Font.ITALIC, getHeight() - 12));
        FontMetrics fm = g.getFontMetrics();
        int base = (height - fm.getHeight()) / 2 + fm.getAscent();

        for (int i = 0; i < NUMBER; i++) {
            g.setColor(getRandColor(100, 160));
            g.drawString(String.valueOf(code.charAt(i)), 16 * i + 17, base);
            AffineTransform affine = new AffineTransform();
            affine.setToRotation(Math.PI / 4.5 * random.nextDouble() * (random.nextBoolean() ? 1 : -1), (width / NUMBER) * i + (height - 12) / 1.8, height / 2);
            g.setTransform(affine);
        }
    }

    /**
     * 生成指定验证码图像文件
     *
     * @param w          宽
     * @param h          高
     * @param outputFile 文件保存地址
     */
    public void outputImage(int w, int h, File outputFile, boolean flag) {
        if (!flag && outputFile == null) {
            return;
        }
        if (!flag) {
            File dir = outputFile.getParentFile();
            if (!dir.exists()) {
                dir.mkdirs();
            }
        }

        try {
            // true:生成流
            // false:生成文件
            if (!flag) {
                outputFile.createNewFile();
                OutputStream fos = new FileOutputStream(outputFile);
                outputImage(w, h, fos);
                fos.close();
            } else {
                outputImage(w, h, os);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 输出指定验证码图片流
     *
     * @param width  宽
     * @param height 高
     * @param os     输出流
     */
    public void outputImage(int width, int height, OutputStream os) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color[] colors = new Color[5];
        Color[] colorSpaces = new Color[]{Color.WHITE, Color.CYAN,
                Color.GRAY, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE,
                Color.PINK, Color.YELLOW};
        float[] fractions = new float[colors.length];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = colorSpaces[random.nextInt(colorSpaces.length)];
            fractions[i] = random.nextFloat();
        }
        Arrays.sort(fractions);

        // 设置边框色
        g2.setColor(Color.GRAY);
        g2.fillRect(0, 0, width, height);

        // 设置背景色
        Color c = getRandColor(200, 250);
        g2.setColor(c);
        g2.fillRect(0, 2, width, height - 4);

        // 使图片扭曲(去掉线)
        shear(g2, width, height, c);

        g2.setColor(getRandColor(100, 160));
        int fontSize = height - 10;
        Font font = new Font("Algerian", Font.ITALIC, fontSize);
        g2.setFont(font);
        //销毁画笔对象
        paintComponent(g2);
        g2.dispose();
        //生成验证码文件
        try {
            //生成指定验证码图像文件
            ImageIO.write(image, "png", os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用图片扭曲
     *
     * @param g     画笔
     * @param w1    宽度
     * @param h1    高度
     * @param color 颜色
     */
    private void shear(Graphics g, int w1, int h1, Color color) {
        shearX(g, w1, h1, color);
        shearY(g, w1, h1, color);
    }

    /**
     * 图片X方向扭曲
     *
     * @param g
     * @param w1
     * @param h1
     * @param color
     */
    private void shearX(Graphics g, int w1, int h1, Color color) {
        int period = random.nextInt(2);
        boolean borderGap = true;
        int frames = 1;
        int phase = random.nextInt(2);
        for (int i = 0; i < h1; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period
                    + (6.2831853071795862D * (double) phase)
                    / (double) frames);
            g.copyArea(0, i, w1, 1, (int) d, 0);
            if (borderGap) {
                g.setColor(color);
                g.drawLine((int) d, i, 0, i);
                g.drawLine((int) d + w1, i, w1, i);
            }
        }
    }

    /**
     * 图片Y方向扭曲
     *
     * @param g
     * @param w1
     * @param h1
     * @param color
     */
    private void shearY(Graphics g, int w1, int h1, Color color) {
        int period = random.nextInt(40) + 10; // 50;
        boolean borderGap = true;
        int frames = 20;
        int phase = 7;
        for (int i = 0; i < w1; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period
                    + (6.2831853071795862D * (double) phase)
                    / (double) frames);
            g.copyArea(i, 0, 1, h1, 0, (int) d);
            if (borderGap) {
                g.setColor(color);
                g.drawLine(i, (int) d, i, 0);
                g.drawLine(i, (int) d + h1, i, h1);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        toggle();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    /*public static void main(String[] args) throws IOException {
        ValidUtils validUtils = new ValidUtils();
        validUtils.outputImage(validUtils.getWidth(), validUtils.getHeight(), new File("D:/1.png"),false);
    }*/
}
