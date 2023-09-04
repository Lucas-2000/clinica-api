package br.com.dev.clinica.domain.appointment;

public enum ConsultType {
    PRIVATE("private"),
    HEALTH_INSURANCE("health insurance");

    private String consultType;

    ConsultType(String consultType) {
        this.consultType = consultType;
    }

    public String getConsultType(){
        return consultType;
    }
}
