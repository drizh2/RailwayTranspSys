package edu.drizh.entity;

public class Contract {
    private Integer sumOfInsurance;
    private String typeOfCargo;
    private Integer timeToTransport;
    private String startStation;
    private String finishStation;
    private Integer cost;
    private Integer weight;
    private String date;
    private Long id;

    public Contract(Integer sumOfInsurance, String typeOfCargo, Integer timeToTransport, String startStation, String finishStation, Integer cost, Integer weight, String date, Long id) {
        this.sumOfInsurance = sumOfInsurance;
        this.typeOfCargo = typeOfCargo;
        this.timeToTransport = timeToTransport;
        this.startStation = startStation;
        this.finishStation = finishStation;
        this.cost = cost;
        this.weight = weight;
        this.date = date;
        this.id = id;
    }

    public Integer getSumOfInsurance() {
        return sumOfInsurance;
    }

    public void setSumOfInsurance(Integer sumOfInsurance) {
        this.sumOfInsurance = sumOfInsurance;
    }

    public String getTypeOfCargo() {
        return typeOfCargo;
    }

    public void setTypeOfCargo(String typeOfCargo) {
        this.typeOfCargo = typeOfCargo;
    }

    public Integer getTimeToTransport() {
        return timeToTransport;
    }

    public void setTimeToTransport(Integer timeToTransport) {
        this.timeToTransport = timeToTransport;
    }

    public String getStartStation() {
        return startStation;
    }

    public void setStartStation(String startStation) {
        this.startStation = startStation;
    }

    public String getFinishStation() {
        return finishStation;
    }

    public void setFinishStation(String finishStation) {
        this.finishStation = finishStation;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
