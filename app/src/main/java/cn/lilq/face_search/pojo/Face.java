package cn.lilq.face_search.pojo;

import java.util.stream.Stream;

public class Face {
    private String userName;
    private Location location;
    private double score;

    public Face() {
    }

    public Face(String userName, Location location, double score) {
        this.userName = userName;
        this.location = location;
        this.score = score;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Face{" +
                "userName='" + userName + '\'' +
                ", location=" + location +
                ", score=" + score +
                '}';
    }

    public static class Location{
        private Double left;//人脸区域离左边界的距离
        private Double top;//人脸区域离上边界的距离
        private Double width;//人脸区域的宽度
        private Double height;//人脸区域的高度
        private Integer rotation;//旋转角度

        public Location(Double left, Double top, Double width, Double height, Integer rotation) {
            this.left = left;
            this.top = top;
            this.width = width;
            this.height = height;
            this.rotation = rotation;
        }

        public Location() {
        }

        public Double getLeft() {
            return left;
        }

        public void setLeft(Double left) {
            this.left = left;
        }

        public Double getTop() {
            return top;
        }

        public void setTop(Double top) {
            this.top = top;
        }

        public Double getWidth() {
            return width;
        }

        public void setWidth(Double width) {
            this.width = width;
        }

        public Double getHeight() {
            return height;
        }

        public void setHeight(Double height) {
            this.height = height;
        }

        public Integer getRotation() {
            return rotation;
        }

        public void setRotation(Integer rotation) {
            this.rotation = rotation;
        }

        @Override
        public String toString() {
            return "Location{" +
                    "left=" + left +
                    ", top=" + top +
                    ", width=" + width +
                    ", height=" + height +
                    ", rotation=" + rotation +
                    '}';
        }
    }
}
