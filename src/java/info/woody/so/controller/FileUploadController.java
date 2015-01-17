package info.woody.so.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

/**
 * http://www.journaldev.com/2573/spring-mvc-file-upload-example-tutorial-single-and-multiple-files
 * 
 * Handles requests for the application file upload requests
 */
@Controller
public class FileUploadController {

    private String ROOT_PATH = System.getProperty("catalina.home");

	@Autowired
	private SqlSessionFactory sqlSessionFactory;

//	@RequestMapping(value = "/exportExcel", method = RequestMethod.POST)
//    public @ResponseBody String exportExcel() {
////		sqlSessionFactory.openSession();
//		try {
//		    Workbook wb = new XSSFWorkbook();
////		    Sheet sheet1 = wb.createSheet();
////		    sheet1.createRow(paramInt)
//		    FileOutputStream fileOut = new FileOutputStream("workbook.xlsx");
//		    wb.write(fileOut);
//		    fileOut.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "OK";
//	}
	
//    /**
//     * Upload single file using Spring Controller
//     */
//    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
//    public @ResponseBody
//    String uploadFileHandler(@RequestParam(required=false) String name,
//            @RequestParam("file") MultipartFile file) {
// 
//        if (!file.isEmpty()) {
//            try {
//                byte[] bytes = file.getBytes();
// 
//                // Creating the directory to store file
//                String ROOT_PATH = System.getProperty("catalina.home");
//                File dir = new File(ROOT_PATH + File.separator + "tmpFiles");
//                if (!dir.exists())
//                    dir.mkdirs();
// 
//                // Create the file on server
//                File serverFile = new File(dir.getAbsolutePath()
//                        + File.separator + name);
//                BufferedOutputStream stream = new BufferedOutputStream(
//                        new FileOutputStream(serverFile));
//                stream.write(bytes);
//                stream.close();
// 
//                System.out.println("Server File Location="
//                        + serverFile.getAbsolutePath());
// 
//                return "You successfully uploaded file=" + name;
//            } catch (Exception e) {
//                return "You failed to upload " + name + " => " + e.getMessage();
//            }
//        } else {
//            return "You failed to upload " + name
//                    + " because the file was empty.";
//        }
//    }

    /**
     * Upload multiple file using Spring Controller
     */
    @RequestMapping(value = "/uploadMultipleFile", method = RequestMethod.POST)
    public @ResponseBody
    String uploadMultipleFileHandler(@RequestParam(value="name",required=false) String[] names,
            @RequestParam("file") MultipartFile[] files) {
 
    	boolean useNewName = true;
        if (null == names || files.length != names.length) {
        	useNewName = false;
        }

        String message = "You successfully uploaded file(s)<pre>";
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
        	String name;
            if (useNewName) {
            	name = names[i];
            } else {
            	name = files[i].getOriginalFilename();
            }
            name = this.getShortFilename(name);
            try {
                byte[] bytes = file.getBytes();
 
                // Creating the directory to store file
                File dir = new File(ROOT_PATH + File.separator + "tmpFiles");
                if (!dir.exists())
                    dir.mkdirs();
 
                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();
 
                message += name + "\n";
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        }
        return message;
    }

    
    private String getShortFilename(String name) {
        try {
        	synchronized(this) {
        		Thread.sleep(1);
        	}
        	return new java.util.Date().getTime() + "_" + name;
        } catch (Exception e) {
        	return null;
        }
    }

    private File getServerFile(String name, FileStorageStatus fileStorageStatus) {
        try {
        	synchronized(this) {
        		Thread.sleep(1);
        	}
        	File dir = new File(ROOT_PATH + File.separator + "tmpFiles");
        	if (!dir.exists()) {
                dir.mkdirs();
        	}
        	if (FileStorageStatus.ALREADY_EXISTS.equals(fileStorageStatus)) {
        		return new File(new File(ROOT_PATH + File.separator + "tmpFiles").getAbsolutePath() + File.separator + name);
        	}
        	if (FileStorageStatus.CREATE_NEW.equals(fileStorageStatus)) {
        		return new File(new File(ROOT_PATH + File.separator + "tmpFiles").getAbsolutePath() + File.separator + new java.util.Date().getTime() + "_" + name); 
        	}
        	return null;
        } catch (Exception e) {
        	return null;
        }
    }

