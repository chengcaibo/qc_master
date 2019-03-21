package com.qichong.entity;

/**
 * 职位信息表实体类
 * 
 * @创建人 孙建雷
 * @时间 2018-4-13 15:54:34
 */
public class JobInfo {

	private String jobCode;// 职业编号
	private String jobName;// 职业名称
	private String parentCode;// 父级编号

	public JobInfo() {
	}

	public JobInfo(String jobCode) {
		this.jobCode = jobCode;
	}

	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	@Override
	public String toString() {
		return "JobInfo [jobCode=" + jobCode + ", jobName=" + jobName + ", parentCode=" + parentCode + "]";
	}

}
