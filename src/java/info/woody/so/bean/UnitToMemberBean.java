package info.woody.so.bean;

public class UnitToMemberBean extends BaseBean {
	private String _unitId  ;
	private int    unitId  ;
	private String _memberId;
	private int    memberId;
	public String get_unitId() {
		return _unitId;
	}
	public int getUnitId() {
		return unitId;
	}
	public String get_memberId() {
		return _memberId;
	}
	public int getMemberId() {
		return memberId;
	}
	public void set_unitId(String _unitId) {
		this._unitId = _unitId;
	}
	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}
	public void set_memberId(String _memberId) {
		this._memberId = _memberId;
	}
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

}