package com.geminno.crazycat;

import android.R.integer;

public class        Dot {
	 int x;  //列
	 int y;  //行
	 int status;  //状态
	 public static final int STATUS_OFF=0;  //未被占用
     public static final int STATUS_ON=1;   //已被路障占用
	 public static final int STATUS_IN=2;   //猫当前占着
	 
	 public Dot(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		this.status = STATUS_OFF;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	 public void setXY(int x,int y){
		 this.x = x;
		 this.y = y;
	 }
	 

}
