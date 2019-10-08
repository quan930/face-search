package cn.lilq.face_search.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * 人脸添加返回api javaBean
 */
public class FaceAddAPI {
    @SerializedName("error_code")
    private Integer errorCode;
    @SerializedName("error_msg")
    private String errorMsg;
    @SerializedName("log_id")
    private Long logId;
    @SerializedName("timestamp")
    private Long timestamp;
    @SerializedName("cached")
    private Integer cached;
    @SerializedName("result")
    private Result result;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getCached() {
        return cached;
    }

    public void setCached(Integer cached) {
        this.cached = cached;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "FaceAddAPI{" +
                "errorCode=" + errorCode +
                ", errorMsg='" + errorMsg + '\'' +
                ", logId=" + logId +
                ", timestamp=" + timestamp +
                ", cached=" + cached +
                ", result=" + result +
                '}';
    }

    class Result {
        @SerializedName("face_token")
        private String token;
        private Location location;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "token='" + token + '\'' +
                    ", location=" + location +
                    '}';
        }
    }
    class Location{
        private Double left;//人脸区域离左边界的距离
        private Double top;//人脸区域离上边界的距离
        private Double width;//人脸区域的宽度
        private Double height;//人脸区域的高度
        private Integer rotation;//旋转角度

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
