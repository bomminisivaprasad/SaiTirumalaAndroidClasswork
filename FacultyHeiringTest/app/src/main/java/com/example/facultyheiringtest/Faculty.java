package com.example.facultyheiringtest;

class Faculty {
    String fname,fmobile,femail,fqualification,fusername,fpassword,fexperianceStatus,fimagePath;

    public Faculty(String fname, String fmobile, String femail, String fqualification, String fusername, String fpassword, String fexperianceStatus, String fimagePath) {
        this.fname = fname;
        this.fmobile = fmobile;
        this.femail = femail;
        this.fqualification = fqualification;
        this.fusername = fusername;
        this.fpassword = fpassword;
        this.fexperianceStatus = fexperianceStatus;
        this.fimagePath = fimagePath;
    }

    public Faculty() {
    }

    public String getFname() {
        return fname;
    }

    public String getFmobile() {
        return fmobile;
    }

    public String getFemail() {
        return femail;
    }

    public String getFqualification() {
        return fqualification;
    }

    public String getFusername() {
        return fusername;
    }

    public String getFpassword() {
        return fpassword;
    }

    public String getFexperianceStatus() {
        return fexperianceStatus;
    }

    public String getFimagePath() {
        return fimagePath;
    }
}
