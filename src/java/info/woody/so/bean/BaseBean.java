package info.woody.so.bean;

import java.lang.reflect.Field;
import java.util.Date;

import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonFormat;

class BaseBean {

	protected int id;
	protected String createBy;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
	protected Date createAt;
	protected String updateBy;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
	protected Date updateAt;
	public int getId() {
		return id;
	}
	public String getCreateBy() {
		return createBy;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public Date getUpdateAt() {
		return updateAt;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public String toString() {
		try {
			ToStringBuilder tsb = new ToStringBuilder(this);
			for (Field eachField : this.getClass().getFields()) {
				if (this.getClass().getMethod("set" + WordUtils.uncapitalize(eachField.getName()), eachField.getType()) != null) {
					tsb.append(eachField.getName(), eachField.get(this));
				}
			}
			return tsb.toString();	
		} catch (Exception e) {
			return "";
		}
	}
}
