package com.qichong.entity;

import java.util.List;

/**
 * 服务端发送给客户端消息实体
 * @author chen
 *
 */
public class Message {
	//传值给客户端的发送人Id(消息提醒业务)
	private Integer fromId;

    private  String  alert;//内容消息
    
    private String tiShi;//提示

    private  List<String>  names;

    private  String  sendMsg;

    private String  from;

    private String  date;
    
    private String photo;
    

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSendMsg() {
        return sendMsg;
    }

    public void setSendMsg(String sendMsg) {
        this.sendMsg = sendMsg;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public Integer getFromId() {
		return fromId;
	}

	public void setFromId(Integer fromId) {
		this.fromId = fromId;
	}

	public String getTiShi() {
		return tiShi;
	}

	public void setTiShi(String tiShi) {
		this.tiShi = tiShi;
	}
	
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Message() {
        super();
    }
}
