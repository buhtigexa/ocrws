package server;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
// clave nueva : 53588545c788957


@Path("/ocr")
public class OCR {
	
	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces("application/json")
	
	public Response uploadFile(
			@FormDataParam("file") InputStream inputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail) {

		Response response = null;
		String localhost = System.getenv("HOSTNAME");
		String id_host = System.getenv("NAME");	
		localhost=" ID :" + localhost + "  SERVICE : " + id_host;
		response = curlOcr(inputStream, fileDetail.getFileName(), localhost);
		return response;
	}
	
	public Response curlOcr(InputStream is, String filename,String localhost){
		
		
		File file =createFile(is, filename);
		Gson gson = new Gson();
		OCRText ocrText = new OCRText("OCR",file.getAbsolutePath(),"",localhost);
		String imageText = "";
		String rawResponse = "";
		String jsonResponse = "";
		String command = "curl -H apikey:53588545c788957 --form file=@/tmp/"+filename+" --form language=spa -form isOverlayRequired=false https://api.ocr.space/Parse/Image";
		
		try {
			 rawResponse = execToString(command);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		try{
			imageText = rawResponse.substring(rawResponse.indexOf("ParsedText")+12,rawResponse.indexOf("ErrorMessage"));
			ocrText= new OCRText("OCR",file.getAbsolutePath(),imageText,localhost);
			ocrText.clean();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		try {
		    file.delete();
		} catch (Exception e) {
		   //e.printStackTrace();
		}
		
		jsonResponse = gson.toJson(ocrText);
		
		return Response.status(200).entity(jsonResponse).build();
	}
	
	
	protected String execToString(String command) throws Exception {
		
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    CommandLine commandline = CommandLine.parse(command);
	    DefaultExecutor exec = new DefaultExecutor();
	    PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
	    exec.setStreamHandler(streamHandler);
	    exec.execute(commandline);
	    return outputStream.toString();
	}
	
	protected File createFile(InputStream in , String fileName){
	
		File tempFile = null;
		
		try {
			tempFile = new File("/tmp/"+fileName);
			FileOutputStream out=null;
			out = new FileOutputStream(tempFile);
			IOUtils.copy(in, out);
			} 
			catch (IOException e) {
			
				e.printStackTrace();
			}
			
		return tempFile;
	}
	
	
}


