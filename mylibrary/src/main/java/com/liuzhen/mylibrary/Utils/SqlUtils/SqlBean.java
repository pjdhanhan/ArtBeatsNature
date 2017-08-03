package com.liuzhen.mylibrary.Utils.SqlUtils;

/**
 * Created by Wxd on 2017-06-16.
 */
public class SqlBean {


    /**
     * Id : 2
     * UserPwd : 123456
     * UserName : 李四
     * JobNumber : A0002
     * UserMark :
     * AddTime : 2017-07-17 16:02:32
     */

    private String Id;
    private String UserPwd;
    private String UserName;
    private String JobNumber;
    private String UserMark;
    private String AddTime;

    @Override
    public String toString() {
        return
                        "Id=" + Id +
                        ", UserPwd=" + UserPwd +
                        ", UserName=" + UserName +
                        ", JobNumber=" + JobNumber +
                        ", UserMark=" + UserMark +
                        ", AddTime=" + AddTime;

    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getUserPwd() {
        return UserPwd;
    }

    public void setUserPwd(String UserPwd) {
        this.UserPwd = UserPwd;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getJobNumber() {
        return JobNumber;
    }

    public void setJobNumber(String JobNumber) {
        this.JobNumber = JobNumber;
    }

    public String getUserMark() {
        return UserMark;
    }

    public void setUserMark(String UserMark) {
        this.UserMark = UserMark;
    }

    public String getAddTime() {
        return AddTime;
    }

    public void setAddTime(String AddTime) {
        this.AddTime = AddTime;
    }
}
