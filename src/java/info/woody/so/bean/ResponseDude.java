package info.woody.so.bean;

import javax.servlet.http.HttpServletResponse;

public class ResponseDude {
	private int messageCode;
	private String messageText;
	public ResponseDude(int messageCode, String messageText) {
		super();
		this.messageCode = messageCode;
		this.messageText = messageText;
	}
	public int getMessageCode() {
		return messageCode;
	}
	public String getMessageText() {
		return messageText;
	}
	public static ResponseDude OK = new ResponseDude(HttpServletResponse.SC_OK, "rest.response.message.gone.ok");
	public static ResponseDude INTERNAL_SERVER_ERROR = new ResponseDude(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "rest.response.message.goneinternal_server_error");
	public static ResponseDude CONFLICT = new ResponseDude(HttpServletResponse.SC_CONFLICT, "rest.response.message.gone.conflict");
	public static ResponseDude NOT_ACCEPTABLE = new ResponseDude(HttpServletResponse.SC_NOT_ACCEPTABLE, "rest.response.message.gone.acceptable");
	public static ResponseDude UNAUTHORIZED = new ResponseDude(HttpServletResponse.SC_UNAUTHORIZED, "rest.response.message.unauthorized");
	public static ResponseDude GONE = new ResponseDude(HttpServletResponse.SC_GONE, "rest.response.message.gone");
	public static ResponseDude NOT_FOUND = new ResponseDude(HttpServletResponse.SC_NOT_FOUND, "rest.response.message.sc_not_found");
	
}
