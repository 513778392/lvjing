package okhttp;



import java.io.File;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/11/11 0011.
 */
public class OkHttpData {
    /**
     * 我的设置-更换手机号	通过手机号，验证码更新用户表user_phone字段	/api/1.0/user/info/updaetPhone?user_id=100001&user_phone=1222222222&verify_code=2198
     我的设置-表扬弹窗	表扬，更新关于我们表fans字段	/api/1.0/about/us/modify?user_id=100001
     我的设置-关于我们	查询关于我们表	/api/1.0/about/us/getPage?page=0&size=10
     我的设置-意见反馈	插入意见反馈表	/api/1.0/feed/back/modify?user_id=100001&feed_content=这是很好的
     我的设置-设置	更新用户信息表message_yn；addlist_yn；auto_yn	/api/1.0/user/info/updaetMyset?user_id=100001&message_yn=Y&addlist_yn=Y&auto_yn=Y
     我的设置-账号与安全	查询用户信息表(addlist_yn,auto_yn,message_yn)	/api/1.0/user/info/getUserById?user_id=100001
     系统通用	系统预设用户兴趣标签库(store_interest)	系统预设用户兴趣标签库	api/1.0/store/interest/getPage?page=0&size=10
     系统预设足迹/美拍标签库
     (store_tag)	系统预设足迹美拍标签库	api/1.0/store/tag/getPage?page=0&size=10
     系统滤镜库	滤镜信息表	api/1.0/mirror/info/getPage?page=0&size=10
     写足迹/美拍-选择频道
     (channel_info)	频道信息表	api/1.0/channel/info/getPage?page=0&size=10
     发评论之前需要过滤（客户端定时更新sys_prep）	敏感词汇表	api/1.0/sys/prep/getPage?page=0&size=10


     首页-足迹更多-2	足迹表，足迹对应用户表，用户表	/api/1.0/fm/info/getFmMoreSec
     */

    public static final String waiwang = "http://one.egodvpt.com:8080";
    //192.168.0.109
    //sslj.100jed.com
    public static final String IP = waiwang;


    //首页-美拍更多（美拍搜索）	美拍表，美拍评论表，美拍对应用户,用户表
    public static final String getStMore = IP+"/api/1.0/st/info/getStMore";

    //首页-足迹更多-2	足迹表，足迹对应用户表，用户表	/api/1.0/fm/info/getFmMoreSec
    public static final String getFmMoreSec = IP+"/api/1.0/fm/info/getFmMoreSec";
    //首页-足迹更多-1（足迹搜索）	足迹表，足迹评论表，足迹对应用户表，用户表	/api/1.0/fm/info/getFmMore
    public static final String getFmMore = IP+"/api/1.0/fm/info/getFmMore";

