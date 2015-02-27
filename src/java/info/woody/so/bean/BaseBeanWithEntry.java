package info.woody.so.bean;

import java.util.List;

public class BaseBeanWithEntry<T> extends BaseBean {

	private List<T> entryModelList;
	public List<T> getEntryModelList() {
		return entryModelList;
	}
	public void setEntryModelList(List<T> entryModelList) {
		this.entryModelList = entryModelList;
	}
}
