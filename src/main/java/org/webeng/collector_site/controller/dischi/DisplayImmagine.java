package org.webeng.collector_site.controller.dischi;

import org.webeng.collector_site.controller.CollectorsBaseController;
import org.webeng.collector_site.data.dao.CollectorsDataLayer;
import org.webeng.collector_site.data.model.Image;
import org.webeng.framework.data.DataException;
import org.webeng.framework.result.StreamResult;
import org.webeng.framework.security.SecurityHelpers;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;

public class DisplayImmagine extends CollectorsBaseController {
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        try {
            int id_image = SecurityHelpers.checkNumeric(request.getParameter("id_image"));
            action_render(request, response, id_image);
        } catch (IOException|DataException ex) {
            handleError(ex, request, response);
        }

    }

    private void action_render(HttpServletRequest request, HttpServletResponse response, int id_image) throws DataException, IOException {
        StreamResult result = new StreamResult(getServletContext());
        Image image = ((CollectorsDataLayer) request.getAttribute("datalayer")).getImageDAO().getImage(id_image);
        if (image != null) {
            request.setAttribute("contentType", image.getImageType());
            request.setAttribute("contentDisposition", "inline");
            File file= new File(getServletContext().getInitParameter("uploads.directory") +"\\"+ image.getFileName());
            result.setResource(file);
            result.activate(request, response);
            //result.activate(image.getImageData(), image.getImageSize(), image.getImageType(), request, response);
        } else {
            throw new DataException("Image not available");
        }
    }
}
