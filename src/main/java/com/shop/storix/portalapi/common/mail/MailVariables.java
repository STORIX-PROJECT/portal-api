package com.shop.storix.portalapi.common.mail;

import java.util.Map;

public interface MailVariables {
    String getTemplateName();
    String getSubjectKey();
    Map<String, Object> toMap();
}