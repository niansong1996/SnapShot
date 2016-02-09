package org.sensation.snapmemo.tool;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.sensation.snapmemo.VO.MemoVO;
import org.sensation.snapmemo.VO.MemoVOLite;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * JSON工具类
 * Created by Alan on 2016/2/5.
 */
public class JSONHandler {

    /**
     * 利用GSON将传回的JSON字符串直接解析称MemoVO类
     *
     * @param JSONMemoVO
     * @return
     */
    public static MemoVO getMemoVO(String JSONMemoVO) {
        Gson gson = new Gson();
        MemoVOLite memoVOLite = gson.fromJson(JSONMemoVO, MemoVOLite.class);
        String date = memoVOLite.getDate();
        String day = getDay(date);
        MemoVO memoVO = new MemoVO(memoVOLite.getTopic(), memoVOLite.getDate(), day, memoVOLite.getContent());

        return memoVO;
    }

    /**
     * 字符串转成ArrayList
     *
     * @param resultJSONString
     * @return
     */
    public static List<MemoVO> getMemoList(String resultJSONString) {
        JSONArray memo;
        Gson gson = new Gson();
        List<MemoVO> memoVOList = new ArrayList<MemoVO>();
        try {
            JSONObject memoList = new JSONObject(resultJSONString);
            memo = memoList.getJSONArray("memo");

            for (int i = 0; i < memo.length(); i++) {
                MemoVO memoVO = gson.fromJson(memo.getString(i), MemoVO.class);
                memoVOList.add(memoVO);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return memoVOList;
    }

    /**
     * 生成memoVO的JSON语句
     *
     * @param memoVO
     * @return
     */
    public static String getMemoJSON(MemoVO memoVO) {
        Gson gson = new Gson();
        String memoJSON;
        TypeAdapter<MemoVO> typeAdapter = gson.getAdapter(MemoVO.class);
        try {
            memoJSON = typeAdapter.toJson(memoVO);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return memoJSON;
    }


    /**
     * 生成登录的JSON字符串
     *
     * @param userName
     * @param password
     * @return
     */
    public static String getSignInJSON(String userName, String password) {
        JSONObject signinJSON = new JSONObject();
        try {
            signinJSON.put("userName", userName);
            signinJSON.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return signinJSON.toString();
    }

    /**
     * 获得根据yyyy-MM-dd格式传入的日期所对应的星期几信息
     *
     * @param date
     * @return
     */
    private static String getDay(String date) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        int dayOfWeekNum = 0;
        String dayOfWeek = "未获得";

        try {
            c.setTime(format.parse(date));

            dayOfWeekNum = c.get(Calendar.DAY_OF_WEEK);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        switch (dayOfWeekNum) {
            case 1:
                dayOfWeek = "星期日";
                break;
            case 2:
                dayOfWeek = "星期一";
                break;
            case 3:
                dayOfWeek = "星期二";
                break;
            case 4:
                dayOfWeek = "星期三";
                break;
            case 5:
                dayOfWeek = "星期四";
                break;
            case 6:
                dayOfWeek = "星期五";
                break;
            case 7:
                dayOfWeek = "星期六";
                break;
        }

        return dayOfWeek;
    }
}
