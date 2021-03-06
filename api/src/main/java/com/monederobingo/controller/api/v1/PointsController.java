package com.monederobingo.controller.api.v1;

import com.monederobingo.controller.base.BaseController;
import com.lealpoints.service.PointsService;
import com.lealpoints.service.model.PointsAwarding;
import com.lealpoints.service.response.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/v1/points")
public class PointsController extends BaseController {

    private PointsService _pointsService;

    @Autowired
    public PointsController(PointsService pointsService) {
        _pointsService = pointsService;
    }

    @RequestMapping(method = POST, headers = ACCEPT_HEADER)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<ServiceResult<Float>> awardPoints(@RequestBody PointsAwarding pointsAwarding) {
        long companyId = pointsAwarding.getCompanyId();
        pointsAwarding.setCompanyId(companyId);
        ServiceResult<Float> serviceResult = _pointsService.awardPoints(pointsAwarding);
        return new ResponseEntity<>(serviceResult, HttpStatus.OK);
    }
}