    private enum FileStorageStatus {
    	CREATE_NEW,
    	ALREADY_EXISTS;
    }
    
    @RequestMapping(value = "/files/{name:.+}", method = RequestMethod.GET)
    public void getFile(@PathVariable("name") String name, HttpServletRequest request, HttpServletResponse response) {
        try {
            File serverFile = this.getServerFile(name, FileStorageStatus.ALREADY_EXISTS);
            if (serverFile.exists()) {
	            // copy it to response's OutputStream
	    		ServletContext context = request.getServletContext();
	    		String mimeType = context.getMimeType(serverFile.getAbsolutePath());
	    		if (mimeType == null) {
	    			mimeType = "application/octet-stream";
	    		}
	        	response.setContentType(mimeType);
	        	response.setContentLength((int)serverFile.length());
	        	
	        	String headerKey = "Content-Disposition";
	    		String headerValue = String.format("attachment; filename=\"%s\"", serverFile.getName());
	    		response.setHeader(headerKey, headerValue);
	
	            org.apache.commons.io.IOUtils.copy(new FileInputStream(serverFile), response.getOutputStream());
	            response.flushBuffer();
            } else {
            }
        } catch (IOException ex) {
        }
    }

    @RequestMapping(value = "/excel/{name:.+}", method = RequestMethod.GET)
    public void getExcel(@PathVariable("name") String name, HttpServletRequest request, HttpServletResponse response) {
        try {
            File serverFile = this.getServerFile(name + ".xlsx", FileStorageStatus.CREATE_NEW);
//////////////////////////////////
			WebApplicationContext wac = (WebApplicationContext)request.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
			SqlSessionFactory sqlSessionFactory = wac.getBean(SqlSessionFactory.class);
			SqlSession sqlSession = sqlSessionFactory.openSession();
			List<Object> list = sqlSession.selectList(name);
			ExcelWriter.newWorkbook(serverFile.getAbsolutePath(), list);

//////////////////////////////////
            if (serverFile.exists()) {
	            // copy it to response's OutputStream
	    		ServletContext context = request.getServletContext();
	    		String mimeType = context.getMimeType(serverFile.getAbsolutePath());
	    		if (mimeType == null) {
	    			mimeType = "application/octet-stream";
	    		}
	        	response.setContentType(mimeType);
	        	response.setContentLength((int)serverFile.length());
	        	
	        	String headerKey = "Content-Disposition";
	    		String headerValue = String.format("attachment; filename=\"%s\"", serverFile.getName());
	    		response.setHeader(headerKey, headerValue);
	
	            org.apache.commons.io.IOUtils.copy(new FileInputStream(serverFile), response.getOutputStream());
	            response.flushBuffer();
            } else {
            }
        } catch (Exception ex) {
        }
    }

    public static class ExcelWriter {
    	public static void newWorkbook(String name, List<Object> list) throws Exception {

		    Workbook wb = new XSSFWorkbook();
		    String safeSheetName = WorkbookUtil.createSafeSheetName(FilenameUtils.getName(name));
		    wb.createSheet(safeSheetName);
		    
			Sheet sheet = wb.getSheetAt(0);
			if (list.size() > 0) {
				Field[] fields = list.get(0).getClass().getDeclaredFields();
				for (Object data : list) {
					Row row = sheet.createRow(list.indexOf(data));
					for (int i = 0; i < fields.length; i++) {
						Cell cell = row.createCell(i);
						fields[i].setAccessible(true);
						cell.setCellValue(String.valueOf(fields[i].get(data)));
					}
				}
			}

		    FileOutputStream fileOut = new FileOutputStream(name);
		    wb.write(fileOut);
		    wb.close();
		    fileOut.close();
    	}
    }

}
