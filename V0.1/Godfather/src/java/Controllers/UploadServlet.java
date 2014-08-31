/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.*;

public class UploadServlet extends HttpServlet {
    
    private boolean isMultipart;
    private String filePath;
    private int maxFileSize = 5000 * 1024;
    private int maxMemSize = 5000 * 1024;
    private File file;
    
    @Override
    public void init(){
        filePath = getServletContext().getInitParameter("file-upload");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        if("input".equals(request.getParameter("function"))){
            String code = "";
            String[] textArea = (request.getParameter("plain_text")).split("\n");
            for(String line : textArea){
                code += getCode(line, request.getParameter("langauge_selection"));
            }
            request.setAttribute("pseudo", request.getParameter("plain_text"));
            request.setAttribute("code", code);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }else{
            
            // check we have upload file request
            isMultipart = ServletFileUpload.isMultipartContent(request);

            if(!isMultipart){
                System.out.println("Is not multipart - whatever that means.");
                return;
            }

            DiskFileItemFactory factory = new DiskFileItemFactory();

            // max size that will be stored in memory
            factory.setSizeThreshold(maxFileSize);

            // Location to save data that is larger than maxMemSize.
            factory.setRepository(new File(""));

            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);

            // maximum file size to be uploaded.
            upload.setSizeMax( maxFileSize );

            try{
                List fileItems = upload.parseRequest(request);

                // Process the uploaded file items
                Iterator i = fileItems.iterator();
                while ( i.hasNext () ){
                    FileItem fi = (FileItem)i.next();
                    if ( !fi.isFormField () ){
                        // Get the uploaded file parameters
                        String fieldName = fi.getFieldName();
                        String fileName = fi.getName();
                        String contentType = fi.getContentType();
                        boolean isInMemory = fi.isInMemory();
                        long sizeInBytes = fi.getSize();
                        // Write the file
                        if( fileName.lastIndexOf("\\") >= 0 ){
                           file = new File( filePath + 
                           fileName.substring( fileName.lastIndexOf("\\"))) ;
                        }else{
                           file = new File( filePath + 
                           fileName.substring(fileName.lastIndexOf("\\")+1)) ;
                        }
                        fi.write( file ) ;
                        processFile(file, request.getParameter("language_selection"));
                        request.setAttribute("filename", fileName);
                   }
                }

                request.getRequestDispatcher("success.jsp").forward(request, response);

            }catch(Exception ex){
                System.out.println(ex);
            }
        }
    }
    
    
    private void processFile(File file, String language){
        try{
            File newFile = new File(file.getName()+"."+language);
            file.createNewFile();
            FileWriter writer = new FileWriter(newFile);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            
            
            while((line= reader.readLine()) != null){
                writer.write(getCode(line, language));
            }
            
            writer.flush();
            writer.close();
            reader.close();
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
    
    private String getCode(String line, String language){
        String code = "";
        /*
        LanguageMap langaugeMap = new JavaLanguageMap();
        
        // gets the correct language map
        if("java".equals(language)){
            langaugeMap = new JavaLanguageMap();
        }else if("php".equals(language)){
            langaugeMap = (LanguageMap) new JavaLanguageMap();
        }else if("py".equals(language)){
            langaugeMap = (LanguageMap) new JavaLanguageMap();
        }
        
        
        code = langaugeMap.processCommand(splitLine(line));
        */
        
        
        // basic java print example
        
        if(line.contains("print")){
            //String text="\"" + line.substring(line.indexOf("\"")) + "\"";
            code = "System.out.print(" + line.substring(line.indexOf("\"")) + ");";
        }
        
        return code;
    }
    
    private String[] splitLine(String line){
         // splits the line on \"
        String[] code = line.split("\"");
        // every 2nd word split on a \" will be a string
        if(code.length > 0){
            for(int i =1; i < code.length; i=i+2){
                code[i] = "\""+code[i]+"\"";
            }
        }
        return code;
    }
        
}