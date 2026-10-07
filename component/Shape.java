package component;

public class Shape {

    protected int[][] vertices;
    protected int color;

    public Shape() {}

    public Shape(int[][] vertices, int color) {this.vertices = vertices; this.color = color;}

    public static class Triangle extends Shape {
        private int x;
        private int y;
        private int width;
        private int height;
        private double oppositeRatio;

        public Triangle() {this(0, 0, 0, 0, 0, 0x000000);}

        public Triangle(int x, int y, int width, int height, double oppositeRatio, int color) {
            this.x = x; this.y = y; this.width = width; this.height = height; this.oppositeRatio = oppositeRatio; this.color = color;
            setVertices();
        }

        public void setVertices() {
            double ratio = Math.min(1, Math.max(0, oppositeRatio));
            int oppositeAngle = (int) Math.round(x + ratio*width);
            int[][] triangle = {{oppositeAngle, y}, {x, y+height}, {x+width, y+height}};
            this.vertices = triangle;
        }

        public void setX(int x) {this.x = x; setVertices();}
        public int getX() {return this.x;}
        public void setY(int y) {this.y = y; setVertices();}
        public int getY() {return this.y;}
        public void setWidth(int width) {this.width = width; setVertices();}
        public int getWidth() {return width;}
        public void setHeight(int height) {this.height = height; setVertices();}
        public int getHeight() {return height;}
        public void setOppositeRatio(double oppositeRatio) {this.oppositeRatio = oppositeRatio; setVertices();}
        public double getOppositeRatio() {return oppositeRatio;} 

        public int getCentroidX() {
            int centroidX = 0;
            for (int[] vertex : vertices) centroidX += vertex[0];
            centroidX = (int) Math.round((double) centroidX/vertices.length);
            return centroidX;
        }
        
        public int getCentroidY() {
            int centroidY = 0;
            for (int[] vertex : vertices) centroidY += vertex[1];
            centroidY = (int) Math.round((double) centroidY/vertices.length);
            return centroidY;
        }
    }

    public static class Rectangle extends Shape {

        public Rectangle() {this(0, 0, 0, 0, 0x000000);}

        public Rectangle(int x, int y, int width, int height, int color) {
            this.color = color;
            int[][] rectangle = {{x, y}, {x+width, y}, {x+width, y+height}, {x, y+height}};
            this.vertices = rectangle;
        }
    }

    public int[][] getVertices() {
        return vertices;
    }

    public int getColor() {
        return color;
    }

    public Shape rotate(double angleRadian, int centerX, int centerY) {
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
        return new Shape(transformed, this.color);
    }
}
