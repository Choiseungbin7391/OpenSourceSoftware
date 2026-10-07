package component;

import javax.swing.*;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Window extends JFrame {

    private int WIDTH;
    private int HEIGHT;
    private int LENGTH; // 총 픽셀 수
    private PixelPanel pixelPanel;

    public Window(String str) {
        super("My Graphic");
        String[] parts = str.split("X");
        WIDTH = Integer.parseInt(parts[0].trim());
        HEIGHT = Integer.parseInt(parts[1].trim());
        LENGTH = WIDTH*HEIGHT;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // 640x480 크기의 렌더링 패널 추가
        this.pixelPanel = new PixelPanel(WIDTH, HEIGHT);
        setLayout(new BorderLayout());
        add(pixelPanel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null); // 화면 중앙에 배치
        setVisible(true);
    }

    public void clearColor(int color) {
        int[] pixels = new int[LENGTH];
        Arrays.fill(pixels, color);
        pixelPanel.setPixelsColor(pixels);
    }

    public void makeRect(int x, int y, int width, int height, int color) {
        int[] pixels = pixelPanel.getPixelsColor();
        for(int i=0; i<Math.min(height, HEIGHT); i++) {
            Arrays.fill(pixels, x+((y+i)*WIDTH), Math.min(x+((y+i)*WIDTH)+width, (y+i+1)*WIDTH), color);
        }
        pixelPanel.setPixelsColor(pixels);
    }

    public void makePoly(int[][] vertexs, int color) {
        int minX = WIDTH; int minY = HEIGHT;
        int maxX = 0; int maxY = 0;
        for (int[] vertex : vertexs) {
            minX = Math.min(vertex[0], minX);
            maxX = Math.max(vertex[0], maxX);
            minY = Math.min(vertex[1], minY);
            maxY = Math.max(vertex[1], maxY);
        }

        int dy = maxY - minY + 1;
        int len = vertexs.length;

        List<List<Integer>> scanlines = new ArrayList<>(dy);
        for (int i = 0; i < dy; i++) {
            scanlines.add(new ArrayList<>());
        }
        for (int i = 0; i < len; i++) {
            int[] first = vertexs[i]; 
            int[] second = vertexs[(i + 1) % len];

            if (second[1] - first[1] != 0) { // 수평선 스킵
                double delta = (double) (second[0] - first[0]) / (second[1] - first[1]);
                
                int startY = Math.min(first[1], second[1]);
                int endY = Math.max(first[1], second[1]);

                for (int y = startY; y < endY; y++) {
                    double testY = y + 0.5;
                    
                    double exactX = first[0] + (testY - first[1]) * delta;
                    int intX = (int) Math.round(exactX);

                    int relativeY = y - minY;

                    if (relativeY >= 0 && relativeY < dy) {
                        scanlines.get(relativeY).add(intX);
                    }
                }
            }
        }
        int[] pixels = pixelPanel.getPixelsColor();
        
        for (int y = 0; y < dy; y++) {
            List<Integer> rowIntersections = scanlines.get(y);

            Collections.sort(rowIntersections);

            for (int i = 0; i < rowIntersections.size() - 1; i += 2) {
                int startX = rowIntersections.get(i);
                int endX = rowIntersections.get(i + 1);

                startX = (startX < 0) ? 0 : startX;
                endX = (endX >= WIDTH) ? WIDTH - 1 : endX;

                int screenY = minY + y;
                if (screenY >= 0 && screenY < HEIGHT) {
                    for (int x = startX; x <= endX; x++) {
                        pixels[x + (screenY * WIDTH)] = color;
                    }
                }
            }
        }
        pixelPanel.setPixelsColor(pixels);
    }
    
}