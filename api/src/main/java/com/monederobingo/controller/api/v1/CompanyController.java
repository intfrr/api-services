package com.monederobingo.controller.api.v1;

import com.lealpoints.service.CompanyService;
import com.lealpoints.service.response.ServiceResult;
import com.monederobingo.controller.base.BaseController;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/v1/companies")
@MultipartConfig
public class CompanyController extends BaseController {

    private CompanyService _companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        _companyService = companyService;
    }

    @RequestMapping(value = "/{companyId}", method = GET)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<xyz.greatapp.libs.service.ServiceResult> get(@PathVariable("companyId") long companyId) {
        xyz.greatapp.libs.service.ServiceResult serviceResult = _companyService.getByCompanyId(companyId);
        return new ResponseEntity<>(serviceResult, HttpStatus.OK);
    }

    @RequestMapping(value = "/logo/{companyId}", method = POST)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<ServiceResult> updateLogo(@PathVariable("companyId") long companyId,
                                                    HttpServletRequest httpServletRequest) throws FileUploadException {
        ServletFileUpload servletFileUpload = getServletFileUpload();
        List<FileItem> items = servletFileUpload.parseRequest(httpServletRequest);
        final ServiceResult serviceResult = _companyService.updateLogo(items, companyId);
        return new ResponseEntity<>(serviceResult, HttpStatus.OK);

    }

    ServletFileUpload getServletFileUpload() {
        return new ServletFileUpload(new DiskFileItemFactory());
    }

}
