package com.spectra.fieldforce.model;

public class ValidateUserResponse {
        private String Status;
        private String StatusCode;
        private UserResponse Response;

    public String getStatus() {
        return Status;
    }

    public String getStatusCode() {
        return StatusCode;
    }

    public UserResponse getResponse() {
        return Response;
    }

    public class UserResponse{
            private String Position;
            private String Manager;
            private String EmployeeID;
            private String OperatingCity;
            private String MobilePhone;
            private String PrimaryEmail;
            private String FullName;
            private String UserName;

        public String getPosition() {
            return Position;
        }

        public String getManager() {
            return Manager;
        }

        public String getEmployeeID() {
            return EmployeeID;
        }

        public String getOperatingCity() {
            return OperatingCity;
        }

        public String getMobilePhone() {
            return MobilePhone;
        }

        public String getPrimaryEmail() {
            return PrimaryEmail;
        }

        public String getFullName() {
            return FullName;
        }

        public String getUserName() {
            return UserName;
        }
    }
}
