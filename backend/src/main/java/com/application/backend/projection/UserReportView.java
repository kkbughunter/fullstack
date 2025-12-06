package com.application.backend.projection;

public interface UserReportView {
    String getFullName();   // CONCAT
    String getUpperName();  // UPPER
    Long getUserCount();    // COUNT
}
