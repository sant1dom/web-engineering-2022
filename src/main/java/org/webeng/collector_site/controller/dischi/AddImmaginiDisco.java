package org.webeng.collector_site.controller.dischi;

import org.webeng.collector_site.controller.CollectorsBaseController;
import org.webeng.collector_site.data.dao.CollectorsDataLayer;
import org.webeng.collector_site.data.impl.ImageImpl;
import org.webeng.collector_site.data.model.Disco;
import org.webeng.collector_site.data.model.Image;
import org.webeng.framework.result.TemplateManagerException;
import org.webeng.framework.result.TemplateResult;
import org.webeng.framework.security.SecurityHelpers;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AddImmaginiDisco extends CollectorsBaseController {
    public static final String REFERRER = "referrer";
    public static final String UPLOAD_LOCATION= "C:/Users/Raluc/Documents/Workspace";
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        if(request.getMethod().equals("POST")){
            addImmagini(request,response);
        }else {
            try {
                HttpSession s = SecurityHelpers.checkSession(request);
                String https_redirect_url = SecurityHelpers.checkHttps(request);
                request.setAttribute("https-redirect", https_redirect_url);
                if (s == null) {
                    action_anonymous(request, response);
                }else{
                    action_logged(request, response);
                }
            } catch (TemplateManagerException  /* | xDataException |*/ | IOException ex) {
                handleError(ex, request, response);
            }
        }
    }

    private void action_logged(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException {
        HttpSession s= SecurityHelpers.checkSession(request);
        String idDisco= (String) s.getAttribute("idDisco");
        request.setAttribute("idDisco", idDisco);
        TemplateResult result = new TemplateResult(getServletContext());
        result.activate("disco/aggiungiImmaginiDisco.ftl", request,response );
    }

    private void action_anonymous(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setAttribute(REFERRER, request.getParameter(REFERRER));
        response.sendRedirect("/login");
    }

    private void addImmagini(HttpServletRequest request, HttpServletResponse response) {
        try {
            System.out.println("hello");
            HttpSession s= SecurityHelpers.checkSession(request);
            Disco disco = ((CollectorsDataLayer) request.getAttribute("datalayer")).getDiscoDAO().getDisco(Integer.parseInt((String) s.getAttribute("idDisco")));
            System.out.println("hello");
            if (request.getParts() != null) {
                List<Part> files_to_upload = request.getParts().stream().filter(part -> "file".equals(part.getName())).collect(Collectors.toList());
                if(files_to_upload!=null) {
                    Collection<File> files_uploaded = new ArrayList<>();
                    System.out.println("hello");
                    for (Part image : files_to_upload) {
                        File uploaded_file =  File.createTempFile("upload_", "", new File(getServletContext().getInitParameter("uploads.directory")));
                        try (InputStream is = image.getInputStream();
                             OutputStream os = new FileOutputStream(uploaded_file);) {
                            byte[] buffer = new byte[1024];
                            int read;
                            while ((read = is.read(buffer)) > 0) {
                                os.write(buffer, 0, read);
                            }
                        }
                        files_uploaded.add(uploaded_file);
                    }
                    System.out.println("hi");
                    for (Part file_to_upload : files_to_upload) {
                        for (File file_uploaded : files_uploaded) {
                            Image immagine = new ImageImpl();
                            immagine.setImageSize(file_to_upload.getSize());
                            immagine.setImageType(file_to_upload.getContentType());
                            immagine.setFileName(file_uploaded.getName());
                            immagine.setDisco(disco);
                            System.out.println(immagine.getFileName());
                            disco.getImmagini().add(immagine);
                        }

                    }
                    ((CollectorsDataLayer) request.getAttribute("datalayer")).getImageDAO().storeImages(disco.getImmagini());
                }

                response.sendRedirect("/home");
            }
        }catch (Exception e){
            handleError(e, request, response);
        }

    }

}

