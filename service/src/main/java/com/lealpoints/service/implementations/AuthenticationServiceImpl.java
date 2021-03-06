package com.lealpoints.service.implementations;

import xyz.greatapp.libs.service.context.ThreadContextService;
import com.lealpoints.i18n.Message;
import com.lealpoints.repository.ClientUserRepository;
import com.lealpoints.repository.CompanyUserRepository;
import com.lealpoints.service.AuthenticationService;
import com.lealpoints.service.response.ServiceMessage;
import com.lealpoints.service.response.ServiceResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationServiceImpl extends BaseServiceImpl implements AuthenticationService {
    private static final Logger logger = LogManager.getLogger(AuthenticationServiceImpl.class.getName());

    private final CompanyUserRepository _companyUserRepository;
    private final ClientUserRepository _clientUserRepository;

    @Autowired
    public AuthenticationServiceImpl(ThreadContextService threadContextService,
                                     CompanyUserRepository companyUserRepository, ClientUserRepository clientUserRepository) {
        super(threadContextService);
        _companyUserRepository = companyUserRepository;
        _clientUserRepository = clientUserRepository;
    }

    public ServiceResult isValidApiKey(Integer userId, String apiKey) {
        try {
            if (apiKey.endsWith("com")) {
                return new ServiceResult(_companyUserRepository.getByCompanyUserIdApiKey(userId, apiKey) != null, ServiceMessage.EMPTY);
            } else if (apiKey.endsWith("cli")) {
                return new ServiceResult(_clientUserRepository.getByClientUserIdApiKey(userId, apiKey) != null, ServiceMessage.EMPTY);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return new ServiceResult(false, getServiceMessage(Message.COMMON_USER_ERROR));
    }
}