    //热门搜索	搜索热门表	/api/1.0/search/hot/getPage
    public static final String hotgetPage = IP+"/api/1.0/search/hot/getPage";
    //足迹详情		/api/1.0/fm/info/getFmMain
    public static final String getFmMain = IP+"/api/1.0/fm/info/getFmMain";
    //美拍详情	美拍表，美拍评论表，美拍对应用户，收藏表,用户表	/api/1.0/st/info/getStSec?st_id=300001
    public static final String getStSec =IP+"/api/1.0/st/info/getStSec";


/**
 *
 我的-主页面 用户信息，用户关注表	/api/1.0/user/info/getMyPage?user_id=100001
 关注按钮	用户关注表	/api/1.0/user/att/addUserAtt?user_id=100001&att_id=100002
 取消关注	用户关注表	/api/1.0/user/att/delUserAtt?user_id=100001&att_id=100002
 我的-关注	用户信息，足迹，美拍，用户关注表	/api/1.0/user/att/getUserAtt?user_id=100001
 我的-粉丝	用户信息，足迹，美拍，用户关注表	/api/1.0/user/att/getAttUser?user_id=100001
 收藏夹，收藏列表所有信息（大json）	包括对应的足迹/美拍/足迹图片的信息	/api/1.0/collect/info/getCollectInfo?user_id=100001
 获取所有收藏夹		/api/1.0/collect/info/getCollectInfo?user_id=100001
 创建收藏夹		/api/1.0/collect/info/addCollectInfo?user_id=100001&collect_name=北京的&collect_see=Y
 编辑收藏夹		/api/1.0/collect/info/upCollectInfo?folder_id=1&collect_name=上海&collect_see=Y
 删除收藏夹(批量删除)	包括删除收藏夹里面的内容	/api/1.0/collect/info/delCollectInfo?folder_id=1_2	批量 用下划线分隔开
 获取收藏夹内所有		/api/1.0/user/collect/getUserCollect?folder_id=1
 我的收藏-新增照片	(其他页面添加收藏，包括足迹收藏A，美拍收藏B，足迹图片收藏C)	/api/1.0/user/collect/addUserCollect?folder_id=1&user_id=100001&collect_id=fm19009888&collect_type=A&pic_addr=http://one.egodvpt.com:8099/yoyogo/about-3.png	collect_type   A：足迹  B：美拍  C：足迹图片 需要把足迹封面，美拍，足迹图片的地址pic_addr传过来
 我的收藏-移动照片		/api/1.0/user/collect/upUserCollect?id=2_3_4	批量 用下划线分隔开
 我的收藏-删除照片		/api/1.0/user/collect/delUserCollect?id=2_3_4



 个人信息	用户信息表	/api/1.0/user/info/getUserById?user_id=100001
 个人信息更新	用户信息表	/api/1.0/user/info/updateUserById?user_id=100001&user_pic=头像地址&user_nick=昵称&user_sex=F&user_birthday=2000-01-01&user_conste=白羊座&user_city=上海&tag_name=篮球_音乐_足球_电影	user_sex: M 男  F 女   ；  tag_name: 兴趣用 _ 分开   ；
 我喜欢的	用户信息表	/api/1.0/user/info/getMyLike?user_id=100002
 个人兴趣标签	用户兴趣表	/api/1.0/user/interest/getUserInterest?user_id=100001
 /api/1.0/user/interest/updateUserInterest?user_id=100001&tag_name=篮球_音乐_足球_电影&tag_type=S	(不需要新增删除，只用update一个接口)
 tag_name:兴趣标签用 _ 分开  tag_type就传 S
 个人主页-更换头像	用户表	/api/1.0/user/info/updateImgById?user_id=100002&user_pic=http://www.aaaa.com/aaa.png
 个人主页	用户信息，用户关注，收藏夹，足迹，美拍，用户（喜欢）足迹/美拍取count，评论表	/api/1.0/user/info/getMyMain?user_id=100002
 /api/1.0/user/info/getMyMainFm?user_id=100002
 /api/1.0/user/info/getMyMainSt?user_id=100002	分为三个接口
 我的（主页面）	用户信息，用户关注表	/api/1.0/user/info/getMyPage?user_id=100001	user_id：用户ID  user_nick：昵称  user_pic1：头像地址
 att_count：关注数量 fans_count：粉丝数量; msg_count:消息数量
 nu:空，不管
 关注按钮	用户关注表	/api/1.0/user/att/addUserAtt?user_id=100001&att_id=100002
 取消关注	用户关注表	/api/1.0/user/att/delUserAtt?user_id=100001&att_id=100002
 我的-关注	用户信息，足迹，美拍，用户关注表	/api/1.0/user/att/getUserAtt?user_id=100001	att_id:关注的ID att_type：关注类别（A单项 B互相）user_nick：昵称
 user_pic1：头像地址 fm_count：足迹数量 st_count 美拍数量
 up_count：点赞数量  nu:空，不管
 我的-粉丝	用户信息，足迹，美拍，用户关注表	/api/1.0/user/att/getAttUser?user_id=100001
 * */
//好传头像 http://one.egodvpt.com:8099/yoyogo/picture
        public static final String picture = "http://one.egodvpt.com:8099/yoyogo/picture/";

// 个人信息	用户信息表	/api/1.0/user/info/getUserById?user_id=100001
public static final String getUserById =IP+"/api/1.0/user/info/getUserById";
// 个人信息更新	用户信息表	/api/1.0/user/info/updateUserById?
public static final String getUserById1 =IP+"/api/1.0/user/info/getUserById";
// 个人主页-更换头像	用户表	/api/1.0/user/info/updateImgById?user_id=100002&user_pic=http://www.aaaa.com/aaa.png
    public static final String updateImgById = IP+"/api/1.0/user/info/updateImgById";



    /*
 * 上传文件
 * 参数一：请求Url
 * 参数二：保存文件的路径名
 * 参数三：保存文件的文件名
 * 参数四：请求回调
 */
    /**
     * 上传文件
     *
     * @param url
     * @param pathName
     * @param fileName
     * @param callback
     */
    public static void doFile(String url, String pathName, String fileName, Callback callback) {
        //判断文件类型
        MediaType MEDIA_TYPE = MediaType.parse(judgeType(pathName));
        //创建文件参数
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(MEDIA_TYPE.type(), fileName,
                        RequestBody.create(MEDIA_TYPE, new File(pathName)));
        //发出请求参数
        Request request = new Request.Builder()
              //  .header("Authorization", "Client-ID " + "9199fdef135c122")
                .url(url)
                .post(builder.build())
                .build();
        Call call = OkHttpManage.getInstance().mClient.newCall(request);
        call.enqueue(callback);
    }

    /**
     * 根据文件路径判断MediaType
     *
     * @param path
     * @return
     */
    private static String judgeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

   /*
     * 将时间戳转换为时间
     */
public static String stampToDate(String s){
    String res;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    long lt = new Long(s);
    Date date = new Date(lt);
    res = simpleDateFormat.format(date);
    return res;
}



}
