package resource;

import executer.Execute;

public class OpenSourceSoftware extends Execute {

    public double i = 0;
    @Override
    public void create() {

    }

    @Override
    public void render() {
        int[][] triangle = {
            {320, 140}, // 0번 (맨 위 꼭짓점)
            {420, 340}, // 1번 (우측 하단)
            {220, 340}  // 2번 (좌측 하단)
        };
        
        makePoly(getRotatedVertices(triangle, i, WIDTH/2, HEIGHT/2), 0xFFFFFF);
        i += 0.1;
    }

    public int[][] getRotatedVertices(int[][] vertices, double angleRadian, int centerX, int centerY) {
        int[][] transformed = new int[vertices.length][2];
        
        // 매번 Math.cos, Math.sin을 호출하지 않도록 루프 밖에서 한 번만 계산하여 성능 최적화
        double cos = Math.cos(angleRadian);
        double sin = Math.sin(angleRadian);

        for (int i = 0; i < vertices.length; i++) {
            int x = vertices[i][0];
            int y = vertices[i][1];
            int relativeX = x - centerX;
            int relativeY = y - centerY;

            // 1. 원점(0,0)을 기준으로 한 2D 회전 변환 공식 적용
            double rotatedX = relativeX * cos - relativeY * sin;
            double rotatedY = relativeX * sin + relativeY * cos;

            // 2. 회전된 결과를 지정된 중심점으로 평행 이동 후 반올림하여 정수 캐스팅
            transformed[i][0] = (int) Math.round(rotatedX + centerX);
            transformed[i][1] = (int) Math.round(rotatedY + centerY);
        }

        return transformed;
    }
}