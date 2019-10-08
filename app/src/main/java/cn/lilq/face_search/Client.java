package cn.lilq.face_search;

import com.baidu.aip.face.AipFace;

import org.json.JSONObject;

import java.util.HashMap;

import cn.lilq.face_search.pojo.FaceAddAPI;
import cn.lilq.face_search.pojo.FaceSearchAPI;
import cn.lilq.face_search.util.GsonUtils;

public class Client {
    private static String APP_ID = "17357537";
    private static String API_KEY = "Un0L4veKMxzmwMhZtGn5RPxP";
    private static String SECRET_KEY = "0hhxXmnREdqigfBdMwhkyr4wo29xzGAV";
    private AipFace client;

    public Client() {
        client = new AipFace(APP_ID, API_KEY, SECRET_KEY);
    }
    public FaceSearchAPI search(String image, String imageType, String groupIdList) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("max_face_num", "10");
        options.put("max_user_num", "3");

//        String image = "取决于image_type参数，传入BASE64字符串或URL字符串或FACE_TOKEN字符串";
//        String imageType = "BASE64";
//        String groupIdList = "test,test0";//用户列表

        // 人脸搜索 M:N 识别
        JSONObject res = client.multiSearch(image, imageType, groupIdList, options);
        FaceSearchAPI faceSearchAPI = GsonUtils.fromJson(res.toString(),FaceSearchAPI.class);
        return  faceSearchAPI;
    }

    public FaceAddAPI add(String image, String imageType, String groupId, String userId,String name) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("action_type", "REPLACE");
        options.put("user_info", name);

//        String image = "取决于image_type参数，传入BASE64字符串或URL字符串或FACE_TOKEN字符串";
//        String imageType = "BASE64";
//        String groupId = "group1";
//        String userId = "user1";
        // 人脸注册
        JSONObject res = client.addUser(image, imageType, groupId, userId, options);
//        System.out.println(res.toString());
//        System.out.println(res.toString(2));
        return GsonUtils.fromJson(res.toString(),FaceAddAPI.class);

    }
}
